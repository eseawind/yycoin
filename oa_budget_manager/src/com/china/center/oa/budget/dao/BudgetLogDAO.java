/**
 * File Name: BudgetLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.vo.BudgetLogVO;


/**
 * BudgetLogDAO
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetLogDAO
 * @since 3.0
 */
public interface BudgetLogDAO extends DAO<BudgetLogBean, BudgetLogVO>
{
    /**
     * sumBudgetLogByBudgetItemId(实际&&预占,用来看看预算是否超支的,不是真正的使用情况)
     * 
     * @param budgetItemId
     * @return
     */
    long sumBudgetLogByBudgetItemId(String budgetItemId);

    /**
     * sumBudgetLogByLevel(实际真实使用的,预占的不在内)
     * 
     * @param budgetLevelName
     * @param budgetItemId
     * @return
     */
    long sumBudgetLogByLevel(String budgetLevelName, String budgetItemId);

    /**
     * sumBudgetLogByLevel(实际&&预占,用来看看预算是否超支的,不是真正的使用情况)
     * 
     * @param budgetLevelName
     * @param budgetItemId
     * @return
     */
    long sumUsedAndPreBudgetLogByLevel(String budgetLevelName, String budgetItemId);

    /**
     * 统计金额
     * 
     * @param condition
     * @return
     */
    long sumVOBudgetLogByCondition(ConditionParse condition);

    int updateUserTypeByRefId(String refId, int useType, String billIds);

    int updateStatuseByRefId(String refId, int status);

    /**
     * 预算结束的时候把预占的预占清空
     * 
     * @param budgetId
     * @return
     */
    int updatePreToZeroByBudgetId(String budgetId);
}
