package com.china.center.oa.product.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * @author ZHUZHU
 */
public interface ProductConstant
{
    /**
     * 正常
     */
    @Defined(key = "productStatus", value = "正常态")
    int STATUS_COMMON = 0;

    /**
     * 锁定
     */
    @Defined(key = "productStatus", value = "锁定态")
    int STATUS_LOCK = 1;

    /**
     * 注销
     */
    @Defined(key = "productStatus", value = "注销态")
    int STATUS_LOGOUT = 2;

    /**
     * 申请
     */
    @Defined(key = "productStatus", value = "申请态")
    int STATUS_APPLY = 3;

    
    /**
     * 结算价引用：管理
     */
    @Defined(key = "sailManagerType", value = "管理")
    int SAIL_MANAGER_TYPE = 0;

    /**
     * 结算价引用：普通
     */
    @Defined(key = "sailManagerType", value = "普通")
    int SAIL_COMMON_TYPE = 1;
    
    /**
     * 自有
     */
    int TEMP_SELF = 0;

    /**
     * 非自有
     */
    int TEMP_PUBLIC = 1;

    /**
     * 正常态
     */
    @Defined(key = "productStatStatus", value = "正常态")
    int STAT_STATUS_COMMON = 0;

    /**
     * 预警态
     */
    @Defined(key = "productStatStatus", value = "预警态")
    int STAT_STATUS_ALERT = 1;

    /**
     * 正常
     */
    @Defined(key = "productOrderStatus", value = "预定")
    int ORDER_STATUS_COMMON = 0;

    /**
     * 结束
     */
    @Defined(key = "productOrderStatus", value = "结束")
    int ORDER_STATUS_END = 1;

    /**
     * 统计周期
     */
    int STAT_DAYS = 15;

    /**
     * 不启用
     */
    @Defined(key = "productStockType", value = "不启用库存模型")
    int STOCKTYPE_NO_USER = 0;

    /**
     * 启用
     */
    @Defined(key = "productStockType", value = "启用库存模型")
    int STOCKTYPE_USER = 1;

    /**
     * 普通产品
     */
    @Defined(key = "productAbstractType", value = "普通产品")
    int ABSTRACT_TYPE_NO = 0;

    /**
     * 虚拟产品
     */
    @Defined(key = "productAbstractType", value = "虚拟产品")
    int ABSTRACT_TYPE_YES = 1;

    /**
     * 产品
     */
    @Defined(key = "productPtype", value = "产品")
    int PTYPE_PRODUCT = 0;

    /**
     * 物料
     */
    @Defined(key = "productPtype", value = "物料")
    int PTYPE_WULIAO = 1;

    /**
     * 普通产品
     */
    @Defined(key = "productCtype", value = "配件产品")
    int CTYPE_NO = 0;

    /**
     * 合成产品
     */
    @Defined(key = "productCtype", value = "合成产品")
    int CTYPE_YES = 1;

    /**
     * 金银章
     */
    @Defined(key = "productType", value = "金银章")
    int PRODUCT_TYPE_OTHER = 0;

    /**
     * 金银币
     */
    @Defined(key = "productType", value = "金银币")
    int PRODUCT_TYPE_PAPER = 1;

    /**
     * 流通币
     */
    @Defined(key = "productType", value = "流通币")
    int PRODUCT_TYPE_METAL = 2;

    /**
     * 旧币
     */
    @Defined(key = "productType", value = "旧币")
    int PRODUCT_TYPE_NUMISMATICS = 3;

    /**
     * 邮票
     */
    @Defined(key = "productType", value = "邮票")
    int PRODUCT_TYPE_STAMP = 4;

    /**
     * 其他
     */
    @Defined(key = "productType", value = "其他")
    int PRODUCT_TYPE_MONCE = 5;

    /**
     * 自有
     */
    @Defined(key = "productSailType", value = "自有")
    int SAILTYPE_SELF = 0;

    /**
     * 经销
     */
    @Defined(key = "productSailType", value = "经销")
    int SAILTYPE_REPLACE = 1;
    
    /**
     * 定制
     */
    @Defined(key = "productSailType", value = "定制")
    int SAILTYPE_CUSTOMER = 2;

    /**
     * 私采
     */
    @Defined(key = "productSailType", value = "私采")
    int SAILTYPE_OTHER = 3;

    /**
     * 管理
     */
    @Defined(key = "manageType", value = "管理")
    int MANAGER_TYPE_MANAGER = 0;

    /**
     * 普通
     */
    @Defined(key = "manageType", value = "普通")
    int MANAGER_TYPE_COMMON = 1;
    
    /**
     * 可以调价
     */
    @Defined(key = "productAjustPrice", value = "可以调价")
    int ADJUSTPRICE_YES = 0;

    /**
     * 不可以
     */
    @Defined(key = "productAjustPrice", value = "不可以调价")
    int ADJUSTPRICE_NO = 1;

    /**
     * 抽检
     */
    @Defined(key = "productCheckType", value = "抽检")
    int CHECKTYPE_SUB = 0;

    /**
     * 全检
     */
    @Defined(key = "productCheckType", value = "全检")
    int CHECKTYPE_ALL = 1;

    /**
     * 出生
     */
    @Defined(key = "productStep", value = "出生")
    String PRODUTC_STEP_NEW = "0";

    @Defined(key = "productStep", value = "成长")
    String PRODUTC_STEP_INCR = "1";

    @Defined(key = "productStep", value = "成熟")
    String PRODUTC_STEP_COMMONR = "2";

    @Defined(key = "productStep", value = "衰退")
    String PRODUTC_STEP_SLOW = "3";

    @Defined(key = "productStep", value = "退市")
    String PRODUTC_STEP_DROP = "4";
    
    /**
     * 呆滞
     */
    @Defined(key = "productStep", value = "呆滞")
    String PRODUTC_STEP_STOP = "5";
    
    @Defined(key = "assembleFlag", value = "金")
    String ASSEMBLEFLAGTYPE_GOLD = "0";
    
    @Defined(key = "assembleFlag", value = "银")
    String ASSEMBLEFLAGTYPE_SILVER = "1";
    
    @Defined(key = "assembleFlag", value = "其它")
    String ASSEMBLEFLAGTYPE_OTHER = "99";
    
    /**
     * 是否随金银价波动 - 否
     */
    @Defined(key = "isWave", value = "否")
    int ISWAVE_NO = 0;
    
    /**
     * 是否随金银价波动 - 金价
     */
    @Defined(key = "isWave", value = "金价")
    int ISWAVE_YES_GODEN = 1;
    
    /**
     * 是否随金银价波动 - 银价
     */
    @Defined(key = "isWave", value = "银价")
    int ISWAVE_YES_SILVER = 2;
    
    /**
     * 通用销售 - 通用产品（写死）- 普通
     */
    String OUT_COMMON_PRODUCTCODE = "191175852";
    
    /**
     * 通用销售 - 通用产品（写死）- 管理
     */
    String OUT_COMMON_MPRODUCTCODE = "191165735";
    
    /**
     * 通用销售 - 通用产品ID（写死）正式时要记得修改页面中的值
     */
    String OUT_COMMON_PRODUCTID = "9775852";
    
    /**
     * 通用销售 - 通用产品ID（写死）正式时要记得修改页面中的值
     */
    String OUT_COMMON_MPRODUCTID = "9865735";
    
    /**
     * 公共库--南京物流中心
     */
    String OUT_COMMON_DEPOT = "99000000000000000002";
    
    /**
     * 南京物流中心-物流中心库(销售可发)
     */
    String OUT_COMMON_DEPOTPART = "1";
    
    /**
     * 旧货
     */
    int PRODUCT_OLDGOOD = 9522152;
    
    /**
     * 非旧货
     */
    int PRODUCT_OLDGOOD_YES = 9522158;
    
    /**
     * 0税率
     */
    int PRODUCT_OLDGOOD_ZERO = 13884386;
    
    /**
     * 结算价配置 1
     */
    @Defined(key = "priceConfigType", value = "结算价配置")
    int PRICECONFIG_SETTLE = 1;
    
    /**
     * 销售价配置 0
     */
    @Defined(key = "priceConfigType", value = "销售价配置")
    int PRICECONFIG_SAIL = 0;
    
    /**
     * 产品分拆 - 库存
     */
    @Defined(key = "decomposeProductType", value = "库存")
    int DECOMPOSE_PRODUCT_STORAGE = 0;

    /**
     * 产品分拆 - 费用
     */
    @Defined(key = "decomposeProductType", value = "费用")
    int DECOMPOSE_PRODUCT_FEE = 1;
}
