/**
 * File Name: LocationVSCityVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationVSCityVO
 * 
 * @author zhuzhu
 * @version 2008-11-8
 * @see LocationVSCityVO
 * @since 1.0
 */
@Entity(inherit = true)
public class LocationVSCityVO extends LocationVSCityBean
{
    @Relationship(relationField = "locationId", tagField = "name")
    private String locationName = "";

    @Relationship(relationField = "provinceId", tagField = "name")
    private String provinceName = "";

    @Relationship(relationField = "cityId", tagField = "name")
    private String cityName = "";

    public LocationVSCityVO()
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

    /**
     * @return the provinceName
     */
    public String getProvinceName()
    {
        return provinceName;
    }

    /**
     * @param provinceName
     *            the provinceName to set
     */
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    /**
     * @return the cityName
     */
    public String getCityName()
    {
        return cityName;
    }

    /**
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
}
