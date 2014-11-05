package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductVSStafferBean;

@Entity(inherit = true)
public class ProductVSStafferVO extends ProductVSStafferBean 
{

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

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
            .append("ProductVSStafferVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)          
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
    
    
}
