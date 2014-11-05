package com.china.center.oa.extsail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.DepotBean;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_ZJRCBASE")
public class ZJRCBaseBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String outId = "";
    
	/**
     * 紫金农商ID 与 OA ID 关联关系。约定一个产品一笔订单
     * 
     * 生成OA订单时回填
     */
    private String oaNo = "";
    
    private String productId = "";

    private String productName = "";
    
    /**
     * ZJRC 产品配置的ID
     */
    private String zjrcProductId = "";
    
    /**
     * ZJRC 的品名
     */
    private String zjrcProductName = "";

    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String locationId = "";

    private int amount = 0;

    /**
     * 产品所在的仓区
     */
    private String depotpartId = "";

    private String depotpartName = "";

    /**
     * 业务员销售价格
     */
    private double price = 0.0d;


    /**
     * 成本价
     */
    private double costPrice = 0.0d;

    /**
     * 中收价
     */
    private double midRevenue = 0.0d;

    /**
     * 总销售价
     */
    private double value = 0.0d;

    /**
     * 成本(和costPrice一样)
     */
    private String description = "";
    
    /**
	 * 处理状态：0-未处理；1-已处理
	 */
	private int pstatus = 0;

    /**
     * default constructor
     */
    public ZJRCBaseBean()
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
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
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
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId()
	{
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId)
	{
		this.locationId = locationId;
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
	 * @return the depotpartId
	 */
	public String getDepotpartId()
	{
		return depotpartId;
	}

	/**
	 * @param depotpartId the depotpartId to set
	 */
	public void setDepotpartId(String depotpartId)
	{
		this.depotpartId = depotpartId;
	}

	/**
	 * @return the depotpartName
	 */
	public String getDepotpartName()
	{
		return depotpartName;
	}

	/**
	 * @param depotpartName the depotpartName to set
	 */
	public void setDepotpartName(String depotpartName)
	{
		this.depotpartName = depotpartName;
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
	 * @return the oaNo
	 */
	public String getOaNo()
	{
		return oaNo;
	}

	/**
	 * @param oaNo the oaNo to set
	 */
	public void setOaNo(String oaNo)
	{
		this.oaNo = oaNo;
	}

	/**
	 * @return the zjrcProductName
	 */
	public String getZjrcProductName()
	{
		return zjrcProductName;
	}

	/**
	 * @param zjrcProductName the zjrcProductName to set
	 */
	public void setZjrcProductName(String zjrcProductName)
	{
		this.zjrcProductName = zjrcProductName;
	}

	/**
	 * @return the zjrcProductId
	 */
	public String getZjrcProductId()
	{
		return zjrcProductId;
	}

	/**
	 * @param zjrcProductId the zjrcProductId to set
	 */
	public void setZjrcProductId(String zjrcProductId)
	{
		this.zjrcProductId = zjrcProductId;
	}

	/**
	 * @return the pstatus
	 */
	public int getPstatus()
	{
		return pstatus;
	}

	/**
	 * @param pstatus the pstatus to set
	 */
	public void setPstatus(int pstatus)
	{
		this.pstatus = pstatus;
	}
}
