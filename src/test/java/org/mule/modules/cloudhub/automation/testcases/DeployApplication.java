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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulesoft.cloudhub.client.Application;

public class DeployApplication extends FunctionalTestCase {
	
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
	public void depApp() {
		
		try {
			Application sandbox_application = (Application) data_objects.getBean("applicationT");
			Map<String,Object> operation_params = new HashMap<String, Object>();
			operation_params.put("domain", sandbox_application.getDomain());
			operation_params.put("muleVersion", sandbox_application.getMuleVersion());
			operation_params.put("file", new File("automation/application.zip"));
			
			MessageProcessor sandboxFlow = lookupFlowConstruct("deploy-application");
			MuleEvent response = sandboxFlow.process(getTestEvent(operation_params));
			//
			
			Application flow_response = (Application) response.getMessage().getPayload();
			
			assertTrue("Objects should be an Application", flow_response instanceof Application);
			assertEquals("Domains dont match",flow_response.getDomain(),sandbox_application.getDomain());
			assertEquals("mule version dont match",flow_response.getMuleVersion(),sandbox_application.getMuleVersion());
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	        	
	}
	
}
