package com.china.center.oa.commission.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.commission.bean.ExternalAssessBean;
import com.china.center.oa.commission.vo.ExternalAssessVO;

public interface ExternalAssessDAO extends DAO<ExternalAssessBean, ExternalAssessVO> 
{
    ExternalAssessBean findByMonthAndStaffer(String month, String stafferId);
}
