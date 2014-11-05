package com.china.center.oa.commission.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.CurProfitBean;
import com.china.center.oa.commission.wrap.ProfitVSStafferWrap;

public interface CurProfitDAO extends DAO<CurProfitBean, CurProfitBean> 
{

    CurProfitBean findByMonthAndStaffer(String month, String stafferId);
    
    List<ProfitVSStafferWrap> queryProfitVSStaffer(String month);
}
