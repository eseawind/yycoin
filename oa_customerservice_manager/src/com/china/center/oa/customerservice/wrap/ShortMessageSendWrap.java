package com.china.center.oa.customerservice.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShortMessageSendWrap implements Serializable
{
	private String msgId = "";

	/**
	 * 
	 */
	public ShortMessageSendWrap()
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
}
