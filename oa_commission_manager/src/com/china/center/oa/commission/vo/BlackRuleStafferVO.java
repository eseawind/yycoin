package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.BlackRuleStafferBean;

@Entity(inherit=true)
public class BlackRuleStafferVO extends BlackRuleStafferBean 
{
    /**
     * 职员
     */
    @Relationship(relationField = "stafferId")
    private String stafferName = "";
    
    public BlackRuleStafferVO(){}

    public String getStafferName() {
        return stafferName;
    }

    public void setStafferName(String stafferName) {
        this.stafferName = stafferName;
    };
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BlackRuleStafferVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)            
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
