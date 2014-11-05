package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_CURMONEY_DETAIL")
public class CurMoneyDetailBean extends AbstractBean 
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private String outId = "";
    
    private int type = 0 ;
    
    private int outType = 0;
    
    /**
     * 到款总额
     */
    private double money = 0.0d;
    
    /**
     * 毛利
     */
    private double profit = 0.0d;
    
    /**
     * 成本
     */
    private double cost = 0.0d;
    
    /**
     * 折扣
     */
    private double promValue = 0.0d;
    
    private double badDebts = 0.0d;
    
    private String refOutFullId = "";
    
    public CurMoneyDetailBean()
    {
        
    }

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
    
    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOutType() {
        return outType;
    }

    public void setOutType(int outType) {
        this.outType = outType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPromValue() {
        return promValue;
    }

    public void setPromValue(double promValue) {
        this.promValue = promValue;
    }   
    
    public String getRefOutFullId() {
        return refOutFullId;
    }

    public void setRefOutFullId(String refOutFullId) {
        this.refOutFullId = refOutFullId;
    }

    public double getBadDebts() {
        return badDebts;
    }

    public void setBadDebts(double badDebts) {
        this.badDebts = badDebts;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CurMoneyDetailBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("outId = ")
        .append(this.outId)
        .append(TAB)
        .append("type = ")
        .append(this.type)
        .append(TAB)
        .append("outtype = ")
        .append(this.outType)
        .append(TAB)        
        .append("cost = ")
        .append(this.cost)
        .append(TAB)
        .append("promValue = ")
        .append(this.promValue)        
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("profit = ")
        .append(this.profit)        
        .append(" )");
        
        return retValue.toString();
    }
}
