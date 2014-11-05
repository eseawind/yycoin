/**
 * File Name: PluginManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import java.util.List;

import com.china.center.oa.flow.plugin.HandlePlugin;


/**
 * PluginManager
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see PluginManager
 * @since 1.0
 */
public interface PluginManager
{
    boolean hasAuth(String instanceId, int pluginType, List<String> processers);

    HandlePlugin getHandlePlugin(int type);

    void putHandlePlugin(HandlePlugin handle);

    void removeHandlePlugin(int type);
}
