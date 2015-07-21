/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cloudhub;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.cloudhub.testcases.CloudHubConnectorTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.mule.tools.devkit.ctf.platform.PlatformManager;

/**
* Created by estebanwasinger on 4/6/15.
*/
@RunWith(Categories.class)
@Categories.IncludeCategory(RegressionSuite.class)
@Suite.SuiteClasses({
        CloudHubConnectorTestCases.class
})
public class RegressionSuite {

    @BeforeClass
    public static void initialiseSuite(){

        ConnectorTestContext.initialize(CloudHubConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {

        ConnectorTestContext<CloudHubConnector> context = ConnectorTestContext.getInstance(CloudHubConnector.class);

        PlatformManager platform =  context.getPlatformManager();

        platform.shutdown();
    }
}