package com.china.center.oa.product.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.ProductChangeRecordBean;
import com.china.center.oa.product.dao.ProductChangeRecordDAO;
import com.china.center.oa.product.vo.ProductChangeRecordVO;

public class ProductChangeRecordDAOImpl extends BaseDAO<ProductChangeRecordBean, ProductChangeRecordVO> implements ProductChangeRecordDAO
{
	
	public List<ProductChangeRecordBean> queryByflowId(ConditionParse condition, int limit)
    {
        condition.removeWhereStr();

        String subQuery = "select distinct(t1.id) as id from T_CENTER_PROVIDE t1, T_CENTER_PRODUCTTYPEVSCUSTOMER t2 where t1.id = t2.customerId "
                          + condition.toString();

        String sql = "select tt1.* from T_CENTER_PROVIDE tt1, (" + subQuery + ") tt2 where tt1.id = tt2.id";

        PageSeparate page = new PageSeparate(limit, limit);

        return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, page, claz);
    }
}
