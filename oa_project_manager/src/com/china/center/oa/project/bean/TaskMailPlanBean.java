package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_TASKPLAN")
public class TaskMailPlanBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 任务Id
	 */
	@FK
	private String taskId = "";
	
	/**
	 * 邮件标题
	 */
	private String title = "";
	
	/**
	 * 邮件内容
	 */
	private String content = "";
	
	/**
	 * 邮件接收人
	 */
	private String tos = "";
	
	/**
	 * 计划发送时间
	 */
	private String planTime = "";
	
	/**
	 * 发送状态
	 */
	private int status = 0;
	
	/**
	 * 类型
	 */
	private int type = 0;
	
	public TaskMailPlanBean()
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getTos()
	{
		return tos;
	}

	public void setTos(String tos)
	{
		this.tos = tos;
	}

	public String getPlanTime()
	{
		return planTime;
	}

	public void setPlanTime(String planTime)
	{
		this.planTime = planTime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
