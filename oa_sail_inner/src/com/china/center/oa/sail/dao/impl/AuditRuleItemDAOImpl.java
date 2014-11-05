package com.china.center.oa.sail.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.AuditRuleItemBean;
import com.china.center.oa.sail.dao.AuditRuleItemDAO;
import com.china.center.oa.sail.vo.AuditRuleItemVO;

public class AuditRuleItemDAOImpl extends BaseDAO<AuditRuleItemBean, AuditRuleItemVO> implements AuditRuleItemDAO
{

	public List<AuditRuleItemBean> queryAuditRuleItems(String industryId, int outType, int payType)
	{
		String sql = "select t2.* from T_CENTER_OUT_AUDITRULE t1, T_CENTER_OUT_AUDITRULE_ITEM t2" +
				" where t1.id = t2.refId and t1.industryId = ? and t1.sailType = ? and t2.payCondition = ?";
		
		return this.jdbcOperation.queryObjectsBySql(sql, industryId, outType, payType).list(claz);
	}
	
}
