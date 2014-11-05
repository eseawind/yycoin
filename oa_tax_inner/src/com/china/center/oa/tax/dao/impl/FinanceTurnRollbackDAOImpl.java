package com.china.center.oa.tax.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tax.bean.FinanceTurnRollbackBean;
import com.china.center.oa.tax.dao.FinanceTurnRollbackDAO;
import com.china.center.oa.tax.vo.FinanceTurnVO;
import com.china.center.tools.ListTools;

public class FinanceTurnRollbackDAOImpl extends BaseDAO<FinanceTurnRollbackBean, FinanceTurnRollbackBean> implements FinanceTurnRollbackDAO 
{
    public FinanceTurnRollbackBean findLastBean()
    {
        List<FinanceTurnRollbackBean> list = this.listEntityBeansByOrder("order by monthKey desc");

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }
}
