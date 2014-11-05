package com.china.center.oa.commission.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.BlackOutBean;

public interface BlackOutDAO extends DAO<BlackOutBean, BlackOutBean> 
{

    List<BlackOutBean> queryByRefIdAndType(String refId, int type);
    
    BlackOutBean findByRefIdAndTypeAndOutId(String refId,int type, String outId);
    
    int queryMaxDaysByRefIdAndType(String refId, int type);
    
}
