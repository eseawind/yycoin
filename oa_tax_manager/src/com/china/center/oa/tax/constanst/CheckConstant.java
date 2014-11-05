/**
 * File Name: CheckConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.constanst;


import com.china.center.jdbc.annotation.Defined;


/**
 * CheckConstant
 * 
 * @author ZHUZHU
 * @version 2011-3-9
 * @see CheckConstant
 * @since 3.0
 */
public interface CheckConstant
{
    /**
     * 销售单
     */
    @Defined(key = "checkType", value = "销售单")
    int CHECK_TYPE_OUT = 0;

    /**
     * 产品合成
     */
    @Defined(key = "checkType", value = "产品合成")
    int CHECK_TYPE_COMPOSE = 1;

    /**
     * 产品调价
     */
    @Defined(key = "checkType", value = "产品调价")
    int CHECK_TYPE_CHANGE = 2;

    /**
     * 收款单
     */
    @Defined(key = "checkType", value = "收款单")
    int CHECK_TYPE_INBILL = 3;

    /**
     * 付款单
     */
    @Defined(key = "checkType", value = "付款单")
    int CHECK_TYPE_OUTBILL = 4;

    /**
     * 采购单
     */
    @Defined(key = "checkType", value = "采购单")
    int CHECK_TYPE_STOCK = 5;

    /**
     * 入库单
     */
    @Defined(key = "checkType", value = "入库单")
    int CHECK_TYPE_BUY = 6;

    /**
     * 新增客户
     */
    @Defined(key = "checkType", value = "新增客户")
    int CHECK_TYPE_CUSTOMER = 7;

    /**
     * 委托结算退单
     */
    @Defined(key = "checkType", value = "委托结算退单")
    int CHECK_TYPE_BASEBALANCE = 8;

}
