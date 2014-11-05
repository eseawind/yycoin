package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CommissionBean;
import com.china.center.oa.commission.vo.CommissionVO;

public interface CommissionDAO extends DAO<CommissionBean, CommissionVO> 
{
    CommissionVO findVOByMonthAndStaffer(String month, String stafferId);
}
