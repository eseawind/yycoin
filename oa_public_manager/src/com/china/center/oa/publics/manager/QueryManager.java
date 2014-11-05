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

import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryManager
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryManager
 * @since 1.0
 */
public interface QueryManager
{
    void putQueryListener(QueryListener listener);

    void removeQueryListener(String key);

    Map<String, QueryListener> getListenerMap();
}
