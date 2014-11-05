package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity(inherit=true)
@Table(name="T_CENTER_FINANCE_FEE_STATS")
public class FinanceFeeStatsBean extends AbstractBean
{

    @Id(autoIncrement=true)
    private String id = "";
    
    private double money = 0.0d;
    
    @Unique(dependFields = {"month","stafferId"})
    private int type = 0;   
    
    public FinanceFeeStatsBean(){}

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

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("FinanceFeeStatsBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("type = ")
        .append(this.type)        
        .append(" )");
        
        return retValue.toString();
    } 
    
    
}
