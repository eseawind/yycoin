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


/**
 * FlowInstanceViewBean
 * 
 * @author ZHUZHU
 * @version 2008-9-3
 * @see
 * @since
 */
@Entity(name = "流程可视人")
@Table(name = "T_CENTER_OAFLOWVIEWER")
public class FlowViewerBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = FlowDefineBean.class)
    private String flowId = "";

    private String processer = "";

    private int type = 0;

    /**
     * default constructor
     */
    public FlowViewerBean()
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
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
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
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
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
     * @return the processer
     */
    public String getProcesser()
    {
        return processer;
    }

    /**
     * @param processer
     *            the processer to set
     */
    public void setProcesser(String processer)
    {
        this.processer = processer;
    }
}
