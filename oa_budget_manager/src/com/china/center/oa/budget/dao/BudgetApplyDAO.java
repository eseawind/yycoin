/**
 * File Name: BudgetApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import java.io.Serializable;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.vo.BudgetApplyVO;


/**
 * BudgetApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetApplyDAO
 * @since 3.0
 */
public interface BudgetApplyDAO extends DAO<BudgetApplyBean, BudgetApplyVO>
{
    boolean updateStatus(Serializable id, int status);
}
