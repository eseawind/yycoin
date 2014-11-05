package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.customer.constant.CustomerConstant;

@SuppressWarnings("serial")
@Entity(inherit = true)
public abstract class AbstractCustomerCorporationBean extends AbstractCustomerBean
{
	/**
	 * 1.个人  2.企业 3.部门
	 */
	@Html(title = "客户性质", must = true, type = Element.SELECT)
	private int type = CustomerConstant.NATURE_CORPORATION;
	
	@Html(title = "营业执照号", maxLength = 100)
	private String licenseNo;

	@Html(title = "注册资金", maxLength = 20, type = Element.DOUBLE, oncheck = JCheck.ONLY_FLOAT)
	private double registryMoney = 0.0d;;

	@Html(title = "注册地址", maxLength = 200)
	private String registryAddress = "";

	@Html(title = "营业地址", maxLength = 200)
	private String businessaddress = "";

	@Html(title = "资产总额", maxLength = 20, type = Element.DOUBLE, oncheck = JCheck.ONLY_FLOAT)
	private double assets = 0.0d;;

	@Html(title = "成立时间", type = Element.DATE)
	private String establishDate;

	@Html(title = "上年度销售额", maxLength = 20, type = Element.DOUBLE, oncheck = JCheck.ONLY_FLOAT)
	private double lastYearSales = 0.0d;;

	@Html(title = "本年度预计销售额", maxLength = 20, type = Element.DOUBLE, oncheck = JCheck.ONLY_FLOAT)
	private double thisYearSales = 0.0d;;

	@Html(title = "员工数量", maxLength = 20,type = Element.NUMBER, oncheck = JCheck.ONLY_NUMBER)
	private int employeeAmount = 0;

	@Html(title = "下游经销商数量", maxLength = 20, type = Element.NUMBER, oncheck = JCheck.ONLY_NUMBER)
	private int sellerAmount = 0;

	@Html(title = "销售区域", maxLength = 40)
	private String saleArea = "";

	@Html(title = "上游代理商", maxLength = 40)
	private String agent = "";

	@Html(title = "代理区域", maxLength = 40)
	private String agentArea = "";
	
	@Html(title = "备注", maxLength = 300, type = Element.TEXTAREA)
	private String description = "";
	
	@Html(title = "预留1")
	private String reserve1 = "";

	@Html(title = "预留2")
	private String reserve2 = "";

	@Html(title = "预留3")
	private String reserve3 = "";

	@Html(title = "预留4")
	private String reserve4 = "";

	@Html(title = "预留5")
	private String reserve5 = "";

	@Html(title = "预留6")
	private String reserve6 = "";

	@Html(title = "预留7")
	private String reserve7 = "";

	@Html(title = "预留8")
	private String reserve8 = "";

	@Html(title = "预留9")
	private String reserve9 = "";

	@Html(title = "预留10")
	private String reserve10 = "";

	public AbstractCustomerCorporationBean()
	{
	}

	public String getLicenseNo()
	{
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo)
	{
		this.licenseNo = licenseNo;
	}

	public double getRegistryMoney()
	{
		return registryMoney;
	}

	public void setRegistryMoney(double registryMoney)
	{
		this.registryMoney = registryMoney;
	}

	public String getRegistryAddress()
	{
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress)
	{
		this.registryAddress = registryAddress;
	}

	public String getBusinessaddress()
	{
		return businessaddress;
	}

	public void setBusinessaddress(String businessaddress)
	{
		this.businessaddress = businessaddress;
	}

	public double getAssets()
	{
		return assets;
	}

	public void setAssets(double assets)
	{
		this.assets = assets;
	}

	public String getEstablishDate()
	{
		return establishDate;
	}

	public void setEstablishDate(String establishDate)
	{
		this.establishDate = establishDate;
	}

	public double getLastYearSales()
	{
		return lastYearSales;
	}

	public void setLastYearSales(double lastYearSales)
	{
		this.lastYearSales = lastYearSales;
	}

	public double getThisYearSales()
	{
		return thisYearSales;
	}

	public void setThisYearSales(double thisYearSales)
	{
		this.thisYearSales = thisYearSales;
	}

	public int getEmployeeAmount()
	{
		return employeeAmount;
	}

	public void setEmployeeAmount(int employeeAmount)
	{
		this.employeeAmount = employeeAmount;
	}

	public int getSellerAmount()
	{
		return sellerAmount;
	}

	public void setSellerAmount(int sellerAmount)
	{
		this.sellerAmount = sellerAmount;
	}

	public String getSaleArea()
	{
		return saleArea;
	}

	public void setSaleArea(String saleArea)
	{
		this.saleArea = saleArea;
	}

	public String getAgent()
	{
		return agent;
	}

	public void setAgent(String agent)
	{
		this.agent = agent;
	}

	public String getAgentArea()
	{
		return agentArea;
	}

	public void setAgentArea(String agentArea)
	{
		this.agentArea = agentArea;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getReserve1()
	{
		return reserve1;
	}

	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}

	public String getReserve2()
	{
		return reserve2;
	}

	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}

	public String getReserve3()
	{
		return reserve3;
	}

	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}

	public String getReserve4()
	{
		return reserve4;
	}

	public void setReserve4(String reserve4)
	{
		this.reserve4 = reserve4;
	}

	public String getReserve5()
	{
		return reserve5;
	}

	public void setReserve5(String reserve5)
	{
		this.reserve5 = reserve5;
	}

	public String getReserve6()
	{
		return reserve6;
	}

	public void setReserve6(String reserve6)
	{
		this.reserve6 = reserve6;
	}

	public String getReserve7()
	{
		return reserve7;
	}

	public void setReserve7(String reserve7)
	{
		this.reserve7 = reserve7;
	}

	public String getReserve8()
	{
		return reserve8;
	}

	public void setReserve8(String reserve8)
	{
		this.reserve8 = reserve8;
	}

	public String getReserve9()
	{
		return reserve9;
	}

	public void setReserve9(String reserve9)
	{
		this.reserve9 = reserve9;
	}

	public String getReserve10()
	{
		return reserve10;
	}

	public void setReserve10(String reserve10)
	{
		this.reserve10 = reserve10;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}
}
