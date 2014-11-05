package com.china.center.oa.flow.init;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.webplugin.inter.FilterLoad;
import com.china.center.webplugin.inter.ResourceLoad;


public class Activator implements BundleActivator
{
    private static final List<String> destoryResourceList = new ArrayList()
    {
        {
            add("flow");
            add("template");
        }
    };

    private static final List ignoreFilterMatchList = new ArrayList()
    {
        {
        }
    };

    public void start(BundleContext context)
        throws Exception
    {
        // 加载jsp资源到webapp下面
        ResourceLoad.init(context, "");

        FilterLoad.putIgnoreFilterMatch(ignoreFilterMatchList);

    }

    public void stop(BundleContext context)
        throws Exception
    {
        for (String each : destoryResourceList)
        {
            // 卸载资源
            ResourceLoad.destory(context, each);
        }

        FilterLoad.removeIgnoreFilterMatch(ignoreFilterMatchList);
    }

}
