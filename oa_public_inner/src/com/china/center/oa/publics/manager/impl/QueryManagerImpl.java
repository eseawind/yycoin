/**
 * File Name: QueryManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Map;

import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.publics.manager.QueryManager;
import com.china.center.oa.publics.statics.PublicStatic;


/**
 * QueryManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryManagerImpl
 * @since 1.0
 */
public class QueryManagerImpl implements QueryManager
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.QueryManager#putQueryListener(com.china.center.oa.publics.listener.QueryListener)
     */
    public void putQueryListener(QueryListener listener)
    {
        PublicStatic.getQueryListenerMap().put(listener.getKey(), listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.QueryManager#removeQueryListener(java.lang.String)
     */
    public void removeQueryListener(String key)
    {
        PublicStatic.getQueryListenerMap().remove(key);
    }

    /**
     * @return the listenerMap
     */
    public Map<String, QueryListener> getListenerMap()
    {
        return PublicStatic.getQueryListenerMap();
    }

    public static Map<String, QueryListener> getStaticListenerMap()
    {
        return PublicStatic.getQueryListenerMap();
    }
}
