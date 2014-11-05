package com.china.center.oa.finance.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.vo.StockPrePayApplyVO;

public interface StockPrePayApplyDAO extends DAO<StockPrePayApplyBean, StockPrePayApplyVO>
{
	List<StockPrePayApplyVO> queryVOsByProviderAndInvoiceId(String providerId, String invoiceId);
}
