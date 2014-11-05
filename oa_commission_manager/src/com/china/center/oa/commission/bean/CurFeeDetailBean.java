package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_CURFEE_DETAIL")
public class CurFeeDetailBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private String refId = "";
    
    private double money = 0.0d;
    
    private String description = "";
    
    public CurFeeDetailBean()
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
    
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CurFeeDetailBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("refId = ")
        .append(this.refId)       
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("description = ")
        .append(this.description)        
        .append(" )");
        
        return retValue.toString();
    }
}
