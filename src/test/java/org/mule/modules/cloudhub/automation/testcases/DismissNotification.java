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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.cloudhub.automation.Sandbox;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;

public class DismissNotification extends FunctionalTestCase {
	
	@SuppressWarnings("unused")
	private ApplicationContext data_objects;
	
	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    	
    }
    
	@Test
	public void dismissNotif() {
		
		try {
			
			
			MessageProcessor sandboxFlow = lookupFlowConstruct("dismiss-notification");
			MuleEvent response = sandboxFlow.process(getTestEvent(Sandbox.getNotification().getHref()));
			//
			String flow_response = (String) response.getMessage().getPayload();
			
			assertEquals("Href mismatch",Sandbox.getNotification().getHref(),flow_response);
			
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	        	
	}
	
}
