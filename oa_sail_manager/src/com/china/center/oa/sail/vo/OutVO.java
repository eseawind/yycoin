/**
 * 文 件 名: OutVO.java <br>
 * 版 权: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * 描 述: <描述> <br>
 * 修 改 人: admin <br>
 * 修改时间: 2008-1-5 <br>
 * 跟踪单号: <跟踪单号> <br>
 * 修改单号: <修改单号> <br>
 * 修改内容: <修改内容> <br>
 */
package com.china.center.oa.sail.vo;


import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.OutBean;


/**
 * Out的展现
 * 
 * @author ZHUZHU
 * @version
 * @see
 * @since 1.0
 */
@Entity(inherit = true)
public class OutVO extends OutBean
{
    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Relationship(relationField = "customerId", tagField = "code")
    private String customerCode = "";

    /**
     * 源仓库
     */
    @Relationship(relationField = "location")
    private String depotName = "";

    /**
     * 入库单的时候,目的仓库
     */
    @Relationship(relationField = "destinationId")
    private String destinationName = "";

    @Relationship(relationField = "depotpartId")
    private String depotpartName = "";

    /**
     * 送货地址
     */
    @Ignore
    private String customerAddress = "";

    @Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Relationship(relationField = "industryId")
    private String industryName = "";

    @Relationship(relationField = "industryId2")
    private String industryName2 = "";

    @Relationship(relationField = "industryId3")
    private String industryName3 = "";
    
    @Relationship(relationField = "reserve9")
    private String stafferName1 = "";
    
    @Relationship(relationField = "eventId")
    private String eventName = "";
    
    @Relationship(relationField = "guarantor")
    private String guarantorName = "";

    /**
     * 退货金额
     */
    @Ignore
    private double retTotal = 0.0d;   
    
    /**
     * 可开票金额
     */
    @Ignore
    private double mayInvoiceMoneys = 0.0d;
    
    @Ignore
    private double mayConfirmMoney = 0.0d;
    
    @Ignore
    private String origId = "";
    
    @Ignore
    private int newReday = 0;
    
    @Ignore
    private String newDutyId = "";
    
    @Ignore
    private String newInvoiceId = "";
    
    @Ignore
    private String pdescription = "";
    
    public OutVO()
    {
    }

    /**
     * @return 返回 locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param 对locationName进行赋值
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /**
     * @return the destinationName
     */
    public String getDestinationName()
    {
        return destinationName;
    }

    /**
     * @param destinationName
     *            the destinationName to set
     */
    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
    }

    /**
     * @return the depotpartName
     */
    public String getDepotpartName()
    {
        return depotpartName;
    }

    /**
     * @param depotpartName
     *            the depotpartName to set
     */
    public void setDepotpartName(String depotpartName)
    {
        this.depotpartName = depotpartName;
    }

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress()
    {
        return customerAddress;
    }

    /**
     * @param customerAddress
     *            the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress)
    {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the depotName
     */
    public String getDepotName()
    {
        return depotName;
    }

    /**
     * @param depotName
     *            the depotName to set
     */
    public void setDepotName(String depotName)
    {
        this.depotName = depotName;
    }

    /**
     * @return the invoiceName
     */
    public String getInvoiceName()
    {
        return invoiceName;
    }

    /**
     * @param invoiceName
     *            the invoiceName to set
     */
    public void setInvoiceName(String invoiceName)
    {
        this.invoiceName = invoiceName;
    }

    /**
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
    }

    /**
     * @return the industryName
     */
    public String getIndustryName()
    {
        return industryName;
    }

    /**
     * @param industryName
     *            the industryName to set
     */
    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    /**
     * @return the industryName2
     */
    public String getIndustryName2()
    {
        return industryName2;
    }

    /**
     * @param industryName2
     *            the industryName2 to set
     */
    public void setIndustryName2(String industryName2)
    {
        this.industryName2 = industryName2;
    }

    /**
     * @return the industryName3
     */
    public String getIndustryName3()
    {
        return industryName3;
    }

    /**
     * @param industryName3
     *            the industryName3 to set
     */
    public void setIndustryName3(String industryName3)
    {
        this.industryName3 = industryName3;
    }

    /**
     * @return the customerCode
     */
    public String getCustomerCode()
    {
        return customerCode;
    }

    /**
     * @param customerCode
     *            the customerCode to set
     */
    public void setCustomerCode(String customerCode)
    {
        this.customerCode = customerCode;
    }

    public String getStafferName1() {
        return stafferName1;
    }

    public void setStafferName1(String stafferName1) {
        this.stafferName1 = stafferName1;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getRetTotal()
	{
		return retTotal;
	}

	public void setRetTotal(double retTotal)
	{
		this.retTotal = retTotal;
	}

	public double getMayInvoiceMoneys()
	{
		return mayInvoiceMoneys;
	}

	public void setMayInvoiceMoneys(double mayInvoiceMoneys)
	{
		this.mayInvoiceMoneys = mayInvoiceMoneys;
	}

	public int getNewReday()
	{
		return newReday;
	}

	public void setNewReday(int newReday)
	{
		this.newReday = newReday;
	}

	public String getNewDutyId()
	{
		return newDutyId;
	}

	public void setNewDutyId(String newDutyId)
	{
		this.newDutyId = newDutyId;
	}

	public String getNewInvoiceId()
	{
		return newInvoiceId;
	}

	public void setNewInvoiceId(String newInvoiceId)
	{
		this.newInvoiceId = newInvoiceId;
	}

	/**
	 * @return the guarantorName
	 */
	public String getGuarantorName()
	{
		return guarantorName;
	}

	/**
	 * @param guarantorName the guarantorName to set
	 */
	public void setGuarantorName(String guarantorName)
	{
		this.guarantorName = guarantorName;
	}

	/**
	 * @return the mayConfirmMoney
	 */
	public double getMayConfirmMoney()
	{
		return mayConfirmMoney;
	}

	/**
	 * @param mayConfirmMoney the mayConfirmMoney to set
	 */
	public void setMayConfirmMoney(double mayConfirmMoney)
	{
		this.mayConfirmMoney = mayConfirmMoney;
	}

	/**
	 * @return the origId
	 */
	public String getOrigId()
	{
		return origId;
	}

	/**
	 * @param origId the origId to set
	 */
	public void setOrigId(String origId)
	{
		this.origId = origId;
	}

	/**
	 * @return the pdescription
	 */
	public String getPdescription()
	{
		return "管理属性：" + DefinedCommon.getValue("pubManagerType", this.getPiMtype()) + 
		",发生： " + DefinedCommon.getValue("outPayInsType", this.getPiType()) + 
		", 状态为：" + DefinedCommon.getValue("outPayInsStatus", this.getPiStatus());
	}

	/**
	 * @param pdescription the pdescription to set
	 */
	public void setPdescription(String pdescription)
	{
		this.pdescription = pdescription;
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
            .append("OutVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("customerCode = ")
            .append(this.customerCode)
            .append(TAB)
            .append("depotName = ")
            .append(this.depotName)
            .append(TAB)
            .append("destinationName = ")
            .append(this.destinationName)
            .append(TAB)
            .append("depotpartName = ")
            .append(this.depotpartName)
            .append(TAB)
            .append("customerAddress = ")
            .append(this.customerAddress)
            .append(TAB)
            .append("invoiceName = ")
            .append(this.invoiceName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("industryName2 = ")
            .append(this.industryName2)
            .append(TAB)
            .append("industryName3 = ")
            .append(this.industryName3)
            .append(TAB)
            .append("stafferName1 = ")
            .append(this.stafferName1)   
            .append(TAB)
            .append("eventName = ")
            .append(this.eventName)            
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
