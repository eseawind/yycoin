/**
 * File Name: CreditItemThrVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.customer.constant.CreditConstant;


/**
 * CreditItemThrVO
 * 
 * @author ZHUZHU
 * @version 2009-10-30
 * @see CreditItemThrVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CreditItemThrVO extends CreditItemThrBean
{
    @Relationship(relationField = "pid")
    private String pName = "";

    @Relationship(relationField = "pid", tagField = "face")
    private int face = CreditConstant.CREDIT_ITEM_FACE_OBVERSE;

    /**
     * default constructor
     */
    public CreditItemThrVO()
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
     * @return the face
     */
    public int getFace()
    {
        return face;
    }

    /**
     * @param face
     *            the face to set
     */
    public void setFace(int face)
    {
        this.face = face;
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

        retValue.append("CreditItemThrVO ( ").append(super.toString()).append(tab).append("pName = ").append(this.pName).append(
            tab).append("face = ").append(this.face).append(tab).append(" )");

        return retValue.toString();
    }
}
