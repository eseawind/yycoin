package com.china.center.oa.tcp.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;

/**
 * 
 * 出库日批价
 * 批量导入，一段一段时间的导入
 *
 * @author fangliwen 2013-9-3
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_OUTBATCHPRICE")
public class OutBatchPriceBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String beginDate = "";
	
	private String endDate = "";
	
	@Unique(dependFields = {"industryId","endDate"})
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT)
	private String productId = "";
	
	@Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
	private String industryId = "";
	
	private double price  = 0.0d;
	
	private String logTime = "";

	/**
	 * 
	 */
	public OutBatchPriceBean()
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
	 * @return the beginDate
	 */
	public String getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
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
	 * @return the industryId
	 */
	public String getIndustryId()
	{
		return industryId;
	}

	/**
	 * @param industryId the industryId to set
	 */
	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}
}
