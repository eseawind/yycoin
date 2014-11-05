/*
 * File Name: RoleBean.java
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
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.vs.RoleAuthBean;


/**
 * RoleBean
 * 
 * @author ZHUZHU
 * @version 2007-12-15
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_OAROLE")
public class RoleBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "角色名称", must = true, maxLength = 100)
    private String name = "";

    @Join(tagClass = LocationBean.class, type = JoinType.LEFT)
    @Html(title = "所属分公司", must = true, type = Element.SELECT)
    private String locationId = "";

    private int visible = PublicConstant.ROLE_VISIBLE_YES;

    @Html(title = "描述", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Ignore
    private List<RoleAuthBean> auth = null;

    /**
     * default constructor
     */
    public RoleBean()
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
     * @return the visible
     */
    public int getVisible()
    {
        return visible;
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
     * @param visible
     *            the visible to set
     */
    public void setVisible(int visible)
    {
        this.visible = visible;
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
     * @return the auth
     */
    public List<RoleAuthBean> getAuth()
    {
        return auth;
    }

    /**
     * @param auth
     *            the auth to set
     */
    public void setAuth(List<RoleAuthBean> auth)
    {
        this.auth = auth;
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
}
