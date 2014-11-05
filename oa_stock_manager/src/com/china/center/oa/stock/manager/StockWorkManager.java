package com.china.center.oa.stock.manager;

import com.china.center.common.MYException;
import com.china.center.oa.stock.bean.StockWorkBean;

public interface StockWorkManager
{
	void statsStockWork();
	
	boolean handleStockWork(String userId, StockWorkBean bean) throws MYException;
}
