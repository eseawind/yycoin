package com.china.center.oa.sail.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.AuditRuleItemBean;
import com.china.center.oa.sail.vo.AuditRuleItemVO;

public interface AuditRuleItemDAO extends DAO<AuditRuleItemBean, AuditRuleItemVO>
{

	List<AuditRuleItemBean> queryAuditRuleItems(String industryId, int outType, int payType);
}
