/**
 * File Name: BudgetListenerBudgetImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.listener.BudgetListener;


/**
 * 预算自己的已经使用合计
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see BudgetListenerBudgetImpl
 * @since 3.0
 */
public class BudgetListenerBudgetImpl implements BudgetListener
{
    private BudgetLogDAO budgetLogDAO = null;

    /**
     * default constructor
     */
    public BudgetListenerBudgetImpl()
    {
    }

    /**
     * 返回预算已经使用的金额(包括预占的)
     */
    public double onSumPreAndUseInEachBudgetItemChange(User user, BudgetItemBean changeItem)
    {
        return budgetLogDAO.sumBudgetLogByBudgetItemId(changeItem.getId()) / 100.0d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "BudgetListener.BudgetImpl";
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO()
    {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO
     *            the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO)
    {
        this.budgetLogDAO = budgetLogDAO;
    }

}
