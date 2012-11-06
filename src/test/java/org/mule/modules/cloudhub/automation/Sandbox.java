/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub.automation;

import com.mulesoft.cloudhub.client.Notification;


public class Sandbox {
	
	public static int sleep_time = 3000;
	public static Notification notification;
	
	public static int getSleep_time() {
		return sleep_time;
	}
	public static void setSleep_time(int sleep_time) {
		Sandbox.sleep_time = sleep_time;
	}
	public static Notification getNotification() {
		return notification;
	}
	public static void setNotification(Notification notification) {
		Sandbox.notification = notification;
	}
	
	
	
}
