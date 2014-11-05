/**
 * File Name: OutConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.constanst;


import com.china.center.jdbc.annotation.Defined;


/**
 * OutConstanst
 * 
 * @author ZHUZHU
 * @version 2008-10-4
 * @see
 * @since
 */
public interface OutConstant
{
    /**
     * 销售单
     */
    int OUT_TYPE_OUTBILL = 0;

    /**
     * 入库单
     */
    int OUT_TYPE_INBILL = 1;
    
    /**
     * 入库单
     */
    int OUT_TYPE_DIAOBO = 3;

    /**
     * 信用正常
     */
    int OUT_CREDIT_COMMON = 0;

    /**
     * 信用超支(临时的)
     */
    int OUT_CREDIT_OVER = 1;

    /**
     * 价格为0
     */
    int OUT_CREDIT_MIN = 2;

    /**
     * 货到收款
     */
    int OUT_SAIL_TYPE_COMMON = 0;

    /**
     * 款到发货
     */
    int OUT_SAIL_TYPE_MONEY = 1;

    /**
     * 使用业务员信用额度和客户信用额度(客户信用额度优先)
     */
    int OUT_SAIL_TYPE_CREDIT_AND_CUR = 2;

    /**
     * 使用业务员信用额度和客户信用额度还不够的时候,分公司总经理担保
     */
    int OUT_SAIL_TYPE_LOCATION_MANAGER = 3;

    /**
     * 保存
     */
    @Defined(key = "outStatus", value = "保存")
    int STATUS_SAVE = 0;

    /**
     * 待结算中心审批
     */
    @Defined(key = "outStatus", value = "待商务审批")
    int STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    @Defined(key = "outStatus", value = "驳回")
    int STATUS_REJECT = 2;

    /**
     * 待回款(这里的销售单库存就变动了)(一般此通过即是销售单已经OK status in (3, 4))
     */
    @Defined(key = "outStatus", value = "已出库")
    int STATUS_PASS = 3;

    /**
     * 财务核对(单据结束)
     */
    @Defined(key = "outStatus", value = "已发货")
    int STATUS_SEC_PASS = 4;

    /**
     * 待财务审批(仅仅是其他入库的时候)
     */
    @Defined(key = "outStatus", value = "待财务审批")
    int OUT_STATUS_SECOND_PASS = 5;

    /**
     * 结算中心通过
     */
    @Defined(key = "outStatus", value = "待物流审批")
    int STATUS_MANAGER_PASS = 6;

    /**
     * 物流管理员通过
     */
    @Defined(key = "outStatus", value = "待库管审批")
    int STATUS_FLOW_PASS = 7;

    /**
     * 待信用担保人审批
     */
    @Defined(key = "outStatus", value = "待信用担保人审批")
    int STATUS_LOCATION_MANAGER_CHECK = 8;

    /**
     * 待总裁审批
     */
    @Defined(key = "outStatus", value = "待总裁审批")
    int STATUS_CEO_CHECK = 9;

    /**
     * 待董事长审批(入库单专用)
     */
    @Defined(key = "outStatus", value = "待董事长审批")
    int STATUS_CHAIRMA_CHECK = 10;

    /**
     * 未付款
     */
    @Defined(key = "outPay", value = "未付款")
    int PAY_NOT = 0;

    /**
     * 付款
     */
    @Defined(key = "outPay", value = "已付款")
    int PAY_YES = 1;

    /**
     * 过期(这里的过期不再pay里面,真实的pay只有0，1)
     */
    @Defined(key = "outPay", value = "过期付款")
    int PAY_OVER = 2;

    /**
     * 非在途
     */
    @Defined(key = "inway", value = "非在途")
    int IN_WAY_NO = 0;

    /**
     * 在途中
     */
    @Defined(key = "inway", value = "在途中")
    int IN_WAY = 1;

    /**
     * 在途结束
     */
    @Defined(key = "inway", value = "在途结束")
    int IN_WAY_OVER = 2;

    /**
     * 销售出库
     */
    @Defined(key = "outType_out", value = "销售出库")
    int OUTTYPE_OUT_COMMON = 0;

    /**
     * 个人领样
     */
    @Defined(key = "outType_out", value = "个人领样")
    int OUTTYPE_OUT_SWATCH = 1;

    /**
     * 零售
     */
    @Defined(key = "outType_out", value = "零售")
    int OUTTYPE_OUT_RETAIL = 2;

    /**
     * 委托代销
     */
    @Defined(key = "outType_out", value = "委托代销")
    int OUTTYPE_OUT_CONSIGN = 3;

    /**
     * 赠送
     */
    @Defined(key = "outType_out", value = "赠送")
    int OUTTYPE_OUT_PRESENT = 4;
    
    /**
     * 客户铺货(原叫巡展) （与个人领样类拟，但客户允许选，且也要允许转销售）
     */
    @Defined(key = "outType_out", value = "客户铺货")
    int OUTTYPE_OUT_SHOW = 5;
    
    /**
     * 巡展领样 （与个人领样类拟，但客户允许选，且也要允许转销售）
     */
    @Defined(key = "outType_out", value = "巡展领样")
    int OUTTYPE_OUT_SHOWSWATCH = 6;
    
    /**
     * 采购入库
     */
    @Defined(key = "outType_in", value = "采购入库")
    int OUTTYPE_IN_COMMON = 0;

    /**
     * 调拨
     */
    @Defined(key = "outType_in", value = "调拨")
    int OUTTYPE_IN_MOVEOUT = 1;

    /**
     * 报废出库
     */
    @Defined(key = "outType_in", value = "报废")
    int OUTTYPE_IN_DROP = 2;

    /**
     * 系统纠正
     */
    @Defined(key = "outType_in", value = "系统纠正")
    int OUTTYPE_IN_ERRORP = 3;

    /**
     * 领样退货
     */
    @Defined(key = "outType_in", value = "领样退库")
    int OUTTYPE_IN_SWATCH = 4;

    /**
     * 销售退库
     */
    @Defined(key = "outType_in", value = "销售退库")
    int OUTTYPE_IN_OUTBACK = 5;

    /**
     * 采购退货
     */
    @Defined(key = "outType_in", value = "采购退货")
    int OUTTYPE_IN_STOCK = 6;

    /**
     * 赠品退货
     */
    @Defined(key = "outType_in", value = "赠品退货")
    int OUTTYPE_IN_PRESENT = 7;
    
    /**
     * 其他入库
     */
    @Defined(key = "outType_in", value = "其他入库")
    int OUTTYPE_IN_OTHER = 99;
    
    /**
     * 调拨申请
     */
    @Defined(key = "outType_in", value = "调拨申请")
    int OUTTYPE_OUT_APPLY = 25;

    /**
     * 查询无实际用处
     */
    @Defined(key = "outType_in", value = "委托退货")
    int OUTTYPE_IN_SETTLE = 98;
    

    /**
     * 不需要发票
     */
    int HASINVOICE_NO = 0;

    /**
     * 需要
     */
    int HASINVOICE_YES = 1;

    /**
     * 流程中决定保存
     */
    String FLOW_DECISION_SAVE = "保存";

    /**
     * 流程中决定提交
     */
    String FLOW_DECISION_SUBMIT = "提交";

    /**
     * 临时的状态
     */
    int STATUS_TEMP = 99;

    /**
     * 可开票
     */
    @Defined(key = "invoiceStatus", value = "可开票")
    int INVOICESTATUS_INIT = 0;

    /**
     * 全部开票
     */
    @Defined(key = "invoiceStatus", value = "全部开票")
    int INVOICESTATUS_END = 1;

    /**
     * 提交
     */
    @Defined(key = "outBalanceStatus", value = "提交")
    int OUTBALANCE_STATUS_SUBMIT = 0;

    /**
     * 结算中心通过
     */
    @Defined(key = "outBalanceStatus", value = "结算中心通过")
    int OUTBALANCE_STATUS_PASS = 1;

    /**
     * 库管通过
     */
    @Defined(key = "outBalanceStatus", value = "库管通过")
    int OUTBALANCE_STATUS_END = 99;

    /**
     * 驳回
     */
    @Defined(key = "outBalanceStatus", value = "驳回")
    int OUTBALANCE_STATUS_REJECT = 2;

    /**
     * 结算单
     */
    @Defined(key = "outBalanceType", value = "结算单")
    int OUTBALANCE_TYPE_SAIL = 0;

    /**
     * 退货单
     */
    @Defined(key = "outBalanceType", value = "委托退货单")
    int OUTBALANCE_TYPE_BACK = 1;

    /**
     * 已结算的退货单
     */
    @Defined(key = "outBalanceType", value = "结算退货单")
    int OUTBALANCE_TYPE_SAILBACK = 2;
    
    /**
     * 调出
     */
    @Defined(key = "moveOut", value = "调出")
    int MOVEOUT_OUT = 0;

    /**
     * 调入
     */
    @Defined(key = "moveOut", value = "调入")
    int MOVEOUT_IN = 1;

    /**
     * 调出回滚
     */
    @Defined(key = "moveOut", value = "调出回滚")
    int MOVEOUT_ROLLBACK = 2;
    
    /**
     * 调拨
     */
    @Defined(key = "moveOut", value = "调拨")
    int MOVEOUT_DIAOBO = 3;

    /**
     * 保存
     */
    @Defined(key = "buyStatus", value = "保存")
    int BUY_STATUS_SAVE = 0;

    /**
     * 待库管处理
     */
    @Defined(key = "buyStatus", value = "待库管处理")
    int BUY_STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    @Defined(key = "buyStatus", value = "驳回")
    int BUY_STATUS_REJECT = 2;

    /**
     * 待回款(这里的销售单库存就变动了)(一般此通过即是销售单已经OK status in (3, 4))
     */
    @Defined(key = "buyStatus", value = "待核对")
    int BUY_STATUS_PASS = 3;

    /**
     * 财务核对(单据结束)
     */
    @Defined(key = "buyStatus", value = "结束")
    int BUY_STATUS_SEC_PASS = 4;

    /**
     * 待财务审批(仅仅是其他入库的时候)
     */
    @Defined(key = "buyStatus", value = "待财务审批")
    int BUY_STATUS_SECOND_PASS = 5;

    /**
     * 待物流审批
     */
    @Defined(key = "buyStatus", value = "待物流审批")
    int BUY_STATUS_MANAGER_PASS = 6;

    /**
     * 物流管理员通过
     */
    @Defined(key = "buyStatus", value = "待库管审批")
    int BUY_STATUS_FLOW_PASS = 7;

    /**
     * 待事业部经理审批
     */
    @Defined(key = "buyStatus", value = "待事业部经理审批")
    int BUY_STATUS_LOCATION_MANAGER_CHECK = 8;

    /**
     * 待总裁审批
     */
    @Defined(key = "buyStatus", value = "待总裁审批")
    int BUY_STATUS_CEO_CHECK = 9;

    /**
     * 待董事长审批(入库单专用)
     */
    @Defined(key = "buyStatus", value = "待董事长审批")
    int BUY_STATUS_CHAIRMA_CHECK = 10;

    /**
     * 坏账状态-NO
     */
    int BADDEBTSCHECKSTATUS_NO = 0;

    /**
     * 坏账状态-YES
     */
    int BADDEBTSCHECKSTATUS_YES = 1;

    /**
     * 普通单据
     */
    @Defined(key = "outVtype", value = "普通单据")
    int VTYPE_DEFAULT = 0;

    /**
     * 关注单据
     */
    @Defined(key = "outVtype", value = "关注单据")
    int VTYPE_SPECIAL = 1;
    
    /**
     * 强制入库类型 - 0~7 
     * 20110401前销售单退货
     */
    @Defined(key = "forceBuyTypes", value="20110401前销售单退货")
    int FORCEBUYTYPE_NOCOST = 0;

    /**
     * 纳税实体错误
     */
    @Defined(key = "forceBuyTypes", value="纳税实体错误")
    int FORCEBUYTYPE_DUTYERROR = 1;
    
    /**
     * 原单找不到
     */
    @Defined(key = "forceBuyTypes", value="原单找不到")
    int FORCEBUYTYPE_ORDERNOTFOUND = 2;
    
    /**
     * 转客户或业务员
     */
    @Defined(key = "forceBuyTypes", value="转客户或业务员")
    int FORCEBUYTYPE_TRANSCUSTOMERORSTAFFER = 3;
    
    /**
     * 调品名
     */
    @Defined(key = "forceBuyTypes", value="调品名")
    int FORCEBUYTYPE_MODIFYPRODUCTNAME = 4;
    
    /**
     * 不良品拆解
     */
    @Defined(key = "forceBuyTypes", value="不良品拆解")
    int FORCEBUYTYPE_SPITBADPRODUCT = 5;
    
    /**
     * 私库转公库
     */
    @Defined(key = "forceBuyTypes", value="私库转公库")
    int FORCEBUYTYPE_PRITOPUB = 6;
    
    /**
     * 调整库存属性
     */
    @Defined(key = "forceBuyTypes", value="调整库存属性")
    int FORCEBUYTYPE_MODIFYSTOREAGEATTR = 7;
    
    /**
     * 报废 - 50 ~ 55
     * 物流报废
     */
    @Defined(key = "forceBuyTypes", value="物流报废")
    int DROP_FLOW = 50;
    
    /**
     * 事业部报废
     */
    @Defined(key = "forceBuyTypes", value="事业部报废")
    int DROP_INDUSTRY = 51;
    
    /**
     * 与事业部共同报废
     */
    @Defined(key = "forceBuyTypes", value="与事业部共同报废")
    int DROP_COMMON = 52;
    
    /**
     * 总部报废
     */
    @Defined(key = "forceBuyTypes", value="总部报废")
    int DROP_HQ = 53;
    
    /**
     * 业务员报废
     */
    @Defined(key = "forceBuyTypes", value="业务员报废")
    int DROP_STAFFER = 54;
    
    /**
     * 配件报废
     */
    @Defined(key = "forceBuyTypes", value="配件报废")
    int DROP_PART = 55;
    

    /**
     * 执行状态   
     * 执行
     */
    @Defined(key = "promStatus", value="执行")
    int OUT_PROMSTATUS_EXEC = 0;
    
    /**
     * 绑定
     */
    @Defined(key = "promStatus", value="绑定")
    int OUT_PROMSTATUS_BIND = 1;
    
    /**
     * 退货绑定
     */
    @Defined(key = "promStatus", value="退货绑定")
    int OUT_PROMSTATUS_BACKBIND = 2;
    
    /**
     * 退部分折扣绑定
     */
    @Defined(key = "promStatus", value="退部分折扣绑定")
    int OUT_PROMSTATUS_PARTBACKBIND = 3;
    
    /**
     * 无
     */
    @Defined(key = "promStatus", value="--")
    int OUT_PROMSTATUS_NONE = -1;
    
    /**
     * 空开空退 提交
     */
    @Defined(key = "outRepaireStatus", value = "提交")
    int OUT_REPAIRE_SUBMIT = 0;
    
    /**
     * 空开空退 通过
     */
    @Defined(key = "outRepaireStatus", value = "待审批")
    int OUT_REPAIRE_PASS = 1;
    
    /**
     * 空开空退 通过
     */
    @Defined(key = "outRepaireStatus", value = "结束")
    int OUT_REPAIRE_END = 2;
    
    /**
     * 空开空退 驳回
     */
    @Defined(key = "outRepaireStatus", value = "驳回")
    int OUT_REPAIRE_REJECT = 99;
    
    /**
     * 空开空退-调整纳税实体与开票类型
     */
    @Defined(key = "outRepaireReason", value = "调整纳税实体与开票类型")
    String OUT_REPAIREREASON_DUTYANDINVOICE = "1";
    
    /**
     * 空开空退-调整成本
     */
    @Defined(key = "outRepaireReason", value = "调整成本")
    String OUT_REPAIREREASON_COST = "2";
    
    /**
     * 空开空退-调整销售价
     */
    @Defined(key = "outRepaireReason", value = "调整销售价")
    String OUT_REPAIREREASON_SAIL = "3";
    
    /**
     * 空开空退-延长账期
     */
    @Defined(key = "outRepaireReason", value = "延长账期")
    String OUT_REPAIREREASON_PEROID = "4";
    
    /**
     * 空开空退-新单不自动勾款
     */
    @Defined(key = "outRepaireReason", value = "新单不自动勾款")
    String OUT_REPAIREREASON_DONOTAUTOPAY = "5";
    
    
    /**
     * 自提
     */
    @Defined(key = "outShipment", value = "自提")
    int OUT_SHIPPING_SELFSERVICE = 0;
    
    /**
     * 公司
     */
    @Defined(key = "outShipment", value = "公司")
    int OUT_SHIPPING_COMPANY = 1;
    
    /**
     * 第三方物流
     */
    @Defined(key = "outShipment", value = "第三方物流")
    int OUT_SHIPPING_3PL = 2;
    
    /**
     * 第三方货运
     */
    @Defined(key = "outShipment", value = "第三方货运")
    int OUT_SHIPPING_TRANSPORT = 3;
    
    /**
     * 第三方物流+货运
     */
    @Defined(key = "outShipment", value = "第三方物流+货运")
    int OUT_SHIPPING_3PLANDDTRANSPORT = 4;
    
    /**
     * 空发
     */
    @Defined(key = "outShipment", value = "空发")
    int OUT_SHIPPING_NOTSHIPPING = 99;    
    
    /**
     * 快递
     */
    @Defined(key = "deliverType", value="快递")
    int OUT_DELIVERTYPE_LOGISTICS = 0;
    
    /**
     * 货运
     */
    @Defined(key = "deliverType", value="货运")
    int OUT_DELIVERTYPE_TRANSPORT = 1;
    
    /**
     * 业务员支付
     */
    @Defined(key = "deliverPay", value="业务员支付")
    int OUT_DELIVERPAY_STAFFER = 1;
    
    /**
     * 公司支付
     */
    @Defined(key = "deliverPay", value="公司支付")
    int OUT_DELIVERPAY_COMPANY = 2;
    
    /**
     * 客户支付
     */
    @Defined(key = "deliverPay", value="客户支付")
    int OUT_DELIVERPAY_CUSTOMER = 3;
    
    /**
     * 结算中心通过
     */
    String FLOW_MANAGER = "MANAGERPASS";

    /**
     * 上海候凯实业有限公司
     */
    String EXCLUDE_DUTY1 = "A1201305101109845399";
    
    /**
     * 上海精延实业有限公司
     */
    String EXCLUDE_DUTY2 = "A1201305101109845398";
    
    /** 未回访 default */
    @Defined(key = "feedBackVisit", value = "未回访")
    int OUT_FEEDBACK_VISIT_NO = 0;

    /** 回访中 */
    @Defined(key = "feedBackVisit", value = "回访中")
    int OUT_FEEDBACK_VISIT_INWAY = 1;
    
    /** 回访结束 */
    @Defined(key = "feedBackVisit", value = "回访结束")
    int OUT_FEEDBACK_VISIT_YES = 2;
    
    /** 未对账 default */
    @Defined(key = "feedBackCheck", value = "未对账")
    int OUT_FEEDBACK_CHECK_NO = 0;

    /** 对账中 */
    @Defined(key = "feedBackCheck", value = "对账中")
    int OUT_FEEDBACK_CHECK_INWAY = 1;
    
    /** 对账结束 */
    @Defined(key = "feedBackCheck", value = "对账结束")
    int OUT_FEEDBACK_CHECK_YES = 2;
    
    @Defined(key = "appOutStatus", value = "审核中")
    int APP_OUT_STATUS_APPROVE = 1;
    
    @Defined(key = "appOutStatus", value = "未通过")
    int APP_OUT_STATUS_REJECT = 2;
    
    @Defined(key = "appOutStatus", value = "待发货")
    int APP_OUT_STATUS_FLOW = 3;
    
    @Defined(key = "appOutStatus", value = "结束")
    int APP_OUT_STATUS_FINISH = 4;
    
    String SWATCH_COMMENT = "领样人为样品保管人，不得转借，样品破损记领样人员费用，样品盘亏，由领样人承担损失赔偿责任。";
    
    /** 未返利 default */
    @Defined(key = "hasRebate", value = "未返利")
    int OUT_REBATE_NO = 0;

    /** 已返利 */
    @Defined(key = "hasRebate", value = "已返利")
    int OUT_REBATE_YES = 1;
    
    /**
     * 新退货登记问题
     */
    
    /**
     * 保存
     */
    @Defined(key = "outbackStatus", value = "保存")
    int OUTBACK_STATUS_SAVE = 0;
    
    /**
     * 待认领
     */
    @Defined(key = "outbackStatus", value = "待认领")
    int OUTBACK_STATUS_CLAIM = 1;
    
    /**
     * 待验货
     */
    @Defined(key = "outbackStatus", value = "待验货")
    int OUTBACK_STATUS_CHECK = 2;
    
    /**
     * 待入库
     */
    @Defined(key = "outbackStatus", value = "待入库")
    int OUTBACK_STATUS_IN = 3;
    
    /**
     * 结束
     */
    @Defined(key = "outbackStatus", value = "已入库")
    int OUTBACK_STATUS_FINISH = 4;
    
    /**
     * 勾款
     */
    @Defined(key = "outPayInsType", value = "勾款")
    int OUT_PAYINS_TYPE_PAY = 0;
    
    /**
     * 开票
     */
    @Defined(key = "outPayInsType", value = "开票")
    int OUT_PAYINS_TYPE_INVOICE = 1;
    
    /**
     * 审批中
     */
    @Defined(key = "outPayInsStatus", value = "审批中")
    int OUT_PAYINS_STATUS_APPROVE = 0;
    
    /**
     * 结束
     */
    @Defined(key = "outPayInsStatus", value = "结束")
    int OUT_PAYINS_STATUS_FINISH = 1;
    
    /**
     * 紧急
     */
    @Defined(key = "outEmergency", value = "紧急")
    int OUT_EMERGENCY_YES = 1;
    
    /**
     * 不紧急
     */
    @Defined(key = "outEmergency", value = "不紧急")
    int OUT_EMERGENCY_NO = 0;
    
    /**
     * 1、捆绑销售 
     */
    @Defined(key = "presentFlag", value = "捆绑销售")
    int OUT_PRESENT_COMBINE_SALE = 1;
    
    /**
     * 2、员工福利
     */
    @Defined(key = "presentFlag", value = "员工福利")
    int OUT_PRESENT_STAFF_WELFARE = 2;
    
    /**
     * 3、业务赠送 
     */
    @Defined(key = "presentFlag", value = "业务赠送 ")
    int OUT_PRESENT_FOR_BUSINESS = 3;
    
    /**
     * 4、物料类赠送
     */
    @Defined(key = "presentFlag", value = "物料类赠送")
    int OUT_PRESENT_MATERIAL = 4;
}
