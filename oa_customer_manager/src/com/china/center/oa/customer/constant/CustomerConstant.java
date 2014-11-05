/**
 * File Name: CustomerConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * CustomerConstant
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerConstant
 * @since 1.0
 */
public interface CustomerConstant
{
    /**
     * ok
     */
    @Defined(key = "customerStatus", value = "正常")
    int STATUS_OK = 0;

    /**
     * apply
     */
    @Defined(key = "customerStatus", value = "申请")
    int STATUS_APPLY = 1;

    /**
     * 删除
     */
    @Defined(key = "customerStatus", value = "删除")
    int STATUS_DELETE = 2;

    /**
     * 审批后等待分配code
     */
    @Defined(key = "customerStatus", value = "等待分配编码")
    int STATUS_WAIT_CODE = 3;

    /**
     * reject
     */
    @Defined(key = "customerStatus", value = "驳回")
    int STATUS_REJECT = 2;

    // customerOpr
    @Defined(key = "customerOpr", value = "增加")
    int OPR_ADD = 0;

    @Defined(key = "customerOpr", value = "更新")
    int OPR_UPDATE = 1;

    @Defined(key = "customerOpr", value = "删除")
    int OPR_DEL = 2;

    /**
     * UPATE_CREDIT
     */
    @Defined(key = "customerOpr", value = "更新客户信用")
    int OPR_UPATE_CREDIT = 3;

    /**
     * 更新利润分配比例
     */
    @Defined(key = "customerOpr", value = "更新利润分配比例")
    int OPR_UPATE_ASSIGNPER = 4;

    /**
     * 空闲的客户
     */
    @Defined(key = "realCustomerStatus", value = "空闲")
    int REAL_STATUS_IDLE = 0;

    /**
     * 被使用的客户
     */
    @Defined(key = "realCustomerStatus", value = "使用")
    int REAL_STATUS_USED = 1;

    /**
     * 申请中的客户
     */
    @Defined(key = "realCustomerStatus", value = "申请中")
    int REAL_STATUS_APPLY = 2;

    /**
     * 批量移交中的客户
     */
    @Defined(key = "realCustomerStatus", value = "批量移交中")
    int REAL_STATUS_BATCHTRANS = 3;
    
    /**
     * 无成交记录
     */
    @Defined(key = "blog", value = "无历史成交")
    int BLOG_NO = 0;

    /**
     * 有成交记录
     */
    @Defined(key = "blog", value = "有历史成交")
    int BLOG_YES = 1;

    /**
     * 无名片
     */
    @Defined(key = "card", value = "无名片")
    int CARD_NO = 0;

    /**
     * 有名片
     */
    @Defined(key = "card", value = "有名片")
    int CARD_YES = 1;

    /**
     * 回收全部
     */
    int RECLAIMSTAFFER_ALL = 0;

    /**
     * 回收拓展
     */
    int RECLAIMSTAFFER_EXPEND = 1;

    /**
     * 回收终端
     */
    int RECLAIMSTAFFER_TER = 2;

    /**
     * 终端
     */
    int SELLTYPE_TER = 0;

    /**
     * 拓展
     */
    int SELLTYPE_EXPEND = 1;

    /**
     * 金融
     */
    int SELLTYPE1_FINANCE_YES = 0;

    /**
     * 非金融
     */
    int SELLTYPE1_FINANCE_NO = 1;
    
    /**
     * 其它
     */
    int SELLTYPE1_FINANCE_OTHER = 99;
    
    /**
     * 未核对
     */
    @Defined(key = "checkStatus", value = "未核对")
    int HIS_CHECK_NO = 0;

    /**
     * 核对
     */
    @Defined(key = "checkStatus", value = "核对")
    int HIS_CHECK_YES = 1;

    /**
     * 是新客户
     */
    int HASNEW_YES = 0;

    /**
     * 不是新客户
     */
    int HASNEW_NO = 1;

    /**
     * 新客户
     */
    @Defined(key = "c_newType", value = "新客户")
    int NEWTYPE_NEW = 0;

    /**
     * 老客户
     */
    @Defined(key = "c_newType", value = "老客户")
    int NEWTYPE_OLD = 1;

    /**
     * 默认级别
     */
    String CREDITLEVELID_DEFAULT = "90000000000000000001";

    /**
     * 黑名单客户
     */
    String BLACK_LEVEL = "90000000000000000000";

    /**
     * 默认信用杠杆倍数
     */
    int DEFAULT_LEVER = 1;

    /**
     * 系统内置的公共客户
     */
    String PUBLIC_CUSTOMER_ID = "99";

    String PUBLIC_CUSTOMER_NAME = "公共客户";

    /**
     * 总行
     */
    @Defined(key = "bankClass", value = "总行")
    int BANK_HQ = 1;
    
    /**
     * 市分行
     */
    @Defined(key = "bankClass", value = "市分行")
    int BANK_BRANCH_CITY = 2;
    
    /**
     * 区分行
     */
    @Defined(key = "bankClass", value = "区支行")
    int BANK_BRANCH_AREA = 3;
    
    /**
     * 路分行
     */
    @Defined(key = "bankClass", value = "路支行")
    int BANK_BRANCH_ROAD = 4;
    
    /**
	 * user status 1 审核中 2有效 3未通过 4 停用
	 */
	/**
	 * 审核中
	 */
	@Defined(key = "appUserStatus", value = "审核中")
	int USER_STATUS_APPROVE = 1;
	
	/**
	 *  有效
	 */
	@Defined(key = "appUserStatus", value = "有效")
	int USER_STATUS_AVAILABLE = 2;

	/**
	 * 未通过
	 */
	@Defined(key = "appUserStatus", value = "未通过")
	int USER_STATUS_REJECT = 3;
	
	/**
	 * 停用
	 */
	@Defined(key = "appUserStatus", value = "停用")
	int USER_STATUS_DROP = 4;
	
	/**
	 * 0:待审核  1:审核通过  2:驳回
	 */
	/**
	 * 审核中
	 */
	@Defined(key = "userApplyStatus", value = "待审核")
	int USER_APPLY_APPROVE = 0;
	
	/**
	 *  有效
	 */
	@Defined(key = "userApplyStatus", value = "审核通过")
	int USER_APPLY_PASS = 1;

	/**
	 * 未通过
	 */
	@Defined(key = "userApplyStatus", value = "驳回")
	int USER_APPLY_REJECT = 2;
	
	int OPRTYPE_NEW = 1;
	
	int OPRTYPE_UPDATE = 2;
	
    /**
     * 个人客户
     */
    @Defined(key = "customerNature", value = "个人")
    int NATURE_INDIVIDUAL = 1;
    
    /**
     * 客户是某一部门
     */
    @Defined(key = "customerNature", value = "部门")
    int NATURE_DEPART = 2;
    
    /**
     * 企业客 户
     */
    @Defined(key = "customerNature", value = "组织")
    int NATURE_CORPORATION = 3;
}
