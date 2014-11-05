package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.DeductionTurnBean;
import com.china.center.oa.commission.dao.DeductionTurnDAO;

public class DeductionTurnDAOImpl extends BaseDAO<DeductionTurnBean, DeductionTurnBean> implements DeductionTurnDAO 
{

    @Override
    public DeductionTurnBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
