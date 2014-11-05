/**
 * File Name: FlowVSTemplate.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vs;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * FlowVSTemplate
 * 
 * @author zhuzhu
 * @version 2009-4-26
 * @see FlowVSTemplateBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_FLOW_TEMPLATE")
public class FlowVSTemplateBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String flowId = "";

    @FK(index = AnoConstant.FK_FIRST)
    private String templateId = "";

    /**
     * default constructor
     */
    public FlowVSTemplateBean()
    {}

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @param flowId the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }

    /**
     * @return the templateId
     */
    public String getTemplateId()
    {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }
}
