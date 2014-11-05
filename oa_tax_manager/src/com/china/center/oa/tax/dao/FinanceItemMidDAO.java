/**
 * File Name: FinanceItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceItemMidBean;

/**
 * FinanceItemDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemMidDAO
 * @since 1.0
 */
public interface FinanceItemMidDAO extends DAO<FinanceItemMidBean, FinanceItemMidBean> {
    
    List<FinanceItemMidBean> queryRefFinanceItemMidBeanByCondition(String outId);
    
}
