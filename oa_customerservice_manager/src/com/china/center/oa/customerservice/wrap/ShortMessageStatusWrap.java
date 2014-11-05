package com.china.center.oa.customerservice.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShortMessageStatusWrap implements Serializable
{
	private String msgId = "";
	
	private String mobile = "";
	
	private int result = 0;
	
	private String ret = "";

	/**
	 * 
	 */
	public ShortMessageStatusWrap()
	{
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
