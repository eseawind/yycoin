package com.china.center.oa.product.init;


import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.PriceChangeConstant;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.ProviderConstant;
import com.china.center.oa.product.constant.StorageConstant;


public class Activator implements BundleActivator
{
    private List<Class> parserClass = new LinkedList<Class>()
    {
        {
            add(ProductConstant.class);
            add(ProviderConstant.class);
            add(DepotConstant.class);
            add(StorageConstant.class);
            add(ComposeConstant.class);
            add(PriceChangeConstant.class);
            add(ProductApplyConstant.class);
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
