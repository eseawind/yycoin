package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_SMSTATUS")
public class ShortMessageStatusBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String msgId = "";
	
	private String mobile = "";
	
	private int retsult = 0;
	
	private String ret = "";
	
	public ShortMessageStatusBean(){
		
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
	 * @return the retsult
	 */
	public int getRetsult()
	{
		return retsult;
	}

	/**
	 * @param retsult the retsult to set
	 */
	public void setRetsult(int retsult)
	{
		this.retsult = retsult;
	}

	/**
	 * @return the ret
	 */
	public String getRet()
	{
		return ret;
	}

	/**
	 * @param ret the ret to set
	 */
	public void setRet(String ret)
	{
		this.ret = ret;
	}
}
