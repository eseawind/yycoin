/**
 * File Name: TravelApplyPayBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.tcp.constanst.TcpConstanst;


/**
 * TravelApplyPayBean
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyPayBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TRAVELAPPLYPAY")
public class TravelApplyPayBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String parentId = "";

    private int receiveType = TcpConstanst.TRAVELAPPLY_RECEIVETYPE_CASH;

    private String bankName = "";

    private String userName = "";

    private String bankNo = "";

    private String description = "";

    /**
     * 核对
     */
    private String cdescription = "";

    private long moneys = 0L;

    /**
     * 核对
     */
    private long cmoneys = 0L;

    /**
     * default constructor
     */
    public TravelApplyPayBean()
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
     * @return the receiveType
     */
    public int getReceiveType()
    {
        return receiveType;
    }

    /**
     * @param receiveType
     *            the receiveType to set
     */
    public void setReceiveType(int receiveType)
    {
        this.receiveType = receiveType;
    }

    /**
     * @return the bankName
     */
    public String getBankName()
    {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the bankNo
     */
    public String getBankNo()
    {
        return bankNo;
    }

    /**
     * @param bankNo
     *            the bankNo to set
     */
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
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
     * @return the moneys
     */
    public long getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(long moneys)
    {
        this.moneys = moneys;
    }

    /**
     * @return the cdescription
     */
    public String getCdescription()
    {
        return cdescription;
    }

    /**
     * @param cdescription
     *            the cdescription to set
     */
    public void setCdescription(String cdescription)
    {
        this.cdescription = cdescription;
    }

    /**
     * @return the cmoneys
     */
    public long getCmoneys()
    {
        return cmoneys;
    }

    /**
     * @param cmoneys
     *            the cmoneys to set
     */
    public void setCmoneys(long cmoneys)
    {
        this.cmoneys = cmoneys;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("TravelApplyPayBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("receiveType = ")
            .append(this.receiveType)
            .append(TAB)
            .append("bankName = ")
            .append(this.bankName)
            .append(TAB)
            .append("userName = ")
            .append(this.userName)
            .append(TAB)
            .append("bankNo = ")
            .append(this.bankNo)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("cdescription = ")
            .append(this.cdescription)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("cmoneys = ")
            .append(this.cmoneys)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
