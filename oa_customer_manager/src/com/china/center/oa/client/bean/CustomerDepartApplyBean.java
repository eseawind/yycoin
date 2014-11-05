package com.china.center.oa.client.bean;

import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;

@SuppressWarnings("serial")
@Entity(name = "企业中部门客户", inherit = true)
@Table(name = "T_CENTER_CUSTOMER_DEPART_APPLY")
public class CustomerDepartApplyBean extends AbstractCustomerDepartBean
{
	@Id
	private String id = "";

	@Unique
	@Html(title = "部门名称", must = true, maxLength = 100)
	private String name = "";
	
	private String updateId = "";
	
	private String updateTime = "";

	@Ignore
	@Html(title = "历史成交", type = Element.SELECT)
	private int historySales;

	@Ignore
	@Html(title = "交易金额", type = Element.NUMBER)
	private double salesAmount = 0.0d;
	
	@Ignore
	@Html(title = "联系次数", type = Element.NUMBER)
	private int contactTimes = 0;

	@Ignore
	@Html(title = "最近联系时间")
	private String lastContactTime = "";
	
	@Ignore
	private List<CustomerContactApplyBean> custContList = null;

	@Ignore
	private List<CustomerBusinessApplyBean> custBusiList = null;
	
	@Ignore
	private List<CustomerDistAddrApplyBean> custAddrList = null;
	
	/**
	 * 
	 */
	public CustomerDepartApplyBean()
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the updateId
	 */
	public String getUpdateId()
	{
		return updateId;
	}

	/**
	 * @param updateId the updateId to set
	 */
	public void setUpdateId(String updateId)
	{
		this.updateId = updateId;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * @return the custContList
	 */
	public List<CustomerContactApplyBean> getCustContList()
	{
		return custContList;
	}

	/**
	 * @param custContList the custContList to set
	 */
	public void setCustContList(List<CustomerContactApplyBean> custContList)
	{
		this.custContList = custContList;
	}

	/**
	 * @return the custBusiList
	 */
	public List<CustomerBusinessApplyBean> getCustBusiList()
	{
		return custBusiList;
	}

	/**
	 * @param custBusiList the custBusiList to set
	 */
	public void setCustBusiList(List<CustomerBusinessApplyBean> custBusiList)
	{
		this.custBusiList = custBusiList;
	}

	/**
	 * @return the historySales
	 */
	public int getHistorySales()
	{
		return historySales;
	}

	/**
	 * @param historySales the historySales to set
	 */
	public void setHistorySales(int historySales)
	{
		this.historySales = historySales;
	}

	/**
	 * @return the salesAmount
	 */
	public double getSalesAmount()
	{
		return salesAmount;
	}

	/**
	 * @param salesAmount the salesAmount to set
	 */
	public void setSalesAmount(double salesAmount)
	{
		this.salesAmount = salesAmount;
	}

	/**
	 * @return the contactTimes
	 */
	public int getContactTimes()
	{
		return contactTimes;
	}

	/**
	 * @param contactTimes the contactTimes to set
	 */
	public void setContactTimes(int contactTimes)
	{
		this.contactTimes = contactTimes;
	}

	/**
	 * @return the lastContactTime
	 */
	public String getLastContactTime()
	{
		return lastContactTime;
	}

	/**
	 * @param lastContactTime the lastContactTime to set
	 */
	public void setLastContactTime(String lastContactTime)
	{
		this.lastContactTime = lastContactTime;
	}

	/**
	 * @return the custAddrList
	 */
	public List<CustomerDistAddrApplyBean> getCustAddrList()
	{
		return custAddrList;
	}

	/**
	 * @param custAddrList the custAddrList to set
	 */
	public void setCustAddrList(List<CustomerDistAddrApplyBean> custAddrList)
	{
		this.custAddrList = custAddrList;
	}
}
