/**
 * File Name: ProviderDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.vo.ProviderVO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.tools.ListTools;


/**
 * ProviderDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderDAOImpl
 * @since 1.0
 */
public class ProviderDAOImpl extends BaseDAO<ProviderBean, ProviderVO> implements ProviderDAO
{
    /**
     * 是否供应商在out里面被引用
     * 
     * @param providerId
     * @return
     */
    public int countProviderInOut(String providerId)
    {
        String sql = "select count(1) from t_center_out where type = 1 and customerId = ?";

        return this.jdbcOperation.queryForInt(sql, providerId);
    }

    public int countProviderByName(String name)
    {
        return this.jdbcOperation.queryForInt("where name = ?", claz, name);
    }

    public List<ProviderBean> queryByLimit(ConditionParse condition, int limit)
    {
        condition.removeWhereStr();

        String subQuery = "select distinct(t1.id) as id from T_CENTER_PROVIDE t1, T_CENTER_PRODUCTTYPEVSCUSTOMER t2 where t1.id = t2.customerId "
                          + condition.toString();

        String sql = "select tt1.* from T_CENTER_PROVIDE tt1, (" + subQuery + ") tt2 where tt1.id = tt2.id";

        PageSeparate page = new PageSeparate(limit, limit);

        return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, page, claz);
    }
    
    public List<ProviderBean> findProviderByName(String name)
    {
        List<ProviderBean> list = this.jdbcOperation.queryForList("where name = ?", claz, name);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list;
    }
}
