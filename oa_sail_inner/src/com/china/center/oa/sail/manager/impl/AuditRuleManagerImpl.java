package com.china.center.oa.sail.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.sail.bean.AuditRuleBean;
import com.china.center.oa.sail.bean.AuditRuleItemBean;
import com.china.center.oa.sail.dao.AuditRuleDAO;
import com.china.center.oa.sail.dao.AuditRuleItemDAO;
import com.china.center.oa.sail.manager.AuditRuleManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;

public class AuditRuleManagerImpl implements AuditRuleManager
{

	private final Log operationLog = LogFactory.getLog("opr");
	
	private AuditRuleDAO auditRuleDAO = null;
	
	private AuditRuleItemDAO auditRuleItemDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private ProductDAO productDAO = null;
	
	/**
	 * 增加
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addBean(AuditRuleBean bean, User user) throws MYException
	{
		
		JudgeTools.judgeParameterIsNull(bean, user);
		
		List<AuditRuleItemBean> itemList = bean.getItemList();
		
		checkBean(bean, itemList);
		
		String id = commonDAO.getSquenceString20();
		
		bean.setId(id);
		
		auditRuleDAO.saveEntityBean(bean);
		
		for (AuditRuleItemBean each : itemList)
		{
			each.setRefId(id);
			
			auditRuleItemDAO.saveEntityBean(each);
		}
		
		operationLog.info("ADD:AuditRuleBean //" + bean +", 操作人: " + user.getStafferName() );
		
		return true;
	}

	/**
	 * checkBean
	 * 
	 * @param bean
	 * @param itemList
	 * @throws MYException
	 */
	private void checkBean(AuditRuleBean bean, List<AuditRuleItemBean> itemList)
			throws MYException
	{
		//同一事业部,同一销售类型下，管理类型，产品类型，材质类型，纸币类型，产品，付款条件，退货条件，低于售价不能重复
		ConditionParse condition = new ConditionParse();
		
		condition.addWhereStr();
		
		condition.addCondition("AuditRuleBean.industryid", "=", bean.getIndustryId());
		
		condition.addIntCondition("AuditRuleBean.sailType", "=", bean.getSailType());
		
		List<AuditRuleBean> list = auditRuleDAO.queryEntityBeansByCondition(condition);
		
		List<AuditRuleItemBean> itemLists = new ArrayList<AuditRuleItemBean>();
		
		if (!ListTools.isEmptyOrNull(list))
		{
			for (AuditRuleBean each : list)
			{
				List<AuditRuleItemBean> alist = auditRuleItemDAO.queryEntityBeansByFK(each.getId());
				
				itemLists.addAll(alist);
			}
		}		
		
		if (!ListTools.isEmptyOrNull(itemLists))
		{
			for (AuditRuleItemBean each : itemList)
			{
				for (AuditRuleItemBean eachItem : itemLists)
				{
					if (each.getManagerType() == eachItem.getManagerType()
							&& each.getProductType() == eachItem.getProductType()
							&& each.getMateriaType() == eachItem.getMateriaType()
							&& each.getCurrencyType() == eachItem.getCurrencyType()
							&& each.getProductId().equals(eachItem.getProductId())
							&& each.getPayCondition() == eachItem.getPayCondition()
							&& each.getReturnCondition() == eachItem.getReturnCondition()
							&& each.getLtSailPrice() == eachItem.getLtSailPrice())
					{
						throw new MYException("同一事业部,同一销售类型下，管理类型，产品类型，材质类型，纸币类型，产品，付款条件，退货条件，低于售价不能重复");
					}
				}
			}
		}

	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateBean(AuditRuleBean bean, User user) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean, user);
		
		AuditRuleBean oldBean = auditRuleDAO.find(bean.getId());
		
		if (null == oldBean)
		{
			throw new MYException("数据错误");
		}
		
		auditRuleDAO.deleteEntityBean(bean.getId());
		
		auditRuleItemDAO.deleteEntityBeansByFK(bean.getId());
		
		operationLog.info("UPDATE:OLD AuditRuleBean //" + oldBean +", 操作人: " + user.getStafferName() );
		
		List<AuditRuleItemBean> itemList = bean.getItemList();
		
		checkBean(bean, itemList);
		
		String id = commonDAO.getSquenceString20();
		
		bean.setId(id);
		
		auditRuleDAO.saveEntityBean(bean);
		
		for (AuditRuleItemBean each : itemList)
		{
			each.setRefId(id);
			
			auditRuleItemDAO.saveEntityBean(each);
		}
		
		operationLog.info("UPDATE:NEW AuditRuleBean //" + bean +", 操作人: " + user.getStafferName() );
		
		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean deleteBean(String id, User user) throws MYException
	{

		JudgeTools.judgeParameterIsNull(id, user);
		
		AuditRuleBean bean = auditRuleDAO.find(id);
		
		if (null == bean)
		{
			throw new MYException("数据错误");
		}
		
		auditRuleDAO.deleteEntityBean(id);
		
		auditRuleItemDAO.deleteEntityBeansByFK(id);
		
		operationLog.info("DEL:AuditRuleBean //" + bean +", 操作人: " + user.getStafferName() );
		
		return true;
	}

	/**
	 * 获取具体的审核数据
	 * 优先级：产品>纸币类型>材质类型>产品类型>管理类型
	 * 
	 */
	public AuditRuleItemBean getAuditRuleItem(String productId, String industryId,
			int outType, int payType)
	{
		List<AuditRuleItemBean> itemList = auditRuleItemDAO.queryAuditRuleItems(industryId, outType, payType);
		
		ProductBean productBean = productDAO.find(productId);
		
		if (null == productBean)
			return null;
		
		// 根据产品的优先级获取审核项 - 优先级顺序为：品名、纸币类型、材质类型、产品类型、管理类型
		if (ListTools.isEmptyOrNull(itemList))
			return null;
		
		AuditRuleItemBean ariBean = null;
		
		// 产品
		for (AuditRuleItemBean each : itemList)
		{
			if (each.getProductId().equals(productId))
			{
				ariBean = each;
				
				break;
			}
			
			if (each.getCurrencyType() == productBean.getMinAmount())
			{
				ariBean = each;
				
				break;
			}
			
			if (each.getMateriaType() == productBean.getCheckDays())
			{
				ariBean = each;
				
				break;
			}
			
			if (each.getProductType() == productBean.getType())
			{
				ariBean = each;
				
				break;
			}
			
			if (each.getManagerType() == MathTools.parseInt(productBean.getReserve4()))
			{
				ariBean = each;
				
				break;
			}
			
		}
		
		return ariBean;
	}
	
	public AuditRuleDAO getAuditRuleDAO()
	{
		return auditRuleDAO;
	}

	public void setAuditRuleDAO(AuditRuleDAO auditRuleDAO)
	{
		this.auditRuleDAO = auditRuleDAO;
	}

	public AuditRuleItemDAO getAuditRuleItemDAO()
	{
		return auditRuleItemDAO;
	}

	public void setAuditRuleItemDAO(AuditRuleItemDAO auditRuleItemDAO)
	{
		this.auditRuleItemDAO = auditRuleItemDAO;
	}
	
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

}
