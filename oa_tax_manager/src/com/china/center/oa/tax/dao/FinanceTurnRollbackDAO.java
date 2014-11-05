package com.china.center.oa.tax.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceTurnRollbackBean;

public interface FinanceTurnRollbackDAO extends DAO<FinanceTurnRollbackBean, FinanceTurnRollbackBean> 
{
    FinanceTurnRollbackBean findLastBean();
}
