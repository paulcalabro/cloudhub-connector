
package com.mulesoft.ch.rest.model.transformers;

import com.mulesoft.ch.rest.model.ApplicationStatusChange.DesiredApplicationStatus;
import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class ApplicationStatusChangeDesiredApplicationStatusEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public ApplicationStatusChangeDesiredApplicationStatusEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(DesiredApplicationStatus.class);
        setName("ApplicationStatusChangeDesiredApplicationStatusEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        DesiredApplicationStatus result = null;
        result = Enum.valueOf(DesiredApplicationStatus.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
