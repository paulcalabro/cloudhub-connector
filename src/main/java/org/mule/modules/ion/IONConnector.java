/**
 * Mule iON Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.ion;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.Payload;
import org.mule.message.ExceptionMessage;

import com.mulesoft.ion.client.Application;
import com.mulesoft.ion.client.ApplicationUpdateInfo;
import com.mulesoft.ion.client.Connection;
import com.mulesoft.ion.client.DomainConnection;
import com.mulesoft.ion.client.Notification;
import com.mulesoft.ion.client.Notification.Priority;
import com.mulesoft.ion.client.NotificationResults;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the ability to interact with Mule iON from within a Mule application. There are operations to deploy, start,
 * stop, and update applications as well as send notifications from your application to iON.
 * <p>
 * When running this connector in an application inside iON, it will use token based authentication
 * to access the API. This will allow access and usage of the iON APIs without the need
 * to specify your username and password.
 * <p>
 * 
 * 
 * @author MuleSoft, Inc.
 */
@Module(name="ion", schemaVersion="1.0")
public class IONConnector {
    
    private Logger logger = LoggerFactory.getLogger(Connection.class);
    
    /**
     * iON URL.
     */
    @Configurable
    @Optional
    @Default(value=Connection.DEFAULT_URL)
    private String url;

    /**
     * iON username.
     */
    @Configurable
    @Optional
    @Placement(order=1, group="Credentials", tab="General")
    private String username;

    /**
     * iON password.
     */
    @Configurable
    @Optional
    @Password
    @Placement(order=2, group="Credentials", tab="General")
    private String password;

    /**
     * Maximum time allowed to deplpoy/undeploy.
     */
    @Configurable
    @Optional
    @Default(value="0")
    @FriendlyName("Maximum time allowed to deplpoy/undeploy.")
    private Long maxWaitTime;

    private Connection connection;
    
    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setMaxWaitTime(final Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**
     * Deploy specified application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:deploy-application}
     *
     * @param file mule application to deploy, Input Object type: java.io.File
     * @param domain The application domain.
     * @param muleVersion The version of Mule, e.g. 3.2.2.
     * @param workerCount The number of workers to deploy.
     * @param environmentVariables Environment variables for you application.
     */
    @Processor
    public void deployApplication(final File file, 
                       String domain,
                       @Optional @Default("3.2.2") String muleVersion, 
                       @Optional @Default("1") int workerCount, 
                       @Optional Map<String,String> environmentVariables) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.deploy(file, muleVersion, workerCount, this.maxWaitTime, environmentVariables);
    }
    
    /**
     * List applications.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:list-applications}
     * @return A list of applications.
     */
    @Processor
    public List<Application> listApplications() {
        return getConnection().listApplications();
    }

    /**
     * Get an application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:get-application}
     * @param domain The application domain.
     * @return An application.
     */
    @Processor
    public Application getApplication(String domain) {
        return getConnection().on(domain).get();
    }

    /**
     * Update an application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:update-application}
     * @param application The application to update.
     */
    @Processor
    public void updateApplication(Application application) {
        ApplicationUpdateInfo appUdateInfo = new ApplicationUpdateInfo(application); 
        getConnection().on(application.getDomain()).update(appUdateInfo);
    }
    
    
    /**
     * Start an application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:start-application}
     * @param domain The application domain.
     */
    @Processor
    public void startApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.start(this.maxWaitTime);
    }

    /**
     * Stop an application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:stop-application}
     * @param domain The application domain.
     */
    @Processor
    public void stopApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.stop();
    }

    /**
     * Delete an application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:delete-application}
     * @param domain The application domain.
     */
    @Processor
    public void deleteApplication(String domain) {
        final DomainConnection domainConnection = getConnection().on(domain);
        domainConnection.delete();
    }

    /**
     * List a user's notifications.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:list-notifications}
     * @param domain The domain to list notifications for.
     * @param maxResults The maximum number of results to retrieve.
     * @param offset The offset to start listing alerts from.
     * @param includeDismissed whether or not to include dismissed messages.
     * @return A List of notifications.
     */
    @Processor
    public NotificationResults listNotifications(@Optional String domain,
                                  @Optional Integer maxResults,
                                  @Optional Integer offset,
                                  @Optional Boolean includeDismissed) {
        Connection connection = getConnection();

        return connection.listNotifications(domain, maxResults, offset, includeDismissed);
    }
    
    /**
     * Dismiss an individual notification.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:dismiss-notification}
     * @param href the href property of the Notification object.
     */
    @Processor
    public void dismissNotification(String href) {
        Connection connection = getConnection();

        connection.dismissNotification(href);
    }

    /**
     * Dismiss all notifications.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:dismiss-all-notifications}
     * @param id the ID of the notification.
     */
    @Processor
    public void dismissAllNotifications() {
        Connection connection = getConnection();

        connection.dismissAllNotifications();
    }

    /**
     * Create a notification inside iON.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:create-notification}
     * @param message the contents of the notification.
     * @param priority The notification priority.
     * @param domain The application domain.
     * @param payload The payload of the message. If it's an exception, it'll get appended to the message.
     * @return The notification that was created.
     */
    @Processor
    public Notification createNotification(String message, 
                                           Priority priority, 
                                           @Optional String domain,
                                           @Payload Object payload) {
        Connection connection = getConnection();

        return connection.createNotification(message, priority, domain);
    }

    /**
     * Create a notification inside iON for your flow. If not running in an iON environment, this will log
     * to the console.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:create-notification-from-flow}
     * @param message the contents of the notification.
     * @param priority The notification priority.
     * @param payload The payload of the message. If it's an exception, it'll get appended to the message.
     * @param includeStacktrace If the message payload contains an Exception, whether or not to include the stacktrace. True by default.
     */
    @Processor
    public void createNotificationFromFlow(String message, 
                                           Priority priority,
                                           @Payload Object payload,
                                           @Optional @Default("true") boolean includeStacktrace) {
        String domain = System.getProperty("domain");
        String token = System.getProperty("ion.api.token");

        if (includeStacktrace) {
            if (payload instanceof ExceptionMessage) {
                Throwable t = ((ExceptionMessage) payload).getException();
                
                message += "\n" + getStackTrace(t);
            }
        }
        
        if (domain == null || token == null) {
            // running locally
            logger.info(message);
        } else {
            // running on iON
            Connection connection = getConnection();
    
            connection.createNotification(message, priority, domain);
        }
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
    
    private synchronized Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    private synchronized Connection createConnection() { 
        if (connection != null) {
            return connection;
        }
        return new Connection(this.url, this.username, this.password, false);
    }
}