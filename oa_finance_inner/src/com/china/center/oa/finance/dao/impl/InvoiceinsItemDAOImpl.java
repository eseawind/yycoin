/**
 * File Name: InvoiceinsItemDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.vo.InvoiceinsItemVO;


/**
 * InvoiceinsItemDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsItemDAOImpl
 * @since 3.0
 */
public class InvoiceinsItemDAOImpl extends BaseDAO<InvoiceinsItemBean, InvoiceinsItemVO> implements InvoiceinsItemDAO
{
	public List<InvoiceinsItemBean> queryHasInvoiceStatusNotEnd(String outId)
	{
		String sql = "select t2.* from t_center_invoiceins t1, t_center_invoiceins_item t2 " +
		"where t1.id= t2.parentid and t2.outid = ? and t1.status in (1, 2, 4, 99) and t1.otype = 0";

		return this.jdbcOperation.queryObjectsBySql(sql, outId).list(claz);
	}
}
