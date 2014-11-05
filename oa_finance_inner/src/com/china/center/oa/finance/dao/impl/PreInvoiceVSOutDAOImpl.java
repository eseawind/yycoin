package com.china.center.oa.finance.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;

public class PreInvoiceVSOutDAOImpl extends BaseDAO<PreInvoiceVSOutBean, PreInvoiceVSOutBean> implements
		PreInvoiceVSOutDAO
{
	public List<PreInvoiceVSOutBean> queryByOutId(String outId)
	{
		String sql = "select t2.* from t_center_preinvoice t1, t_center_vs_preInvoiceOut t2 " +
				"where t1.id = t2.parentId and t2.outId=?";
		
		return this.jdbcOperation.queryObjectsBySql(sql, outId).list(claz);
	}

	public List<PreInvoiceVSOutBean> queryByOutBalanceId(String balanceId)
	{
		String sql = "select t2.* from t_center_preinvoice t1, t_center_vs_preInvoiceOut t2 " +
		"where t1.id = t2.parentId and t2.outBalanceId=?";
		
		 return this.jdbcOperation.queryObjectsBySql(sql, balanceId).list(claz);
	}
	
}
