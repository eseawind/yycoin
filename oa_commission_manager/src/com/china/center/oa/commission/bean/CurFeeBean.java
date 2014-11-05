package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_CURFEE")
public class CurFeeBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private double money = 0.0d;
    
    public CurFeeBean()
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
    
    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CurFeeBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(" )");
        
        return retValue.toString();
    }
}
