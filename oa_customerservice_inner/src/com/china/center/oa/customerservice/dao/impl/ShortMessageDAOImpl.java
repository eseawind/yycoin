package com.china.center.oa.customerservice.dao.impl;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customerservice.bean.ShortMessageBean;
import com.china.center.oa.customerservice.dao.ShortMessageDAO;

public class ShortMessageDAOImpl extends BaseDAO<ShortMessageBean, ShortMessageBean> implements ShortMessageDAO
{
    public boolean updateResult(String msgId, String mobile, int result, String description)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set result = ?, description = ? where msgid = ? and mobile = ?";

        jdbcOperation.update(sql, result, description, msgId, mobile);

        return true;
    }
}
