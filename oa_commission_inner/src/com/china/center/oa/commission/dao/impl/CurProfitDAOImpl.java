package com.china.center.oa.commission.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.CurProfitBean;
import com.china.center.oa.commission.dao.CurProfitDAO;
import com.china.center.oa.commission.wrap.ProfitVSStafferWrap;

public class CurProfitDAOImpl extends BaseDAO<CurProfitBean, CurProfitBean> implements CurProfitDAO 
{

    @Override
    public CurProfitBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }

    @Override
    public List<ProfitVSStafferWrap> queryProfitVSStaffer(String month) 
    {
        String sql = "select a.years as years , a.stafferName as stafferName, b.money as curMoney, c.moneys as allMoneys, b.month as month"
            + " from T_CENTER_EXTERNAL_STAFFERYEAR a"
            + " left outer join"
            + " T_CENTER_CURPROFIT b on (a.stafferid = b.stafferid)"
            + " left outer join"
            + " T_CENTER_ALLPROFIT c on (a.stafferid = c.stafferid) where b.month = ?" ;
      
        
        final List<ProfitVSStafferWrap> result = new LinkedList<ProfitVSStafferWrap>();

        this.jdbcOperation.query(sql, new Object[] {month},
            new RowCallbackHandler()
            {

                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    ProfitVSStafferWrap wrap = new ProfitVSStafferWrap();
                    
                    String years = rst.getString("years");
                    String stafferName = rst.getString("stafferName");
                    double curMoney = rst.getDouble("curMoney");
                    double allMoneys = rst.getDouble("allMoneys");
                    String month = rst.getString("month");
                    
                    wrap.setYears(years);
                    wrap.setStafferName(stafferName);
                    wrap.setCurMoney(curMoney);
                    wrap.setAllMoneys(allMoneys);
                    wrap.setMonth(month);
                    
                    result.add(wrap);
                }
            });

        return result;
        
    }
    
}
