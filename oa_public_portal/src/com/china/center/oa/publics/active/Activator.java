package com.china.center.oa.publics.active;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.webplugin.inter.FilterLoad;
import com.china.center.webplugin.inter.ResourceLoad;
import com.china.center.webportal.filter.FilterListenerService;


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
    private static final String ADMIN = "admin";

    private static final List filterMatchList = new ArrayList()
    {
        {
            add("/admin/index.jsp");
            add("/admin/checkuser.do");
            add("/admin/image.jsp");
            add("/admin/logout.do");
            add("/js/");
            add("/admin_js/");
            add("/css/");
            add("/down/");
            add("/zjrc/index.jsp");
            add("/admin/down.do");
            add("/admin/query.do");
            add("/admin/logon.do");
        }
    };

    private AdminFilterListener adminFL = new AdminFilterListener();

    public void start(BundleContext context)
        throws Exception
    {
        // 加载jsp资源到webapp下面
        ResourceLoad.init(context, "");

        FilterLoad.putIgnoreFilterMatch(filterMatchList);

        FilterListenerService.putFilterListener(adminFL);
    }

    public void stop(BundleContext context)
        throws Exception
    {
        // 卸载资源
        ResourceLoad.destory(context, ADMIN);

        FilterLoad.removeIgnoreFilterMatch(filterMatchList);

        FilterListenerService.removeFilterListener(adminFL.getListenerType());
    }

}
