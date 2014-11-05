package com.china.center.oa.finance.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.vo.StockPrePayApplyVO;

public class StockPrePayApplyDAOImpl extends BaseDAO<StockPrePayApplyBean, StockPrePayApplyVO> implements
		StockPrePayApplyDAO
{

	@Override
	public List<StockPrePayApplyVO> queryVOsByProviderAndInvoiceId(
			String providerId, String invoiceId)
	{
		return this.queryEntityVOsByCondition("where StockPrePayApplyBean.providerId = ? and StockPrePayApplyBean.invoiceId = ? and StockPrePayApplyBean.status = 4 and StockPrePayApplyBean.realMoneys < StockPrePayApplyBean.moneys", providerId, invoiceId);
	}
	
}
