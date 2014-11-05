package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_center_vs_preInvoiceOut")
public class PreInvoiceVSOutBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String parentId = "";
	
	/**
	 * 关联的销售单
	 */
	@FK(index = AnoConstant.FK_FIRST)
	private String outId = "";
	
	/**
	 * 委托代销单
	 */
	private String outBalanceId = "";
	
	
	/**
	 * 销售单金额
	 */
	private double money = 0.d;
	
	/**
	 * 开票金额 小 于等于 销售单金额
	 */
	private double invoiceMoney = 0.0d;
	
	private double mayInvoiceMoney = 0.0d;
	
	public PreInvoiceVSOutBean()
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

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public double getInvoiceMoney()
	{
		return invoiceMoney;
	}

	public void setInvoiceMoney(double invoiceMoney)
	{
		this.invoiceMoney = invoiceMoney;
	}

	public double getMayInvoiceMoney()
	{
		return mayInvoiceMoney;
	}

	public void setMayInvoiceMoney(double mayInvoiceMoney)
	{
		this.mayInvoiceMoney = mayInvoiceMoney;
	}

	public String getOutBalanceId()
	{
		return outBalanceId;
	}

	public void setOutBalanceId(String outBalanceId)
	{
		this.outBalanceId = outBalanceId;
	}
}
