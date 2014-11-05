package com.china.center.oa.finance.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.dao.InvoiceStorageDAO;
import com.china.center.oa.finance.vo.InvoiceStorageVO;

public class InvoiceStorageDAOImpl extends BaseDAO<InvoiceStorageBean, InvoiceStorageVO> implements
		InvoiceStorageDAO
{

	@Override
	public List<InvoiceStorageVO> querySelfSumInvoice(String stafferId)
	{
		String sql = "select b.id as invoiceId ,b.name as invoiceName, a.providerId, a.providerName, count(*) as invoiceAmount, sum(moneys) as moneys,  sum(moneys - hasConfirmMoneys) as mayConfirmMoneys" +
						" from T_CENTER_INVOICESTORAGE a, t_center_invoice b " +
						" where a.invoiceid = b.id and a.stafferId = ?" +
						" group by b.id, b.name, a.providerId, a.providerName";
		
		return this.jdbcOperation.queryObjectsBySql(sql, stafferId).list(this.clazVO);
	}
	
}
