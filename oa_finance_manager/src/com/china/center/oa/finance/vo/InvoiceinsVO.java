/**
 * File Name: InvoiceinsVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.InvoiceinsBean;


/**
 * InvoiceinsVO
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsVO
 * @since 3.0
 */
@Entity(inherit = true)
public class InvoiceinsVO extends InvoiceinsBean
{
    @Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "processer")
    private String processName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";
    
    @Relationship(relationField = "invoiceId", tagField = "val")
    private double val = 0.0d;
    
    /**
	 * 省
	 */
	@Relationship(relationField = "provinceId")
	private String provinceName = "";
	
	/**
	 * 市
	 */
	@Relationship(relationField = "cityId")	
	private String cityName = "";
	
	/**
	 * 区
	 */
	@Relationship(relationField = "areaId")
	private String areaName = "";
	
	/**
	 * 运输方式1
	 */
	@Relationship(relationField = "transport1")
	private String transportName1 = "";
	
	/**
	 * 运输方式2
	 */
	@Relationship(relationField = "transport2")
	private String transportName2 = "";	
	
	@Ignore
	private String shippingName = "";
    
    /**
     * default constructor
     */
    public InvoiceinsVO()
    {
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
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    /**
     * @return the processName
     */
    public String getProcessName()
    {
        return processName;
    }

    /**
     * @param processName
     *            the processName to set
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }
    
	/**
	 * @return the val
	 */
	public double getVal()
	{
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(double val)
	{
		this.val = val;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the transportName1
	 */
	public String getTransportName1() {
		return transportName1;
	}

	/**
	 * @param transportName1 the transportName1 to set
	 */
	public void setTransportName1(String transportName1) {
		this.transportName1 = transportName1;
	}

	/**
	 * @return the transportName2
	 */
	public String getTransportName2() {
		return transportName2;
	}

	/**
	 * @param transportName2 the transportName2 to set
	 */
	public void setTransportName2(String transportName2) {
		this.transportName2 = transportName2;
	}

	/**
	 * @return the shippingName
	 */
	public String getShippingName() {
		return shippingName;
	}

	/**
	 * @param shippingName the shippingName to set
	 */
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
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
            .append("InvoiceinsVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("invoiceName = ")
            .append(this.invoiceName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("processName = ")
            .append(this.processName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
