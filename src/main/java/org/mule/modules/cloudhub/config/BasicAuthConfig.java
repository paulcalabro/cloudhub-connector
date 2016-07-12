/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub.config;

import com.mulesoft.cloudhub.client.CloudHubConnectionImpl;
import com.mulesoft.cloudhub.client.CloudHubException;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

/**
 * Connection Management Strategy
 *
 * @author MuleSoft, Inc.
 */
@ConnectionManagement(friendlyName = "Basic Auth Authentication")
public class BasicAuthConfig implements Config {

    private CloudHubConnectionImpl cloudHubClient;

    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**
     * Maximum time allowed to deplpoy/undeploy.
     */
    @Configurable
    @Default(value = "0")
    @FriendlyName("Maximum time allowed to deploy or undeploy.")
    private Long maxWaitTime;
    
    /**
     * Specifies the amount of time, in milliseconds, that the consumer will wait for a response before it times out. Default value is 0, which means infinite.
     */
    @Configurable
    @Default("0")
    private Integer readTimeout;

    /**
     * Specifies the amount of time, in milliseconds, that the consumer will attempt to establish a connection before it times out. Default value is 0, which means infinite.
     */
    @Configurable
    @Default("0")
    private Integer connectionTimeout;

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @param url CloudHub URL
     * @param sandbox SandBox name
     * @param connectionTimeout Specifies the amount of time, in milliseconds, that the consumer will attempt to establish a connection before it times out. Default value is 0, which means infinite
     * @param readTimeout Specifies the amount of time, in milliseconds, that the consumer will wait for a response before it times out. Default value is 0, which means infinite
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password, @Default("https://anypoint.mulesoft.com/cloudhub/") String url, @Optional String sandbox) throws ConnectionException {
        try {
            cloudHubClient = new CloudHubConnectionImpl(url, username, password, sandbox, readTimeout, connectionTimeout, false);
            cloudHubClient.getSupportedMuleVersions();
        } catch (CloudHubException e) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), e.getMessage(),e);
        }
    }

    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        /*
         * CODE FOR CLOSING A CONNECTION GOES IN HERE
         */
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        if (cloudHubClient == null) {
            return false;
        } else {
            return cloudHubClient.isCsAuthentication();
        }
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId() {
        return cloudHubClient.getUsername();
    }

    public CloudHubConnectionImpl getClient() {
        return cloudHubClient;
    }
    
    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}