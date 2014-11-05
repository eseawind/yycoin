/*
 * File Name: LocationBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationBean
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_OALOCATION")
public class LocationBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "名称", must = true, maxLength = 20)
    private String name = "";

    @Html(title = "标识", must = true, oncheck = JCheck.ONLY_LETTER, maxLength = 10, tip = "只能是字母")
    private String code = "";

    @Html(name = "parentName", title = "父级组织", must = true, maxLength = 20)
    @Join(tagClass = PrincipalshipBean.class)
    private String parentId = "";

    @Html(title = "描述", maxLength = 100, type = Element.TEXTAREA)
    private String description = "";

    @Ignore
    private List<LocationVSCityBean> citys = null;

    /**
     * @return the citys
     */
    public List<LocationVSCityBean> getCitys()
    {
        return citys;
    }

    /**
     * @param citys
     *            the citys to set
     */
    public void setCitys(List<LocationVSCityBean> citys)
    {
        this.citys = citys;
    }

    /**
     * default constructor
     */
    public LocationBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
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
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
}
