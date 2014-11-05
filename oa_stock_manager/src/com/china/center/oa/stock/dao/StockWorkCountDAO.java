package com.china.center.oa.stock.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.stock.bean.StockWorkCountBean;

public interface StockWorkCountDAO extends DAO<StockWorkCountBean, StockWorkCountBean>
{
	boolean updateCount(String stockId, String stockItemId);
}
