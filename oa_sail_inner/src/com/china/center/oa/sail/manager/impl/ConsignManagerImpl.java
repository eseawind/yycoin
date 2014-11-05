/**
 * File Name: ConsignManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-29<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager.impl;


import java.util.List;

import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.TransportBean;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.manager.ConsignManager;


/**
 * ConsignManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-29
 * @see ConsignManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class ConsignManagerImpl implements ConsignManager
{
    private ConsignDAO consignDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public ConsignManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.ConsignManager#addConsign(com.china.center.oa.sail.bean.ConsignBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addConsign(ConsignBean bean)
        throws MYException
    {
        return consignDAO.addConsign(bean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.ConsignManager#addTransport(com.china.center.oa.sail.bean.TransportBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addTransport(TransportBean bean)
        throws MYException
    {
        bean.setId(commonDAO.getSquenceString());

        return consignDAO.addTransport(bean);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateTransport(TransportBean bean)
        throws MYException
    {
        return consignDAO.updateTransport(bean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.ConsignManager#delConsign(java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean delConsign(String id)
        throws MYException
    {
        return consignDAO.delConsign(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.ConsignManager#delTransport(java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean delTransport(String id)
        throws MYException
    {
        return consignDAO.delTransport(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.ConsignManager#updateConsign(com.china.center.oa.sail.bean.ConsignBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateConsign(ConsignBean bean)
        throws MYException
    {
        return consignDAO.updateConsign(bean);
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean updateAllConsign(List<ConsignBean> list)
        throws MYException
    {
    	for(ConsignBean each : list)
    	{
    		consignDAO.updateConsign(each);
    	}
    	
    	return true;
    }

    /**
     * 处理发货单(一个发货单同配送单号，可以有多条发货数)
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
	public boolean handleAllConsign(List<ConsignBean> list) throws MYException
	{
    	ConditionParse con = new ConditionParse();
    	
    	con.addCondition("t1.distId", "=", list.get(0).getDistId());
    	
    	List<ConsignBean> oldList = consignDAO.queryConsignByCondition(con);
    	
    	for (ConsignBean each : oldList)
    	{
    		consignDAO.delConsignBean(each.getGid());
    	}
    	
    	for(ConsignBean each : list)
    	{
    		each.setCurrentStatus(99);
    		consignDAO.addConsign(each);
    	}
    	
		return true;
	}
    
    /**
     * @return the consignDAO
     */
    public ConsignDAO getConsignDAO()
    {
        return consignDAO;
    }

    /**
     * @param consignDAO
     *            the consignDAO to set
     */
    public void setConsignDAO(ConsignDAO consignDAO)
    {
        this.consignDAO = consignDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }
}
