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

import org.mule.tck.junit4.FunctionalTestCase;

public class ListNotifications extends FunctionalTestCase {
	
//	private ApplicationContext data_objects;
//
//	@Override
//	protected String getConfigResources() {
//		return "automation/automation-test-flows.xml";
//	}
//
//    private MessageProcessor lookupFlowConstruct(String name) {
//        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
//    }
//
//    @Before
//    public void setUp() {
//    	data_objects = new ClassPathXmlApplicationContext("automation/Notifications.xml");
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//	@Test
//	public void listNotif() {
//
//		try {
//
//
//			MessageProcessor sandboxFlow = lookupFlowConstruct("list-notifications");
//			MuleEvent response = sandboxFlow.process(getTestEvent(null));
//			//
//
//			NotificationResults flow_response = (NotificationResults) response.getMessage().getPayload();
//
//			assertTrue("Objects should be an NotificationResults", flow_response instanceof NotificationResults);
//			assertEquals("Href must be the same",Sandbox.getNotification().getHref(), flow_response.getData().get(0).getHref());
//
//		} catch (MuleException e) {
//			e.printStackTrace();
//			fail();
//		}catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
}
