package org.mule.modules.cloudhub.configs;

import com.mulesoft.cloudhub.client.CloudHubConnectionImpl;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.Default;

/**
 * This configuration only runs inside of CloudHub instances. Retrieves the API Token from the CloudHub instance where the connector is been runned.
 */
@Configuration( configElementName = "token-config", friendlyName = "Token Authentication")
public class TokenConfig implements CloudHubConfig{

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

    public CloudHubConnectionImpl getClient() {
        if(cloudHubClient == null){
            cloudHubClient = new CloudHubConnectionImpl(url, null, null, null, false);
    }
        return cloudHubClient;
    }

    public Long getMaxWaitTime() {
        return getMaxWaitTime();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMaxWaitTime(Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }
}
