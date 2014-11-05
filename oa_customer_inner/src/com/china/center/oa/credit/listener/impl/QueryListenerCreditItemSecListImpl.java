/**
 * File Name: QueryListenerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.listener.impl;


import java.util.List;

import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryListenerCreditItemSecListImpl
 * @since 1.0
 */
public class QueryListenerCreditItemSecListImpl implements QueryListener
{
    private CreditItemSecDAO creditItemSecDAO = null;

    /**
     * default constructor
     */
    public QueryListenerCreditItemSecListImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return creditItemSecDAO.listEntityVOs();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$creditItemSecList";
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

}
