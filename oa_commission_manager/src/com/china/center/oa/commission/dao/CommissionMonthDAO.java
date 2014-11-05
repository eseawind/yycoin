package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CommissionMonthBean;
import com.china.center.oa.commission.vo.CommissionMonthVO;

public interface CommissionMonthDAO extends DAO<CommissionMonthBean, CommissionMonthVO> 
{

    CommissionMonthBean findLastBean();
}
