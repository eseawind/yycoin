package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.FinanceFeeStatsBean;

public interface FinanceFeeStatsDAO extends DAO<FinanceFeeStatsBean, FinanceFeeStatsBean> 
{
    FinanceFeeStatsBean findByTypeAndMonthAndStaffer(int type, String month, String stafferId);
}
