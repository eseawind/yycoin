package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity(name = "出库单对应的客户 ")
@Table(name = "T_CENTER_VS_PACKCUST")
public class PackageVSCustomerBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	@Unique(dependFields = "customerId")
	private String packageId = "";
	
	private String customerId = "";
	
	private String customerName = "";
	
	/**
	 * 辅助连续打印
	 */
	private int indexPos = 0;

	public PackageVSCustomerBean()
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
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the packageId
	 */
	public String getPackageId()
	{
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(String packageId)
	{
		this.packageId = packageId;
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
	 * @return the indexPos
	 */
	public int getIndexPos()
	{
		return indexPos;
	}

	/**
	 * @param indexPos the indexPos to set
	 */
	public void setIndexPos(int indexPos)
	{
		this.indexPos = indexPos;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
}
