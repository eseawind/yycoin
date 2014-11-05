package com.china.center.oa.stock.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.stock.bean.StockWorkBean;
import com.china.center.oa.stock.dao.StockWorkDAO;
import com.china.center.tools.StringTools;

public class StockWorkDAOImpl extends BaseDAO<StockWorkBean, StockWorkBean> implements StockWorkDAO
{

	@Override
	public boolean ifExistsChangeSendDate(String stockId, String stockItemId)
	{
		List<StockWorkBean> list = this.queryEntityBeansByCondition("where stockId = ? and StockItemId = ?", stockId, stockItemId);
		
		for (StockWorkBean each : list)
		{
			if (!StringTools.isNullOrNone(each.getSendDate())){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean updateIsNew(String stockId, String stockItemId)
	{
		String sql = "update T_CENTER_STOCK_WORK set isNew = 0 where stockId = ? and stockItemId = ?";
		
		this.jdbcOperation.update(sql, stockId, stockItemId);
		
		return true;
	}

	@Override
	public StockWorkBean getLatestSendDate(String stockId, String stockItemId)
	{
		List<StockWorkBean> list = this.queryEntityBeansByCondition("where stockId = ? and StockItemId = ?", stockId, stockItemId);
		
		for (StockWorkBean each : list)
		{
			// 最新一次任务处理修改的发货日期
			if (each.getIsNew() == 1){
				return each;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.china.center.oa.stock.dao.StockWorkDAO#queryEntityVOsByCondition(java.lang.String, com.china.center.jdbc.util.PageSeparate)
	 */
	@Override
	public List<StockWorkBean> queryEntityVOsByCondition(String condtion,
			PageSeparate page) {
		return this.jdbcOperation.queryObjectsByPageSeparate(condtion, page, this.clazVO);
	}
	
}
