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
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.Default;

/**
 * This configuration only runs inside of CloudHub instances. Retrieves the API Token from the CloudHub instance where the connector is been run.
 */
@Configuration( configElementName = "token-config", friendlyName = "Inhered Token Authentication")
public class TokenConfig implements Config {

    private CloudHubConnectionImpl cloudHubClient;

    /**
     * Maximum time allowed to deploy/undeploy.
     */
    @Configurable
    @Default(value = "0")
    @FriendlyName("Maximum time allowed to deploy or undeploy.")
    private Long maxWaitTime;

    @Configurable
    @Default("https://anypoint.mulesoft.com/cloudhub/")
    private String url;
    
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

    public CloudHubConnectionImpl getClient() {
        if(cloudHubClient == null){
            cloudHubClient = new CloudHubConnectionImpl(url, null, null, null, readTimeout, connectionTimeout, false);
    }
        return cloudHubClient;
    }

    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }
}
