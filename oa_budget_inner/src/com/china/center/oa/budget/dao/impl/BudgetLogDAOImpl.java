/**
 * File Name: BudgetLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.vo.BudgetLogVO;


/**
 * BudgetLogDAO
 * 
 * @author ZHUZHU
 * @version 2009-6-26
 * @see BudgetLogDAOImpl
 * @since 1.0
 */
public class BudgetLogDAOImpl extends BaseDAO<BudgetLogBean, BudgetLogVO> implements BudgetLogDAO
{
    public long sumBudgetLogByBudgetItemId(String budgetItemId)
    {
        // status 0:正式
        String sql = BeanTools.getSumHead(this.claz, "monery") + "where budgetItemId = '"
                     + budgetItemId + "' and status = 0 and usertype <> 2";

        return (long)this.jdbcOperation.queryForDouble(sql);
    }

    public long sumBudgetLogByLevel(String level, String levelId)
    {
        // userType=1就是实际使用,不分临时的还是不临时的
        String sql = BeanTools.getSumHead(this.claz, "monery") + "where " + level + " = '"
                     + levelId + "' and userType = 1";

        return (long)this.jdbcOperation.queryForDouble(sql);
    }

    public long sumUsedAndPreBudgetLogByLevel(String level, String levelId)
    {
    	// and status = 0 
        String sql = BeanTools.getSumHead(this.claz, "monery") + "where " + level + " = '"
                     + levelId + "' and status = 0 and usertype <> 2";

        return (long)this.jdbcOperation.queryForDouble(sql);
    }

    public int updateUserTypeByRefId(String refId, int useType, String billIds)
    {
        String sql = BeanTools.getUpdateHead(this.claz)
                     + "set userType = ? , billIds = ? where refId = ?";

        return this.jdbcOperation.update(sql, useType, billIds, refId);
    }

    public int updateStatuseByRefId(String refId, int status)
    {
        String sql = BeanTools.getUpdateHead(this.claz) + "set status = ? where refId = ?";

        return this.jdbcOperation.update(sql, status, refId);
    }

    public int updatePreToZeroByBudgetId(String budgetId)
    {
        // 把预占的先备份
        String sql = BeanTools.getUpdateHead(this.claz)
                     + "set bakmonery = monery where budgetId = ? and userType = 0 and bakmonery = 0";

        this.jdbcOperation.update(sql, budgetId);

        // 清0
        sql = BeanTools.getUpdateHead(this.claz)
              + "set monery = 0 where budgetId = ? and userType = 0";

        this.jdbcOperation.update(sql, budgetId);

        return 0;
    }

    public long sumVOBudgetLogByCondition(ConditionParse condition)
    {
        String sql = this.jdbcOperation
            .queryObjects(condition.toString(), clazVO)
            .getLastSqlByHead("sum(monery)");

        return (long)this.jdbcOperation.queryForDouble(sql);
    }

}
