package com.china.center.oa.customerservice.constant;

import com.china.center.jdbc.annotation.Defined;

public interface CustomerServiceConstant
{
	/** 回访 */
	@Defined(key = "feedbackType", value = "回访")
	int FEEDBACK_TYPE_VISIT = 1;
	
	/** 对账 */
	@Defined(key = "feedbackType", value = "对帐")
	int FEEDBACK_TYPE_CHECK = 2;
	
	/** 异常处理*/
	@Defined(key = "feedbackType", value = "异常处理")
	int FEEDBACK_TYPE_EXCEPTION_PROCESS = 3;
	
	/** 异常回访*/
	@Defined(key = "feedbackType", value = "异常回访")
	int FEEDBACK_TYPE_EXCEPTION_VISIT = 4;

	/** 异常对账*/
	@Defined(key = "feedbackType", value = "异常对账")
	int FEEDBACK_TYPE_EXCEPTION_CHECK = 5;
	
	/** 待分配*/
	@Defined(key = "feedbackStatus", value = "待分配")
	int FEEDBACK_STATUS_DISTRIBUTE = 1;
	
	/** 待接受*/
	@Defined(key = "feedbackStatus", value = "待接受")
	int FEEDBACK_STATUS_ACCEPT = 2;
	
	/** 处理中*/
	@Defined(key = "feedbackStatus", value = "处理中")
	int FEEDBACK_STATUS_PROCESSING = 3;
	
	/** 异常 待处理中*/
	@Defined(key = "feedbackStatus", value = "异常待处理中")
	int FEEDBACK_STATUS_EXCEPTION_PROCESSING = 4;
	
	/** 已完成*/
	@Defined(key = "feedbackStatus", value = "已完成")
	int FEEDBACK_STATUS_OK = 99;
	
	/** 无异常 */
	@Defined(key = "exceptionStatus", value = "无异常")
	int FEEDBACK_EXCEPTION_STATUS_DEFAULT = 0;
	
	/** 完成 */
	@Defined(key = "exceptionStatus", value = "完成")
	int FEEDBACK_EXCEPTION_STATUS_OK = 1;
	
	/** 处理中 */
	@Defined(key = "exceptionStatus", value = "处理中")
	int FEEDBACK_EXCEPTION_STATUS_INWAY = 2;
	
	/** 成功联系*/
	@Defined(key = "hasConnect", value = "是")
	int FEEDBACK_CONNECT_YES = 1;
	
	/** 未联系上*/
	@Defined(key = "hasConnect", value = "否")
	int FEEDBACK_CONNECT_NO = 2;

	/** 是否收货 */
	@Defined(key = "receive", value = "已收")
	int FEEDBACK_RECEIVE_YES = 1;
	
	/** 是否收货 */
	@Defined(key = "receive", value = "未收")
	int FEEDBACK_RECEIVE_NO = 2;
	
	/** 收货有异常*/
	@Defined(key = "receiveException", value = "有")
	int FEEDBACK_RECEIVE_EXCEPTION_YES = 1;
	
	/** 收货无异常*/
	@Defined(key = "receiveException", value = "无")
	int FEEDBACK_RECEIVE_EXCEPTION_NO = 2;
	
	/** 承诺回复对账*/
	@Defined(key = "promiseReplyCheck", value = "是")
	int FEEDBACK_PROMISE_REPLYCHECK_YES = 1;
	
	/** 不承诺回复对账*/
	@Defined(key = "promiseReplyCheck", value = "否")
	int FEEDBACK_PROMISE_REPLYCHECK_NO = 2;
	
	/** 收到确认传真*/
	@Defined(key = "confirmFax", value = "是")
	int FEEDBACK_CONFIRMFAX_YES = 1;
	
	/** 未收到确认传真*/
	@Defined(key = "confirmFax", value = "否")
	int FEEDBACK_CONFIRMFAX_NO = 2;
	
	/** 符合对账结果 */
	//@Defined(key = "checkResult", value = "已收对账函")
	//int FEEDBACK_CHECKRESULT_YES = 1;
	
	/** 不符合对账结果 */
	//@Defined(key = "checkResult", value = "对账一致")
	//int FEEDBACK_CHECKRESULT_NO = 2;
	
	@Defined(key = "checkResult", value = "对账异常")
	int FEEDBACK_CHECKRESULT_EXCEPTION = 3;
	
	@Defined(key = "checkResult", value = "已回款")
	int FEEDBACK_CHECKRESULT_HASPAY = 4;
	
	@Defined(key = "checkResult", value = "已退货")
	int FEEDBACK_CHECKRESULT_HASRET = 5;
	
	@Defined(key = "checkResult", value = "对账一致，已收对账函原件")
	int FEEDBACK_CHECKRESULT_YES1 = 6;
	
	@Defined(key = "checkResult", value = "对账一致，已收对账函传真件")
	int FEEDBACK_CHECKRESULT_YES2 = 7;
	
	@Defined(key = "checkResult", value = "对账期结束未回对账函")
	int FEEDBACK_CHECKRESULT_END_NOTRECEIVE = 8;
	
	/** 保存*/
	@Defined(key = "feedbackPstatus", value = "初始")
	int FEEDBACK_STATUS_INIT = 99;
	
	/** 保存*/
	@Defined(key = "feedbackPstatus", value = "保存")
	int FEEDBACK_STATUS_SAVE = 0;
	
	/** 提交*/
	@Defined(key = "feedbackPstatus", value = "提交")
	int FEEDBACK_STATUS_SUBMIT = 1;
	
	/** 传真已发*/
	@Defined(key = "feedbackPstatus", value = "传真已发")
	int FEEDBACK_STATUS_SENDFAX = 2;
	
	/** 传真已收*/
	@Defined(key = "feedbackPstatus", value = "传真已收")
	int FEEDBACK_STATUS_RECEIVEFAX = 3;
	
	@Defined(key = "checkProcess", value = "联络中")
	int FEEDBACK_CHECKPROCESS_CONNECTION = 1;
	
	@Defined(key = "checkProcess", value = "对账中")
	int FEEDBACK_CHECKPROCESS_CHECKING = 2;
	
	@Defined(key = "checkProcess", value = "对账完成")
	int FEEDBACK_CHECKPROCESS_FINISH = 3;
}
