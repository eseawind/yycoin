package com.china.center.oa.finance.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.vo.InvoiceStorageVO;

public interface InvoiceStorageDAO extends DAO<InvoiceStorageBean, InvoiceStorageVO>
{
	List<InvoiceStorageVO> querySelfSumInvoice(String stafferId);
}
