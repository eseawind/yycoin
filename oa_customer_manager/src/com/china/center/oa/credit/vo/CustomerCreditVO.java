/**
 * File Name: CustomerCreditVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.credit.vs.CustomerCreditBean;


/**
 * CustomerCreditVO
 * 
 * @author ZHUZHU
 * @version 2009-11-8
 * @see CustomerCreditVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CustomerCreditVO extends CustomerCreditBean
{
    @Relationship(relationField = "cid")
    private String cname = "";

    @Relationship(relationField = "itemId")
    private String itemName = "";

    @Relationship(relationField = "pitemId")
    private String pitemName = "";

    @Relationship(relationField = "valueId")
    private String valueName = "";

    /**
     * default constructor
     */
    public CustomerCreditVO()
    {
    }

    /**
     * @return the itemName
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @param itemName
     *            the itemName to set
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    /**
     * @return the pitemName
     */
    public String getPitemName()
    {
        return pitemName;
    }

    /**
     * @param pitemName
     *            the pitemName to set
     */
    public void setPitemName(String pitemName)
    {
        this.pitemName = pitemName;
    }

    /**
     * @return the valueName
     */
    public String getValueName()
    {
        return valueName;
    }

    /**
     * @param valueName
     *            the valueName to set
     */
    public void setValueName(String valueName)
    {
        this.valueName = valueName;
    }

    /**
     * @return the cname
     */
    public String getCname()
    {
        return cname;
    }

    /**
     * @param cname
     *            the cname to set
     */
    public void setCname(String cname)
    {
        this.cname = cname;
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

        retValue.append("CustomerCreditVO ( ").append(super.toString()).append(tab).append("cname = ").append(
            this.cname).append(tab).append("itemName = ").append(this.itemName).append(tab).append("pitemName = ").append(
            this.pitemName).append(tab).append("valueName = ").append(this.valueName).append(tab).append(" )");

        return retValue.toString();
    }
}
