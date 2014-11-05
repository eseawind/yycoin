/**
 * File Name: FlowDefineHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.helper;


import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.constant.FlowConstant;


/**
 * FlowDefineHelper
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see FlowHelper
 * @since 1.0
 */
public abstract class FlowHelper
{
    /**
     * FlowDefineBean is release
     * 
     * @param bean
     * @return
     */
    public static boolean isRelease(FlowDefineBean bean)
    {
        return bean.getStatus() == FlowConstant.FLOW_STATUS_REALSE;
    }

    /**
     * isSubInstance
     * 
     * @param bean
     * @return
     */
    public static boolean isSubInstance(FlowInstanceBean bean)
    {
        return bean.getType() == FlowConstant.FLOW_PARENTTYPE_SUB;
    }

    public static boolean isAbstactToken(FlowTokenBean bean)
    {
        return bean.getType() == FlowConstant.TOKEN_TYPEE_ABSTOKEN;
    }
}
