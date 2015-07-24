/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub.automation.functional.legacy;

import org.mule.tck.junit4.FunctionalTestCase;

public class CreateNotification extends FunctionalTestCase {
	
//	private ApplicationContext data_objects;
//	private ApplicationContext applications;
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
//    	applications = new ClassPathXmlApplicationContext("automation/Applications.xml");
//    	data_objects = new ClassPathXmlApplicationContext("automation/Notifications.xml");
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//	@Test
//	public void createNotif() {
//
//		try {
//
//			Application sandbox_application = (Application) applications.getBean("applicationA");
//
//			Notification test_notification = (Notification) data_objects.getBean("notificationA");
//
//			Map<String,Object> operation_params = new HashMap<String, Object>();
//			operation_params.put("message", test_notification.getMessage());
//			operation_params.put("priority", test_notification.getPriority());
//			operation_params.put("domain", sandbox_application.getDomain());
//
//			MessageProcessor sandboxFlow = lookupFlowConstruct("create-notification");
//			MuleEvent response = sandboxFlow.process(getTestEvent(operation_params));
//			//
//
//			Notification flow_response = (Notification) response.getMessage().getPayload();
//
//			Sandbox.setNotification(flow_response);
//
//			assertTrue("Objects should be an Notification", flow_response instanceof Notification);
//			assertEquals("Domains dont match",flow_response.getDomain(), sandbox_application.getDomain());
//			assertEquals("Message dont match",flow_response.getMessage(),test_notification.getMessage());
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
	
}
