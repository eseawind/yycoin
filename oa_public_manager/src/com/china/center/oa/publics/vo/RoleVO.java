/**
 * File Name: RoleVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.RoleBean;


/**
 * RoleVO
 * 
 * @author zhuzhu
 * @version 2008-11-15
 * @see RoleVO
 * @since 1.0
 */
@Entity(inherit = true)
public class RoleVO extends RoleBean
{
    @Relationship(relationField = "locationId")
    private String locationName = "";

    /**
     * default constructor
     */
    public RoleVO()
    {
    }

    /**
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }
}
