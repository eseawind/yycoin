package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_CURMONEY")
public class CurMoneyBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private double money = 0.0d;
    
    private double profit = 0.0d;
    
    /**
     * 坏账
     */
    private double badDebts = 0.0d;
    
    public CurMoneyBean()
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
        .append("CurMoneyBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
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
