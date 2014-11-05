/**
 * File Name: BudgetListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetItemBean;


/**
 * BudgetListener
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see BudgetListener
 * @since 3.0
 */
public interface BudgetListener extends ParentListener
{
    /**
     * 监听预算的预占和使用,主要是预算的变更检查借款和实际使用，也包括申请途中的报销
     * 
     * @param user
     * @param apply
     * @param changeItemList
     * @param newList
     * @throws MYException
     */
    @Deprecated
    double onSumPreAndUseInEachBudgetItemChange(User user, BudgetItemBean changeItem);
}
