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
import com.china.center.oa.budget.bean.BudgetLogTmpBean;
import com.china.center.oa.budget.dao.BudgetLogTmpDAO;


/**
 * 
 * 请输入功能描述
 *
 * @author fangliwen 2012-6-20
 */
public class BudgetLogTmpDAOImpl extends BaseDAO<BudgetLogTmpBean, BudgetLogTmpBean> implements BudgetLogTmpDAO
{

    @Override
    public long sumTurnBudgetLogTmp(String level, String levelId) {
        
        String sql = BeanTools.getSumHead(this.claz, "monery") + "where " + level + " = '"
        + levelId + "' and status = 0 ";

        return (long)this.jdbcOperation.queryForDouble(sql);
    
    }


    
}
