package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CurMoneyBean;
import com.china.center.oa.commission.dao.CurMoneyDAO;

public class CurMoneyDAOImpl extends BaseDAO<CurMoneyBean, CurMoneyBean> implements CurMoneyDAO 
{

    @Override
    public CurMoneyBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
