/**
 * File Name: BudgetDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.vo.BudgetVO;


/**
 * BudgetDAO
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetDAO
 * @since 3.0
 */
public interface BudgetDAO extends DAO<BudgetBean, BudgetVO>
{
    boolean updateStatus(String id, int status);

    boolean updateCarryStatus(String id, int carryStatus);

    List<BudgetBean> querySubmitBudgetByParentId(String parentId);

    /**
     * 当前运行的预算
     * 
     * @return
     */
    List<BudgetVO> queryCurrentRunBudget();

    int countByYearAndType(String year, int type);

    boolean updateTotal(String id, double total);

    boolean updateRealMoney(String id, double realMoney);

    double countParentBudgetTotal(String parentId);
    
    boolean updateTotalById(String id, double total);
    
    boolean updateRealMoneyById(String id, double realMoney);
}
