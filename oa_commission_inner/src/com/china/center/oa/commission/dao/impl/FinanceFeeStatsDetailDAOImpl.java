package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.FinanceFeeStatsDetailBean;
import com.china.center.oa.commission.dao.FinanceFeeStatsDetailDAO;
import com.china.center.oa.commission.vo.FinanceFeeStatsDetailVO;

public class FinanceFeeStatsDetailDAOImpl extends BaseDAO<FinanceFeeStatsDetailBean, FinanceFeeStatsDetailVO> implements
        FinanceFeeStatsDetailDAO 
{

    @Override
    public boolean updateUsedByMonthAndStafferIdAndType(String month, String stafferId, int type, int used) 
    {        
        String sql = "update T_CENTER_FINANCE_FEE_DETAIL set used = ? where month = ? and stafferId = ? and type = ?";

        jdbcOperation.update(sql, used, month, stafferId, type );
        
        return true ;
    }    
   
}
