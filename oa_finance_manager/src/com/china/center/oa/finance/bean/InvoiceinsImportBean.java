package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_INVOICEINS_IMPORT")
public class InvoiceinsImportBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String batchId = "";
	
	/**
	 * 销售单/委托结算单
	 */
	private String outId = "";
	
	private String customerId = "";
	
	/**
	 * 业务员
	 */
	private String stafferId = "";
	
	/**
	 * 0:销售 1：结算单
	 */
	private int type = 0;
	
	private double invoiceMoney = 0.0d;
	
	/**
	 * 发票号
	 * 1发票号： 1开票申请单 ： N 销售单
	 */
	private String invoiceNum = "";
	
	/**
	 * 纳税实体全部为 永银文化， 开票人 为 纳税实体对应的人
	 */
	private String invoiceId = "";
	
	private String invoiceHead = "";
	
	/**
	 * 开票日期
	 */
	private String invoiceDate = "";
	
	/**
	 * 关联生成的开票申请
	 */
	@FK(index = AnoConstant.FK_FIRST)
	private String refInsId = "";
	
	/**
	 * 导入人
	 */
	private String stafferName = "";
	
	private String logTime = "";
	
	private int shipping = 0;
	
	/**
	 * 运输方式1
	 */
	private int transport1 = 0;
	
	/**
	 * 运输方式2
	 */
	private int transport2 = 0;
	
	/**
	 * 省
	 */
	private String provinceId = "";
	
	/**
	 * 市
	 */
	private String cityId = "";
	
	/**
	 * 区
	 */
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
     * 快递运费支付方式 - deliverPay
     */
    private int expressPay = -1;
    
    /**
     * 货运运费支付方式 - deliverPay
     */
    private int transportPay = -1;
    
    private String description = "";
    
    /**
     * 1:同销售单一致 2：新配送地址
     */
    private int addrType = 0;

	/**
	 * 
	 */
	public InvoiceinsImportBean()
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
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
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
	 * @return the invoiceMoney
	 */
	public double getInvoiceMoney()
	{
		return invoiceMoney;
	}

	/**
	 * @param invoiceMoney the invoiceMoney to set
	 */
	public void setInvoiceMoney(double invoiceMoney)
	{
		this.invoiceMoney = invoiceMoney;
	}

	/**
	 * @return the invoiceNum
	 */
	public String getInvoiceNum()
	{
		return invoiceNum;
	}

	/**
	 * @param invoiceNum the invoiceNum to set
	 */
	public void setInvoiceNum(String invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the invoiceHead
	 */
	public String getInvoiceHead()
	{
		return invoiceHead;
	}

	/**
	 * @param invoiceHead the invoiceHead to set
	 */
	public void setInvoiceHead(String invoiceHead)
	{
		this.invoiceHead = invoiceHead;
	}

	/**
	 * @return the refInsId
	 */
	public String getRefInsId()
	{
		return refInsId;
	}

	/**
	 * @param refInsId the refInsId to set
	 */
	public void setRefInsId(String refInsId)
	{
		this.refInsId = refInsId;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
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
	 * @return the invoiceDate
	 */
	public String getInvoiceDate()
	{
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
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

	/**
	 * @return the stafferId
	 */
	public String getStafferId()
	{
		return stafferId;
	}

	/**
	 * @param stafferId the stafferId to set
	 */
	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public int getShipping() {
		return shipping;
	}

	public void setShipping(int shipping) {
		this.shipping = shipping;
	}

	public int getTransport1() {
		return transport1;
	}

	public void setTransport1(int transport1) {
		this.transport1 = transport1;
	}

	public int getTransport2() {
		return transport2;
	}

	public void setTransport2(int transport2) {
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	public int getExpressPay() {
		return expressPay;
	}

	public void setExpressPay(int expressPay) {
		this.expressPay = expressPay;
	}

	public int getTransportPay() {
		return transportPay;
	}

	public void setTransportPay(int transportPay) {
		this.transportPay = transportPay;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the addrType
	 */
	public int getAddrType() {
		return addrType;
	}

	/**
	 * @param addrType the addrType to set
	 */
	public void setAddrType(int addrType) {
		this.addrType = addrType;
	}
}
