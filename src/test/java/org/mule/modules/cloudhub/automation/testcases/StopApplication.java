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

public class StopApplication extends FunctionalTestCase {
//
//	private ApplicationContext data_objects;
//	private Map<String,Object> operation_sandbox;
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
//    	data_objects = new ClassPathXmlApplicationContext("automation/Applications.xml");
//    	operation_sandbox = new HashMap<String, Object>();
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//	@Test
//	public void stopApp() {
//
//		try {
//			Application sandbox_application = (Application) data_objects.getBean("applicationA");
//
//			MessageProcessor start_sandboxFlow = lookupFlowConstruct("stop-application");
//			MuleEvent start_response = start_sandboxFlow.process(getTestEvent(sandbox_application.getDomain()));
//			//
//			Thread.sleep(Sandbox.sleep_time);
//			MessageProcessor sandboxFlow = lookupFlowConstruct("get-application");
//			MuleEvent response = sandboxFlow.process(getTestEvent(sandbox_application.getDomain()));
//
//			Application flow_response = (Application) response.getMessage().getPayload();
//
//			assertTrue("Objects should be an Application", flow_response instanceof Application);
//
//			if (flow_response.getStatus() == com.mulesoft.cloudhub.client.Application.Status.UNDEPLOYING || flow_response.getStatus() == com.mulesoft.cloudhub.client.Application.Status.UNDEPLOYED){
//				assertTrue(true);
//			}
////			assertEquals("Status dont match",com.mulesoft.cloudhub.client.Application.Status.UNDEPLOYING, flow_response.getStatus());
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
