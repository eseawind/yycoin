/**
 * File Name: AbstractBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;


/**
 * AbstractBean
 * 
 * @author zhuzhu
 * @version 2008-11-9
 * @see AbstractProviderBean
 * @since 1.0
 */
public abstract class AbstractProviderBean implements Serializable
{
    @Html(title = "名称", must = true, maxLength = 80)
    private String name = "";

    @Unique
    @Html(title = "编码", must = true, oncheck = JCheck.ONLY_NUMBER_OR_LETTER, maxLength = 40)
    private String code = "";

    @Html(title = "联系人", maxLength = 40)
    private String connector = "";

    @Html(title = "地址", maxLength = 200)
    private String address = "";

    @Html(title = "手机", maxLength = 40)
    private String phone = "";

    @Html(title = "固话", maxLength = 40)
    private String tel = "";

    @Html(title = "传真", maxLength = 40)
    private String fax = "";

    @Html(title = "QQ", maxLength = 40)
    private String qq = "";

    @Html(title = "E-Mail", maxLength = 40)
    private String email = "";

    @Html(title = "银行", maxLength = 100)
    private String bank = "";

    @Html(title = "帐号", maxLength = 40)
    private String accounts = "";

    @Html(title = "所属片区", type = Element.SELECT)
    private String location = "";

    /**
     * 109付款方式
     */
    @Html(title = "付费类型", type = Element.SELECT, must = true)
    private int type = 0;

    @Html(title = "税点(‰)", must = true, type = Element.NUMBER, maxLength = 3)
    private int dues = 0;

    private int mtype = 0;

    private int htype = 0;

    private int bakId = 0;

    private String logTime = "";

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the connector
     */
    public String getConnector()
    {
        return connector;
    }

    /**
     * @param connector
     *            the connector to set
     */
    public void setConnector(String connector)
    {
        this.connector = connector;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * @return the tel
     */
    public String getTel()
    {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax()
    {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * @return the qq
     */
    public String getQq()
    {
        return qq;
    }

    /**
     * @param qq
     *            the qq to set
     */
    public void setQq(String qq)
    {
        this.qq = qq;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the bank
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param bank
     *            the bank to set
     */
    public void setBank(String bank)
    {
        this.bank = bank;
    }

    /**
     * @return the accounts
     */
    public String getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(String accounts)
    {
        this.accounts = accounts;
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
     * @return the htype
     */
    public int getHtype()
    {
        return htype;
    }

    /**
     * @param htype
     *            the htype to set
     */
    public void setHtype(int htype)
    {
        this.htype = htype;
    }

    /**
     * @return the bakId
     */
    public int getBakId()
    {
        return bakId;
    }

    /**
     * @param bakId
     *            the bakId to set
     */
    public void setBakId(int bakId)
    {
        this.bakId = bakId;
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
     * @return the location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * @return the dues
     */
    public int getDues()
    {
        return dues;
    }

    /**
     * @param dues
     *            the dues to set
     */
    public void setDues(int dues)
    {
        this.dues = dues;
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
            .append("AbstractProviderBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("connector = ")
            .append(this.connector)
            .append(TAB)
            .append("address = ")
            .append(this.address)
            .append(TAB)
            .append("phone = ")
            .append(this.phone)
            .append(TAB)
            .append("tel = ")
            .append(this.tel)
            .append(TAB)
            .append("fax = ")
            .append(this.fax)
            .append(TAB)
            .append("qq = ")
            .append(this.qq)
            .append(TAB)
            .append("email = ")
            .append(this.email)
            .append(TAB)
            .append("bank = ")
            .append(this.bank)
            .append(TAB)
            .append("accounts = ")
            .append(this.accounts)
            .append(TAB)
            .append("location = ")
            .append(this.location)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("dues = ")
            .append(this.dues)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("htype = ")
            .append(this.htype)
            .append(TAB)
            .append("bakId = ")
            .append(this.bakId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
