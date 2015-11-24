
package org.mule.modules.cloudhub.connectivity;

import javax.annotation.Generated;
import org.mule.api.ConnectionException;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.TestableConnection;
import org.mule.modules.cloudhub.config.BasicAuthConfig;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class BasicAuthConfigCloudHubConnectorAdapter
    extends BasicAuthConfig
    implements ConnectionManagementConnectionAdapter<BasicAuthConfig, ConnectionManagementConfigCloudHubConnectorConnectionKey> , TestableConnection<ConnectionManagementConfigCloudHubConnectorConnectionKey>
{


    @Override
    public void connect(ConnectionManagementConfigCloudHubConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getUsername(), connectionKey.getPassword(), connectionKey.getUrl(), connectionKey.getSandbox());
    }

    @Override
    public void test(ConnectionManagementConfigCloudHubConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getUsername(), connectionKey.getPassword(), connectionKey.getUrl(), connectionKey.getSandbox());
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public String connectionId() {
        return super.connectionId();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public BasicAuthConfig getStrategy() {
        return this;
    }

}
