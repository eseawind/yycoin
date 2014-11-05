/*
 * File Name: User.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 人员类
 * 
 * @author ZHUZHU
 * @version 2007-3-25
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_PROVIDEUSER")
public class ProviderUserBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Unique
    @Html(title = "登录名", must = true, maxLength = 16)
    private String name = "";

    @Html(title = "密码", must = true, maxLength = 16, type = Element.PASSWORD)
    private String password = "";

    @Html(title = "加密码", maxLength = 40)
    private String pwkey = "";

    @FK
    @Join(tagClass = ProviderBean.class)
    private String provideId = "";

    private String locationId = "";

    private String roleId = "";

    /**
     * 0:正常 1:锁定
     */
    private int status = PublicConstant.LOGIN_STATUS_COMMON;

    /**
     * 登录失败次数
     */
    private int fail = 0;

    private String loginTime = "";

    /**
     * default constructor
     */
    public ProviderUserBean()
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
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the provideId
     */
    public String getProvideId()
    {
        return provideId;
    }

    /**
     * @param provideId
     *            the provideId to set
     */
    public void setProvideId(String provideId)
    {
        this.provideId = provideId;
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
     * @return the roleId
     */
    public String getRoleId()
    {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the fail
     */
    public int getFail()
    {
        return fail;
    }

    /**
     * @param fail
     *            the fail to set
     */
    public void setFail(int fail)
    {
        this.fail = fail;
    }

    /**
     * @return the loginTime
     */
    public String getLoginTime()
    {
        return loginTime;
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLoginTime(String loginTime)
    {
        this.loginTime = loginTime;
    }

    /**
     * @return the pwkey
     */
    public String getPwkey()
    {
        return pwkey;
    }

    /**
     * @param pwkey
     *            the pwkey to set
     */
    public void setPwkey(String pwkey)
    {
        this.pwkey = pwkey;
    }
}
