/**
 * File Name: CityVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.CityBean;


/**
 * CityVO
 * 
 * @author ZHUZHU
 * @version 2011-8-28
 * @see CityVO
 * @since 3.0
 */
@Entity(inherit = true)
public class CityVO extends CityBean
{
    @Relationship(relationField = "parentId")
    private String parentName = "";

    /**
     * default constructor
     */
    public CityVO()
    {
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("CityVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("parentName = ")
            .append(this.parentName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
