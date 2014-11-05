/**
 * File Name: LocationVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.LocationBean;


/**
 * LocationVO
 * 
 * @author ZHUZHU
 * @version 2008-11-8
 * @see LocationVO
 * @since 1.0
 */
@Entity(inherit = true)
public class LocationVO extends LocationBean
{
    @Ignore
    private List<LocationVSCityVO> cityVOs = null;

    @Relationship(relationField = "parentId")
    private String parentName = "";

    /**
     * default constructor
     */
    public LocationVO()
    {
    }

    /**
     * @return the cityVOs
     */
    public List<LocationVSCityVO> getCityVOs()
    {
        return cityVOs;
    }

    /**
     * @param cityVOs
     *            the cityVOs to set
     */
    public void setCityVOs(List<LocationVSCityVO> cityVOs)
    {
        this.cityVOs = cityVOs;
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
}
