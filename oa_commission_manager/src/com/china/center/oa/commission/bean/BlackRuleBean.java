package com.china.center.oa.commission.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.commission.constant.BlackConstant;

@Entity
@Table(name = "T_CENTER_BLACKRULE")
public class BlackRuleBean implements Serializable 
{
    @Id
    private String id           = "";

    /**
     * 规则名称
     */
    @Html(title = "名称", must = true, type = Element.INPUT, maxLength = 100)
    private String name = "";
    
    /**
     * 类型
     */
    @Html(title = "类型", must = true, type = Element.SELECT)
    private int    type         = BlackConstant.TYPE_BLACK;

    /**
     * 品类，分号间隔
     */
    @Html(title = "品类", must = true, type = Element.INPUT)
    private String    productType  = "";

    /**
     * 事业部，分号间隔
     */
    @Html(title = "事业部", must = true, type = Element.INPUT)
    private String industryId   = "";

    /**
     * 部门，分号间隔
     */
    @Html(title = "部门", must = true, type = Element.INPUT)
    private String departmentId = "";

    /**
     * 销售单，分号间隔
     */    
    @Html(title = "销售单", must = true, type = Element.INPUT)
    private String outId        = "";

    /**
     * 销售开始时间
     */
    @Html(title = "销售开始时间", must = true, type = Element.DATE)
    private String beginOutTime = "";

    /**
     * 销售结束时间
     */
    @Html(title = "销售结束时间", must = true, type = Element.DATE)
    private String endOutTime   = "";

    @Ignore
    private List<BlackRuleProductBean> productList = null;
    
    @Ignore
    private List<BlackRuleStafferBean> stafferList = null;
    
    /**
     * default constructor
     */
    public BlackRuleBean() 
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getBeginOutTime() {
        return beginOutTime;
    }

    public void setBeginOutTime(String beginOutTime) {
        this.beginOutTime = beginOutTime;
    }

    public String getEndOutTime() {
        return endOutTime;
    }

    public void setEndOutTime(String endOutTime) {
        this.endOutTime = endOutTime;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<BlackRuleProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<BlackRuleProductBean> productList) {
        this.productList = productList;
    }

    public List<BlackRuleStafferBean> getStafferList() {
        return stafferList;
    }

    public void setStafferList(List<BlackRuleStafferBean> stafferList) {
        this.stafferList = stafferList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("BlackRuleBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("name = ")
        .append(this.name)        
        .append(TAB)
        .append("type = ")
        .append(this.type)
        .append(TAB)
        .append("productType = ")
        .append(this.productType)
        .append(TAB)
        .append("industryId = ")
        .append(this.industryId)
        .append(TAB)
        .append("departmentId = ")
        .append(this.departmentId)
        .append(TAB)
        .append("outId = ")
        .append(this.outId)
        .append(TAB)
        .append("beginOutTime = ")
        .append(this.beginOutTime)
        .append(TAB)
        .append("endOutTime = ")
        .append(this.endOutTime)
        .append(" )");
        
        return retValue.toString();
    }
}
