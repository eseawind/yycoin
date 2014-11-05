package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


public abstract class AbstractBean implements Serializable 
{

    /**
     * 月份
     */
    @FK
    private String month = "";
    
    /**
     * 业务员
     */
    @Join(tagClass=StafferBean.class, type=JoinType.LEFT)
    private String stafferId = "";

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    }
    
    public AbstractBean()
    {
        
    }
    
    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("AbstractBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("month = ")
        .append(this.month)        
        .append(TAB)
        .append("stafferId = ")
        .append(this.stafferId)
        .append(" )");
        
        return retValue.toString();
    }
    
}
