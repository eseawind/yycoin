package com.china.center.oa.product.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.GoldSilverPriceBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.GoldSilverPriceDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;

public class PriceConfigManagerImpl implements PriceConfigManager
{
	private final Log operationLog = LogFactory.getLog("opr");
	
	private PriceConfigDAO priceConfigDAO = null;
	
	private GoldSilverPriceDAO goldSilverPriceDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private ProductDAO productDAO = null;
	
	public PriceConfigManagerImpl()
	{
		
	}
	
	@Transactional(rollbackFor=MYException.class)
	public boolean addBean(PriceConfigBean bean, User user) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean, user);
		
		check(bean);
		
		String id = commonDAO.getSquenceString20();
		
		bean.setId(id);
				
		priceConfigDAO.saveEntityBean(bean);
		
		operationLog.info(user.getStafferName()+".将产品价格配置增加："+ bean);
		
		return true;
	}

	@Transactional(rollbackFor=MYException.class)
	public boolean updateBean(PriceConfigBean bean, User user)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean, user);
		
		PriceConfigBean oldBean = priceConfigDAO.find(bean.getId());
		
		if (null == oldBean)
		{
			throw new MYException("数据错误");
		}
		
		priceConfigDAO.deleteEntityBean(bean.getId());
				
		check(bean);
		
		priceConfigDAO.saveEntityBean(bean);
		
		operationLog.info(user.getStafferName()+".将产品价格配置更新，旧的价格."+ oldBean + ".新的价格."+bean);		
		
		return true;
	}

	public PriceConfigBean calcSailPrice(PriceConfigBean bean)
	{
		ProductBean product = productDAO.find(bean.getProductId());
		
		if (null == product)
		{
			return null;
		}

		double gold = 0.0d;
		double silver = 0.0d;
		
		List<GoldSilverPriceBean> gspList = goldSilverPriceDAO.listEntityBeans();
		
		if (!ListTools.isEmptyOrNull(gspList))
		{
			GoldSilverPriceBean gspBean = gspList.get(0);
			
			gold = gspBean.getGold();
			
			silver = gspBean.getSilver();
		}
		
		double sailPrice = product.getCost() * gold + product.getPlanCost() * silver + bean.getPrice() + bean.getGsPriceUp();
		
		// 不受金银价波动影响
		if (bean.getFtype() == 1)
		{
			sailPrice = bean.getPrice() + bean.getGsPriceUp();
		}
			
		bean.setGold(product.getCost() * gold);
		bean.setSilver(product.getPlanCost() * silver);
		bean.setSailPrice(sailPrice);
		
		return bean;
		
	}

	/**
	 * 校验
	 * @param bean
	 * @throws MYException
	 */
	private void check(PriceConfigBean bean) throws MYException
	{
		// 根据产品，是否随金银价波动，事业部，职员查找出当前的数据
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("PriceConfigBean.productId", "=", bean.getProductId());
		
		if (bean.getType() == ProductConstant.PRICECONFIG_SAIL)
		{
			con.addCondition("and PriceConfigBean.industryId like '%" + bean.getIndustryId() + "%'");	
		}
		
		con.addIntCondition("PriceConfigBean.type", "=", bean.getType());
		
		List<PriceConfigBean> list = priceConfigDAO.queryEntityBeansByCondition(con);
		
		if (list.size() > 0)
		{
			throw new MYException("同一产品同一事业部只能有一条配置信息");
		}
		
	}
	
	@Transactional(rollbackFor=MYException.class)
	public boolean deleteBean(String id, User user) throws MYException
	{
		JudgeTools.judgeParameterIsNull(id, user);
		
		PriceConfigBean bean = priceConfigDAO.find(id);
		
		if (null == bean)
		{
			throw new MYException("数据错误");
		}
		
		priceConfigDAO.deleteEntityBean(id);
		
		operationLog.info(user.getStafferName()+".将产品价格配置删除："+ bean);
		
		return true;
	}

	/**
	 * 获取结算价
	 * 
	 */
	public PriceConfigBean getPriceConfigBean(String productId, String industryId, String stafferId)
	{
		List<PriceConfigBean> list = priceConfigDAO.queryMinPricebyProductIdAndIndustryId(productId, industryId);
		
		PriceConfigBean bean = null;
		
		if (!ListTools.isEmptyOrNull(list))
		{
			bean = list.get(0);
			
			List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
			
			if (ListTools.isEmptyOrNull(list1))
			{
				ProductBean product = productDAO.find(bean.getProductId());
				
				if (null == product)
				{
					bean.setPrice(0);
				}
				else
				{
					bean.setPrice(product.getSailPrice());
				}
			}
			else
			{
				PriceConfigBean cb = this.calcSailPrice(list1.get(0));
				
				if (null != cb)
					bean.setPrice(cb.getSailPrice());
				else
					bean.setPrice(0);
			}
		}
		else
		{
			List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
			
			if (!ListTools.isEmptyOrNull(list1))
			{
				bean = list1.get(0);
				
				PriceConfigBean cb = this.calcSailPrice(list1.get(0));
				
				if (null != cb)
					bean.setPrice(cb.getSailPrice());
				else
					bean.setPrice(0);

				bean.setMinPrice(0);
			}
		}
		
		return bean;
	}

	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public GoldSilverPriceDAO getGoldSilverPriceDAO()
	{
		return goldSilverPriceDAO;
	}

	public void setGoldSilverPriceDAO(GoldSilverPriceDAO goldSilverPriceDAO)
	{
		this.goldSilverPriceDAO = goldSilverPriceDAO;
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
