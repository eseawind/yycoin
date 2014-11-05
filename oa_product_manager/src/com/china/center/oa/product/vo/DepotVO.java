/**
 * File Name: DepotVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-2-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.DepotBean;


/**
 * DepotVO
 * 
 * @author ZHUZHU
 * @version 2012-2-26
 * @see DepotVO
 * @since 1.0
 */
@Entity(inherit = true)
public class DepotVO extends DepotBean
{
    @Relationship(relationField = "industryId")
    private String industryName = "";
    
    @Relationship(relationField = "industryId2")
    private String industry2Name = "";

    /**
     * default constructor
     */
    public DepotVO()
    {
    }

    /**
     * @return the industryName
     */
    public String getIndustryName()
    {
        return industryName;
    }

    /**
     * @param industryName
     *            the industryName to set
     */
    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    
    
    public String getIndustry2Name() {
        return industry2Name;
    }

    public void setIndustry2Name(String industry2Name) {
        this.industry2Name = industry2Name;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("DepotVO ( ").append(super.toString()).append(TAB).append("industryName = ").append(
            this.industryName).append(TAB).append("industry2Name = ").append(
                    this.industry2Name).append(TAB).append(" )");

        return retValue.toString();
    }
}
