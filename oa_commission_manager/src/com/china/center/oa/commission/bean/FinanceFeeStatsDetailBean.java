package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_FINANCE_FEE_DETAIL")
public class FinanceFeeStatsDetailBean extends AbstractBean 
{

    @Id(autoIncrement=true)
    private String id = "";    
    
    private String outId = "";
    
    private double outValue = 0.0d;
    
    private double outBackValue = 0.0d;
    
    private double otherBuyValue = 0.0d;
    
    private double money = 0.0d;
    
    private int type = 0;
    
    private double ratio = 0.0d;
    
    private int used = 0;
    
    public FinanceFeeStatsDetailBean(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    };
    
    
    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public double getOutValue() {
        return outValue;
    }

    public void setOutValue(double outValue) {
        this.outValue = outValue;
    }

    public double getOutBackValue() {
        return outBackValue;
    }

    public void setOutBackValue(double outBackValue) {
        this.outBackValue = outBackValue;
    }

    public double getOtherBuyValue() {
        return otherBuyValue;
    }

    public void setOtherBuyValue(double otherBuyValue) {
        this.otherBuyValue = otherBuyValue;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("FinanceFeeStatsDetailBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("outId = ")
        .append(this.outId)
        .append(TAB)
        .append("outValue = ")
        .append(this.outValue)
        .append(TAB)
        .append("outBackValue = ")
        .append(this.outBackValue)
        .append(TAB)
        .append("otherBuyValue = ")
        .append(this.otherBuyValue)
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("type = ")
        .append(this.type)
        .append(TAB)
        .append("used = ")
        .append(this.used)        
        .append(" )");
        
        return retValue.toString();
    } 
       
}
