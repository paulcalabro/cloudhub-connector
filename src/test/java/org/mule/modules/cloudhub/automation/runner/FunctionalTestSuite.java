/**
 * Mule CloudHub Connector
 * <p/>
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.cloudhub.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.modules.cloudhub.automation.functional.CloudHubConnectorTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.mule.tools.devkit.ctf.platform.PlatformManager;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CloudHubConnectorTestCases.class}
)
public class FunctionalTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(CloudHubConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {

        ConnectorTestContext<CloudHubConnector> context = ConnectorTestContext.getInstance(CloudHubConnector.class);
        PlatformManager platform = context.getPlatformManager();
        platform.shutdown();
    }
}