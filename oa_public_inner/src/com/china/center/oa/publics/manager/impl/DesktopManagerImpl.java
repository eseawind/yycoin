/**
 * File Name: DeskManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Map;

import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.manager.DesktopManager;
import com.china.center.oa.publics.statics.PublicStatic;


/**
 * DeskManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopManagerImpl
 * @since 3.0
 */
public class DesktopManagerImpl implements DesktopManager
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.QueryManager#putQueryListener(com.china.center.oa.publics.listener.QueryListener)
     */
    public void putListener(DesktopListener listener)
    {
        PublicStatic.getDeskListenerMap().put(listener.getKey(), listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.QueryManager#removeQueryListener(java.lang.String)
     */
    public void removeListener(String key)
    {
        PublicStatic.getDeskListenerMap().remove(key);
    }

    /**
     * @return the listenerMap
     */
    public Map<String, DesktopListener> getListenerMap()
    {
        return PublicStatic.getDeskListenerMap();
    }
}
