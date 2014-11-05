package com.china.center.oa.commission.constant;

import com.china.center.jdbc.annotation.Defined;

public interface BlackConstant 
{
    /**
     * 黑名单
     */
    @Defined(key="blackType", value="黑名单")
    int TYPE_BLACK = 0;
    
    /**
     * 资金占用费
     */
    @Defined(key="blackType", value="资金占用费")
    int TYPE_FINANCE = 1;
    
    /**
     * 超期应收
     */
    @Defined(key="blackDataType", value="超期应收")
    int DATATYPE_BLACK = 0;
    
    /**
     * 超期最长天数的应收
     */
    @Defined(key="blackDataType", value="超期最长天数的应收")
    int DATATYPE_LONGESTBLACK = 1;
    
    /**
     * 全部应收
     */
    @Defined(key="blackDataType", value="全部应收")
    int DATATYPE_ALLNOTPAY = 2;
    
}
