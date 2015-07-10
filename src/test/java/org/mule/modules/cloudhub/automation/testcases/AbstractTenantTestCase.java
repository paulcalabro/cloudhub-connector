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

import com.mulesoft.cloudhub.client.Tenant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class AbstractTenantTestCase extends AbstractAutomationTest {

	
	protected Map<String, Object> makePayload(Tenant tenant) {
		final String tenantId = "Smartguy";
		final String domain = "fact-generator";
		
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("domain", domain);
		payload.put("tenantId", tenantId);
		
		if (tenant != null) {
			payload.put("tenant", tenant);
		}
		
		return payload;
	}
	
	protected Tenant makeTenant() {
		Tenant tenant = new Tenant();
        tenant.setCompanyName("Acme");
        tenant.setContactEmail("mariano.gonzalez@mulesoft.com");
		tenant.setCreated(new Date());
		tenant.setEnabled(true);
		tenant.setHref("acme");
		tenant.setId("acme");
		
		Map<String, String> config = new HashMap<String, String>();
		config.put("hello", "world");
		tenant.setConfiguration(config);
		
		return tenant;
	}
}
