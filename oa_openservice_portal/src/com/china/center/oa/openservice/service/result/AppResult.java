package com.china.center.oa.openservice.service.result;

import java.io.Serializable;

public class AppResult implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int retCode = 0;
	
	private String retMsg = "";
	
	private Object obj = null;
	
	public AppResult()
	{
		
	}

	public int getRetCode()
	{
		return retCode;
	}

	public void setRetCode(int retCode)
	{
		this.retCode = retCode;
	}

	public String getRetMsg()
	{
		return retMsg;
	}

	public void setRetMsg(String retMsg)
	{
		this.retMsg = retMsg;
	}

	public Object getObj()
	{
		return obj;
	}

	public void setObj(Object obj)
	{
		this.obj = obj;
	}
	
	public void setError(String msg)
	{
		this.retCode = -1;
		
		this.retMsg = msg;
	}
	
	public void setSuccess(String msg)
	{
		this.retCode = 0;
		
		this.retMsg = msg;
	}
	
	public void setSuccessAndObj(String msg, Object obj)
	{
		this.retCode = 0;
		
		this.retMsg = msg;
		
		this.obj = obj;
	}
}
