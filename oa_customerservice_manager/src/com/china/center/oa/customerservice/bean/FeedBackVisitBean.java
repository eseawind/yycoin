package com.china.center.oa.customerservice.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;

@Entity
@Table(name = "T_CENTER_FEEDBACK_VISIT")
public class FeedBackVisitBean implements Serializable
{
	@Id
	private String id = "";
	
	@FK
	private String taskId = "";
	
	/** 任务类型 */
	private int taskType = 0;
	
	/** 回访者 */
	@Html(title = "回访者", must = true)
	private String caller = "";
	
	/** 回访时间 */
	@Html(title = "回访时间", must = true, type = Element.DATETIME)
	private String callTime = "";
	
	/** 客户 */
	@Html(title = "客户", must = true)
	private String customerName = "";
	
	@Html(title = "业务员", must = true)
	private String stafferName = "";
	
	/** 异常处理者 */
	private String exceptionProcesser = "";
	
	@Html(name = "exceptionProcesserName",title = "异常处理者", readonly = true)
	private String exceptionProcesserName = "";
	
	/** 异常状态  0 完成 1 处理中(等待处理中) */
	@Html(title = "异常状态", type = Element.SELECT)
	private int exceptionStatus = 0;
	
	/** 是否联系成功 */
	@Html(title = "是否联系成功", must = true, type = Element.SELECT)
	private int ifHasContact;
	
	/** 联系人 */
	@Html(title = "联系人", type = Element.INPUT, maxLength = 100)
	private String contact = "";
	
	/** 联系电话 */
	@Html(title = "联系电话", type = Element.INPUT, maxLength = 100)
	private String contactPhone = "";
	
	/** 计划回复 时间*/
	@Html(title = "计划回复时间", type = Element.DATE)
	private String planReplyDate = "";
	
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
	@Html(title = "描述", type = Element.TEXTAREA, maxLength = 300)
	private String description = "";
	
	@Html(title = "状态", must = true, type = Element.SELECT)
	private int status = 0;
	
	@Html(title = "回访时间")
	private String logTime = "";

	/** 描述 */
	@Html(title = "异常信息相关人", type = Element.TEXTAREA, maxLength = 300)
	private String exceptRef = "";
	
	@Ignore
	private List<FeedBackVisitItemBean> itemList = null;
	
	/**
	 * 
	 */
	public FeedBackVisitBean()
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

	public int getTaskType()
	{
		return taskType;
	}

	public void setTaskType(int taskType)
	{
		this.taskType = taskType;
	}

	public String getCaller()
	{
		return caller;
	}

	public void setCaller(String caller)
	{
		this.caller = caller;
	}

	public String getCallTime()
	{
		return callTime;
	}

	public void setCallTime(String callTime)
	{
		this.callTime = callTime;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getExceptionProcesser()
	{
		return exceptionProcesser;
	}

	public void setExceptionProcesser(String exceptionProcesser)
	{
		this.exceptionProcesser = exceptionProcesser;
	}

	public int getIfHasContact()
	{
		return ifHasContact;
	}

	public void setIfHasContact(int ifHasContact)
	{
		this.ifHasContact = ifHasContact;
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

	public String getPlanReplyDate()
	{
		return planReplyDate;
	}

	public void setPlanReplyDate(String planReplyDate)
	{
		this.planReplyDate = planReplyDate;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getExceptionStatus()
	{
		return exceptionStatus;
	}

	public void setExceptionStatus(int exceptionStatus)
	{
		this.exceptionStatus = exceptionStatus;
	}

	public List<FeedBackVisitItemBean> getItemList()
	{
		return itemList;
	}

	public void setItemList(List<FeedBackVisitItemBean> itemList)
	{
		this.itemList = itemList;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getExceptionProcesserName()
	{
		return exceptionProcesserName;
	}

	public void setExceptionProcesserName(String exceptionProcesserName)
	{
		this.exceptionProcesserName = exceptionProcesserName;
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

	public String getExceptRef()
	{
		return exceptRef;
	}

	public void setExceptRef(String exceptRef)
	{
		this.exceptRef = exceptRef;
	}
}
