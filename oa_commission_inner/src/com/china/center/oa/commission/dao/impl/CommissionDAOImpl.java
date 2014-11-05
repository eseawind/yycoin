package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CommissionBean;
import com.china.center.oa.commission.dao.CommissionDAO;
import com.china.center.oa.commission.vo.CommissionVO;

public class CommissionDAOImpl extends BaseDAO<CommissionBean, CommissionVO> implements CommissionDAO 
{

    @Override
    public CommissionVO findVOByMonthAndStaffer(String month, String stafferId)
    {
        return this.findUniqueVO("where month=? and stafferId=?", month, stafferId);
    }
    
}
