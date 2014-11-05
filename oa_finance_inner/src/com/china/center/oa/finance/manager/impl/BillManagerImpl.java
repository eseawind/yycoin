/**
 * File Name: BillManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.Collection;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.PaymentVSOutDAO;
import com.china.center.oa.finance.listener.BillListener;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.StatBankManager;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferTransferDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.manager.StockManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * BillManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see BillManagerImpl
 * @since 3.0
 */
@Exceptional
public class BillManagerImpl extends AbstractListenerManager<BillListener> implements BillManager
{
    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    private OutDAO outDAO = null;

    private CommonDAO commonDAO = null;

    private BankDAO bankDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private DutyDAO dutyDAO = null;

    private StafferDAO stafferDAO = null;

    private PaymentDAO paymentDAO = null;

    private StockManager stockManager = null;

    private PaymentVSOutDAO paymentVSOutDAO = null;

    private StockItemDAO stockItemDAO = null;

    private StatBankManager statBankManager = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;

    private StafferTransferDAO stafferTransferDAO = null;
    
    private static Object LOCK = new Object();
    
    private static boolean     LOCK_BILL       = false;

    /**
     * default constructor
     */
    public BillManagerImpl()
    {
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addInBillBean(User user, InBillBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        return addInBillBeanWithoutTransaction(user, bean);
    }

    public boolean addInBillBeanWithoutTransaction(User user, InBillBean bean)
        throws MYException
    {
        synchronized (LOCK)
        {
            bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));

            if (StringTools.isNullOrNone(bean.getLogTime()))
            {
                bean.setLogTime(TimeTools.now());
            }

            if (bean.getType() == FinanceConstant.INBILL_TYPE_UNBORROW)
            {
                // 个人还款
            }
            else
            {
                // 验证销售单绑定策略(非坏账)
                if ( !StringTools.isNullOrNone(bean.getOutId())
                    && bean.getType() != FinanceConstant.INBILL_TYPE_BADOUT)
                {
                    OutBean out = outDAO.find(bean.getOutId());

                    if (out == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }

                    // 已经支付的
                    double hasPay = inBillDAO.sumByOutId(bean.getOutId());

                    // 委托代销存在结算价与出库价不同情况
                    if (out.getOutType() != OutConstant.OUTTYPE_OUT_CONSIGN)
                    {
                    	// 发现支付的金额过多
                        if (MathTools.compare(hasPay + bean.getMoneys(), out.getTotal()) > 0)
                        {
                            throw new MYException(
                                "销售单[%s]的总金额[%.2f],当前已付金额[%.2f],本次申请付款[%.2f],付款金额超出销售金额", bean
                                    .getOutId(), out.getTotal(), hasPay, bean.getMoneys());
                        }
                    }

                    // 更新已经支付的金额
                    outDAO.updateHadPay(bean.getOutId(), hasPay + bean.getMoneys());
                }

                // 更新坏账状态
                if ( !StringTools.isNullOrNone(bean.getOutId())
                    && bean.getType() == FinanceConstant.INBILL_TYPE_BADOUT)
                {
                    OutBean out = outDAO.find(bean.getOutId());

                    if (out == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }

                    bean.setCustomerId(out.getCustomerId());

                    bean.setOwnerId(out.getStafferId());

                    outDAO.updataBadDebtsCheckStatus(bean.getOutId(),
                        OutConstant.BADDEBTSCHECKSTATUS_YES);
                }
            }

            return saveInBillInner(bean);
        }
    }

    public boolean saveInBillInner(InBillBean bean)
        throws MYException
    {
        if (bean.getSrcMoneys() == 0.0)
        {
            bean.setSrcMoneys(bean.getMoneys());
        }

        // 设置mtype
        setMtype(bean);

        // 如果自动创建,则为已核对
        if (bean.getCreateType() == FinanceConstant.BILL_CREATETYPE_AUTO)
            bean.setCheckStatus(PublicConstant.CHECK_STATUS_END);
        else
            bean.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
        
        boolean ret = inBillDAO.saveEntityBean(bean);
        
        // 财务标记
        Collection<BillListener> listenerMapValues = this.listenerMapValues();
        
        for(BillListener listener : listenerMapValues){
            
            listener.onAddInBill(null, bean);
        }
        
        return ret;
    }

    /**
     * setMtype
     * 
     * @param bean
     * @throws MYException
     */
    private void setMtype(InBillBean bean)
        throws MYException
    {
        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DutyBean duty = dutyDAO.find(bank.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setMtype(duty.getMtype());
        bean.setDutyId(duty.getId());
    }

    private void setMtype(OutBillBean bean)
        throws MYException
    {
        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DutyBean duty = dutyDAO.find(bank.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setMtype(duty.getMtype());
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteInBillBean(User user, String id)
        throws MYException
    {
        // 如果被OUTID绑定不能删除
        JudgeTools.judgeParameterIsNull(user, id);

        InBillBean bill = inBillDAO.find(id);

        if (bill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bill.getLock() == FinanceConstant.BILL_LOCK_YES)
        {
            throw new MYException("单据已经被统计固化,请确认操作");
        }

        if (bill.getCheckStatus() == PublicConstant.CHECK_STATUS_END)
        {
            throw new MYException("单据已经被核对,请确认操作");
        }

        if ( !StringTools.isNullOrNone(bill.getOutId())
            || !StringTools.isNullOrNone(bill.getOutBalanceId()))
        {
            throw new MYException("单据已经被销售单[%s]绑定,请确认操作", bill.getOutId());
        }

        if ( !StringTools.isNullOrNone(bill.getPaymentId()))
        {
            throw new MYException("单据已经和回款绑定,只能通过退领删除收款,请确认操作", bill.getOutId());
        }

        int countByBill = paymentVSOutDAO.countByBill(id);

        if (countByBill > 0)
        {
            throw new MYException("收付款单被申请绑定(可能被驳回的单据关联),请确认操作");
        }

        // 帐户余额
        double total = statBankManager.findTotalByBankId(bill.getBankId());

        if (total - bill.getMoneys() < 0)
        {
            throw new MYException("帐户剩余[%.2f],当前删除总金额[%.2f],帐户金额不足", total, bill.getMoneys());
        }

        inBillDAO.deleteEntityBean(id);

        // 更新回款单的状态(此逻辑已经删除)
        if (false && !StringTools.isNullOrNone(bill.getPaymentId()))
        {
            PaymentBean payment = paymentDAO.find(bill.getPaymentId());

            double hasUsed = inBillDAO.sumByPaymentId(bill.getPaymentId());

            payment.setUseMoney(hasUsed);

            if (MathTools.compare(hasUsed, payment.getMoney()) >= 0)
            {
                payment.setUseall(FinanceConstant.PAYMENT_USEALL_END);
            }
            else
            {
                payment.setUseall(FinanceConstant.PAYMENT_USEALL_INIT);
            }

            paymentDAO.updateEntityBean(payment);
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateInBillBean(User user, InBillBean bean)
        throws MYException
    {
        return inBillDAO.updateEntityBean(bean);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addOutBillBean(User user, OutBillBean bean)
        throws MYException
    {
        return addOutBillBeanWithoutTransaction(user, bean);
    }

    public boolean addOutBillBeanWithoutTransaction(User user, OutBillBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        double total = statBankManager.findTotalByBankId(bean.getBankId());

        if (total - bean.getMoneys() < 0)
        {
            throw new MYException("帐户剩余[%.2f],当前付款金额[%.2f],金额不足", total, bean.getMoneys());
        }

        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));

        bean.setLogTime(TimeTools.now());

        // 去掉审批
//        if (bean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER)
//        {
//            bean.setStatus(FinanceConstant.OUTBILL_STATUS_MANAGER);
//        }

        // 处理采购付款
        handleStockItem(user, bean);

        saveOutBillInner(bean);
        
        if ((bean.getType() == FinanceConstant.OUTBILL_TYPE_HANDLING
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_STOCK
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER)
                && bean.getCreateType() == FinanceConstant.BILL_CREATETYPE_HAND)
        {
        	// 转账一步到位
        	if (bean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER) {
        		InBillBean inbill = saveInBill(user, bean.getId(), bean);

                bean.setStatus(FinanceConstant.OUTBILL_STATUS_CONFIRM);

                bean.setLock(FinanceConstant.BILL_LOCK_YES);

                bean.setRefBillId(inbill.getId());

                outBillDAO.updateEntityBean(bean);
        	}
        	
            // 针对有些类型的手工付款要自动生成凭证
            Collection<BillListener> listenerMapValues = this.listenerMapValues();
            
            for(BillListener listener : listenerMapValues){
                
                listener.onCreateOutBill(user, bean);
            }
            
        }

        
        return true;
    }

    private boolean saveOutBillInner(OutBillBean bean)
        throws MYException
    {
        if (bean.getSrcMoneys() == 0.0)
        {
            bean.setSrcMoneys(bean.getMoneys());
        }

        setMtype(bean);

        return outBillDAO.saveEntityBean(bean);
    }

    /**
     * handleStockItem
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void handleStockItem(User user, OutBillBean bean)
        throws MYException
    {
        if ( !StringTools.isNullOrNone(bean.getStockItemId()))
        {
            // 检查是否item全部付款
            StockItemBean item = stockItemDAO.find(bean.getStockItemId());

            if (item == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            ConditionParse con = new ConditionParse();

            con.addWhereStr();

            con.addCondition("OutBillBean.stockItemId", "=", bean.getStockItemId());

            double sum = outBillDAO.sumByCondition(con);

            // 全部付款
            if ( (sum + bean.getMoneys()) == item.getTotal())
            {
                // 关联采购项付款状态
                stockManager.payStockItemWithoutTransaction(user, bean.getStockItemId());
            }
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteOutBillBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bill = outBillDAO.find(id);

        if (bill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bill.getCheckStatus() == PublicConstant.CHECK_STATUS_END)
        {
            throw new MYException("单据已经被核对,请确认操作");
        }

        if ( !StringTools.isNullOrNone(bill.getStockId())
            || !StringTools.isNullOrNone(bill.getStockItemId()))
        {
            throw new MYException("单据已经被采购单/销售单/回款单[%s]关联,请确认操作", bill.getStockId());
        }

        if (bill.getLock() == FinanceConstant.BILL_LOCK_YES)
        {
            throw new MYException("单据已经被统计固化,请确认操作");
        }

        int countByBill = paymentVSOutDAO.countByBill(id);

        if (countByBill > 0)
        {
            throw new MYException("收付款单被申请绑定(可能被驳回的单据关联),请确认操作");
        }

        outBillDAO.deleteEntityBean(id);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public String splitInBillBean(User user, String id, double newMoney)
        throws MYException
    {
        return splitInBillBeanWithoutTransactional(user, id, newMoney);
    }
    
    @Transactional(rollbackFor = MYException.class)
    public String freezeInBillBean(User user, String id, double newMoney)
        throws MYException
    {
    	JudgeTools.judgeParameterIsNull(id);
    	
    	InBillBean inBill = inBillDAO.find(id);

        if (inBill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        if (inBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
        {
        	throw new MYException("数据错误,不是预收单");
        }
        
        if (inBill.getFreeze() == FinanceConstant.BILL_FREEZE_YES)
        {
        	throw new MYException("数据错误,当前预收已是冻结状态");
        }
        
        if (newMoney > inBill.getMoneys())
        {
        	throw new MYException("数据错误,冻结金额要小于或等于预收金额");
        }
    	
         String newBillId = splitInBillBeanWithoutTransactional(user, id, newMoney);
        
        // 冻结预收
         InBillBean bean = inBillDAO.find(newBillId);
         
         if (null == bean)
         {
        	 throw new MYException("冻结数据异常");
         }
         
         // 打上冻结标志
         bean.setFreeze(1);
         
         inBillDAO.updateEntityBean(bean);
         
         return newBillId;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean passTransferOutBillBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bean = outBillDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() != FinanceConstant.OUTBILL_TYPE_TRANSFER)
        {
            throw new MYException("不是转账付款,请确认操作");
        }

        if (bean.getStatus() != FinanceConstant.OUTBILL_STATUS_SUBMIT)
        {
            throw new MYException("转账付款不是待财务经理审批状态,请确认操作");
        }

        bean.setStatus(FinanceConstant.OUTBILL_STATUS_MANAGER);

        outBillDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * 生成收款单(同时锁定)
     * 
     * @param user
     * @param id
     * @param bean
     * @return
     * @throws MYException
     */
    private InBillBean saveInBill(User user, String id, OutBillBean bean)
        throws MYException
    {
        // 生成收款单
        InBillBean inbill = new InBillBean();

        inbill.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));

        inbill.setBankId(bean.getDestBankId());

        inbill.setDescription("自动接收转账:" + id);

        inbill.setDestBankId(bean.getBankId());

        inbill.setLocationId(user.getLocationId());

        inbill.setLock(FinanceConstant.BILL_LOCK_YES);

        inbill.setLogTime(TimeTools.now());

        inbill.setMoneys(bean.getMoneys());

        inbill.setRefBillId(id);

        inbill.setStafferId(user.getStafferId());

        inbill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

        inbill.setType(FinanceConstant.INBILL_TYPE_TRANSFER);

        saveInBillInner(inbill);

        return inbill;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectTransferOutBillBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bean = outBillDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() != FinanceConstant.OUTBILL_TYPE_TRANSFER)
        {
            throw new MYException("不是转账付款,请确认操作");
        }

        if (bean.getStatus() != FinanceConstant.OUTBILL_STATUS_SUBMIT)
        {
            throw new MYException("转账付款不是待财务经理审批状态,请确认操作");
        }

        // 驳回有两种,如果没有锁定直接删除(这里要要是删除付款对于帐户来说是增加钱)
        if (bean.getLock() == FinanceConstant.BILL_LOCK_NO)
        {
            outBillDAO.deleteEntityBean(id);
        }
        else
        {
            // 锁定了生成一个自己的收款单
            InBillBean inbill = new InBillBean();

            inbill.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));

            inbill.setBankId(bean.getBankId());

            inbill.setDescription("驳回自动生成收款单(转账):" + id);

            inbill.setDestBankId(bean.getBankId());

            inbill.setLocationId(user.getLocationId());

            inbill.setLock(FinanceConstant.BILL_LOCK_YES);

            inbill.setLogTime(TimeTools.now());

            inbill.setMoneys(bean.getMoneys());

            inbill.setRefBillId(id);

            inbill.setStafferId(bean.getStafferId());

            inbill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

            inbill.setType(FinanceConstant.INBILL_TYPE_TRANSFER);

            saveInBillInner(inbill);

            // 更新源单状态
            bean.setStatus(FinanceConstant.OUTBILL_STATUS_END);

            bean.setLock(FinanceConstant.BILL_LOCK_YES);

            bean.setRefBillId(inbill.getId());

            bean.setDescription(bean.getDescription() + "" + "此单被驳回,由于锁定自动生成了同样金额的收款单:"
                                + inbill.getId());

            outBillDAO.updateEntityBean(bean);
        }

        return true;
    }

    public String splitInBillBeanWithoutTransactional(User user, String id, double newMoney)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InBillBean bill = inBillDAO.find(id);

        if (bill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (newMoney <= 0.0)
        {
            throw new MYException("分拆金额不能小于0,请确认操作");
        }

        if (MathTools.compare(bill.getMoneys(), newMoney) < 0)
        {
            throw new MYException("金额不足,无法分拆,请确认操作");
        }

        bill.setMoneys(bill.getMoneys() - newMoney);

        String newId = commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX);

        bill.setDescription(bill.getDescription() + "<br>" + "销售关联分拆了:"
                            + MathTools.formatNum(newMoney) + "出去,新单:" + newId);

        inBillDAO.updateEntityBean(bill);

        // 分拆后时间不能变(锁定状态不能变)
        bill.setId(newId);

        bill.setMoneys(newMoney);

        bill.setSrcMoneys(newMoney);

        bill.setUpdateId(0);

        bill.setDescription("分拆" + id + "后自动生成新的收款单");

        saveInBillInner(bill);

        return bill.getId();
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateInBillBeanChecks(User user, String id, String checks)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InBillBean inBill = inBillDAO.find(id);

        if (inBill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        inBill.setChecks("[" + user.getStafferName() + "]" + checks);

        inBill.setCheckStatus(PublicConstant.CHECK_STATUS_END);

        inBillDAO.updateEntityBean(inBill);

        return true;
    }

    public boolean updateBillBeanChecksWithoutTransactional(User user, String id, String checks)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InBillBean inBill = inBillDAO.find(id);

        if (inBill != null && inBill.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
        {
            return updateInBillBeanChecks(user, id, checks);
        }

        OutBillBean bill = outBillDAO.find(id);

        if (bill != null && bill.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
        {
            return updateOutBillBeanChecks(user, id, checks);
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateOutBillBeanChecks(User user, String id, String checks)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bill = outBillDAO.find(id);

        if (bill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bill.setChecks("[" + user.getStafferName() + "]" + checks);

        bill.setCheckStatus(PublicConstant.CHECK_STATUS_END);

        outBillDAO.updateEntityBean(bill);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean changeBillToTran(User user, String billId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user);

        InBillBean inBill = inBillDAO.find(billId);

        if (inBill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        CustomerBean customerBean = customerMainDAO.find(inBill.getCustomerId());

        if (customerBean == null)
        {
            throw new MYException("预收的客户不存在,请确认操作");
        }

        StafferVSCustomerBean vs = stafferVSCustomerDAO.findByUnique(inBill.getCustomerId());

        if (vs == null)
        {
            throw new MYException("客户当前和职员无挂靠关系,请确认操作");
        }

        if (vs.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("客户当前已经和本人挂靠,无法预收移交,请确认操作");
        }

        StafferBean destStaffer = stafferDAO.find(vs.getStafferId());

        if (destStaffer == null)
        {
            throw new MYException("客户当前没有正确关联职员,请确认操作");
        }

        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        // 客户下所有的预收
        con.addCondition("customerId", "=", inBill.getCustomerId());

        con.addIntCondition("status", "=", FinanceConstant.INBILL_STATUS_NOREF);

        List<InBillBean> inBillList = inBillDAO.queryEntityBeansByCondition(con);

        changeBillListToTranWithoutTransactional(user, inBillList, destStaffer);

        return true;
    }

    /**
     * changeBillListToTranWithoutTransactional
     * 
     * @param user
     * @param inBillList
     * @param destStaffer
     * @return
     * @throws MYException
     */
    public boolean changeBillListToTranWithoutTransactional(User user, List<InBillBean> inBillList,
                                                            StafferBean destStaffer)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, inBillList, destStaffer);

        if (inBillList.size() == 0)
        {
            return true;
        }

        // 预收交接的变更
        Collection<BillListener> listenerMapValues = this.listenerMapValues();

        for (BillListener billListener : listenerMapValues)
        {
            billListener.onChageBillToStaffer(user, inBillList, destStaffer);
        }

        // 更新归属
        for (InBillBean inBillBean : inBillList)
        {
            StafferBean srcStaffer = stafferDAO.find(inBillBean.getStafferId());

            if (srcStaffer == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            inBillBean.setOwnerId(destStaffer.getId());

            inBillBean.setDescription("预收移交从:"
                                      + srcStaffer.getName() + "到:" + destStaffer.getName());
        }

        inBillDAO.updateAllEntityBeans(inBillList);

        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passTransferOutBillBean1(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bean = outBillDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() != FinanceConstant.OUTBILL_TYPE_TRANSFER)
        {
            throw new MYException("不是转账付款,请确认操作");
        }

        if (bean.getStatus() != FinanceConstant.OUTBILL_STATUS_MANAGER)
        {
            throw new MYException("转账付款不是待财务经理审批状态,请确认操作");
        }

        InBillBean inbill = saveInBill(user, id, bean);

        bean.setStatus(FinanceConstant.OUTBILL_STATUS_CONFIRM);

        bean.setLock(FinanceConstant.BILL_LOCK_YES);

        bean.setRefBillId(inbill.getId());

        outBillDAO.updateEntityBean(bean);

        // 针对有些类型的付款要自动生成凭证
        Collection<BillListener> listenerMapValues = this.listenerMapValues();
        
        for(BillListener listener : listenerMapValues){
            
            listener.onCreateOutBill(user, bean);
        }
        
        return true;
    }
    
    /**
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectTransferOutBillBean1(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBillBean bean = outBillDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() != FinanceConstant.OUTBILL_TYPE_TRANSFER)
        {
            throw new MYException("不是转账付款,请确认操作");
        }

        if (bean.getStatus() != FinanceConstant.OUTBILL_STATUS_MANAGER)
        {
            throw new MYException("转账付款不是待财务总监审批状态,请确认操作");
        }

        // 驳回有两种,如果没有锁定直接删除(这里要要是删除付款对于帐户来说是增加钱)
        if (bean.getLock() == FinanceConstant.BILL_LOCK_NO)
        {
            outBillDAO.deleteEntityBean(id);
        }
        else
        {
            // 锁定了生成一个自己的收款单
            InBillBean inbill = new InBillBean();

            inbill.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));

            inbill.setBankId(bean.getBankId());

            inbill.setDescription("驳回自动生成收款单(转账):" + id);

            inbill.setDestBankId(bean.getBankId());

            inbill.setLocationId(user.getLocationId());

            inbill.setLock(FinanceConstant.BILL_LOCK_YES);

            inbill.setLogTime(TimeTools.now());

            inbill.setMoneys(bean.getMoneys());

            inbill.setRefBillId(id);

            inbill.setStafferId(bean.getStafferId());

            inbill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

            inbill.setType(FinanceConstant.INBILL_TYPE_TRANSFER);

            saveInBillInner(inbill);

            // 更新源单状态
            bean.setStatus(FinanceConstant.OUTBILL_STATUS_END);

            bean.setLock(FinanceConstant.BILL_LOCK_YES);

            bean.setRefBillId(inbill.getId());

            bean.setDescription(bean.getDescription() + "" + "此单被驳回,由于锁定自动生成了同样金额的收款单:"
                                + inbill.getId());

            outBillDAO.updateEntityBean(bean);
        }

        return true;
    }
    
    /**
     * 客户预收拆分，一分为二
     * {@inheritDoc}
     */
	public String splitInBillBeanToOtherCustomer(User user, String id,
			String destCustomerId, String destOwerner, double newMoney)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id, destCustomerId, destOwerner);

        InBillBean bill = inBillDAO.find(id);

        if (bill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (newMoney <= 0.0)
        {
            throw new MYException("分拆金额不能小于0,请确认操作");
        }

        if (MathTools.compare(bill.getMoneys(), newMoney) < 0)
        {
            throw new MYException("金额不足,无法分拆,请确认操作");
        }

        bill.setMoneys(bill.getMoneys() - newMoney);

        String newId = commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX);

        // 
        bill.setDescription("最后预收移交 "+ newMoney +" 出去,新单" + newId);
		
		inBillDAO.updateEntityBean(bill);

        // 分拆后时间不能变(锁定状态不能变)
        bill.setId(newId);

        bill.setMoneys(newMoney);

        bill.setSrcMoneys(newMoney);

        bill.setUpdateId(0);

        bill.setCustomerId(destCustomerId);
        
        bill.setOwnerId(destOwerner);
        
        bill.setDescription("从收款单:"+id + " 收到预收(金额):"+ newMoney);

        saveInBillInner(bill);

        return bill.getId();
	}
    
    /**
     * @return the lOCK_FINANCE
     */
    public synchronized static boolean isLOCK_BILL() {
        return LOCK_BILL;
    }

    /**
     * @param lock_finance the lOCK_FINANCE to set
     */
    public synchronized static void setLOCK_BILL(boolean lock_bill) {
    	LOCK_BILL = lock_bill;
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
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
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
     * @return the statBankManager
     */
    public StatBankManager getStatBankManager()
    {
        return statBankManager;
    }

    /**
     * @param statBankManager
     *            the statBankManager to set
     */
    public void setStatBankManager(StatBankManager statBankManager)
    {
        this.statBankManager = statBankManager;
    }

    /**
     * @return the stockManager
     */
    public StockManager getStockManager()
    {
        return stockManager;
    }

    /**
     * @param stockManager
     *            the stockManager to set
     */
    public void setStockManager(StockManager stockManager)
    {
        this.stockManager = stockManager;
    }

    /**
     * @return the stockItemDAO
     */
    public StockItemDAO getStockItemDAO()
    {
        return stockItemDAO;
    }

    /**
     * @param stockItemDAO
     *            the stockItemDAO to set
     */
    public void setStockItemDAO(StockItemDAO stockItemDAO)
    {
        this.stockItemDAO = stockItemDAO;
    }

    /**
     * @return the paymentVSOutDAO
     */
    public PaymentVSOutDAO getPaymentVSOutDAO()
    {
        return paymentVSOutDAO;
    }

    /**
     * @param paymentVSOutDAO
     *            the paymentVSOutDAO to set
     */
    public void setPaymentVSOutDAO(PaymentVSOutDAO paymentVSOutDAO)
    {
        this.paymentVSOutDAO = paymentVSOutDAO;
    }

    /**
     * @return the stafferTransferDAO
     */
    public StafferTransferDAO getStafferTransferDAO()
    {
        return stafferTransferDAO;
    }

    /**
     * @param stafferTransferDAO
     *            the stafferTransferDAO to set
     */
    public void setStafferTransferDAO(StafferTransferDAO stafferTransferDAO)
    {
        this.stafferTransferDAO = stafferTransferDAO;
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

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}

	/**
     * @return the stafferVSCustomerDAO
     */
    public StafferVSCustomerDAO getStafferVSCustomerDAO()
    {
        return stafferVSCustomerDAO;
    }

    /**
     * @param stafferVSCustomerDAO
     *            the stafferVSCustomerDAO to set
     */
    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
    {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
    }
}
