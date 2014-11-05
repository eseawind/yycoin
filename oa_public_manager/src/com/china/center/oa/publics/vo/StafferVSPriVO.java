/**
 * File Name: StafferVSPriVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.vs.StafferVSPriBean;


/**
 * StafferVSPriVO
 * 
 * @author ZHUZHU
 * @version 2010-8-7
 * @see StafferVSPriVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StafferVSPriVO extends StafferVSPriBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "principalshipId")
    private String principalshipName = "";

    /**
     * default constructor
     */
    public StafferVSPriVO()
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
     * @return the principalshipName
     */
    public String getPrincipalshipName()
    {
        return principalshipName;
    }

    /**
     * @param principalshipName
     *            the principalshipName to set
     */
    public void setPrincipalshipName(String principalshipName)
    {
        this.principalshipName = principalshipName;
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
            .append("StafferVSPriVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("principalshipName = ")
            .append(this.principalshipName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
