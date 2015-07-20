/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cloudhub;

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

/**
 * Connection Management Strategy
 *
 * @author MuleSoft, Inc.
 */
@ConnectionManagement(friendlyName = "Configuration")
public class CloudHubConfig {

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
    @FriendlyName("Maximum time allowed to deplpoy/undeploy.")
    private Long maxWaitTime;

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password, @Default("https://cloudhub.io/") String url) throws ConnectionException {
        try {
            cloudHubClient = new CloudHubConnectionImpl(url, username, password, null, false);
            cloudHubClient.isCsAuthentication();
        } catch (CloudHubException e) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), e.getMessage());
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
            return true;
        }
    }

    /**
     * Are we connected
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }

    public CloudHubConnectionImpl getClient() {
        return cloudHubClient;
    }
}