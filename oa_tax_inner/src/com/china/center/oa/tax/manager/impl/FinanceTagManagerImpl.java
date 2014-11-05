package com.china.center.oa.tax.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsTagBean;
import com.china.center.oa.finance.bean.SailTagConfigBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsTagDAO;
import com.china.center.oa.finance.dao.SailTagConfigDAO;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutPayTagBean;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutPayTagDAO;
import com.china.center.oa.tax.bean.FinanceTagBean;
import com.china.center.oa.tax.dao.FinanceTagDAO;
import com.china.center.oa.tax.manager.FinanceTagManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

/**
 * 
 * 财务标记 数据 manager
 *
 * @author fangliwen 2014-5-7
 */
public class FinanceTagManagerImpl implements FinanceTagManager
{
	private final Log triggerLog = LogFactory.getLog("trigger");

	private FinanceTagDAO financeTagDAO = null;
	
	private InvoiceinsTagDAO invoiceinsTagDAO = null;
	
	private SailTagConfigDAO sailTagConfigDAO = null;
	
	private InvoiceinsDAO invoiceinsDAO = null;
	
	private InsVSOutDAO insVSOutDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private OutPayTagDAO outPayTagDAO = null;
	
	private InBillDAO inBillDAO = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	/**
	 * 
	 */
	public FinanceTagManagerImpl()
	{
	}

	@Override
	public boolean addFinanceTagBeanWithoutTransaction(User user,
			FinanceTagBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean);
		
		financeTagDAO.saveEntityBean(bean);
		
		return true;
	}
	
	public boolean addFinanceTagBeanWithoutTransaction(User user,
			List<FinanceTagBean> list) throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
		financeTagDAO.saveAllEntityBeans(list);
		
		return true;
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean addFinanceTagBeanWithTransaction(User user,
			FinanceTagBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean);
		
		financeTagDAO.saveEntityBean(bean);
		
		return true;
	}

	@Override
	public void processInvoiceinsTagData()
	{
        triggerLog.info("processInvoiceinsTagData 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	try{
                		processTagInner();
                	}catch(MYException e)
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
      
        triggerLog.info("processInvoiceinsTagData 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
	}
	
	private void processTagInner() throws MYException
	{
		List<InvoiceinsTagBean> list = invoiceinsTagDAO.listEntityBeans();
		
		for (InvoiceinsTagBean each : list)
		{
			InvoiceinsVO insBean = invoiceinsDAO.findVO(each.getInsId());
			
			double rate = insBean.getVal()/100;
			
			String dutyId = insBean.getDutyId();
			
			if (!dutyId.equals(PublicConstant.DEFAULR_DUTY_ID) && !dutyId.equals(PublicConstant.MANAGER2_DUTY_ID))
			{
				dutyId = "NO-90201008080000000001";
			}
			
			int changeTime = 0;
			
			int oldGood = 9522158;
			
			List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(each.getInsId(), AnoConstant.FK_FIRST);
			
			for (InsVSOutBean vs : vsList)
			{
				if (vs.getType() != FinanceConstant.INSVSOUT_TYPE_OUT)
					continue;
				
				if (StringTools.isNullOrNone(vs.getOutId()))
					continue;
				
				OutBean out = outDAO.find(vs.getOutId());
				
				String changeDate = TimeTools.changeFormat(out.getChangeTime(), TimeTools.LONG_FORMAT, "yyyyMM");
				String now = TimeTools.changeFormat(TimeTools.now(), TimeTools.LONG_FORMAT, "yyyyMM");
				
				if (changeDate.equals(now))
					changeTime = 0;
				else
					changeTime = 1;
				
				BaseBean base = baseDAO.queryEntityBeansByFK(out.getFullId()).get(0);
				
				if (base.getOldGoods() != -1 && base.getOldGoods() != 0)
				{
					oldGood = base.getOldGoods();
				}
					
				List<SailTagConfigBean> configList = sailTagConfigDAO.queryEntityBeansByCondition("where changeTime = ? and oldgood = ? and rate = ? and dutyid = ?", 
						changeTime, oldGood, rate, dutyId);
				
				if (ListTools.isEmptyOrNull(configList))
				{
//					throw new MYException("销售出库标记处理出错：销售单[%s], changeTime[%s], oldGood[%s], dutyId[%s], rate[%.2f]",
//							out.getFullId(), changeTime, oldGood, dutyId, rate);
					
					triggerLog.info(String.format("销售出库标记处理出错：开票[%s]中销售单[%s], changeTime[%s], oldGood[%s], dutyId[%s], rate[%.2f]",
							each.getInsId(),out.getFullId(), changeTime, oldGood, dutyId, rate));
					
					saveTagBean(each, out, "");
				}else{
					SailTagConfigBean config = configList.get(0);
					
					saveTagBean(each, out, config.getFlag());
				}
			}
			
			invoiceinsTagDAO.deleteEntityBean(each.getId());
		}
	}

	private void saveTagBean(InvoiceinsTagBean each, OutBean out, String tag) throws MYException
	{
		FinanceTagBean tagBean = new FinanceTagBean();
		
		tagBean.setType("OUTCOMMON");
		tagBean.setTypeName("销售");
		tagBean.setFullId(out.getFullId());
		tagBean.setInsId(each.getInsId());
		tagBean.setStatsTime(TimeTools.now());
		
		tagBean.setTag(tag);
		
		this.addFinanceTagBeanWithoutTransaction(null, tagBean);
	}
	
	@Override
	public void processOutPayTagData()
	{
        triggerLog.info("processOutPayTagData 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	try{
                		processPayTagInner();
                	}catch(MYException e)
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
      
        triggerLog.info("processOutPayTagData 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
	}
	
	/**
	 * @see "销售单回款标识.doc"
	 * @throws MYException
	 */
	private void processPayTagInner() throws MYException
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addIntCondition("status", "=", 1);
		
		outPayTagDAO.deleteEntityBeansByCondition(con);
		
		List<OutPayTagBean> tagList = outPayTagDAO.queryEntityBeansByCondition("where status in (0,2)");
		
		for (OutPayTagBean each : tagList)
		{
			// 确定付款类型 （永银文化，非永银文化普通， 管理， 前3种混合）
			List<InBillBean> billList = inBillDAO.queryEntityBeansByFK(each.getOutId());
			
			if (ListTools.isEmptyOrNull(billList))
			{
				//throw new MYException("销售单[%s]没有关联收款单", each.getOutId());
				triggerLog.info("销售单["+ each.getOutId() +"]没有关联收款单");
				
				continue;
			}
			
			boolean hybrid = false;
			
			String dutyId = billList.get(0).getDutyId();
			int mtype = billList.get(0).getMtype();
			
			for (InBillBean bill : billList)
			{
				if (!dutyId.equals(bill.getDutyId()))
				{
					hybrid = true;
					break;
				}
			}
			
			// 确定开票类型 结束态 开票
			List<InsVSOutBean> insList = insVSOutDAO.queryEntityBeansByFK(each.getOutId());
			
			String insDutyId = "";
			int insMtype = -1;
			
			// 未开票
			if (!ListTools.isEmptyOrNull(insList))
			{
				for (InsVSOutBean vs : insList)
				{
					InvoiceinsBean insBean = invoiceinsDAO.find(vs.getInsId());
					
					if (null == insBean) {
						continue;
					}
					
					if (insBean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
					{
						insDutyId = insBean.getDutyId();
						insMtype = insBean.getMtype();
						break;
					}
				}
			}
			
			String tag = "";
			
			if (hybrid)
			{
				// 未开票
				if (StringTools.isNullOrNone(insDutyId))
				{
					tag = "HK16";
				}else if (insMtype == PublicConstant.MANAGER_TYPE_MANAGER)
				{
					tag = "HK14";  // 外围
				}else if (insDutyId.equals(PublicConstant.DEFAULR_DUTY_ID))
				{
					tag = "HK15";
				}else{
					tag = "HK13";
				}
				
			}else{
				if (mtype == PublicConstant.MANAGER_TYPE_MANAGER)
				{
					// 未开票
					if (StringTools.isNullOrNone(insDutyId))
					{
						tag = "HK12";
					}else if (insMtype == PublicConstant.MANAGER_TYPE_MANAGER)
					{
						tag = "HK11";
					}else if (insDutyId.equals(PublicConstant.DEFAULR_DUTY_ID))
					{
						tag = "HK9";
					}else{
						tag = "HK10";
					}
				}else if(dutyId.equals(PublicConstant.DEFAULR_DUTY_ID))
				{
					// 未开票
					if (StringTools.isNullOrNone(insDutyId))
					{
						tag = "HK4";
					}else if (insMtype == PublicConstant.MANAGER_TYPE_MANAGER)
					{
						tag = "HK3";
					}else if (insDutyId.equals(PublicConstant.DEFAULR_DUTY_ID))
					{
						tag = "HK1";
					}else{
						tag = "HK2";
					}
				}else{
					// 未开票
					if (StringTools.isNullOrNone(insDutyId))
					{
						tag = "HK8";
					}else if (insMtype == PublicConstant.MANAGER_TYPE_MANAGER)
					{
						tag = "HK7";
					}else if (insDutyId.equals(PublicConstant.DEFAULR_DUTY_ID))
					{
						tag = "HK5";
					}else{
						tag = "HK6";
					}
				}
			}
			
			// 生成标记数据
			if (each.getStatus() == 2) // 未开票第一次导出时，状态为2
			{
				// 本次处理时，发现仍未开票，则不处理
				if (StringTools.isNullOrNone(insDutyId))
				{
					continue;
				}else{
					// 已开票第二次生成标记数据，且状态置为1，表示结束
					saveTagInner(each, tag);
					
					each.setStatus(1);
					
					outPayTagDAO.updateEntityBean(each);
				}
			}else{
				// 未开票第一次导出时，状态为2
				if (StringTools.isNullOrNone(insDutyId))
				{
					saveTagInner(each, tag);
					
					each.setStatus(2);
					
					outPayTagDAO.updateEntityBean(each);
				}else{
					// 已开票第二次生成标记数据，且状态置为1，表示结束
					saveTagInner(each, tag);
					
					each.setStatus(1);
					
					outPayTagDAO.updateEntityBean(each);
				}
			}
		}
	}

	private void saveTagInner(OutPayTagBean each, String tag)
			throws MYException
	{
		FinanceTagBean financeTag = new FinanceTagBean();
		
		financeTag.setType("OUTPAY");
		financeTag.setTypeName("销售单回款");
		financeTag.setFullId(each.getOutId());
		financeTag.setStatsTime(TimeTools.now());
		
		financeTag.setTag(tag);
		
		this.addFinanceTagBeanWithoutTransaction(null, financeTag);
	}
	
	/**
	 * @return the financeTagDAO
	 */
	public FinanceTagDAO getFinanceTagDAO()
	{
		return financeTagDAO;
	}

	/**
	 * @param financeTagDAO the financeTagDAO to set
	 */
	public void setFinanceTagDAO(FinanceTagDAO financeTagDAO)
	{
		this.financeTagDAO = financeTagDAO;
	}

	/**
	 * @return the invoiceinsTagDAO
	 */
	public InvoiceinsTagDAO getInvoiceinsTagDAO()
	{
		return invoiceinsTagDAO;
	}

	/**
	 * @param invoiceinsTagDAO the invoiceinsTagDAO to set
	 */
	public void setInvoiceinsTagDAO(InvoiceinsTagDAO invoiceinsTagDAO)
	{
		this.invoiceinsTagDAO = invoiceinsTagDAO;
	}

	/**
	 * @return the sailTagConfigDAO
	 */
	public SailTagConfigDAO getSailTagConfigDAO()
	{
		return sailTagConfigDAO;
	}

	/**
	 * @param sailTagConfigDAO the sailTagConfigDAO to set
	 */
	public void setSailTagConfigDAO(SailTagConfigDAO sailTagConfigDAO)
	{
		this.sailTagConfigDAO = sailTagConfigDAO;
	}

	/**
	 * @return the insVSOutDAO
	 */
	public InsVSOutDAO getInsVSOutDAO()
	{
		return insVSOutDAO;
	}

	/**
	 * @param insVSOutDAO the insVSOutDAO to set
	 */
	public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
	{
		this.insVSOutDAO = insVSOutDAO;
	}

	/**
	 * @return the invoiceinsDAO
	 */
	public InvoiceinsDAO getInvoiceinsDAO()
	{
		return invoiceinsDAO;
	}

	/**
	 * @param invoiceinsDAO the invoiceinsDAO to set
	 */
	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO)
	{
		this.invoiceinsDAO = invoiceinsDAO;
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
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the outPayTagDAO
	 */
	public OutPayTagDAO getOutPayTagDAO()
	{
		return outPayTagDAO;
	}

	/**
	 * @param outPayTagDAO the outPayTagDAO to set
	 */
	public void setOutPayTagDAO(OutPayTagDAO outPayTagDAO)
	{
		this.outPayTagDAO = outPayTagDAO;
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
}
