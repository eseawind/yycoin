package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;

@Entity
@Table(name = "T_CENTER_BLACKRULE_PRODUCT")
public class BlackRuleProductBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";
    
    /**
     * 关联单号
     */
    @FK    
    private String refId = "";
    
    /**
     * 产品
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = "";
    
    public BlackRuleProductBean(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    };
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BlackRuleProductBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)           
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
