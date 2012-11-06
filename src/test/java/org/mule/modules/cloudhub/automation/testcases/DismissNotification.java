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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.junit.*;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.transformer.TransformerException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.mule.api.MuleEvent;
import org.mule.modules.cloudhub.automation.Sandbox;
import org.mule.tck.junit4.FunctionalTestCase;


import com.mulesoft.cloudhub.client.Application;
import com.mulesoft.cloudhub.client.Notification;
import com.mulesoft.cloudhub.client.NotificationResults;

public class DismissNotification extends FunctionalTestCase {
	
	private ApplicationContext data_objects;
	
	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
    private MessageProcessor lookupFlowConstruct(String name) {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
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
