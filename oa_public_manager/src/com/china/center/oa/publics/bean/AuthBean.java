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

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * AuthBean
 * 
 * @author ZHUZHU
 * @version 2007-12-15
 * @see
 * @since
 */
@Entity(cache = false)
@Table(name = "T_CENTER_AUTH")
public class AuthBean implements DataClone<AuthBean>, Serializable
{
    @Id
    private String id = "";

    private String name = "";

    private String parentId = "";

    private int level = PublicConstant.ROLE_LEVEL_ROOT;

    /**
     * Ȩ������
     */
    private int type = PublicConstant.AUTH_TYPE_LOCATION;

    private int bottomFlag = PublicConstant.BOTTOMFLAG_YES;

    /**
     * Copy Constructor
     * 
     * @param authBean
     *            a <code>AuthBean</code> object
     */
    public AuthBean(AuthBean authBean)
    {
        this.id = authBean.id;
        this.name = authBean.name;
        this.parentId = authBean.parentId;
        this.level = authBean.level;
        this.type = authBean.type;
        this.bottomFlag = authBean.bottomFlag;
    }

    /**
     * default constructor
     */
    public AuthBean()
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
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
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

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the bottomFlag
     */
    public int getBottomFlag()
    {
        return bottomFlag;
    }

    /**
     * @param bottomFlag
     *            the bottomFlag to set
     */
    public void setBottomFlag(int bottomFlag)
    {
        this.bottomFlag = bottomFlag;
    }

    public AuthBean clones()
    {
        return new AuthBean(this);
    }
}
