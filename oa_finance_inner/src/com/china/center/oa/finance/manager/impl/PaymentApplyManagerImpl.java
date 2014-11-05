/**
 * File Name: PaymentApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBankBean;
import com.china.center.oa.client.dao.CustomerBankDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.AutoPayMonitorDAO;
import com.china.center.oa.finance.dao.BackPayApplyDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.PaymentVSOutDAO;
import com.china.center.oa.finance.helper.BillHelper;
import com.china.center.oa.finance.listener.PaymentApplyListener;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.PaymentApplyManager;
import com.china.center.oa.finance.vo.BatchSplitInBillWrap;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vo.PaymentVO;
import com.china.center.oa.finance.vs.PaymentVSOutBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.helper.LockHelper;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * PaymentApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-8
 * @see PaymentApplyManagerImpl
 * @since 3.0
 */
@Exceptional
public class PaymentApplyManagerImpl extends AbstractListenerManager<PaymentApplyListener> implements PaymentApplyManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log operationLog = LogFactory.getLog("opr");

    private PaymentApplyDAO paymentApplyDAO = null;

    private PaymentVSOutDAO paymentVSOutDAO = null;

    private CommonDAO commonDAO = null;

    private PaymentDAO paymentDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private InBillDAO inBillDAO = null;

    private BillManager billManager = null;

    private ParameterDAO parameterDAO = null;

    private BackPayApplyDAO backPayApplyDAO = null;

    private OutDAO outDAO = null;

    private OutManager outManager = null;

    private OutBalanceDAO outBalanceDAO = null;

    private AutoPayMonitorDAO autoPayMonitorDAO = null;
    
    private CustomerBankDAO customerBankDAO = null;
    
    private StafferVSCustomerDAO stafferVSCustomerDAO = null;
    
    private DutyDAO dutyDAO = null;
    
    private OutBillDAO outBillDAO = null;
    
    /**
     * default constructor
     */
    public PaymentApplyManagerImpl()
    {
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addPaymentApply(User user, PaymentApplyBean bean)
        throws MYException
    {
        return addPaymentApplyWithoutTrans(user, bean);
    }

	private boolean addPaymentApplyWithoutTrans(User user, PaymentApplyBean bean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean, bean.getVsList());

        checkAdd(user, bean);

        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_PAYMENTAPPLY_PREFIX));

        bean.setLogTime(TimeTools.now());

        bean.setStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        double promValue = 0.0d;
        
        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_AUTO)
        {
            promValue = bean.getBadMoney();
            bean.setBadMoney(0);
        }
        
        List<PaymentVSOutBean> vsList = bean.getVsList();

        // 校验是否是特殊单据
        for (PaymentVSOutBean vsItem : vsList)
        {
            vsItem.setId(commonDAO.getSquenceString20());

            vsItem.setParentId(bean.getId());

            vsItem.setLogTime(bean.getLogTime());

            // 处理每个节点(如果就是待稽核)
            fillEachItem(bean, vsItem);
        }

        // 原来的type
        int oldType = bean.getType();

        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_TEMP
                || bean.getType() == FinanceConstant.PAYAPPLY_TYPE_AUTO)
        {
            bean.setType(FinanceConstant.PAYAPPLY_TYPE_BING);
        }

        // 销售单绑定(销售与收款单直接关联)
        if (oldType == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
            for (PaymentVSOutBean vsItem : vsList)
            {
                // 校验是否一个销售单被多次绑定
                int count = paymentApplyDAO.countApplyByOutId(vsItem.getOutId());

                if (count > 0)
                {
                    throw new MYException("单据[%s]已经申请付款,请审批付款后再提交新的申请", vsItem.getOutId());
                }

                OutBean out = outDAO.find(vsItem.getOutId());

                // 更新预付金额
                InBillVO bill = inBillDAO.findVO(vsItem.getBillId());

                if (bill == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 2012后
                if (OATools.getManagerFlag() && out.getOutTime().compareTo("2012-01-01") >= 0)
                {
                    if ( !out.getDutyId().equals(bill.getDutyId()))
                    {
                        throw new MYException("勾款认领时不可跨纳税实体扣款,请确认操作");
                    }
                }

                if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
                {
                    throw new MYException("关联的收款单必须是预收,请确认操作");
                }

                bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

                inBillDAO.updateEntityBean(bill);
                
                // 票款一致标记未打的
                updatePayInvoiceData(bill.getDutyId(), bill.getMtype(), vsItem.getOutId(), vsItem.getOutBalanceId(), OutConstant.OUT_PAYINS_STATUS_APPROVE);
            }
        }

        // 界面上直接回款绑定销售和预收
        if (oldType == FinanceConstant.PAYAPPLY_TYPE_PAYMENT)
        {
            PaymentVO payment = paymentDAO.findVO(bean.getPaymentId());

            if (payment == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 指定认领的操作检查
            if ( !"0".equals(payment.getDestStafferId())
                && !StringTools.isNullOrNone(payment.getDestStafferId()))
            {
                if ( !user.getStafferId().equals(payment.getDestStafferId()))
                {
                    throw new MYException("此回款只能[%s]认领,请确认操作", user.getStafferName());
                }
            }

            for (PaymentVSOutBean vsItem : vsList)
            {
                if ( !StringTools.isNullOrNone(vsItem.getOutId()))
                {
                    // 校验是否一个销售单被多次绑定(因为委托单里面也是关联销售单的)
                    int count = paymentApplyDAO.countApplyByOutId(vsItem.getOutId());

                    if (count > 0)
                    {
                        throw new MYException("单据[%s]已经申请付款,请审批付款后再提交新的申请", vsItem.getOutId());
                    }
                    
                    // 票款一致标记未打的
                    updatePayInvoiceData(payment.getDutyId(), payment.getMtype(), vsItem.getOutId(), vsItem.getOutBalanceId(), OutConstant.OUT_PAYINS_STATUS_APPROVE);
                }
            }
        }

        double tt = bean.getMoneys();

        // 业务员勾款(销售单界面勾款)
        if (oldType == FinanceConstant.PAYAPPLY_TYPE_TEMP
                || oldType == FinanceConstant.PAYAPPLY_TYPE_AUTO)
        {
            // 只有一个销售单
            String outId = vsList.get(0).getOutId();

            // 校验是否一个销售单被多次申请付款
            int count = paymentApplyDAO.countApplyByOutId(outId);

            if (count > 0)
            {
                throw new MYException("单据[%s]已经申请付款,请审批付款后再提交新的申请", outId);
            }

            OutBean out = outDAO.find(outId);

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            String outBalanceId = vsList.get(0).getOutBalanceId();

            tt = 0.0d;

            double lastMoney = 0.0d;

            int type = 0;
            
            // 600
            if (StringTools.isNullOrNone(outBalanceId))
            {
                lastMoney = outManager.outNeedPayMoney(user, outId);
                
                if (oldType == FinanceConstant.PAYAPPLY_TYPE_AUTO)
                {
                    lastMoney = promValue;
                }
                
                // 票款一致标记已打 
                if (!StringTools.isNullOrNone(out.getPiDutyId()))
                {
                	type = 1;
                }
            }
            else
            {
                // 看结算单的钱
                OutBalanceBean outBal = outBalanceDAO.find(outBalanceId);

                if (outBal == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

        		// 减去结算单退货部分
            	double refTotal = outBalanceDAO.sumByOutBalanceId(outBalanceId);
                
                lastMoney = outBal.getTotal() - outBal.getPayMoney() - refTotal;
                
                // 票款一致标记已打
                if (!StringTools.isNullOrNone(outBal.getPiDutyId()))
                {
                	type = 1;
                }
            }

            double total = 0.0d;

            boolean remove = false;
            
            List<String> dutyList = new ArrayList<String>();

            for (Iterator iterator = vsList.iterator(); iterator.hasNext();)
            {
                PaymentVSOutBean vsItem = (PaymentVSOutBean)iterator.next();

                if (remove)
                {
                    iterator.remove();
                }

                // 1800
                total += vsItem.getMoneys();

                // 保证金额不超出
                if (MathTools.compare(total, lastMoney) > 0)
                {
                    // 拆分此单
                    billManager.splitInBillBeanWithoutTransactional(user, vsItem.getBillId(),
                        (total - lastMoney));

                    remove = true;
                }

                // 更新预付金额
                InBillVO bill = inBillDAO.findVO(vsItem.getBillId());

                if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
                {
                    throw new MYException("关联的收款单必须是预收,请确认操作");
                }

                // 2012后
                if (OATools.getManagerFlag() && out.getOutTime().compareTo("2012-01-01") >= 0
                    && false)
                {
                    if ( !out.getDutyId().equals(bill.getDutyId()))
                    {
                        throw new MYException("勾款认领时不可跨纳税实体扣款,请确认操作");
                    }
                }

                vsItem.setMoneys(bill.getMoneys());

                bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

                inBillDAO.updateEntityBean(bill);

                tt += bill.getMoneys();
                
                // reFor 确定用到预收的类型,进而确认对开票的限制 
                dutyList.add(bill.getDutyId());
            }
            
            // 票款标记未打的,要处理
            if (type == 0)
            {
            	processPayInvoice(outId, outBalanceId, dutyList, 0);
            }
        }

        // 预收转费用
        if (oldType == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
        {
            String billId = bean.getVsList().get(0).getBillId();

            checkHaveBackPay(billId);

            // 更新预付金额
            InBillBean bill = inBillDAO.find(billId);

            if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
            {
                throw new MYException("预收转费用必须是预收,请确认操作");
            }

            if ( !bill.getOwnerId().equals(user.getStafferId()))
            {
                throw new MYException("只能操作自己的单据,请确认操作");
            }

            bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

            inBillDAO.updateEntityBean(bill);
        }

        // 预收拆分,一分为二
        if (oldType == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
        {
            String billId = bean.getVsList().get(0).getBillId();

            // 更新预付金额
            InBillBean bill = inBillDAO.find(billId);

            if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
            {
                throw new MYException("预收拆分必须是预收,请确认操作");
            }

            bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

            inBillDAO.updateEntityBean(bill);
        }
        
        bean.setMoneys(tt);
        
        paymentApplyDAO.saveEntityBean(bean);

        paymentVSOutDAO.saveAllEntityBeans(vsList);
        
        saveFlowlog(user, bean);

        return true;
	}

	/**
	 * processPayInvoice
	 * @param vsList
	 * @param dutyList
	 * @throws MYException
	 */
	public void processPayInvoice(String outId, String outBalanceId,
			List<String> dutyList, int status) throws MYException
	{
		String piDutyId = PublicConstant.DEFAULR_DUTY_ID;
		int piMtype = PublicConstant.MANAGER_TYPE_COMMON;
		
		List<String> tempList = new ArrayList<String>();
		
		for (String dutyId : dutyList)
		{
			DutyBean duty = dutyDAO.find(dutyId);
			
			if (null != duty)
			{
				if (duty.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
				{
					piDutyId = "MANAGE";
					piMtype = PublicConstant.MANAGER_TYPE_MANAGER;
					
					break;
				}else{
					
					if (!duty.getId().equals(PublicConstant.DEFAULR_DUTY_ID))
					{
						if (!tempList.contains(duty.getId()))
						{
							tempList.add(duty.getId());
						}
					}
				}
			}
		}
		
		// 只有一种非永银文化的普通类
		if (piMtype == PublicConstant.MANAGER_TYPE_COMMON)
		{
			 if (tempList.size() > 0)
		     {
		     	if (tempList.size() == 1)
		     	{
		     		piDutyId = tempList.get(0);
		     	}else{
		     		piDutyId = PublicConstant.DEFAULR_DUTY_ID;
		     	}
		     }else
		     {
		    	 piDutyId = "COMMON";
		     }
		}

		updatePayInvoiceData(piDutyId, piMtype, outId, outBalanceId, status);
	}

	/**
	 * 票款一致标记未打的
	 * @param dutyId
	 * @param mtype
	 * @param outId
	 * @param outBalanceId
	 * @param status
	 * @throws MYException
	 */
	private void updatePayInvoiceData(final String dutyId, final int mtype, final String outId, final String outBalanceId, final int status)
			throws MYException
	{
		OutBean out = outDAO.find(outId);

		// 票款一致标记未打的
		if (!StringTools.isNullOrNone(outBalanceId))
		{
			OutBalanceBean balanceBean = outBalanceDAO.find(outBalanceId);
			
			// 勾款时，发现还没有开票，销售单中打上勾过款的标记
			if (StringTools.isNullOrNone(balanceBean.getPiDutyId()))
		    {
		        // 管理
		        if (mtype == PublicConstant.MANAGER_TYPE_MANAGER)
		        {
		        	outBalanceDAO.updatePayInvoiceData(balanceBean.getId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_MANAGER, 
		        			dutyId, status); // OutConstant.OUT_PAYINS_STATUS_APPROVE
		        }
		        else if (dutyId.equals(PublicConstant.DEFAULR_DUTY_ID)) // 永银文化
		        {
		        	outBalanceDAO.updatePayInvoiceData(balanceBean.getId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_COMMON, 
		        			dutyId, status);
		        }else{ // 非永银文化的普通
		        	outBalanceDAO.updatePayInvoiceData(balanceBean.getId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_COMMON, 
		        			dutyId, status);
		        }
		    }
			
		}else{
			if (StringTools.isNullOrNone(out.getPiDutyId()))
		    {
		        // 管理
		        if (mtype == PublicConstant.MANAGER_TYPE_MANAGER)
		        {
		        	outDAO.updatePayInvoiceData(out.getFullId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_MANAGER, 
		        			dutyId, status);
		        }
		        else if (dutyId.equals(PublicConstant.DEFAULR_DUTY_ID)) // 永银文化
		        {
		        	outDAO.updatePayInvoiceData(out.getFullId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_COMMON, 
		        			dutyId, status);
		        }else{ // 非永银文化的普通
		        	outDAO.updatePayInvoiceData(out.getFullId(), 
		        			OutConstant.OUT_PAYINS_TYPE_PAY, PublicConstant.MANAGER_TYPE_COMMON, 
		        			dutyId, status);
		        }
		    }
		}
	}
    
    /**
     * addPaymentApply5
     */
	@Transactional(rollbackFor = MYException.class)
    public boolean addPaymentApply5(User user, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(user, bean, bean.getVsList());
	    
	    // 预收拆分
		String newBillId = billManager.splitInBillBeanToOtherCustomer(user, bean.getOriBillId(), bean.getCustomerId(), bean.getStafferId(), bean.getMoneys());
	    
		bean.getVsList().get(0).setBillId(newBillId);
		
	    return addPaymentApplyWithoutTrans(user, bean);
	}
	
	/**
	 * addPaymentApply6 
	 * 批量勾款  多个预收  <-> 应收
	 */
	@Transactional(rollbackFor = MYException.class)
    public boolean addPaymentApply6(User user, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(user, bean, bean.getVsList());
	    
	    // 获取客户所有的预收，再次检查金额是否满足
	    ConditionParse condtion = setCondition(bean);

		List<InBillBean> billList = inBillDAO.queryEntityBeansByCondition(condtion);
	    
	    if (ListTools.isEmptyOrNull(billList))
	    {
	    	throw new MYException("没有满足的预收，请确认");
	    }
	    
	    List<PaymentVSOutBean> vsList = bean.getVsList();
	    
	    for (PaymentVSOutBean each : vsList)
	    {
            // 一个销售单 对应多预收
            String outId = each.getOutId();

            // 校验是否一个销售单被多次申请付款
            int count = paymentApplyDAO.countApplyByOutId(outId);

            if (count > 0)
            {
                throw new MYException("单据[%s]已经申请付款,请审批付款后再提交新的申请", outId);
            }

            OutBean out = outDAO.find(outId);

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            String outBalanceId = each.getOutBalanceId();

            double lastMoney = 0.0d;
            double moneys = 0.0d;

            // 600
            if (StringTools.isNullOrNone(outBalanceId))
            {
                lastMoney = outManager.outNeedPayMoney(user, outId);
            }
            else
            {
                // 看结算单的钱
                OutBalanceBean outBal = outBalanceDAO.find(outBalanceId);

                if (outBal == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

        		// 减去结算单退货部分
            	double refTotal = outBalanceDAO.sumByOutBalanceId(outBalanceId);
                
                lastMoney = outBal.getTotal() - outBal.getPayMoney() - refTotal;
            }
            
            moneys = lastMoney;

            List<PaymentVSOutBean> vs = new ArrayList<PaymentVSOutBean>();
            
            // 销售单一个一个的勾
        	for (Iterator<InBillBean> iterator = billList.iterator(); iterator.hasNext();)
            {
        		InBillBean bill = iterator.next();
        		
        		if (out.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER 
        				&& bill.getMtype() != PublicConstant.MANAGER_TYPE_MANAGER)
        		{
        			continue;
        		}
        		
        		if (MathTools.compare(bill.getMoneys(), lastMoney) > 0)
                {
                    // 拆分此单  移交lastMoney出去使用
                    String newId = billManager.splitInBillBeanWithoutTransactional(user, bill.getId(), lastMoney);

                    bill.setMoneys(bill.getMoneys() - lastMoney);
                    
                    InBillBean newBill = inBillDAO.find(newId);
                    
                    newBill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);
                    
                    inBillDAO.updateEntityBean(newBill);
                    
                    each.setPaymentId(bill.getPaymentId());
                    each.setMoneys(lastMoney);
                    each.setBillId(newId);
                    
                    vs.add(each);
                    
                    lastMoney = 0.0d;
                    
                    break;
                    
                }else{
                	 // 原预收 状态置为 关联销售
                    bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);
                    
                    inBillDAO.updateEntityBean(bill);
                    
                    iterator.remove();
                    
                    lastMoney -= bill.getMoneys();
                    
                    each.setPaymentId(bill.getPaymentId());
                    each.setMoneys(bill.getMoneys());
                    each.setBillId(bill.getId());
                    
                    vs.add(each);
                    
                    if (lastMoney == 0.0d)
                    {
                    	break;
                    }
                }
            }
        
        	// 预收不够
        	if (lastMoney > 0)
        	{
        		throw new MYException("销售单[%s]勾款时发现预收不足，请检查", out.getFullId());
        	}else{
        		 // 生成收款申请数据 PaymentApplyBean
                PaymentApplyBean apply = new PaymentApplyBean();
                
                BeanUtil.copyProperties(apply, bean);
                
                apply.setPaymentId("");
                apply.setLogTime(TimeTools.now());
                apply.setMoneys(moneys);
                
                apply.setVsList(vs);
                
                // save
                savePaymentApplyInner(user, apply);
        	}
        }
	    
	    return true;
	}
	
	private void savePaymentApplyInner(User user, PaymentApplyBean bean)
	{
		bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_PAYMENTAPPLY_PREFIX));

        bean.setLogTime(TimeTools.now());

        bean.setStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        List<PaymentVSOutBean> vsList = bean.getVsList();

        // 校验是否是特殊单据
        for (PaymentVSOutBean vsItem : vsList)
        {
            vsItem.setId(commonDAO.getSquenceString20());

            vsItem.setParentId(bean.getId());

            vsItem.setLogTime(bean.getLogTime());

            // 处理每个节点(如果就是待稽核)
            fillEachItem(bean, vsItem);
        }

        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_TEMP
                || bean.getType() == FinanceConstant.PAYAPPLY_TYPE_AUTO)
        {
            bean.setType(FinanceConstant.PAYAPPLY_TYPE_BING);
        }
        
        paymentApplyDAO.saveEntityBean(bean);

        paymentVSOutDAO.saveAllEntityBeans(vsList);

        saveFlowlog(user, bean);
	}

	private ConditionParse setCondition(PaymentApplyBean bean)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("InBillBean.ownerId", "=", bean.getStafferId());

		condtion.addCondition("InBillBean.customerId", "=", bean.getCustomerId());

		condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
		
		condtion.addCondition(" and InBillBean.moneys >= 0.01");
		
		condtion.addCondition("order by InBillBean.logTime desc");
		return condtion;
	}
	
    /**
     * 处理每个节点
     * 
     * @param bean
     * @param vsItem
     */
    private void fillEachItem(PaymentApplyBean bean, PaymentVSOutBean vsItem)
    {
        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
        {
            return;
        }

        String dutyId = "";

        String bdutyId = "";

        if ( !StringTools.isNullOrNone(vsItem.getOutId()))
        {
            OutBean out = outDAO.find(vsItem.getOutId());

            if (out != null)
            {
                dutyId = out.getDutyId();
            }
        }

        if ( !StringTools.isNullOrNone(vsItem.getOutBalanceId()))
        {
            OutBalanceBean outBalanceBean = outBalanceDAO.find(vsItem.getOutBalanceId());

            if (outBalanceBean != null)
            {
                OutBean out = outDAO.find(outBalanceBean.getOutId());

                if (out != null)
                {
                    dutyId = out.getDutyId();
                }
            }
        }

        // 客户预收直接返回(没有任何关联)
        if (StringTools.isNullOrNone(dutyId))
        {
            return;
        }

        if ( !StringTools.isNullOrNone(vsItem.getBillId()))
        {
            InBillVO inbill = inBillDAO.findVO(vsItem.getBillId());

            if (inbill != null)
            {
                bdutyId = inbill.getDutyId();
            }
        }

        if ( !StringTools.isNullOrNone(vsItem.getPaymentId()))
        {
            PaymentVO paymentBean = paymentDAO.findVO(vsItem.getPaymentId());

            if (paymentBean != null)
            {
                bdutyId = paymentBean.getDutyId();
            }
        }

        // 关注单据
        if ( !dutyId.equals(bdutyId))
        {
            bean.setVtype(PublicConstant.VTYPE_SPECIAL);

            bean.setStatus(FinanceConstant.PAYAPPLY_STATUS_CHECK);
        }
    }

    /**
     * 是否存在退款申请
     * 
     * @param billId
     * @throws MYException
     */
    private void checkHaveBackPay(String billId)
        throws MYException
    {
        // 一个单子只能存在一个申请
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("BackPayApplyBean.billId", "=", billId);

        condition.addIntCondition("BackPayApplyBean.status", "<",
            BackPayApplyConstant.STATUS_REJECT);

        int countByCondition = backPayApplyDAO.countByCondition(condition.toString());

        if (countByCondition > 0)
        {
            throw new MYException("此预收存在未结束的退款申请,请确认操作");
        }
    }

    private void saveFlowlog(User user, PaymentApplyBean bean)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(bean.getId());

        log.setActor(user.getStafferName());

        log.setOprMode(PublicConstant.OPRMODE_SUBMIT);

        log.setDescription(user.getStafferName() + "提交付款申请");

        log.setLogTime(TimeTools.now());

        log.setAfterStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        log.setPreStatus(bean.getStatus());

        flowLogDAO.saveEntityBean(log);
    }

    private void checkAdd(User user, PaymentApplyBean bean)
        throws MYException
    {
        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
            return;
        }

        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_TEMP
                || bean.getType() == FinanceConstant.PAYAPPLY_TYPE_AUTO)
        {
            return;
        }

        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
        {
            return;
        }
        
        if (bean.getType() == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
        {
            return;
        }

        PaymentBean payment = paymentDAO.find(bean.getPaymentId());

        if (payment == null)
        {            
            throw new MYException("数据错误,请确认操作");
        }

        if ( !payment.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的回款单,请确认操作");
        }

        if (payment.getUseall() == FinanceConstant.PAYMENT_USEALL_END)
        {
            throw new MYException("回款单已经全部被使用,请确认操作");
        }

        List<PaymentVSOutBean> vsList = bean.getVsList();

        double total = 0.0d;

        for (PaymentVSOutBean vsItem : vsList)
        {
            total += vsItem.getMoneys();
        }

        bean.setMoneys(total);

        double hasUsed = inBillDAO.sumByPaymentId(bean.getPaymentId());

        if (hasUsed + bean.getMoneys() > payment.getMoney())
        {
            throw new MYException("回款使用金额溢出,总金额[%.2f],已使用金额[%.2f],本次申请金额[%.2f],请确认操作", payment
                .getMoney(), hasUsed, bean.getMoneys());
        }
    }

    private void checkUpdate(PaymentApplyBean bean)
        throws MYException
    {
        PaymentBean payment = paymentDAO.find(bean.getPaymentId());

        if (payment == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !payment.getStafferId().equals(bean.getStafferId()))
        {
            throw new MYException("只能操作自己的回款单,请确认操作");
        }

        if (payment.getUseall() == FinanceConstant.PAYMENT_USEALL_END)
        {
            throw new MYException("回款单已经使用结束,请确认操作");
        }

        if (bean.getStatus() != FinanceConstant.PAYAPPLY_STATUS_REJECT)
        {
            throw new MYException("只有驳回才可以被修改,请确认操作");
        }

        double hasUsed = inBillDAO.sumByPaymentId(bean.getPaymentId());

        if (MathTools.compare(hasUsed + bean.getMoneys(), payment.getMoney()) > 0)
        {
            throw new MYException("回款使用金额溢出,总金额[%.2f],已使用金额[%.2f],本次申请金额[%.2f],请确认操作", payment
                .getMoney(), hasUsed, bean.getMoneys());
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deletePaymentApply(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PaymentApplyBean payment = paymentApplyDAO.find(id);

        if (payment == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (payment.getStatus() != FinanceConstant.PAYAPPLY_STATUS_REJECT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        paymentApplyDAO.deleteEntityBean(id);

        paymentVSOutDAO.deleteEntityBeansByFK(id);

        flowLogDAO.deleteEntityBeansByFK(id);

        operationLog.info(user.getName() + "删除了PaymentApply:" + payment);

        return true;
    }

    /**
     * CORE 回款认领等的审核通过(回款转预收/销售单绑定(预收转应收)/预收转费用)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passPaymentApply(User user, String id, String reason,String description)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PaymentApplyBean apply = checkPass(id);
        apply.setStatus(FinanceConstant.PAYAPPLY_STATUS_PASS);

        paymentApplyDAO.updateEntityBean(apply);

        PaymentBean payment = paymentDAO.find(apply.getPaymentId());
        
        // CORE 生成收款单,更新销售单和委托清单付款状态/或者转成费用
        createInbill(user, apply, payment, reason,description);
        
        // 更新回款单的状态和使用金额
        updatePayment(apply);
        
        // TAX_ADD 回款转预收/销售单绑定(预收转应收)/预收转费用 通过
        Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

        for (PaymentApplyListener listener : listenerMapValues)
        {
            listener.onPassBean(user, apply);
        }
        
        savePassLog(user, FinanceConstant.PAYAPPLY_STATUS_INIT, apply, reason);
        
        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean passCheck(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PaymentApplyBean apply = paymentApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        //此处防止多人操作时，有人操作界面中缓存数据
        if (apply.getStatus() != FinanceConstant.PAYAPPLY_STATUS_CHECK){
            
            throw new MYException("该收款单已被其他人处理过");
        }
        
        apply.setStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        paymentApplyDAO.updateEntityBean(apply);

        savePassLog(user, FinanceConstant.PAYAPPLY_STATUS_CHECK, apply, reason);

        return true;
    }

    /**
     * createInbill
     * 
     * @param user
     * @param apply
     * @param payment
     * @throws MYException
     */
    private void createInbill(User user, PaymentApplyBean apply, PaymentBean payment, String reason,String description)
        throws MYException
    {
        List<PaymentVSOutBean> vsList = apply.getVsList();
        
        for (PaymentVSOutBean item : vsList)
        {
            if (item.getMoneys() == 0.0d)
            {
                continue;
            }

            // 生成收款单(回款转预收)
            if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_PAYMENT)
            {
                // 回款转收款通过,增加收款单
                saveBillInner(user, apply, payment, item, reason,description);
            }
            // 预收转费用
            else if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
            {
                // 把预收转成费用,且新生成的需要核对
                String billId = item.getBillId();

                // 更新预付金额
                InBillBean bill = inBillDAO.find(billId);

                if (bill.getStatus() != FinanceConstant.INBILL_STATUS_PREPAYMENTS)
                {
                    throw new MYException("预收转费用必须是关联申请态,请确认操作");
                }

                bill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

                bill.setOutId("");

                bill.setOutBalanceId("");

                // 变成未核对的状态
                bill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);

                // 转成费用的收款单
                bill.setType(FinanceConstant.INBILL_TYPE_FEE);

                bill.setDescription(bill.getDescription() + " " + description);

                inBillDAO.updateEntityBean(bill);
            }
            // 预收拆分
            else if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
            {
                // 把预收转成费用,且新生成的需要核对
                String billId = item.getBillId();

                // 更新预付金额
                InBillBean bill = inBillDAO.find(billId);

                if (bill.getStatus() != FinanceConstant.INBILL_STATUS_PREPAYMENTS)
                {
                    throw new MYException("预收拆分必须是关联申请态,请确认操作");
                }

                bill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

                bill.setOutId("");

                bill.setOutBalanceId("");

                // 变成未核对的状态
                bill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);

                // 转成销售收入的收款单
                bill.setType(FinanceConstant.INBILL_TYPE_SAILOUT);

                bill.setDescription(bill.getDescription() + " " + apply.getDescription());

                inBillDAO.updateEntityBean(bill);
            }
            else
            {
                // 绑定销售单(回款转预收&&预收转应收)
                InBillBean bill = inBillDAO.find(item.getBillId());

                if (bill == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                if (bill.getStatus() == FinanceConstant.INBILL_STATUS_PAYMENTS)
                {
                    throw new MYException("收款单状态错误,请确认操作");
                }

                // 这里防止并行对销售单操作
                synchronized (LockHelper.getLock(item.getOutId()))
                {
                    OutBean outBean = outDAO.find(item.getOutId());

                    if (outBean == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }

                    if ( !OutHelper.canFeeOpration(outBean))
                    {
                        throw new MYException("销售单状态错误,请确认操作");
                    }

                    if ( !StringTools.isNullOrNone(outBean.getChecks()))
                    {
                        bill.setDescription(bill.getDescription() + "<br>销售单核对信息:"
                                            + outBean.getChecks() + "<br>审批意见(" + item.getOutId()
                                            + "):" + reason  + " " + description);
                    }

                    if (bill.getCheckStatus() == PublicConstant.CHECK_STATUS_END)
                    {
                        bill.setDescription(bill.getDescription() + "<br>与销售单关联付款所以重置核对状态,原核对信息:"
                                            + bill.getChecks() + "<br>审批意见(" + item.getOutId()
                                            + "):" + reason  + " " + description);
                    }
                    else
                    {
                        bill.setDescription(bill.getDescription() + "<br>审批意见(" + item.getOutId()
                                            + "):" + reason  + " " + description);
                    }

                    if (BillHelper.isPreInBill(bill))
                    {
                        // 这里需要把收款单的状态变成未核对
                        BillHelper.initInBillCheckStatus(bill);
                    }

                    bill.setOutId(item.getOutId());

                    bill.setOutBalanceId(item.getOutBalanceId());

                    bill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

                    // 谁审批的就是谁的单子
                    bill.setStafferId(user.getStafferId());

                    // 用坏账勾款 标记  借用createType add by f 2012-8-2
                    if (bill.getType()== FinanceConstant.INBILL_TYPE_BADOUT){
                        
                        bill.setCreateType(FinanceConstant.BILL_CREATETYPE_HANDBADOUT);
                    }
                    
                    inBillDAO.updateEntityBean(bill);
                }
            }
        }
	        
        // 更新收款单ID到申请里面
        paymentVSOutDAO.updateAllEntityBeans(vsList);

        // 销售单绑定
        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
            String outId = vsList.get(0).getOutId();

            String outBalanceId = vsList.get(0).getOutBalanceId();

            // 可能存在坏账处理
            processOut(user, apply, outId, outBalanceId);
        }

        // 里面存在多个销售单或者委托清单(回款转收款 )/这里没有坏账
        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_PAYMENT)
        {
            // 指定认领的操作检查
            if ( !"0".equals(payment.getDestStafferId())
                && !StringTools.isNullOrNone(payment.getDestStafferId()))
            {
                if ( !apply.getStafferId().equals(payment.getDestStafferId()))
                {
                    throw new MYException("此回款只能[%s]认领,请确认操作", user.getStafferName());
                }
            }

            // 手续费
            if (payment.getHandling() > 0)
            {
                int maxFee = parameterDAO.getInt(SysConfigConstant.MAX_RECVIVE_FEE);

                OutBillBean out = new OutBillBean();

                out.setBankId(payment.getBankId());
                out.setDescription("回款转收款自动生成手续费:" + payment.getId() + ".回款金额:"
                                   + MathTools.formatNum(payment.getMoney()));
                out.setLocationId(user.getLocationId());
                out.setMoneys(payment.getHandling());

                if (payment.getMoney() < maxFee)
                {
                    // 个人承担这个费用
                    out.setOwnerId(apply.getStafferId());
                }

                out.setType(FinanceConstant.OUTBILL_TYPE_HANDLING);

                out.setProvideId(payment.getCustomerId());

                out.setStafferId(user.getStafferId());

                // REF
                out.setStockId(payment.getId());

                billManager.addOutBillBeanWithoutTransaction(user, out);
            }

            // 处理销售的回款和付款绑定的核心
            for (PaymentVSOutBean item : vsList)
            {
                if (StringTools.isNullOrNone(item.getOutId()))
                {
                    continue;
                }

                String outId = item.getOutId();

                String outBalanceId = item.getOutBalanceId();

                // 这里肯定是没有坏账的(从设计上就保证)
                apply.setBadMoney(0);
                processOut(user, apply, outId, outBalanceId);
            }
        }
    }

    private void saveBillInner(User user, PaymentApplyBean apply, PaymentBean payment,
                               PaymentVSOutBean item, String reason,String description)
        throws MYException
    {
        InBillBean inBean = new InBillBean();

        inBean.setBankId(payment.getBankId());

        inBean.setCustomerId(apply.getCustomerId());

        inBean.setLocationId(user.getLocationId());

        inBean.setLogTime(TimeTools.now());

        inBean.setMoneys(item.getMoneys());

        inBean.setSrcMoneys(item.getMoneys());
        
        if (StringTools.isNullOrNone(item.getOutId()))
        {
            inBean.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

            inBean.setDescription("自动生成预收收款单,从回款单:" + payment.getId() + ",未关联销售单.审批意见:" + reason + " " + description);
        }
        else
        {
        	// 控制如果销售单正在对账,则不能操作
        	OutBean out = outDAO.find(item.getOutId());
        	
        	if (out.getFeedBackCheck() == 1)
        	{
        		throw new MYException("销售单[%s]正在对账中，不能勾款", item.getOutId());
        	}
        	
            inBean.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

            inBean.setDescription("自动生成收款单,从回款单:" + payment.getId() + ",关联的销售单:" + item.getOutId()
                                  + ".审批意见:" + reason + " " + description);
        }

        inBean.setOutId(item.getOutId());

        inBean.setOutBalanceId(item.getOutBalanceId());

        inBean.setOwnerId(apply.getStafferId());

        inBean.setPaymentId(payment.getId());

        inBean.setStafferId(user.getStafferId());

        inBean.setType(FinanceConstant.INBILL_TYPE_SAILOUT);

        billManager.addInBillBeanWithoutTransaction(user, inBean);

        item.setBillId(inBean.getId());
    }

    public void processOut(User user, String outId, String outBalanceId)
    throws MYException
	{
    	PaymentApplyBean apply = new PaymentApplyBean();
    	
    	apply.setBadMoney(0);
    	
    	processOut(user, apply, outId, outBalanceId);
	}
    /**
     * CORE 处理销售的回款和付款绑定的核心(关联多个就是多次)
     * 
     * @param user
     * @param apply
     * @param outId
     * @param outBalanceId
     * @throws MYException
     */
    private void processOut(User user, PaymentApplyBean apply, String outId, String outBalanceId)
        throws MYException
    {
        synchronized (LockHelper.getLock(outId))
        {
            OutBean out = outDAO.find(outId);
            
            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if ( !OutHelper.canFeeOpration(out))
            {
                throw new MYException("销售单状态错误,请确认操作");
            }

            // 看看销售单是否溢出
            double hasPay = inBillDAO.sumByOutId(outId);

            // 有坏账的存在
            if (apply.getBadMoney() != 0)
            {
                out.setBadDebts(apply.getBadMoney());

                outDAO.modifyBadDebts(outId, apply.getBadMoney());
            }

            out.setHadPay(hasPay);

            // 更新已经支付的金额
            outDAO.updateHadPay(outId, hasPay);

            // 先把委托代销的全部搞定
            if ( !StringTools.isNullOrNone(outBalanceId))
            {
                // 更新委托清单
                OutBalanceBean outBal = outBalanceDAO.find(outBalanceId);

                // 看看委托代销是否溢出
                double hasOutBalancePay = inBillDAO.sumByOutBalanceId(outBal.getId());

                // 发现支付的金额过多
                if (MathTools.compare(hasOutBalancePay, outBal.getTotal()) > 0)
                {
                    throw new MYException("委托清单[%s]的总金额[%.2f],当前已付金额[%.2f]付款金额超出销售金额", outBal
                        .getId(), outBal.getTotal(), hasOutBalancePay);
                }

                outBalanceDAO.updateHadPay(outBal.getId(), hasOutBalancePay);

                // 如果全部支付就自动表示收款
                if (MathTools.equal2(outBal.getTotal(), hasOutBalancePay))
                {
                    outBalanceDAO.updateHadPay(outId, OutConstant.PAY_YES);
                }
                
                // 票款一致状态变化更新
                if (outBal.getPiType() == OutConstant.OUT_PAYINS_TYPE_PAY && outBal.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
                	outBalanceDAO.updatePayInvoiceStatus(outBal.getId(), OutConstant.OUT_PAYINS_STATUS_FINISH);
                
            }else{ // 增加票款一致状态变化更新
            	if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_PAY && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
            		outDAO.updatePayInvoiceStatus(outId, OutConstant.OUT_PAYINS_STATUS_FINISH);
            }

            tryUpdateOutPayStatus(user, out);
        }
    }

    /**
     * 尝试更新销售单的付款状态 *
     * 
     * @param user
     * @param out
     * @throws MYException
     */
    private void tryUpdateOutPayStatus(User user, OutBean out)
        throws MYException
    {
        // 看看销售单是否可以结帐
        ResultBean result = outManager.checkOutPayStatus(user, out);

        // 如果全部支付就自动表示收款
        if (result.getResult() == 0)
        {
        	// 尝试全部付款
            outManager.payOutWithoutTransactional(user, out.getFullId(), "付款申请通过");        	
        }

        // 回款超出了限制(非法)
        if (result.getResult() == -1)
        {
            throw new MYException(result.getMessage());
        }

        // 付款未完全,逻辑是正常的
        if (result.getResult() == 1)
        {
            _logger.info(result.getMessage());
        }
    }

    private void updatePayment(PaymentApplyBean apply)
    {
        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
            return;
        }

        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
        {
            return;
        }

        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
        {
            return;
        }
        
        PaymentBean payment = paymentDAO.find(apply.getPaymentId());

        double hasUsed = inBillDAO.sumByPaymentId(apply.getPaymentId());

        payment.setUseMoney(hasUsed);

        if (MathTools.compare(hasUsed, payment.getMoney()) >= 0)
        {
            payment.setUseall(FinanceConstant.PAYMENT_USEALL_END);
            payment.setUpdateTime(TimeTools.now());
        }
        else
        {
            payment.setUseall(FinanceConstant.PAYMENT_USEALL_INIT);
        }

        paymentDAO.updateEntityBean(payment);
        
        // 认领通过时,增加回款来源信息记录
        String fromer = payment.getFromer();
        String fromerNo = payment.getFromerNo();
        
        if (!StringTools.isNullOrNone(fromer) && !StringTools.isNullOrNone(fromerNo))
        {
        	CustomerBankBean custBank = customerBankDAO.findByUnique(fromer, fromerNo);
            
            if (null == custBank)
            {
            	CustomerBankBean cbBean = new CustomerBankBean();
            	
            	cbBean.setCustomerId(payment.getCustomerId());
            	cbBean.setAccountName(fromer);
            	cbBean.setAccountNO(fromerNo);
            	
            	customerBankDAO.saveEntityBean(cbBean);
            	
            }else{
            	if (!custBank.getCustomerId().equals(payment.getCustomerId()))
            	{
            		custBank.setCustomerId(payment.getCustomerId());
            		
            		customerBankDAO.updateEntityBean(custBank);
            	}
            }
        }
    }

    private PaymentApplyBean checkPass(String id)
        throws MYException
    {
        PaymentApplyBean apply = paymentApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (apply.getStatus() == FinanceConstant.PAYAPPLY_STATUS_PASS)
        {
            throw new MYException("状态不正确,请确认操作");
        }

        List<PaymentVSOutBean> vsList = paymentVSOutDAO.queryEntityBeansByFK(id);

        double total = 0.0d;

        for (PaymentVSOutBean vsItem : vsList)
        {
            total += vsItem.getMoneys();
        }

        apply.setVsList(vsList);

        apply.setMoneys(total);

        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
            return apply;
        }

        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
        {
            return apply;
        }

        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
        {
            return apply;
        }        
        
        // 检查是否溢出
        PaymentBean payment = paymentDAO.find(apply.getPaymentId());

        if (payment == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        double hasUsed = inBillDAO.sumByPaymentId(apply.getPaymentId());

        if (MathTools.compare(hasUsed + apply.getMoneys(), payment.getMoney()) > 0)
        {
            throw new MYException("回款使用金额溢出,总金额[%.2f],已使用金额[%.2f],本次申请金额[%.2f],请确认操作", payment
                .getMoney(), hasUsed, apply.getMoneys());
        }

        return apply;
    }

    private void savePassLog(User user, int oldStatus, PaymentApplyBean apply, String reason)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(apply.getId());

        log.setActor(user.getStafferName());

        log.setOprMode(PublicConstant.OPRMODE_PASS);

        log.setDescription(reason);

        log.setLogTime(TimeTools.now());

        log.setPreStatus(oldStatus);

        log.setAfterStatus(apply.getStatus());

        flowLogDAO.saveEntityBean(log);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectPaymentApply(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PaymentApplyBean apply = paymentApplyDAO.find(id);
        
        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (apply.getStatus() == FinanceConstant.PAYAPPLY_STATUS_PASS)
        {
            throw new MYException("状态错误,请确认操作");
        }

        // 增加促销退货产生的勾款申请, 强制通过.
        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_BING)
        {
        	String desc = apply.getDescription();
        	
        	if (desc.indexOf("参与了促销活动") != -1)
        	{
        		throw new MYException("促销单退货自动生成的勾款申请,不允许驳回,请通过");
        	}
        }
        
        apply.setStatus(FinanceConstant.PAYAPPLY_STATUS_REJECT);

        paymentApplyDAO.updateEntityBean(apply);
        
        List<PaymentVSOutBean> vsList = paymentVSOutDAO.queryEntityBeansByFK(id);

        if(null != vsList)
        {
	        for (PaymentVSOutBean item : vsList)
	        {
	            // 如果是关联收款单则取消/预收转费用
	            if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_BING
	                || apply.getType() == FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE)
	            {
	                InBillBean bill = inBillDAO.find(item.getBillId());
	
	                if (bill != null)
	                {
	                	if (bill.getStatus() != FinanceConstant.INBILL_STATUS_PREPAYMENTS)
	                	{
	                		continue;
	                	}
	                	
	                    bill.setOutId("");
	
	                    bill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
	
	                    bill.setDescription(bill.getDescription() + "<br>驳回[" + apply.getId()
	                                        + "]状态重置到预收");
	
	                    inBillDAO.updateEntityBean(bill);
	                }
	            }
	            
	            // 当勾款驳回时,对于自己打的标记,要初始为原值
	            if (!StringTools.isNullOrNone(item.getOutId()))
	            {
	            	OutBean out = outDAO.find(item.getOutId());
	            	
	            	if (out == null)
	                {
	                    throw new MYException("数据错误,请确认操作");
	                }
	            	
	            	// 委托结算
	            	if (!StringTools.isNullOrNone(item.getOutBalanceId()))
	            	{
	            		OutBalanceBean balance = outBalanceDAO.find(item.getOutBalanceId());
	            		
	            		if (null == balance){
	            			throw new MYException("数据错误,请确认操作");
	            		}
	            		
	            		if (balance.getPiType() == OutConstant.OUT_PAYINS_TYPE_PAY && balance.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
	            		{
	            			outBalanceDAO.initPayInvoiceData(balance.getId());
	            		}
	            		
	            	}else{
	            		if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_PAY && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
	            		{
	            			outDAO.initPayInvoiceData(out.getFullId());
	            		}
	            	}
	            }
	        }
	        
	        // 拆分驳回
	        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT)
	        {
	        	// 把新产生的（状态为关联申请）的收款单置为原来客户与业务员，且为预收
	        	String newBillId = vsList.get(0).getBillId();
	        	
	        	String oriBillId = apply.getOriBillId();
	        	
	        	InBillBean bill = inBillDAO.find(newBillId);
	        	
	            if (bill != null)
	            {
	            	if (bill.getStatus() == FinanceConstant.INBILL_STATUS_PREPAYMENTS)
                	{
	            		bill.setOutId("");

	 	                bill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
	 	                
	 	                bill.setCustomerId(apply.getOriCustomerId());
	 	                
	 	                bill.setOwnerId(apply.getOriStafferId());

	 	                bill.setDescription(bill.getDescription() + "<br>预收拆分驳回[" + apply.getId()
	 	                                    + "]状态重置到原客户预收");

	 	                inBillDAO.updateEntityBean(bill);
                	}
	            }
	            
	            bill = inBillDAO.find(oriBillId);
	            
	            if (bill != null)
	            {
	            	bill.setDescription(bill.getDescription() + "<br>预收拆分驳回[" + apply.getId()
	                        + "]状态重置到预收");

	            	inBillDAO.updateEntityBean(bill);
	            }
	        }
	        
        }       
        
        // 这里驳回需要单据复原
        if (apply.getType() == FinanceConstant.PAYAPPLY_TYPE_PAYMENT)
        {
            PaymentBean pay = paymentDAO.find(apply.getPaymentId());

            if (pay != null && pay.getUseall() == FinanceConstant.PAYMENT_USEALL_INIT)
            {
                pay.setStafferId("");

                pay.setCustomerId("");

                pay.setStatus(FinanceConstant.PAYMENT_STATUS_INIT);

                pay.setUpdateTime("");

                paymentDAO.updateEntityBean(pay);
            }
        }

        saveRejectLog(user, apply, reason);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checkPaymentApply(User user, String id, String checks, String refId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PaymentApplyBean apply = paymentApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        apply.setCheckStatus(PublicConstant.CHECK_STATUS_END);
        apply.setChecks(checks + " [" + TimeTools.now() + ']');
        apply.setCheckrefId(refId);

        paymentApplyDAO.updateEntityBean(apply);

        return true;
    }

    private void saveRejectLog(User user, PaymentApplyBean apply, String reason)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(apply.getId());

        log.setActor(user.getStafferName());

        log.setOprMode(PublicConstant.OPRMODE_REJECT);

        log.setDescription(reason);

        log.setLogTime(TimeTools.now());

        log.setPreStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        log.setAfterStatus(FinanceConstant.PAYAPPLY_STATUS_REJECT);

        flowLogDAO.saveEntityBean(log);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updatePaymentApply(User user, PaymentApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getVsList());

        checkUpdate(bean);

        bean.setLogTime(TimeTools.now());

        bean.setStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        List<PaymentVSOutBean> vsList = bean.getVsList();

        for (PaymentVSOutBean vsItem : vsList)
        {
            vsItem.setId(commonDAO.getSquenceString20());

            vsItem.setParentId(bean.getId());

            vsItem.setLogTime(bean.getLogTime());

            fillEachItem(bean, vsItem);
        }

        paymentApplyDAO.updateEntityBean(bean);

        paymentVSOutDAO.deleteEntityBeansByFK(bean.getId());

        paymentVSOutDAO.saveAllEntityBeans(vsList);

        saveUpdateFlowlog(user, bean);

        return true;
    }

    /**
     * saveUpdateFlowlog
     * 
     * @param user
     * @param bean
     */
    private void saveUpdateFlowlog(User user, PaymentApplyBean bean)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(bean.getId());

        log.setActor(user.getStafferName());

        log.setOprMode(PublicConstant.OPRMODE_SUBMIT);

        log.setDescription(user.getStafferName() + "修改付款申请");

        log.setLogTime(TimeTools.now());

        log.setAfterStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        log.setPreStatus(FinanceConstant.PAYAPPLY_STATUS_REJECT);

        flowLogDAO.saveEntityBean(log);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean batchSplitInBill(User user, String billId, List<BatchSplitInBillWrap> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(billId, list);
    	
    	InBillBean bean = inBillDAO.find(billId);
    	
    	if (null == bean)
    	{
    		throw new MYException("预收[%s]不存在", billId);
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
    	{
    		throw new MYException("[%s]不是预收态", billId);
    	}
    	
    	double total = 0.0d;
    	
    	for (BatchSplitInBillWrap each : list)
    	{
    		total += each.getMoney();
    	}
    	
    	if (bean.getMoneys() < total)
    	{
    		throw new MYException("拆分的预收总金额不能大于被拆分预收");
    	}
    	
    	// merge 将 同一客户 ,且不为勾款的合并; 同一客户,且为勾款的,同一销售单 合并; 同一客户,且为勾款的,同一结算单合并
    	Map<String, BatchSplitInBillWrap> map = new HashMap<String, BatchSplitInBillWrap>();
    	
    	for (BatchSplitInBillWrap each : list)
    	{
    		String key = each.getCustomerId() + "-" + each.getType() + "-" + each.getOutId() + "-" + each.getBalanceId();
    		
    		if (!map.containsKey(key))
    		{
    			map.put(key, each);
    			
    		}else{
    			BatchSplitInBillWrap wrap = map.get(key);
    			
    			wrap.setMoney(wrap.getMoney() + each.getMoney());
    		}
    	}
    	
    	// 一个一个处理
    	for (BatchSplitInBillWrap each : map.values())
    	{
    		StafferVSCustomerBean stafferVSCustomer = stafferVSCustomerDAO.findByUnique(each.getCustomerId());
    		
    		if (null == stafferVSCustomer)
    		{
    			throw new MYException("转入的客户没有挂靠关系，对应的销售单[%s]", each.getOutId());
    		}
    		
    		// 2.对勾款的类型，拆分的预收置为关联申请，且生成收款申请单
    		if (each.getType() == 0)
    		{
    			processBatchSplitRefOut(user, billId, bean, each, stafferVSCustomer);
    			
    		}else{
    			processBatchSplit(user, billId, bean, each, stafferVSCustomer);
    		}
    	}
    	
		return true;
	}

    /**
     * processBatchSplit 
     *  批量拆分预收
     *   不用审批，生成凭证直接结束
     * @param user
     * @param billId
     * @param bean
     * @param each
     * @param stafferVSCustomer
     * @throws MYException
     */
	private void processBatchSplit(User user, String billId, InBillBean bean,
			BatchSplitInBillWrap each, StafferVSCustomerBean stafferVSCustomer)
			throws MYException
	{
		String newId = billManager.splitInBillBeanToOtherCustomer(user, billId, each.getCustomerId(), stafferVSCustomer.getStafferId(), each.getMoney());
		
		InBillBean newInBill = inBillDAO.find(newId);
		
		if (newInBill == null)
		{
		    throw new MYException("数据错误,请确认操作");
		}
		
		newInBill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
		
		inBillDAO.updateEntityBean(newInBill);
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT);
		apply.setCustomerId(each.getCustomerId());
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setPaymentId(newInBill.getPaymentId());
		apply.setStafferId(stafferVSCustomer.getStafferId());
		apply.setDescription("批量拆分预收");
		
		apply.setOriCustomerId(bean.getCustomerId());
		apply.setOriStafferId(bean.getOwnerId());
		apply.setOriBillId(billId);

		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		apply.setVsList(vsList);

		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(newInBill.getMoneys());

		vs.setOutId("");

		vs.setPaymentId(newInBill.getPaymentId());

		vs.setBillId(newInBill.getId());
		
		vs.setStafferId(stafferVSCustomer.getStafferId());

		vsList.add(vs);
		// -- end --

		apply.setMoneys(newInBill.getMoneys());
		
		// TAX_ADD 回款转预收/销售单绑定(预收转应收)/预收转费用/预收拆分
		Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

		for (PaymentApplyListener listener : listenerMapValues)
		{
		    listener.onPassBean(user, apply);
		}
	}

	/**
	 * processBatchSplitRefOut
	 *  批量拆分且勾销售单（销售出库、结算单）要求一次勾清
	 * @param user
	 * @param billId
	 * @param each
	 * @param stafferVSCustomer
	 * @throws MYException
	 */
	private void processBatchSplitRefOut(User user, String billId, InBillBean bean,
			BatchSplitInBillWrap each, StafferVSCustomerBean stafferVSCustomer)
			throws MYException
	{
		double needPayMoney = 0.0d;
		
		OutBean out = null;
		
		if (StringTools.isNullOrNone(each.getBalanceId()))
		{
			// 销售单
			out = outDAO.find(each.getOutId());
			
			if (null == out)
			{
				throw new MYException("数据错误");
			}
			
			needPayMoney = outManager.outNeedPayMoney(null, each.getOutId());
		}else{
			// 结算单
			OutBalanceBean outBalance = outBalanceDAO.find(each.getBalanceId());
			
			if (null == outBalance)
			{
				throw new MYException("数据错误1");
			}
			
			out = outDAO.find(outBalance.getOutId());
			
			if (null == out)
			{
				throw new MYException("数据错误2");
			}
			
			double refTotal = outBalanceDAO.sumByOutBalanceId(outBalance.getId());
		    
			needPayMoney = outBalance.getTotal() - outBalance.getPayMoney() - refTotal;
		}
		
		if (MathTools.compare(each.getMoney(), needPayMoney) != 0)
		{
		    throw new MYException("销售单需付款为[%.2f],但导入中金额为[%.2f],两个金额须一致,请确认操作", needPayMoney, each.getMoney());
		}
		
		// 正在对账
		if (out.getFeedBackCheck() == 1)
		{
			throw new MYException("此销售单[%s]正在对账，不允许勾款:", out.getFullId());
		}
		
		String newId = billManager.splitInBillBeanToOtherCustomer(user, billId, each.getCustomerId(), stafferVSCustomer.getStafferId(), each.getMoney());
		
		InBillBean newInBill = inBillDAO.find(newId);
		
		if (newInBill == null)
		{
		    throw new MYException("数据错误,请确认操作");
		}
		
		newInBill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
		
		inBillDAO.updateEntityBean(newInBill);
		
		// 勾款
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TEMP);
		apply.setCustomerId(each.getCustomerId());
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setStafferId(stafferVSCustomer.getStafferId());
		apply.setBadMoney(0);
		apply.setDescription("批量拆分勾款");
		apply.setOperator(user.getStafferId());
		apply.setOperatorName(user.getStafferName());
		
		apply.setOriCustomerId(bean.getCustomerId());
		apply.setOriStafferId(bean.getOwnerId());
		apply.setOriBillId(billId);
		
		apply.setMoneys(newInBill.getMoneys());

		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(newInBill.getMoneys());
		
		vs.setOutId(each.getOutId());
		vs.setOutBalanceId(each.getBalanceId());

		vs.setBillId(newId);

		vs.setPaymentId(newInBill.getPaymentId());

		vs.setStafferId(apply.getStafferId());

		vsList.add(vs);

		apply.setVsList(vsList);

		// 生成勾款申请
		addPaymentApplyWithoutTrans(user, apply);
		
		// 批量拆分的款马上生成凭证
		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT);
		apply.setDescription("批量拆分预收");
		
		// TAX_ADD 回款转预收/销售单绑定(预收转应收)/预收转费用/预收拆分
		Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

		for (PaymentApplyListener listener : listenerMapValues)
		{
		    listener.onPassBean(user, apply);
		}
	}
    
	@Transactional(rollbackFor = MYException.class)
    public boolean addTransferApply(User user, PaymentApplyBean bean)
        throws MYException
    {
		JudgeTools.judgeParameterIsNull(user, bean, bean.getVsList());
		
		List<PaymentVSOutBean> vsList = bean.getVsList();

        //check
		String paymentId = bean.getPaymentId();
		
		PaymentBean payment = paymentDAO.find(paymentId);
		
		if (null == payment)
		{
			throw new MYException("数据错误,请确认操作");
		}
		
		if (payment.getCtype() !=FinanceConstant.PAYMENTCTYPE_INTERNAL)
		{
			throw new MYException("回款不是内部资金类型,请确认操作");
		}
		
		if (payment.getStatus() != FinanceConstant.PAYMENT_STATUS_INIT)
        {
            throw new MYException("回款已经被人认领,请确认操作");
        }
		
		PaymentVSOutBean vs = vsList.get(0);
		
		OutBillBean outBill = outBillDAO.find(vs.getOutId());
		
		if (null == outBill)
		{
			throw new MYException("数据错误,请确认操作");
		}
		
		if (outBill.getStatus() != FinanceConstant.OUTBILL_STATUS_CONFIRM)
		{
			throw new MYException("转账付款单状态不是待确认,请确认操作");
		}
		
		if (!MathTools.equal(payment.getMoney(), outBill.getMoneys()))
		{
			throw new MYException("转账付款单金额[%.2f]与回款金额[%.2f]不一致,请确认操作", outBill.getMoneys(), payment.getMoney());
		}
		
		payment.setStafferId(user.getStafferId());

		payment.setCustomerId("");

		payment.setStatus(FinanceConstant.PAYMENT_STATUS_END);
        
        paymentDAO.updateEntityBean(payment);
        
        outBill.setStatus(FinanceConstant.OUTBILL_STATUS_END);
        
        outBill.setDescription(outBill.getDescription() + ", 内部资金勾款:" + paymentId);
        
        outBillDAO.updateEntityBean(outBill);
		
        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_PAYMENTAPPLY_PREFIX));

        bean.setLogTime(TimeTools.now());

        bean.setStatus(FinanceConstant.PAYAPPLY_STATUS_INIT);

        // 校验是否是特殊单据
        for (PaymentVSOutBean vsItem : vsList)
        {
            vsItem.setId(commonDAO.getSquenceString20());

            vsItem.setParentId(bean.getId());

            vsItem.setLogTime(bean.getLogTime());
        }

        paymentApplyDAO.saveEntityBean(bean);

        paymentVSOutDAO.saveAllEntityBeans(vsList);
        
        saveFlowlog(user, bean);
        
        // TAX_ADD
        Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

		for (PaymentApplyListener listener : listenerMapValues)
		{
		    listener.onDrawTransfer(user, payment, outBill.getId());
		}

        return true;
	}
	
	/**
	 * @id 回款单HK
	 * @customerId 供应商
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean addDrawProviderApply(User user, PaymentApplyBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean);
		
		// 检查HK是存在且未认领
		PaymentBean payment = paymentDAO.find(bean.getPaymentId());
		
		if (null == payment)
		{
			throw new MYException("数据错误,请确认操作");
		}
		
		if (payment.getCtype() !=FinanceConstant.PAYMENTCTYPE_EXTERNAL)
		{
			throw new MYException("回款不是外部资金类型,请确认操作");
		}
		
		if (payment.getStatus() != FinanceConstant.PAYMENT_STATUS_INIT)
        {
            throw new MYException("回款已经被人认领,请确认操作");
        }
		
        InBillBean inBean = new InBillBean();

        inBean.setBankId(payment.getBankId());

        inBean.setCustomerId(bean.getCustomerId());

        inBean.setLocationId(user.getLocationId());

        inBean.setLogTime(TimeTools.now());

        inBean.setMoneys(payment.getMoney());

        inBean.setSrcMoneys(payment.getMoney());
        
        inBean.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

        inBean.setDescription("自动生成预收收款单(供应商),从回款单:" + payment.getId() + "," + bean.getDescription());

        inBean.setOwnerId(user.getStafferId());

        inBean.setPaymentId(payment.getId());

        inBean.setStafferId(user.getStafferId());

        inBean.setType(FinanceConstant.INBILL_TYPE_PURCHASEBACK);

        billManager.addInBillBeanWithoutTransaction(user, inBean);

        // 修改 回款状态
        payment.setStafferId(user.getStafferId());

        payment.setCustomerId(bean.getCustomerId());

        payment.setStatus(FinanceConstant.PAYMENT_STATUS_END);
        payment.setUseMoney(payment.getMoney());
        payment.setUseall(FinanceConstant.PAYMENT_USEALL_END);
        payment.setUpdateTime(TimeTools.now());

        paymentDAO.updateEntityBean(payment);
        
        bean.setType(FinanceConstant.PAYAPPLY_TYPE_PAYMENT);
        
        // 回款认领凭证 1.银行-暂记户  2.暂记户-预收
        // TAX_ADD 回款转预收/销售单绑定(预收转应收)/预收转费用 通过
        Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

        for (PaymentApplyListener listener : listenerMapValues)
        {
            listener.onPassBean(user, bean);
        }
        
        return true;
    }
	
	@Transactional(rollbackFor = MYException.class)
	public boolean refPurchaseBack(User user, String customerId, String ids) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, customerId, ids);
		
		// 根据供应商找到对应的预收
		ConditionParse con = setCondition(user, customerId);
		
		List<InBillBean> billList = inBillDAO.queryEntityBeansByCondition(con);
		
		if (ListTools.isEmptyOrNull(billList))
		{
			throw new MYException("没有可用的供应商预收");
		}
		
		// 预收与采购退货 是 多对多关系， 在此采用由采购退单 找可用的预收
		List<PaymentApplyBean> applyList = new ArrayList<PaymentApplyBean>();
		
		String fullIds [] = ids.split("~");
		
		for (String fullId : fullIds)
		{
			if (StringTools.isNullOrNone(fullId))
				continue;
			
			OutBean out = outDAO.find(fullId);
			
			if (null == out)
				continue;
			
			if (out.getType() != OutConstant.OUT_TYPE_INBILL && out.getOutType() != OutConstant.OUTTYPE_IN_STOCK)
			{
				continue;
			}
			
			if (out.getHadPay() != 0)
				continue;
			
			// 勾预收 - 需勾金额总额
			double lastMoney = -out.getTotal();
			double moneys = lastMoney;
			
			List<PaymentVSOutBean> vs = new ArrayList<PaymentVSOutBean>();
            
            // 销售单一个一个的勾
        	for (Iterator<InBillBean> iterator = billList.iterator(); iterator.hasNext();)
            {
        		InBillBean bill = iterator.next();
        		
        		if (MathTools.compare(bill.getMoneys(), lastMoney) > 0)
                {
                    // 拆分此单  移交lastMoney出去使用
                    String newId = billManager.splitInBillBeanWithoutTransactional(user, bill.getId(), lastMoney);

                    bill.setMoneys(bill.getMoneys() - lastMoney);
                    
                    InBillBean newBill = inBillDAO.find(newId);
                    
                    newBill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                    newBill.setOutId(out.getFullId());
                    
                    inBillDAO.updateEntityBean(newBill);
                    
                    PaymentVSOutBean each = new PaymentVSOutBean();
                    
                    each.setPaymentId(bill.getPaymentId());
                    each.setMoneys(lastMoney);
                    each.setBillId(newId);
                    each.setOutId(out.getFullId());
                    
                    vs.add(each);
                    
                    lastMoney = 0.0d;
                    
                    break;
                    
                }else{
                	 // 原预收 状态置为 关联销售
                    bill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                    bill.setOutId(out.getFullId());
                    
                    inBillDAO.updateEntityBean(bill);
                    
                    iterator.remove();
                    
                    lastMoney -= bill.getMoneys();
                    
                    PaymentVSOutBean each = new PaymentVSOutBean();
                    
                    each.setPaymentId(bill.getPaymentId());
                    each.setMoneys(bill.getMoneys());
                    each.setBillId(bill.getId());
                    each.setOutId(out.getFullId());
                    
                    vs.add(each);
                    
                    if (lastMoney == 0.0d)
                    {
                    	break;
                    }
                }
            }
        
        	// 预收不够
        	if (lastMoney > 0)
        	{
        		throw new MYException("采购退单[%s]勾款时发现预收不足，请检查", out.getFullId());
        	}else{
        		 // 生成收款申请数据 PaymentApplyBean
                PaymentApplyBean apply = new PaymentApplyBean();
                
                apply.setStafferId(user.getStafferId());
                apply.setPaymentId("");
                apply.setLogTime(TimeTools.now());
                apply.setMoneys(moneys);
                
                apply.setVsList(vs);
                
                applyList.add(apply);
        	}
			
			// 更新采购退货单 hadpay
			outDAO.modifyOutHadPay(fullId, out.getTotal());
		}
		
		// TAX_ADD 供应商预收(预收账款(客户/职员/部门)) /采购退货  (应付账款-货款(单位)) 通过
        Collection<PaymentApplyListener> listenerMapValues = this.listenerMapValues();

        for (PaymentApplyListener listener : listenerMapValues)
        {
            listener.onStockBack(user, applyList);
        }
		
		return true;
	}

	private ConditionParse setCondition(User user, String customerId)
	{
		ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();
		
		condtion.addCondition("InBillBean.customerId", "=", customerId);
        
        condtion.addCondition("InBillBean.ownerId", "=", user.getStafferId());
        
        condtion.addIntCondition("InBillBean.type", "=", FinanceConstant.INBILL_TYPE_PURCHASEBACK);

        condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
        
        condtion.addCondition(" and InBillBean.moneys >= 0.01");//此处过滤金额为0
        
        condtion.addCondition("InBillBean.moneys", "<>", FinanceConstant.BILL_ZERO_DOUBLE);//此处过滤金额为0

        condtion.addCondition("order by InBillBean.logTime desc");
        
        return condtion;
	}
	
    /**
     * @return the paymentApplyDAO
     */
    public PaymentApplyDAO getPaymentApplyDAO()
    {
        return paymentApplyDAO;
    }

    /**
     * @param paymentApplyDAO
     *            the paymentApplyDAO to set
     */
    public void setPaymentApplyDAO(PaymentApplyDAO paymentApplyDAO)
    {
        this.paymentApplyDAO = paymentApplyDAO;
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
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
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
     * @return the billManager
     */
    public BillManager getBillManager()
    {
        return billManager;
    }

    /**
     * @param billManager
     *            the billManager to set
     */
    public void setBillManager(BillManager billManager)
    {
        this.billManager = billManager;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
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
     * @return the backPayApplyDAO
     */
    public BackPayApplyDAO getBackPayApplyDAO()
    {
        return backPayApplyDAO;
    }

    /**
     * @param backPayApplyDAO
     *            the backPayApplyDAO to set
     */
    public void setBackPayApplyDAO(BackPayApplyDAO backPayApplyDAO)
    {
        this.backPayApplyDAO = backPayApplyDAO;
    }

	/**
	 * @return the autoPayMonitorDAO
	 */
	public AutoPayMonitorDAO getAutoPayMonitorDAO()
	{
		return autoPayMonitorDAO;
	}

	/**
	 * @param autoPayMonitorDAO the autoPayMonitorDAO to set
	 */
	public void setAutoPayMonitorDAO(AutoPayMonitorDAO autoPayMonitorDAO)
	{
		this.autoPayMonitorDAO = autoPayMonitorDAO;
	}

	/**
	 * @return the customerBankDAO
	 */
	public CustomerBankDAO getCustomerBankDAO()
	{
		return customerBankDAO;
	}

	/**
	 * @param customerBankDAO the customerBankDAO to set
	 */
	public void setCustomerBankDAO(CustomerBankDAO customerBankDAO)
	{
		this.customerBankDAO = customerBankDAO;
	}

	/**
	 * @return the stafferVSCustomerDAO
	 */
	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	/**
	 * @param stafferVSCustomerDAO the stafferVSCustomerDAO to set
	 */
	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	/**
	 * @return the dutyDAO
	 */
	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	/**
	 * @param dutyDAO the dutyDAO to set
	 */
	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the outBillDAO
	 */
	public OutBillDAO getOutBillDAO()
	{
		return outBillDAO;
	}

	/**
	 * @param outBillDAO the outBillDAO to set
	 */
	public void setOutBillDAO(OutBillDAO outBillDAO)
	{
		this.outBillDAO = outBillDAO;
	}
}
