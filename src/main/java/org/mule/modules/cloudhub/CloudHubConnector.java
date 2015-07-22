/**
 * Mule CloudHub Connector
 * <p/>
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub;

import com.mulesoft.ch.rest.model.*;
import com.mulesoft.cloudhub.client.CloudHubConnectionImpl;
import com.mulesoft.cloudhub.client.CloudHubDomainConnectionI;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleEvent;
import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.RefOnly;
import org.mule.modules.cloudhub.configs.CloudHubConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
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
@Connector(name = "cloudhub", schemaVersion = "2.0", friendlyName = "Cloudhub") public class CloudHubConnector {

    public static final String TENANT_ID_PROPERTY = "tenantId";
    public static final String EXCEPTION_MESSAGE_CUSTOM_PROPERTY = "exception.message";
    public static final String EXCEPTION_STACKTRACE_CUSTOM_PROPERTY = "exception.stacktrace";
    public static final String DOMAIN_SYSTEM_PROPERTY = "domain";

    private Logger logger = LoggerFactory.getLogger(CloudHubConnector.class);

    //
    @ConnectionStrategy
    private CloudHubConfig connectionStrategy;

    /**
     * Deploy specified application.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:deploy-application}
     *
     * @param file   mule application to deploy, Input Object type:
     *               java.io.InputStream
     * @param domain The application domain.
     */
    @Processor
    public void deployApplication(@Default("#[payload]") InputStream file, String domain) {
        client().connectWithDomain(domain)
                .deployApplication(file, getConnectionStrategy().getMaxWaitTime());
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
    public void createAndDeployApplication(@Default("#[payload]") InputStream file, String domain, @Default("3.7.0") String muleVersion, @Default("1") int workerCount,
                                           @Optional Map<String, String> environmentVariables, @Optional Boolean persistentQueues, @Optional Boolean multitenanted) {

        createApplication(domain,muleVersion,environmentVariables,workerCount,persistentQueues,multitenanted);
        CloudHubDomainConnectionI connection = client().connectWithDomain(domain);
        connection.deployApplication(file, getConnectionStrategy().getMaxWaitTime());
    }

    /**
     *
     * @param domain
     * @param muleVersion
     * @param environmentVariables
     * @param workersCount
     * @param persistentQueues
     * @param multitenanted
     * @return
     */
    @Processor
    public Application createApplication(String domain, @Default("3.7.0") String muleVersion, Map<String,String> environmentVariables, @Default("1") Integer workersCount, @Optional Boolean persistentQueues, @Optional Boolean multitenanted ){
        Application app = new Application();
        app.setMuleVersion(muleVersion);
        app.setProperties(environmentVariables);
        app.setWorkers(workersCount);
        app.setPersistentQueues(persistentQueues);
        app.setDomain(domain);
        app.setMultitenanted(multitenanted);
        client().createApplication(app);
        return app;
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
    public Collection<Application> listApplications() {
        return client().retrieveApplications();
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
        return client().connectWithDomain(domain).retrieveApplication();
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
    public void updateApplication(@RefOnly @Default("#[payload]") Application application) {
        ApplicationUpdateInfo appUpdateInfo = new ApplicationUpdateInfo(application);
        client().connectWithDomain(application.getDomain()).updateApplication(appUpdateInfo);
    }

    /**
     * Change Application Status
     *
     * @param domain The application domain
     * @param newDesiredStatus New application desired status (Start/Stop)
     */
    @Processor
    public void changeApplicationStatus(String domain, ApplicationStatusChange.DesiredApplicationStatus newDesiredStatus) {
        ApplicationStatusChange statusChange = new ApplicationStatusChange(newDesiredStatus);
        client().connectWithDomain(domain).updateApplicationStatus(statusChange, getConnectionStrategy().getMaxWaitTime());
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
        client().connectWithDomain(domain).deleteApplication();
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
     * @throws <p> Cloudhub exception in case there was a problem with cloudhub
     *             communication
     *             </p>
     */
    @Processor
    public NotificationResults retrieveNotifications(@Optional Integer maxResults, @Optional Integer offset,Notification.NotificationStatus status, MuleEvent muleEvent, String message, String domain) {
        return client().retrieveNotifications(domain,getTenantIdFrom(muleEvent),maxResults,offset,status,message);
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
     * @throws <p> A CloudhubException in case the notification could not be created
     *             </p>
     * @since 1.4
     */
    @Processor
    public void createNotification(String message, Notification.NotificationLevelDO priority, @Optional Map<String, String> customProperties, MuleEvent muleEvent) {

        Notification notification = new Notification();
        notification.setPriority(priority);
        notification.setMessage(message);
        notification.setDomain(getDomain());
        notification.setTenantId(getTenantIdFrom(muleEvent));
        notification.setTransactionId(getTransactionIdFrom(muleEvent));

        if (getDomain() != null) {
            client().createNotification(notification);
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
     * @return an instance of {@link com.mulesoft.ch.rest.model.TenantResults}
     */
    @Processor
    public TenantResults listTenants(String domain, @Default("25") Integer limit, Integer offset, String query, Boolean enabled) {
        return client().connectWithDomain(domain).retrieveTenants(limit, offset, query, enabled);
    }

    /**
     * <p>
     * Creates a tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:create-tenant}
     *
     * @param tenant an instance of {@link com.mulesoft.ch.rest.model.Tenant}
     *               representing the tenant
     * @param domain the domain that will own the tenant
     * @return an instance of {@link com.mulesoft.ch.rest.model.Tenant}
     * carrying the state of the newly created tenant
     */
    @Processor
    public Tenant createTenant(@RefOnly @Default("#[payload]") Tenant tenant, String domain) {
        return client().connectWithDomain(domain).createTenant(tenant);
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
     * @return an instance of {@link com.mulesoft.ch.rest.model.Tenant}
     */
    @Processor
    public Tenant getTenant(String domain, String tenantId) {
        return client().connectWithDomain(domain).retrieveTenant(tenantId);
    }

    /**
     * <p>
     * Updates a tenant
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:update-tenant}
     *
     * @param tenant an instance of {@link com.mulesoft.ch.rest.model.Tenant}
     *               with the tenant's new state
     * @param domain the domain that will own the tenant
     * @return an instance of {@link com.mulesoft.ch.rest.model.Tenant}
     * carrying the tenant's updated state
     */
    @Processor
    public Tenant updateTenant(@RefOnly @Default("#[payload]") Tenant tenant, String domain) {
        return client().connectWithDomain(domain).updateTenant(tenant);
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
        client().connectWithDomain(domain).deleteTenant(tenantId);
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
        client().connectWithDomain(domain).deleteTenants(tenantIds);
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

    private String getDomain() {
        return System.getProperty(DOMAIN_SYSTEM_PROPERTY);
    }

    public CloudHubConfig getConnectionStrategy() {
        return connectionStrategy;
    }

    public void setConnectionStrategy(CloudHubConfig connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

    private CloudHubConnectionImpl client() {
        return connectionStrategy.getClient();
    }

}
