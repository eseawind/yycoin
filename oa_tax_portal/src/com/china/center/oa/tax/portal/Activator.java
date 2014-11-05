package com.china.center.oa.tax.portal;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.webplugin.inter.FilterLoad;
import com.china.center.webplugin.inter.ResourceLoad;


/**
 * Activator
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see Activator
 * @since 1.0
 */
public class Activator implements BundleActivator
{
    private static final List<String> destoryResourceList = new ArrayList()
    {
        {
            add("tax");
            add("finance");
            add("tax_js");
        }
    };

    private static final List filterMatchList = new ArrayList()
    {
        {

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
