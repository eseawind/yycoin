/**
 * File Name: StafferTransferVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-10-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.vs.StafferTransferBean;


/**
 * StafferTransferVO
 * 
 * @author ZHUZHU
 * @version 2011-10-23
 * @see StafferTransferVO
 * @since 3.0
 */
@Entity(inherit = true)
public class StafferTransferVO extends StafferTransferBean
{
    @Relationship(relationField = "destId")
    private String destName = "";

    @Relationship(relationField = "srcId")
    private String srcName = "";

    /**
     * default constructor
     */
    public StafferTransferVO()
    {
    }

    /**
     * @return the destName
     */
    public String getDestName()
    {
        return destName;
    }

    /**
     * @param destName
     *            the destName to set
     */
    public void setDestName(String destName)
    {
        this.destName = destName;
    }

    /**
     * @return the srcName
     */
    public String getSrcName()
    {
        return srcName;
    }

    /**
     * @param srcName
     *            the srcName to set
     */
    public void setSrcName(String srcName)
    {
        this.srcName = srcName;
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

        retValue.append("StafferTransferVO ( ").append(super.toString()).append(TAB).append(
            "destName = ").append(this.destName).append(TAB).append("srcName = ").append(
            this.srcName).append(TAB).append(" )");

        return retValue.toString();
    }

}
