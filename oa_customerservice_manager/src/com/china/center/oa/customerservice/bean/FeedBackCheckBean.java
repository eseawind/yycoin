package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;

@Entity
@Table(name = "T_CENTER_FEEDBACK_CHECK")
public class FeedBackCheckBean implements Serializable
{
	@Id
	private String  id = "";
	
	@FK
	private String taskId = "";
	
	@Html(title = "客户", must = true)
	private String customerName = "";
	
	@Html(title = "业务员", must = true)
	private String stafferName = "";
	
	@Html(title = "统计开始")
	private String statsStar = "";
	
	@Html(title = "统计结束")
	private String statsEnd = "";
	
	@Html(title = "事业部")
	private String industryIdName = "";
	
	/** 统计时间 */
	private String statTime = "";
	
	/** 回访者 */
	@Html(title = "回访者", must = true)
	private String caller = "";
	
	/** 联系人 */
	@Html(title = "联系人", must = true, maxLength = 100)
	private String contact = "";
	
	/** 联系电话 */
	@Html(title = "联系电话", must = true, maxLength = 100)
	private String contactPhone = "";
	
	/** 是否承诺回复对账 */
	@Html(title = "是否承诺回复对账", must = true, type = Element.SELECT)
	private int ifPromiseReplyCheck;
	
	/** 计划回复时间 */
	@Html(title = "计划回复时间", type = Element.DATE)
	private String planReplyDate = "";
	
	/** 是否收到确认传真 */
	@Html(title = "是否收到确认传真", must = true, type = Element.SELECT)
	private int ifReceiveConfirmFax;

	/** 是否发出确认传真 */
	@Html(title = "是否发出确认传真", must = true, type = Element.SELECT)
	private int ifSendConfirmFax;
	
	/** 对账结果 */
	@Html(title = "对账结果", must = true, type = Element.SELECT)
	private int checkResult;
	
	@Html(title = "描述", type = Element.TEXTAREA, maxLength = 300)
	private String description = "";

	@Html(title = "状态", must = true, type = Element.SELECT)
	private int status = 0;
	
	@Html(title = "回访时间")
	private String logTime = "";
	
	@Html(title = "对账进度", must = true, type = Element.SELECT)
	private int checkProcess = 0;
	
	/** 异常处理者 */
	private String exceptionProcesser = "";
	
	@Html(name = "exceptionProcesserName",title = "异常处理者", readonly = true)
	private String exceptionProcesserName = "";
	
	/** 异常状态  0 完成 1 处理中(等待处理中) */
	@Html(title = "异常状态", type = Element.SELECT)
	private int exceptionStatus = 0;
	
	/** 实际异常原因   229*/
	@Html(title = "实际异常原因", must=true, type = Element.SELECT)
	private int actualExceptionReason;
	
	/** 解决办法  230*/
	@Html(title = "解决办法", must = true, type = Element.SELECT)
	private int resolve;
	
	/** 处理描述 */
	@Html(title = "处理描述", must = true, type = Element.TEXTAREA, maxLength = 300)
	private String resolveText = "";
	
	/** 描述 */
	@Html(title = "异常信息相关人", type = Element.TEXTAREA, maxLength = 300)
	private String exceptRef = "";
	
	/**
	 * 
	 */
	public FeedBackCheckBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getStatTime()
	{
		return statTime;
	}

	public void setStatTime(String statTime)
	{
		this.statTime = statTime;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public String getContactPhone()
	{
		return contactPhone;
	}

	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone;
	}

	public int getIfPromiseReplyCheck()
	{
		return ifPromiseReplyCheck;
	}

	public void setIfPromiseReplyCheck(int ifPromiseReplyCheck)
	{
		this.ifPromiseReplyCheck = ifPromiseReplyCheck;
	}

	public String getPlanReplyDate()
	{
		return planReplyDate;
	}

	public void setPlanReplyDate(String planReplyDate)
	{
		this.planReplyDate = planReplyDate;
	}

	public int getIfReceiveConfirmFax()
	{
		return ifReceiveConfirmFax;
	}

	public void setIfReceiveConfirmFax(int ifReceiveConfirmFax)
	{
		this.ifReceiveConfirmFax = ifReceiveConfirmFax;
	}

	public int getCheckResult()
	{
		return checkResult;
	}

	public void setCheckResult(int checkResult)
	{
		this.checkResult = checkResult;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getCaller()
	{
		return caller;
	}

	public void setCaller(String caller)
	{
		this.caller = caller;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	public String getStatsStar()
	{
		return statsStar;
	}

	public void setStatsStar(String statsStar)
	{
		this.statsStar = statsStar;
	}

	public String getStatsEnd()
	{
		return statsEnd;
	}

	public void setStatsEnd(String statsEnd)
	{
		this.statsEnd = statsEnd;
	}

	public String getIndustryIdName()
	{
		return industryIdName;
	}

	public void setIndustryIdName(String industryIdName)
	{
		this.industryIdName = industryIdName;
	}

	public int getIfSendConfirmFax()
	{
		return ifSendConfirmFax;
	}

	public void setIfSendConfirmFax(int ifSendConfirmFax)
	{
		this.ifSendConfirmFax = ifSendConfirmFax;
	}

	public int getCheckProcess()
	{
		return checkProcess;
	}

	public void setCheckProcess(int checkProcess)
	{
		this.checkProcess = checkProcess;
	}

	public String getExceptionProcesser()
	{
		return exceptionProcesser;
	}

	public void setExceptionProcesser(String exceptionProcesser)
	{
		this.exceptionProcesser = exceptionProcesser;
	}

	public String getExceptionProcesserName()
	{
		return exceptionProcesserName;
	}

	public void setExceptionProcesserName(String exceptionProcesserName)
	{
		this.exceptionProcesserName = exceptionProcesserName;
	}

	public int getExceptionStatus()
	{
		return exceptionStatus;
	}

	public void setExceptionStatus(int exceptionStatus)
	{
		this.exceptionStatus = exceptionStatus;
	}

	public int getActualExceptionReason()
	{
		return actualExceptionReason;
	}

	public void setActualExceptionReason(int actualExceptionReason)
	{
		this.actualExceptionReason = actualExceptionReason;
	}

	public int getResolve()
	{
		return resolve;
	}

	public void setResolve(int resolve)
	{
		this.resolve = resolve;
	}

	public String getResolveText()
	{
		return resolveText;
	}

	public void setResolveText(String resolveText)
	{
		this.resolveText = resolveText;
	}

	public String getExceptRef()
	{
		return exceptRef;
	}

	public void setExceptRef(String exceptRef)
	{
		this.exceptRef = exceptRef;
	}
}
