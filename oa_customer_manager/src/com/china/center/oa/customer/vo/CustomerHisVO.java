/**
 * File Name: CustomerVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.customer.bean.CustomerHisBean;


/**
 * CustomerVO
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerHisVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CustomerHisVO extends CustomerHisBean
{
    @Relationship(relationField = "updaterId")
    private String updaterName = "";

    @Relationship(relationField = "hlocal")
    private String hlocalName = "";

    @Relationship(relationField = "provinceId")
    private String provinceName = "";

    @Relationship(relationField = "cityId")
    private String cityName = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Relationship(relationField = "creditLevelId")
    private String creditLevelName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";

    /**
     * default constructor
     */
    public CustomerHisVO()
    {
    }

    /**
     * @return the updaterName
     */
    public String getUpdaterName()
    {
        return updaterName;
    }

    /**
     * @param updaterName
     *            the updaterName to set
     */
    public void setUpdaterName(String updaterName)
    {
        this.updaterName = updaterName;
    }

    /**
     * @return the hlocalName
     */
    public String getHlocalName()
    {
        return hlocalName;
    }

    /**
     * @param hlocalName
     *            the hlocalName to set
     */
    public void setHlocalName(String hlocalName)
    {
        this.hlocalName = hlocalName;
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

        retValue.append("CustomerHisVO ( ").append(super.toString()).append(TAB).append(
            "updaterName = ").append(this.updaterName).append(TAB).append("hlocalName = ").append(
            this.hlocalName).append(TAB).append(" )");

        return retValue.toString();
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
     * @return the creditLevelName
     */
    public String getCreditLevelName()
    {
        return creditLevelName;
    }

    /**
     * @param creditLevelName
     *            the creditLevelName to set
     */
    public void setCreditLevelName(String creditLevelName)
    {
        this.creditLevelName = creditLevelName;
    }

    /**
     * @return the areaName
     */
    public String getAreaName()
    {
        return areaName;
    }

    /**
     * @param areaName
     *            the areaName to set
     */
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }
}
