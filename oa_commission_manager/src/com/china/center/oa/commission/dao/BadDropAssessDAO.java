package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.BadDropAssessBean;

public interface BadDropAssessDAO extends DAO<BadDropAssessBean, BadDropAssessBean> 
{
    BadDropAssessBean findByMonthAndStaffer(String month, String stafferId, int type);
}
