package com.china.center.oa.tcp.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;
import com.china.center.oa.tcp.dao.OutBatchPriceDAO;
import com.china.center.oa.tcp.vo.OutBatchPriceVO;
import com.china.center.tools.ListTools;

public class OutBatchPriceDAOImpl extends BaseDAO<OutBatchPriceBean, OutBatchPriceVO> implements OutBatchPriceDAO
{

	public OutBatchPriceBean queryByProductIdAndChangeTime(String productId, String industryId,
			String changeTime)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("OutBatchPriceBean.productId", "=", productId);
		
		con.addCondition("OutBatchPriceBean.industryId", "=", industryId);
		
		con.addCondition("OutBatchPriceBean.beginDate", "<=", changeTime);
		
		con.addCondition("OutBatchPriceBean.endDate", ">=", changeTime);
		
		List<OutBatchPriceBean> list = this.queryEntityBeansByCondition(con);
		
		if (!ListTools.isEmptyOrNull(list))
			return list.get(0);
		else
			return null;
	}
	
}
