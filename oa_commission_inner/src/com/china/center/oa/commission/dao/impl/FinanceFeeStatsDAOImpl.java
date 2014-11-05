package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.FinanceFeeStatsBean;
import com.china.center.oa.commission.dao.FinanceFeeStatsDAO;

public class FinanceFeeStatsDAOImpl extends BaseDAO<FinanceFeeStatsBean, FinanceFeeStatsBean> implements FinanceFeeStatsDAO 
{

    @Override
    public FinanceFeeStatsBean findByTypeAndMonthAndStaffer(int type, String month, String stafferId) 
    {
        return this.findUnique("where type=? and month=? and stafferId=?", type, month, stafferId);
    }
    
}
