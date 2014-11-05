/**
 * File Name: PlanBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.constant.PlanConstant;


/**
 * PlanBean
 * 
 * @author ZHUZHU
 * @version 2009-1-11
 * @see PlanBean
 * @since 1.0
 */

@Entity
@Table(name = "T_CENTER_PLAN")
public class PlanBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String fkId = "";

    private int type = 0;

    /**
     * 0:执行一次 1：每天执行，到结束日期为止
     */
    private int carryType = PlanConstant.PLAN_CARRYTYPES_EVERYDAY;

    private int status = PlanConstant.PLAN_STATUS_INIT;

    private String logTime = "";

    /**
     * 执行顺序 数字越小越先 越大越靠后执行
     */
    private int orderIndex = 0;

    /**
     * 开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String beginTime = "";

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime = "";

    /**
     * 最后结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String carryTime = "";

    private String realCarryTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public PlanBean()
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
     * @return the carryTime
     */
    public String getCarryTime()
    {
        return carryTime;
    }

    /**
     * @param carryTime
     *            the carryTime to set
     */
    public void setCarryTime(String carryTime)
    {
        this.carryTime = carryTime;
    }

    /**
     * @return the realCarryTime
     */
    public String getRealCarryTime()
    {
        return realCarryTime;
    }

    /**
     * @param realCarryTime
     *            the realCarryTime to set
     */
    public void setRealCarryTime(String realCarryTime)
    {
        this.realCarryTime = realCarryTime;
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
     * @return the carryType
     */
    public int getCarryType()
    {
        return carryType;
    }

    /**
     * @param carryType
     *            the carryType to set
     */
    public void setCarryType(int carryType)
    {
        this.carryType = carryType;
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
     * @return the orderIndex
     */
    public int getOrderIndex()
    {
        return orderIndex;
    }

    /**
     * @param orderIndex
     *            the orderIndex to set
     */
    public void setOrderIndex(int orderIndex)
    {
        this.orderIndex = orderIndex;
    }
}
