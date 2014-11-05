package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.BadDropAssessDetailBean;

@Entity(inherit=true)
public class BadDropAssessDetailVO extends BadDropAssessDetailBean 
{
    @Relationship(relationField="stafferId")
    private String stafferName = "";
    
    public BadDropAssessDetailVO()
    {
        
    }

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
        .append("CurFeeDetailVO ( ")
        .append(super.toString())
        .append(TAB)
        .append("stafferName = ")
        .append(this.stafferName)
        .append(" )");
        
        return retValue.toString();
    }
}
