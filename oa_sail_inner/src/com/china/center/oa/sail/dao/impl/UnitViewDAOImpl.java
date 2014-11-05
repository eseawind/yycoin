/**
 * File Name: UnitViewDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao.impl;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.UnitViewBean;
import com.china.center.oa.sail.dao.UnitViewDAO;


/**
 * UnitViewDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-27
 * @see UnitViewDAOImpl
 * @since 3.0
 */
public class UnitViewDAOImpl extends BaseDAO<UnitViewBean, UnitViewBean> implements UnitViewDAO
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private PlatformTransactionManager transactionManager = null;

    public UnitViewBean find(Serializable id)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("id", "=", id.toString());

        List<UnitViewBean> list = this.queryEntityBeansByCondition(con.toString());

        if (list.size() == 0)
        {
            return null;
        }

        return list.get(0);
    }

    public void syn()
    {
        jdbcOperation.update("TRUNCATE TABLE T_CENTER_UNIT");
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int update = jdbcOperation
                        .update("INSERT INTO T_CENTER_UNIT SELECT * FROM V_CENTER_UNIT");

                    triggerLog.info("add unit:" + update);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
    }
    
    // 增量（白天）
    public void synNew()
    {
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int update = jdbcOperation
                        .update("INSERT INTO T_CENTER_UNIT " +
                        		"SELECT * FROM V_CENTER_UNIT where not exists (select * from T_CENTER_UNIT where V_CENTER_UNIT.id = T_CENTER_UNIT.id)");

                    triggerLog.info("add unit(day time):" + update);

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
}
