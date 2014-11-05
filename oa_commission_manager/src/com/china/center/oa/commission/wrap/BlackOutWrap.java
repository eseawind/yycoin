package com.china.center.oa.commission.wrap;

import java.io.Serializable;

public class BlackOutWrap implements Serializable 
{

    private String outId = "";
    
    private double money = 0.0d;
    
    private int days = 0;
    
    private String customerName = "";
    
    public BlackOutWrap()
    {
        
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
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
    
}
