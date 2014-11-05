package com.china.center.oa.note.active;


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
 * @version 2010-6-27
 * @see Activator
 * @since 1.0
 */
public class Activator implements BundleActivator
{
    private static final List<String> destoryResourceList = new ArrayList()
    {
        {
        }
    };

    private static final List ignoreFilterMatchList = new ArrayList()
    {
        {
            add("/sms");
        }
    };

    public void start(BundleContext context)
        throws Exception
    {
        // ResourceLoad.init(context, "");

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
