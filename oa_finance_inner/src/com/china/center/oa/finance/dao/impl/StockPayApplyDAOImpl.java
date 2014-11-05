/**
 * File Name: StockPayApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import java.util.List;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.vo.StockPayApplyVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;


/**
 * StockPayApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-17
 * @see StockPayApplyDAOImpl
 * @since 3.0
 */
public class StockPayApplyDAOImpl extends BaseDAO<StockPayApplyBean, StockPayApplyVO> implements StockPayApplyDAO
{

	@Override
	public boolean isExistsNotFinish(String id, String refId)
	{
		return this.countByCondition("where id <> ? and refId = ? and status <> 4", id, refId) > 0;
	}

	@Override
	public List<ConfirmInsWrap> queryCanConfirmApply(String stafferId,
			String invoceId)
	{
		String sql = "select a.id as fullId, 999 as outtype, a.invoiceId, '' as origId, b.name as unit, (a.realMoneys - a.hasConfirmInsMoney) as mayConfirmMoney " +
					 " from t_center_stockpayapply a, t_center_provide b" +
					 " where a.provideid = b.id and a.stafferId = ? and a.invoiceId = ? and a.status = 4 and a.hasConfirm = 0 and a.realMoneys > a.hasConfirmInsMoney";
		
		return this.jdbcOperation.queryObjectsBySql(sql, stafferId, invoceId).list(ConfirmInsWrap.class);
	}
	
	public boolean updateHasConfirm(String fullId, int hasConfirm)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirm = ? where id = ?";

		jdbcOperation.update(sql, hasConfirm, fullId);
		
		return true;
	}


	@Override
	public boolean updateHasConfirmMoney(String fullId, double confirmMoney)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirmInsMoney = ? where id = ?";

		jdbcOperation.update(sql, confirmMoney, fullId);
		
		return true;
	}

	@Override
	public boolean hasCreateApply(String stockItemId)
	{
		List<StockPayApplyBean> applyList = this.queryEntityBeansByFK(stockItemId, AnoConstant.FK_FIRST);
		
		return (applyList.size() > 0);
	}
}
