/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.cloudhub.automation.functional;

import com.mulesoft.ch.rest.model.*;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.modules.cloudhub.utils.WorkerType;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

/*
* @TODO: This must be split into several classes to be compliant with the certification !!!!. 
 */
public class CloudHubConnectorTestCases extends AbstractTestCase<CloudHubConnector> {

    public static String DOMAIN_NAME;
    static Boolean isSetUp = Boolean.FALSE;
    static Boolean isDeployed = Boolean.FALSE;
    public static final String DOMAIN = "domain";
    public static final String CH_APP_NAME = "ch-connector-tests-delete-me";

    public CloudHubConnectorTestCases() {
        this.setConnectorClass(CloudHubConnector.class);
    }

    @Before
    public void setUpCloudHubApps() throws FileNotFoundException, InterruptedException, URISyntaxException {
        if (!isSetUp) {
            DOMAIN_NAME = "ch-connector-tests-delete-me-" + new Date().getTime();
            URL resourceUrl = getClass().getResource("/dummy-app.zip");
            Path resourcePath = Paths.get(resourceUrl.toURI());
            InputStream is = new FileInputStream(resourcePath.toString());
            getConnector().createAndDeployApplication(is, DOMAIN_NAME, "3.7.0", 1, WorkerType.Medium, null, false, false, false, false);
            while (!isDeployed) {
                Thread.sleep(2000);
                if (getConnector().getApplication(DOMAIN_NAME).getStatus() == ApplicationStatus.STARTED) {
                    isDeployed = Boolean.TRUE;
                    isSetUp = Boolean.TRUE;
                }
            }
            Thread.sleep(10000);
        }
    }

    @Test
    public void testRetrieveApp() {
        printMethodName(new Object() {

        }.getClass().getEnclosingMethod().getName());
        Application application = getConnector().getApplication(DOMAIN_NAME);
        Assert.assertEquals(DOMAIN_NAME, application.getDomain());
    }

    // TODO IMPROVE -- CH RETURNS A HASHMAP INSTEAD OF A APPLICATION
    @Test
    public void testUndeployApp() {
        printMethodName(new Object() {

        }.getClass().getEnclosingMethod().getName());
        getConnector().deleteApplication(DOMAIN_NAME);
        Object list = getConnector().listApplications();
        for (LinkedHashMap application : (Collection<LinkedHashMap>) list) {
            Assert.assertEquals(false, application.get("domain").equals(DOMAIN_NAME));
        }
        isSetUp = Boolean.FALSE;
        isDeployed = Boolean.FALSE;
    }

    @Test
    public void changeAppStatus() throws InterruptedException {
        printMethodName(new Object() {

        }.getClass().getEnclosingMethod().getName());
        getConnector().changeApplicationStatus(DOMAIN_NAME, ApplicationStatusChange.DesiredApplicationStatus.STOP);
        Thread.sleep(5000);
        ApplicationStatus appStatus = getConnector().getApplication(DOMAIN_NAME).getStatus();
        Boolean status = appStatus.equals(ApplicationStatus.UNDEPLOYED) || appStatus.equals(ApplicationStatus.UNDEPLOYING);
        Assert.assertEquals(Boolean.TRUE, status);

        getConnector().changeApplicationStatus(DOMAIN_NAME, ApplicationStatusChange.DesiredApplicationStatus.START);
        Thread.sleep(5000);
        appStatus = getConnector().getApplication(DOMAIN_NAME).getStatus();
        status = appStatus.equals(ApplicationStatus.STARTED) || appStatus.equals(ApplicationStatus.DEPLOYING);
        Assert.assertEquals(Boolean.TRUE, status);
    }

    @Test
    public void sendNotifications() {
        printMethodName(new Object() {

        }.getClass().getEnclosingMethod().getName());
        Boolean founded = Boolean.FALSE;
        String notificationMessage = "This is a notification test " + new Date().getTime();
        getConnector().createNotification(notificationMessage, Notification.NotificationLevelDO.INFO, DOMAIN_NAME, null, null);
        NotificationResults notificationResults = getConnector().listNotifications(DOMAIN_NAME, 25, null, Notification.NotificationStatus.Status.READ);
        for (Notification notification : notificationResults.getData()) {
            if (notification.getMessage().equals(notificationMessage)) {
                founded = Boolean.TRUE;
            }
        }
        Assert.assertEquals(Boolean.TRUE, founded);
    }

    //TODO Flacky test
//    @Test
//    public void retrieveNotifications(){
//        printMethodName(new Object() {
//
//        }.getClass().getEnclosingMethod().getName());
//        LogResults logResults = getConnector().retrieveApplicationLogs(DOMAIN_NAME, null, null, 1, null, null, null, Boolean.TRUE, null);
//        Assert.assertEquals("Your application is started.",logResults.getData().get(0).getMessage());
//    }

    private void printMethodName(String name) {
        System.out.println("==============================================");
        System.out.println("=  " + name);
        System.out.println("==============================================");
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {

        ConnectorTestContext<CloudHubConnector> context = ConnectorTestContext.getInstance(CloudHubConnector.class);

        ConnectorDispatcher<CloudHubConnector> dispatcher = context.getConnectorDispatcher();

        CloudHubConnector connector = dispatcher.createMockup();

        Object list = connector.listApplications();
        for (LinkedHashMap application : (Collection<LinkedHashMap>) list) {
            if (((String) application.get(DOMAIN)).contains(CH_APP_NAME)) {
                connector.deleteApplication(((String) application.get(DOMAIN)));
            }
        }
    }

}
