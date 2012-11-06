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
/**
 * Mule QuickBooks Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

//package org.mule.module.jira.automation;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//import twitter4j.IDs;
//import twitter4j.ResponseList;
//import twitter4j.Status;
//import twitter4j.User;
//
//public class JiraTestUtils {
//
//	public static int TIMELINE_DEFAULT_LENGTH = 20;
//
//	public static boolean isStatusIdOnTimeline(ResponseList<Status> statusList, long statusId) {
//
//		Status status;
//		boolean found = false;
//
//		Iterator<Status> iter = statusList.iterator();
//        while (iter.hasNext() && found == false){
//        	status = iter.next();
//        	if (status.getId() == statusId) {
//        		found = true;
//        	}
//        }
//        return found;
//	}
//
//	public static String getStatusTextOnTimeline(ResponseList<Status> statusList, long statusId) {
//
//		Status status;
//		boolean found = false;
//
//		Iterator<Status> iter = statusList.iterator();
//        while (iter.hasNext() && found == false){
//        	status = iter.next();
//        	if (status.getId() == statusId) {
//        		return status.getText();
//           	}
//        }
//        return null;
//    }
//
//	public static boolean isUserOnList(ResponseList<User> userList, long userId) {
//
//		User user;
//		boolean found = false;
//
//		Iterator<User> iter = userList.iterator();
//        while (iter.hasNext() && found == false){
//        	user = iter.next();
//        	if (user.getId() == userId) {
//        		found = true;
//        	}
//        }
//        return found;
//	}
//
//	public static boolean isUserIdOnIdList(IDs ids, long userId) {
//
//		 boolean contains = false;
//		 long[] idArray = ids.getIDs();
//
//         for(int i=0; i < idArray.length; i++){
//
//             if(Long.toString(idArray[i]).equals(userId)){
//                     contains = true;
//                     break;
//             }
//         }
//
//         if(contains){
//	             return true;
//	     }else{
//	             return false;
//	     }
//
//	}
//
//}
//
