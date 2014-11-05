package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name="T_CENTER_EXTERNAL_STAFFERYEAR")
public class ExternalStafferYearBean implements Serializable
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private String stafferId = "";
    
    private String stafferName = "";
    
    private String years = "";
    
    public ExternalStafferYearBean(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    };
    
    public String getStafferName() {
        return stafferName;
    }

    public void setStafferName(String stafferName) {
        this.stafferName = stafferName;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("ExteranlStafferYearBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("stafferId = ")
        .append(this.stafferId)
        .append(TAB)
        .append("years = ")
        .append(this.years)        
        .append(" )");
        
        return retValue.toString();
    } 
}
