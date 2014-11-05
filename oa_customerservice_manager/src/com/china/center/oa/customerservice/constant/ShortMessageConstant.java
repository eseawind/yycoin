package com.china.center.oa.customerservice.constant;

import com.china.center.jdbc.annotation.Defined;

public interface ShortMessageConstant
{
	@Defined(key = "sendStatus", value = "成功")
	int SEND_SUCCESS = 1;
	
	@Defined(key = "sendStatus", value = "帐户格式不正确")
	int SEND_FORMAT_ERROR = 0;
	
	@Defined(key = "sendStatus", value = "服务器拒绝")
	int SEND_SERVER_DENIED = -1;
	
	@Defined(key = "sendStatus", value = "密钥不正确")
	int SEND_AUTHKEY_ERROR = -2;
	
	@Defined(key = "sendStatus", value = "密钥已锁定")
	int SEND_AUTHKEY_LOCKED = -3;
	
	@Defined(key = "sendStatus", value = "参数不正确")
	int SEND_PARAM_ERROR = -4;
	
	@Defined(key = "sendStatus", value = "无此帐户")
	int SEND_ACCOUNT_NOTEXIST = -5;
	
	@Defined(key = "sendStatus", value = "帐户已锁定或已过期")
	int SEND_ACCOUNT_LOCKED = -6;
	
	@Defined(key = "sendStatus", value = "帐户未开启接口发送")
	int SEND_ACCOUNT_NOTOPEN = -7;
	
	@Defined(key = "sendStatus", value = "不可使用该通道组")	
	int SEND_CHANNEL_ERROR = -8;
	
	@Defined(key = "sendStatus", value = "帐户余额不足")
	int SEND_ACCOUNT_BALANCE_NOTENOUGH = -9;
	
	@Defined(key = "sendStatus", value = "内部错误")
	int SEND_INTERNAL_ERROR = -10;
	
	@Defined(key = "sendStatus", value = "扣费失败")
	int SEND_OPERATION_FAIL = -11;
	
	/**
	 * 单条发送
	 */
	@Defined(key = "sendStype", value = "单条短信")
	int MODE_SINGLE = 0;
	
	/**
	 * 导入
	 */
	@Defined(key = "sendStype", value = "批量导入短信")
	int MODE_IMPORT = 1;
	
	/**
	 * 一条信息多个手机号
	 */
	@Defined(key = "sendStype", value = "批量客户或职员短信")
	int MODE_MULTI = 2;
	
	@Defined(key = "checkSMStatus", value = "短消息转发成功")
	String STATUS_DELIVRD = "DELIVRD";
	
	@Defined(key = "checkSMStatus", value = "短消息超过有效期")
	String STATUS_EXPIRED = "EXPIRED";
	
	@Defined(key = "checkSMStatus", value = "短消息已经被删除")
	String STATUS_DELETED = "DELETED";
	
	@Defined(key = "checkSMStatus", value = "短消息是不可达的")
	String STATUS_UNDELIV = "UNDELIV";
	
	@Defined(key = "checkSMStatus", value = "短消息在等待发送中")
	String STATUS_ACCEPTD = "ACCEPTD";
	
	@Defined(key = "checkSMStatus", value = "未知短消息状态")
	String STATUS_UNKNOWN = "UNKNOWN";
	
	@Defined(key = "checkSMStatus", value = "短消息被短信中心拒绝")
	String STATUS_REJECTD = "REJECTD";
	
	@Defined(key = "checkSMStatus", value = "目的号码是黑名单号码")
	String STATUS_DTBLACK = "DTBLACK";
	
	@Defined(key = "checkSMStatus", value = "发送内容被过滤")
	String STATUS_DTWORDS = "DTWORDS";
	
	@Defined(key = "checkSMStatus", value = "发送失败原因未明")
	String STATUS_DTFAILD = "DTFAILD";
	
	@Defined(key = "checkSMStatus", value = "运营商系统忙状态未知")
	String STATUS_ERRBUSY = "ERRBUSY";

	//1:提交成功；-1：提交失败; 3:发送成功；4：发送失败
	@Defined(key = "smsResult", value = "提交成功")
	int SMS_RESULT_SUBMIT_SUCCESS = 1;
	
	@Defined(key = "smsResult", value = "提交失败")
	int SMS_RESULT_SUBMIT_FAIL = -1;
	
	@Defined(key = "smsResult", value = "发送成功")
	int SMS_RESULT_SEND_SUCCESS = 2;
	
	@Defined(key = "smsResult", value = "发送失败")
	int SMS_RESULT_SEND_FAIL = 3;
	
	@Defined(key = "sendMode", value = "下行")
	int SMS_DOWN = 1;
	
	/**
	 * 一条信息多个手机号
	 */
	@Defined(key = "sendMode", value = "上行")
	int SMS_UP = 2;
}
