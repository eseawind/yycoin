/**
 * File Name: TcpParamWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-13<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.wrap;


import java.io.Serializable;


/**
 * 处理参数
 * 
 * @author ZHUZHU
 * @version 2011-9-13
 * @see TcpParamWrap
 * @since 1.0
 */
public class TcpParamWrap implements Serializable
{
    private String id = "";

    private String processId = "";

    private String reason = "";

    private String type = "";

    private String dutyId = "";
    
    private String compliance = "";

    private Object other = null;

    private Object other2 = null;

    private Object other3 = null;

    /**
     * default constructor
     */
    public TcpParamWrap()
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
     * @return the processId
     */
    public String getProcessId()
    {
        return processId;
    }

    /**
     * @param processId
     *            the processId to set
     */
    public void setProcessId(String processId)
    {
        this.processId = processId;
    }

    /**
     * @return the reason
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * @return the other
     */
    public Object getOther()
    {
        return other;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(Object other)
    {
        this.other = other;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the other2
     */
    public Object getOther2()
    {
        return other2;
    }

    /**
     * @param other2
     *            the other2 to set
     */
    public void setOther2(Object other2)
    {
        this.other2 = other2;
    }

    /**
     * @return the other3
     */
    public Object getOther3()
    {
        return other3;
    }

    /**
     * @param other3
     *            the other3 to set
     */
    public void setOther3(Object other3)
    {
        this.other3 = other3;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
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
            .append("TcpParamWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("processId = ")
            .append(this.processId)
            .append(TAB)
            .append("reason = ")
            .append(this.reason)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("compliance = ")
            .append(this.compliance)            
            .append(TAB)
            .append("other = ")
            .append(this.other)
            .append(TAB)
            .append("other2 = ")
            .append(this.other2)
            .append(TAB)
            .append("other3 = ")
            .append(this.other3)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
