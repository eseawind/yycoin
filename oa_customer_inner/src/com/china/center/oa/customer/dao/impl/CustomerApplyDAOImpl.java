/**
 * File Name: CustomerApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customer.bean.CustomerApplyBean;
import com.china.center.oa.customer.dao.CustomerApplyDAO;
import com.china.center.oa.customer.vo.CustomerApplyVO;


/**
 * CustomerApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerApplyDAOImpl
 * @since 1.0
 */
public class CustomerApplyDAOImpl extends BaseDAO<CustomerApplyBean, CustomerApplyVO> implements CustomerApplyDAO
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
