
package org.mule.modules.cloudhub.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/cloudhub</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class CloudhubNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(CloudhubNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cloudhub] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cloudhub] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new CloudHubConnectorBasicAuthConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("token-config", new CloudHubConnectorTokenConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("token-config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("deploy-application", new DeployApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("deploy-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-and-deploy-application", new CreateAndDeployApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-and-deploy-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-application", new CreateApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-application", new UpdateApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-applications", new ListApplicationsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-applications", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-application", new GetApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("retrieve-application-logs", new RetrieveApplicationLogsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("retrieve-application-logs", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("change-application-status", new ChangeApplicationStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("change-application-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-application", new DeleteApplicationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-application", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-notifications", new ListNotificationsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-notifications", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-notification", new CreateNotificationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-notification", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("change-notification-status", new ChangeNotificationStatusDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("change-notification-status", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-notification", new GetNotificationDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-notification", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("list-tenants", new ListTenantsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("list-tenants", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-tenant", new CreateTenantDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-tenant", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-tenant", new GetTenantDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-tenant", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-tenant", new UpdateTenantDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-tenant", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-tenant", new DeleteTenantDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-tenant", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-tenants", new DeleteTenantsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-tenants", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("poll-notifications", new PollNotificationsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("poll-notifications", "@Source", ex);
        }
    }

}
