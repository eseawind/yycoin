/**
 * File Name: TaxFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.facade;


import com.china.center.common.MYException;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.TaxBean;


/**
 * TaxFacade
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see TaxFacade
 * @since 1.0
 */
public interface TaxFacade
{
    Object FINANCE_LOCK = new Object();

    Object CHECK_LOCK = new Object();

    boolean addTaxBean(String userId, TaxBean bean)
        throws MYException;

    boolean updateTaxBean(String userId, TaxBean bean)
        throws MYException;

    boolean deleteTaxBean(String userId, String id)
        throws MYException;

    boolean addFinanceBean(String userId, FinanceBean bean)
        throws MYException;

    boolean updateFinanceBean(String userId, FinanceBean bean)
        throws MYException;

    boolean deleteFinanceBean(String userId, String id)
        throws MYException;

    boolean checks(String userId, String id, String reason)
        throws MYException;

    boolean checks2(String userId, String id, int type, String reason)
        throws MYException;

    boolean updateFinanceCheck(String userId, String id, String reason)
        throws MYException;
}
