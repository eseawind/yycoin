/**
 * File Name: CustomerLocationJobImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.job.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.publics.trigger.CommonJob;


/**
 * CustomerLocationJobImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerLocationJobImpl
 * @since 1.0
 */
public class CustomerLocationJobImpl implements CommonJob
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ClientManager clientManager = null;

    /**
     * default constructor
     */
    public CustomerLocationJobImpl()
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
        	clientManager.synchronizationAllCustomerLocation();
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
        return "CustomerLocationJob.Impl";
    }

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager()
	{
		return clientManager;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager)
	{
		this.clientManager = clientManager;
	}
}
