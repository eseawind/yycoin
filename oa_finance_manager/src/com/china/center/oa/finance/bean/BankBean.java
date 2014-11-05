/*
 * File Name: BankBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-16
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 银行
 * 
 * @author ZHUZHU
 * @version 2007-12-16
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_BANK")
public class BankBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "名称", must = true, maxLength = 40)
    private String name = "";

    /**
     * 帐号
     */
    @Html(title = "帐号", must = true, maxLength = 100)
    private String bankNo = "";

    /**
     * 暂时不使用
     */
    @Html(title = "类型", type = Element.SELECT)
    private int type = FinanceConstant.BANK_TYPE_NOTDUTY;

    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    @FK
    @Join(tagClass = DutyBean.class)
    @Html(title = "纳税实体", type = Element.SELECT)
    private String dutyId = "";

    /**
     * 生成科目所使用
     */
    @Ignore
    @Html(title = "银行科目编码", must = true, maxLength = 40)
    private String code = "";

    /**
     * 生成科目所使用
     */
    @Ignore
    @Html(title = "暂记户科目编码", must = true, maxLength = 40)
    private String code2 = "";

    /**
     * 生成科目所使用
     */
    @Ignore
    @Html(title = "科目类型", must = true, type = Element.SELECT)
    private int taxType = 0;

    @Ignore
    private String parentTaxId = "";

    @Ignore
    private String parentTaxId2 = "";

    @Ignore
    private int unit = 0;

    @Ignore
    private int department = 0;

    @Ignore
    private int staffer = 0;

    @Ignore
    private int depot = 0;

    @Ignore
    private int product = 0;

    @Ignore
    private int duty = 0;

    /**
     * 余额
     */
    private double total = 0.0d;

    @Html(title = "备注", maxLength = 100, type = Element.TEXTAREA)
    private String description = "";

    /**
     * default constructor
     */
    public BankBean()
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
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
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
     * @return the unit
     */
    public int getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(int unit)
    {
        this.unit = unit;
    }

    /**
     * @return the department
     */
    public int getDepartment()
    {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(int department)
    {
        this.department = department;
    }

    /**
     * @return the staffer
     */
    public int getStaffer()
    {
        return staffer;
    }

    /**
     * @param staffer
     *            the staffer to set
     */
    public void setStaffer(int staffer)
    {
        this.staffer = staffer;
    }

    /**
     * @return the parentTaxId
     */
    public String getParentTaxId()
    {
        return parentTaxId;
    }

    /**
     * @param parentTaxId
     *            the parentTaxId to set
     */
    public void setParentTaxId(String parentTaxId)
    {
        this.parentTaxId = parentTaxId;
    }

    /**
     * @return the code2
     */
    public String getCode2()
    {
        return code2;
    }

    /**
     * @param code2
     *            the code2 to set
     */
    public void setCode2(String code2)
    {
        this.code2 = code2;
    }

    /**
     * @return the parentTaxId2
     */
    public String getParentTaxId2()
    {
        return parentTaxId2;
    }

    /**
     * @param parentTaxId2
     *            the parentTaxId2 to set
     */
    public void setParentTaxId2(String parentTaxId2)
    {
        this.parentTaxId2 = parentTaxId2;
    }

    /**
     * @return the depot
     */
    public int getDepot()
    {
        return depot;
    }

    /**
     * @param depot
     *            the depot to set
     */
    public void setDepot(int depot)
    {
        this.depot = depot;
    }

    /**
     * @return the product
     */
    public int getProduct()
    {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(int product)
    {
        this.product = product;
    }

    /**
     * @return the duty
     */
    public int getDuty()
    {
        return duty;
    }

    /**
     * @param duty
     *            the duty to set
     */
    public void setDuty(int duty)
    {
        this.duty = duty;
    }

    /**
     * @return the taxType
     */
    public int getTaxType()
    {
        return taxType;
    }

    /**
     * @param taxType
     *            the taxType to set
     */
    public void setTaxType(int taxType)
    {
        this.taxType = taxType;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("BankBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(TAB).append(
            "name = ").append(this.name).append(TAB).append("bankNo = ").append(this.bankNo).append(TAB).append(
            "type = ").append(this.type).append(TAB).append("mtype = ").append(this.mtype).append(TAB).append(
            "dutyId = ").append(this.dutyId).append(TAB).append("code = ").append(this.code).append(TAB).append(
            "code2 = ").append(this.code2).append(TAB).append("taxType = ").append(this.taxType).append(TAB).append(
            "parentTaxId = ").append(this.parentTaxId).append(TAB).append("parentTaxId2 = ").append(this.parentTaxId2).append(
            TAB).append("unit = ").append(this.unit).append(TAB).append("department = ").append(this.department).append(
            TAB).append("staffer = ").append(this.staffer).append(TAB).append("depot = ").append(this.depot).append(TAB).append(
            "product = ").append(this.product).append(TAB).append("duty = ").append(this.duty).append(TAB).append(
            "total = ").append(this.total).append(TAB).append("description = ").append(this.description).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
