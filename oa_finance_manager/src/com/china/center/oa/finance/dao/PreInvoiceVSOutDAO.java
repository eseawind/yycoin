package com.china.center.oa.finance.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;

public interface PreInvoiceVSOutDAO extends DAO<PreInvoiceVSOutBean, PreInvoiceVSOutBean>
{
	List<PreInvoiceVSOutBean> queryByOutId(String outId);
	
	List<PreInvoiceVSOutBean> queryByOutBalanceId(String balanceId);
}
