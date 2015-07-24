/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.cloudhub;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.cloudhub.testcases.CloudHubConnectorTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.mule.tools.devkit.ctf.platform.PlatformManager;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
* Created by estebanwasinger on 4/6/15.
*/
@RunWith(Categories.class)
@Categories.IncludeCategory(RegressionSuite.class)
@Suite.SuiteClasses({
        CloudHubConnectorTestCases.class
})
public class RegressionSuite {

    public static final String DOMAIN = "domain";
    public static final String CH_APP_NAME = "ch-connector-tests-delete-me";

    @BeforeClass
    public static void initialiseSuite(){

        ConnectorTestContext.initialize(CloudHubConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {

        ConnectorTestContext<CloudHubConnector> context = ConnectorTestContext.getInstance(CloudHubConnector.class);

        PlatformManager platform =  context.getPlatformManager();

        ConnectorDispatcher<CloudHubConnector> dispatcher = context.getConnectorDispatcher();

        CloudHubConnector connector = dispatcher.createMockup();

        Object list = connector.listApplications();
        for (LinkedHashMap application : (Collection<LinkedHashMap>) list) {
            if(((String)application.get(DOMAIN)).contains(CH_APP_NAME)){
                connector.deleteApplication(((String) application.get(DOMAIN)));
            }
        }

        platform.shutdown();
    }
}