/**
 * File Name: EnumBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * EnumBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see EnumBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_ENUM")
public class EnumBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = EnumDefineBean.class, tagField = "name")
    @Html(title = "类型", type = Element.SELECT, must = true)
    private String type = "";

    @Column(name = "keyss")
    private String key = "";

    @Html(title = "配置项", must = true)
    @Column(name = "val")
    private String value = "";

    /**
     * 0:初始化 1:人工加入
     */
    private int status = PublicConstant.ENUM_ADD;

    public EnumBean()
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
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
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
}
