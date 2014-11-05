package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.sail.constanst.SailConstant;

@Entity
@Table(name = "T_CENTER_PROMOTIONITEM")
public class PromotionItemBean implements Serializable {

    @Id(autoIncrement=true)
    private String id = "";
    
    /**
     * 关联原单ID
     */
    @FK
    private String refId = "";
    
    /**
     * 0:产品(0) 1:产品类型(1) 2：销售类型(2)
     */
    private int type = SailConstant.SAILCONFIG_ONLYPRODUCT;
    
    /**
     * 自有，经销，定制
     */
    private int sailType = -1;
    
    /**
     * 产品类型
     *
     * 1:纸币是z，2:金银币是j，3:古币是g，4:邮票是y，0:其他类型是q
     */     
    private int productType = -1;
    
    /**
     * 产品
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = ""; 

    /**
     * 
     */
    public PromotionItemBean()
    {
        
    }
    
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
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSailType() {
        return sailType;
    }

    public void setSailType(int sailType) {
        this.sailType = sailType;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("PromotionItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append(TAB)
            .append("type = ")
            .append(this.type)            
            .append(TAB)
            .append("sailType = ")
            .append(this.sailType)
            .append(TAB)
            .append("productType = ")
            .append(this.productType)
            .append(TAB)
            .append("productId = ")
            .append(this.productId) 
            .append(TAB)
            .append(" )");            
            ;
        
        return retValue.toString();
    }
}
