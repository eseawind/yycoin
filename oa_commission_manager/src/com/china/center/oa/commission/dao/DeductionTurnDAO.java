package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.DeductionTurnBean;

public interface DeductionTurnDAO extends DAO<DeductionTurnBean, DeductionTurnBean> 
{
    DeductionTurnBean findByMonthAndStaffer(String month, String stafferId);
}
