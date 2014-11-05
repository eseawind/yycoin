/**
 * File Name: FinanceReferDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-4-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceRefer;


/**
 * FinanceReferDAO
 * 
 * @author ZHUZHU
 * @version 2012-4-27
 * @see FinanceReferDAO
 * @since 3.0
 */
public interface FinanceReferDAO extends DAO<FinanceRefer, FinanceRefer>
{
    void syn();
}
