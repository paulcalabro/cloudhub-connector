
package org.mule.modules.cloudhub.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>CloudHubConnectorProcessAdapter</code> is a wrapper around {@link CloudHubConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class CloudHubConnectorProcessAdapter
    extends CloudHubConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<CloudHubConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, CloudHubConnectorCapabilitiesAdapter> getProcessTemplate() {
        final CloudHubConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,CloudHubConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, CloudHubConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, CloudHubConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
