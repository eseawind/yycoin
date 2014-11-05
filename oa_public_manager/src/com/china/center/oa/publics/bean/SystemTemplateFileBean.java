/**
 * File Name: TemplateFileBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * SystemTemplateFileBean
 * 
 * @author ZHUZHU
 * @version 2009-4-21
 * @see SystemTemplateFileBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_SYSTEM_TEMPLATEFILE")
public class SystemTemplateFileBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String pid = "";

    /**
     */
    private int type = 0;

    @Unique
    private String fk = "";

    private String name = "";

    private String fileName = "";

    private String path = "";

    private String description = "";

    /**
     * default constructor
     */
    public SystemTemplateFileBean()
    {}

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
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
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
     * @return the fileName
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * @return the fk
     */
    public String getFk()
    {
        return fk;
    }

    /**
     * @param fk
     *            the fk to set
     */
    public void setFk(String fk)
    {
        this.fk = fk;
    }

    /**
     * @return the pid
     */
    public String getPid()
    {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(String pid)
    {
        this.pid = pid;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("SystemTemplateFileBean ( ").append(super.toString()).append(tab).append(
            "id = ").append(this.id).append(tab).append("pid = ").append(this.pid).append(tab).append(
            "type = ").append(this.type).append(tab).append("fk = ").append(this.fk).append(tab).append(
            "name = ").append(this.name).append(tab).append("fileName = ").append(this.fileName).append(
            tab).append("path = ").append(this.path).append(tab).append("description = ").append(
            this.description).append(tab).append(" )");

        return retValue.toString();
    }
}
