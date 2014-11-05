package com.china.center.oa.finance.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;
import com.china.center.tools.TimeTools;

public class PreInvoiceApplyDAOImpl extends BaseDAO<PreInvoiceApplyBean, PreInvoiceApplyVO> implements
		PreInvoiceApplyDAO
{
    public int updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, this.claz);
    }

	public int updateInvoiceMoney(String id, Long invoiceMoney)
	{
		 return this.jdbcOperation.updateField("invoiceMoney", invoiceMoney, id, this.claz);
	}

	public int countOverTimeBeans(String stafferId)
	{
		return this.countByCondition("where stafferId = ? and planOutTime < '" + TimeTools.now_short() + "' and status <> 99", stafferId);
	}
}
