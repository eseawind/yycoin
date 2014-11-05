/**
 * File Name: InsVSOutDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.vs.InsVSOutBean;


/**
 * InsVSOutDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InsVSOutDAO
 * @since 3.0
 */
public interface InsVSOutDAO extends DAO<InsVSOutBean, InsVSOutBean>
{
    double getSumMoneysByBaseId(String baseId);
    
	double sumOutHasInvoiceStatusNotEnd(String outId);
	
	double sumOutBlanceHasInvoiceStatusNotEnd(String balanceId);

}
