/**
 * File Name: FinanceConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * FinanceConstant
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see FinanceConstant
 * @since 3.0
 */
public interface FinanceConstant
{
    /**
     * 管理属性
     */
    @Defined(key = "bankType", value = "管理属性")
    int BANK_TYPE_NOTDUTY = 0;

    /**
     * 非管理属性
     */
    @Defined(key = "bankType", value = "非管理属性")
    int BANK_TYPE_INDUTY = 1;

    /**
     * 回款单状态--未认领
     */
    @Defined(key = "paymentStatus", value = "未认领")
    int PAYMENT_STATUS_INIT = 0;

    /**
     * 回款单状态--已认领
     */
    @Defined(key = "paymentStatus", value = "已认领")
    int PAYMENT_STATUS_END = 1;

    /**
     * 回款单状态--已删除
     */
    @Defined(key = "paymentStatus", value = "已删除")
    int PAYMENT_STATUS_DELETE = 2;

    /**
     * 对公
     */
    @Defined(key = "paymentType", value = "对公")
    int PAYMENT_PAY_PUBLIC = 0;

    /**
     * 对私
     */
    @Defined(key = "paymentType", value = "对私")
    int PAYMENT_PAY_SELF = 1;

    /**
     * 销售收入
     */
    @Defined(key = "inbillType", value = "销售收入")
    int INBILL_TYPE_SAILOUT = 0;

    /**
     * 银行贷款(The loan is interest free)
     */
    @Defined(key = "inbillType", value = "银行贷款")
    int INBILL_TYPE_LOAN = 1;

    /**
     * 卖出公司资产
     */
    @Defined(key = "inbillType", value = "卖出公司资产")
    int INBILL_TYPE_ASSETS = 2;

    /**
     * 利息
     */
    @Defined(key = "inbillType", value = "利息")
    int INBILL_TYPE_INTEREST = 3;

    /**
     * 坏账
     */
    @Defined(key = "inbillType", value = "销售坏账")
    int INBILL_TYPE_BADOUT = 4;

    /**
     * 预收转费用
     */
    @Defined(key = "inbillType", value = "预收转费用")
    int INBILL_TYPE_FEE = 5;

    /**
     * 个人还款
     */
    @Defined(key = "inbillType", value = "个人还款")
    int INBILL_TYPE_UNBORROW = 6;

    /**
     * 采购退款
     */
    @Defined(key = "inbillType", value = "采购退款")
    int INBILL_TYPE_PURCHASEBACK = 97;
    
    /**
     * 转账
     */
    @Defined(key = "inbillType", value = "转账")
    int INBILL_TYPE_TRANSFER = 98;

    /**
     * 其他
     */
    @Defined(key = "inbillType", value = "其他")
    int INBILL_TYPE_OTHER = 99;

    /**
     * 未使用
     */
    @Defined(key = "paymentUseall", value = "未使用")
    int PAYMENT_USEALL_INIT = 0;

    /**
     * 全部使用
     */
    @Defined(key = "paymentUseall", value = "全部使用")
    int PAYMENT_USEALL_END = 1;

    /**
     * 提交
     */
    @Defined(key = "payApplyStatus", value = "提交")
    int PAYAPPLY_STATUS_INIT = 0;

    /**
     * 驳回
     */
    @Defined(key = "payApplyStatus", value = "驳回")
    int PAYAPPLY_STATUS_REJECT = 2;

    /**
     * 待稽核
     */
    @Defined(key = "payApplyStatus", value = "待稽核")
    int PAYAPPLY_STATUS_CHECK = 3;

    /**
     * 通过
     */
    @Defined(key = "payApplyStatus", value = "通过")
    int PAYAPPLY_STATUS_PASS = 1;

    /**
     * 回款转收款(就是回款转预收)
     */
    @Defined(key = "payApplyType", value = "回款转收款")
    int PAYAPPLY_TYPE_PAYMENT = 0;

    /**
     * 销售单绑定
     */
    @Defined(key = "payApplyType", value = "销售单绑定")
    int PAYAPPLY_TYPE_BING = 1;

    /**
     * 临时
     */
    int PAYAPPLY_TYPE_TEMP = 2;

    /**
     * 预收转费用
     */
    @Defined(key = "payApplyType", value = "预收转费用")
    int PAYAPPLY_TYPE_CHANGEFEE = 3;

    /**
     * 自动生成收款申请
     */
    int PAYAPPLY_TYPE_AUTO = 4;
    
    /**
     * 预收拆分，转其它客户
     */
    @Defined(key = "payApplyType", value = "拆预收转客户")
    int PAYAPPLY_TYPE_TRANSPAYMENT = 5;
    
    /**
     * 正常
     */
    @Defined(key = "outbillStatus", value = "正常")
    int OUTBILL_STATUS_INIT = 0;

    /**
     * 转账中 -> 改为 待财务经理审批
     */
    @Defined(key = "outbillStatus", value = "待财务经理审批")
    int OUTBILL_STATUS_SUBMIT = 1;
    
    /**
     * 待财务总监审批
     */
    @Defined(key = "outbillStatus", value = "待财务总监审批")
    int OUTBILL_STATUS_MANAGER = 3;

    /**
     * 转账结束
     */
    @Defined(key = "outbillStatus", value = "转账结束")
    int OUTBILL_STATUS_END = 2;

    /**
     * 转账待确认
     */
    @Defined(key = "outbillStatus", value = "转账待确认")
    int OUTBILL_STATUS_CONFIRM = 5;
    
    /**
     * 已收
     */
    @Defined(key = "inbillStatus", value = "已收")
    int INBILL_STATUS_PAYMENTS = 0;

    /**
     * 关联申请
     */
    @Defined(key = "inbillStatus", value = "关联申请")
    int INBILL_STATUS_PREPAYMENTS = 1;

    /**
     * 预收
     */
    @Defined(key = "inbillStatus", value = "预收")
    int INBILL_STATUS_NOREF = 2;

    /**
     * 未锁定
     */
    @Defined(key = "billLock", value = "未锁定")
    int BILL_LOCK_NO = 0;

    /**
     * 锁定
     */
    @Defined(key = "billLock", value = "锁定")
    int BILL_LOCK_YES = 1;

    /**
     * 自动
     */
    @Defined(key = "billCreateType", value = "自动")
    int BILL_CREATETYPE_AUTO = 0;

    /**
     * 人工
     */
    @Defined(key = "billCreateType", value = "人工")
    int BILL_CREATETYPE_HAND = 1;

    /**
     * 人工-坏账勾 款
     */
    @Defined(key = "billCreateType", value = "人工-坏账勾 款")
    int BILL_CREATETYPE_HANDBADOUT = 2;
    
    /**
     * 现金
     */
    @Defined(key = "outbillPayType", value = "现金")
    int OUTBILL_PAYTYPE_MONEY = 0;

    /**
     * 银行
     */
    @Defined(key = "outbillPayType", value = "银行")
    int OUTBILL_PAYTYPE_BANK = 1;

    /**
     * 采购付款
     */
    @Defined(key = "outbillType", value = "采购付款")
    int OUTBILL_TYPE_STOCK = 0;

    /**
     * 买固定资产
     */
    @Defined(key = "outbillType", value = "买固定资产")
    int OUTBILL_TYPE_BUY_ASSET = 1;

    /**
     * 买低值用品(买办公品)
     */
    @Defined(key = "outbillType", value = "买办公低值品")
    int OUTBILL_TYPE_BUY_COMMON = 2;

    /**
     * 买无形资产
     */
    @Defined(key = "outbillType", value = "买无形资产")
    int OUTBILL_TYPE_BUY_ABS = 3;

    /**
     * 支付代摊费用
     */
    @Defined(key = "outbillType", value = "支付代摊费用")
    int OUTBILL_TYPE_PAY_APPORTION = 4;

    /**
     * 长期股权投资
     */
    @Defined(key = "outbillType", value = "长期股权投资")
    int OUTBILL_TYPE_BUY_STOCK = 5;

    /**
     * 个人借款
     */
    @Defined(key = "outbillType", value = "个人借款")
    int OUTBILL_TYPE_BORROW = 6;

    /**
     * 押金和定金
     */
    @Defined(key = "outbillType", value = "押金和定金")
    int OUTBILL_TYPE_CASH = 7;

    /**
     * 费用报销
     */
    @Defined(key = "outbillType", value = "费用报销")
    int OUTBILL_TYPE_WIPEOUT = 8;

    /**
     * 税金
     */
    @Defined(key = "outbillType", value = "税金")
    int OUTBILL_TYPE_DUTY = 9;

    /**
     * 销售/预收退款
     */
    @Defined(key = "outbillType", value = "销售/预收退款")
    int OUTBILL_TYPE_OUTBACK = 10;

    /**
     * 手续费
     */
    @Defined(key = "outbillType", value = "手续费")
    int OUTBILL_TYPE_HANDLING = 11;

    /**
     * 资金-销售费用
     */
    @Defined(key = "outbillType", value = "资金-销售费用")
    int OUTBILL_TYPE_SALECHARGE = 11;
    
    /**
     * 采购预付款
     */
    @Defined(key = "outbillType", value = "采购预付款")
    int OUTBILL_TYPE_STOCKPREPAY = 15;
    
    /**
     * 转账
     */
    @Defined(key = "outbillType", value = "转账")
    int OUTBILL_TYPE_TRANSFER = 98;

    /**
     * 其他
     */
    @Defined(key = "outbillType", value = "其他")
    int OUTBILL_TYPE_OTHER = 99;

    /**
     * 销售单
     */
    @Defined(key = "invsoutType", value = "销售单")
    int INSVSOUT_TYPE_OUT = 0;

    /**
     * 委托结算
     */
    @Defined(key = "invsoutType", value = "委托结算")
    int INSVSOUT_TYPE_BALANCE = 1;

    /**
     * 初始
     */
    @Defined(key = "invoiceinsStatus", value = "初始")
    int INVOICEINS_STATUS_INIT = 0;

    /**
     * 待财务审核
     */
    @Defined(key = "invoiceinsStatus", value = "待财务开票")
    int INVOICEINS_STATUS_SUBMIT = 1;

    /**
     * 待稽核
     */
    @Defined(key = "invoiceinsStatus", value = "待财务稽核")
    int INVOICEINS_STATUS_CHECK = 2;

    /**
     * 待财务确认
     */
    @Defined(key = "invoiceinsStatus", value = "待财务确认")
    int INVOICEINS_STATUS_CONFIRM = 4;
    
    /**
     * 驳回
     */
    @Defined(key = "invoiceinsStatus", value = "驳回")
    int INVOICEINS_STATUS_REJECT = 98;

    /**
     * 结束
     */
    @Defined(key = "invoiceinsStatus", value = "结束")
    int INVOICEINS_STATUS_END = 99;
    
    /**
     * 保存 
     */
    @Defined(key = "invoiceinsStatus", value = "保存")
    int INVOICEINS_STATUS_SAVE = 3;

    /**
     * 普通
     */
    @Defined(key = "invoiceinsType", value = "普通")
    int INVOICEINS_TYPE_COMMON = 0;

    /**
     * 对分公司
     */
    @Defined(key = "invoiceinsType", value = "对分公司")
    int INVOICEINS_TYPE_DUTY = 1;

    /**
     * 默认
     */
    @Defined(key = "invoiceinssType", value = "AX-AX")
    int INVOICEINS_STYPE_AXAX = 0;

    /**
     * 数量相同、单价不同
     */
    @Defined(key = "invoiceinssType", value = "仅单价不同")
    int INVOICEINS_STYPE_A1A1_PD = 1;

    /**
     * 数量单价均不同
     */
    @Defined(key = "invoiceinssType", value = "数量单价不同")
    int INVOICEINS_STYPE_A1A1_APD = 2;

    /**
     * 所有场景
     */
    @Defined(key = "invoiceinssType", value = "A1-A2")
    int INVOICEINS_STYPE_A1A2 = 3;

    /**
     * 所有场景
     */
    @Defined(key = "invoiceinssType", value = "A2-A1")
    int INVOICEINS_STYPE_A2A1 = 4;

    /**
     * 初始
     */
    @Defined(key = "paymentChechStatus", value = "初始")
    int PAYMENTY_CHECKSTATUS_INIT = 0;

    /**
     * 回款核对
     */
    @Defined(key = "paymentChechStatus", value = "回款核对")
    int PAYMENTY_CHECKSTATUS_CHECK1 = 1;

    /**
     * 认款核对
     */
    @Defined(key = "paymentChechStatus", value = "认款核对")
    int PAYMENTY_CHECKSTATUS_CHECK2 = 2;

    /**
     * 删除核对
     */
    @Defined(key = "paymentChechStatus", value = "删除核对")
    int PAYMENTY_CHECKSTATUS_CHECK3 = 3;

    /**
     * 坏账账户ID
     */
    String BANK_BADDEBT = "A1201104050002047319";
    
    /**
     * 金额为0
     */
    int BILL_ZERO = 0;
    
    /**
     * 金额为0.0
     */
    String BILL_ZERO_DOUBLE = "0.00";
    
    /**
     * 发票抬头-单位
     */
    @Defined(key = "invoiceinsHeadType", value = "单位")
    int INVOICEINS_HEADTYPE_ENTERPRISE = 0;
    
    /**
     * 发票抬头-个人
     */
    @Defined(key = "invoiceinsHeadType", value = "个人")
    int INVOICEINS_HEADTYPE_INDIVIDUAL = 1;
    
    /**
     * 开票 - 确认已付款
     */
    int INVOICEINS_PAY_CONFIRM_STATUS_YES = 1;
    
    /**
     * 开票 - 待付款
     */
    int INVOICEINS_PAY_CONFIRM_STATUS_NO = 0;
    
    /**
     * 开票 - 待确认开票
     */
    int INVOICEINS_CONFIRM_STATUS_NO = 0;
    
    /**
     * 开票 - 确认已开票
     */
    int INVOICEINS_CONFIRM_STATUS_YES = 1;
    
    /**
     * 开票
     */
    @Defined(key = "invoiceinsOtype", value = "开票")
    int INVOICEINS_TYPE_OUT = 0;
    
    /**
     * 退票
     */
    @Defined(key = "invoiceinsOtype", value = "退票")
    int INVOICEINS_TYPE_IN = 1;
    
    /**
     * 已冻结
     */
    @Defined(key = "billFreezeType", value = "已冻结")
    int BILL_FREEZE_YES = 1;
    
    /**
     * 未冻结
     */
    @Defined(key = "billFreezeType", value = "未冻结")
    int BILL_FREEZE_NO = 0;
    
    /**
     * 外部
     */
    @Defined(key = "paymentCtype", value = "外部")
    int PAYMENTCTYPE_EXTERNAL = 0;
    
    /**
     * 内部
     */
    @Defined(key = "paymentCtype", value = "内部")
    int PAYMENTCTYPE_INTERNAL = 1;
}
