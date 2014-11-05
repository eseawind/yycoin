package com.china.center.oa.product.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.vo.PriceConfigVO;

public interface PriceConfigDAO extends DAO<PriceConfigBean, PriceConfigVO>
{
	List<PriceConfigBean> querySailPricebyProductId(String productId);
	
	List<PriceConfigBean> queryMinPricebyProductIdAndIndustryId(String productId, String industryId);
}
