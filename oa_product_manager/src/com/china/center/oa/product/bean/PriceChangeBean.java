/**
 * File Name: PriceChangeBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.product.constant.PriceChangeConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * CHECK PriceChangeBean
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_PRICE_CHANGE")
public class PriceChangeBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    private String logTime = "";

    /**
     * 0:正常 1:回滚
     */
    private int status = PriceChangeConstant.STATUS_COMMON;

    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    @Html(title = "调价原因", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;

    @Ignore
    private List<PriceChangeSrcItemBean> srcList = null;

    @Ignore
    private List<PriceChangeNewItemBean> newList = null;

    /**
     * default constructor
     */
    public PriceChangeBean()
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
     * @return the srcList
     */
    public List<PriceChangeSrcItemBean> getSrcList()
    {
        return srcList;
    }

    /**
     * @param srcList
     *            the srcList to set
     */
    public void setSrcList(List<PriceChangeSrcItemBean> srcList)
    {
        this.srcList = srcList;
    }

    /**
     * @return the newList
     */
    public List<PriceChangeNewItemBean> getNewList()
    {
        return newList;
    }

    /**
     * @param newList
     *            the newList to set
     */
    public void setNewList(List<PriceChangeNewItemBean> newList)
    {
        this.newList = newList;
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
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
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
            .append("PriceChangeBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("srcList = ")
            .append(this.srcList)
            .append(TAB)
            .append("newList = ")
            .append(this.newList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
