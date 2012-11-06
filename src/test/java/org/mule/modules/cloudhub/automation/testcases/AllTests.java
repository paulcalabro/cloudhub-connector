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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		ListApplications.class,
		GetApplication.class,
		StartApplication.class,
		StopApplication.class,
		CreateNotification.class,
		ListNotifications.class,
		DismissNotification.class
		})



public class AllTests{
	
	@BeforeClass
    public static void setUp() {
	}

	@AfterClass
    public static void tearDown() {
    }

	
}
