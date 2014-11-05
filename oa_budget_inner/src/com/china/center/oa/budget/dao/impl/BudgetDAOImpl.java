/**
 * File Name: BudgetDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.tools.ListTools;


/**
 * BudgetDAO
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetDAOImpl
 * @since 1.0
 */
public class BudgetDAOImpl extends BaseDAO<BudgetBean, BudgetVO> implements BudgetDAO
{
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }

    public boolean updateCarryStatus(String id, int carryStatus)
    {
        this.jdbcOperation.updateField("carryStatus", carryStatus, id, claz);

        return true;
    }

    /**
     * querySubmitBudgetByParentId
     * 
     * @param parentId
     * @return
     */
    public List<BudgetBean> querySubmitBudgetByParentId(String parentId)
    {
        // 3和99的预算都在里面
        return this.queryEntityBeansByCondition("where parentId = ? and status >= ?", parentId,
            BudgetConstant.BUDGET_STATUS_SUBMIT);
    }

    /**
     * queryCurrentRunBudget
     * 
     * @return
     */
    public List<BudgetVO> queryCurrentRunBudget()
    {
        return this.queryEntityVOsByCondition(
            "where BudgetBean.level = ? and BudgetBean.status = ? and carryStatus = ?",
            BudgetConstant.BUDGET_LEVEL_MONTH, BudgetConstant.BUDGET_STATUS_PASS,
            BudgetConstant.BUDGET_CARRY_DOING);
    }

    /**
     * countByYearAndType
     * 
     * @param year
     * @param type
     * @return
     */
    public int countByYearAndType(String year, int type)
    {
        return super.countByCondition("where year = ? and type = ?", year, type);
    }

    public boolean updateTotal(String id, double total)
    {
        this.jdbcOperation.updateField("total", total, id, claz);

        return true;
    }

    public boolean updateRealMoney(String id, double realMoney)
    {
        this.jdbcOperation.updateField("realMonery", realMoney, id, claz);

        return true;
    }

    /**
     * 统计父级预算下面的子预算已经占用的金额
     * 
     * @param parentId
     * @return
     */
    public double countParentBudgetTotal(String parentId)
    {
        final List<Double> ruslt = new ArrayList<Double>();
        jdbcOperation.query(
            "select sum(total) as rst from T_CENTER_BUDGET where parentId = ? and status = ?",
            new Object[] {parentId, BudgetConstant.BUDGET_STATUS_PASS}, new RowCallbackHandler()
            {
                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    ruslt.add(rst.getDouble("rst"));
                }
            });

        if (ListTools.isEmptyOrNull(ruslt))
        {
            return 0.0;
        }

        return ruslt.get(0);
    }

    @Override
    public boolean updateTotalById(String id, double total) {

        String sql = BeanTools.getUpdateHead(claz) + "set total = total + " + total
        + " where id = ?";

        this.jdbcOperation.update(sql, id);
        
        return true;
        
    }
    
    public boolean updateRealMoneyById(String id, double realMoney)
    {

        String sql = BeanTools.getUpdateHead(claz) + "set realMonery = realMonery + " + realMoney
        + " where id = ?";

        this.jdbcOperation.update(sql, id);
        
        return true;
        
    }
}
