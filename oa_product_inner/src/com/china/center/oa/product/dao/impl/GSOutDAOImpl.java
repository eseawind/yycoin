package com.china.center.oa.product.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.dao.GSOutDAO;
import com.china.center.oa.product.vo.GSOutVO;

public class GSOutDAOImpl extends BaseDAO<GSOutBean, GSOutVO> implements GSOutDAO
{
	public boolean modifyStatus(String id, int status)
    {
        jdbcOperation.updateField("status", status, id, this.claz);

        return true;
    }
}
