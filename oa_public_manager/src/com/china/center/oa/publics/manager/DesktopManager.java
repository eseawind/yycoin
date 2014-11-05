/**
 * File Name: QueryManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.Map;

import com.china.center.oa.publics.listener.DesktopListener;


/**
 * QueryManager
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see DesktopManager
 * @since 1.0
 */
public interface DesktopManager
{
    void putListener(DesktopListener listener);

    void removeListener(String key);

    Map<String, DesktopListener> getListenerMap();
}
