package com.china.center.oa.commission.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.commission.bean.BlackOutBean;
import com.china.center.oa.commission.dao.BlackOutDAO;

public class BlackOutDAOImpl extends BaseDAO<BlackOutBean, BlackOutBean> implements BlackOutDAO 
{

    @Override
    public List<BlackOutBean> queryByRefIdAndType(String refId, int type) 
    {
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("BlackOutBean.refId", "=", refId);
        
        con.addIntCondition("BlackOutBean.type", "=", type);
        
        return this.queryEntityBeansByCondition(con);
        
    }

    @Override
    public BlackOutBean findByRefIdAndTypeAndOutId(String refId, int type, String outId) 
    {
        return this.findUnique("where refId = ? and type = ? and outId = ?", refId, type, outId);
    }

    @Override
    public int queryMaxDaysByRefIdAndType(String refId, int type) 
    {
        String sql = "select max(days) as days from T_CENTER_BLACK_OUT where refId = ? and type = ?";
        
        return this.jdbcOperation.queryForInt(
            sql, refId, type);
    }
    
}
