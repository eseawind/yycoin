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
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.tools.ListTools;

/**
 * BudgetDAO
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetItemDAOImpl
 * @since 1.0
 */
public class BudgetItemDAOImpl extends BaseDAO<BudgetItemBean, BudgetItemVO> implements
        BudgetItemDAO {
    public double sumBudgetTotal(String budgetId) {
        final List<Double> ruslt = new ArrayList<Double>();
        jdbcOperation.query(
                "select sum(budget) as rst from T_CENTER_BUDGETITEM where budgetId = ?",
                new Object[] { budgetId }, new RowCallbackHandler() {
                    public void processRow(ResultSet rst) throws SQLException {
                        ruslt.add(rst.getDouble("rst"));
                    }
                });

        if (ListTools.isEmptyOrNull(ruslt)) {
            return 0.0;
        }

        return ruslt.get(0);
    }

    /**
     * findByBudgetIdAndFeeItemId
     * 
     * @param budgetId
     * @param feeItemId
     * @return
     */
    public BudgetItemBean findByBudgetIdAndFeeItemId(String budgetId, String feeItemId) {
        return this.findUnique("where budgetId = ? and feeItemId = ?", budgetId, feeItemId);
    }

    /**
     * vo
     * 
     * @param budgetId
     * @param feeItemId
     * @return
     */
    public BudgetItemVO findVOByBudgetIdAndFeeItemId(String budgetId, String feeItemId) {
        return this.findUniqueVO("where budgetId = ? and feeItemId = ?", budgetId, feeItemId);
    }

    /**
     * @param budgetId
     * @return
     */
    public boolean updateUseMoneyEqualsRealMoney(String budgetId) {
        this.jdbcOperation.update(BeanTools.getUpdateHead(claz)
                + "set realMonery = useMonery where budgetId = ?", budgetId);
        return true;
    }

    @Override
    public boolean updateBudgetById(String budgetItemId, double budget) {

        String sql = BeanTools.getUpdateHead(claz) + "set budget = budget + " + budget
                + " where id = ?";

        this.jdbcOperation.update(sql, budgetItemId);

        return true;
    }
    
    /**
     * 针对预算已结束的场景<br>
     * 
     * @param budgetItemId
     * @param budget
     * @return
     */
    public boolean updateUseMoneyAndRealMoneyById(String budgetItemId, double budget) {

        String sql = BeanTools.getUpdateHead(claz) + "set realMonery = realMonery + " + budget + ", useMonery = useMonery + " + budget
                + " where id = ?";

        this.jdbcOperation.update(sql, budgetItemId);

        return true;
    }
}
