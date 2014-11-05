/**
 * File Name: TaxConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.constanst;


import com.china.center.jdbc.annotation.Defined;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * TaxConstanst
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxConstanst
 * @since 1.0
 */
public interface TaxConstanst
{
    /**
     * 资产
     */
    @Defined(key = "taxPtype", value = "资产")
    String TAX_PTYPE_PROPERTY = "A1110000000000000001";

    /**
     * 负债
     */
    @Defined(key = "taxPtype", value = "负债")
    String TAX_PTYPE_OBLIGATION = "A1110000000000000002";

    /**
     * 权益
     */
    @Defined(key = "taxPtype", value = "权益")
    String TAX_PTYPE_EQUITY = "A1110000000000000003";

    /**
     * 损益
     */
    @Defined(key = "taxPtype", value = "损益")
    String TAX_PTYPE_LOSS = "A1110000000000000004";

    /**
     * 借方
     */
    @Defined(key = "taxForward", value = "借方")
    int TAX_FORWARD_IN = 0;

    /**
     * 贷方
     */
    @Defined(key = "taxForward", value = "贷方")
    int TAX_FORWARD_OUT = 1;

    /**
     * 不计算
     */
    @Defined(key = "taxCheckStaffer", value = "不计算")
    int TAX_CHECKSTAFFER_NO = 0;

    /**
     * 计算
     */
    @Defined(key = "taxCheckStaffer", value = "计算")
    int TAX_CHECKSTAFFER_YES = 1;

    @Defined(key = "taxCheck", value = "不核算")
    int TAX_CHECK_NO = 0;

    @Defined(key = "taxCheck", value = "核算")
    int TAX_CHECK_YES = 1;

    @Defined(key = "taxType", value = "现金")
    int TAX_TYPE_CASH = 0;

    @Defined(key = "taxType", value = "银行")
    int TAX_TYPE_BANK = 1;

    @Defined(key = "taxType", value = "其他")
    int TAX_TYPE_OTHER = 2;

    @Defined(key = "taxType", value = "存货")
    int TAX_TYPE_SAVE = 3;

    @Defined(key = "taxType", value = "应付")
    int TAX_TYPE_PAY = 4;

    @Defined(key = "taxType", value = "应收")
    int TAX_TYPE_REVEIVE = 5;

    /**
     * 父级点
     */
    @Defined(key = "taxBottomFlag", value = "父级科目")
    int TAX_BOTTOMFLAG_ROOT = 0;

    /**
     * 子级点
     */
    @Defined(key = "taxBottomFlag", value = "最小科目")
    int TAX_BOTTOMFLAG_ITEM = 1;

    /**
     * 管理凭证
     */
    @Defined(key = "financeType", value = "管理")
    int FINANCE_TYPE_MANAGER = PublicConstant.MANAGER_TYPE_MANAGER;

    /**
     * 普通凭证
     */
    @Defined(key = "financeType", value = "普通")
    int FINANCE_TYPE_DUTY = PublicConstant.MANAGER_TYPE_COMMON;

    /**
     * 手工凭证
     */
    @Defined(key = "financeCreateType", value = "手工凭证")
    int FINANCE_CREATETYPE_HAND = 0;

    /**
     * 采购拿货
     */
    @Defined(key = "financeCreateType", value = "采购-采购拿货")
    int FINANCE_CREATETYPE_STOCK_IN = 1;

    /**
     * 采购付款
     */
    @Defined(key = "financeCreateType", value = "采购-采购付款")
    int FINANCE_CREATETYPE_STOCK_PAY = 2;

    /**
     * 产品合成
     */
    @Defined(key = "financeCreateType", value = "产品-产品合成")
    int FINANCE_CREATETYPE_PRODUCT_COMPOSE = 3;

    /**
     * 产品合成回滚
     */
    @Defined(key = "financeCreateType", value = "产品-产品合成回滚")
    int FINANCE_CREATETYPE_PRODUCT_COMPOSE_BACK = 4;

    /**
     * 产品调价
     */
    @Defined(key = "financeCreateType", value = "产品-产品调价")
    int FINANCE_CREATETYPE_PRODUCT_PRICE = 5;

    /**
     * 产品调价回滚
     */
    @Defined(key = "financeCreateType", value = "产品-产品调价回滚")
    int FINANCE_CREATETYPE_PRODUCT_PRICE_BACK = 6;
    
    /**
     * 产品拆分
     */
    @Defined(key = "financeCreateType", value = "产品-产品拆分")
    int FINANCE_CREATETYPE_PRODUCT_DECOMPOSE = 7;
    
    /**
     * 产品-金银料出入库
     */
    @Defined(key = "financeCreateType", value = "产品-金银料出入库")
    int FINANCE_CREATETYPE_GS_OUT = 8;

    /**
     * 入库-调出（调入接受时）
     */
    @Defined(key = "financeCreateType", value = "入库-调拨")
    int FINANCE_CREATETYPE_BUY_OUT = 20;

    /**
     * 入库-报废
     */
    @Defined(key = "financeCreateType", value = "入库-报废")
    int FINANCE_CREATETYPE_BUY_DROP = 21;

    /**
     * 入库-系统纠正
     */
    @Defined(key = "financeCreateType", value = "入库-系统纠正")
    int FINANCE_CREATETYPE_BUY_ERRORP = 22;

    /**
     * 入库-采购退货
     */
    @Defined(key = "financeCreateType", value = "入库-采购退货")
    int FINANCE_CREATETYPE_BUY_STOCKBACK = 23;

    /**
     * 入库-其他入库
     */
    @Defined(key = "financeCreateType", value = "入库-其他入库")
    int FINANCE_CREATETYPE_BUY_OTHER = 24;

    /**
     * 入库-个人领样退库
     */
    @Defined(key = "financeCreateType", value = "入库-个人领样退库")
    int FINANCE_CREATETYPE_OUT_SWATCHBACK = 25;

    /**
     * 入库-销售退库
     */
    @Defined(key = "financeCreateType", value = "入库-销售退库")
    int FINANCE_CREATETYPE_OUT_SAILBACK = 26;

    /**
     * 入库-领样转销售对冲
     */
    @Defined(key = "financeCreateType", value = "入库-领样转销售对冲")
    int FINANCE_CREATETYPE_OUT_DUICHONG = 27;

    /**
     * 入库-B单入库对冲
     */
    // @Defined(key = "financeCreateType", value = "入库-B单入库对冲")
    int FINANCE_CREATETYPE_OUT_BDUI = 28;

    /**
     * 销售-销售出库
     */
    @Defined(key = "financeCreateType", value = "销售-销售出库")
    int FINANCE_CREATETYPE_SAIL_COMMON = 40;

    /**
     * 销售-个人领样
     */
    @Defined(key = "financeCreateType", value = "销售-个人领样")
    int FINANCE_CREATETYPE_SAIL_SWATCH = 41;

    /**
     * 销售-领样转销售
     */
    @Defined(key = "financeCreateType", value = "销售-领样转销售")
    int FINANCE_CREATETYPE_SAIL_SWATCHSAIL = 42;

    /**
     * 销售-零售
     */
    @Defined(key = "financeCreateType", value = "销售-零售")
    int FINANCE_CREATETYPE_SAIL_RETAIL = 43;

    /**
     * 销售-委托发货
     */
    @Defined(key = "financeCreateType", value = "销售-委托发货")
    int FINANCE_CREATETYPE_SAIL_CONSIGN = 44;

    /**
     * 销售-委托结算单
     */
    @Defined(key = "financeCreateType", value = "销售-委托结算单")
    int FINANCE_CREATETYPE_SAIL_CONSIGN_PAY = 45;

    /**
     * 销售-委托退货单
     */
    @Defined(key = "financeCreateType", value = "销售-委托退货单")
    int FINANCE_CREATETYPE_SAIL_CONSIGN_BACK = 46;

    /**
     * 销售-赠送
     */
    @Defined(key = "financeCreateType", value = "销售-赠送")
    int FINANCE_CREATETYPE_SAIL_PRESENT = 47;

    /**
     * 销售-销售坏账
     */
    @Defined(key = "financeCreateType", value = "销售-销售坏账")
    int FINANCE_CREATETYPE_SAIL_BADMONEY = 48;

    /**
     * 销售-坏账取消(暂时取消)
     */
    @Defined(key = "financeCreateType_temp", value = "销售-坏账取消")
    int FINANCE_CREATETYPE_SAIL_BADMONEY_BACK = 49;

    /**
     * 销售-销售移交
     */
    @Defined(key = "financeCreateType", value = "销售-销售移交")
    int FINANCE_CREATETYPE_SAIL_TRAN = 50;

    /**
     * 资金-回款新增
     */
    @Defined(key = "financeCreateType", value = "资金-回款新增")
    int FINANCE_CREATETYPE_PAYBACK_ADD = 60;

    /**
     * 资金-回款认领
     */
    @Defined(key = "financeCreateType", value = "资金-回款认领")
    int FINANCE_CREATETYPE_BILL_GETPAY = 61;

    /**
     * 资金-回款退领(暂时取消)
     */
    // @Defined(key = "financeCreateType_temp", value = "资金-回款退领")
    int FINANCE_CREATETYPE_BILL_GETPAY_BACK = 62;

    /**
     * 资金-预收转应收
     */
    @Defined(key = "financeCreateType", value = "资金-预收转应收")
    int FINANCE_CREATETYPE_BILL_MAYTOREAL = 63;

    /**
     * 资金-预收转应收回滚
     */
    @Defined(key = "financeCreateType", value = "资金-预收转应收回滚")
    int FINANCE_CREATETYPE_BILL_MAYTOREAL_BACK = 64;

    /**
     * 资金-销售退款
     */
    @Defined(key = "financeCreateType", value = "资金-销售退款")
    int FINANCE_CREATETYPE_BILL_SAILBACK = 65;

    /**
     * 资金-预收退款
     */
    @Defined(key = "financeCreateType", value = "资金-预收退款")
    int FINANCE_CREATETYPE_BILL_MAYBACK = 66;

    /**
     * 资金-预收转费用
     */
    @Defined(key = "financeCreateType", value = "资金-预收转费用")
    int FINANCE_CREATETYPE_BILL_TOFEE = 67;

    /**
     * 资金-预收移交
     */
    @Defined(key = "financeCreateType", value = "资金-预收移交")
    int FINANCE_CREATETYPE_BILL_CHANGE = 68;

    /**
     * 资金-预收转货款
     */
    @Defined(key = "financeCreateType", value = "资金-预收转货款")
    int FINANCE_CREATETYPE_BILL_MAYTOSTOCKBACK = 96;
    
    /**
     * 资金-预收拆分
     */
    @Defined(key = "financeCreateType", value = "资金-预收拆分")
    int FINANCE_CREATETYPE_BILL_SPLIT = 100;
    
    /**
     * 资金-手续费/转账付款
     */
    @Defined(key = "financeCreateType", value = "资金-手续费/转账付款")
    int FINANCE_CREATETYPE_BILL_OUTPAY = 69;
    /**
     * 报销-出差借款
     */
    @Defined(key = "financeCreateType", value = "报销-出差借款")
    int FINANCE_CREATETYPE_TCP_BORROW = 70;

    /**
     * 报销-业务招待费借款
     */
    @Defined(key = "financeCreateType", value = "报销-业务招待费借款")
    int FINANCE_CREATETYPE_TCP_ENTERTAIN = 71;

    /**
     * 报销-日常办公和固定资产采购申请及借款
     */
    @Defined(key = "financeCreateType", value = "报销-日常办公借款")
    int FINANCE_CREATETYPE_TCP_STOCK = 72;

    /**
     * 报销-对公业务借款
     */
    @Defined(key = "financeCreateType", value = "报销-对公业务借款")
    int FINANCE_CREATETYPE_TCP_PUBLIC = 73;

    /**
     * 报销-出差报销
     */
    @Defined(key = "financeCreateType", value = "报销-出差报销")
    int FINANCE_CREATETYPE_EXPENSE_BORROW = 80;

    /**
     * 报销-业务招待费报销
     */
    @Defined(key = "financeCreateType", value = "报销-业务招待费报销")
    int FINANCE_CREATETYPE_EXPENSE_ENTERTAIN = 81;

    /**
     * 报销-日常费用报销
     */
    @Defined(key = "financeCreateType", value = "报销-日常费用报销")
    int FINANCE_CREATETYPE_EXPENSE_PUBLIC = 82;

    /**
     * 报销-通用报销
     */
    @Defined(key = "financeCreateType", value = "报销-通用报销")
    int FINANCE_CREATETYPE_EXPENSE_COMMON = 83;
    
    /**
     * 销售返利
     */
    @Defined(key = "financeCreateType", value = "销售返利")
    int FINANCE_CREATETYPE_REBATE_COMMON = 84;

    /**
     * 中收
     */
    @Defined(key = "financeCreateType", value = "中收申请")
    int FINANCE_CREATETYPE_MID_APPLY = 85;
    
    /**
     * 开票-确认付款
     */
    @Defined(key = "financeCreateType", value = "开票-确认付款")
    int FINANCE_CREATETYPE_INVOICEINS_CONFIRMPAY = 90;
    
    /**
     * 开票-退票
     */
    @Defined(key = "financeCreateType", value = "开票-退票")
    int FINANCE_CREATETYPE_INVOICEINS_RET = 91;
    
    /**
     * 内部资金-回款认领
     */
    @Defined(key = "financeCreateType", value = "资金-内部资金认领")
    int FINANCE_CREATETYPE_INNERBILL_GETPAY = 95;
    
    /**
     * 损益结转
     */
    @Defined(key = "financeCreateType", value = "损益结转")
    int FINANCE_CREATETYPE_TURN = 998;

    /**
     * 利润结转
     */
    @Defined(key = "financeCreateType", value = "利润结转")
    int FINANCE_CREATETYPE_PROFIT = 999;

    /**
     * 客户
     */
    @Defined(key = "unitType", value = "客户")
    int UNIT_TYPE_CUSTOMER = 0;

    /**
     * 供应商
     */
    @Defined(key = "unitType", value = "供应商")
    int UNIT_TYPE_PROVIDE = 1;

    /**
     * 纳税实体
     */
    @Defined(key = "unitType", value = "纳税实体")
    int UNIT_TYPE_DUTY = 2;

    /**
     * 默认级别
     */
    int TAX_LEVEL_DEFAULT = 0;

    /**
     * 最大级别
     */
    int TAX_LEVEL_MAX = 8;

    /**
     * 最小计算单位转换倍数
     */
    int DOUBLE_TO_INT = 10000;

    /**
     * 未核对
     */
    @Defined(key = "financeStatus", value = "未核对")
    int FINANCE_STATUS_NOCHECK = 0;

    /**
     * 已核对
     */
    @Defined(key = "financeStatus", value = "已核对")
    int FINANCE_STATUS_CHECK = 1;

    /**
     * 删除隐藏
     */
    @Defined(key = "financeStatus", value = "删除隐藏")
    int FINANCE_STATUS_HIDDEN = 2;

    /**
     * 未锁定
     */
    @Defined(key = "financeLock", value = "未锁定")
    int FINANCE_LOCK_NO = 0;

    /**
     * 已锁定
     */
    @Defined(key = "financeLock", value = "已锁定")
    int FINANCE_LOCK_YES = 1;

    /**
     * 银行
     */
    @Defined(key = "taxRefType", value = "银行")
    int TAX_REFTYPE_BANK = 0;

    /**
     * 暂记户
     */
    @Defined(key = "taxRefType", value = "暂记户")
    int TAX_REFTYPE_BANKTEMP = 1;

    /**
     * 原始
     */
    @Defined(key = "financeUpdateFlag", value = "原始")
    int FINANCE_UPDATEFLAG_NO = 0;

    /**
     * 更改
     */
    @Defined(key = "financeUpdateFlag", value = "更改")
    int FINANCE_UPDATEFLAG_YES = 1;

    /**
     * 结转的LOCK
     */
    String FINANCETURN_OPR_LOCK = "FINANCETURN_OPR_LOCK";

}
