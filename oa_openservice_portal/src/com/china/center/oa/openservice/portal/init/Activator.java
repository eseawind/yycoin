package com.china.center.oa.openservice.portal.init;


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
            add("webmobi");
            add("webmobi_js");
        }
    };
	
    @SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private static final List filterMatchList = new ArrayList()
    {
        {
            add("/openService/appsService.do");
            add("/webmobi/demo.jsp");
        }
    };

    
    @SuppressWarnings({ "unchecked" })
	public void start(BundleContext context)
        throws Exception
    {
        System.out.println("start openservice Bundle");
        // 加载JSP资源到WEBAPP下面
        ResourceLoad.init(context, "");
        
        FilterLoad.putIgnoreFilterMatch(filterMatchList);
    }

    @SuppressWarnings({ "unchecked" })
	public void stop(BundleContext context)
        throws Exception
    {
        System.out.println("stop openservice Bundle");
        
        for (String each : destoryResourceList)
        {
            // 卸载资源
            ResourceLoad.destory(context, each);
        }
        
        FilterLoad.removeIgnoreFilterMatch(filterMatchList);
    }

}
