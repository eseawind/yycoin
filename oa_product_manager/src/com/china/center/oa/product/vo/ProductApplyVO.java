package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductApplyBean;

@Entity(inherit=true)
public class ProductApplyVO extends ProductApplyBean 
{

    /**
     * 事业部名称，用逗号间隔
     */
    @Ignore    
    private String industryIdsName = "";
    
    /**
     * 经理名称
     */
    @Relationship(relationField = "productManagerId")    
    private String productManagerName = "";
    
    /**
     * 申请者
     */
    @Relationship(relationField = "oprId")
    private String oprName = "";

    public String getProductManagerName() {
        return productManagerName;
    }

    public void setProductManagerName(String productManagerName) {
        this.productManagerName = productManagerName;
    }

    public String getOprName() {
        return oprName;
    }

    public void setOprName(String oprName) {
        this.oprName = oprName;
    }
    
    public String getIndustryIdsName() {
        return industryIdsName;
    }

    public void setIndustryIdsName(String industryIdsName) {
        this.industryIdsName = industryIdsName;
    }

    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productManagerName = ")
            .append(this.productManagerName)
            .append(TAB)
            .append("oprName = ")
            .append(this.oprName)
            .append(TAB)
            .append("industryIdsName = ")
            .append(this.industryIdsName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
    
}
