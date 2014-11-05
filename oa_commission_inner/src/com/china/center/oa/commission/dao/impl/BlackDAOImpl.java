package com.china.center.oa.commission.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.commission.bean.BlackBean;
import com.china.center.oa.commission.dao.BlackDAO;
import com.china.center.oa.commission.vo.BlackVO;

public class BlackDAOImpl extends BaseDAO<BlackBean, BlackVO> implements BlackDAO 
{
	private final Log triggerLog = LogFactory.getLog("trigger");

    private PlatformTransactionManager transactionManager = null;
    
	@Override
	public List<BlackBean> queryByLogDate(String logDate)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("BlackBean.logDate", "<>", logDate);
		
		return this.queryEntityBeansByCondition(con);
	}

	@Override
	public void backup()
	{
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    int update = jdbcOperation
                        .update("INSERT INTO T_CENTER_BLACK_H(id,logDate,stafferId,money,days,allMoneys,credit,entryDate,removeDate,industryId, backupDate) " +
                        		"SELECT id,logDate,stafferId,money,days,allMoneys,credit,entryDate,removeDate,industryId, now() FROM T_CENTER_BLACK");

                    jdbcOperation
                    .update("INSERT INTO t_center_black_out_h(id,refId,type,outId,days,money,customerName,backupDate) " +
                    		"SELECT id,refId,type,outId,days,money,customerName,now() FROM t_center_black_out");
                    
                    jdbcOperation
                    .update("INSERT INTO t_center_black_out_detail_h(Id,outId,outBalanceId,productId,amount,price,costprice,backupDate) " +
                    		"SELECT Id,outId,outBalanceId,productId,amount,price,costprice,now() FROM t_center_black_out_detail");
                    
                    triggerLog.info("backup black list:" + update);

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
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
}
