/**
 * File Name: QueryListenerBudgetImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.listener.impl;


import java.util.List;

import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerBudgetImpl
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see QueryListenerFeeItemImpl
 * @since 3.0
 */
public class QueryListenerFeeItemImpl implements QueryListener
{
    private FeeItemDAO feeItemDAO = null;

    /**
     * default constructor
     */
    public QueryListenerFeeItemImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return feeItemDAO.listEntityBeans();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$feeItemList";
    }

    /**
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
    }

}
