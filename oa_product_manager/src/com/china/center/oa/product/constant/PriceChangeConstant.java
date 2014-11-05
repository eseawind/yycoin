/**
 * File Name: PriceChangeConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * PriceChangeConstant
 * 
 * @author ZHUZHU
 * @version 2010-10-5
 * @see PriceChangeConstant
 * @since 1.0
 */
public interface PriceChangeConstant
{
    /**
     * 正常
     */
    @Defined(key = "priceChangeStatus", value = "正常")
    int STATUS_COMMON = 0;

    /**
     * 回滚
     */
    @Defined(key = "priceChangeStatus", value = "回滚")
    int STATUS_ROLLBACK = 1;
}
