/**
 * File Name: ResultBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;


/**
 * ResultBean
 * 
 * @author ZHUZHU
 * @version 2011-3-5
 * @see ResultBean
 * @since 3.0
 */
public class ResultBean implements Serializable
{
    /**
     * 0:收支相等 -1:费用不足 1:费用超支
     */
    private int result = 0;

    private String message = "";

    private String refId = "";

    private double value = 0.0d;

    /**
     * default constructor
     */
    public ResultBean()
    {
        success();
    }

    public void success()
    {
        this.result = 0;
    }

    /**
     * @return the result
     */
    public int getResult()
    {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(int result)
    {
        this.result = result;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ResultBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("result = ")
            .append(this.result)
            .append(TAB)
            .append("message = ")
            .append(this.message)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("value = ")
            .append(this.value)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
