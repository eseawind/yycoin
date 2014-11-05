package com.china.center.oa.commission.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.commission.bean.ExternalAssessBean;
import com.china.center.oa.commission.dao.ExternalAssessDAO;
import com.china.center.oa.commission.vo.ExternalAssessVO;

public class ExternalAssessDAOImpl extends BaseDAO<ExternalAssessBean, ExternalAssessVO> implements ExternalAssessDAO 
{

    @Override
    public ExternalAssessBean findByMonthAndStaffer(String month, String stafferId) 
    {
        return this.findUnique("where month = ? and stafferId = ?", month, stafferId);
    }
    
}
