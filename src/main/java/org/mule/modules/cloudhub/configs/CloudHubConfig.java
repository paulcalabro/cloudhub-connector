package org.mule.modules.cloudhub.configs;

import com.mulesoft.cloudhub.client.CloudHubConnectionImpl;

public interface CloudHubConfig {

    public CloudHubConnectionImpl getClient();

    public Long getMaxWaitTime();

}
