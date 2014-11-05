package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CurMoneyBean;

public interface CurMoneyDAO extends DAO<CurMoneyBean, CurMoneyBean> 
{

    CurMoneyBean findByMonthAndStaffer(String month, String stafferId);
}
