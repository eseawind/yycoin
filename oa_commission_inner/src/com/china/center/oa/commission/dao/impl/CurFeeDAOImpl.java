package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CurFeeBean;
import com.china.center.oa.commission.dao.CurFeeDAO;

public class CurFeeDAOImpl extends BaseDAO<CurFeeBean, CurFeeBean> implements CurFeeDAO 
{

    @Override
    public CurFeeBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
