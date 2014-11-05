/**
 * File Name: DefaultLoadQueryListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import java.util.List;

import com.china.center.oa.publics.manager.QueryManager;


/**
 * DefaultLoadQueryListener
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see DefaultLoadQueryListener
 * @since 1.0
 */
public class DefaultLoadQueryListener
{
    private QueryManager queryManager = null;

    private List<QueryListener> listenerList = null;

    /**
     * default constructor
     */
    public DefaultLoadQueryListener()
    {
    }

    public void init()
    {
        for (QueryListener each : listenerList)
        {
            queryManager.putQueryListener(each);
        }
    }

    public void destroy()
    {
        for (QueryListener each : listenerList)
        {
            queryManager.removeQueryListener(each.getKey());
        }
    }

    /**
     * @return the queryManager
     */
    public QueryManager getQueryManager()
    {
        return queryManager;
    }

    /**
     * @param queryManager
     *            the queryManager to set
     */
    public void setQueryManager(QueryManager queryManager)
    {
        this.queryManager = queryManager;
    }

    /**
     * @return the listenerList
     */
    public List<QueryListener> getListenerList()
    {
        return listenerList;
    }

    /**
     * @param listenerList
     *            the listenerList to set
     */
    public void setListenerList(List<QueryListener> listenerList)
    {
        this.listenerList = listenerList;
    }
}
