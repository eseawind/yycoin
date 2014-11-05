/**
 * File Name: ChangeStatusJobImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.job.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.customer.manager.CustomerCheckManager;
import com.china.center.oa.publics.trigger.CommonJob;


/**
 * ChangeStatusJobImpl(客户信息核对申请状态更新)
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see ChangeStatusJobImpl
 * @since 1.0
 */
public class ChangeStatusJobImpl implements CommonJob
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private final Log _logger = LogFactory.getLog(getClass());

    private CustomerCheckManager customerCheckManager = null;

    /**
     * default constructor
     */
    public ChangeStatusJobImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.CommonJob#excuteJob()
     */
    public void excuteJob()
    {
        try
        {
            int count = customerCheckManager.autoChangeStatus();

            triggerLog.info("更新客户信息核对申请状态中更新了" + count + "个状态");
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.CommonJob#getJobName()
     */
    public String getJobName()
    {
        return "ChangeStatusJob.Impl";
    }

    /**
     * @return the customerCheckManager
     */
    public CustomerCheckManager getCustomerCheckManager()
    {
        return customerCheckManager;
    }

    /**
     * @param customerCheckManager
     *            the customerCheckManager to set
     */
    public void setCustomerCheckManager(CustomerCheckManager customerCheckManager)
    {
        this.customerCheckManager = customerCheckManager;
    }

}
