/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

///**
// * Mule QuickBooks Connector
// *
// * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
// *
// * The software in this package is published under the terms of the CPAL v1.0
// * license, a copy of which has been included with this distribution in the
// * LICENSE.txt file.
// */
//
//package org.mule.modules.cloudhub.automation.testcases;
//
//import static org.junit.Assert.*;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//
//import java.util.Map;
//import org.junit.*;
//import org.mule.api.MuleException;
//import org.mule.api.processor.MessageProcessor;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.expression.spel.ast.OpPlus;
//
//
//import org.mule.api.MuleEvent;
//import org.mule.tck.junit4.FunctionalTestCase;
//
//import org.mule.modules.quickbooks.online.AccountOnlineDetail;
//import org.mule.modules.quickbooks.online.automation.Sandbox;
//import org.mule.modules.quickbooks.online.schema.Account;
//import org.mule.modules.quickbooks.online.schema.IdType;
//
//public class DeleteAccountTestCase extends FunctionalTestCase {
//	
//	private MuleEvent response;
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
//    
//	@Test
//	public void testDeleteSandboxAccount() {
//		
//		System.out.print("Deleting Account-> > > > >");
//    	try {
//    		
//		Map<String, Object> operationParams = new HashMap<String, Object>();
//		operationParams.put("id", Sandbox.getAccount().getId());
//		operationParams.put("type", "ACCOUNT");
//    	
//		MessageProcessor sandboxFlow = lookupFlowConstruct("delete-object");
//    	sandboxFlow.process(getTestEvent(operationParams));
//    	}  catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//		System.out.print("Done");
//		
//	}
//	
//	
//}
