/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub.automation.testcases;

import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public abstract class AbstractAutomationTest extends FunctionalTestCase {

	protected MessageProcessor lookupFlowConstruct(String name) {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
