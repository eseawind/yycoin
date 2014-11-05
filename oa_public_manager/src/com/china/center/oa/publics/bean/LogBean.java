/**
 * 
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;


/**
 * LogBean
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see LogBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_LOG")
public class LogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @FK
    private String fkId = "";

    private String module = "";

    private String operation = "";

    private String log = "";

    private String locationId = "";

    private String logTime = "";

    /**
     * ��λ�õ�id
     */
    private String posid = "";

    private String position = "";

    /**
     * 
     */
    public LogBean()
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
     * @return the module
     */
    public String getModule()
    {
        return module;
    }

    /**
     * @param module
     *            the module to set
     */
    public void setModule(String module)
    {
        this.module = module;
    }

    /**
     * @return the operation
     */
    public String getOperation()
    {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    /**
     * @return the log
     */
    public String getLog()
    {
        return log;
    }

    /**
     * @param log
     *            the log to set
     */
    public void setLog(String log)
    {
        this.log = log;
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
     * @return the fkId
     */
    public String getFkId()
    {
        return fkId;
    }

    /**
     * @param fkId
     *            the fkId to set
     */
    public void setFkId(String fkId)
    {
        this.fkId = fkId;
    }

    /**
     * @return the position
     */
    public String getPosition()
    {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(String position)
    {
        this.position = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof LogBean)) return false;
        final LogBean other = (LogBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
    }

    /**
     * @return the posid
     */
    public String getPosid()
    {
        return posid;
    }

    /**
     * @param posid
     *            the posid to set
     */
    public void setPosid(String posid)
    {
        this.posid = posid;
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

        retValue.append("LogBean ( ").append(super.toString()).append(tab).append("id = ").append(
            this.id).append(tab).append("stafferId = ").append(this.stafferId).append(tab).append(
            "fkId = ").append(this.fkId).append(tab).append("module = ").append(this.module).append(
            tab).append("operation = ").append(this.operation).append(tab).append("log = ").append(
            this.log).append(tab).append("locationId = ").append(this.locationId).append(tab).append(
            "logTime = ").append(this.logTime).append(tab).append("posid = ").append(this.posid).append(
            tab).append("position = ").append(this.position).append(tab).append(" )");

        return retValue.toString();
    }
}
