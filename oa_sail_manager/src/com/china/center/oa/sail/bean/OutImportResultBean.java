package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_out_result")
public class OutImportResultBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 中信单号
	 */
	private String citicNo = "";
	
	/**
	 * OA单号
	 */
	private String OANo = "";
	
	/**
	 * 处理结果
	 */
	private String  processResult = "";   
	
	private int citicAmount = 0;
	
	private double citicMoney = 0.0d;
	
	private int OAamount = 0;
	
	private double OAmoney = 0.0d;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCiticNo()
	{
		return citicNo;
	}

	public void setCiticNo(String citicNo)
	{
		this.citicNo = citicNo;
	}

	public String getOANo()
	{
		return OANo;
	}

	public void setOANo(String oANo)
	{
		OANo = oANo;
	}

	public String getProcessResult()
	{
		return processResult;
	}

	public void setProcessResult(String processResult)
	{
		this.processResult = processResult;
	}

	public int getCiticAmount()
	{
		return citicAmount;
	}

	public void setCiticAmount(int citicAmount)
	{
		this.citicAmount = citicAmount;
	}

	public double getCiticMoney()
	{
		return citicMoney;
	}

	public void setCiticMoney(double citicMoney)
	{
		this.citicMoney = citicMoney;
	}

	public int getOAamount()
	{
		return OAamount;
	}

	public void setOAamount(int oAamount)
	{
		OAamount = oAamount;
	}

	public double getOAmoney()
	{
		return OAmoney;
	}

	public void setOAmoney(double oAmoney)
	{
		OAmoney = oAmoney;
	}
}
