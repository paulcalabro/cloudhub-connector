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
import org.mule.api.annotations.param.Default;

/**
 * This configuration only runs inside of CloudHub instances. Retrieves the API Token from the CloudHub instance where the connector is been run.
 */
@Configuration( configElementName = "token-config", friendlyName = "Inherited Token Authentication")
public class TokenConfig extends BaseCloudHubConfig {

    private CloudHubConnectionImpl cloudHubClient;

    @Configurable
    @Default("https://anypoint.mulesoft.com/cloudhub/")
    private String url;

    public CloudHubConnectionImpl getClient() {
        if(cloudHubClient == null){
            cloudHubClient = new CloudHubConnectionImpl(url, null, null, null, readTimeout, connectionTimeout, false);
    }
        return cloudHubClient;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
