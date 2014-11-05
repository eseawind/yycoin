/**
 * File Name: BudgetFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.facade;


import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;


/**
 * BudgetFacade
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetFacade
 * @since 3.0
 */
public interface BudgetFacade
{
    boolean addBudget(String userId, BudgetBean bean)
        throws MYException;

    boolean delBudget(String userId, String cid)
        throws MYException;

    boolean updateBudget(String userId, BudgetBean bean)
        throws MYException;

    boolean updateBudgetItem(String userId, BudgetItemBean bean, String reason)
        throws MYException;

    boolean delBudgetItem(String userId, String id)
        throws MYException;

    boolean rejectBudget(String userId, String cid, String reson)
        throws MYException;

    boolean passBudget(String userId, String cid)
        throws MYException;

    boolean passBudgetApply(String userId, String mode, String cid)
        throws MYException;

    boolean rejectBudgetApply(String userId, String mode, String cid, String reson)
        throws MYException;

    boolean addFeeItem(String userId, FeeItemBean bean)
        throws MYException;

    boolean updateFeeItem(String userId, FeeItemBean bean)
        throws MYException;

    boolean deleteFeeItem(String userId, String id)
        throws MYException;

    boolean addBudgetApply(String userId, BudgetApplyBean bean)
        throws MYException;
}
