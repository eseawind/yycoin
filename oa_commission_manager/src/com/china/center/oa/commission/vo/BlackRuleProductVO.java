package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.BlackRuleProductBean;

@Entity(inherit=true)
public class BlackRuleProductVO extends BlackRuleProductBean 
{

    /**
     * 产品品名
     */
    @Relationship(relationField = "productId")
    private String productName = "";

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BlackRuleProductVO(){};
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BlackRuleProductVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)            
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
