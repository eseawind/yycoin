package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;

@SuppressWarnings("serial")
public abstract class AbstractBlackOutBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";
    
    /**
     * 关联单号
     */
    @FK
    private String refId = "";
    
    /**
     * 类型 0:超期应收对应的订单 1：最长超期天数的订单 2：所有应收对应的订单
     */
    private int type = 0;
    
    /**
     * 单号明细
     */
    private String outId = "";
    
    private double money = 0.0d;
    
    private int days = 0;
    
    private String customerName = "";
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    };
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("BlackOutBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("refId = ")
        .append(this.refId)
        .append(TAB)
        .append("outId = ")
        .append(this.outId)
        .append(" )");
        
        return retValue.toString();
    }
}
