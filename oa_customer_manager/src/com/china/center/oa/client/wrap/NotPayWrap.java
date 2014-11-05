/**
 * File Name: NotPayWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-5-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.wrap;


import java.io.Serializable;


/**
 * 未付款的客户
 * 
 * @author ZHUZHU
 * @version 2010-5-27
 * @see NotPayWrap
 * @since 1.0
 */
public class NotPayWrap implements Serializable
{
    private String cid = "";

    private String cname = "";

    private String ccode = "";

    private String creditName = "";

    private double creditVal = 0.0d;

    private double notPay = 0.0d;

    /**
     * default constructor
     */
    public NotPayWrap()
    {}

    /**
     * @return the cid
     */
    public String getCid()
    {
        return cid;
    }

    /**
     * @param cid
     *            the cid to set
     */
    public void setCid(String cid)
    {
        this.cid = cid;
    }

    /**
     * @return the cname
     */
    public String getCname()
    {
        return cname;
    }

    /**
     * @param cname
     *            the cname to set
     */
    public void setCname(String cname)
    {
        this.cname = cname;
    }

    /**
     * @return the ccode
     */
    public String getCcode()
    {
        return ccode;
    }

    /**
     * @param ccode
     *            the ccode to set
     */
    public void setCcode(String ccode)
    {
        this.ccode = ccode;
    }

    /**
     * @return the creditName
     */
    public String getCreditName()
    {
        return creditName;
    }

    /**
     * @param creditName
     *            the creditName to set
     */
    public void setCreditName(String creditName)
    {
        this.creditName = creditName;
    }

    /**
     * @return the creditVal
     */
    public double getCreditVal()
    {
        return creditVal;
    }

    /**
     * @param creditVal
     *            the creditVal to set
     */
    public void setCreditVal(double creditVal)
    {
        this.creditVal = creditVal;
    }

    /**
     * @return the notPay
     */
    public double getNotPay()
    {
        return notPay;
    }

    /**
     * @param notPay
     *            the notPay to set
     */
    public void setNotPay(double notPay)
    {
        this.notPay = notPay;
    }
}
