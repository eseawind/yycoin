package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;

/**
 * 
 * 客户配送地址
 *
 * @author fangliwen 2013-11-28
 */
@SuppressWarnings("serial")
public abstract class AbstractCustomerDistAddrBean implements Serializable {

	@Html(title = "省")
	@Join(tagClass = ProvinceBean.class)
	protected String provinceId = "";

	@Html(title = "市")
	@Join(tagClass = CityBean.class)
	protected String cityId = "";

	@Html(title = "区")
	@Join(tagClass = AreaBean.class, type = JoinType.LEFT)
	protected String areaId = "";
	
	/** 详细地址 */
	protected String address = "";
	
	/** 完整的地址 */
	protected String fullAddress;

	@Html(title = "收货人")
	protected String contact;

	@Html(title = "收货人电话")
	protected String telephone;

	@Html(title = "地址性质")
	protected int atype;

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
	
	public AbstractCustomerDistAddrBean() {
		
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId()
	{
		return provinceId;
	}


	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}


	/**
	 * @return the cityId
	 */
	public String getCityId()
	{
		return cityId;
	}


	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}


	/**
	 * @return the areaId
	 */
	public String getAreaId()
	{
		return areaId;
	}


	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}


	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}


	/**
	 * @return the fullAddress
	 */
	public String getFullAddress()
	{
		return fullAddress;
	}


	/**
	 * @param fullAddress the fullAddress to set
	 */
	public void setFullAddress(String fullAddress)
	{
		this.fullAddress = fullAddress;
	}


	/**
	 * @return the contact
	 */
	public String getContact()
	{
		return contact;
	}


	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact)
	{
		this.contact = contact;
	}


	/**
	 * @return the telephone
	 */
	public String getTelephone()
	{
		return telephone;
	}


	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}


	/**
	 * @return the atype
	 */
	public int getAtype()
	{
		return atype;
	}


	/**
	 * @param atype the atype to set
	 */
	public void setAtype(int atype)
	{
		this.atype = atype;
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
