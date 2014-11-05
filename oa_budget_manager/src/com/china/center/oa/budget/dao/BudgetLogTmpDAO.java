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
import com.china.center.oa.budget.bean.BudgetLogTmpBean;


/**
 * 
 * 请输入功能描述
 *
 * @author fangliwen 2012-6-20
 */
public interface BudgetLogTmpDAO extends DAO<BudgetLogTmpBean, BudgetLogTmpBean>
{
    
    long sumTurnBudgetLogTmp(String level, String levelId);
}
