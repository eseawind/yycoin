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
import com.china.center.oa.customer.bean.CustomerApplyBean;


/**
 * CustomerVO
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerApplyVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CustomerApplyVO extends CustomerApplyBean
{
    @Relationship(tagField = "name", relationField = "provinceId")
    private String provinceName = "";

    @Relationship(tagField = "name", relationField = "cityId")
    private String cityName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";

    @Relationship(tagField = "name", relationField = "updaterId")
    private String stafferName = "";

    @Relationship(relationField = "hlocal")
    private String hlocalName = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    /**
     * default constructor
     */
    public CustomerApplyVO()
    {
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

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("CustomerApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("provinceName = ")
            .append(this.provinceName)
            .append(TAB)
            .append("cityName = ")
            .append(this.cityName)
            .append(TAB)
            .append("areaName = ")
            .append(this.areaName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("hlocalName = ")
            .append(this.hlocalName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
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
