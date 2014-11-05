package com.china.center.oa.customerservice.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShortMessageReplyWrap implements Serializable
{
	private String mobile = "";
	
	private String time = "";
	
	private String content = "";

	/**
	 * 
	 */
	public ShortMessageReplyWrap()
	{
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
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
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
}
