package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_goldSilverPrice")
public class GoldSilverPriceBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private double gold = 0.0d;
	
	private double silver = 0.0d;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public double getGold()
	{
		return gold;
	}

	public void setGold(double gold)
	{
		this.gold = gold;
	}

	public double getSilver()
	{
		return silver;
	}

	public void setSilver(double silver)
	{
		this.silver = silver;
	}
	
}
