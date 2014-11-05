/**
 * File Name: FinanceTurnVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tax.bean.FinanceTurnBean;


/**
 * FinanceTurnVO
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceTurnVO
 * @since 3.0
 */
@Entity(inherit = true)
public class FinanceTurnVO extends FinanceTurnBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * default constructor
     */
    public FinanceTurnVO()
    {
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
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

        retValue.append("FinanceTurnVO ( ").append(super.toString()).append(TAB).append(
            "stafferName = ").append(this.stafferName).append(TAB).append(" )");

        return retValue.toString();
    }

}
