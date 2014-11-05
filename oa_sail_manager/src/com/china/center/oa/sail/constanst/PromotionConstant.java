package com.china.center.oa.sail.constanst;

import com.china.center.jdbc.annotation.Defined;

public interface PromotionConstant {

    /**
     * 参照时间-开单时间
     */
    @Defined(key="refTime",value="开单时间")
    int REFTIME_ORDERTIME = 0;
    
    /**
     * 参照时间-付款时间
     */
    @Defined(key="refTime", value="付款时间")
    int REFTIME_PAYTIME = 1;
    
    /**
     * 是否含黑名单客户
     */
    @Defined(key = "isBlack", value = "包含黑名单客户")
    int ISBLACK_INCLUDE = 0;
    
    /**
     * 是否含黑名单客户
     */
    @Defined(key = "isBlack", value = "不包含黑名单客户")
    int ISBACK_EXCLUDE = 1;
    
    /**
     * 是否开票
     */
    @Defined(key = "isInvoice", value = "开票")
    int ISINVOICE_YES = 0;

    /**
     * 是否开票
     */
    @Defined(key = "isInvoice", value = "不开票")
    int ISINVOICE_NO = 1;
    
    /**
     * 是否允许退货 
     */
    @Defined(key = "isReturn", value = "否")
    int ISRETURN_NO = 0;
    
    /**
     * 是否允许退货 
     */
    @Defined(key = "isReturn", value = "是")
    int ISRETURN_YES = 1;
    
    /**
     * 直降
     */
    @Defined(key = "rebateType", value = "直降")
    int REBATETYPE_DOWN = 0;
    
    /**
     * 折扣
     */
    @Defined(key = "rebateType", value = "折扣")
    int REBATETYPE_ZK = 1;
    
    /**
     * 增加信用分
     */
    @Defined(key = "rebateType", value = "增加信用分")
    int ADD_CREDIT = 2;
    
    /**
     * 返物
     */
//    @Defined(key = "rebateType", value = "返物")    
//    int REBATETYPE_RET = 2;
    
    /**
     * 订单累计
     */
    @Defined(key = "isAddUp", value = "累计")
    int ISADDUP_YES = 0;
    
    /**
     * 订单不累计
     */
    @Defined(key = "isAddUp", value = "不累计")
    int ISADDUP_NO = 1;
    
    /**
     * 付款时间限制 - 不限
     */
    @Defined(key = "payTime", value="不限")
    int PAYTIME_UNLIMIT = 0;
    
    /**
     * 付款时间限制 - 先款后开单
     */
    @Defined(key = "payTime", value="先款后开单")
    int PAYTIME_MONEYBEFORDER = 1;
    
    /**
     * 礼包 - 不限
     */
    @Defined(key = "giftBag", value="不限")
    int GIFTBAG_UNLIMIT = 0;
    
    /**
     * 礼包 - 限制礼包
     */
    @Defined(key = "giftBag", value="礼包")
    int GIFTBAG_LIMIT = 1;
    
    int ERROR_MONEY = 1;
    
    int ERROR_AMOUNT = 2;
    
    int ERROR_LESSPROMVALUE = 3;
    
    int ERROR_GIFTBAG = 4;
    
    @Defined(key = "inValid", value="有效")
    int INVALID_YES = 0;
    
    @Defined(key = "inValid", value="无效")
    int INVALID_NO = 1;
}
