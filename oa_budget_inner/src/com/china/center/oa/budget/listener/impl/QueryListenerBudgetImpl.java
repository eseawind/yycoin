/**
 * File Name: QueryListenerBudgetImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.listener.impl;


import java.util.List;

import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerBudgetImpl
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see QueryListenerBudgetImpl
 * @since 3.0
 */
public class QueryListenerBudgetImpl implements QueryListener
{
    private BudgetDAO budgetDAO = null;

    /**
     * default constructor
     */
    public QueryListenerBudgetImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return budgetDAO.queryCurrentRunBudget();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$releaseBudget";
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

}
