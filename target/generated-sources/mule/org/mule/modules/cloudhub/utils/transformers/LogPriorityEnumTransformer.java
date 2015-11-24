
package org.mule.modules.cloudhub.utils.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.modules.cloudhub.utils.LogPriority;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class LogPriorityEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public LogPriorityEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(LogPriority.class);
        setName("LogPriorityEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        LogPriority result = null;
        result = Enum.valueOf(LogPriority.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
