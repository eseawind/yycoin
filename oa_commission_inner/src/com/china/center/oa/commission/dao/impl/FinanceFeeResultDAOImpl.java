package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.FinanceFeeResultBean;
import com.china.center.oa.commission.dao.FinanceFeeResultDAO;

public class FinanceFeeResultDAOImpl extends BaseDAO<FinanceFeeResultBean, FinanceFeeResultBean> implements FinanceFeeResultDAO 
{

    @Override
    public FinanceFeeResultBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
