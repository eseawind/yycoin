package com.china.center.oa.commission.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CommissionMonthBean;
import com.china.center.oa.commission.dao.CommissionMonthDAO;
import com.china.center.oa.commission.vo.CommissionMonthVO;
import com.china.center.tools.ListTools;

public class CommissionMonthDAOImpl extends BaseDAO<CommissionMonthBean, CommissionMonthVO> implements CommissionMonthDAO 
{

    @Override
    public CommissionMonthBean findLastBean() 
    {
        List<CommissionMonthBean> list = this.listEntityBeansByOrder("order by month desc");
        
        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }
        
        return list.get(0);
    }
    
}
