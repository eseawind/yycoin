package com.china.center.oa.publics.inner;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.common.taglib.PageSelectOption;
import com.china.center.oa.publics.manager.impl.DefaultDymOptionImpl;


public class Activator implements BundleActivator
{
    private DefaultDymOptionImpl defaultDymOptionImpl = new DefaultDymOptionImpl();

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context)
        throws Exception
    {
        PageSelectOption.putDymOption(defaultDymOptionImpl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context)
        throws Exception
    {
        PageSelectOption.removeDymOption(defaultDymOptionImpl);
    }

}
