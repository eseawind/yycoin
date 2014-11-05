package com.china.center.oa.sail.portal;


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
            add("sail");
            add("transport");
            add("sail_js");
        }
    };

    private static final List filterMatchList = new ArrayList()
    {
        {
        	add("/sailImport/");
        	add("/sail/outImport.do");
        }
    };

    public void start(BundleContext context)
        throws Exception
    {
        // 加载JSP资源到WEBAPP下面
        ResourceLoad.init(context, "");

        FilterLoad.putIgnoreFilterMatch(filterMatchList);

    }

    public void stop(BundleContext context)
        throws Exception
    {
        for (String each : destoryResourceList)
        {
            // 卸载资源
            ResourceLoad.destory(context, each);
        }

        FilterLoad.removeIgnoreFilterMatch(filterMatchList);
    }

}
