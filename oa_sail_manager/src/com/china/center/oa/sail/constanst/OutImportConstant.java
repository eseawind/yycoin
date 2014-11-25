package com.china.center.oa.sail.constanst;

import com.china.center.jdbc.annotation.Defined;

public interface OutImportConstant
{

	/**
	 * 中信银行接口常量
	 */
	String CITIC = "CITIC";
	
	String CITIC_OPERATORID = "10650002";
	
	String CITIC_OPERATORNAME = "蔡彬（中信）";
	
	int CITIC_OUTTYPE = 0 ;
	
	int CITIC_REDAY = 30;
	
	int CITIC_PAYTYPE = 2;
	
	String CITIC_DUTY = "90201008080000000001";
	
	String CITIC_INVOICEID = "90000000000000000001";
	
	String CITIC_DEPOT = "A1201304121109613150";
	
	String CITIC_DEPOTPART = "A1201304121109613151";
	
	String CITIC_DEPOTPARTNAME = "中信银行--南京物流中心_默认仓区";
	
	String CITIC_OWNER = "0";
	
	/**
	 * 浦发
	 */
	String CITIC_DEPOT_PUFA = "A1201204171505676333";
	
	String CITIC_DEPOTPART_PUFA = "A1201204171505676334";
	
	String CITIC_DEPOTPARTNAME_PUFA = "银行--南京物流中心_默认仓区";
	
	/**
	 * 接口状态 - 初始
	 */
	@Defined(key = "citic_status", value = "初始")
	int STATUS_INIT = 0;
	
	/**
	 * 接口状态 - 成功
	 */
	@Defined(key = "citic_status", value = "成功")
	int STATUS_SUCCESSFULL = 2;
	
	/**
	 * 接口状态 - 初始
	 */
	@Defined(key = "logStatus", value = "失败")
	int LOGSTATUS_FAIL = 0;
	
	/**
	 * 接口状态 - 1:进行中
	 */
	@Defined(key = "logStatus", value = "处理中")
	int LOGSTATUS_ING = 1;
	
	/**
	 * 接口状态 - 成功
	 */
	@Defined(key = "logStatus", value = "成功")
	int LOGSTATUS_SUCCESSFULL = 2;
	
	/**
	 * 成功且已预占
	 */
	@Defined(key = "logStatus", value = "成功且已预占")
	int LOGSTATUS_SUCCESSPREUSE = 3;
	
	/**
	 * 发货排序  库存不满足
	 */
	@Defined(key = "citic_enoughStock", value = "否")
	int ENOUGHSTOCK_NO = 0;
	
	/**
	 * 发货排序  库存不满足
	 */
	@Defined(key = "citic_enoughStock", value = "是")
	int ENOUGHSTOCK_YES = 1;
	
	String outTypesArr [] = new String[]{"销售出库","个人领样","零售","委托代销","赠送","客户铺货","巡展领样"};
	
	int outTypeiArr [] = new int []{0,1,2,3,4,5,6}; 
	
	String shipping [] = new String []{"自提","公司","第三方快递","第三方货运","第三方快递+货运","空发"};
	int ishipping [] = new int []{0,1,2,3,4,99};
	
	String expressPay [] = new String []{"业务员支付","公司支付","客户支付"};
	int iexpressPay [] = new int []{1,2,3};
	
	String outPresentTypesArr [] = new String[]{"捆绑销售","员工福利","业务赠送","物料类赠送"};
	
	int outPresentTypeiArr [] = new int []{1,2,3,4}; 
	
	/**
	 * 未预占
	 */
	@Defined(key = "preUse", value = "未预占")
	int PREUSE_NO = 0;
	
	/**
	 * 已预占
	 */
	@Defined(key = "preUse", value = "已预占")
	int PREUSE_YES = 1;
	
	/**
	 * 紫金农商指定仓库、仓区  紫金农商需确定新的仓库。。。
	 */
	String ZJRC_DEPOT = "A1201407241115878114";
	
	String ZJRC_DEPOTPART = "A1201407241115878115";
	
	String ZJRC_DEPOTPARTNAME = "业务一部-紫金农商库_默认仓区";
	
    /**
     * 保存
     */
    @Defined(key = "zjrcOutStatus", value = "保存")
    int STATUS_SAVE = 0;

    /**
     * 提交
     */
    @Defined(key = "zjrcOutStatus", value = "提交")
    int STATUS_SUBMIT = 1;
    
    /**
     * 备货中
     */
    @Defined(key = "zjrcOutStatus", value = "备货中")
    int STATUS_PREPARE = 2;
    
    /**
     * 配送中
     */
    @Defined(key = "zjrcOutStatus", value = "配送中")
    int STATUS_SHIPPING = 3;

    /**
     * 主出纳签收
     * 增加紫金订单状态“主出纳签收”，此状态在流程中置于 “银行签收” 状态前，“配送中” 状态后
     */
    @Defined(key = "zjrcOutStatus", value = "主出纳签收")
    int STATUS_CASHER_RECEIVE = 6;
    
    /**
     * 待银行签收
     */
    @Defined(key = "zjrcOutStatus", value = "银行已签收")
    int STATUS_BANK_RECEIVE = 4;
    
    /**
     * 待客户签收
     */
    @Defined(key = "zjrcOutStatus", value = "客户已签收")
    int STATUS_CUSTOMER_RECEIVE = 5;

    
    /**
     * 结束
     */
    @Defined(key = "zjrcOutStatus", value = "结束")
    int STATUS_CUSTOMER_END = 99;
}
