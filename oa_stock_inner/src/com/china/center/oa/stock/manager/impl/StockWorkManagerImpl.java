package com.china.center.oa.stock.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.bean.StockWorkBean;
import com.china.center.oa.stock.bean.StockWorkCountBean;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.dao.PriceAskProviderDAO;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.dao.StockWorkCountDAO;
import com.china.center.oa.stock.dao.StockWorkDAO;
import com.china.center.oa.stock.manager.StockWorkManager;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.oa.stock.vo.StockVO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class StockWorkManagerImpl implements StockWorkManager
{
	private final static Log triggerLog = LogFactory.getLog("trigger");

	private StockDAO stockDAO = null;
	
	private StockItemDAO stockItemDAO = null;
	
	private StockWorkDAO stockWorkDAO = null;
	
	private StockWorkCountDAO stockWorkCountDAO = null;
	
	private PriceAskProviderDAO priceAskProviderDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private ParameterDAO parameterDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	/**
	 * 
	 */
	public StockWorkManagerImpl()
	{
	}

	@Override
	public void statsStockWork()
	{
        triggerLog.info("statsStockWork 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	try {
                		stockWork();	
                	} catch (MYException e) {
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
      
        triggerLog.info("statsStockWork 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }
	
	private void  stockWork() throws MYException
	{
		List<StockVO> voList = stockDAO.queryEntityVOsByCondition("where StockBean.status = ?", StockConstant.STOCK_STATUS_STOCKMANAGERPASS);
		
		for (StockVO each : voList)
		{
			List<StockItemVO> itemVOList = stockItemDAO.queryEntityVOsByFK(each.getId());
			
			for (StockItemVO eachItem : itemVOList)
			{
				String priceAskId = eachItem.getPriceAskProviderId();
				
				if (StringTools.isNullOrNone(priceAskId))
					continue;
				
				if (eachItem.getHasRef() == StockConstant.STOCK_ITEM_HASREF_YES) {
					continue;
				}
				
				PriceAskProviderBean pap = priceAskProviderDAO.find(priceAskId);
				
				// 确认发货时间  - 供应商确认时间
				String confirmSendDate = pap.getConfirmSendDate();
				String provideConfirmDate = pap.getProvideConfirmDate();
				// 
				if (StringTools.isNullOrNone(confirmSendDate) || StringTools.isNullOrNone(provideConfirmDate))
					continue;
				
				int days = TimeTools.cdate(pap.getConfirmSendDate(), pap.getProvideConfirmDate());
				
				// rules
				/**
				 * rule 1:
				 * days <= 5, 每天生成
				 * 5 < days <= 15, 20% * days , 50% * days, 近3天每天生成 
				 * days >= 16 , 20% * days , 50% * days , 80% * days, 近3天每天生成
				 * 
				 * rule 2:
				 * 如果对任务进行处理过，且更改了发货日期，则上面规则不生效，改为每天生成，直到拿到货
				 */
				
				// 根据 stockId, stockItemId 确认是否存在更改过发货日期的任务 - 每日生成任务单
				if (stockWorkDAO.ifExistsChangeSendDate(each.getId(), eachItem.getId()))
				{
					saveStockWorkInner(each, eachItem, pap);
				}else {
					String now = TimeTools.now_short();
					
					if (days <= 5)
					{
						saveStockWorkInner(each, eachItem, pap);
					}else if (days > 5 && days <= 15)
					{
						int day1 = Math.round(days * 0.2f);
						int day2 = Math.round(days * 0.5f);
						
						boolean has = false;
						
						if (TimeTools.cdate(now, pap.getProvideConfirmDate()) == day1
								|| TimeTools.cdate(now, pap.getProvideConfirmDate()) == day2)
						{
							has = true;
							
							saveStockWorkInner(each, eachItem, pap);
						}
						
						if (TimeTools.cdate(pap.getConfirmSendDate(), now) <= 3 
								&& TimeTools.cdate(pap.getConfirmSendDate(), now) >= 0)
						{
							if (!has){
								saveStockWorkInner(each, eachItem, pap);
							}
						}
						
					}else {
						int day1 = Math.round(days * 0.2f);
						int day2 = Math.round(days * 0.5f);
						int day3 = Math.round(days * 0.8f);
						
						boolean has = false;
						
						if (TimeTools.cdate(now, pap.getProvideConfirmDate()) == day1
								|| TimeTools.cdate(now, pap.getProvideConfirmDate()) == day2
								|| TimeTools.cdate(now, pap.getProvideConfirmDate()) == day3)
						{
							has = true;
							
							saveStockWorkInner(each, eachItem, pap);
						}
						
						if (TimeTools.cdate(pap.getConfirmSendDate(), now) <= 3 
								&& TimeTools.cdate(pap.getConfirmSendDate(), now) >= 0)
						{
							if (!has){
								saveStockWorkInner(each, eachItem, pap);
							}
						}
					}
				}
			}
		}
	}
	
	private void saveStockWorkInner(StockVO vo, StockItemVO itemVO, PriceAskProviderBean pap)
	{
		StockWorkBean bean = new StockWorkBean();
		
		bean.setId(commonDAO.getSquenceString20());
		bean.setTarget(vo.getTarget());
		
		bean.setStockId(vo.getId());
		bean.setStockItemId(itemVO.getId());
		bean.setProductId(itemVO.getProductId());
		bean.setProductName(itemVO.getProductName());
		bean.setProvideId(itemVO.getProviderId());
		bean.setProvideName(itemVO.getProviderName());
		bean.setConfirmSendDate(pap.getConfirmSendDate());
		bean.setProvideConfirmDate(pap.getProvideConfirmDate());
		
		bean.setNeedTime(vo.getNeedTime());
		bean.setWorkDate(TimeTools.now_short());
		bean.setLogTime(TimeTools.now());
		
		if (vo.getMode() == StockConstant.STOCK_MODE_SAIL)
		{
			String follower = parameterDAO.getString("STOCK_WORK_FOLLOWER");
			
			bean.setStafferName(follower);
			
			if (!StringTools.isNullOrNone(follower))
			{
				StafferBean sb = stafferDAO.findByUnique(follower);
				
				if (null != sb){
					bean.setStafferId(sb.getId());
				}
			}
		}else {
			bean.setStafferName(vo.getUserName());
			bean.setStafferId(vo.getStafferId());
		}
		
		StockWorkBean lbean = stockWorkDAO.getLatestSendDate(vo.getId(), itemVO.getId());
		
		if (null != lbean)
		{
			bean.setNewSendDate(lbean.getSendDate());
		}
		
		stockWorkDAO.saveEntityBean(bean);
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean handleStockWork(String userId, StockWorkBean bean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, bean, bean.getId());
		
		String id = bean.getId();
		
		StockWorkBean obean = stockWorkDAO.find(id);
		
		if (null == obean)
		{
			throw new MYException("数据错误");
		}
		
		obean.setStatus(StockConstant.STOCKWORK_STATUS_END);
		obean.setConnector(bean.getConnector());
		obean.setWay(bean.getWay());
		obean.setFollowPlan(bean.getFollowPlan());
		obean.setDeliveryDate(bean.getDeliveryDate());
		obean.setTechnology(bean.getTechnology());
		obean.setPay(bean.getPay());
		obean.setSendDate(bean.getSendDate());
		obean.setDescription(bean.getDescription());
		
		// 发货日期变更时，标记该任务中的发货日期为最新的
		if (!StringTools.isNullOrNone(bean.getSendDate()))
		{
			stockWorkDAO.updateIsNew(obean.getStockId(), obean.getStockItemId());
			
			obean.setIsNew(1);
			obean.setNewSendDate(bean.getSendDate());
		}
		
		stockWorkDAO.updateEntityBean(obean);
		
		// 计算跟单次数（stockId + stockItemId)
		boolean count = stockWorkCountDAO.updateCount(obean.getStockId(), obean.getStockItemId());
		
		if (!count)
		{
			StockWorkCountBean countBean = new StockWorkCountBean();
			
			countBean.setStockId(obean.getStockId());
			countBean.setStockItemId(obean.getStockItemId());
			countBean.setCount(1);
			
			stockWorkCountDAO.saveEntityBean(countBean);
		}
		
		return true;
	}
	
	/**
	 * @return the stockDAO
	 */
	public StockDAO getStockDAO()
	{
		return stockDAO;
	}

	/**
	 * @param stockDAO the stockDAO to set
	 */
	public void setStockDAO(StockDAO stockDAO)
	{
		this.stockDAO = stockDAO;
	}

	/**
	 * @return the stockItemDAO
	 */
	public StockItemDAO getStockItemDAO()
	{
		return stockItemDAO;
	}

	/**
	 * @param stockItemDAO the stockItemDAO to set
	 */
	public void setStockItemDAO(StockItemDAO stockItemDAO)
	{
		this.stockItemDAO = stockItemDAO;
	}

	/**
	 * @return the stockWorkDAO
	 */
	public StockWorkDAO getStockWorkDAO()
	{
		return stockWorkDAO;
	}

	/**
	 * @param stockWorkDAO the stockWorkDAO to set
	 */
	public void setStockWorkDAO(StockWorkDAO stockWorkDAO)
	{
		this.stockWorkDAO = stockWorkDAO;
	}

	/**
	 * @return the stockWorkCountDAO
	 */
	public StockWorkCountDAO getStockWorkCountDAO()
	{
		return stockWorkCountDAO;
	}

	/**
	 * @param stockWorkCountDAO the stockWorkCountDAO to set
	 */
	public void setStockWorkCountDAO(StockWorkCountDAO stockWorkCountDAO)
	{
		this.stockWorkCountDAO = stockWorkCountDAO;
	}

	/**
	 * @return the priceAskProviderDAO
	 */
	public PriceAskProviderDAO getPriceAskProviderDAO()
	{
		return priceAskProviderDAO;
	}

	/**
	 * @param priceAskProviderDAO the priceAskProviderDAO to set
	 */
	public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
	{
		this.priceAskProviderDAO = priceAskProviderDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
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
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO()
	{
		return parameterDAO;
	}

	/**
	 * @param parameterDAO the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO)
	{
		this.parameterDAO = parameterDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}
}
