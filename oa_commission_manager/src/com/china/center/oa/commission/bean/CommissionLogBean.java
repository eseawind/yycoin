package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity
@Table(name="T_CENTER_COMMISSION_LOG")
public class CommissionLogBean implements Serializable
{
    @Id(autoIncrement=true)
    private String id = "";
    
    @Unique(dependFields = {"status"})
    @FK   
    private String month = "";
    
    private int status = 0;
    
    private String opr = "";
    
    private String lastModified = "";
    
    public CommissionLogBean()
    {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
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
        .append("CommissionLogBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("month = ")
        .append(this.month)
        .append(TAB)
        .append("status = ")
        .append(this.status)
        .append(TAB)
        .append("opr = ")
        .append(this.opr)        
        .append(TAB)
        .append("lastModified = ")
        .append(this.lastModified)
        .append(" )");
        
        return retValue.toString();
        
    }
}
