package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;

@Entity
@Table(name="T_SUBPRODUCT_APPLY")
public class ProductSubApplyBean implements Serializable {

    @Id(autoIncrement=true)
    private String id = "";
    
    /**
     * 关联ID
     */
    @FK  
    private String refId = "";
    
    /**
     * 子产品ID
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String subProductId = "";
    
    /**
     * 子产品数量
     */
    private int subProductAmount = 0;

    public ProductSubApplyBean()
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

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public int getSubProductAmount() {
        return subProductAmount;
    }

    public void setSubProductAmount(int subProductAmount) {
        this.subProductAmount = subProductAmount;
    }
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductSubApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("subProductId = ")
            .append(this.subProductId)
            .append(TAB)
            .append("subProductAmount = ")
            .append(this.subProductAmount)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
