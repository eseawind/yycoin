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
@Entity(name = "个人客户", inherit = true)
@Table(name = "T_CENTER_CUSTOMER_INDIVIDUAL")
public class CustomerIndividualBean extends AbstractCustomerIndividualBean
{
	@Id
	private String id = "";

	@Unique
	@Html(title = "姓名", must = true, maxLength = 100)
	private String name = "";
	
	/**
	 * 数据来源主表 main
	 */
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
	private List<CustomerContactBean> custContList = null;

	@Ignore
	private List<CustomerBusinessBean> custBusiList = null;
	
	@Ignore
	private List<CustomerDistAddrBean> custAddrList = null;
	
	public CustomerIndividualBean()
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
	 * @return the custContList
	 */
	public List<CustomerContactBean> getCustContList()
	{
		return custContList;
	}

	/**
	 * @param custContList the custContList to set
	 */
	public void setCustContList(List<CustomerContactBean> custContList)
	{
		this.custContList = custContList;
	}

	/**
	 * @return the custBusiList
	 */
	public List<CustomerBusinessBean> getCustBusiList()
	{
		return custBusiList;
	}

	/**
	 * @param custBusiList the custBusiList to set
	 */
	public void setCustBusiList(List<CustomerBusinessBean> custBusiList)
	{
		this.custBusiList = custBusiList;
	}

	/**
	 * @return the custAddrList
	 */
	public List<CustomerDistAddrBean> getCustAddrList()
	{
		return custAddrList;
	}

	/**
	 * @param custAddrList the custAddrList to set
	 */
	public void setCustAddrList(List<CustomerDistAddrBean> custAddrList)
	{
		this.custAddrList = custAddrList;
	}
}
