/**
 * File Name: ProviderListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.listener.ProviderListener;
import com.china.center.oa.sail.dao.OutDAO;


/**
 * ProviderListenerTaxImpl
 * 
 * @author ZHUZHU
 * @version 2011-8-21
 * @see ProviderListenerSailImpl
 * @since 3.0
 */
public class ProviderListenerSailImpl implements ProviderListener
{
    private OutDAO outDAO = null;

    /**
     * default constructor
     */
    public ProviderListenerSailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProviderListener#onDelete(com.china.center.oa.product.bean.ProviderBean)
     */
    public void onDelete(ProviderBean bean)
        throws MYException
    {
        if (outDAO.countProviderInOut(bean.getId()) > 0)
        {
            throw new MYException("供应商已经被入库单使用不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "ProviderListener.SailImpl";
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

}
