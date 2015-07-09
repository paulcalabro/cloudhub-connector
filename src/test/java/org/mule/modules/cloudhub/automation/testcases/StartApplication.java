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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.cloudhub.automation.Sandbox;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulesoft.cloudhub.client.Application;

public class StartApplication extends FunctionalTestCase {
	
	private ApplicationContext data_objects;
	@SuppressWarnings("unused")
	private Map<String,Object> operation_sandbox;
	
	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
    @Before
    public void setUp() {
    	data_objects = new ClassPathXmlApplicationContext("automation/Applications.xml");
    	operation_sandbox = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
    	
    }
    
	@Test
	public void startApp() {
		
		try {
			Application sandbox_application = (Application) data_objects.getBean("applicationA");
			
			MessageProcessor start_sandboxFlow = lookupFlowConstruct("start-application");
			@SuppressWarnings("unused")
			MuleEvent start_response = start_sandboxFlow.process(getTestEvent(sandbox_application.getDomain()));
			//
			Thread.sleep(Sandbox.sleep_time);
			MessageProcessor sandboxFlow = lookupFlowConstruct("get-application");
			MuleEvent response = sandboxFlow.process(getTestEvent(sandbox_application.getDomain()));

			Application flow_response = (Application) response.getMessage().getPayload();
			
			assertTrue("Objects should be an Application", flow_response instanceof Application);
			assertEquals("Domains dont match",flow_response.getStatus(), com.mulesoft.cloudhub.client.Application.Status.DEPLOYING);
			
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	        	
	}
	
}
