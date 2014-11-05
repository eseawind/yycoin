package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductSubApplyBean;

@Entity(inherit=true)
public class ProductSubApplyVO extends ProductSubApplyBean 
{

    @Relationship(relationField = "subProductId")
    private String subProductName = "";

    public String getSubProductName() {
        return subProductName;
    }

    public void setSubProductName(String subProductName) {
        this.subProductName = subProductName;
    }
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductSubApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("subProductName = ")
            .append(this.subProductName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
