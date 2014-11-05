/**
 * File Name: RoleAuthBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vs;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.CacheRelation;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.ProvinceBean;


/**
 * RoleAuthBean
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_VS_LOCATIONCITY")
@CacheRelation(relation = CityBean.class)
public class LocationVSCityBean implements DataClone<LocationVSCityBean>, Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    @Join(tagClass = ProvinceBean.class)
    private String provinceId = "";

    @Unique
    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = CityBean.class)
    private String cityId = "";

    /**
     * Copy Constructor
     * 
     * @param locationVSCityBean
     *            a <code>LocationVSCityBean</code> object
     */
    public LocationVSCityBean(LocationVSCityBean locationVSCityBean)
    {
        this.id = locationVSCityBean.id;
        this.locationId = locationVSCityBean.locationId;
        this.provinceId = locationVSCityBean.provinceId;
        this.cityId = locationVSCityBean.cityId;
    }

    /**
     * default constructor
     */
    public LocationVSCityBean()
    {
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if ( ! (obj instanceof LocationVSCityBean))
        {
            return false;
        }

        LocationVSCityBean cobj = (LocationVSCityBean)obj;

        return cobj.getCityId().equals(cityId);
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the provinceId
     */
    public String getProvinceId()
    {
        return provinceId;
    }

    /**
     * @param provinceId
     *            the provinceId to set
     */
    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    /**
     * @return the cityId
     */
    public String getCityId()
    {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public LocationVSCityBean clones()
    {
        return new LocationVSCityBean(this);
    }
}
