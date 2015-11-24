
package org.mule.modules.cloudhub.sources;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.callback.SourceCallback;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.source.MessageSource;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.security.oauth.processor.AbstractListeningMessageProcessor;


/**
 * PollNotificationsMessageSource wraps {@link org.mule.modules.cloudhub.CloudHubConnector#pollNotifications(org.mule.api.callback.SourceCallback, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean)} method in {@link CloudHubConnector } as a message source capable of generating Mule events.  The POJO's method is invoked in its own thread.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class PollNotificationsMessageSource
    extends AbstractListeningMessageProcessor
    implements Runnable, FlowConstructAware, MuleContextAware, Startable, Stoppable, MessageSource
{

    protected Object domain;
    protected String _domainType;
    protected Object poolingFrequency;
    protected Integer _poolingFrequencyType;
    protected Object amountToRetrieve;
    protected Integer _amountToRetrieveType;
    protected Object markAsRead;
    protected Boolean _markAsReadType;
    /**
     * Thread under which this message source will execute
     * 
     */
    private Thread thread;

    public PollNotificationsMessageSource(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    /**
     * Sets markAsRead
     * 
     * @param value Value to set
     */
    public void setMarkAsRead(Object value) {
        this.markAsRead = value;
    }

    /**
     * Sets domain
     * 
     * @param value Value to set
     */
    public void setDomain(Object value) {
        this.domain = value;
    }

    /**
     * Sets poolingFrequency
     * 
     * @param value Value to set
     */
    public void setPoolingFrequency(Object value) {
        this.poolingFrequency = value;
    }

    /**
     * Sets amountToRetrieve
     * 
     * @param value Value to set
     */
    public void setAmountToRetrieve(Object value) {
        this.amountToRetrieve = value;
    }

    /**
     * Method to be called when Mule instance gets started.
     * 
     */
    public void start()
        throws MuleException
    {
        if (thread == null) {
            thread = new Thread(this, "Receiving Thread");
        }
        thread.start();
    }

    /**
     * Method to be called when Mule instance gets stopped.
     * 
     */
    public void stop()
        throws MuleException
    {
        thread.interrupt();
    }

    /**
     * Implementation {@link Runnable#run()} that will invoke the method on the pojo that this message source wraps.
     * 
     */
    public void run() {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(null, false, null);
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            final SourceCallback sourceCallback = this;
            final String transformedDomain = ((String) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_domainType").getGenericType(), null, domain));
            final Integer transformedPoolingFrequency = ((Integer) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_poolingFrequencyType").getGenericType(), null, poolingFrequency));
            final Integer transformedAmountToRetrieve = ((Integer) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_amountToRetrieveType").getGenericType(), null, amountToRetrieve));
            final Boolean transformedMarkAsRead = ((Boolean) transform(getMuleContext(), ((MuleEvent) null), getClass().getDeclaredField("_markAsReadType").getGenericType(), null, markAsRead));
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((CloudHubConnector) object).pollNotifications(sourceCallback, transformedDomain, transformedPoolingFrequency, transformedAmountToRetrieve, transformedMarkAsRead);
                    return null;
                }

            }
            , null, ((MuleEvent) null));
        } catch (MessagingException e) {
            getFlowConstruct().getExceptionListener().handleException(e, e.getEvent());
        } catch (Exception e) {
            getMuleContext().getExceptionListener().handleException(e);
        }
    }

}
