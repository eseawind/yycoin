package com.china.center.oa.sail.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.AuditRuleBean;
import com.china.center.oa.sail.bean.AuditRuleItemBean;

public interface AuditRuleManager
{

	boolean addBean(AuditRuleBean bean, User user) throws MYException;
	
	boolean updateBean(AuditRuleBean bean, User user) throws MYException;
	
	boolean deleteBean(String id, User user) throws MYException;
	
	AuditRuleItemBean getAuditRuleItem(String productId, String industryId, int outType, int payType);
}
