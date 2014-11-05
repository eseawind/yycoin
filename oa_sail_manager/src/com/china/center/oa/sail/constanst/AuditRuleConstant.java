package com.china.center.oa.sail.constanst;

import com.china.center.jdbc.annotation.Defined;

public interface AuditRuleConstant
{

	/**
	 * 客户信用和业务员信用额度担保
	 */
	@Defined(key = "payCondition", value = "客户信用和业务员信用额度担保")
	int PAYTYPE_CUSTOMER_CREDIT = 2;
	
	/**
	 * 事业部经理担保
	 */
	@Defined(key = "payCondition", value = "事业部经理担保")
	int PAYTYPE_INDUSTRY_CREDIT = 3;
	
	/**
	 * 款到发货(黑名单客户/零售)
	 */
	@Defined(key = "payCondition", value = "款到发货(黑名单客户/零售)")
	int PAYTYPE_BLACK = 1;
	
	/**
	 * 可退
	 */
	@Defined(key = "returnCondition", value = "可退")
	int RETURNCONDITION_YES = 0;
	
	/**
	 * 不可退
	 */
	@Defined(key = "returnCondition", value = "不可退")
	int RETURNCONDITION_NO = 1;

	/**
	 * 是，允许低于最低售价
	 */
	@Defined(key = "lessThanSailPrice", value = "是")	
	int LESSTHANSAILPRICE_YES = 0;
	
	/**
	 * 否，不能低于最低售价
	 */
	@Defined(key = "lessThanSailPrice", value = "否")	
	int LESSTHANSAILPRICE_NO = 1;
}
