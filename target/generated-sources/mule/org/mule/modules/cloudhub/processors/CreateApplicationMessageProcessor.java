
package org.mule.modules.cloudhub.processors;

import com.mulesoft.ch.rest.model.Application;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.devkit.internal.metadata.fixes.STUDIO7157;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;
import org.mule.modules.cloudhub.CloudHubConnector;
import org.mule.modules.cloudhub.utils.WorkerType;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * CreateApplicationMessageProcessor invokes the {@link org.mule.modules.cloudhub.CloudHubConnector#createApplication(java.lang.String, java.lang.String, java.lang.Integer, org.mule.modules.cloudhub.utils.WorkerType, java.util.Map, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean)} method in {@link CloudHubConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class CreateApplicationMessageProcessor
    extends DevkitBasedMessageProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object domain;
    protected String _domainType;
    protected Object muleVersion;
    protected String _muleVersionType;
    protected Object workersCount;
    protected Integer _workersCountType;
    protected Object workerSize;
    protected WorkerType _workerSizeType;
    protected Object environmentVariables;
    protected Map<String, String> _environmentVariablesType;
    protected Object persistentQueues;
    protected Boolean _persistentQueuesType;
    protected Object multitenanted;
    protected Boolean _multitenantedType;
    protected Object vpnEnabled;
    protected Boolean _vpnEnabledType;
    protected Object autoRestartMonitoring;
    protected Boolean _autoRestartMonitoringType;

    public CreateApplicationMessageProcessor(String operationName) {
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

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets autoRestartMonitoring
     * 
     * @param value Value to set
     */
    public void setAutoRestartMonitoring(Object value) {
        this.autoRestartMonitoring = value;
    }

    /**
     * Sets muleVersion
     * 
     * @param value Value to set
     */
    public void setMuleVersion(Object value) {
        this.muleVersion = value;
    }

    /**
     * Sets vpnEnabled
     * 
     * @param value Value to set
     */
    public void setVpnEnabled(Object value) {
        this.vpnEnabled = value;
    }

    /**
     * Sets persistentQueues
     * 
     * @param value Value to set
     */
    public void setPersistentQueues(Object value) {
        this.persistentQueues = value;
    }

    /**
     * Sets workersCount
     * 
     * @param value Value to set
     */
    public void setWorkersCount(Object value) {
        this.workersCount = value;
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
     * Sets multitenanted
     * 
     * @param value Value to set
     */
    public void setMultitenanted(Object value) {
        this.multitenanted = value;
    }

    /**
     * Sets workerSize
     * 
     * @param value Value to set
     */
    public void setWorkerSize(Object value) {
        this.workerSize = value;
    }

    /**
     * Sets environmentVariables
     * 
     * @param value Value to set
     */
    public void setEnvironmentVariables(Object value) {
        this.environmentVariables = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(null, false, event);
            final String _transformedDomain = ((String) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_domainType").getGenericType(), null, domain));
            final String _transformedMuleVersion = ((String) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_muleVersionType").getGenericType(), null, muleVersion));
            final Integer _transformedWorkersCount = ((Integer) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_workersCountType").getGenericType(), null, workersCount));
            final WorkerType _transformedWorkerSize = ((WorkerType) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_workerSizeType").getGenericType(), null, workerSize));
            final Map<String, String> _transformedEnvironmentVariables = ((Map<String, String> ) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_environmentVariablesType").getGenericType(), null, environmentVariables));
            final Boolean _transformedPersistentQueues = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_persistentQueuesType").getGenericType(), null, persistentQueues));
            final Boolean _transformedMultitenanted = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_multitenantedType").getGenericType(), null, multitenanted));
            final Boolean _transformedVpnEnabled = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_vpnEnabledType").getGenericType(), null, vpnEnabled));
            final Boolean _transformedAutoRestartMonitoring = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateApplicationMessageProcessor.class.getDeclaredField("_autoRestartMonitoringType").getGenericType(), null, autoRestartMonitoring));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((CloudHubConnector) object).createApplication(_transformedDomain, _transformedMuleVersion, _transformedWorkersCount, _transformedWorkerSize, _transformedEnvironmentVariables, _transformedPersistentQueues, _transformedMultitenanted, _transformedVpnEnabled, _transformedAutoRestartMonitoring);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        MetaDataModel metaDataPayload = getPojoOrSimpleModel(Application.class);
        DefaultMetaDataKey keyForStudio = new DefaultMetaDataKey("OUTPUT_METADATA", null);
        metaDataPayload.addProperty(STUDIO7157 .getStructureIdentifierMetaDataModelProperty(keyForStudio, false, false));
        return new DefaultResult<MetaData>(new DefaultMetaData(metaDataPayload));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(null, false, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at CloudHubConnector at createApplication retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
