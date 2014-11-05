/**
 * File Name: QueryListenerBankImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.listener.impl;


import java.util.List;

import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.tax.dao.TaxTypeDAO;


/**
 * QueryListenerBankImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-25
 * @see QueryListenerTaxTypeImpl
 * @since 3.0
 */
public class QueryListenerTaxTypeImpl implements QueryListener
{
    private TaxTypeDAO taxTypeDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return taxTypeDAO.listEntityBeans();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$taxPtypeList";
    }

    /**
     * @return the taxTypeDAO
     */
    public TaxTypeDAO getTaxTypeDAO()
    {
        return taxTypeDAO;
    }

    /**
     * @param taxTypeDAO
     *            the taxTypeDAO to set
     */
    public void setTaxTypeDAO(TaxTypeDAO taxTypeDAO)
    {
        this.taxTypeDAO = taxTypeDAO;
    }

}
