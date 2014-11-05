package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;

/**
 * 
 * 财务往来
 *
 * @author fangliwen 2013-11-28
 */
@SuppressWarnings("serial")
public abstract class AbstractCustomerBusinessBean implements Serializable {

	@Html(title = "客户账号类型")
	protected int custAccountType;

	@Html(title = "客户账号开户行")
	protected String custAccountBank;

	@Html(title = "客户账号开户名")
	protected String custAccountName;

	@Html(title = "客户账号")
	protected String custAccount;

	@Html(title = "我司账号类型")
	protected int myAccountType;

	@Html(title = "我司账号开户行")
	protected String myAccountBank;

	@Html(title = "我司账号开户名")
	protected String myAccountName;

	@Html(title = "我司账号")
	protected String myAccount;

	/**
	 * 0 有效；1 无效（逻辑删除）
	 */
	protected int valid = 0;
	
	@Html(title = "预留1")
	protected String reserve1 = "";

	@Html(title = "预留2")
	protected String reserve2 = "";

	@Html(title = "预留3")
	protected String reserve3 = "";

	@Html(title = "预留4")
	protected String reserve4 = "";

	@Html(title = "预留5")
	protected String reserve5 = "";

	@Html(title = "预留6")
	protected String reserve6 = "";

	@Html(title = "预留7")
	protected String reserve7 = "";

	@Html(title = "预留8")
	protected String reserve8 = "";

	@Html(title = "预留9")
	protected String reserve9 = "";

	@Html(title = "预留10")
	protected String reserve10 = "";
	
	public AbstractCustomerBusinessBean() {
		
	}

	/**
	 * @return the custAccountType
	 */
	public int getCustAccountType()
	{
		return custAccountType;
	}

	/**
	 * @param custAccountType the custAccountType to set
	 */
	public void setCustAccountType(int custAccountType)
	{
		this.custAccountType = custAccountType;
	}

	/**
	 * @return the custAccountBank
	 */
	public String getCustAccountBank()
	{
		return custAccountBank;
	}

	/**
	 * @param custAccountBank the custAccountBank to set
	 */
	public void setCustAccountBank(String custAccountBank)
	{
		this.custAccountBank = custAccountBank;
	}

	/**
	 * @return the custAccountName
	 */
	public String getCustAccountName()
	{
		return custAccountName;
	}

	/**
	 * @param custAccountName the custAccountName to set
	 */
	public void setCustAccountName(String custAccountName)
	{
		this.custAccountName = custAccountName;
	}

	/**
	 * @return the custAccount
	 */
	public String getCustAccount()
	{
		return custAccount;
	}

	/**
	 * @param custAccount the custAccount to set
	 */
	public void setCustAccount(String custAccount)
	{
		this.custAccount = custAccount;
	}

	/**
	 * @return the myAccountType
	 */
	public int getMyAccountType()
	{
		return myAccountType;
	}

	/**
	 * @param myAccountType the myAccountType to set
	 */
	public void setMyAccountType(int myAccountType)
	{
		this.myAccountType = myAccountType;
	}

	/**
	 * @return the myAccountBank
	 */
	public String getMyAccountBank()
	{
		return myAccountBank;
	}

	/**
	 * @param myAccountBank the myAccountBank to set
	 */
	public void setMyAccountBank(String myAccountBank)
	{
		this.myAccountBank = myAccountBank;
	}

	/**
	 * @return the myAccountName
	 */
	public String getMyAccountName()
	{
		return myAccountName;
	}

	/**
	 * @param myAccountName the myAccountName to set
	 */
	public void setMyAccountName(String myAccountName)
	{
		this.myAccountName = myAccountName;
	}

	/**
	 * @return the myAccount
	 */
	public String getMyAccount()
	{
		return myAccount;
	}

	/**
	 * @param myAccount the myAccount to set
	 */
	public void setMyAccount(String myAccount)
	{
		this.myAccount = myAccount;
	}

	/**
	 * @return the valid
	 */
	public int getValid()
	{
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(int valid)
	{
		this.valid = valid;
	}

	/**
	 * @return the reserve1
	 */
	public String getReserve1()
	{
		return reserve1;
	}

	/**
	 * @param reserve1 the reserve1 to set
	 */
	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}

	/**
	 * @return the reserve2
	 */
	public String getReserve2()
	{
		return reserve2;
	}

	/**
	 * @param reserve2 the reserve2 to set
	 */
	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}

	/**
	 * @return the reserve3
	 */
	public String getReserve3()
	{
		return reserve3;
	}

	/**
	 * @param reserve3 the reserve3 to set
	 */
	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}

	/**
	 * @return the reserve4
	 */
	public String getReserve4()
	{
		return reserve4;
	}

	/**
	 * @param reserve4 the reserve4 to set
	 */
	public void setReserve4(String reserve4)
	{
		this.reserve4 = reserve4;
	}

	/**
	 * @return the reserve5
	 */
	public String getReserve5()
	{
		return reserve5;
	}

	/**
	 * @param reserve5 the reserve5 to set
	 */
	public void setReserve5(String reserve5)
	{
		this.reserve5 = reserve5;
	}

	/**
	 * @return the reserve6
	 */
	public String getReserve6()
	{
		return reserve6;
	}

	/**
	 * @param reserve6 the reserve6 to set
	 */
	public void setReserve6(String reserve6)
	{
		this.reserve6 = reserve6;
	}

	/**
	 * @return the reserve7
	 */
	public String getReserve7()
	{
		return reserve7;
	}

	/**
	 * @param reserve7 the reserve7 to set
	 */
	public void setReserve7(String reserve7)
	{
		this.reserve7 = reserve7;
	}

	/**
	 * @return the reserve8
	 */
	public String getReserve8()
	{
		return reserve8;
	}

	/**
	 * @param reserve8 the reserve8 to set
	 */
	public void setReserve8(String reserve8)
	{
		this.reserve8 = reserve8;
	}

	/**
	 * @return the reserve9
	 */
	public String getReserve9()
	{
		return reserve9;
	}

	/**
	 * @param reserve9 the reserve9 to set
	 */
	public void setReserve9(String reserve9)
	{
		this.reserve9 = reserve9;
	}

	/**
	 * @return the reserve10
	 */
	public String getReserve10()
	{
		return reserve10;
	}

	/**
	 * @param reserve10 the reserve10 to set
	 */
	public void setReserve10(String reserve10)
	{
		this.reserve10 = reserve10;
	}

	public String toString() {
		System.out.println("22");
		return "";
	}
}
