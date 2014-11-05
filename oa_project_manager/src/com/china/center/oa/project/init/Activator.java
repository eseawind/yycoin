package com.china.center.oa.project.init;


import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.project.constant.ProjectConstant;


public class Activator implements BundleActivator
{
    private List<Class> parserClass = new LinkedList<Class>()
    {
        {
            add(ProjectConstant.class);
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context)
        throws Exception
    {
        for (Class each : parserClass)
        {
            DefinedCommon.addDefinedClass(each);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context)
        throws Exception
    {
        for (Class each : parserClass)
        {
            DefinedCommon.reomoveConstant(each);
        }
    }

}
