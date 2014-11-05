/**
 * File Name: TemplateFileBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;


/**
 * TemplateFileBean
 * 
 * @author ZHUZHU
 * @version 2009-4-21
 * @see TemplateFileBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_TEMPLATEFILE")
public class TemplateFileBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "名称", must = true, maxLength = 100)
    private String name = "";

    private String fileName = "";

    private String path = "";

    private String logTime = "";

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * default constructor
     */
    public TemplateFileBean()
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
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
}
