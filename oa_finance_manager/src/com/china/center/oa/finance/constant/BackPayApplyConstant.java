/**
 * File Name: BackPayApplyConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * BackPayApplyConstant
 * 
 * @author ZHUZHU
 * @version 2011-3-5
 * @see BackPayApplyConstant
 * @since 3.0
 */
public interface BackPayApplyConstant
{
    /**
     * 初始(B)
     */
    @Defined(key = "backPayApplyStatus", value = "初始")
    int STATUS_INIT = 0;

    /**
     * 待结算中心审核
     */
    @Defined(key = "backPayApplyStatus", value = "待结算中心审核")
    int STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    @Defined(key = "backPayApplyStatus", value = "驳回")
    int STATUS_REJECT = 98;

    /**
     * 待出纳审核
     */
    @Defined(key = "backPayApplyStatus", value = "待出纳审核")
    int STATUS_SEC = 2;

    /**
     * 结束(E)
     */
    @Defined(key = "backPayApplyStatus", value = "结束")
    int STATUS_END = 99;

    /**
     * 销售退款
     */
    @Defined(key = "backPayApplyType", value = "销售退款")
    int TYPE_OUT = 0;

    /**
     * 预收退款
     */
    @Defined(key = "backPayApplyType", value = "预收退款")
    int TYPE_BILL = 1;
}
