package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.FinanceFeeStatsDetailBean;
import com.china.center.oa.commission.vo.FinanceFeeStatsDetailVO;

public interface FinanceFeeStatsDetailDAO extends DAO<FinanceFeeStatsDetailBean, FinanceFeeStatsDetailVO> 
{
    boolean updateUsedByMonthAndStafferIdAndType(String month, String stafferId, int type, int used);
}
