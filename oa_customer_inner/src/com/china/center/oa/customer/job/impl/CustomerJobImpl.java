/**
 * File Name: CustomerJobImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.job.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.publics.trigger.CommonJob;


/**
 * CustomerJobImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerJobImpl
 * @since 1.0
 */
public class CustomerJobImpl implements CommonJob
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log logger1 = LogFactory.getLog("core");

    private CustomerMainDAO customerMainDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.CommonJob#excuteJob()
     */
    @Transactional(rollbackFor = {MYException.class})
    public void excuteJob()
    {
        try
        {
            int count = customerMainDAO.autoUpdateCustomerStatus();

            logger1.info("同步客户的状态和新旧属性中更新了" + count + "个客户的新旧属性状态");
        }
        catch (Exception e)
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
        return "CustomerJob.Impl";
    }

	/**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

}
