package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity(name = "统计领样金额")
@Table(name = "T_CENTER_SWATCHSTATS")
public class SwatchStatsBean implements Serializable
{
	@Id()
	private String id = "";
	
	@Unique
	private String stafferId = "";
	
	private double totalMoney = 0.0d;
	
	@Ignore
	private List<SwatchStatsItemBean> itemList = null;
	
	public SwatchStatsBean()
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

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public double getTotalMoney()
	{
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney)
	{
		this.totalMoney = totalMoney;
	}

	public List<SwatchStatsItemBean> getItemList()
	{
		return itemList;
	}

	public void setItemList(List<SwatchStatsItemBean> itemList)
	{
		this.itemList = itemList;
	}
}
