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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.mulesoft.cloudhub.client.Tenant;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class CreateTenant extends AbstractAutomationTest {

	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
	
	@Test
	public void createTenant() throws Exception {
		Tenant tenant = new Tenant();
		tenant.setCompanyName("Acme");
		tenant.setContactEmail("mariano.gonzalez@mulesoft.com");
		tenant.setContactName("Mariano Gonzalez");
		tenant.setCreated(new Date());
		tenant.setEnabled(true);
		tenant.setHref("acme");
		tenant.setId("acme");
		
		Map<String, String> config = new HashMap<String, String>();
		config.put("hello", "world");
		tenant.setConfiguration(config);
		
		MessageProcessor sandboxFlow = lookupFlowConstruct("create-tenant");
		MuleEvent response = sandboxFlow.process(getTestEvent(tenant));
		//
		
		Tenant responsTenant = (Tenant) response.getMessage().getPayload();
		
		assertEquals("ids should be the same", tenant.getId(), responsTenant.getId());
	}
	

}
