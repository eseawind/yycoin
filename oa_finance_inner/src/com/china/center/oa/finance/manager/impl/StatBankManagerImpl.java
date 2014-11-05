/**
 * File Name: StatBankManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.StatBankBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.StatBankDAO;
import com.china.center.oa.finance.manager.StatBankManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;


/**
 * StatBankManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankManagerImpl
 * @since 3.0
 */
public class StatBankManagerImpl implements StatBankManager
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private StatBankDAO statBankDAO = null;

    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    private CommonDAO commonDAO = null;

    private PaymentDAO paymentDAO = null;

    private BankDAO bankDAO = null;

    private PlatformTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public StatBankManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.StatBankManager#statBank()
     */
    public void statBank()
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -1);

        final String timeKey = TimeTools.getStringByFormat(new Date(cal.getTime().getTime()),
            "yyyyMM");

        try
        {
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    statBankInner(timeKey);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
    }

    private void statBankInner(String timeKey)
    {
        List<BankBean> bankList = bankDAO.listEntityBeans();

        for (BankBean bankBean : bankList)
        {
            handlerEach(timeKey, bankBean);
        }
    }

    private void handlerEach(String timeKey, BankBean bankBean)
    {
        StatBankBean stat = statBankDAO.findByBankIdAndTimeKey(bankBean.getId(), timeKey);

        if (stat != null)
        {
            triggerLog.info(bankBean.getName() + "的[" + timeKey + "]月结已经完成过一次");

            return;
        }

        // 获取上上月的月结
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -2);

        String lastestKey = TimeTools
            .getStringByFormat(new Date(cal.getTime().getTime()), "yyyyMM");

        StatBankBean lastStat = statBankDAO.findByBankIdAndTimeKey(bankBean.getId(), lastestKey);

        if (lastStat == null)
        {
            lastStat = new StatBankBean();

            lastStat.setTotal(0.0d);

            lastStat.setBankId(bankBean.getId());

            lastStat.setTimeKey(lastestKey);
        }

        Calendar calNew = Calendar.getInstance();

        calNew.add(Calendar.MONTH, -1);

        String ym = TimeTools.getStringByFormat(new Date(calNew.getTime().getTime()), "yyyy-MM-");

        // 统计上一个月的余额
        String beginTime = ym + "01 00:00:00";

        String endTime = ym + "31 23:59:59";

        ConditionParse inCon = new ConditionParse();

        inCon.addWhereStr();

        inCon.addCondition("InBillBean.bankId", "=", bankBean.getId());

        inCon.addCondition("InBillBean.logTime", ">=", beginTime);

        inCon.addCondition("InBillBean.logTime", "<=", endTime);

        // 总收入
        double inTotal = inBillDAO.sumByCondition(inCon) + lastStat.getTotal();

        ConditionParse outCon = new ConditionParse();

        outCon.addWhereStr();

        outCon.addCondition("OutBillBean.bankId", "=", bankBean.getId());

        outCon.addCondition("OutBillBean.logTime", ">=", beginTime);

        outCon.addCondition("OutBillBean.logTime", "<=", endTime);

        double outTotal = outBillDAO.sumByCondition(outCon);

        // 月结余
        double lastTotal = inTotal - outTotal;

        StatBankBean newStat = new StatBankBean();

        newStat.setId(commonDAO.getSquenceString20());

        newStat.setBankId(bankBean.getId());

        newStat.setTimeKey(timeKey);

        newStat.setLogTime(TimeTools.now());

        newStat.setTotal(lastTotal);

        int inLockNumber = inBillDAO.lockByCondition(inCon);

        int outLockNumber = outBillDAO.lockByCondition(outCon);

        newStat.setDescription("系统自动结余,锁定收款单:" + inLockNumber + "个,锁定付款单:" + outLockNumber + "个");

        statBankDAO.saveEntityBean(newStat);
    }

    public double findTotalByBankId(String bankId)
    {
        Calendar calNew = Calendar.getInstance();

        calNew.add(Calendar.MONTH, -1);

        // 获得上月的结余
        String timeKey = TimeTools
            .getStringByFormat(new Date(calNew.getTime().getTime()), "yyyyMM");

        StatBankBean lastStat = statBankDAO.findByBankIdAndTimeKey(bankId, timeKey);

        if (lastStat == null)
        {
            lastStat = new StatBankBean();

            lastStat.setTotal(0.0d);

            lastStat.setBankId(bankId);

            lastStat.setTimeKey(timeKey);
        }

        String ym = TimeTools.now("yyyy-MM-");

        // 统计本月的余额
        String beginTime = ym + "01 00:00:00";

        String endTime = ym + "31 23:59:59";

        ConditionParse inCon = new ConditionParse();

        inCon.addWhereStr();

        inCon.addCondition("InBillBean.bankId", "=", bankId);

        inCon.addCondition("InBillBean.logTime", ">=", beginTime);

        inCon.addCondition("InBillBean.logTime", "<=", endTime);

        // 获取没有认领的回款
        double sumNotUserByBankId = paymentDAO.sumNotUserByBankId(bankId);

        // 总收入(本月收入+上月结余+回款没有认领的)
        double inTotal = inBillDAO.sumByCondition(inCon) + lastStat.getTotal() + sumNotUserByBankId;

        ConditionParse outCon = new ConditionParse();

        outCon.addWhereStr();

        outCon.addCondition("OutBillBean.bankId", "=", bankId);

        outCon.addCondition("OutBillBean.logTime", ">=", beginTime);

        outCon.addCondition("OutBillBean.logTime", "<=", endTime);

        // 付款的
        double outTotal = outBillDAO.sumByCondition(outCon);

        // 月结余
        double lastTotal = inTotal - outTotal;

        // 防止-0.0000000000009等
        if (MathTools.compare(lastTotal, 0.0d) == 0)
        {
            return 0.0d;
        }

        // 本月统计
        return lastTotal;
    }

    /**
     * @return the statBankDAO
     */
    public StatBankDAO getStatBankDAO()
    {
        return statBankDAO;
    }

    /**
     * @param statBankDAO
     *            the statBankDAO to set
     */
    public void setStatBankDAO(StatBankDAO statBankDAO)
    {
        this.statBankDAO = statBankDAO;
    }

    /**
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
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
     * @return the paymentDAO
     */
    public PaymentDAO getPaymentDAO()
    {
        return paymentDAO;
    }

    /**
     * @param paymentDAO
     *            the paymentDAO to set
     */
    public void setPaymentDAO(PaymentDAO paymentDAO)
    {
        this.paymentDAO = paymentDAO;
    }
}
