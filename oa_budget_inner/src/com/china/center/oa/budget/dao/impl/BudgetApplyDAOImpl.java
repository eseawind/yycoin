/**
 * File Name: BudgetApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao.impl;


import java.io.Serializable;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.vo.BudgetApplyVO;


/**
 * BudgetApplyDAo
 * 
 * @author ZHUZHU
 * @version 2009-6-14
 * @see BudgetApplyDAo
 * @since 1.0
 */
public class BudgetApplyDAOImpl extends BaseDAO<BudgetApplyBean, BudgetApplyVO> implements BudgetApplyDAO
{
    /**
     * updateStatus
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(Serializable id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }
}
