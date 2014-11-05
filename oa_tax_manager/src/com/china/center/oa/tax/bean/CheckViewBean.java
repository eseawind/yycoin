/**
 * File Name: CheckViewBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.tax.constanst.CheckConstant;


/**
 * CheckViewBean
 * 
 * @author ZHUZHU
 * @version 2011-3-9
 * @see CheckViewBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_CHECK")
public class CheckViewBean implements Serializable
{
    @Id
    private String id = "";

    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;

    private String logTime = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    @Join(tagClass = UnitBean.class, type = JoinType.LEFT)
    private String unitId = "";

    private String refId = "";

    private int type = CheckConstant.CHECK_TYPE_COMPOSE;

    /**
     * default constructor
     */
    public CheckViewBean()
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
     * @return the unitId
     */
    public String getUnitId()
    {
        return unitId;
    }

    /**
     * @param unitId
     *            the unitId to set
     */
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
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
            .append("CheckViewBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("unitId = ")
            .append(this.unitId)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
