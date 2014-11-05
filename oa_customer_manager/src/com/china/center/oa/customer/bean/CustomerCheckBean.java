/**
 * File Name: CustomerCheckBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.CommonConstant;


/**
 * CustomerCheckBean
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCheckBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_CUS_CHECK")
public class CustomerCheckBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = StafferBean.class, alias = "s1")
    private String applyerId = "";

    @Html(title = "被检职员", type = Element.SELECT, must = true)
    @Join(tagClass = StafferBean.class, alias = "s2")
    private String checkerId = "";

    @Join(tagClass = StafferBean.class, alias = "s3", type = JoinType.LEFT)
    private String approverId = "-1";

    @Html(title = "开始时间", type = Element.DATETIME, must = true)
    private String beginTime = "";

    @Html(title = "结束时间", type = Element.DATETIME, must = true)
    private String endTime = "";

    private int status = CommonConstant.STATUS_INIT;

    private int retInit = 0;

    private int retOK = 0;

    private int retError = 0;

    private String logTime = "";

    private String locationId = "";

    @Html(title = "备注", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * default constructor
     */
    public CustomerCheckBean()
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
     * @return the applyerId
     */
    public String getApplyerId()
    {
        return applyerId;
    }

    /**
     * @param applyerId
     *            the applyerId to set
     */
    public void setApplyerId(String applyerId)
    {
        this.applyerId = applyerId;
    }

    /**
     * @return the checkerId
     */
    public String getCheckerId()
    {
        return checkerId;
    }

    /**
     * @param checkerId
     *            the checkerId to set
     */
    public void setCheckerId(String checkerId)
    {
        this.checkerId = checkerId;
    }

    /**
     * @return the approverId
     */
    public String getApproverId()
    {
        return approverId;
    }

    /**
     * @param approverId
     *            the approverId to set
     */
    public void setApproverId(String approverId)
    {
        this.approverId = approverId;
    }

    /**
     * @return the beginTime
     */
    public String getBeginTime()
    {
        return beginTime;
    }

    /**
     * @param beginTime
     *            the beginTime to set
     */
    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
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
     * @return the retInit
     */
    public int getRetInit()
    {
        return retInit;
    }

    /**
     * @param retInit
     *            the retInit to set
     */
    public void setRetInit(int retInit)
    {
        this.retInit = retInit;
    }

    /**
     * @return the retOK
     */
    public int getRetOK()
    {
        return retOK;
    }

    /**
     * @param retOK
     *            the retOK to set
     */
    public void setRetOK(int retOK)
    {
        this.retOK = retOK;
    }

    /**
     * @return the retError
     */
    public int getRetError()
    {
        return retError;
    }

    /**
     * @param retError
     *            the retError to set
     */
    public void setRetError(int retError)
    {
        this.retError = retError;
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
}
