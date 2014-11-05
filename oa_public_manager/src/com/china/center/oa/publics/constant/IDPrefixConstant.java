/**
 * File Name: IDPrefixConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;

/**
 * IDPrefixConstant
 * 
 * @author ZHUZHU
 * @version 2011-7-3
 * @see IDPrefixConstant
 * @since 3.0
 */
public interface IDPrefixConstant
{
    /**
     * BILL的ID的前缀
     */
    String ID_BILL_PREFIX = "SF";

    /**
     * 凭证ID前缀
     */
    String ID_FINANCE_PREFIX = "PZ";

    /**
     * 预算ID前缀
     */
    String ID_BUDGET_PREFIX = "YS";

    /**
     * 回款ID前缀
     */
    String ID_PAYMENT_PREFIX = "HK";

    /**
     * 回款单申请(申请类的都是U开头的)
     */
    String ID_PAYMENTAPPLY_PREFIX = "UH";

    /**
     * 采购付款的ID前缀
     */
    String ID_STOCKPAYMENTAPPLY_PREFIX = "UC";

    /**
     * 报销申请的ID
     */
    String ID_TCP_PREFIX = "UT";
    
    /**
     * 提成申请的ID
     */
    String ID_COMMIS_PREFIX = "TC";
    
    /**
     * 任务申请的ID
     */
    String ID_TASK_PREFIX = "RW";
    /**
     * 任务申请的编码
     */
    String CODE_TASK_PREFIX = "TC";
    
    /**
     * 合同申请的ID
     */
    String ID_AGREEMENT_PREFIX = "HT";
    
    /**
     * 项目申请的ID
     */
    String ID_PROJECT_PREFIX = "XM";
    
    /**
     * 合同产品行项目ID
     */
    String ID_AGREEMENT_PL_PREFIX = "PL";
    
    /**
     * 项目产品行项目ID
     */
    String ID_PROJECT_PL_PREFIX = "XM";
    
    /**
     * 项目人员行项目ID
     */
    String ID_PROJECT_RY_PREFIX = "RY";
    
    /**
     * 合同付款行项目ID
     */
    String ID_AGREEMENT_FK_PREFIX = "FK";
    
    /**
     * 合同交付行项目ID
     */
    String ID_AGREEMENT_JF_PREFIX = "JF";
    
    /**
     * 合同开票行项目ID
     */
    String ID_AGREEMENT_KP_PREFIX = "KP";
    
    
    /**
     * 合同申请的编码
     */
    String CODE_AGREEMENT_PREFIX = "HC";
    
    /**
     * 项目申请的编码
     */
    String CODE_PROJECT_PREFIX = "PC";
    
    /**
     * 返利
     */
    String ID_REBATE_PRIFIX = "FL";

    /**
     * 配送
     */
    String ID_DISTRIBUTION_PRIFIX = "PS";
}
