/**
 * File Name: CheckViewDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-9<br>
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
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.tax.bean.CheckViewBean;
import com.china.center.oa.tax.dao.CheckViewDAO;
import com.china.center.oa.tax.vo.CheckViewVO;


/**
 * CheckViewDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-3-9
 * @see CheckViewDAOImpl
 * @since 3.0
 */
public class CheckViewDAOImpl extends BaseDAO<CheckViewBean, CheckViewVO> implements CheckViewDAO
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private PlatformTransactionManager transactionManager = null;

    public boolean updateCheck(String tableName, String id, String reason)
    {
        String key = "id";

        if ("T_CENTER_OUT".equalsIgnoreCase(tableName))
        {
            key = "fullId";
        }

        String sql = "update " + tableName + " set checkStatus = ?, checks = ? where " + key
                     + " = ?";

        this.jdbcOperation.update(sql, PublicConstant.CHECK_STATUS_END, reason, id);

        return true;
    }

    public synchronized void syn()
    {
        jdbcOperation.update("TRUNCATE TABLE T_CENTER_CHECK");

        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int update = jdbcOperation
                        .update("INSERT INTO T_CENTER_CHECK SELECT * FROM V_CENTER_CHECK");

                    triggerLog.info("add check:" + update);

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
