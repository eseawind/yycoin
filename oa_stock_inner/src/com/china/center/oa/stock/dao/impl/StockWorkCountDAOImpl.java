package com.china.center.oa.stock.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.stock.bean.StockWorkCountBean;
import com.china.center.oa.stock.dao.StockWorkCountDAO;

public class StockWorkCountDAOImpl extends BaseDAO<StockWorkCountBean, StockWorkCountBean> implements
		StockWorkCountDAO
{
	@Override
	public boolean updateCount(String stockId, String stockItemId)
	{
		String sql = "update T_CENTER_STOCK_WORKCOUNT set count = count + 1 where stockId = ? and stockItemId = ?";
		
		int count = this.jdbcOperation.update(sql, stockId, stockItemId);
		
		return (count > 0);
	}
	
}
