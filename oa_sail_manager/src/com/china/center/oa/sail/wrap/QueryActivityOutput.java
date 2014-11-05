package com.china.center.oa.sail.wrap;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class QueryActivityOutput implements Serializable
{
	private String activityId = "";
	
	private String activityName = "";
	
	private String description = "";
	
	List<Wrap> products = null;
	
	public QueryActivityOutput()
	{
		
	}

	public String getActivityId()
	{
		return activityId;
	}

	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Wrap> getProducts()
	{
		return products;
	}

	public void setProducts(List<Wrap> products)
	{
		this.products = products;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName()
	{
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName)
	{
		this.activityName = activityName;
	}
}
