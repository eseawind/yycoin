/**
 * File Name: ModuleConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * ModuleConstant
 * 
 * @author zhuzhu
 * @version 2008-12-10
 * @see OperationConstant
 * @since 1.0
 */
public interface OperationConstant
{
    /**
     * 提交
     */
    String OPERATION_SUBMIT = "提交";

    /**
     * 通过
     */
    String OPERATION_PASS = "通过";

    /**
     * 驳回
     */
    String OPERATION_REJECT = "驳回";

    /**
     * 更新
     */
    String OPERATION_UPDATE = "更新";

    /**
     * 结束
     */
    String OPERATION_END = "结束";

    /**
     * 挂靠
     */
    @Defined(key = "changeLogOpr", value = "挂靠")
    int OPERATION_CHANGELOG_ADD = 0;

    /**
     * 卸载
     */
    @Defined(key = "changeLogOpr", value = "卸载")
    int OPERATION_CHANGELOG_DEL = 1;
}
