package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.FinanceFeeStatsDetailBean;

@Entity(inherit=true)
public class FinanceFeeStatsDetailVO extends FinanceFeeStatsDetailBean 
{
    @Relationship(relationField="stafferId")
    private String stafferName = "";
    
    public FinanceFeeStatsDetailVO()
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
        .append("FinanceFeeStatsDetailVO ( ")
        .append(super.toString())
        .append(TAB)
        .append("stafferName = ")
        .append(this.stafferName)
        .append(" )");
        
        return retValue.toString();
    }
}
