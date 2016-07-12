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
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

/**
 * Basic Auth Configuration
 *
 * @author MuleSoft, Inc.
 */
@ConnectionManagement(friendlyName = "Basic Auth Authentication")
public class BasicAuthConfig extends BaseCloudHubConfig {

    private CloudHubConnectionImpl cloudHubClient;

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @param url CloudHub URL
     * @param sandbox SandBox name
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
     * Check if the CH Connection is still valid
     */
    @ValidateConnection
    public boolean isConnected() {
        if (cloudHubClient == null) {
            return false;
        } else {
            try {
                cloudHubClient.retrieveAccount();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @ConnectionIdentifier
    public String connectionId() {
        return cloudHubClient.getUsername();
    }

    public CloudHubConnectionImpl getClient() {
        return cloudHubClient;
    }

}