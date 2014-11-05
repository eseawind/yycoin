/**
 * File Name: StafferListenerCustomerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.listener.impl;


import com.china.center.common.MYException;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.listener.StafferListener;


/**
 * StafferListenerCustomerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-8
 * @see StafferListenerCustomerImpl
 * @since 1.0
 */
public class StafferListenerCustomerImpl implements StafferListener
{
    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.StafferListener#onAdd(com.china.center.oa.publics.bean.StafferBean)
     */
    public void onAdd(StafferBean bean)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.StafferListener#onDelete(java.lang.String)
     */
    public void onDelete(String stafferId)
        throws MYException
    {
        if (stafferVSCustomerDAO.countByFK(stafferId) > 0)
        {
            throw new MYException("职员下面还有客户,不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.StafferListener#onUpdate(com.china.center.oa.publics.bean.StafferBean)
     */
    public void onUpdate(StafferBean bean)
        throws MYException
    {

    }

    public void onDrop(StafferBean bean)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "StafferListener.CustomerImpl";
    }

    /**
     * @return the stafferVSCustomerDAO
     */
    public StafferVSCustomerDAO getStafferVSCustomerDAO()
    {
        return stafferVSCustomerDAO;
    }

    /**
     * @param stafferVSCustomerDAO
     *            the stafferVSCustomerDAO to set
     */
    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
    {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
    }

}
