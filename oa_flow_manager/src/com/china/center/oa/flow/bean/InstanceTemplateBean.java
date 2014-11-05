/**
 * File Name: InstanceTemplateBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.flow.constant.FlowConstant;


/**
 * InstanceTemplateBean
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see InstanceTemplateBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_INSTANCETEMPLATE")
public class InstanceTemplateBean implements DataClone<InstanceTemplateBean>, Serializable
{
    @Id
    private String id = "";

    private String name = "";

    @FK
    private String instanceId = "";

    private String templateId = "";

    private String flowId = "";

    private String path = "";

    private String logTime = "";

    private int readonly = FlowConstant.TEMPLATE_READONLY_YES;

    /**
     * Copy Constructor
     * 
     * @param instanceTemplateBean
     *            a <code>InstanceTemplateBean</code> object
     */
    public InstanceTemplateBean(InstanceTemplateBean instanceTemplateBean)
    {
        this.id = instanceTemplateBean.id;
        this.name = instanceTemplateBean.name;
        this.instanceId = instanceTemplateBean.instanceId;
        this.templateId = instanceTemplateBean.templateId;
        this.flowId = instanceTemplateBean.flowId;
        this.path = instanceTemplateBean.path;
        this.logTime = instanceTemplateBean.logTime;
        this.readonly = instanceTemplateBean.readonly;
    }

    /**
     * default constructor
     */
    public InstanceTemplateBean()
    {
    }

    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        result = prime * result + readonly;
        return result;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        final InstanceTemplateBean other = (InstanceTemplateBean)obj;

        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if ( !id.equals(other.id))
        {
            return false;
        }
        if (readonly != other.readonly)
        {
            return false;
        }

        return true;
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
     * @return the instanceId
     */
    public String getInstanceId()
    {
        return instanceId;
    }

    /**
     * @param instanceId
     *            the instanceId to set
     */
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    /**
     * @return the templateId
     */
    public String getTemplateId()
    {
        return templateId;
    }

    /**
     * @param templateId
     *            the templateId to set
     */
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    /**
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
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
     * @return the readonly
     */
    public int getReadonly()
    {
        return readonly;
    }

    /**
     * @param readonly
     *            the readonly to set
     */
    public void setReadonly(int readonly)
    {
        this.readonly = readonly;
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

        retValue
            .append("InstanceTemplateBean ( ")
            .append(super.toString())
            .append(tab)
            .append("id = ")
            .append(this.id)
            .append(tab)
            .append("name = ")
            .append(this.name)
            .append(tab)
            .append("instanceId = ")
            .append(this.instanceId)
            .append(tab)
            .append("templateId = ")
            .append(this.templateId)
            .append(tab)
            .append("flowId = ")
            .append(this.flowId)
            .append(tab)
            .append("path = ")
            .append(this.path)
            .append(tab)
            .append("logTime = ")
            .append(this.logTime)
            .append(tab)
            .append("readonly = ")
            .append(this.readonly)
            .append(tab)
            .append(" )");

        return retValue.toString();
    }

    public InstanceTemplateBean clones()
    {
        return new InstanceTemplateBean(this);
    }
}
