/**
 * File Name: TaxVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;


/**
 * TaxVO
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see TaxVO
 * @since 1.0
 */
@Entity(inherit = true)
public class TaxVO extends TaxBean
{
    @Relationship(relationField = "ptype")
    private String ptypeName = "";

    @Relationship(relationField = "refId")
    private String refName = "";

    @Relationship(relationField = "parentId")
    private String parentName = "";

    @Ignore
    private String parentAllShow = "";

    @Ignore
    private String other = "";

    /**
     * default constructor
     */
    public TaxVO()
    {
    }

    public String getOther()
    {
        StringBuilder sb = new StringBuilder();

        if (getUnit() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("单位").append("/");
        }

        if (getDepartment() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("部门").append("/");
        }

        if (getStaffer() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("职员").append("/");
        }

        if (getProduct() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("产品").append("/");
        }

        if (getDepot() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("仓库").append("/");
        }

        if (getDuty() == TaxConstanst.TAX_CHECK_YES)
        {
            sb.append("纳税实体").append("/");
        }

        if (sb.length() > 0)
        {
            sb.delete(sb.length() - 1, sb.length());
        }

        other = sb.toString();

        return other;
    }

    /**
     * @return the ptypeName
     */
    public String getPtypeName()
    {
        return ptypeName;
    }

    /**
     * @param ptypeName
     *            the ptypeName to set
     */
    public void setPtypeName(String ptypeName)
    {
        this.ptypeName = ptypeName;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(String other)
    {
        this.other = other;
    }

    /**
     * @return the refName
     */
    public String getRefName()
    {
        return refName;
    }

    /**
     * @param refName
     *            the refName to set
     */
    public void setRefName(String refName)
    {
        this.refName = refName;
    }

    /**
     * @return the parentName
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * @param parentName
     *            the parentName to set
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * @return the parentAllShow
     */
    public String getParentAllShow()
    {
        return parentAllShow;
    }

    /**
     * @param parentAllShow
     *            the parentAllShow to set
     */
    public void setParentAllShow(String parentAllShow)
    {
        this.parentAllShow = parentAllShow;
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
            .append("TaxVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("ptypeName = ")
            .append(this.ptypeName)
            .append(TAB)
            .append("refName = ")
            .append(this.refName)
            .append(TAB)
            .append("parentName = ")
            .append(this.parentName)
            .append(TAB)
            .append("parentAllShow = ")
            .append(this.parentAllShow)
            .append(TAB)
            .append("other = ")
            .append(this.other)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
