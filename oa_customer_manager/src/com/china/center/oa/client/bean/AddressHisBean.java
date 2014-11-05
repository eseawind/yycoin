package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;

/**
 * 
 * 这里应该做继承
 *
 * @author fangliwen 2013-3-29
 */

@SuppressWarnings("serial")
@Entity(name = "客户地址薄")
@Table(name = "T_CENTER_ADDRESS_HIS")
public class AddressHisBean implements Serializable
{

	@Id
	private String id = "";
	
	/**
	 * 客户ID  - 一个客户有多个地址
	 */
	@FK
	private String customerId = "";
	
	private String customerName = "";
	
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
	 * 业务员
	 */
	private String stafferId = "";
	
	/**
	 * 时间
	 */
	private String logTime = "";
	
	private String updator = "";
	
	private String updateTime = "";
	
	/**
	 * 每一次修改的批次号。
	 */
	private String batch = "";
	
	public AddressHisBean()
	{
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	
	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getUpdator()
	{
		return updator;
	}

	public void setUpdator(String updator)
	{
		this.updator = updator;
	}

	public String getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getBatch()
	{
		return batch;
	}

	public void setBatch(String batch)
	{
		this.batch = batch;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AddressHisBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
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
            .append(" )");

        return retValue.toString();
    }
}
