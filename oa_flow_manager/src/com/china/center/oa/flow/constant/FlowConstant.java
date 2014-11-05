/**
 *
 */
package com.china.center.oa.flow.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * @author Administrator
 */
public interface FlowConstant
{
    /**
     * 普通模式
     */
    @Defined(key = "flowMode", value = "普通模式")
    int FLOW_MODE_NONE = 0;

    /**
     * 模板模式
     */
    @Defined(key = "flowMode", value = "模板模式")
    int FLOW_MODE_TEMPLATE = 1;

    /**
     * 主流程
     */
    @Defined(key = "parentType", value = "主流程")
    int FLOW_PARENTTYPE_ROOT = 0;

    /**
     * 子流程
     */
    // @Defined(key = "parentType", value = "子流程")
    int FLOW_PARENTTYPE_SUB = 1;

    /**
     * HTML模式
     */
    // @Defined(key = "flowMode", value = "HTML模式")
    int FLOW_MODE_HTML = 2;

    /**
     * 综合模式
     */
    // @Defined(key = "flowMode", value = "综合模式")
    int FLOW_MODE_ALL = 3;

    /**
     * 职员
     */
    @Defined(key = "flowPluginType", value = "职员插件")
    int FLOW_PLUGIN_STAFFER = 0;

    /**
     * 群组
     */
    @Defined(key = "flowPluginType", value = "群组插件")
    int FLOW_PLUGIN_GROUP = 1;

    /**
     * 自主
     */
    @Defined(key = "flowPluginType", value = "自主插件")
    int FLOW_PLUGIN_SELF = 2;

    /**
     * 上级主管
     */
    @Defined(key = "flowPluginType", value = "上级主管")
    int FLOW_PLUGIN_ORG = 3;

    /**
     * 子流程插件
     */
    int FLOW_PLUGIN_SUBFLOW = 998;

    /**
     * 结束
     */
    @Defined(key = "flowPluginType", value = "结束插件")
    int FLOW_PLUGIN_END = 999;

    /**
     * 无
     */
    int FLOW_PLUGIN_NONE = 9999;

    /**
     * 流程初始化
     */
    @Defined(key = "flowStatus", value = "初始")
    int FLOW_STATUS_INIT = 0;

    /**
     * 流程发布
     */
    @Defined(key = "flowStatus", value = "发布")
    int FLOW_STATUS_REALSE = 1;

    /**
     * 流程废弃
     */
    @Defined(key = "flowStatus", value = "废弃")
    int FLOW_STATUS_DROP = 2;

    /**
     * 流程实例开始
     */
    int FLOW_INSTANCE_BEGIN = 0;

    /**
     * 流程实例结束
     */
    int FLOW_INSTANCE_END = 999;

    /**
     * FK的userId的index值
     */
    int FK_INDEX_USERID = 1;

    /**
     * 只读
     */
    int TEMPLATE_READONLY_YES = 1;

    /**
     * 只读-no
     */
    int TEMPLATE_READONLY_NO = 0;

    /**
     * 写操作-yes
     */
    int TEMPLATE_EDIT_YES = TEMPLATE_READONLY_YES;

    /**
     * 写操作-no
     */
    int TEMPLATE_EDIT_NO = TEMPLATE_READONLY_NO;

    /**
     * 不具备
     */
    int OPERATION_NO = 0;

    /**
     * 具备
     */
    int OPERATION_YES = 1;

    /**
     * 环节类型--普通环节
     */
    @Defined(key = "tokenType", value = "普通环节")
    int TOKEN_TYPE_REALTOKEN = 0;

    /**
     * 环节类型--子流程
     */
    // @Defined(key = "tokenType", value = "子流程")
    int TOKEN_TYPEE_ABSTOKEN = 1;

    /**
     * 提交模式--自主选择
     */
    @Defined(key = "tokenMode", value = "自主选择")
    int TOKEN_MODE_SELF = 0;

    /**
     * 提交模式--自动,不需要选择人员
     */
    @Defined(key = "tokenMode", value = "自动选择")
    int TOKEN_MODE_AUTO = 1;

    /**
     * 提交模式--选择指定的人员
     */
    @Defined(key = "tokenMode", value = "选择指定范围")
    int TOKEN_MODE_SELECT = 2;

    @Defined(key = "instanceOper", value = "提交")
    int OPERATION_SUBMIT = 0;

    @Defined(key = "instanceOper", value = "通过")
    int OPERATION_PASS = 1;

    @Defined(key = "instanceOper", value = "驳回到上一环节")
    int OPERATION_REJECT = 2;

    @Defined(key = "instanceOper", value = "驳回到初始")
    int OPERATION_REJECTALL = 3;

    @Defined(key = "instanceOper", value = "异常终止")
    int OPERATION_EXEND = 4;

    @Defined(key = "instanceOper", value = "结束")
    int OPERATION_END = 5;

    @Defined(key = "instanceOper", value = "驳回到父流程的上一环节")
    int OPERATION_PARENT_REJECT = 6;
}
