/**
 * File Name: AlarmConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * AlarmConstant
 * 
 * @author ZHUZHU
 * @version 2012-5-8
 * @see AlarmConstant
 * @since 3.0
 */
public interface AlarmConstant
{
    @Defined(key = "alarmStatus", value = "初始")
    int ALARMBEAN_STATUS = 0;

    @Defined(key = "alarmStatus", value = "结束")
    int ALARMBEAN_END = 99;
}
