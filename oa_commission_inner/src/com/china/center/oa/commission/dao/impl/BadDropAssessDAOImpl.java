package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.BadDropAssessBean;
import com.china.center.oa.commission.dao.BadDropAssessDAO;

public class BadDropAssessDAOImpl extends BaseDAO<BadDropAssessBean, BadDropAssessBean> implements BadDropAssessDAO 
{

    @Override
    public BadDropAssessBean findByMonthAndStaffer(String month, String stafferId, int type)
    {
        return this.findUnique("where month = ? and stafferId = ? and type = ?", month, stafferId, type);
    
    }
    
}
