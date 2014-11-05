/**
 * File Name: BudgetItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.vo.BudgetItemVO;


/**
 * BudgetItemDAO
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetItemDAO
 * @since 3.0
 */
public interface BudgetItemDAO extends DAO<BudgetItemBean, BudgetItemVO>
{
    double sumBudgetTotal(String budgetId);

    BudgetItemBean findByBudgetIdAndFeeItemId(String budgetId, String feeItemId);

    BudgetItemVO findVOByBudgetIdAndFeeItemId(String budgetId, String feeItemId);

    boolean updateUseMoneyEqualsRealMoney(String budgetId);
    
    boolean updateBudgetById(String budgetItemId, double budget);
    
    boolean updateUseMoneyAndRealMoneyById(String budgetItemId, double budget);
}
