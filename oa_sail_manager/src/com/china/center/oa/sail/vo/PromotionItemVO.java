package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.PromotionItemBean;

/**
 * 
 * 请输入功能描述
 *
 * @author fangliwen 2012-8-16
 */
@Entity(inherit = true)
public class PromotionItemVO extends PromotionItemBean {
    
    @Relationship(relationField = "productId")
    private String productName = "";

    /**
     * 
     */
    public PromotionItemVO()
    {
        
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
           
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("PromotionItemVO ( ")
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
