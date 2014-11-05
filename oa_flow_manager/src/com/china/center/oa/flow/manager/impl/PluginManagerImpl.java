/**
 * File Name: PluginManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.oa.flow.manager.PluginManager;
import com.china.center.oa.flow.plugin.HandlePlugin;


/**
 * PluginManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see PluginManagerImpl
 * @since 1.0
 */
public class PluginManagerImpl implements PluginManager
{
    private Map<Integer, HandlePlugin> handlers = new HashMap<Integer, HandlePlugin>();

    /**
     * default constructor
     */
    public PluginManagerImpl()
    {
    }

    public boolean hasAuth(String instanceId, int pluginType, List<String> processers)
    {
        HandlePlugin handler = getHandlePlugin(pluginType);

        if (handler == null)
        {
            return false;
        }

        return handler.hasAuth(instanceId, processers);
    }

    /**
     * getHandlerPlugin
     * 
     * @param type
     * @return
     */
    public HandlePlugin getHandlePlugin(int type)
    {
        for (HandlePlugin handlerPlugin : handlers.values())
        {
            if (handlerPlugin.getType() == type)
            {
                return handlerPlugin;
            }
        }

        return null;
    }

    public void putHandlePlugin(HandlePlugin handle)
    {
        handlers.put(handle.getType(), handle);
    }

    public void removeHandlePlugin(int type)
    {
        handlers.remove(type);
    }
}
