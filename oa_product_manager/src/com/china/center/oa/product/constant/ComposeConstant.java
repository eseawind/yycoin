/**
 * File Name: ComposeConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * ComposeConstant
 * 
 * @author ZHUZHU
 * @version 2010-10-3
 * @see ComposeConstant
 * @since 1.0
 */
public interface ComposeConstant
{
    /**
     * 合成
     */
    @Defined(key = "composeType", value = "合成")
    int COMPOSE_TYPE_COMPOSE = 0;

    /**
     * 分解
     */
    @Defined(key = "composeType", value = "分解")
    int COMPOSE_TYPE_DECOMPOSE = 1;

    @Defined(key = "composeStatus", value = "提交")
    int STATUS_SUBMIT = 0;

    @Defined(key = "composeStatus", value = "驳回")
    int STATUS_REJECT = 1;

    @Defined(key = "composeStatus", value = "生产部经理通过")
    int STATUS_MANAGER_PASS = 2;

    @Defined(key = "composeStatus", value = "运营总监通过(已合成)")
    int STATUS_CRO_PASS = 3;
    
    @Defined(key = "composeStatus", value = "待事业部经理审批")
    int STATUS_INDUSTRY_PASS = 4;
    
    @Defined(key = "composeStatus", value = "结束")
    int STATUS_OK = 99;
}
