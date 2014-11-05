/**
 * File Name: InvoiceinsItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.vo.InvoiceinsItemVO;


/**
 * InvoiceinsItemDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsItemDAO
 * @since 3.0
 */
public interface InvoiceinsItemDAO extends DAO<InvoiceinsItemBean, InvoiceinsItemVO>
{
	List<InvoiceinsItemBean> queryHasInvoiceStatusNotEnd(String outId);
}
