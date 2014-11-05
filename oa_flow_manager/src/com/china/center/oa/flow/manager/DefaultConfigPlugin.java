/**
 * File Name: DefaultConfigPlugin.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import java.util.ArrayList;
import java.util.List;

import com.china.center.oa.flow.plugin.HandlePlugin;


/**
 * DefaultConfigPlugin
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see DefaultConfigPlugin
 * @since 1.0
 */
public class DefaultConfigPlugin
{
    private PluginManager pluginManager = null;

    private List<HandlePlugin> handleList = new ArrayList();

    /**
     * default constructor
     */
    public DefaultConfigPlugin(List<HandlePlugin> handleList)
    {
        this.handleList.addAll(handleList);
    }

    public void init()
    {
        for (HandlePlugin handlePlugin : this.handleList)
        {
            pluginManager.putHandlePlugin(handlePlugin);
        }
    }

    public void destroy()
    {
        for (HandlePlugin handlePlugin : this.handleList)
        {
            pluginManager.removeHandlePlugin(handlePlugin.getType());
        }
    }

    /**
     * @return the pluginManager
     */
    public PluginManager getPluginManager()
    {
        return pluginManager;
    }

    /**
     * @param pluginManager
     *            the pluginManager to set
     */
    public void setPluginManager(PluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
    }
}
