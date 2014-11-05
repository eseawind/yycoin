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

import com.china.center.oa.publics.manager.DesktopManager;


/**
 * DefaultLoadQueryListener
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see DefaultLoadDesktopListener
 * @since 1.0
 */
public class DefaultLoadDesktopListener
{
    private DesktopManager desktopManager = null;

    private List<DesktopListener> listenerList = null;

    /**
     * default constructor
     */
    public DefaultLoadDesktopListener()
    {
    }

    public void init()
    {
        for (DesktopListener each : listenerList)
        {
            desktopManager.putListener(each);
        }
    }

    public void destroy()
    {
        for (DesktopListener each : listenerList)
        {
            desktopManager.removeListener(each.getKey());
        }
    }

    /**
     * @return the listenerList
     */
    public List<DesktopListener> getListenerList()
    {
        return listenerList;
    }

    /**
     * @param listenerList
     *            the listenerList to set
     */
    public void setListenerList(List<DesktopListener> listenerList)
    {
        this.listenerList = listenerList;
    }

    /**
     * @return the desktopManager
     */
    public DesktopManager getDesktopManager()
    {
        return desktopManager;
    }

    /**
     * @param desktopManager
     *            the desktopManager to set
     */
    public void setDesktopManager(DesktopManager desktopManager)
    {
        this.desktopManager = desktopManager;
    }
}
