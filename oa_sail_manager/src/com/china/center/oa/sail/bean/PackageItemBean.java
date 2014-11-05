package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "发货单明细")
@Table(name = "T_CENTER_PACKAGE_ITEM")
public class PackageItemBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String packageId = "";
	
	@FK(index = AnoConstant.FK_FIRST)
	private String outId = "";
	
	private String baseId = "";
	
	private String productId = "";
	
	private String productName = "";
	
	private int amount = 0;
	
	private double price = 0.0d;
	
	private double value = 0.0d;
	
	private String outTime = "";
	
	private String description = "";
	
	private String customerId = "";
	
	/**
	 * 发票配送时 针对 银行 客户 记录  发票对应的商品明细，客户，销售单对应的银行订单号
	 */
	private String printText = "";
	
	/**
     * 销售单 紧急 标识 1:紧急
     */
    private int emergency = 0;
	
	/**
	 * 合成产品的子产品  <br>
	 */
	@Ignore
	private String showSubProductName = "";
	
	@Ignore
	private String refId = "";
	
	public PackageItemBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPackageId()
	{
		return packageId;
	}

	public void setPackageId(String packageId)
	{
		this.packageId = packageId;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public String getBaseId()
	{
		return baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getShowSubProductName()
	{
		return showSubProductName;
	}

	public void setShowSubProductName(String showSubProductName)
	{
		this.showSubProductName = showSubProductName;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the refId
	 */
	public String getRefId()
	{
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	/**
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/**
	 * @return the value
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value)
	{
		this.value = value;
	}

	/**
	 * @return the amount
	 */
	public int getAmount()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the outTime
	 */
	public String getOutTime()
	{
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the printText
	 */
	public String getPrintText() {
		return printText;
	}

	/**
	 * @param printText the printText to set
	 */
	public void setPrintText(String printText) {
		this.printText = printText;
	}

	/**
	 * @return the emergency
	 */
	public int getEmergency() {
		return emergency;
	}

	/**
	 * @param emergency the emergency to set
	 */
	public void setEmergency(int emergency) {
		this.emergency = emergency;
	}
}
