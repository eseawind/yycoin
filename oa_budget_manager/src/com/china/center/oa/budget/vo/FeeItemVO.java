/**
 * File Name: FeeItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.budget.bean.FeeItemBean;


/**
 * FeeItemVO
 * 
 * @author ZHUZHU
 * @version 2011-7-9
 * @see FeeItemVO
 * @since 3.0
 */
@Entity(inherit = true)
public class FeeItemVO extends FeeItemBean
{
    @Relationship(relationField = "taxId")
    private String taxName = "";

    @Relationship(relationField = "taxId", tagField = "code")
    private String taxCode = "";

    @Relationship(relationField = "taxId2")
    private String taxName2 = "";

    @Relationship(relationField = "taxId2", tagField = "code")
    private String taxCode2 = "";

    /**
     * default constructor
     */
    public FeeItemVO()
    {
    }

    /**
     * @return the taxName
     */
    public String getTaxName()
    {
        return taxName;
    }

    /**
     * @param taxName
     *            the taxName to set
     */
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode()
    {
        return taxCode;
    }

    /**
     * @param taxCode
     *            the taxCode to set
     */
    public void setTaxCode(String taxCode)
    {
        this.taxCode = taxCode;
    }

    /**
     * @return the taxName2
     */
    public String getTaxName2()
    {
        return taxName2;
    }

    /**
     * @param taxName2
     *            the taxName2 to set
     */
    public void setTaxName2(String taxName2)
    {
        this.taxName2 = taxName2;
    }

    /**
     * @return the taxCode2
     */
    public String getTaxCode2()
    {
        return taxCode2;
    }

    /**
     * @param taxCode2
     *            the taxCode2 to set
     */
    public void setTaxCode2(String taxCode2)
    {
        this.taxCode2 = taxCode2;
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
            .append("FeeItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("taxName = ")
            .append(this.taxName)
            .append(TAB)
            .append("taxCode = ")
            .append(this.taxCode)
            .append(TAB)
            .append("taxName2 = ")
            .append(this.taxName2)
            .append(TAB)
            .append("taxCode2 = ")
            .append(this.taxCode2)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
