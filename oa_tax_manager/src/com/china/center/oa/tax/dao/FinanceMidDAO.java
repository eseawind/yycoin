/**
 * File Name: FinanceDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceMidBean;

/**
 * FinanceDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceMidDAO
 * @since 1.0
 */
public interface FinanceMidDAO extends DAO<FinanceMidBean, FinanceMidBean> {
    
    List<FinanceMidBean> queryRefFinanceMidBeanByCondition(String outId); 
    
}
