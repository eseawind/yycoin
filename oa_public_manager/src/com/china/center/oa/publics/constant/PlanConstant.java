/**
 * File Name: PlanConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * PlanConstant
 * 
 * @author ZHUZHU
 * @version 2009-1-11
 * @see PlanConstant
 * @since 1.0
 */
public interface PlanConstant
{
    /**
     * 计划的初始态
     */
    @Defined(key = "planStatus", value = "初始")
    int PLAN_STATUS_INIT = 0;

    @Defined(key = "planStatus", value = "结束")
    int PLAN_STATUS_END = 1;

    /**
     * 一次执行
     */
    @Defined(key = "carryType", value = "一次执行")
    int PLAN_CARRYTYPES_ONCE = 0;

    /**
     * 每天执行
     */
    @Defined(key = "carryType", value = "每天执行")
    int PLAN_CARRYTYPES_EVERYDAY = 1;
}
