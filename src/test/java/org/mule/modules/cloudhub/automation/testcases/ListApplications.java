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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;

import com.mulesoft.cloudhub.client.Application;

public class ListApplications extends FunctionalTestCase {
	
	@SuppressWarnings("unused")
	private ApplicationContext data_objects;
	@SuppressWarnings("unused")
	private Map<String,Object> operation_sandbox;
	
	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
    @Before
    public void setUp() {
//    	data_objects = new ClassPathXmlApplicationContext("automation/QBOAccounts.xml");
    	operation_sandbox = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
    	
//    	try {
//    		
////        	
//    	} catch (MuleException e) {
//    		assertFalse(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
   	
    }
    
	@Test
	public void listApps() {
		
		try {
			MessageProcessor sandboxFlow = lookupFlowConstruct("list-applications");
			MuleEvent response = sandboxFlow.process(getTestEvent(null));
			//
			
			@SuppressWarnings("unchecked")
			List<Application> applications = (List<Application>) response.getMessage().getPayload();
			assertTrue("Size must be > than 0",applications.size()>0);
			assertTrue("Objects should be Applications", applications.get(0) instanceof Application);
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
//    		Assert.assertTrue("The exception should be a TransformerException", e.getCause() instanceof TransformerException);
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	        	
	}
	
}
