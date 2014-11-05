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

import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryListenerCreditImpl
 * @since 1.0
 */
public class QueryListenerCreditImpl implements QueryListener
{
    private CreditLevelDAO creditLevelDAO = null;

    /**
     * default constructor
     */
    public QueryListenerCreditImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return creditLevelDAO.listEntityBeans();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$creditLevel";
    }

    /**
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
    }
}
