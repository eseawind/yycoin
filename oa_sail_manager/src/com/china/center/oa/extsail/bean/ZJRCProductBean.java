package com.china.center.oa.extsail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;

/**
 * 紫金农商产品
 * @author smart
 *
 */
@SuppressWarnings("serial")
@Entity(name = "紫金农行产品")
@Table(name = "T_CENTER_ZJRCPRODUCT")
public class ZJRCProductBean implements Serializable
{
	@Id
	private String id = "";
	
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT)
	@Html(name = "productName", title = "OA产品名", must = true)
	private String productId = "";
	
	/**
	 * 显示紫金农商的产品名
	 */
	@Unique
	@Html(title = "开单产品名", must = true, maxLength = 100)
	private String zjrProductName = "";
	
	/**
	 * 销售价
	 */
	@Html(title = "销售价", must = true, type = Element.DOUBLE)
	private double price = 0.0d;
	
	/**
	 * 供货价
	 */
	@Html(title = "供货价", must = true, type = Element.DOUBLE)
	private double costPrice = 0.0d;
	
	/**
	 * 中收
	 */
	@Html(title = "中收", must = true, type = Element.DOUBLE)
	private double midRevenue = 0.0d;
	
	private String stafferId = "";
	
	@Html(title = "业务员")
	private String stafferName = "";
	
	@Html(title = "时间")
	private String logTime = "";

	public ZJRCProductBean()
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
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the zjrProductName
	 */
	public String getZjrProductName()
	{
		return zjrProductName;
	}

	/**
	 * @param zjrProductName the zjrProductName to set
	 */
	public void setZjrProductName(String zjrProductName)
	{
		this.zjrProductName = zjrProductName;
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
	 * @return the costPrice
	 */
	public double getCostPrice()
	{
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(double costPrice)
	{
		this.costPrice = costPrice;
	}

	/**
	 * @return the midRevenue
	 */
	public double getMidRevenue()
	{
		return midRevenue;
	}

	/**
	 * @param midRevenue the midRevenue to set
	 */
	public void setMidRevenue(double midRevenue)
	{
		this.midRevenue = midRevenue;
	}

	/**
	 * @return the stafferId
	 */
	public String getStafferId()
	{
		return stafferId;
	}

	/**
	 * @param stafferId the stafferId to set
	 */
	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
}
