package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_SHORTMESSAGE")
public class ShortMessageBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 批量发短信ID(上行时与id 一样）
	 */
	private String batchId = "";
	
	/**
	 * 由短信提供商返回，失败时，则无
	 * 与 mobile 组合唯一
	 */
	private String msgId = "";
	
	/**
	 * 短信类型， 系统配置 232 t_center_enumdefine
	 */
	private int smType = 0;
	
	/**
	 * 手机（多人接收时，发送时以,分隔，最多100个手机号，拆开存储为多行）
	 */
	private String mobile = "";
	
	/**
	 * 短信内容(长度 60)
	 */
	private String content = "";
	
	/**
	 * 发送类型 0：单条；1：导入；2:批量选取, 3:其它
	 */
	private int stype = 0;
	
	/**
	 * 批量选取客户或职员
	 */
	private String custAndStaff = "";
	
	/**
	 * 1：下行； 2：上行
	 */
	private int smode = 1;
	
	/**
	 * 时间
	 */
	private String logTime = "";
	
	/**
	 * 发送短信操作人
	 */
	private String stafferName = "";
	
	/**
	 * 1:提交成功；-1：提交失败; 3:发送成功；4：发送失败
	 */
	private int result = 0;
	
	/**
	 * 失败原因
	 */
	private int ret = 100;
	
	private String description = "";
	
	/**
	 * 
	 */
	public ShortMessageBean()
	{
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId()
	{
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}

	/**
	 * @return the smType
	 */
	public int getSmType()
	{
		return smType;
	}

	/**
	 * @param smType the smType to set
	 */
	public void setSmType(int smType)
	{
		this.smType = smType;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile()
	{
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the stype
	 */
	public int getStype()
	{
		return stype;
	}

	/**
	 * @param stype the stype to set
	 */
	public void setStype(int stype)
	{
		this.stype = stype;
	}

	/**
	 * @return the smode
	 */
	public int getSmode()
	{
		return smode;
	}

	/**
	 * @param smode the smode to set
	 */
	public void setSmode(int smode)
	{
		this.smode = smode;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	/**
	 * @return the result
	 */
	public int getResult()
	{
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(int result)
	{
		this.result = result;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return the ret
	 */
	public int getRet()
	{
		return ret;
	}

	/**
	 * @param ret the ret to set
	 */
	public void setRet(int ret)
	{
		this.ret = ret;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * @return the custAndStaff
	 */
	public String getCustAndStaff()
	{
		return custAndStaff;
	}

	/**
	 * @param custAndStaff the custAndStaff to set
	 */
	public void setCustAndStaff(String custAndStaff)
	{
		this.custAndStaff = custAndStaff;
	}

	public String toString()
	{
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("ShortMessageBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("batchId = ")
        .append(this.batchId)
        .append(TAB)
        .append("msgId = ")
        .append(this.msgId)
        .append(TAB)
        .append("smType = ")
        .append(this.smType)
        .append(TAB)
        .append("mobile = ")
        .append(this.mobile)
        .append(TAB)
        .append("content = ")
        .append(this.content)
        .append(TAB)
        .append("stype = ")
        .append(this.stype)
        .append(TAB)
        .append("smode = ")
        .append(this.smode)
        .append(TAB)
        .append("logTime = ")
        .append(this.logTime)
        .append(TAB)
        .append("stafferName = ")
        .append(this.stafferName)
        .append(TAB)
        .append("result = ")
        .append(this.result)
        .append(TAB)
        .append("ret = ")
        .append(this.ret)
        .append(TAB)
        .append("description = ")
        .append(this.description)
        .append(" )");
        
        return retValue.toString();
    }
}
