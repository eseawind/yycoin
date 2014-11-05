/**
 * File Name: FinanceTurnDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceTurnBean;
import com.china.center.oa.tax.vo.FinanceTurnVO;


/**
 * FinanceTurnDAO
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceTurnDAO
 * @since 3.0
 */
public interface FinanceTurnDAO extends DAO<FinanceTurnBean, FinanceTurnVO>
{
    FinanceTurnVO findLastVO();
}
