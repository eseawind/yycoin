package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_COMMISSION_MONTH")
public class CommissionMonthBean extends AbstractBean
{
    @Id
    private String id = "";
    
    private String beginTime = "";
    
    private String endTime = "";
    
    private String logTime = "";
    
    public CommissionMonthBean()
    {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CommissionMonthBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("logTime = ")
        .append(this.logTime)
        .append(TAB)
        .append("beginTime = ")
        .append(this.beginTime)
        .append(TAB)
        .append("endTime = ")
        .append(this.endTime)        
        .append(" )");
        
        return retValue.toString();
    }
    
}
