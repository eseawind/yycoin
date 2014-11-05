/**
 * File Name: TaxItemConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.constanst;

/**
 * TaxItemConstanst
 * 
 * @author ZHUZHU
 * @version 2011-6-12
 * @see TaxItemConstanst
 * @since 3.0
 */
public interface TaxItemConstanst
{
    /**
     * 应收账款(客户/职员/部门)
     */
    String REVEIVE_PRODUCT = "1132";

    /**
     * 其它应收-坏账(职员/部门)
     */
    String BAD_REVEIVE_PRODUCT = "1133-10";

    /**
     * 其他应收款-样品(部门/职员/产品/仓库 )
     */
    String OTHER_REVEIVE_PRODUCT = "1133-14";

    /**
     * 库存商品(产品/仓库)
     */
    String DEPOR_PRODUCT = "1243";

    /**
     * 库存商品-中转(产品/仓库)
     */
    String DEPOR_PRODUCT_TEMP = "1245";

    /**
     * 委托代销商品(产品/仓库)
     */
    String CONSIGN_PRODUCT = "1261";
    
    /**
     * 委托加工物资
     */
    String CONSIGN_PROCESS = "1251";

    /**
     * 应付账款-货款(单位)
     */
    String PAY_PRODUCT = "2122-01";

    /**
     * 预收账款(客户/职员/部门)
     */
    String PREREVEIVE_PRODUCT = "2131";

    /**
     * 营业外收入
     */
    String EXT_RECEIVE = "5301";

    /**
     * 主营业务税金及附加(部门)
     */
    String MAIN_TAX = "5402";

    /**
     * 主营业务收入(部门/职员/产品/仓区)
     */
    String MAIN_RECEIVE = "5101";

    /**
     * 主营业务成本(部门/职员/产品/仓区)
     */
    String MAIN_COST = "5401";

    /**
     * 调价收入(部门)
     */
    String PRICECHANGE_REVEIVE = "5202";

    /**
     * 业务招待费-招待应酬费(部门/职员)
     */
    String RECEIVE_COMMON = "5504-005";
    
    /**
     * 营业费用-买赠费用(单位/部门/职员/纳税实体)
     */
    String RECEIVE_COMMON1 = "5504-48";

    /**
     * 银行手续费-部门(部门/职员)
     */
    String HAND_BANK_DEPARTMENT = "5504-017";

    /**
     * 银行手续费-个人(部门/职员)
     */
    String HAND_BANK_PERSON = "5504-017";

    /**
     * 营业费用-运输费(部门/职员)
     */
    String TRAN_FEE = "5505-05";
    
    /**
     * 营业费用-运输费(部门/职员)
     */
    String TRAN_FEE1 = "5504-05";

    /**
     * 营业外支出(无)
     */
    String OTHER_PAY = "5601";

    /**
     * 本年利润(无)
     */
    String YEAR_PROFIT = "3131";

    /**
     * 未分配利润（无）
     */
    String NO_PROFIT = "3141-12";

    /**
     * 其他应收款_备用金(部门/职员)
     */
    String OTHER_RECEIVE_BORROW = "1133-01";
    
    /**
     * 资金往来手续费
     */
    String HAND_BANK_FINA = "5506-07";
    
    /**
     * 主营业务收入_折扣(部门/职员)
     */
    String MAIN_RECEIVE_REBATE = "5104";
    
    /**
     * 报废 - 销售系 产品报废损失
     */
    String DROP0 = "5504-30";
    
    /**
     * 报废 - 职能系 产品报废损失
     */
    String DROP1 = "5505-30";
    
    /**
     * 报废 - 存货破损扣款
     */
    String DROP2 = "1133-06";
    
    /**
     * 报废 - 存货破损扣款/产品报废损失
     */
    String DROP3 = "1133-06/5504-30";
    
    /**
     * 合成尾差
     */
    String COMPOSEDIFF = "5505-33";
    
    /**
     * 销售费用_业务员开票税金
     */
    String SAIL_INVOICEINS_FEE = "5504-37";
    
    /**
     * 主营业务税金及附加
     */
    String MAIN_INVOICEINS = "5402";
    
    /**
     * 销售费用(部门/职员)
     */
    String SAIL_CHARGE = "5504-25";
    
    /**
     * 其它应付款-财务待处理
     */
    String OTHERPAY_TRANSFER = "2181-55";
    
    /**
     * 样品毛利准备
     */
    String SWATCH_GROSS = "1142";
    
    /**
     * 营业费用-中收
     */
    String SALE_FEE_MID = "5504-47";
    
    /**
     * 预提费用
     */
    String PRE_FEE = "2191";
}
