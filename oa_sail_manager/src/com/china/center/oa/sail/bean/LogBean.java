/*
 * File Name: LogBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-5-18
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.oa.sail.constanst.OutConstant;


/**
 * 日志bean(记录到文本的日志)
 * 
 * @author ZHUZHU
 * @version 2007-5-18
 * @see
 * @since
 */
public class LogBean implements Serializable
{
    private int type = 0;

    private String outId = "";

    private String refId = "";

    private int outType = 0;

    private String productName = "";

    private String locationId = "";

    private int beforCount = 0;

    private int afterCount = 0;

    private int current = 0;

    private String staffer = "";

    private String user = "";

    private double value = 0.0d;

    private int oprType = 0;

    private int preStatus = 0;

    private int afterStatus = 0;

    private String log = "";

    /**
     * default constructor
     */
    public LogBean()
    {
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getOprTypes()).append(',');

        buffer.append(this.outId).append(',');

        buffer.append(this.refId).append(',');

        buffer.append(getTypes()).append(',');

        buffer.append(getOutTypes()).append(',');

        buffer.append(getLogString(this.productName)).append(',');

        buffer.append(beforCount).append(',');

        buffer.append(afterCount).append(',');

        buffer.append(current).append(',');

        buffer.append(value).append(',');

        buffer.append(getLogString(staffer)).append(',');

        buffer.append(user).append(',');

        buffer.append(preStatus).append(',');

        buffer.append(afterStatus).append(',');

        buffer.append(getLogString(this.locationId)).append(',');

        buffer.append(log);

        return buffer.toString();
    }

    public String getLogString(String s)
    {
        if (s == null)
        {
            return "";
        }

        return s.replaceAll(",", "");
    }

    public void setTypes(String s)
    {

    }

    public String getTypes()
    {
        if (this.type == OutConstant.OUT_TYPE_OUTBILL)
        {
            return "销售单";
        }

        if (this.type == OutConstant.OUT_TYPE_INBILL)
        {
            return "入库单";
        }

        return "未知类型";
    }

    public void setOutTypes(String str)
    {

    }

    public String getOutTypes()
    {
        if (this.outType == 0)
        {
            return "销售出库";
        }

        if (this.outType == 0)
        {
            return "采购入库";
        }

        if (this.outType == 1)
        {
            return "调出/个人领样";
        }

        if (this.outType == 2)
        {
            return "盘亏出库";
        }

        if (this.outType == 3)
        {
            return "盘盈入库";
        }

        if (this.outType == 4)
        {
            return "调入";
        }

        if (this.outType == 5)
        {
            return "退换货入库";
        }

        if (this.outType == 6)
        {
            return "报废出库";
        }

        return "未知类型";
    }

    public String getOprTypes()
    {
        if (this.oprType == 0)
        {
            return "库单提交";
        }

        return "库单驳回";
    }

    public void setOprTypes(String str)
    {
    }

    /**
     * @return the afterCount
     */
    public int getAfterCount()
    {
        return afterCount;
    }

    /**
     * @param afterCount
     *            the afterCount to set
     */
    public void setAfterCount(int afterCount)
    {
        this.afterCount = afterCount;
    }

    /**
     * @return the beforCount
     */
    public int getBeforCount()
    {
        return beforCount;
    }

    /**
     * @param beforCount
     *            the beforCount to set
     */
    public void setBeforCount(int beforCount)
    {
        this.beforCount = beforCount;
    }

    /**
     * @return the current
     */
    public int getCurrent()
    {
        return current;
    }

    /**
     * @param current
     *            the current to set
     */
    public void setCurrent(int current)
    {
        this.current = current;
    }

    /**
     * @return the outType
     */
    public int getOutType()
    {
        return outType;
    }

    /**
     * @param outType
     *            the outType to set
     */
    public void setOutType(int outType)
    {
        this.outType = outType;
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
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the staffer
     */
    public String getStaffer()
    {
        return staffer;
    }

    /**
     * @param staffer
     *            the staffer to set
     */
    public void setStaffer(String staffer)
    {
        this.staffer = staffer;
    }

    /**
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * @return the oprType
     */
    public int getOprType()
    {
        return oprType;
    }

    /**
     * @param oprType
     *            the oprType to set
     */
    public void setOprType(int oprType)
    {
        this.oprType = oprType;
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
     * @return the preStatus
     */
    public int getPreStatus()
    {
        return preStatus;
    }

    /**
     * @param preStatus
     *            the preStatus to set
     */
    public void setPreStatus(int preStatus)
    {
        this.preStatus = preStatus;
    }

    /**
     * @return the afterStatus
     */
    public int getAfterStatus()
    {
        return afterStatus;
    }

    /**
     * @param afterStatus
     *            the afterStatus to set
     */
    public void setAfterStatus(int afterStatus)
    {
        this.afterStatus = afterStatus;
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
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

}
