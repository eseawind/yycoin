/**
 * File Name: DefaultDymOptionImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.china.center.common.taglib.DymOption;
import com.china.center.common.taglib.MapBean;
import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.tools.InnerReflect;
import com.china.center.tools.ReflectException;


/**
 * DefaultDymOptionImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-25
 * @see DefaultDymOptionImpl
 * @since 3.0
 */
public class DefaultDymOptionImpl implements DymOption
{

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.common.taglib.DymOption#getOptionList(java.lang.String)
     */
    public List<MapBean> getOptionList(String key)
    {
        List<MapBean> result = new ArrayList();

        Map<String, QueryListener> staticListenerMap = QueryManagerImpl.getStaticListenerMap();

        QueryListener queryListener = staticListenerMap.get(key);

        if (queryListener == null)
        {
            return result;
        }

        List<?> beanList = queryListener.getBeanList();

        for (Object object : beanList)
        {
            try
            {
                MapBean bean = new MapBean();
                bean.setKey(InnerReflect.get(object, "id").toString());
                bean.setValue(InnerReflect.get(object, "name").toString());
                result.add(bean);
            }
            catch (ReflectException e)
            {
            }
        }

        return result;
    }

}
