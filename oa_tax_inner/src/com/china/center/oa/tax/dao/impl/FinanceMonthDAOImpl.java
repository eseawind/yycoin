/**
 * File Name: FinanceMonthDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.dao.FinanceMonthDAO;
import com.china.center.oa.tax.vo.FinanceMonthVO;

/**
 * FinanceMonthDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-31
 * @see FinanceMonthDAOImpl
 * @since 3.0
 */
public class FinanceMonthDAOImpl extends BaseDAO<FinanceMonthBean, FinanceMonthVO> implements
        FinanceMonthDAO {
    public long sumMonthTurnTotal(String taxId, String beginKey, String endKey) {
        String sql = BeanTools.getSumHead(claz, "monthTurnTotal")
                + "where taxId = ? and monthKey >= ? and monthKey <= ?";

        return (long) this.jdbcOperation.queryForDouble(sql, taxId, beginKey, endKey);
    }

    @Override
    public long sumCurMonthTurnTotal(String taxId, String level, String curYear) {
        String sql = BeanTools.getSumHead(claz, "monthTurnTotal")
                + "where taxId" + level +" = ? and monthKey = ?";

        return (long) this.jdbcOperation.queryForDouble(sql, taxId, curYear);
    }

    @Override
    public long sumAllMonthTurnTotal(String taxId, String level, String beginYear, String endYear) {
        
        String sql = BeanTools.getSumHead(claz, "monthTurnTotal")
                + "where taxId" + level + " = ? and monthKey >= ? and monthKey <= ?";

        return (long) this.jdbcOperation.queryForDouble(sql, taxId, beginYear, endYear);
    }
}
