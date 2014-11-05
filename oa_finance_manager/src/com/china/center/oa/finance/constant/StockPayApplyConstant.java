/**
 * File Name: StockPayApplyConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * StockPayApplyConstant
 * 
 * @author ZHUZHU
 * @version 2011-2-17
 * @see StockPayApplyConstant
 * @since 3.0
 */
public interface StockPayApplyConstant
{
    /**
     * 初始
     */
    @Defined(key = "stockPayApplyStatus", value = "初始")
    int APPLY_STATUS_INIT = 0;

    /**
     * 驳回
     */
    @Defined(key = "stockPayApplyStatus", value = "驳回")
    int APPLY_STATUS_REJECT = 1;

    /**
     * 待营运中心审批
     */
    @Defined(key = "stockPayApplyStatus", value = "待营运中心审批")
    int APPLY_STATUS_SAIL = 11;

    /**
     * 待稽核审批
     */
    @Defined(key = "stockPayApplyStatus", value = "待稽核审批")
    int APPLY_STATUS_CHECK = 12;

    /**
     * 待财务总监审批
     */
    @Defined(key = "stockPayApplyStatus", value = "待财务总监审批")
    int APPLY_STATUS_CFO = 13;

    /**
     * 待总裁审批(5万以上的)
     */
    @Defined(key = "stockPayApplyStatus", value = "待总裁审批")
    int APPLY_STATUS_CEO = 2;

    /**
     * 待财务付款
     */
    @Defined(key = "stockPayApplyStatus", value = "待财务付款")
    int APPLY_STATUS_SEC = 3;

    /**
     * 付款结束
     */
    @Defined(key = "stockPayApplyStatus", value = "付款结束")
    int APPLY_STATUS_END = 4;
    
    /**
     * 结束付款
     */
    @Defined(key = "stockPayApplyFinal", value = "结束付款")
    int APPLY_ISFINAL_YES = 0;
    
    @Defined(key = "stockPayApplyFinal", value = "未结束付款")
    int APPLY_ISFINAL_NO = 1;
}
