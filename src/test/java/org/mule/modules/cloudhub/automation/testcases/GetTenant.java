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

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.mulesoft.cloudhub.client.Tenant;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class GetTenant extends AbstractTenantTestCase {

	@Test
	public void getTenant() throws Exception {
		Map<String, Object> payload = this.makePayload(null);
		MessageProcessor sandboxFlow = lookupFlowConstruct("get-tenant");
		MuleEvent response = sandboxFlow.process(getTestEvent(payload));
		//
		
		Tenant responseTenant = (Tenant) response.getMessage().getPayload();
		Assert.assertNotNull("got null response", responseTenant);
		assertEquals("ids should be the same", payload.get("tenantId"), responseTenant.getId());
	}
}
