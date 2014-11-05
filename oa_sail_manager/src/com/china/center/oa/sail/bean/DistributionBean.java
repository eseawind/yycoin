package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;

@Entity(name = "销售单配送信息")
@Table(name = "T_CENTER_DISTRIBUTION")
public class DistributionBean implements Serializable 
{
	@Id
	private String id = "";
	
	/**
	 * 关联销售单号
	 */
	@FK
	private String outId = "";
	
	/**
	 * 关联地址库ID
	 */		
	private String refAddId = "";
	
	/**
	 * 发货方式
	 */	
	private int shipping = 0;
	
	/**
	 * 运输方式1
	 */
	@Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e1")
	private int transport1 = 0;
	
	/**
	 * 运输方式2
	 */
	@Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e2")
	private int transport2 = 0;
	
	/**
	 * 省
	 */
	@Join(tagClass=ProvinceBean.class, type = JoinType.LEFT)
	private String provinceId = "";
	
	/**
	 * 市
	 */
	@Join(tagClass=CityBean.class, type = JoinType.LEFT)
	private String cityId = "";
	
	/**
	 * 区
	 */
	@Join(tagClass = AreaBean.class, type = JoinType.LEFT)
	private String areaId = "";
	
	/**
	 * 详细地址
	 */
	private String address = "";
	
	/**
	 * 收货人
	 */
	private String receiver = "";
	
	/**
	 * 手机
	 */
	private String mobile = "";
	
	/**
	 * 固话
	 */
	private String telephone = "";
	
	/**
	 * 备注
	 */
	private String description = "";
	
    /**
     * 快递运费支付方式 - deliverPay
     */
    private int expressPay = -1;
    
    /**
     * 货运运费支付方式 - deliverPay
     */
    private int transportPay = -1;
    
    /**
     * 打印次数
     */
    private int printCount = 0;
    
    /**
     * 发货日期
     */
    private String outboundDate = "";
	
	
	public DistributionBean()
	{		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getRefAddId() {
		return refAddId;
	}

	public void setRefAddId(String refAddId) {
		this.refAddId = refAddId;
	}

	public int getShipping() {
		return shipping;
	}

	public void setShipping(int shipping) {
		this.shipping = shipping;
	}

	public int getTransport1()
	{
		return transport1;
	}

	public void setTransport1(int transport1)
	{
		this.transport1 = transport1;
	}

	public int getTransport2()
	{
		return transport2;
	}

	public void setTransport2(int transport2)
	{
		this.transport2 = transport2;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getExpressPay()
	{
		return expressPay;
	}

	public void setExpressPay(int expressPay)
	{
		this.expressPay = expressPay;
	}

	public int getTransportPay()
	{
		return transportPay;
	}

	public void setTransportPay(int transportPay)
	{
		this.transportPay = transportPay;
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
	 * @return the printCount
	 */
	public int getPrintCount()
	{
		return printCount;
	}

	/**
	 * @param printCount the printCount to set
	 */
	public void setPrintCount(int printCount)
	{
		this.printCount = printCount;
	}

	/**
	 * @return the outboundDate
	 */
	public String getOutboundDate() {
		return outboundDate;
	}

	/**
	 * @param outboundDate the outboundDate to set
	 */
	public void setOutboundDate(String outboundDate) {
		this.outboundDate = outboundDate;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("DistributionBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("refAddId = ")
            .append(this.refAddId)
            .append(TAB)
            .append("shipping = ")
            .append(this.shipping)
            .append(TAB)
            .append("transport1 = ")
            .append(this.transport1)
            .append(TAB)
            .append("transport2 = ")
            .append(this.transport2)
            .append(TAB)
            .append("provinceId = ")
            .append(this.provinceId)
            .append(TAB)
            .append("cityId = ")
            .append(this.cityId)
            .append(TAB)
            .append("address = ")
            .append(this.address)
            .append(TAB)
            .append("receiver = ")
            .append(this.receiver)
            .append(TAB)
            .append("mobile = ")
            .append(this.mobile)
            .append(TAB)
            .append("telephone = ")
            .append(this.telephone)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
