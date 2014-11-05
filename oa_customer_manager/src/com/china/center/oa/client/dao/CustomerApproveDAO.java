package com.china.center.oa.client.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.vo.CustomerApproveVO;

public interface CustomerApproveDAO extends DAO<CustomerApproveBean, CustomerApproveVO>
{
    int countByCode(String code);

    boolean updateStatus(String id, int status);

    boolean updateCode(String id, String code);
}
