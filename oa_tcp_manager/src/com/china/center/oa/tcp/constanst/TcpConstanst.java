/**
 * File Name: TcpConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.constanst;


import com.china.center.jdbc.annotation.Defined;


/**
 * TcpConstanst
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TcpConstanst
 * @since 3.0
 */
public interface TcpConstanst
{
    /**
     * 初始
     */
    @Defined(key = "tcpStatus", value = "初始")
    int TCP_STATUS_INIT = 0;

    /**
     * 驳回
     */
    @Defined(key = "tcpStatus", value = "驳回")
    int TCP_STATUS_REJECT = 1;

    /**
     * 待部门经理审批
     */
    @Defined(key = "tcpStatus", value = "待部门经理审批")
    int TCP_STATUS_WAIT_DEPARTMENT = 2;

    /**
     * 待大区经理审批
     */
    //@Defined(key = "tcpStatus", value = "待大区经理审批")
    int TCP_STATUS_WAIT_AREA = 3;

    /**
     * 待事业部经理审批
     */
    @Defined(key = "tcpStatus", value = "待事业部经理审批")
    int TCP_STATUS_WAIT_LOCATION = 4;

    /**
     * 待财务总监审批
     */
    //@Defined(key = "tcpStatus", value = "待财务副总裁审批")
    int TCP_STATUS_WAIT_CFO = 5;

    /**
     * 待总裁审批
     */
    @Defined(key = "tcpStatus", value = "待总经理审批")
    int TCP_STATUS_WAIT_CEO = 6;

    /**
     * 待董事长审批
     */
    //@Defined(key = "tcpStatus", value = "待董事长审批")
    int TCP_STATUS_WAIT_TOP = 7;
    
    /**
     * 待人事部审批
     */
    @Defined(key = "tcpStatus", value = "待人事部审批")
    int TCP_STATUS_HR = 8;

    /**
     * 待采购货比三家
     */
    @Defined(key = "tcpStatus", value = "待采购货比三家")
    int TCP_STATUS_WAIT_BUY = 11;

    /**
     * 待中心总监
     */
    //@Defined(key = "tcpStatus", value = "待中心总监审批")
    int TCP_STATUS_WAIT_CCHECK = 12;

    /**
     * 待财务审批
     */
    @Defined(key = "tcpStatus", value = "待财务审批")
    int TCP_STATUS_WAIT_CHECK = 20;

    /**
     * 待权签人会签
     */
    @Defined(key = "tcpStatus", value = "待权签人会签")
    int TCP_STATUS_WAIT_QQR = 21;

    /**
     * 待财务支付
     */
    @Defined(key = "tcpStatus", value = "待财务支付")
    int TCP_STATUS_WAIT_PAY = 22;

    /**
     * 待财务入账
     */
    @Defined(key = "tcpStatus", value = "待财务入账")
    int TCP_STATUS_LAST_CHECK = 23;

    /**
     * 待商务部审核
     */
    @Defined(key = "tcpStatus", value = "待商务部审核")
    int TCP_STATUS_BUSI_CHECK = 24;
    
    /**************************FOR PREINVOICE*******************************/
    /**
     * 待税务主管审核
     */
    @Defined(key = "tcpStatus", value = "待税务主管审核")
    int TCP_STATUS_TAX_CHECK = 25;
    
    /**
     * 待财务开票
     */
    @Defined(key = "tcpStatus", value = "待财务开票")
    int TCP_STATUS_TAX_INVOICE = 26;
    
    /**
     * 待申请人关联销售
     */
    @Defined(key = "tcpStatus", value = "待申请人关联销售")
    int TCP_STATUS_APPLY_RELATE = 27;
    
    /**
     * 待副总裁审批
     */
    @Defined(key = "tcpStatus", value = "待总监(副总经理)审批")
    int TCP_STATUS_WAIT_DEPUTYCEO = 28;
    
    /**
     * 出纳
     */
    @Defined(key = "tcpStatus", value = "出纳审核")
    int TCP_STATUS_FINANCE = 29;
    
    /**
     * 结束
     */
    @Defined(key = "tcpStatus", value = "结束")
    int TCP_STATUS_END = 99;

    /**
     * 借款
     */
    @Defined(key = "travelApplyBorrow", value = "借款")
    int TRAVELAPPLY_BORROW_YES = 1;

    /**
     * 不借款(不占预算)
     */
    @Defined(key = "travelApplyBorrow", value = "不借款(不占预算)")
    int TRAVELAPPLY_BORROW_NO = 0;

    /**
     * 不借款(占预算)
     */
    @Defined(key = "travelApplyBorrow", value = "不借款(占预算)")
    int TRAVELAPPLY_BORROW_NO_BUTHOLD = 2;
    
    /**
     * 普通报销
     */
    @Defined(key = "expenseSpecialType", value = "普通报销")
    int SPECIALTYPE_TEMPLATE_NO = 0;

    /**
     * 模板报销
     */
    @Defined(key = "expenseSpecialType", value = "模板报销")
    int SPECIALTYPE_TEMPLATE_YES = 1;

    /**
     * 公司付款给员工
     */
    @Defined(key = "expensePayType", value = "公司付款给员工")
    int PAYTYPE_PAY_YES = 1;

    /**
     * 收支均衡
     */
    @Defined(key = "expensePayType", value = "借款报销收支平衡")
    int PAYTYPE_PAY_OK = 0;

    /**
     * 员工付款给公司
     */
    @Defined(key = "expensePayType", value = "员工付款给公司")
    int PAYTYPE_PAY_NO = 2;

    /**
     * 现金
     */
    @Defined(key = "travelApplyReceiveType", value = "现金")
    int TRAVELAPPLY_RECEIVETYPE_CASH = 0;

    /**
     * 银行
     */
    @Defined(key = "travelApplyReceiveType", value = "银行")
    int TRAVELAPPLY_RECEIVETYPE_BANK = 1;

    /**
     * 出差申请处理的URL
     */
    String TCP_TRAVELAPPLY_PROCESS_URL = "../tcp/apply.do?method=findTravelApply&update=2&id=";
    
    /**
     * 提成申请处理的URL
     */
    String TCP_COMMIS_PROCESS_URL = "../tcp/apply.do?method=findTravelApply&update=1&id=";

    /**
     * 报销处理的URL
     */
    String TCP_EXPENSE_PROCESS_URL = "../tcp/expense.do?method=findExpense&update=2&id=";

    /**
     * 申请借款的详细URL
     */
    String TCP_TRAVELAPPLY_DETAIL_URL = "../tcp/apply.do?method=findTravelApply&id=";

    /**
     * 报销详细URL
     */
    String TCP_EXPENSE_DETAIL_URL = "../tcp/expense.do?method=findExpense&id=";
    
    /**
     * 返利处理的URL
     */
    String REBATE_PROCESS_URL = "../tcp/rebate.do?method=findRebate&update=2&id=";
    
    /**
     * 返利详细URL
     */
    String REBATE_DETAIL_URL = "../tcp/rebate.do?method=findRebate&id=";

    /**
     * 预开票处理的URL
     */
    String PREINVOICE_PROCESS_URL = "../tcp/preinvoice.do?method=findPreInvoice&update=2&id=";
    
    /**
     * 预开票详细URL
     */
    String PREINVOICE_DETAIL_URL = "../tcp/preinvoice.do?method=findPreInvoice&id=";
    
    /**
     * 预收退款处理的URL
     */
    String BACKPREPAY_PROCESS_URL = "../tcp/backprepay.do?method=findBackPrePay&update=2&id=";
    
    /**
     * 预收退款详细URL
     */
    String BACKPREPAY_DETAIL_URL = "../tcp/backprepay.do?method=findBackPrePay&id=";
    
    /**
     * 归属
     */
    @Defined(key = "tcpPool", value = "归属")
    int TCP_POOL_COMMON = 0;

    /**
     * 共享池
     */
    @Defined(key = "tcpPool", value = "共享池")
    int TCP_POOL_POOL = 1;

    /**
     * 销售系
     */
    @Defined(key = "tcpStype", value = "销售系")
    int TCP_STYPE_SAIL = 0;

    /**
     * 职能系
     */
    @Defined(key = "tcpStype", value = "职能系")
    int TCP_STYPE_WORK = 1;

    /**
     * 管理系
     */
    @Defined(key = "tcpStype", value = "管理系")
    int TCP_STYPE_MANAGER = 2;
    
    
    @Defined(key = "tcpType", value = "请假加班申请")
    int VOCATION_WORK = 4;
    
    @Defined(key = "tcpType", value = "提成特批申请")
    int COMMISSION = 5;

    /**
     * 差旅费申请及借款
     */
    @Defined(key = "tcpType", value = "差旅费申请及借款")
    int TCP_APPLYTYPE_TRAVEL = 0;

    /**
     * 业务招待费申请及借款
     */
    @Defined(key = "tcpType", value = "业务招待费申请及借款")
    int TCP_APPLYTYPE_ENTERTAIN = 1;

    /**
     * 日常办公和固定资产采购申请及借
     */
    @Defined(key = "tcpType", value = "日常办公和固定资产采购申请及借款")
    int TCP_APPLYTYPE_STOCK = 2;

    /**
     * 对公业务申请及借款
     */
    @Defined(key = "tcpType", value = "对公业务申请及借款")
    int TCP_APPLYTYPE_PUBLIC = 3;
    
    /**
     * 中收申请
     */
    @Defined(key = "tcpType", value = "中收申请")
    int TCP_APPLYTYPE_MID = 7;

    /**
     * 差旅费报销
     */
    @Defined(key = "tcpType", value = "差旅费报销")
    int TCP_EXPENSETYPE_TRAVEL = 11;

    /**
     * 业务招待费报销
     */
    @Defined(key = "tcpType", value = "业务招待费报销")
    int TCP_EXPENSETYPE_ENTERTAIN = 12;

    /**
     * 日常费用报销(日常办公和固定资产采购申请及借/对公业务申请及借款)
     */
    @Defined(key = "tcpType", value = "日常费用报销")
    int TCP_EXPENSETYPE_PUBLIC = 13;

    /**
     * 通用费用报销
     */
    @Defined(key = "tcpType", value = "通用费用报销")
    int TCP_EXPENSETYPE_COMMON = 14;

    /**
     * 中收报销
     */
    @Defined(key = "tcpType", value = "中收报销")
    int TCP_EXPENSETYPE_MID = 15;
    
    /**
     * 返利
     */
    @Defined(key = "tcpType", value = "返利")
    int TCP_REBATETYPE = 21;
    
    /**
     * 预开票
     */
    @Defined(key = "tcpType", value = "预开票")
    int TCP_PREINVOICE = 22;
    
    /**
     * 申请预收退款
     */
    @Defined(key = "tcpType", value = "申请预收退款")
    int TCP_BACKPREPAY = 23;
    
    /**
     * 未关联报销
     */
    @Defined(key = "tcpApplyFeedback", value = "未关联报销")
    int TCP_APPLY_FEEDBACK_NO = 0;

    /**
     * 已关联报销(说明借款结束)
     */
    @Defined(key = "tcpApplyFeedback", value = "已关联报销")
    int TCP_APPLY_FEEDBACK_YES = 1;

    /**
     * 公司付款给员工
     */
    @Defined(key = "tcpPayType", value = "公司付款给员工")
    int PAYTYPE_GPAY_YES = 1;

    /**
     * 收支均衡
     */
    @Defined(key = "tcpPayType", value = "借款报销收支平衡")
    int PAYTYPE_GPAY_OK = 0;

    /**
     * 员工付款给公司
     */
    @Defined(key = "tcpPayType", value = "员工付款给公司")
    int PAYTYPE_GPAY_NO = 2;

    /**
     * 员工付款给公司
     */
    @Defined(key = "tcpPayType", value = "员工申请借款")
    int PAYTYPE_GPAY_BO = 98;
        
    /**
     * 是否合规-合规
     */
    @Defined(key = "tcpComplianceType", value = "合规")
    String COMPLIANCE_YES = "0";
    
    /**
     * 不合规
     */
    @Defined(key = "tcpComplianceType", value = "不合规")
    String COMPLIANCE_NO = "1";
    
    /**
     * 请假/加班>目的类型
     */
    @Defined(key = "purposeTypeContent", value = "无")
    int PURPOSETYPE = -1;
    @Defined(key = "purposeTypeContent", value = "加班申请")
    int JIABANSHENQING = 21;
    @Defined(key = "purposeTypeContent", value = "请假申请")
    int QINGJIASHENQING = 31;
    @Defined(key = "purposeTypeContent", value = "出差撤销")
    int CHUCHAICHEXIAO = 12;
    @Defined(key = "purposeTypeContent", value = "加班撤销")
    int JIABANCHEXIAO = 22;
    @Defined(key = "purposeTypeContent", value = "请假撤销")
    int QINGJIACHEXIAO = 32;
    
    
    /**
     * 请假类型
     */
    @Defined(key = "vocationTypeContent", value = "无")
    int VOCATIONTYPE = -1;
    @Defined(key = "vocationTypeContent", value = "病假")
    int BINGJIA = 1;
    @Defined(key = "vocationTypeContent", value = "事假")
    int SHIJIA = 2;
    @Defined(key = "vocationTypeContent", value = "婚假")
    int HUNJIA = 3;
    @Defined(key = "vocationTypeContent", value = "产假")
    int CHANJIA = 4;
    @Defined(key = "vocationTypeContent", value = "丧假")
    int SANGJIA = 5;
    @Defined(key = "vocationTypeContent", value = "年假")
    int NIANJIA = 6;
    @Defined(key = "vocationTypeContent", value = "调休")
    int TIAOXIU = 7;
    
    /**
     * 申请对象
     */
    @Defined(key = "selApplyobj", value = "个人")
    int PERSONAL = 0;
    
    /**
     * 申请对象
     */
    @Defined(key = "selApplyobj", value = "部门")
    int DEPARTMENT = 1;
    
    /**
     * 申请类型
     */
    @Defined(key = "selApplyType", value = "成本扣减")
    int COST_DEDUCTION = 0;
    
    /**
     * 申请类型
     */
    @Defined(key = "selApplyType", value = "费用扣减")
    int EXPENSES_DEDUCTION = 1;
    
    /**
     * 申请类型
     */
    @Defined(key = "selApplyType", value = "成本变费用")
    int COST_CHANGE = 1;
    
    /**
     * 变更类型
     */
    @Defined(key = "selChangeType", value = "指定金额")
    int SPECI_MONEY = 1;
    
    /**
     * 变更类型
     */
    @Defined(key = "selChangeType", value = "统计金额")
    int COUNT_MONEY = 2;
    
    
    /**
     * 初始
     */
    @Defined(key = "commissionStatus", value = "初始")
    int COMMISSION_STATUS_INIT = 0;

    /**
     * 驳回
     */
    @Defined(key = "commissionStatus", value = "驳回")
    int COMMISSION_STATUS_REJECT = 1;


    /**
     * 待事业部经理审批
     */
    @Defined(key = "commissionStatus", value = "待事业部经理审批")
    int COMMISSION_STATUS_WAIT_LOCATION = 4;


    /**
     * 待总裁审批
     */
    @Defined(key = "commissionStatus", value = "待总裁审批")
    int COMMISSION_STATUS_WAIT_CEO = 6;

    /**
     * 待董事长审批
     */
    @Defined(key = "commissionStatus", value = "待董事长审批")
    int COMMISSION_STATUS_WAIT_TOP = 7;


    /**
     * 结束
     */
    @Defined(key = "commissionStatus", value = "结束")
    int COMMISSION_STATUS_END = 99;
    
    /**
     * 定制返利
     */
    @Defined(key = "rebateTypes", value = "定制返利")
    int REBATETYPE_CUSTOMIZE = 0;

    /**
     * 自有返利
     */
    @Defined(key = "rebateTypes", value = "自有返利")
    int REBATETYPE_SELF = 1;

    /**
     * 政策返利
     */
    @Defined(key = "rebateTypes", value = "政策返利")
    int REBATETYPE_POLICY = 2;
    
    /**
     * 增值税可抵扣17%"
     */
    @Defined(key = "preInvoiceType", value = "增值税可抵扣17%")
    int PREINVOICE_TAX17_YES = 0;

    /**
     * 增值税不可抵扣17%
     */
    @Defined(key = "preInvoiceType", value = "增值税不可抵扣17%")
    int PREINVOICE_TAX17_NO = 1;
    
    /**
     * 增值税0%
     */
    @Defined(key = "preInvoiceType", value = "增值税0%")
    int PREINVOICE_TAX0 = 2;

    /**
     * 旧货2%
     */
    @Defined(key = "preInvoiceType", value = "旧货2%")
    int PREINVOICE_TAX2 = 3;
    
    /**
     * 普通6%
     */
    @Defined(key = "preInvoiceType", value = "普通6%")
    int PREINVOICE_TAX6 = 4;
}
