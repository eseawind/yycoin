package com.china.center.oa.product.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductChangeRecordBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.vo.ProductChangeRecordVO;

public interface ProductChangeRecordDAO extends DAO<ProductChangeRecordBean, ProductChangeRecordVO>
{

	public List<ProductChangeRecordBean> queryByflowId(ConditionParse condition, int limit);
}
