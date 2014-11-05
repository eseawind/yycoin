/**
 * File Name: AbstractBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;

/**
 * AbstractCustomerBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see AbstractCustomerBean
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractCustomerBean implements Serializable 
{
	@Html(title = "客户编码", must = true, maxLength = 40, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
	private String code = "";

	@Html(title = "省", must = true, type = Element.SELECT)
	@Join(tagClass = ProvinceBean.class)
	private String provinceId = "";

	@Html(title = "市", must = true, type = Element.SELECT)
	@Join(tagClass = CityBean.class)
	private String cityId = "";

	@Html(title = "区", type = Element.SELECT, must = true)
	@Join(tagClass = AreaBean.class, type = JoinType.LEFT)
	private String areaId = "";

	@Html(title = "简称", maxLength = 100)
	private String simpleName = "";

	@Html(title = "地址", maxLength = 200)
	private String address = "";

	/**
	 * 101
	 */
	@Html(title = "客户类型", must = true, type = Element.SELECT)
	private int selltype = CustomerConstant.SELLTYPE_TER;

	/**
	 * 102
	 */
	@Html(title = "客户分类1", must = true, type = Element.SELECT)
	private int protype = 0;

	/**
	 * 103 -- 之前客户用的字段newtype
	 */
	@Html(title = "客户分类2", must = true, type = Element.SELECT)
	private int protype2 = 0;

    /**
     * 104
     */
    @Html(title = "客户等级", must = true, type = Element.SELECT)
    private int qqtype = 0;
	
	/**
	 * 105
	 */
	@Html(title = "开发进程", must = true, type = Element.SELECT)
	private int rtype;

	/**
	 * 106
	 */
	@Html(title = "客户来源", must = true, type = Element.SELECT)
	private int fromType;
	
	/**
	 * 介绍人
	 */
	@Html(title = "介绍人", maxLength = 40, type = Element.INPUT)
	private String introducer = "";

	/**
	 * 108
	 */
	@Html(title = "行业", must = true, type = Element.SELECT)
	private int industry;

	@Html(title = "所属组织", maxLength = 40)
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT, alias = "C1")
	private String refCorpCustId = "";
	
	@Html(title = "所属部门", maxLength = 40)
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT, alias = "C2")
	private String refDepartCustId = "";
	
	/**
	 * 曾用名，以 ; 分隔
	 */
	@Html(name = "formerCustName", title = "曾用名", maxLength = 300, type = Element.TEXTAREA)
	private String formerCust = "";
	
	@Ignore
	private String formerCustName = "";
	
    private String createrId = "";
    
    /**
     * 创建时间
     */
    private String logTime = "";
    
	/**
	 * default constructor
	 */
	public AbstractCustomerBean() {
	}
	
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public String getCityId()
	{
		return cityId;
	}

	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}

	public String getAreaId()
	{
		return areaId;
	}

	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}

	public String getSimpleName()
	{
		return simpleName;
	}

	public void setSimpleName(String simpleName)
	{
		this.simpleName = simpleName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the selltype
	 */
	public int getSelltype()
	{
		return selltype;
	}

	/**
	 * @param selltype the selltype to set
	 */
	public void setSelltype(int selltype)
	{
		this.selltype = selltype;
	}

	public int getProtype()
	{
		return protype;
	}

	public void setProtype(int protype)
	{
		this.protype = protype;
	}

	public int getProtype2()
	{
		return protype2;
	}

	public void setProtype2(int protype2)
	{
		this.protype2 = protype2;
	}

	public int getFromType()
	{
		return fromType;
	}

	public void setFromType(int fromType)
	{
		this.fromType = fromType;
	}

	public String getIntroducer()
	{
		return introducer;
	}

	public void setIntroducer(String introducer)
	{
		this.introducer = introducer;
	}

	public int getIndustry()
	{
		return industry;
	}

	public void setIndustry(int industry)
	{
		this.industry = industry;
	}

	public String getCreaterId()
	{
		return createrId;
	}

	public void setCreaterId(String createrId)
	{
		this.createrId = createrId;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public int getQqtype()
	{
		return qqtype;
	}

	public void setQqtype(int qqtype)
	{
		this.qqtype = qqtype;
	}

	public int getRtype()
	{
		return rtype;
	}

	public void setRtype(int rtype)
	{
		this.rtype = rtype;
	}

	/**
	 * @return the refCorpCustId
	 */
	public String getRefCorpCustId()
	{
		return refCorpCustId;
	}

	/**
	 * @param refCorpCustId the refCorpCustId to set
	 */
	public void setRefCorpCustId(String refCorpCustId)
	{
		this.refCorpCustId = refCorpCustId;
	}

	/**
	 * @return the refDepartCustId
	 */
	public String getRefDepartCustId()
	{
		return refDepartCustId;
	}

	/**
	 * @param refDepartCustId the refDepartCustId to set
	 */
	public void setRefDepartCustId(String refDepartCustId)
	{
		this.refDepartCustId = refDepartCustId;
	}

	/**
	 * @return the formerCust
	 */
	public String getFormerCust()
	{
		return formerCust;
	}

	/**
	 * @param formerCust the formerCust to set
	 */
	public void setFormerCust(String formerCust)
	{
		this.formerCust = formerCust;
	}

	/**
	 * @return the formerCustName
	 */
	public String getFormerCustName()
	{
		return formerCustName;
	}

	/**
	 * @param formerCustName the formerCustName to set
	 */
	public void setFormerCustName(String formerCustName)
	{
		this.formerCustName = formerCustName;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		//final String TAB = ",";

		StringBuilder retValue = new StringBuilder();

		return retValue.toString();
	}
}
