package com.china.center.oa.tcp.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.dao.RebateApplyDAO;
import com.china.center.oa.tcp.vo.RebateApplyVO;

public class RebateApplyDAOImpl extends BaseDAO<RebateApplyBean, RebateApplyVO> implements
		RebateApplyDAO
{
    public int updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, this.claz);
    }
}
