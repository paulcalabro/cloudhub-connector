/**
 * Mule Development Kit
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.modules.ion;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.Payload;
import org.mule.message.ExceptionMessage;

import com.mulesoft.ion.client.Application;
import com.mulesoft.ion.client.Connection;
import com.mulesoft.ion.client.DomainConnection;
import com.mulesoft.ion.client.Notification.Priority;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * iON Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Module(name="ion", schemaVersion="1.0")
public class IONConnector {

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
    private String username;

    /**
     * iON password.
     */
    @Configurable
    @Optional
    private String password;

    /**
     * Maximum time allowed to deplpoy/undeploy.
     */
    @Configurable
    @Optional
    @Default(value="0")
    private Long maxWaitTime;

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
     * @param file mule application to deploy
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
        return getConnection().list();
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
        getConnection().on(application.getDomain()).update(application);
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
     * Create a notification inside iON.
     * 
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:create-notification}
     * @param message the contents of the notification.
     * @param priority The notification priority.
     * @param domain The application domain.
     * @param payload The payload of the message. If it's an exception, it'll get appended to the message.
     * @param includeStacktrace If the message payload contains an Exception, whether or not to include the stacktrace. True by default.
     */
    @Processor
    public void createNotification(String message, 
                                   Priority priority, 
                                   @Optional String domain,
                                   @Payload Object payload,
                                   @Optional @Default("true") boolean includeStacktrace) {
        Connection connection = getConnection();

        if (includeStacktrace) {
            if (payload instanceof ExceptionMessage) {
                Throwable t = ((ExceptionMessage) payload).getException();
                
                message += "\n" + getStackTrace(t);
            }
        }
        
        connection.createNotification(message, priority, domain);
    }

    public static String getStackTrace(Throwable aThrowable) {
      final Writer result = new StringWriter();
      final PrintWriter printWriter = new PrintWriter(result);
      aThrowable.printStackTrace(printWriter);
      return result.toString();
    }
    
    private Connection getConnection() {
        return new Connection(this.url, this.username, this.password);
    }
}