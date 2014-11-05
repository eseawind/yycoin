package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.commission.bean.BlackRuleBean;

@Entity(inherit=true)
public class BlackRuleVO extends BlackRuleBean 
{
    /**
     * 品类名称
     */
    @Ignore
    private String productTypeName = "";
    
    /**
     * 事业部名
     */
    @Ignore
    private String industryName = "";
    
    /**
     * 部门名称
     */
    @Ignore
    private String departmentName = "";
    
    /**
     * 品名
     */
    @Ignore
    private String productName = "";
    
    /**
     * 职员
     */
    @Ignore
    private String stafferName = "";
    
    public BlackRuleVO()
    {
        
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
            .append("BlackRuleVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productTypeName = ")
            .append(this.productTypeName)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)            
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
