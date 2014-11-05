/**
 * File Name: BankManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.bean.StatBankBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.StatBankDAO;
import com.china.center.oa.finance.listener.BankListener;
import com.china.center.oa.finance.manager.BankManager;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.tools.FileTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;


/**
 * BankManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see BankManagerImpl
 * @since 3.0
 */
@Exceptional
public class BankManagerImpl extends AbstractListenerManager<BankListener> implements BankManager
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private BankDAO bankDAO = null;

    private CommonDAO commonDAO = null;

    private PaymentDAO paymentDAO = null;

    private StatBankDAO statBankDAO = null;

    private ParameterDAO parameterDAO = null;

    private InBillDAO inBillDAO = null;

    private DutyDAO dutyDAO = null;

    private OutBillDAO outBillDAO = null;

    /**
     * default constructor
     */
    public BankManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BankManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.BankBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, BankBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @bankDAO", "名称已经存在");

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setMtype(duty.getMtype());

        bankDAO.saveEntityBean(bean);

        Collection<BankListener> listenerMapValues = this.listenerMapValues();

        for (BankListener bankListener : listenerMapValues)
        {
            bankListener.onAddBank(user, bean);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BankManager#deleteBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BankBean old = bankDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Collection<BankListener> listenerMapValues = this.listenerMapValues();

        for (BankListener bankListener : listenerMapValues)
        {
            bankListener.onDeleteBank(user, old);
        }

        bankDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BankManager#updateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.BankBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, BankBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        BankBean old = bankDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique2 @bankDAO", "名称已经存在");

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setMtype(duty.getMtype());

        bankDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * 导出银行统计
     */
    public void exportAllCurrentBankStat()
    {
        triggerLog.info("begin exportAllCurrentBankStat...");

        List<BankBean> lList = bankDAO.listEntityBeans();

        String root = getBankPath() + "/" + TimeTools.now("yyyyMMdd");

        FileTools.mkdirs(root);

        for (BankBean bank : lList)
        {
            WriteFile write = null;

            OutputStream out = null;

            try
            {
                String path = root + "/" + bank.getName() + "_统计_" + TimeTools.now("yyyyMMdd")
                              + ".csv";

                FileTools.delete(path);

                out = new FileOutputStream(path);

                write = WriteFileFactory.getMyTXTWriter();

                write.openFile(out);

                this.wirteBankStat(write, bank);

                write.close();

                triggerLog.info("exportAllCurrentBankStat:" + path);
            }
            catch (Throwable e)
            {
                triggerLog.error(e, e);
            }
            finally
            {
                if (write != null)
                {
                    try
                    {
                        write.close();
                    }
                    catch (IOException e)
                    {
                    }
                }

                if (out != null)
                {
                    try
                    {
                        out.close();
                    }
                    catch (IOException e)
                    {
                    }
                }
            }
        }

        triggerLog.info("end exportAllCurrentBankStat...");
    }

    /**
     * @return the mailAttchmentPath
     */
    public String getBankPath()
    {
        return ConfigLoader.getProperty("bankStore");
    }

    public void wirteBankStat(WriteFile write, BankBean bank)
        throws IOException
    {
        write.writeLine("日期,标识,帐户,类型,金额,异动金额");

        StatBankBean stat = statBankDAO.findByBankIdAndTimeKey(bank.getId(), parameterDAO
            .getString(SysConfigConstant.BANK_STAT_POINT));

        // TEMPLATE 导出CSV工具
        WriteFileBuffer line = new WriteFileBuffer(write);

        // 期初导出
        if (stat != null)
        {
            line.writeColumn("[" + stat.getTimeKey() + "]");
            // 防止ID显示不正常
            line.writeColumn("A" + stat.getId());
            line.writeColumn(bank.getName());
            line.writeColumn("期初余额");
            line.writeColumn(stat.getTotal());
            line.writeColumn(stat.getTotal());

            line.writeLine();
        }

        String beginTime = parameterDAO.getString(SysConfigConstant.BANK_EXPORT_POINT);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("logTime", ">=", beginTime);

        condtion.addCondition("bankId", "=", bank.getId());

        PageSeparate page = new PageSeparate();

        int count = inBillDAO.countByCondition(condtion.toString());

        page.reset2(count, 2000);

        while (page.nextPage())
        {
            List<InBillBean> voList = this.inBillDAO.queryEntityBeansByCondition(condtion, page);

            for (InBillBean each : voList)
            {
                line.reset();

                line.writeColumn("[" + each.getLogTime() + "]");
                line.writeColumn(each.getId());
                line.writeColumn(bank.getName());
                line.writeColumn("收款单");
                line.writeColumn(each.getMoneys());
                line.writeColumn(each.getMoneys());

                line.writeLine();
            }
        }

        // 付款单
        count = outBillDAO.countByCondition(condtion.toString());

        page.reset2(count, 2000);

        while (page.nextPage())
        {
            List<OutBillBean> voList = this.outBillDAO.queryEntityBeansByCondition(condtion, page);

            for (OutBillBean each : voList)
            {
                line.reset();

                line.writeColumn("[" + each.getLogTime() + "]");
                line.writeColumn(each.getId());
                line.writeColumn(bank.getName());
                line.writeColumn("付款单");
                line.writeColumn(each.getMoneys());
                line.writeColumn( -each.getMoneys());

                line.writeLine();
            }
        }

        // 没有认领的回款单
        condtion.clear();

        condtion.addWhereStr();

        // 因为回款一旦申请就可以变成已收,此时实际上没有生回款单,所以这里使用useall
        condtion.addIntCondition("useall", "=", FinanceConstant.PAYMENT_USEALL_INIT);

        condtion.addCondition("bankId", "=", bank.getId());

        count = paymentDAO.countByCondition(condtion.toString());

        page.reset2(count, 2000);

        while (page.nextPage())
        {
            List<PaymentBean> voList = this.paymentDAO.queryEntityBeansByCondition(condtion, page);

            for (PaymentBean each : voList)
            {
                line.reset();

                line.writeColumn("[" + each.getLogTime() + "]");
                line.writeColumn(each.getId());
                line.writeColumn(bank.getName());
                line.writeColumn("未认领回款");
                line.writeColumn(each.getMoney());
                line.writeColumn(each.getMoney());

                line.writeLine();
            }
        }

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
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

}
