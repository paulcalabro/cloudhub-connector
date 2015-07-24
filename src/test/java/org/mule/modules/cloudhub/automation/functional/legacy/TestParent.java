/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.cloudhub.automation.functional.legacy;

import org.junit.Before;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

public class TestParent{

    private CloudHubConnector connector;
    private ConnectorDispatcher<CloudHubConnector> dispatcher;


    protected CloudHubConnector getConnector() {
        return connector;
    }


    protected ConnectorDispatcher<CloudHubConnector> getDispatcher() {
        return dispatcher;
    }

    @Before
    public void init() throws Exception {

        //Context instance
        ConnectorTestContext<CloudHubConnector> context = ConnectorTestContext.getInstance(CloudHubConnector.class);

        //Connector dispatcher
        dispatcher = context.getConnectorDispatcher();

        connector = dispatcher.createMockup();

        setUp();
    }


    protected void setUp() throws Exception {
    }
}