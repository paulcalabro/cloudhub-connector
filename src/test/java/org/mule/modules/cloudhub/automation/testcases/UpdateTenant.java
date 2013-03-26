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
public class UpdateTenant extends AbstractTenantTestCase {
	
	@Test
	public void updateTenant() throws Exception {
		Tenant tenant = this.makeTenant();
		tenant.setCompanyName("Mule Rules!");
		
		Map<String, Object> payload = this.makePayload(tenant);
		MessageProcessor sandboxFlow = lookupFlowConstruct("update-tenant");
		
		MuleEvent response = sandboxFlow.process(getTestEvent(payload));
		
		Tenant responseTenant = (Tenant) response.getMessage().getPayload();
		
		assertEquals("companies should be the same", tenant.getCompanyName(), responseTenant.getCompanyName());
	}


}
