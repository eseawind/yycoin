/**
 * File Name: CreditItemSecVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.credit.bean.CreditItemSecBean;


/**
 * CreditItemSecVO
 * 
 * @author ZHUZHU
 * @version 2009-10-27
 * @see CreditItemSecVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CreditItemSecVO extends CreditItemSecBean
{
    @Relationship(relationField = "pid")
    private String pName = "";

    @Relationship(relationField = "pid", tagField = "per")
    private double parentPer = 0.0d;

    @Relationship(relationField = "pid", tagField = "type")
    private int parentType = 0;

    @Ignore
    private String point = "0.0";

    /**
     * default constructor
     */
    public CreditItemSecVO()
    {
    }

    /**
     * @return the pName
     */
    public String getPName()
    {
        return pName;
    }

    /**
     * @param name
     *            the pName to set
     */
    public void setPName(String name)
    {
        pName = name;
    }

    /**
     * @return the point
     */
    public String getPoint()
    {
        return point;
    }

    /**
     * @param point
     *            the point to set
     */
    public void setPoint(String point)
    {
        this.point = point;
    }

    /**
     * @return the parentPer
     */
    public double getParentPer()
    {
        return parentPer;
    }

    /**
     * @param parentPer
     *            the parentPer to set
     */
    public void setParentPer(double parentPer)
    {
        this.parentPer = parentPer;
    }

    /**
     * @return the parentType
     */
    public int getParentType()
    {
        return parentType;
    }

    /**
     * @param parentType
     *            the parentType to set
     */
    public void setParentType(int parentType)
    {
        this.parentType = parentType;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("CreditItemSecVO ( ").append(super.toString()).append(tab).append("pName = ").append(this.pName).append(
            tab).append("parentPer = ").append(this.parentPer).append(tab).append("parentType = ").append(
            this.parentType).append(tab).append("point = ").append(this.point).append(tab).append(" )");

        return retValue.toString();
    }
}
