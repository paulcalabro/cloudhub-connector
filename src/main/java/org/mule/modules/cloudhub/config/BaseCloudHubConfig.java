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
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.Default;

public abstract class BaseCloudHubConfig {

    public abstract CloudHubConnectionImpl getClient();

    /**
     * Specifies the amount of time, in milliseconds, that the consumer will wait for a response before it times out. Default value is 0, which means infinite.
     */
    @Configurable
    @Default("0")
    Integer readTimeout;

    /**
     * Specifies the amount of time, in milliseconds, that the consumer will attempt to establish a connection before it times out. Default value is 0, which means infinite.
     */
    @Configurable
    @Default("0")
    Integer connectionTimeout;

    /**
     * Maximum time allowed to deploy/undeploy.
     */
    @Configurable
    @Default(value = "0")
    @FriendlyName("Maximum time allowed to deploy or undeploy.")
    private Long maxWaitTime;


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

    public Long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }
}
