package com.china.center.oa.stock.portal.init;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.webplugin.inter.FilterLoad;
import com.china.center.webplugin.inter.ResourceLoad;
import com.china.center.webportal.filter.FilterListenerService;


public class Activator implements BundleActivator
{
    private static final List<String> destoryResourceList = new ArrayList()
    {
        {
            add("ask");
            add("stock");
            add("stockwork");
        }
    };

    private static final List filterMatchList = new ArrayList()
    {
        {
            add("/netask/index.jsp");
            add("/netask/checkuser.do");
        }
    };

    private NetAskFilterListener netAskFL = new NetAskFilterListener();

    public void start(BundleContext context)
        throws Exception
    {
        // 加载JSP资源到WEBAPP下面
        ResourceLoad.init(context, "");

        FilterLoad.putIgnoreFilterMatch(filterMatchList);

        FilterListenerService.putFilterListener(netAskFL);

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

        FilterListenerService.removeFilterListener(netAskFL.getListenerType());
    }

}
