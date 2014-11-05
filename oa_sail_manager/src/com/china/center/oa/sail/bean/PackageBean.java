package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.sail.constanst.ShipConstant;

@SuppressWarnings("serial")
@Entity(name = "发货单（包）")
@Table(name = "T_CENTER_PACKAGE")
public class PackageBean implements Serializable
{
	/**
	 * ID
	 */
	@Id
	private String id = "";
	
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
	private String customerId = "";
	
	/**
	 * 发货方式
	 */
	private int shipping = 0;
	
	/**
	 * 运输方式1 - 快递使用
	 */
	@Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e1")
	private int transport1 = 0;
	
    /**
     * 快递运费支付方式 - deliverPay
     */
    private int expressPay = -1;
    
    /**
     * 货运
     */
	@Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e2")
	private int transport2 = 0;
	
    /**
     * 货运运费支付方式 - deliverPay
     */
    private int transportPay = -1; 
	
	/**
	 * 完整的收货地址
	 */
	private String address = "";
	
	/**
	 * 收货人
	 */
	private String receiver = "";
	
	/**
	 * 移动电话
	 */
	private String mobile = "";
	
	/**
	 * 数量合计 
	 */
	private int amount = 0;
	
	/**
	 * 商品种类数
	 */
	private int productCount = 0;
	
	/**
	 * 金额合计
	 */
	private double total = 0.0d;
	
	/**
	 * 状态 - 0:初始; 1:已拣配， 99:已发货
	 */
	private int status = ShipConstant.SHIP_STATUS_INIT;
	
	/**
	 * 申请人
	 */
	private String stafferName = "";
	
	/**
	 * 事业部
	 */
	private String industryName = "";
	
	/**
	 * 部门
	 */
	private String departName = "";
	
	/**
	 * 地点
	 */
	@Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
	private String locationId = "";
	
	private String logTime = "";
	
	/**
	 * 拣配
	 */
	@FK
	private String pickupId = "";
	
	private int index_pos = 0;
	
	/**
	 * 发货时间
	 */
	private String shipTime = "";
	
	private String cityId = "";
	
	/**
     * 销售单 紧急 标识 1:紧急
     */
    private int emergency = 0;
	
	@Ignore
	List<PackageItemBean> itemList = null;

	public PackageBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public int getShipping()
	{
		return shipping;
	}

	public void setShipping(int shipping)
	{
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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getPickupId()
	{
		return pickupId;
	}

	public void setPickupId(String pickupId)
	{
		this.pickupId = pickupId;
	}

	public int getIndex_pos()
	{
		return index_pos;
	}

	public void setIndex_pos(int index_pos)
	{
		this.index_pos = index_pos;
	}

	public List<PackageItemBean> getItemList()
	{
		return itemList;
	}

	public void setItemList(List<PackageItemBean> itemList)
	{
		this.itemList = itemList;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	public String getIndustryName()
	{
		return industryName;
	}

	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}

	public String getDepartName()
	{
		return departName;
	}

	public void setDepartName(String departName)
	{
		this.departName = departName;
	}

	/**
	 * @return the expressPay
	 */
	public int getExpressPay()
	{
		return expressPay;
	}

	/**
	 * @param expressPay the expressPay to set
	 */
	public void setExpressPay(int expressPay)
	{
		this.expressPay = expressPay;
	}

	/**
	 * @return the transport2
	 */
	public int getTransport2()
	{
		return transport2;
	}

	/**
	 * @param transport2 the transport2 to set
	 */
	public void setTransport2(int transport2)
	{
		this.transport2 = transport2;
	}

	/**
	 * @return the transportPay
	 */
	public int getTransportPay()
	{
		return transportPay;
	}

	/**
	 * @param transportPay the transportPay to set
	 */
	public void setTransportPay(int transportPay)
	{
		this.transportPay = transportPay;
	}

	/**
	 * @return the productCount
	 */
	public int getProductCount()
	{
		return productCount;
	}

	/**
	 * @param productCount the productCount to set
	 */
	public void setProductCount(int productCount)
	{
		this.productCount = productCount;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId()
	{
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId)
	{
		this.locationId = locationId;
	}

	/**
	 * @return the shipTime
	 */
	public String getShipTime()
	{
		return shipTime;
	}

	/**
	 * @param shipTime the shipTime to set
	 */
	public void setShipTime(String shipTime)
	{
		this.shipTime = shipTime;
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
	 * @return the emergency
	 */
	public int getEmergency() {
		return emergency;
	}

	/**
	 * @param emergency the emergency to set
	 */
	public void setEmergency(int emergency) {
		this.emergency = emergency;
	}
}
