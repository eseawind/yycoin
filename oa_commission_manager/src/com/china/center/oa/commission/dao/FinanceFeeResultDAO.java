package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.FinanceFeeResultBean;

public interface FinanceFeeResultDAO extends DAO<FinanceFeeResultBean, FinanceFeeResultBean> 
{

    FinanceFeeResultBean findByMonthAndStaffer(String month, String stafferId);
}
