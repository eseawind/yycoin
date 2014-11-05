package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CommissionFreezeBean;
import com.china.center.oa.commission.vo.CommissionFreezeVO;

public interface CommissionFreezeDAO extends DAO<CommissionFreezeBean, CommissionFreezeVO> 
{
    CommissionFreezeBean findByMonthAndStaffer(String month, String stafferId);
}
