package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CommissionFreezeBean;
import com.china.center.oa.commission.dao.CommissionFreezeDAO;
import com.china.center.oa.commission.vo.CommissionFreezeVO;

public class CommissionFreezeDAOImpl extends BaseDAO<CommissionFreezeBean, CommissionFreezeVO> implements CommissionFreezeDAO 
{

    @Override
    public CommissionFreezeBean findByMonthAndStaffer(String month, String stafferId) 
    {
        
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
