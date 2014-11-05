/**
 * File Name: BudgetConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * BudgetConstant
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetConstant
 * @since 1.0
 */
public interface BudgetConstant
{
    /**
     * 操作锁
     */
    String BUDGETLOG_ADD_LOCK = "BUDGETLOG_ADD_LOCK";

    /**
     * 预算--初始化
     */
    @Defined(key = "budgetStatus", value = "初始化")
    int BUDGET_STATUS_INIT = 0;

    /**
     * 预算--驳回
     */
    @Defined(key = "budgetStatus", value = "驳回")
    int BUDGET_STATUS_REJECT = 1;

    /**
     * 提交
     */
    @Defined(key = "budgetStatus", value = "提交")
    int BUDGET_STATUS_SUBMIT = 2;

    /**
     * 预算--通过(正式预算)
     */
    @Defined(key = "budgetStatus", value = "通过")
    int BUDGET_STATUS_PASS = 3;

    /**
     * 预算--结束
     */
    @Defined(key = "budgetStatus", value = "结束")
    int BUDGET_STATUS_END = 99;

    /**
     * 预算执行--初始化
     */
    @Defined(key = "budgetCarry", value = "未执行")
    int BUDGET_CARRY_INIT = 0;

    /**
     * 预算执行--进行中
     */
    @Defined(key = "budgetCarry", value = "执行中")
    int BUDGET_CARRY_DOING = 1;

    /**
     * 预算执行--执行平衡
     */
    @Defined(key = "budgetCarry", value = "平衡")
    int BUDGET_CARRY_BALANCE = 2;

    /**
     * 预算执行--超支
     */
    @Defined(key = "budgetCarry", value = "超支")
    int BUDGET_CARRY_OVER = 3;

    /**
     * 预算执行--节省
     */
    @Defined(key = "budgetCarry", value = "节约")
    int BUDGET_CARRY_LESS = 4;

    /**
     * 公司预算
     */
    @Defined(key = "budgetType", value = "公司预算")
    int BUDGET_TYPE_COMPANY = 0;

    /**
     * 事业部预算
     */
    @Defined(key = "budgetType", value = "事业部预算")
    int BUDGET_TYPE_LOCATION = 1;

    /**
     * 部门预算
     */
    @Defined(key = "budgetType", value = "部门预算")
    int BUDGET_TYPE_DEPARTMENT = 2;

    /**
     * 年度预算
     */
    @Defined(key = "budgetLevel", value = "年度预算")
    int BUDGET_LEVEL_YEAR = 0;

    /**
     * 暂时没有季度预算
     */
    // @Defined(key = "budgetLevel", value = "季度预算")
    // int BUDGET_LEVEL_QUARTER = 1;
    /**
     * 月度预算
     */
    @Defined(key = "budgetLevel", value = "月度预算")
    int BUDGET_LEVEL_MONTH = 2;

    /**
     * 根预算
     */
    String BUDGET_ROOT = "0";

    /**
     * 预算的执行任务从200开始到299(结束预算)
     */
    int BUGFET_PLAN_TYPE = 201;

    /**
     * 变更预算
     */
    @Defined(key = "budgetApplyType", value = "变更预算")
    int BUDGET_APPLY_TYPE_MODIFY = 0;

    /**
     * 追加预算
     */
    @Defined(key = "budgetApplyType", value = "追加预算")
    int BUDGET_APPLY_TYPE_ADD = 1;

    /**
     * 初始化
     */
    @Defined(key = "budgetApplyStatus", value = "初始化")
    int BUDGET_APPLY_STATUS_INIT = 0;

    /**
     * 驳回
     */
    @Defined(key = "budgetApplyStatus", value = "驳回")
    int BUDGET_APPLY_STATUS_REJECT = 1;

    /**
     * 提交
     */
    @Defined(key = "budgetApplyStatus", value = "待审批")
    int BUDGET_APPLY_STATUS_WAIT_APPROVE = 2;

    /**
     * 财务总监通过
     */
    @Defined(key = "budgetApplyStatus", value = "待总经理核准")
    int BUDGET_APPLY_STATUS_WAIT_COO = 3;

    /**
     * 总经理通过
     */
    @Defined(key = "budgetApplyStatus", value = "待董事长核准")
    int BUDGET_APPLY_STATUS_WAIT_CEO = 4;

    /**
     * 已核准
     */
    @Defined(key = "budgetApplyStatus", value = "已核准")
    int BUDGET_APPLY_STATUS_END = 99;

    /**
     * 预算详细URL
     */
    String BUDGET_DETAIL_URL = "../budget/budget.do?method=findBudget&update=1&id=";

    /**
     * 固定费用预算
     */
    @Defined(key = "feeItemType", value = "固定费用预算")
    int FEEITEM_TYPE_REGULARFEE = 0;

    /**
     * 固定资产投入预算
     */
    @Defined(key = "feeItemType", value = "固定资产投入预算")
    int FEEITEM_TYPE_REGULARBUDGET = 1;

    /**
     * 可变费用预算
     */
    @Defined(key = "feeItemType", value = "可变费用预算")
    int FEEITEM_TYPE_VARIABLE = 2;

    /**
     * 差旅费
     */
    String FEE_ITEM_TRAVELLING = "A1201112140004373609";

    /**
     * 预算使用来源-借款
     */
    @Defined(key = "budgetLogFromType", value = "借款")
    int BUDGETLOG_FROMTYPE_TCP = 0;

    /**
     * 预算使用来源-报销
     */
    @Defined(key = "budgetLogFromType", value = "报销")
    int BUDGETLOG_FROMTYPE_EXPENSE = 1;

    /**
     * 预算使用类型-预占
     */
    @Defined(key = "budgetLogUserType", value = "预占")
    int BUDGETLOG_USERTYPE_PRE = 0;

    /**
     * 预算使用类型使用
     */
    @Defined(key = "budgetLogUserType", value = "使用")
    int BUDGETLOG_USERTYPE_REAL = 1;
    
    /**
     * 预算使用类型流转
     */
    @Defined(key="budgetLogUserType", value = "流转")
    int BUDGETLOG_USERTYPE_TURN = 2;

    /**
     * 正式
     */
    @Defined(key = "budgetLogStatus", value = "正式(占用预算)")
    int BUDGETLOG_STATUS_OK = 0;

    /**
     * 临时(因为申请占用了部分预算,报销的时候先把申请的预算冻结,然后把报销的预算占用,这样避免重复占用预算)
     */
    @Defined(key = "budgetLogStatus", value = "临时(不占用预算)")
    int BUDGETLOG_STATUS_TEMP = 1;
}
