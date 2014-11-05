package com.china.center.oa.client.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.dao.CustomerApproveDAO;
import com.china.center.oa.client.vo.CustomerApproveVO;

public class CustomerApproveDAOImpl extends BaseDAO<CustomerApproveBean, CustomerApproveVO> implements CustomerApproveDAO
{
    /**
     * 统计code
     * 
     * @param code
     * @return
     */
    public int countByCode(String code)
    {
        return this.jdbcOperation.queryForInt("where code = ?", claz, code);
    }

    /**
     * 修改申请状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }

    public boolean updateCode(String id, String code)
    {
        this.jdbcOperation.updateField("code", code, id, claz);

        return true;
    }
}
