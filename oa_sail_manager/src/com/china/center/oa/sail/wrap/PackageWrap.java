/**
 * 
 */
package com.china.center.oa.sail.wrap;

import java.io.Serializable;

/**
 * @author smart
 *
 */
@SuppressWarnings("serial")
public class PackageWrap implements Serializable
{
	private String outId = "";
	
	private String description = "";

	public PackageWrap()
	{
	}

	/**
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
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
}
