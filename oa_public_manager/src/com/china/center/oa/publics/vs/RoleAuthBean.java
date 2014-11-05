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

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * RoleAuthBean
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_ROLEAUTH")
public class RoleAuthBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String roleId = "";

    private String authId = "";

    /**
     * default constructor
     */
    public RoleAuthBean()
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
     * @return the authId
     */
    public String getAuthId()
    {
        return authId;
    }

    /**
     * @param authId
     *            the authId to set
     */
    public void setAuthId(String authId)
    {
        this.authId = authId;
    }
}
