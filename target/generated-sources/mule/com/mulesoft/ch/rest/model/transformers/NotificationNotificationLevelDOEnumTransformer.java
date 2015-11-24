
package com.mulesoft.ch.rest.model.transformers;

import com.mulesoft.ch.rest.model.Notification.NotificationLevelDO;
import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.2", date = "2015-11-24T09:26:58-03:00", comments = "Build 3.7.x.2633.51164b9")
public class NotificationNotificationLevelDOEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public NotificationNotificationLevelDOEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(NotificationLevelDO.class);
        setName("NotificationNotificationLevelDOEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        NotificationLevelDO result = null;
        result = Enum.valueOf(NotificationLevelDO.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
