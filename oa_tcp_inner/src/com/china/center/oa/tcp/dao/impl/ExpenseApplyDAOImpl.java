/**
 * File Name: ExpenseApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;


/**
 * ExpenseApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-9-22
 * @see ExpenseApplyDAOImpl
 * @since 3.0
 */
public class ExpenseApplyDAOImpl extends BaseDAO<ExpenseApplyBean, ExpenseApplyVO> implements ExpenseApplyDAO
{
    public int updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, this.claz);
    }

    public int updateBorrowTotal(String id, long borrowTotal)
    {
        return this.jdbcOperation.updateField("borrowTotal", borrowTotal, id, this.claz);
    }

    public int updateTotal(String id, long total)
    {
        return this.jdbcOperation.updateField("total", total, id, this.claz);
    }

    public int updateBorrowStafferId(String id, String borrowStafferId)
    {
        return this.jdbcOperation.updateField("borrowStafferId", borrowStafferId, id, this.claz);
    }

    @Override
    public int updateCompliance(String id, String compliance) {
       
        return this.jdbcOperation.updateField("compliance", compliance, id, this.claz);
    }
}
