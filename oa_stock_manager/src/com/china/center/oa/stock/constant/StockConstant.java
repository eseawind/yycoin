/**
 *
 */
package com.china.center.oa.stock.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * @author Administrator
 */
public interface StockConstant
{
    /**
     * 初始态
     */
    @Defined(key = "stockStatus", value = "初始态")
    int STOCK_STATUS_INIT = 0;

    /**
     * 待事业部经理审核
     */
    @Defined(key = "stockStatus", value = "待事业部经理审核")
    int STOCK_STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    @Defined(key = "stockStatus", value = "驳回")
    int STOCK_STATUS_REJECT = 2;

    /**
     * 待询价员询价
     */
    @Defined(key = "stockStatus", value = "待询价员询价")
    int STOCK_STATUS_MANAGERPASS = 3;

    /**
     * 待采购主管审核
     */
    @Defined(key = "stockStatus", value = "待采购经理审核")
    int STOCK_STATUS_PRICEPASS = 4;

    /**
     * 待董事长审核
     */
    @Defined(key = "stockStatus", value = "待董事长审核")
    int STOCK_STATUS_STOCKPASS = 5;

    /**
     * 待采购拿货
     */
    @Defined(key = "stockStatus", value = "待采购拿货")
    int STOCK_STATUS_STOCKMANAGERPASS = 6;

    /**
     * 待结束采购
     */
    @Defined(key = "stockStatus", value = "待结束采购")
    int STOCK_STATUS_END = 7;

    /**
     * 采购到货(结束)
     */
    @Defined(key = "stockStatus", value = "采购到货(结束)")
    int STOCK_STATUS_LASTEND = 8;

    /**
     * 待副总经理审核
     */
    @Defined(key = "stockStatus", value = "待副总经理审核")
    int STOCK_STATUS_STOCKPASS_DUTYCEO = 9;
    
    /**
     * 待总经理审核
     */
    @Defined(key = "stockStatus", value = "待总经理审核")
    int STOCK_STATUS_STOCKPASS_CEO = 10;
    
    /**
     * 采购item的开始(没有询价)
     */
    @Defined(key = "stockItemStatus", value = "开始")
    int STOCK_ITEM_STATUS_INIT = 0;

    /**
     * 采购item的结束(已经询价)
     */
    @Defined(key = "stockItemStatus", value = "结束")
    int STOCK_ITEM_STATUS_END = 1;

    /**
     * 采购item的询价结束
     */
    @Defined(key = "stockItemStatus", value = "询价完成")
    int STOCK_ITEM_STATUS_ASK = 2;

    /**
     * 无异常
     */
    @Defined(key = "stockExceptStatus", value = "无异常")
    int EXCEPTSTATUS_COMMON = 0;

    /**
     * 异常(价格不是最小的)
     */
    @Defined(key = "stockExceptStatus", value = "异常(价格不是最小)")
    int EXCEPTSTATUS_EXCEPTION_MIN = 1;

    /**
     * 异常(钱过多)
     */
    @Defined(key = "stockExceptStatus", value = "异常(采购金额过多)")
    int EXCEPTSTATUS_EXCEPTION_MONEY = 2;

    int DISPLAY_YES = 0;

    int DISPLAY_NO = 1;

    /**
     * 没有付款
     */
    @Defined(key = "stockPay", value = "未付款")
    int STOCK_PAY_NO = 0;

    /**
     * 付款
     */
    @Defined(key = "stockPay", value = "付款")
    int STOCK_PAY_YES = 1;

    /**
     * 付款申请
     */
    int STOCK_PAY_APPLY = 2;

    /**
     * 驳回
     */
    int STOCK_PAY_REJECT = 3;

    /**
     * 采购的item是否被库单关联 否
     */
    int STOCK_ITEM_REF_NO = 0;

    /**
     * 采购的item是否被库单关联 是
     */
    int STOCK_ITEM_REF_YES = 1;

    /**
     * 采购未逾期
     */
    int STOCK_OVERTIME_NO = 0;

    /**
     * 采购逾期
     */
    int STOCK_OVERTIME_YES = 1;

    /**
     * 采购单付款状态--初始
     */
    int STOCK_ITEM_PAY_STATUS_INIT = 0;

    /**
     * 采购单付款状态--使用
     */
    int STOCK_ITEM_PAY_STATUS_USED = 1;

    /**
     * 公卖(公卖是采购人不承担成本的)
     */
    @Defined(key = "stockSailType", value = "公卖")
    int STOCK_SAILTYPE_PUBLIC = 0;

    /**
     * 自卖(自卖是采购人承担成本的,自卖的采购只能自己卖出,他人无法卖出的)
     */
    @Defined(key = "stockSailType", value = "自卖")
    int STOCK_SAILTYPE_SELF = 1;

    /**
     * 不要发票
     */
    @Defined(key = "stockInvoice", value = "不要发票")
    int INVOICE_NO = 1;

    /**
     * 需要发票
     */
    @Defined(key = "stockInvoice", value = "需要发票")
    int INVOICE_YES = 1;

    /**
     * 普通采购
     */
    @Defined(key = "stockStype", value = "普通采购")
    int STOCK_STYPE_COMMON = 0;

    /**
     * 代销采购
     */
    @Defined(key = "stockStype", value = "代销采购")
    int STOCK_STYPE_ISAIL = 1;

    /**
     * 外协加工
     */
    @Defined(key = "stockStype", value = "外协加工")
    int STOCK_STYPE_NSAIL = 2;

    /**
     * 没有生成库单
     */
    @Defined(key = "hasRef", value = "否")
    int STOCK_ITEM_HASREF_NO = 0;

    /**
     * 生成库单
     */
    @Defined(key = "hasRef", value = "是")
    int STOCK_ITEM_HASREF_YES = 1;

    /**
     * 未拿货
     */
    @Defined(key = "stockItemFech", value = "未拿货")
    int STOCK_ITEM_FECH_NO = 0;

    /**
     * 已拿货
     */
    @Defined(key = "stockItemFech", value = "已拿货")
    int STOCK_ITEM_FECH_YES = 1;

    /**
     * 销售采购
     */
    @Defined(key = "stockMode", value = "销售采购")
    int STOCK_MODE_SAIL = 0;

    /**
     * 生产采购
     */
    @Defined(key = "stockMode", value = "生产采购")
    int STOCK_MODE_MAKE = 1;
    
    /**
     * 管理
     */
    @Defined(key = "stockManagerType", value = "管理")
    int MANAGER_TYPE_MANAGER = 0;

    /**
     * 普通
     */
    @Defined(key = "stockManagerType", value = "普通")
    int MANAGER_TYPE_COMMON = 1;
    
    /**
     * 普通 - 旧货(作废)
     */
    int MANAGER_TYPE_COMMON2 = 2;
    
    /**
     * 普通 - 零税率(作废)
     */
    int MANAGER_TYPE_COMMON3 = 3;
    
    /**
     * QQ
     */
    @Defined(key = "stockWorkWay", value = "QQ")
    int STOCKWORK_WAY_QQ = 0;
    
    /**
     * 固话
     */
    @Defined(key = "stockWorkWay", value = "固话")
    int STOCKWORK_WAY_TEL = 1;
    
    /**
     * 手机
     */
    @Defined(key = "stockWorkWay", value = "手机")
    int STOCKWORK_WAY_MOBILE = 2;
    
    /**
     * 邮件
     */
    @Defined(key = "stockWorkWay", value = "邮件")
    int STOCKWORK_WAY_EMAIL = 3;
    
    /**
     * 传真
     */
    @Defined(key = "stockWorkWay", value = "传真")
    int STOCKWORK_WAY_FAX = 4;
    
    
    /**
     * 待处理
     */
    @Defined(key = "stockWorkStatus", value = "待处理")
    int STOCKWORK_STATUS_WAIT = 0;
    
    /**
     * 处理结束
     */
    @Defined(key = "stockWorkStatus", value = "处理结束")
    int STOCKWORK_STATUS_END = 1;
}
