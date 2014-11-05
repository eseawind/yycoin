/**
 * File Name: FeeItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.tax.bean.TaxBean;


/**
 * FeeItemBean
 * 
 * @author ZHUZHU
 * @version 2008-12-7
 * @see FeeItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_FEEITEM")
public class FeeItemBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "预算项", must = true, maxLength = 100)
    private String name = "";

    /**
     * 关联的费用科目(报销系)
     */
    @Join(tagClass = TaxBean.class, alias = "TaxBean1", type = JoinType.LEFT)
    @Html(title = "费用科目(销售)", name = "taxName", must = true, maxLength = 100)
    private String taxId = "";

    /**
     * 关联的费用科目(职能/管理系)
     */
    @Join(tagClass = TaxBean.class, alias = "TaxBean2", type = JoinType.LEFT)
    @Html(title = "费用科目(职能/管理)", name = "taxName2", must = true, maxLength = 100)
    private String taxId2 = "";

    /**
     * 预算项属性
     */
    @Html(title = "类型", must = true, type = Element.SELECT)
    private int type = BudgetConstant.FEEITEM_TYPE_REGULARFEE;

    public FeeItemBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the taxId
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the taxId2
     */
    public String getTaxId2()
    {
        return taxId2;
    }

    /**
     * @param taxId2
     *            the taxId2 to set
     */
    public void setTaxId2(String taxId2)
    {
        this.taxId2 = taxId2;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("FeeItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("taxId = ")
            .append(this.taxId)
            .append(TAB)
            .append("taxId2 = ")
            .append(this.taxId2)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
