/**
 * File Name: ProviderConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.constant;

import com.china.center.jdbc.annotation.Defined;

/**
 * ProviderConstant
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderConstant
 * @since 1.0
 */
public interface ProviderConstant
{
    @Defined(key = "checkStatus", value = "未核对")
    int HIS_CHECK_NO = 0;

    @Defined(key = "checkStatus", value = "核对")
    int HIS_CHECK_YES = 1;
}
