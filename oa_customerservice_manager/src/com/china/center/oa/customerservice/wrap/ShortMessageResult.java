package com.china.center.oa.customerservice.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShortMessageResult implements Serializable
{
	private int ret = 0;
	
	private Object obj = null;

	/**
	 * 
	 */
	public ShortMessageResult()
	{
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
	 * @return the obj
	 */
	public Object getObj()
	{
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj)
	{
		this.obj = obj;
	}
}
