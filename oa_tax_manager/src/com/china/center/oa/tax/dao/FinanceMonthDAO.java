/**
 * File Name: FinanceMonthDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.vo.FinanceMonthVO;


/**
 * FinanceMonthDAO
 * 
 * @author ZHUZHU
 * @version 2011-7-31
 * @see FinanceMonthDAO
 * @since 3.0
 */
public interface FinanceMonthDAO extends DAO<FinanceMonthBean, FinanceMonthVO>
{
    /**
     * 获取负债的当前累计
     * 
     * @param taxId
     * @param year
     * @return
     */
    long sumMonthTurnTotal(String taxId, String beginYear, String endYear);
    
    /**
     * 本期损溢结转金额累计
     * @param taxId
     * @param level
     * @param beginYear
     * @param endYear
     * @return
     */
    long sumCurMonthTurnTotal(String taxId, String level, String curYear);
    
    /**
     * 全年损溢结转金额累计
     * @param taxId
     * @param level
     * @param beginYear
     * @param endYear
     * @return
     */
    long sumAllMonthTurnTotal(String taxId, String level, String beginYear, String endYear);
}
