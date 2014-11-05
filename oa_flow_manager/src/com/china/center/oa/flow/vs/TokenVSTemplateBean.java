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
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.constant.FlowConstant;


/**
 * FlowVSTemplate
 * 
 * @author zhuzhu
 * @version 2009-4-26
 * @see TokenVSTemplateBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_TOKEN_TEMPLATE")
public class TokenVSTemplateBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";
    
    private String flowId = "";

    @FK
    private String tokenId = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = TemplateFileBean.class)
    private String templateId = "";

    private int viewTemplate = FlowConstant.TEMPLATE_READONLY_YES;

    private int editTemplate = FlowConstant.TEMPLATE_EDIT_NO;

    /**
     * default constructor
     */
    public TokenVSTemplateBean()
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
     * @return the tokenId
     */
    public String getTokenId()
    {
        return tokenId;
    }

    /**
     * @param tokenId
     *            the tokenId to set
     */
    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
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
     * @return the viewTemplate
     */
    public int getViewTemplate()
    {
        return viewTemplate;
    }

    /**
     * @param viewTemplate
     *            the viewTemplate to set
     */
    public void setViewTemplate(int viewTemplate)
    {
        this.viewTemplate = viewTemplate;
    }

    /**
     * @return the editTemplate
     */
    public int getEditTemplate()
    {
        return editTemplate;
    }

    /**
     * @param editTemplate
     *            the editTemplate to set
     */
    public void setEditTemplate(int editTemplate)
    {
        this.editTemplate = editTemplate;
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
}
