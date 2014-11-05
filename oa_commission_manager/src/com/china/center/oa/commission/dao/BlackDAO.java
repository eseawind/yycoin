package com.china.center.oa.commission.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.BlackBean;
import com.china.center.oa.commission.vo.BlackVO;

public interface BlackDAO extends DAO<BlackBean, BlackVO> 
{
    List<BlackBean> queryByLogDate(String logDate);
    
    void backup();
}
