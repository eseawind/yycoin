package com.china.center.oa.stock.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.stock.bean.StockWorkBean;

public interface StockWorkDAO extends DAO<StockWorkBean, StockWorkBean>
{
	boolean ifExistsChangeSendDate(String stockId, String stockItemId);
	
	boolean updateIsNew(String stockId, String stockItemId);
	
	StockWorkBean getLatestSendDate(String stockId, String stockItemId);
	
	List<StockWorkBean> queryEntityVOsByCondition(String condtion, PageSeparate page);
}
