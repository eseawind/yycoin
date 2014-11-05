/**
 * File Name: QueryListenerBankImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener.impl;


import java.util.List;

import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerBankImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-25
 * @see QueryListenerBankImpl
 * @since 3.0
 */
public class QueryListenerBankImpl implements QueryListener
{
    private BankDAO bankDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return bankDAO.listEntityBeansByOrder("order by name");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$bankList";
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
    }

}
