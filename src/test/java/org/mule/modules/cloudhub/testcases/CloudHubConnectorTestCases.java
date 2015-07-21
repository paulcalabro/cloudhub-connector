/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cloudhub.testcases;

/**
* Created by estebanwasinger on 20/7/15.
*/

import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.cloudhub.RegressionSuite;
import org.mule.modules.cloudhub.TestParent;


public class CloudHubConnectorTestCases extends TestParent {

    //TODO - Remove DUMMY Test case
    @Category({ RegressionSuite.class })
    @Test
    public void testDeployApplication(){
        Assert.assertEquals(true , getConnector().listApplications().size() > 1);
    }



}
