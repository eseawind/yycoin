/**
 * File Name: CreditConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * CreditConstant
 * 
 * @author ZHUZHU
 * @version 2009-11-1
 * @see CreditConstant
 * @since 1.0
 */
public interface CreditConstant
{
    /**
     * static credit
     */
    @Defined(key = "creditType", value = "静态指标")
    int CREDIT_TYPE_STATIC = 0;

    /**
     * dynamic credit
     */
    @Defined(key = "creditType", value = "动态指标")
    int CREDIT_TYPE_DYNAMIC = 1;

    /**
     * percent item
     */
    @Defined(key = "creditItemType", value = "百分制")
    int CREDIT_ITEM_TYPE_PERCENT = 0;

    /**
     * real item
     */
    @Defined(key = "creditItemType", value = "实际值")
    int CREDIT_ITEM_TYPE_REAL = 1;

    /**
     * obverse face
     */
    @Defined(key = "creditItemFace", value = "正向指标")
    int CREDIT_ITEM_FACE_OBVERSE = 0;

    /**
     * negative face
     */
    @Defined(key = "creditItemFace", value = "负向指标")
    int CREDIT_ITEM_FACE_NEGATIVE = 1;

    /**
     * sub yes
     */
    @Defined(key = "creditItemSub", value = "子项设值")
    int CREDIT_ITEM_SUB_YES = 0;

    /**
     * sub no
     */
    @Defined(key = "creditItemSub", value = "直接设值")
    int CREDIT_ITEM_SUB_NO = 1;

    /**
     * 直接设置-parent
     */
    String SET_DRECT_PARENT = "90000000000000009999";

    /**
     * 直接设置(总经理设置)
     */
    String SET_DRECT = "80000000000000000099";

    /**
     * 未逾期加分
     */
    String OUT_COMMON_ITEM = "80000000000000000042";

    /**
     * 未逾期加分的父项
     */
    String OUT_COMMON_ITEM_PARENT = "90000000000000000005";

    /**
     * 财务年度最大单比交易额
     */
    String OUT_MAX_BUSINESS = "80000000000000000051";

    /**
     * 财务年度最大单比交易额-父项
     */
    String OUT_MAX_BUSINESS_PARENT = "90000000000000000006";

    /**
     * 财务年度交易总额
     */
    String OUT_TOTAL_BUSINESS = "80000000000000000061";

    /**
     * 财务年度交易总额-父项
     */
    String OUT_TOTAL_BUSINESS_PARENT = "90000000000000000007";

    /**
     * 逾期减分档次
     */
    String OUT_DELAY_ITEM = "80000000000000000041";

    /**
     * 逾期减分档次的父项
     */
    String OUT_DELAY_ITEM_PARENT = OUT_COMMON_ITEM_PARENT;

    /**
     * 未付款
     */
    int PAY_NOT = 0;

    /**
     * 付款
     */
    int PAY_YES = 1;

    /**
     * 销售单未处理
     */
    int CREDIT_OUT_INIT = 0;

    /**
     * 销售单已经处理
     */
    int CREDIT_OUT_END = 1;
}
