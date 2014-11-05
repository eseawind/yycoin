/**
 * File Name: CityBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * ProvinceBean
 * 
 * @author ZHUZHU
 * @version 2008-11-8
 * @see ProvinceBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_PROVINCE")
public class ProvinceBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    private String name = "";

    /**
     * default constructor
     */
    public ProvinceBean()
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
}
