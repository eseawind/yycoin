package com.china.center.oa.finance.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;

public interface PreInvoiceApplyDAO extends DAO<PreInvoiceApplyBean, PreInvoiceApplyVO>
{
	int updateStatus(String id, int status);
	
	int updateInvoiceMoney(String id, Long invoiceMoney);
	
	int countOverTimeBeans(String stafferId);
}
