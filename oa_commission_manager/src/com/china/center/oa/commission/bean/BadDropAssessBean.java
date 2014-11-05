package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(name = "报废",inherit=true)
@Table(name = "T_CENTER_ASSESS_BROKEN")
public class BadDropAssessBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = ""; 
    
    private double value = 0.0d;
    
    private int type = 0;
    
    public BadDropAssessBean()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("BadDropAssessBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("value = ")
        .append(this.value)    
        .append(TAB)
        .append("type = ")
        .append(this.type)
        .append(" )");
        
        return retValue.toString();
    }
}
