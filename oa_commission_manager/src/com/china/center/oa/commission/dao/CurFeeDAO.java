package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CurFeeBean;

public interface CurFeeDAO extends DAO<CurFeeBean, CurFeeBean> 
{
    CurFeeBean findByMonthAndStaffer(String month, String stafferId);
}
