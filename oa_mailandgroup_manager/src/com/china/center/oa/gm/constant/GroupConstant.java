/**
 * File Name: GroupConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.gm.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * GroupConstant
 * 
 * @author zhuzhu
 * @version 2009-4-7
 * @see GroupConstant
 * @since 1.0
 */
public interface GroupConstant
{
    /**
     * 自有
     */
    @Defined(key = "groupType", value = "自有")
    int GROUP_TYPE_PRIVATE = 0;

    /**
     * 公共
     */
    @Defined(key = "groupType", value = "公共")
    int GROUP_TYPE_PUBLIC = 1;

    /**
     * 系统
     */
    @Defined(key = "groupType", value = "系统")
    int GROUP_TYPE_SYSTEM = 2;
}
