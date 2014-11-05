/**
 * File Name: CommonConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * CommonConstant
 * 
 * @author zhuzhu
 * @version 2009-3-15
 * @see CommonConstant
 * @since 1.0
 */
public interface CommonConstant
{
    /**
     * 初始
     */
    @Defined(key = "commonStatus", value = "初始")
    int STATUS_INIT = 0;

    @Defined(key = "commonStatus", value = "提交")
    int STATUS_SUBMIT = 1;

    /**
     * 通过
     */
    @Defined(key = "commonStatus", value = "通过")
    int STATUS_PASS = 2;

    /**
     * 驳回
     */
    @Defined(key = "commonStatus", value = "驳回")
    int STATUS_REJECT = 3;

    /**
     * 结束
     */
    @Defined(key = "commonStatus", value = "结束")
    int STATUS_END = 99;

    @Defined(key = "commonResult", value = "初始")
    int RESULT_INIT = 0;

    @Defined(key = "commonResult", value = "正常")
    int RESULT_OK = 1;

    @Defined(key = "commonResult", value = "异常")
    int RESULT_ERROR = 2;
}
