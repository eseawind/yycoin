/**
 * File Name: StorageConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * StorageConstant
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageConstant
 * @since 1.0
 */
public interface StorageConstant
{
    /**
     * 初始化
     */
    @Defined(key = "storageType", value = "初始化")
    int OPR_STORAGE_INIT = 0;

    /**
     * 修改
     */
    @Defined(key = "storageType", value = "修改")
    int OPR_STORAGE_UPDATE = 1;

    /**
     * 销售出库
     */
    @Defined(key = "storageType", value = "销售出库")
    int OPR_STORAGE_OUTBILL = 2;

    /**
     * 采购入库
     */
    @Defined(key = "storageType", value = "采购入库")
    int OPR_STORAGE_OUTBILLIN = 3;

    /**
     * 移动
     */
    @Defined(key = "storageType", value = "储位移动")
    int OPR_STORAGE_MOVE = 4;

    /**
     * 合成/分解
     */
    @Defined(key = "storageType", value = "合成/分解")
    int OPR_STORAGE_COMPOSE = 5;

    /**
     * 移动
     */
    @Defined(key = "storageType", value = "仓区移动")
    int OPR_DDEPOTPART_MOVE = 6;

    /**
     * 产品调价
     */
    @Defined(key = "storageType", value = "产品调价")
    int OPR_DDEPOTPART_CHANGE = 7;

    /**
     * 领样退库
     */
    @Defined(key = "storageType", value = "领样退库")
    int OPR_STORAGE_SWATCH = 8;

    /**
     * 调价回滚
     */
    @Defined(key = "storageType", value = "调价回滚")
    int OPR_DDEPOTPART_CHANGE_ROLLBACK = 7;

    /**
     * 名下转移
     */
    @Defined(key = "storageType", value = "名下转移")
    int OPR_DDEPOTPART_APPLY_MOVE = 8;

    /**
     * 产品调拨
     */
    @Defined(key = "storageType", value = "产品调拨")
    int OPR_STORAGE_REDEPLOY = 9;

    /**
     * 代销退货
     */
    @Defined(key = "storageType", value = "代销退货")
    int OPR_STORAGE_BALANCE = 10;

    /**
     * 调拨/报废/纠错/其他
     */
    @Defined(key = "storageType", value = "调拨/报废/纠错/其他")
    int OPR_STORAGE_INOTHER = 11;

    /**
     * 领样退库
     */
    @Defined(key = "storageType", value = "领样退库")
    int OPR_STORAGE_SWATH = 12;

    /**
     * 销售退库
     */
    @Defined(key = "storageType", value = "销售退库")
    int OPR_STORAGE_OUTBACK = 13;

    /**
     * 调拨回滚
     */
    @Defined(key = "storageType", value = "调拨回滚")
    int OPR_STORAGE_REDEPLOY_ROLLBACK = 14;
    
    /**
     * 产品拆分
     */
    @Defined(key = "storageType", value = "产品拆分")
    int OPR_STORAGE_DECOMPOSE = 15;
    
    /**
     * 金银料出入库
     */
    @Defined(key = "storageType", value = "金银料出入库")
    int OPR_STORAGE_GSOUT = 16;

    /**
     * 公共的职员(特殊处理)
     */
    @Defined(key = "pubStaffer", value = "私有")
    String PUBLIC_STAFFER = "0";

    /**
     * 申请
     */
    @Defined(key = "storageApplyStatus", value = "申请")
    int STORAGEAPPLY_STATUS_SUBMIT = 0;

    /**
     * 通过
     */
    @Defined(key = "storageApplyStatus", value = "通过")
    int STORAGEAPPLY_STATUS_PASS = 1;
    
    /**
     * 金银料出库
     */
    @Defined(key = "storageGSType", value = "出库")
    int STORAGEAPPLY_GS_OUT = 0;

    /**
     * 金银料入库
     */
    @Defined(key = "storageGSType", value = "入库")
    int STORAGEAPPLY_GS_IN = 1;
    
    /**
     * 保存
     */
    @Defined(key = "gsOutStatus", value = "保存")
    int GSOUT_STATUS_SAVE = 0;

    /**
     * 待事业部经理审批
     */
    @Defined(key = "gsOutStatus", value = "待事业部经理审批")
    int GSOUT_STATUS_SUBMIT = 1;

    /**
     * 驳回
     */
    @Defined(key = "gsOutStatus", value = "驳回")
    int GSOUT_STATUS_REJECT = 2;

    /**
     * 单据结束
     */
    @Defined(key = "gsOutStatus", value = "结束")
    int GSOUT_STATUS_SEC_PASS = 4;

    /**
     * 待库管审批
     */
    @Defined(key = "gsOutStatus", value = "待库管审批")
    int GSOUT_STATUS_FLOW_PASS = 7;
    
    /**
     * 金料
     */
    String GOLD_PRODUCT = "7631391";
    
    /**
     * 银料
     */
    String SILVER_PRODUCT = "7631392";

    /**
     * 库存数大于0
     */
    @Defined(key = "storageAmountType", value = "大于0")
    int STORAGE_AMOUNT_POSITIVE = 0;

    /**
     * 库存数小于或等于0
     */
    @Defined(key = "storageAmountType1", value = "小于或等于0")
    int STORAGE_AMOUNT_NEGATIVE = 0;
}
