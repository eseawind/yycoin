package com.china.center.oa.product.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.vo.PriceConfigVO;

public class PriceConfigDAOImpl extends BaseDAO<PriceConfigBean, PriceConfigVO> implements PriceConfigDAO
{
	public List<PriceConfigBean> querySailPricebyProductId(String productId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("PriceConfigBean.productId", "=", productId);
		
		con.addIntCondition("PriceConfigBean.type", "=", ProductConstant.PRICECONFIG_SETTLE);
		
		return this.queryEntityBeansByCondition(con);
	}

	public List<PriceConfigBean> queryMinPricebyProductIdAndIndustryId(
			String productId, String industryId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("PriceConfigBean.productId", "=", productId);
		
		con.addIntCondition("PriceConfigBean.type", "=", ProductConstant.PRICECONFIG_SAIL);
		
		con.addCondition("and PriceConfigBean.industryId like '%" + industryId + "%'");
		
		return this.queryEntityBeansByCondition(con);
	}
	
}
