
package org.mule.modules.cloudhub.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.modules.cloudhub.CloudHubConnector;


/**
 * A <code>CloudHubConnectorMetadataAdapter</code> is a wrapper around {@link CloudHubConnector } that adds support for querying metadata about the extension.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class CloudHubConnectorMetadataAdapter
    extends CloudHubConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Cloudhub";
    private final static String MODULE_VERSION = "2.0.1";
    private final static String DEVKIT_VERSION = "3.7.2";
    private final static String DEVKIT_BUILD = "3.7.x.2633.51164b9";
    private final static String MIN_MULE_VERSION = "3.6.0";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

}
