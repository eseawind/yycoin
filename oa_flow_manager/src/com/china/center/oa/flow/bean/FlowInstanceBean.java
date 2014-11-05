/**
 * File Name: FlowInstanceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.publics.bean.StafferBean;


@Entity
@Table(name = "T_CENTER_OAFLOWINSTANCE")
public class FlowInstanceBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = FlowDefineBean.class)
    private String flowId = "";

    @Html(title = "标题", must = true, maxLength = 100)
    private String title = "";

    /**
     * liminal value
     */
    @Html(title = "总金额", must = true, maxLength = 10, oncheck = JCheck.ONLY_FLOAT)
    private double liminal = 0.0d;

    private String locationId = "";

    private String parentId = "";

    private String parentTokenId = "";

    private String logTime = "";

    @Html(title = "截止时间", type = Element.DATETIME, maxLength = 40)
    private String endTime = "";

    private int status = FlowConstant.FLOW_INSTANCE_BEGIN;

    /**
     * 流程实例类型
     */
    private int type = FlowConstant.FLOW_PARENTTYPE_ROOT;

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    @Html(title = "内容", type = Element.TEXTAREA, maxLength = 500)
    private String description = "";

    @Join(tagClass = FlowTokenBean.class)
    private String currentTokenId = "";

    /**
     * 流程附件
     */
    private String attachment = "";

    /**
     * 文件的真实名称
     */
    private String fileName = "";

    /**
     *
     */
    public FlowInstanceBean()
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
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
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
     * @return the endTime
     */
    public String getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
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
     * @return the attachment
     */
    public String getAttachment()
    {
        return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }

    /**
     * @return the currentTokenId
     */
    public String getCurrentTokenId()
    {
        return currentTokenId;
    }

    /**
     * @param currentTokenId
     *            the currentTokenId to set
     */
    public void setCurrentTokenId(String currentTokenId)
    {
        this.currentTokenId = currentTokenId;
    }

    public double getLiminal()
    {
        return liminal;
    }

    public void setLiminal(double liminal)
    {
        this.liminal = liminal;
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
     * @return the parentTokenId
     */
    public String getParentTokenId()
    {
        return parentTokenId;
    }

    /**
     * @param parentTokenId
     *            the parentTokenId to set
     */
    public void setParentTokenId(String parentTokenId)
    {
        this.parentTokenId = parentTokenId;
    }
}
