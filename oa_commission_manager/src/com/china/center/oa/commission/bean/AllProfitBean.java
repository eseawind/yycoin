package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity(name="累计到款净利")
@Table(name="T_CENTER_ALLPROFIT")
public class AllProfitBean implements Serializable
{
    @Id(autoIncrement=true)
    private String id = "";
    
    @Unique
    private String stafferId = "";
    
    private double moneys = 0.0d;
    
    private String lastModified = "";
    
    public AllProfitBean()
    {       
    }

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

    public double getMoneys() {
        return moneys;
    }

    public void setMoneys(double moneys) {
        this.moneys = moneys;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
    
    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("AllProfitBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("stafferId = ")
        .append(this.stafferId)
        .append(TAB)
        .append("moneys = ")
        .append(this.moneys)
        .append(TAB)
        .append("lastModified = ")
        .append(this.lastModified)
        .append(" )");
        
        return retValue.toString();
    }
}
