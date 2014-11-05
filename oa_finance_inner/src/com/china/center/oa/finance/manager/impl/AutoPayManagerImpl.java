package com.china.center.oa.finance.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBankBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerBankDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.AutoPayLogBean;
import com.china.center.oa.finance.bean.AutoPayMonitorBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.AutoPayLogDAO;
import com.china.center.oa.finance.dao.AutoPayMonitorDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.listener.AutoPayListener;
import com.china.center.oa.finance.manager.AutoPayManager;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.PaymentApplyManager;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class AutoPayManagerImpl extends AbstractListenerManager<AutoPayListener> implements AutoPayManager
{
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private PaymentDAO paymentDAO = null;
	
	private CustomerBankDAO customerBankDAO = null;
	
	private CustomerMainDAO customerMainDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private BillManager billManager = null;
	
	private OutDAO outDAO = null;
	
	private PaymentApplyDAO paymentApplyDAO = null;
	
	private InBillDAO inBillDAO = null;
	
	private OutBalanceDAO outBalanceDAO = null;
	
	private AutoPayMonitorDAO autoPayMonitorDAO = null;
	
	private AutoPayLogDAO autoPayLogDAO = null;
	
	private OutManager outManager = null;
	
	private PaymentApplyManager paymentApplyManager = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	/**
	 * default construct
	 */
	public AutoPayManagerImpl()
	{
	}

	/**
	 * 自动认款，暂记户-预收
	 * 
	 * 定时任务：每小时执行一次
	 * 
	 * {@inheritDoc}
	 */
	public void autoProcessPaymentToPre()
	{
		synchronized (FinanceFacade.PAYMENT_LOCK)
		{
			triggerLog.info("autoProcessPaymentToPre 开始...");
	        
	        long statsStar = System.currentTimeMillis();
	        
	        // 查询所有未认领的回款. 
	        ConditionParse con = new ConditionParse();
	        
	        con.addWhereStr();
	        con.addIntCondition("PaymentBean.status", "=", FinanceConstant.PAYMENT_STATUS_INIT);
	        
	        List<PaymentBean> list = paymentDAO.queryEntityBeansByCondition(con);
	        
	        for (final PaymentBean each : list)
	        {
	        	TransactionTemplate tran = new TransactionTemplate(transactionManager);
	        	
	            try
	            {
	                tran.execute(new TransactionCallback()
	                {
	                    public Object doInTransaction(TransactionStatus arg0)
	                    {
	                    	try
							{
								processPaymentToPre(each);
							}
							catch (MYException e)
							{
								throw new RuntimeException(e);
							}
	                    	
	                        return Boolean.TRUE;
	                    }
	                });
	            }
	            catch (Exception e)
	            {
	                triggerLog.error(e, e);
	            }
	        }
	      
	        triggerLog.info("autoProcessPaymentToPre 结束... ,共处理：" + list.size() +"条回款.共耗时："+ (System.currentTimeMillis() - statsStar));
	        
	        return;
		}
	}

	/**
	 * 处理单元
	 * 1.再次检查状态是否为未认领；
	 * 2.根据来源确定客户，生成预收， 同时更新回款为已认领等信息, 且不能退领操作
	 * 3.生成凭证
	 * 4.重复1的操作
	 * @param bean
	 */
	private void processPaymentToPre(PaymentBean bean) throws MYException
	{
		PaymentBean payment = paymentDAO.find(bean.getId());
		
		if (null == payment)
			throw new MYException("数据错误");
		
		checkPayment(payment, false);
		
		if (StringTools.isNullOrNone(payment.getFromerNo()) || StringTools.isNullOrNone(payment.getFromer()))
		{
			return;
		}
		
		CustomerBankBean custBankBean = customerBankDAO.findByUnique(payment.getFromer(), payment.getFromerNo());

		if (null == custBankBean)
		{
			return;
		}
		
		String customerId = custBankBean.getCustomerId();
		
		// 检查客户的有效性
		CustomerBean customer = customerMainDAO.find(customerId);
		
		if (null == customer)
		{
			throw new MYException("数据错误");
		}
		
		// 只限中信银行客户 -- trial to use
		if (customer.getName().startsWith("中信银行") && customer.getName().endsWith("（银行）"))
        {
        	//
        }else
        {
        	return;
        }
		
		StafferVSCustomerBean vs = stafferVSCustomerDAO.findByUnique(customerId);
		
		if (vs == null)
		{
			throw new MYException("客户没有挂靠");
		}
		
		// 可以转为预收了
        InBillBean inBean = new InBillBean();

        inBean.setBankId(payment.getBankId());

        inBean.setCustomerId(customerId);

        inBean.setLocationId(PublicConstant.CENTER_LOCATION);

        inBean.setLogTime(TimeTools.now());

        inBean.setMoneys(payment.getMoney());

        inBean.setSrcMoneys(payment.getMoney());
        
        // 预收
        inBean.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

        inBean.setDescription("系统自动生成预收收款单,从回款单:" + payment.getId() + ",未关联销售单.审批意见: OK" + ",系统自动操作");

        inBean.setOutId("");

        inBean.setOutBalanceId("");

        inBean.setOwnerId(vs.getStafferId());

        inBean.setPaymentId(payment.getId());

        inBean.setStafferId(StafferConstant.SUPER_STAFFER);

        inBean.setType(FinanceConstant.INBILL_TYPE_SAILOUT);

        billManager.addInBillBeanWithoutTransaction(null, inBean);

		// 更新回款单 - 系统自动完成,由系统认领
        checkPayment(payment, true);
        
        // 退领时要知道认领人
        payment.setStafferId(vs.getStafferId());
			
        payment.setCustomerId(customerId);
			
        payment.setStatus(FinanceConstant.PAYMENT_STATUS_END);
        
        // 回款HK单一次性全部认领,所以 HK为全部使用
        payment.setUseall(FinanceConstant.PAYMENT_USEALL_END);
        payment.setUpdateTime(TimeTools.now());
        
        paymentDAO.updateEntityBean(payment);
        
        // TAX_ADD 暂记户/预收  （不用审批,直接一步到位）
        Collection<AutoPayListener> listenerMapValues = this.listenerMapValues();

        for (AutoPayListener listener : listenerMapValues)
        {
            listener.onCompletePaymentToPre(payment);
        }
	}

	private void checkPayment(PaymentBean payment, boolean query) throws MYException
	{
		if (query)
		{
			payment = paymentDAO.find(payment.getId());
			
			if (null == payment)
			{
				throw new MYException("数据错误");
			}
		}
			
		if (payment.getStatus() != FinanceConstant.PAYMENT_STATUS_INIT)
		{
		   throw new MYException("回款已经被人认领,请确认操作");
		}
	}
	
	/**
	 * 自动勾款
	 * 1.销售出库，零售
	 * 2.委托代销结算单
	 * {@inheritDoc}
	 */
	public void autoRefInbillToSail()
	{
		synchronized (FinanceFacade.PAYMENT_APPLY_LOCK)
		{
			try{
				BillManagerImpl.setLOCK_BILL(true);
				
				// 销售出库，零售 自动勾款
				autoRefInbillToSailWithoutConsign();
				
				// 委托代销结算单
				autoRefInbillToSailWithConsign();
			}finally{
				BillManagerImpl.setLOCK_BILL(false);
			}
		}
	}

	private void autoRefInbillToSailWithoutConsign()
	{
        triggerLog.info("autoRefInbillToSail.autoRefInbillToSailWithoutConsign 开始...");
        
        long statsStar = System.currentTimeMillis();
        
        // 查询可以勾款的数据.  销售出库与零售
        ConditionParse con = new ConditionParse();
        
        setCondition(con);
        
        List<OutBean> list = outDAO.queryEntityBeansByCondition(con);
        
        // 排除不能操作勾款的单据
        for(Iterator<OutBean> iterator = list.iterator(); iterator.hasNext();)
        {
        	OutBean out = iterator.next();
        	
        	// 存在退货未结束的，不操作
        	con.clear();
        	
        	con.addWhereStr();
        	
        	con.addCondition("OutBean.refOutFullId", "=", out.getFullId());

    		con.addCondition(" and OutBean.status not in (3, 4)");

    		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
    		
    		int count = outDAO.countByCondition(con.toString());
    		
    		if (count > 0)
    		{
    			iterator.remove();
    			
    			continue;
    		}
    		
    		// 收款申请中存在未结束的申请 ,不操作
            int count1 = paymentApplyDAO.countApplyByOutId2(out.getFullId());
            
            if (count1 > 0)
            {
            	iterator.remove();
            	
            	continue;
            }
            
            // 试用阶段只限制有账号关系的使用
            if (out.getCustomerName().startsWith("中信银行") && out.getCustomerName().endsWith("（银行）"))
            {
            	//
            }else
            {
            	iterator.remove();
            	
            	continue;
            }
        }
        
        // 一个一个的勾款,模拟手工勾款
        for (final OutBean each : list)
        {
        	if (each.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
        	{
        		triggerLog.info(each.getFullId() + " 开票审批中,不允许勾款");
        		continue;
        	}
        	
    		// 获取销售单应付的金额
    		final double needPay = outManager.outNeedPayMoney(null, each.getFullId());
    		
    		if (needPay == 0.0)
    			continue;
    		
        	final List<InBillBean> billList = refInbillToSailPre(each, needPay);
        	
        	if (billList == null)
        		continue;

        	TransactionTemplate tran = new TransactionTemplate(transactionManager);
        	
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                    	try
						{
                    		refInbillToSail(each, billList, needPay);
						}
						catch (MYException e)
						{
							throw new RuntimeException(e);
						}
                    	
                        return Boolean.TRUE;
                    }
                });
            }
            catch (Exception e)
            {
                triggerLog.error(e, e);
            }
            
            refInbillToSailPost(each, billList);
        }
      
        triggerLog.info("autoRefInbillToSail.autoRefInbillToSailWithoutConsign 结束...,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
	}
	
	/**
	 * 处理委托代销结算单
	 */
	private void autoRefInbillToSailWithConsign()
	{
        triggerLog.info("autoRefInbillToSail.autoRefInbillToSailWithConsign 开始...");
        
        long statsStar = System.currentTimeMillis();
        
        // 查询可以勾款的数据.  委托代销结算单
        ConditionParse con = new ConditionParse();
        
        setCondition1(con);
        
        List<OutBalanceBean> list = outBalanceDAO.queryEntityBeansByCondition(con);
        
        // 排除不能操作勾款的单据
        for(Iterator<OutBalanceBean> iterator = list.iterator(); iterator.hasNext();)
        {
        	OutBalanceBean outBalance = iterator.next();
        	
        	OutBean out = outDAO.find(outBalance.getOutId());
        	
        	if (out == null)
        	{
        		iterator.remove();
        		
        		continue;
        	}
        	
        	if (out.getFeedBackCheck() == 1){
        		iterator.remove();
        		
        		continue;
        	}
        	
        	// 存在退货未结束的，不操作
        	con.clear();
        	
        	con.addWhereStr();
        	
        	con.addCondition("OutBalanceBean.refOutBalanceId", "=", outBalance.getId());

        	con.addCondition("OutBalanceBean.status", "<>", OutConstant.OUTBALANCE_STATUS_END);

    		int count = outBalanceDAO.countByCondition(con.toString());
    		
    		if (count > 0){
    			iterator.remove();
    			
    			continue;
    		}
    		
    		// 收款申请中存在未结束的申请 ,不操作
            int count1 = paymentApplyDAO.countApplyByOutId2(out.getFullId());
            
            if (count1 > 0)
            {
            	iterator.remove();
            	
            	continue;
            }
            
            // 试用阶段只限制中信订单的使用
            if (out.getCustomerName().startsWith("中信银行") && out.getCustomerName().endsWith("（银行）"))
            {
            	//
            }else
            {
            	iterator.remove();
            	
            	continue;
            }
        }
        
        // 一个一个的勾款,模拟手工勾款
        for (final OutBalanceBean each : list)
        {
        	if (each.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
        	{
        		triggerLog.info(each.getId() + "/" + each.getOutId() + " 开票审批中,不允许勾款");
        		continue;
        	}
        	
    		// 获取委托结算单应付的金额
    		// 减去结算单退货部分
        	double refTotal = outBalanceDAO.sumByOutBalanceId(each.getId());
            
        	final double needPay = each.getTotal() - each.getPayMoney() - refTotal;
    		
    		if (needPay == 0.0)
    			continue;
    		
        	final List<InBillBean> billList = refInbillToSailSettlePre(each, needPay);
        	
        	if (billList == null)
        		continue;
        	
        	TransactionTemplate tran = new TransactionTemplate(transactionManager);
        	
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus tranStatus)
                    {
                    	try
						{
                    		refInbillToSailSettle(each, billList, needPay);
						}
						catch (MYException e)
						{
							throw new RuntimeException(e);
						}
                    	
                        return Boolean.TRUE;
                    }
                });
            }
            catch (Exception e)
            {
                triggerLog.error(e, e);
            }
            
            refInbillToSailSettlePost(each, billList);
        }
      
        triggerLog.info("autoRefInbillToSail.autoRefInbillToSailWithConsign 结束....,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
	}
	
	private List<InBillBean> refInbillToSailPre(final OutBean out, double needPay)
	{
		// 查询可用的预收
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("InBillBean.ownerId", "=", out.getStafferId());

		condtion.addCondition("InBillBean.customerId", "=", out.getCustomerId());

		condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
		
		condtion.addCondition("InBillBean.moneys", ">", 0);

		// 排除冻结的预收 1: 表示冻结
		condtion.addIntCondition("InBillBean.freeze", "=", 0);
		
		// 票款一致限制
		if (out.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
			condtion.addIntCondition("InBillBean.mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);
		else if (!StringTools.isNullOrNone(out.getPiDutyId()))
		{
			if (!out.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
			{
				condtion.addCondition(" and InBillBean.dutyId in ('90201008080000000001','"+ out.getPiDutyId() + "')");	
			}else{
				// do nothing
			}
		}
		
		condtion.addCondition("order by InBillBean.logTime desc");

		List<InBillBean> billList = inBillDAO.queryEntityBeansByCondition(condtion);
		
		double canPay = 0.0d;
		
		List<AutoPayMonitorBean> apmList = new ArrayList<AutoPayMonitorBean>();
		
		AutoPayMonitorBean apmbean1 = new AutoPayMonitorBean();
		
		apmbean1.setRefId(out.getFullId());
		
		apmList.add(apmbean1);
		
		for(InBillBean inbill : billList)
		{
			canPay += inbill.getMoneys();
			
			AutoPayMonitorBean apmbean = new AutoPayMonitorBean();
			
			apmbean.setRefId(inbill.getId());
			apmList.add(apmbean);
		}
		
		if (canPay < needPay)
			return null;
		
		saveAutoPayMonitorInner(apmList);
		
		return billList;
	}
	
	private void saveAutoPayMonitorInner(final List<AutoPayMonitorBean> apmList)
	{
		TransactionTemplate tran = new TransactionTemplate(transactionManager);
		
		tran.execute(new TransactionCallback()
		{
			public Object doInTransaction(TransactionStatus tstatus)
			{
				autoPayMonitorDAO.saveAllEntityBeans(apmList);
				
				return Boolean.TRUE;
			}
		}
		);
	}
	
	private void refInbillToSailPost(final OutBean out, final List<InBillBean> billList)
	{
		TransactionTemplate tran = new TransactionTemplate(transactionManager);
		
		tran.execute(new TransactionCallback()
		{
			public Object doInTransaction(TransactionStatus tstatus)
			{
				AutoPayMonitorBean apmBean = autoPayMonitorDAO.findByUnique(out.getFullId());
				
				if (null != apmBean)
					autoPayMonitorDAO.deleteEntityBean(apmBean.getId());
				
				for (InBillBean each : billList)
				{
					AutoPayMonitorBean apmBean1 = autoPayMonitorDAO.findByUnique(each.getId());
					
					if (null != apmBean1)
						autoPayMonitorDAO.deleteEntityBean(apmBean1.getId());
				}
				
				return Boolean.TRUE;
			}
		}
		);
	}
	
	private void refInbillToSailSettlePost(final OutBalanceBean out, final List<InBillBean> billList)
	{
		TransactionTemplate tran = new TransactionTemplate(transactionManager);
		
		tran.execute(new TransactionCallback()
		{
			public Object doInTransaction(TransactionStatus tstatus)
			{
				AutoPayMonitorBean apmBean = autoPayMonitorDAO.findByUnique(out.getId());
				
				if (null != apmBean)
					autoPayMonitorDAO.deleteEntityBean(apmBean.getId());
				
				for (InBillBean each : billList)
				{
					AutoPayMonitorBean apmBean1 = autoPayMonitorDAO.findByUnique(each.getId());
					
					if (null != apmBean1)
						autoPayMonitorDAO.deleteEntityBean(apmBean1.getId());
				}
				
				return Boolean.TRUE;
			}
		}
		);
	}
	
	private List<InBillBean> refInbillToSailSettlePre(final OutBalanceBean out, double needPay)
	{
		// 查询可用的预收
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("InBillBean.ownerId", "=", out.getStafferId());

		condtion.addCondition("InBillBean.customerId", "=", out.getCustomerId());

		condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
		
		condtion.addCondition("InBillBean.moneys", ">", 0);

		// 排除冻结的预收 1: 表示冻结
		condtion.addIntCondition("InBillBean.freeze", "=", 0);
		
		// 票款一致限制
		if (out.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
			condtion.addIntCondition("InBillBean.mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);
		else if (!StringTools.isNullOrNone(out.getPiDutyId()))
		{
			if (!out.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
			{
				condtion.addCondition(" and InBillBean.dutyId in ('90201008080000000001','"+ out.getPiDutyId() + "')");	
			}else{
				// do nothing
			}
		}
		
		condtion.addCondition("order by InBillBean.logTime desc");

		List<InBillBean> billList = inBillDAO.queryEntityBeansByCondition(condtion);
		
		double canPay = 0.0d;
		
		List<AutoPayMonitorBean> apmList = new ArrayList<AutoPayMonitorBean>();
		
		AutoPayMonitorBean apmbean1 = new AutoPayMonitorBean();
		
		apmbean1.setRefId(out.getId());
		
		apmList.add(apmbean1);
		
		for(InBillBean inbill : billList)
		{
			canPay += inbill.getMoneys();
			
			AutoPayMonitorBean apmbean = new AutoPayMonitorBean();
			
			apmbean.setRefId(inbill.getId());
			apmList.add(apmbean);
		}
		
		if (canPay < needPay)
			return null;
		
		saveAutoPayMonitorInner(apmList);
		
		return billList;
	}
	
	/**
	 * CORE
	 * 处理自动勾款单元(销售出库、零售）
	 * @param out
	 * @throws MYException
	 */
	private void refInbillToSail(final OutBean out, final List<InBillBean> billList, final double needPay) throws MYException
	{
		UserVO user = new UserVO();
        
        user.setStafferId(StafferConstant.SUPER_STAFFER);
        user.setStafferName("系统");
        
        // 勾款 - 预收转应收
        double needPayMoney = needPay;
        
        // 再次计算 需要勾的钱
        double lastNeedPay = outManager.outNeedPayMoney(null, out.getFullId());
        
        if (lastNeedPay == 0.0)
        	return;
        
        if (lastNeedPay != needPayMoney)
        {
        	return;
        }
        
        List<String> dutyList = new ArrayList<String>();
        
		List<InBillBean> inList = new ArrayList<InBillBean>();
		
		for (int i = 0; i < billList.size(); i++ )
        {
            InBillBean each = billList.get(i);

            InBillBean oldInBill = inBillDAO.find(each.getId());
            
            if (oldInBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
            {
            	throw new MYException("收款单不是预收状态,脏数据");
            }
            
            if (each.getMoneys() != oldInBill.getMoneys())
            {
            	throw new MYException("收款单金额不一致,脏数据");
            }
            
            if (out.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER 
    				&& each.getMtype() != PublicConstant.MANAGER_TYPE_MANAGER)
    		{
    			continue;
    		}
            
            // 最后的处理
            if (each.getMoneys() > needPayMoney)
            {
                String newId = billManager.splitInBillBeanWithoutTransactional(user, each.getId(), needPayMoney);

                // 预收置为应收
                InBillBean newInBill = inBillDAO.find(newId);

                if (newInBill == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 取消关联了
                newInBill.setOutId(out.getFullId());
                newInBill.setOutBalanceId("");

                // 未核对
                newInBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                newInBill.setChecks("");

                newInBill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                newInBill
                    .setDescription("系统自动勾款,预收转应收:" + out.getFullId());

                inBillDAO.updateEntityBean(newInBill);
                
                inList.add(newInBill);
                
                saveLog(out.getFullId(), "", each.getId(), needPayMoney, out.getOutType());

                needPayMoney = 0.0d;
                
                dutyList.add(newInBill.getDutyId());
                
                break;
            }
            else
            {
                // 逐步的转已收
            	needPayMoney = needPayMoney - each.getMoneys();
                each.setOutId(out.getFullId());
                each.setOutBalanceId("");
                each.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

                // 未核对
                each.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                each.setChecks("");

                each.setDescription(each.getDescription() + ",系统自动勾款,预收转应收:" + out.getFullId());

                // 谁审批的就是谁的单子
                each.setStafferId(user.getStafferId());

                // 用坏账勾款 标记  借用createType add by f 2012-8-2
                if (each.getType()== FinanceConstant.INBILL_TYPE_BADOUT){
                    
                	each.setCreateType(FinanceConstant.BILL_CREATETYPE_HANDBADOUT);
                }
                
                inBillDAO.updateEntityBean(each);

                inList.add(each);
                
                dutyList.add(each.getDutyId());
                
                saveLog(out.getFullId(), "", each.getId(), each.getMoneys(), out.getOutType());    
                
                if (needPayMoney == 0.0d)
                {
                    break;
                }
            }
        }
		
		if (needPayMoney > 0){
			throw new MYException("销售单[%s]勾款时发现预收不足，请检查", out.getFullId());
		}

		paymentApplyManager.processOut(user, out.getFullId(), "");
		
		if (StringTools.isNullOrNone(out.getPiDutyId()))
		{
			// 根据使用的预收来决定票款一致的标记
			paymentApplyManager.processPayInvoice(out.getFullId(), "", dutyList, 1);
		}
		
		// TAX_ADD 预收/应收  （不用审批,直接一步到位）
        Collection<AutoPayListener> listenerMapValues = this.listenerMapValues();

        for (AutoPayListener listener : listenerMapValues)
        {
            listener.onRefInbillToSail(inList);
        }
	}

	/**
	 * CORE
	 * 处理自动勾款单元(委托代销结算单）
	 * @param out
	 * @throws MYException
	 */
	private void refInbillToSailSettle(final OutBalanceBean outBalance, final List<InBillBean> billList, final double needPay) throws MYException
	{
		UserVO user = new UserVO();
        
        user.setStafferId(StafferConstant.SUPER_STAFFER);
        user.setStafferName("系统");
        
        // 勾款 - 预收转应收
        double needPayMoney = needPay;
        
        double refTotal = outBalanceDAO.sumByOutBalanceId(outBalance.getId());
        
    	double lastNeedPay = outBalance.getTotal() - outBalance.getPayMoney() - refTotal;
        
    	if (lastNeedPay == 0.0)
        	return;
        
        if (lastNeedPay != needPayMoney)
        {
        	return;
        }
    	
        List<String> dutyList = new ArrayList<String>();
        
		List<InBillBean> inList = new ArrayList<InBillBean>();
		
		for (int i = 0; i < billList.size(); i++ )
        {
            InBillBean each = billList.get(i);

            InBillBean oldInBill = inBillDAO.find(each.getId());
            
            if (null == oldInBill)
            {
            	throw new MYException("收款单不已不存在");
            }
            
            if (oldInBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
            {
            	throw new MYException("收款单不是预收状态,脏数据");
            }
            
            if (each.getMoneys() != oldInBill.getMoneys())
            {
            	throw new MYException("收款单金额不一致,脏数据");
            }
            
            if (outBalance.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER 
    				&& each.getMtype() != PublicConstant.MANAGER_TYPE_MANAGER)
    		{
    			continue;
    		}
            
            // 最后的处理
            if (each.getMoneys() > needPayMoney)
            {
            	// 多余部分预收拆分出去
                String newId = billManager.splitInBillBeanWithoutTransactional(user, each.getId(), needPayMoney);

                // needPayMoney 置为已收
                InBillBean newInBill = inBillDAO.find(newId);

                if (newInBill == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 取消关联了
                newInBill.setOutId(outBalance.getOutId());
                newInBill.setOutBalanceId(outBalance.getId());

                // 未核对
                newInBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                newInBill.setChecks("");

                newInBill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                newInBill
                    .setDescription(newInBill.getDescription() + ",系统自动勾款,预收转应收:" + outBalance.getOutId());

                // 谁审批的就是谁的单子
                newInBill.setStafferId(user.getStafferId());

                // 用坏账勾款 标记  借用createType add by f 2012-8-2
                if (newInBill.getType()== FinanceConstant.INBILL_TYPE_BADOUT){
                    
                	newInBill.setCreateType(FinanceConstant.BILL_CREATETYPE_HANDBADOUT);
                }

                inBillDAO.updateEntityBean(newInBill);
                
                inList.add(newInBill);
                
                saveLog(outBalance.getOutId(), outBalance.getId(), each.getId(), needPayMoney,OutConstant.OUTTYPE_OUT_CONSIGN);

                needPayMoney = 0.0d;
                
                dutyList.add(newInBill.getDutyId());
                
                break;
            }
            else
            {
                // 逐步的转已收
            	needPayMoney = needPayMoney - each.getMoneys();
            	each.setOutId(outBalance.getOutId());
            	each.setOutBalanceId(outBalance.getId());
            	each.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);

                // 未核对
            	each.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
            	each.setChecks("");

            	each.setDescription(each.getDescription() + ",系统自动勾款,预收转应收:" + outBalance.getOutId());

                // 谁审批的就是谁的单子
            	each.setStafferId(user.getStafferId());

                // 用坏账勾款 标记  借用createType add by f 2012-8-2
                if (each.getType()== FinanceConstant.INBILL_TYPE_BADOUT){
                    
                	each.setCreateType(FinanceConstant.BILL_CREATETYPE_HANDBADOUT);
                }
                
                inBillDAO.updateEntityBean(each);
                
                inList.add(each);
                
                dutyList.add(each.getDutyId());
                
                saveLog(outBalance.getOutId(), outBalance.getId(), oldInBill.getId(), oldInBill.getMoneys(),OutConstant.OUTTYPE_OUT_CONSIGN);
                
                if (needPayMoney == 0.0d)
                {
                    break;
                }
            }
        }
		
		if (needPayMoney > 0){
			throw new MYException("结算单[%s/%s]勾款时发现预收不足，请检查", outBalance.getId(), outBalance.getOutId());
		}
		
		paymentApplyManager.processOut(user, outBalance.getOutId(), outBalance.getId());
		
		if (StringTools.isNullOrNone(outBalance.getPiDutyId()))
		{
			// 根据使用的预收来决定票款一致的标记
			paymentApplyManager.processPayInvoice(outBalance.getOutId(), outBalance.getId(), dutyList, 1);
		}
		
		// TAX_ADD 预收/应收  （不用审批,直接一步到位）
        Collection<AutoPayListener> listenerMapValues = this.listenerMapValues();

        for (AutoPayListener listener : listenerMapValues)
        {
            listener.onRefInbillToSail(inList);
        }
	}

	private void saveLog(String outId, String outBalanceId, String billId, double money, int outType)
	{
		AutoPayLogBean log = new AutoPayLogBean();
		
		log.setOutId(outId);
		log.setOutBalanceId(outBalanceId);
		log.setOutType(outType);
		log.setBillId(billId);
		log.setMoney(money);
		log.setLogTime(TimeTools.now());
		
		autoPayLogDAO.saveEntityBean(log);
	}
	
	private void setCondition(ConditionParse con)
	{
		con.addWhereStr();
		
		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
		con.addCondition(" and OutBean.outType in (0, 2)");
		con.addCondition(" and OutBean.status not in (0, 2)");
		con.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);
		
		con.addCondition("OutBean.outtime", ">=", "2013-03-01");
		
		con.addIntCondition("OutBean.feedbackcheck", "=", 0);
		
		con.addCondition(" order by OutBean.outTime asc");
	}
	
	private void setCondition1(ConditionParse con)
	{
		con.addWhereStr();
		
		con.addIntCondition("OutBalanceBean.type", "=", OutConstant.OUTBALANCE_TYPE_SAIL);
		con.addIntCondition("OutBalanceBean.status", "=", OutConstant.OUTBALANCE_STATUS_PASS);
		con.addCondition("OutBalanceBean.payMoney", "=", 0);
		
		con.addCondition(" order by OutBalanceBean.logTime asc");
	}
	/**
	 * @return the paymentDAO
	 */
	public PaymentDAO getPaymentDAO()
	{
		return paymentDAO;
	}

	/**
	 * @param paymentDAO the paymentDAO to set
	 */
	public void setPaymentDAO(PaymentDAO paymentDAO)
	{
		this.paymentDAO = paymentDAO;
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
	 * @return the billManager
	 */
	public BillManager getBillManager()
	{
		return billManager;
	}

	/**
	 * @param billManager the billManager to set
	 */
	public void setBillManager(BillManager billManager)
	{
		this.billManager = billManager;
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

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	/**
	 * @return the paymentApplyDAO
	 */
	public PaymentApplyDAO getPaymentApplyDAO()
	{
		return paymentApplyDAO;
	}

	/**
	 * @param paymentApplyDAO the paymentApplyDAO to set
	 */
	public void setPaymentApplyDAO(PaymentApplyDAO paymentApplyDAO)
	{
		this.paymentApplyDAO = paymentApplyDAO;
	}

	/**
	 * @return the inBillDAO
	 */
	public InBillDAO getInBillDAO()
	{
		return inBillDAO;
	}

	/**
	 * @param inBillDAO the inBillDAO to set
	 */
	public void setInBillDAO(InBillDAO inBillDAO)
	{
		this.inBillDAO = inBillDAO;
	}

	/**
	 * @return the outBalanceDAO
	 */
	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	/**
	 * @param outBalanceDAO the outBalanceDAO to set
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
	 * @param outManager the outManager to set
	 */
	public void setOutManager(OutManager outManager)
	{
		this.outManager = outManager;
	}

	/**
	 * @return the paymentApplyManager
	 */
	public PaymentApplyManager getPaymentApplyManager()
	{
		return paymentApplyManager;
	}

	/**
	 * @param paymentApplyManager the paymentApplyManager to set
	 */
	public void setPaymentApplyManager(PaymentApplyManager paymentApplyManager)
	{
		this.paymentApplyManager = paymentApplyManager;
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
	 * @return the autoPayLogDAO
	 */
	public AutoPayLogDAO getAutoPayLogDAO()
	{
		return autoPayLogDAO;
	}

	/**
	 * @param autoPayLogDAO the autoPayLogDAO to set
	 */
	public void setAutoPayLogDAO(AutoPayLogDAO autoPayLogDAO)
	{
		this.autoPayLogDAO = autoPayLogDAO;
	}

	public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}
}
