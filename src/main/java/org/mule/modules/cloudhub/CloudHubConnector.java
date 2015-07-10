/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.cloudhub;

import com.mulesoft.cloudhub.client.*;
import com.mulesoft.cloudhub.client.Notification.Priority;
import org.mule.api.ConnectionException;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleEvent;
import org.mule.api.annotations.*;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the ability to interact with Mule CloudHub from within a Mule
 * application. There are operations to deploy, start, stop, and update
 * applications as well as send notifications from your application to CloudHub.
 * <p/>
 * When running this connector in an application inside CloudHub, it will use
 * token based authentication to access the API. This will allow access and
 * usage of the CloudHub APIs without the need to specify your username and
 * password.
 * <p/>
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "cloudhub", schemaVersion = "1.0", friendlyName = "Cloudhub", minMuleVersion = "3.6.0") public class CloudHubConnector {

    public static final String TENANT_ID_PROPERTY = "tenantId";
    public static final String EXCEPTION_MESSAGE_CUSTOM_PROPERTY = "exception.message";
    public static final String EXCEPTION_STACKTRACE_CUSTOM_PROPERTY = "exception.stacktrace";
    public static final String DOMAIN_SYSTEM_PROPERTY = "domain";

    private Logger logger = LoggerFactory.getLogger(Connection.class);

    /**
     * CloudHub URL.
     */
    @Configurable
    @Default(value = Connection.DEFAULT_URL)
    private String url;

    /**
     * CloudHub username.
     */
    private String username;

    /**
     * CloudHub password.
     */
    private String password;

    /**
     * Maximum time allowed to deplpoy/undeploy.
     */
    @Configurable
    @Default(value = "0")
    @FriendlyName("Maximum time allowed to deplpoy/undeploy.")
    private Long maxWaitTime;

    private CloudhubConnection connection;

    private static String getTransactionIdFrom(MuleEvent muleEvent) {
        return muleEvent.getMessage().getMessageRootId();
    }

    private static String getTenantIdFrom(MuleEvent muleEvent) {
        return muleEvent.getMessage().getInboundProperty(TENANT_ID_PROPERTY);
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Deploy specified application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:deploy-application}
     *
     * @param file                 mule application to deploy, Input Object type:
     *                             java.io.InputStream
     * @param domain               The application domain.
     * @param muleVersion          The version of Mule, e.g. 3.4.1.
     * @param workerCount          The number of workers to deploy.
     * @param environmentVariables Environment variables for you application.
     */
    @Processor
    public void deployApplication(@Default("#[payload]") InputStream file, String domain, @Default("3.4.1") String muleVersion, @Default("1") int workerCount,
            @Optional Map<String, String> environmentVariables) {

        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.deploy(file, muleVersion, workerCount, this.maxWaitTime, environmentVariables);
    }

    /**
     * Tries creating the specified application (if it doesn't exist already)
     * and deploying afterwards.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:create-and-deploy-application}
     *
     * @param file                 mule application to deploy, Input Object type:
     *                             java.io.InputStream
     * @param domain               The application domain.
     * @param muleVersion          The version of Mule, e.g. 3.4.1.
     * @param workerCount          The number of workers to deploy.
     * @param environmentVariables Environment variables for you application.
     */
    @Processor
    public void createAndDeployApplication(@Default("#[payload]") InputStream file, String domain, @Default("3.4.1") String muleVersion, @Default("1") int workerCount,
            @Optional Map<String, String> environmentVariables) {

        final DomainConnection domainConnection = getConnection().on(domain);

        if (domainConnection.available()) {
            Application app = new Application();
            app.setDomain(domain);
            app.setHasFile(false);
            app.setWorkers(1);
            domainConnection.createApplication(app);
        }

        domainConnection.deploy(file, muleVersion, workerCount, this.maxWaitTime, environmentVariables);
    }

    /**
     * List applications.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:list-applications}
     *
     * @return A list of applications.
     */
    @Processor
    public List<Application> listApplications() {
        return getConnection().listApplications();
    }

    /**
     * Get an application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:get-application}
     *
     * @param domain The application domain.
     * @return An application.
     */
    @Processor
    public Application getApplication(String domain) {
        return getConnection().on(domain).get();
    }

    /**
     * Update an application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:update-application}
     *
     * @param application The application to update.
     */
    @Processor
    public void updateApplication(@Default("#[payload]") Application application) {
        ApplicationUpdateInfo appUdateInfo = new ApplicationUpdateInfo(application);
        getConnection().on(application.getDomain()).update(appUdateInfo);
    }

    /**
     * Start an application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:start-application}
     *
     * @param domain The application domain.
     */
    @Processor
    public void startApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.start(this.maxWaitTime);
    }

    /**
     * Stop an application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:stop-application}
     *
     * @param domain The application domain.
     */
    @Processor
    public void stopApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.stop();
    }

    /**
     * <p>
     * Delete an application.
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:delete-application}
     *
     * @param domain <p>
     *               The application domain.
     *               </p>
     */
    @Processor
    public void deleteApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.delete();
    }

    /**
     * <p>
     * List a user's notifications.
     * </p>
     * <p/>
     * <p>
     * In the case of a multitenant application it searches for the
     * notifications registered for the current tenant.
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:list-notifications}
     *
     * @param maxResults <p>
     *                   The maximum number of results to retrieve.
     *                   </p>
     * @param offset     <p>
     *                   The offset to start listing alerts from.
     *                   </p>
     * @param muleEvent  <p>
     *                   Processed mule event
     *                   </p>
     * @return A List of notifications.
     */
    @Processor
    public NotificationResults listNotifications(@Optional Integer maxResults, @Optional Integer offset, MuleEvent muleEvent) {
        return getConnection().listNotifications(maxResults, offset, getTenantIdFrom(muleEvent));
    }

    /**
     * <p>
     * Dismiss an individual notification.
     * </p>
     * <p/>
     * <p>
     * This operation does not depend on the tenant environment.
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:dismiss-notification}
     *
     * @param href <p>
     *             The href property of the Notification object.
     *             </p>
     */
    @Processor
    public void dismissNotification(String href) {
        getConnection().dismissNotification(href);
    }

    /**
     * <p>
     * Create a notification inside CloudHub.
     * </p>
     * <p/>
     * <p>
     * If the notification is sent after an exception, it attaches the
     * exception.message and exception.stacktrace as as custom properties of the
     * notification.
     * <p/>
     * Those custom properties can be accessed from Cloudhub console with the
     * names 'exception.message' and 'exception.stacktrace'
     * </p>
     * <p/>
     * <p>
     * In the case of multitenant applications the connector will create a
     * notification for a particular tenant. (the one that is executing the
     * flow)
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:create-notification}
     *
     * @param message          <p>
     *                         The contents of the notification.
     *                         </p>
     * @param priority         <p>
     *                         The notification priority.
     *                         </p>
     * @param customProperties <p>
     *                         a map to represent custom placeholders on the notification
     *                         template
     *                         </p>
     * @param muleEvent        <p>
     *                         Processed mule event
     *                         </p>
     */
    @Processor
    public void createNotification(String message, Priority priority, @Optional Map<String, String> customProperties, MuleEvent muleEvent) {
        customProperties = merge(customProperties, handleException(muleEvent));

        String domain = getDomain();

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setPriority(priority);
        notification.setDomain(domain);
        notification.setCustomProperties(customProperties);
        notification.setTenantId(getTenantIdFrom(muleEvent));
        notification.setTransactionId(getTransactionIdFrom(muleEvent));
        if (domain != null) {
            getConnection().create(notification);
        } else {
            logger.info("Cloudhub connector is running in a stand alone application, so it won't create a notification");
        }
    }

    /**
     * List all available tenants for the current domain
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:list-tenants}
     *
     * @param domain the domain owning the tenants
     * @param limit  The maximum number of results to return by default. Maximum of
     *               100.
     * @param offset The offset to start searching at
     * @param query  The company name, contact name, and email of the tenant to
     *               search form. Performs a case insensitive match to any part of
     *               the tenant name.
     * @return an instance of {@link com.mulesoft.cloudhub.client.TenantResults}
     */
    @Processor
    public TenantResults listTenants(String domain, @Default("25") Integer limit, @Optional Integer offset, @Optional String query) {

        return this.getConnection().listTenants(domain, limit, offset, query);
    }

    /**
     * <p>
     * Creates a tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:create-tenant}
     *
     * @param tenant an instance of {@link com.mulesoft.cloudhub.client.Tenant}
     *               representing the tenant
     * @param domain the domain that will own the tenant
     * @return an instance of {@link com.mulesoft.cloudhub.client.Tenant}
     * carrying the state of the newly created tenant
     */
    @Processor
    public Tenant createTenant(@Default("#[payload]") Tenant tenant, String domain) {
        return this.getConnection().create(tenant, domain);
    }

    /**
     * <p>
     * Returns an specific tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:get-tenant}
     *
     * @param domain   the domain owning the tenants
     * @param tenantId the id of the tenant you want
     * @return an instance of {@link com.mulesoft.cloudhub.client.Tenant}
     */
    @Processor
    public Tenant getTenant(String domain, String tenantId) {
        return this.getConnection().getTenant(domain, tenantId);
    }

    /**
     * <p>
     * Updates a tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:update-tenant}
     *
     * @param tenant an instance of {@link com.mulesoft.cloudhub.client.Tenant}
     *               with the tenant's new state
     * @param domain the domain that will own the tenant
     * @return an instance of {@link com.mulesoft.cloudhub.client.Tenant}
     * carrying the tenant's updated state
     */
    @Processor
    public Tenant updateTenant(@Default("#[payload]") Tenant tenant, String domain) {
        return this.getConnection().update(tenant, domain);
    }

    /**
     * <p>
     * Deletes a given tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:delete-tenant}
     *
     * @param tenantId the id of the tenant to be deleted
     * @param domain   the domain that owns the tenant to be deleted
     */
    @Processor
    public void deleteTenant(String domain, String tenantId) {
        this.getConnection().delete(tenantId, domain);
    }

    /**
     * <p>
     * Deletes all tenants for a given domain
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:delete-tenants}
     *
     * @param domain    the domain you want to clear of tenants
     * @param tenantIds a list with tenant ids to be deleted
     */
    @Processor
    public void deleteTenants(String domain, List<String> tenantIds) {
        this.getConnection().deleteTenants(domain, tenantIds);
    }

    private Map<String, String> merge(Map<String, String> customProperties, Map<String, String> exceptionProperties) {
        if (!exceptionProperties.isEmpty()) {
            if (customProperties == null) {
                customProperties = new HashMap<String, String>();
            }

            customProperties.putAll(exceptionProperties);
        }
        return customProperties;
    }

    private Map<String, String> handleException(MuleEvent muleEvent) {
        ExceptionPayload exceptionPayload = muleEvent.getMessage().getExceptionPayload();
        Map<String, String> customProperties = new HashMap<String, String>();
        if (exceptionPayload != null) {
            customProperties.put(EXCEPTION_MESSAGE_CUSTOM_PROPERTY, exceptionPayload.getMessage());
            customProperties.put(EXCEPTION_STACKTRACE_CUSTOM_PROPERTY, getStackTrace(exceptionPayload.getException()));
        }

        return customProperties;
    }

    private String getDomain() {
        return System.getProperty(DOMAIN_SYSTEM_PROPERTY);
    }

    /**
     * @param userName The CloudHub user name
     * @param password The CloudHub password
     * @throws ConnectionException If a connection cannot be made
     */
    @Connect
    @TestConnectivity(active = false)
    public void connect(@ConnectionKey String userName, String password) throws ConnectionException {
        this.username = userName;
        this.connection = new Connection(this.url, userName, password, false);

    }

    @Disconnect
    public void disconnect() {
        connection = null;
    }

    @ValidateConnection
    public boolean isConnected() {
        return connection != null && connection.test();
    }

    protected CloudhubConnection getConnection() {
        return this.connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @ConnectionIdentifier
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(final Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

}
