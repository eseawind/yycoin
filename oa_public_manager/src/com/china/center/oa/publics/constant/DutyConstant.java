/**
 * File Name: DutyComstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * DutyComstant
 * 
 * @author ZHUZHU
 * @version 2010-8-8
 * @see DutyConstant
 * @since 1.0
 */
public interface DutyConstant
{
    /**
     * 一般纳税人
     */
    @Defined(key = "dutyType", value = "一般纳税人")
    int DUTY_TYPE_COMMON = 0;

    @Defined(key = "dutyType", value = "一般纳税人(17+2)")
    int DUTY_TYPE_COMMON2 = 4;

    @Defined(key = "dutyType", value = "一般纳税人(SPE)")
    int DUTY_TYPE_COMMON3 = 5;

    @Defined(key = "dutyType", value = "小规模纳税人")
    int DUTY_TYPE_SMALL = 1;

    @Defined(key = "dutyType", value = "服务类")
    int DUTY_TYPE_SERVICE = 2;

    @Defined(key = "dutyType", value = "个体户")
    int DUTY_TYPE_PERSONAL = 3;

    /**
     * 显示
     */
    @Defined(key = "dutyShowType", value = "显示")
    int SHOWTYPE_YES = 0;

    @Defined(key = "dutyShowType", value = "不显示")
    int SHOWTYPE_NO = 1;
}
