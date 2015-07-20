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

public class ListApplications extends FunctionalTestCase {
	
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
////    	data_objects = new ClassPathXmlApplicationContext("automation/QBOAccounts.xml");
//    	operation_sandbox = new HashMap<String, Object>();
//    }
//
//    @After
//    public void tearDown() {
//
////    	try {
////
//////
////    	} catch (MuleException e) {
////    		assertFalse(true);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//
//    }
//
//	@Test
//	public void listApps() {
//
//		try {
//			MessageProcessor sandboxFlow = lookupFlowConstruct("list-applications");
//			MuleEvent response = sandboxFlow.process(getTestEvent(null));
//			//
//
//			List<Application> applications = (List<Application>) response.getMessage().getPayload();
//			assertTrue("Size must be > than 0",applications.size()>0);
//			assertTrue("Objects should be Applications", applications.get(0) instanceof Application);
//		} catch (MuleException e) {
//			e.printStackTrace();
//			fail();
////    		Assert.assertTrue("The exception should be a TransformerException", e.getCause() instanceof TransformerException);
//		}catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
	
}
