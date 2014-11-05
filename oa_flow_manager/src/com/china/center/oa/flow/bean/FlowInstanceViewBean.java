/**
 * File Name: FlowInstanceViewBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-9-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * FlowInstanceViewBean
 * 
 * @author ZHUZHU
 * @version 2008-9-3
 * @see
 * @since
 */
@Entity(name = "流程实例可视")
@Table(name = "T_CENTER_OAFLOWINSTANCEVIEW")
public class FlowInstanceViewBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK(index = FlowConstant.FK_INDEX_USERID)
    private String viewer = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String createId = "";

    @Join(tagClass = FlowDefineBean.class)
    private String flowId = "";

    private int type = FlowConstant.FLOW_PLUGIN_STAFFER;

    @FK
    @Unique(dependFields = "stafferId")
    @Join(tagClass = FlowInstanceBean.class)
    private String instanceId = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public FlowInstanceViewBean()
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
     * @return the instanceId
     */
    public String getInstanceId()
    {
        return instanceId;
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
     * @param instanceId
     *            the instanceId to set
     */
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    /**
     * @return the viewer
     */
    public String getViewer()
    {
        return viewer;
    }

    /**
     * @param viewer
     *            the viewer to set
     */
    public void setViewer(String viewer)
    {
        this.viewer = viewer;
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
     * @return the createId
     */
    public String getCreateId()
    {
        return createId;
    }

    /**
     * @param createId
     *            the createId to set
     */
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("FlowInstanceViewBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("viewer = ")
            .append(this.viewer)
            .append(TAB)
            .append("createId = ")
            .append(this.createId)
            .append(TAB)
            .append("flowId = ")
            .append(this.flowId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("instanceId = ")
            .append(this.instanceId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
