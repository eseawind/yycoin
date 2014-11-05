/**
 * File Name: FinanceReferDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-4-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.tax.bean.FinanceRefer;
import com.china.center.oa.tax.dao.FinanceReferDAO;


/**
 * FinanceReferDAOImpl
 * 
 * @author ZHUZHU
 * @version 2012-4-27
 * @see FinanceReferDAOImpl
 * @since 3.0
 */
public class FinanceReferDAOImpl extends BaseDAO<FinanceRefer, FinanceRefer> implements FinanceReferDAO
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private PlatformTransactionManager transactionManager = null;

    private ParameterDAO parameterDAO = null;

    public synchronized void syn()
    {
        final String begin = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);

        jdbcOperation.update("TRUNCATE TABLE T_CENTER_FINANCEREFER");

        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int update = jdbcOperation
                        .update("INSERT INTO T_CENTER_FINANCEREFER select 0, 0, t.unitId, '' from (select DISTINCT(unitId) as unitId from T_CENTER_FINANCEITEM where financeDate >= '"
                                + begin + "' and unitId <> '') t");

                    triggerLog.info("add UNIT FinanceRefer:" + update);

                    update = jdbcOperation
                        .update("INSERT INTO T_CENTER_FINANCEREFER select 0, 1, t.stafferId, '' from (select DISTINCT(stafferId) as stafferId from T_CENTER_FINANCEITEM where financeDate >= '"
                                + begin + "' and stafferId <> '') t");

                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

}
