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

import com.mulesoft.ch.rest.model.*;
import com.mulesoft.cloudhub.client.CloudHubConnectionImpl;
import com.mulesoft.cloudhub.client.CloudHubDomainConnectionI;
import org.mule.api.MuleEvent;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.RefOnly;
import org.mule.api.callback.SourceCallback;
import org.mule.modules.cloudhub.config.Config;
import org.mule.modules.cloudhub.utils.LogPriority;
import org.mule.modules.cloudhub.utils.WorkerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

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
@Connector(name = "cloudhub", schemaVersion = "2.0", friendlyName = "Cloudhub", minMuleVersion = "3.6.0")
public class CloudHubConnector {

    public static final String TENANT_ID_PROPERTY = "tenantId";
    public static final String DOMAIN_SYSTEM_PROPERTY = "domain";

    private static final Logger  logger = LoggerFactory.getLogger(CloudHubConnector.class);

    @org.mule.api.annotations.Config
    private Config config;

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
        client().connectWithDomain(domain).deployApplication(file, getConfig().getMaxWaitTime());
    }

    /**
     * Tries creating the specified application (if it doesn't exist already)
     * and deploying afterwards.
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:create-and-deploy-application}
     *
     * @param file                  mule application to deploy, Input Object type:
     *                              java.io.InputStream
     * @param domain                The application domain.
     * @param muleVersion           The version of Mule, e.g. 3.7.0.
     * @param workersCount          The number of workers to deploy.
     * @param environmentVariables  Environment variables for you application.
     * @param workerSize            Size of each worker (Micro/Small/Medium/Large/xLarge)
     * @param persistentQueues      Defines if the Application will have PersistentQueues Enabled or not
     * @param multitenanted         Defines if the application will have multi-tenancy enabled or not
     * @param vpnEnabled            If Enabled, the application will be hosted in a worker with VPN support
     * @param autoRestartMonitoring If enabled, Monitoring Autorestart will be enabled
     */
    @Processor
    public void createAndDeployApplication(@Default("#[payload]") InputStream file, String domain, @Default("3.7.0") String muleVersion, @Default("1") Integer workersCount,
            @Optional WorkerType workerSize, @Optional Map<String, String> environmentVariables, @Optional Boolean persistentQueues, @Optional Boolean multitenanted,
            @Optional Boolean vpnEnabled, @Optional Boolean autoRestartMonitoring) {

        createApplication(domain, muleVersion, workersCount, workerSize, environmentVariables, falseInNull(persistentQueues), falseInNull(multitenanted), falseInNull(vpnEnabled),
                falseInNull(autoRestartMonitoring));
        CloudHubDomainConnectionI connection = client().connectWithDomain(domain);
        connection.deployApplication(file, getConfig().getMaxWaitTime());
    }

    /**
     * Creates an application with out deploying a Mule App
     *
     * @param domain                The application domain
     * @param muleVersion           The version of Mule to use. e.g. 3.7.0
     * @param environmentVariables  Environment variables for your Mule Application
     * @param workersCount          Number of workers to deploy
     * @param workerSize            Size of each worker (Micro/Small/Medium/Large/xLarge)
     * @param persistentQueues      Support for presistent queues
     * @param multitenanted         Support for multi tenancy
     * @param vpnEnabled            Support for VPN
     * @param autoRestartMonitoring Support for auto restart monitoring
     * @return The created application
     */
    @Processor
    public Application createApplication(String domain, @Default("3.7.0") String muleVersion, @Default("1") Integer workersCount,
                                         @Optional WorkerType workerSize, @Optional Map<String, String> environmentVariables, @Optional Boolean persistentQueues, @Optional Boolean multitenanted,
                                         @Optional Boolean vpnEnabled, @Optional Boolean autoRestartMonitoring) {
        Application app = buildApplication(domain, muleVersion, workersCount, workerSize, environmentVariables, persistentQueues, multitenanted, vpnEnabled, autoRestartMonitoring);
        return client().createApplication(app);
    }

    /**
     * Updates an application.
     *
     * <p/>
     * {@sample.xml ../../../doc/CloudHub-connector.xml.sample
     * cloudhub:update-application}
     * @param domain                The application Domain
     * @param muleVersion           The version of Mule to use. e.g. 3.7.0
     * @param workersCount          Number of workers to deploy
     * @param workerSize            Size of each worker (Micro/Small/Medium/Large/xLarge)
     * @param environmentVariables  Environment variables for your application
     * @param persistentQueues      Defines if the Application will have PersistentQueues Enabled or not
     * @param multitenanted         Defines if the application will have multi-tenancy enabled or not
     * @param vpnEnabled            If Enabled, the application will be hosted in a worker with VPN support
     * @param autoRestartMonitoring If enabled, Monitoring Autorestart will be enabled
     * @return The result of the update operation
     */
    @Processor
    public Application updateApplication(String domain, @Default("3.7.0") String muleVersion, @Default("1") Integer workersCount,
                                         @Optional WorkerType workerSize, @Optional Map<String, String> environmentVariables, @Optional Boolean persistentQueues, @Optional Boolean multitenanted,
                                         @Optional Boolean vpnEnabled, @Optional Boolean autoRestartMonitoring) {
        Application app = buildApplication(domain, muleVersion, workersCount, workerSize, environmentVariables, persistentQueues, multitenanted, vpnEnabled, autoRestartMonitoring);

        ApplicationUpdateInfo appUpdateInfo = new ApplicationUpdateInfo(app);
        return client().connectWithDomain(app.getDomain()).updateApplication(appUpdateInfo);
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
     * Get application log
     *
     * @param domain        The Application Domain
     * @param endDate       Enddate of the logs to retrieve
     * @param startDate     Startdate of the logs to retrieve
     * @param limit         Amount of log items to retrieve (Defaults to 100)
     * @param offset        For paging, start at
     * @param priority      Specifies log items of a specific priority (INFO, ERROR)
     * @param search        Specific string to search for
     * @param tail          If enabled the limit works as a tail -xxxx lines
     * @param worker        Worker name
     * @return LogResults, POJO that contains the requested logs
     */
    @Processor
    public LogResults retrieveApplicationLogs(String domain, @Optional String endDate, @Optional String startDate, @Default("100") Integer limit, @Optional Integer offset,
                                              @Optional LogPriority priority, @Optional String search, @Optional Boolean tail, @Optional String worker) {

        Map<String, String> queryParams = new HashMap<String, String>();
        addToMapIfNotNull("endDate", endDate, queryParams);
        addToMapIfNotNull("startDate", startDate, queryParams);
        addToMapIfNotNull("limit", limit, queryParams);
        addToMapIfNotNull("offset", offset, queryParams);
        addToMapIfNotNull("priority", priority, queryParams);
        addToMapIfNotNull("search", search, queryParams);
        addToMapIfNotNull("tail", tail, queryParams);
        addToMapIfNotNull("worker", worker, queryParams);

        return client().connectWithDomain(domain).retrieveApplicationLog(queryParams);
    }

    /**
     * Change Application Status
     *
     * @param domain           The application domain
     * @param newDesiredStatus New application desired status (Start/Stop)
     */
    @Processor
    public void changeApplicationStatus(String domain, ApplicationStatusChange.DesiredApplicationStatus newDesiredStatus) {
        ApplicationStatusChange statusChange = new ApplicationStatusChange(newDesiredStatus);
        client().connectWithDomain(domain).updateApplicationStatus(statusChange, getConfig().getMaxWaitTime());
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
     * @param domain        The application Domain
     * @param maxResults    The maximum number of results to retrieve.     
     * @param offset        The offset to start listing alerts from.
     * @param status        The status which the notifications are in (by Default lists READ)
     *
     * @return A List of notifications.
     *
     */
    //TODO -- The status filter is not working properly
    @Processor
    public NotificationResults listNotifications(@Optional String domain, @Default("25") Integer maxResults, @Optional Integer offset, @Default("READ")Notification.NotificationStatus.Status status) {

        Notification.NotificationStatus statusPojo = new Notification.NotificationStatus(status);
        return client().retrieveNotifications(domain, "", maxResults, offset, statusPojo, "");
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
     *
     * @param domain            The application domain.
     @param customProperties <p>
     *                         a map to represent custom placeholders on the notification
     *                         template
     *                         </p>
     * @param muleEvent        <p>
     *                         Processed mule event
     *                         </p>
     *
     * @since 1.4
     */
    @Processor
    public void createNotification(String message, Notification.NotificationLevelDO priority, String domain,
            @Optional Map<String, String> customProperties, MuleEvent muleEvent) {

        Notification notification = new Notification();
        notification.setPriority(priority);
        notification.setMessage(message);
        notification.setDomain(domain);
        notification.setTenantId(getTenantIdFrom(muleEvent));
        notification.setTransactionId(getTransactionIdFrom(muleEvent));

        client().createNotification(notification);
    }

    /**
     * Change the notification status (READ or UNREAD)
     *
     * @param notificationId ID of the notification
     * @param status New desired status
     */
    @Processor
    public void changeNotificationStatus(@FriendlyName("Notification ID") String notificationId, Notification.NotificationStatus.Status status) {
        Notification.NotificationStatus notificationStatus = new Notification.NotificationStatus(status);
        client().updateNotificationStatus(notificationId, notificationStatus);
    }

    /**
     * Retrieves a notification by their ID
     *
     * @param notificationId ID of the notification
     * @return A notification
     */
    @Processor
    public Notification getNotification(@FriendlyName("Notification ID") String notificationId) {
        return client().retrieveNotification(notificationId);
    }

    /**
     * Poll notifications
     *
     * @param source           SourceCallback
     * @param poolingFrequency Pooling frequency
     * @param amountToRetrieve Amount to retrieve each time
     * @param domain           Domain to filter notifications
     * @param markAsRead       Boolean to mark as read after been processed
     * @return The received notification
     * @throws Exception       Exception that occurs when notifications cannot be received.
     */
    @Source
    public Notification pollNotifications(SourceCallback source, @Optional String domain, @Default("5000") Integer poolingFrequency, @Default("50") Integer amountToRetrieve, @Optional Boolean markAsRead) throws Exception {
        markAsRead = falseInNull(markAsRead);
        Date date = new Date();
        Date newDate = null;
        while (true) {
            NotificationResults notificationResults = this.listNotifications(domain, amountToRetrieve, null, Notification.NotificationStatus.Status.READ);
            if(!notificationResults.getData().isEmpty()) {

                Notification newestNotification = notificationResults.getData().remove(0);
                if (newestNotification.getCreatedAt().compareTo(date) == 1) {
                    processNotification(source, markAsRead, date, newestNotification);
                    newDate = newestNotification.getCreatedAt();

                    for (Notification notification : notificationResults.getData()) {
                        processNotification(source, markAsRead, date, notification);
                    }
                    date = newDate;
                }
                Thread.sleep(poolingFrequency);
            }
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
     * @param enabled   Defines if multi-tenancy is enabled or not
     *
     * @return an instance of {@link com.mulesoft.ch.rest.model.TenantResults}
     */
    @Processor
    public TenantResults listTenants(String domain, @Default("25") Integer limit, Integer offset, String query, @Optional Boolean enabled) {
        return client().connectWithDomain(domain).retrieveTenants(limit, offset, query, falseInNull(enabled));
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

    private static String getTransactionIdFrom(MuleEvent muleEvent) {
        return muleEvent.getMessage().getMessageRootId();
    }

    private static String getTenantIdFrom(MuleEvent muleEvent) {
        return muleEvent.getMessage().getInboundProperty(TENANT_ID_PROPERTY);
    }

    private String getDomain() {
        return System.getProperty(DOMAIN_SYSTEM_PROPERTY);
    }

    public Config getConfig() {
        return config;
    }

    private Boolean falseInNull(Boolean bool) {
        if (bool == null) {
            return Boolean.FALSE;
        } else {
            return bool;
        }
    }

    private void addToMapIfNotNull(String queryParamKey, Object queryParamValue, Map<String, String> queryParamsMap) {
        if (queryParamValue != null) {
            if (!isBlank(queryParamValue.toString())) {
                queryParamsMap.put(queryParamKey, queryParamValue.toString());
            }
        }
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    private CloudHubConnectionImpl client() {
        return config.getClient();
    }

    private Application buildApplication(String domain, String muleVersion,Integer workersCount, WorkerType workerSize, Map<String, String> environmentVariables, Boolean persistentQueues, Boolean multitenanted, Boolean vpnEnabled, Boolean autoRestartMonitoring) {
        Application app = new Application();
        app.setMuleVersion(muleVersion);
        app.setProperties(environmentVariables);
        app.setWorkers(workersCount);
        app.setVpnEnabled(falseInNull(vpnEnabled));
        app.setMonitoringAutoRestart(falseInNull(autoRestartMonitoring));
        app.setWorkerType(workerSize != null ? workerSize.toString() : null);
        app.setPersistentQueues(falseInNull(persistentQueues));
        app.setDomain(domain);
        app.setMultitenanted(falseInNull(multitenanted));
        return app;
    }

    private void processNotification(SourceCallback source, Boolean markAsRead, Date date, Notification notification) throws Exception {
        if (notification.getCreatedAt().compareTo(date) == 1) {
            source.process(notification);
            if (markAsRead) {
                changeNotificationStatus(notification.getId(), Notification.NotificationStatus.Status.READ);
            }
        }
    }

}
