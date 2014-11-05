package com.china.center.oa.extsail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;

@SuppressWarnings("serial")
@Entity(name = "紫金农商销售")
@Table(name = "T_CENTER_ZJRCOUT")
public class ZJRCOutBean implements Serializable
{
	/**
	 * 排序
	 */
	private String id = "";
	
	/**
	 * 紫金农商的单据ID
	 */
	@Id
	private String fullId = "";
	
	private String outTime = "";
	
	private int status = OutImportConstant.STATUS_SAVE;
	
	private String customerId = "";

    private String customerName = "";
    
    /**
     * 进出产品所在的仓库
     */
    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String location = "";
    
    private String stafferName = "";

    private String stafferId = "";
    
    private double total = 0.0d;

    private String description = "";
	
	/**
     * 库单类型
     */
    private int outType = OutConstant.OUTTYPE_OUT_COMMON;

    /**
     * 0:销售单 1:入库单
     */
    private int type = OutConstant.OUT_TYPE_OUTBILL;
    
	/**
	 * 发货信息
	 */
    @Join(tagClass=ProvinceBean.class, type = JoinType.LEFT)
	private String provinceId = "";
	
    @Join(tagClass=CityBean.class, type = JoinType.LEFT)
	private String cityId = "";
	
    @Join(tagClass = AreaBean.class, type = JoinType.LEFT)
	private String areaId = "";
	
	private String address = "";
	
	private String receiver = "";
	
	private String handPhone = "";
	
	private String shipDescription = "";
	
	private String invoiceHead = "";
	
	private String invoiceDetail = "";
	
	private String invoiceDescription = "";

	private String logTime = "";
	
	/**
	 * 配送单号
	 */
	private String tranNo = "";
	
	@Ignore
	List<ZJRCBaseBean> baseList = null;
	
	public ZJRCOutBean()
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
	 * @return the fullId
	 */
	public String getFullId()
	{
		return fullId;
	}

	/**
	 * @param fullId the fullId to set
	 */
	public void setFullId(String fullId)
	{
		this.fullId = fullId;
	}

	/**
	 * @return the outTime
	 */
	public String getOutTime()
	{
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}

	/**
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
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
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
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

	/**
	 * @return the total
	 */
	public double getTotal()
	{
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total)
	{
		this.total = total;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the outType
	 */
	public int getOutType()
	{
		return outType;
	}

	/**
	 * @param outType the outType to set
	 */
	public void setOutType(int outType)
	{
		this.outType = outType;
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
	 * @return the receiver
	 */
	public String getReceiver()
	{
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * @return the handPhone
	 */
	public String getHandPhone()
	{
		return handPhone;
	}

	/**
	 * @param handPhone the handPhone to set
	 */
	public void setHandPhone(String handPhone)
	{
		this.handPhone = handPhone;
	}

	/**
	 * @return the shipDescription
	 */
	public String getShipDescription()
	{
		return shipDescription;
	}

	/**
	 * @param shipDescription the shipDescription to set
	 */
	public void setShipDescription(String shipDescription)
	{
		this.shipDescription = shipDescription;
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
	 * @return the invoiceDetail
	 */
	public String getInvoiceDetail()
	{
		return invoiceDetail;
	}

	/**
	 * @param invoiceDetail the invoiceDetail to set
	 */
	public void setInvoiceDetail(String invoiceDetail)
	{
		this.invoiceDetail = invoiceDetail;
	}

	/**
	 * @return the invoiceDescription
	 */
	public String getInvoiceDescription()
	{
		return invoiceDescription;
	}

	/**
	 * @param invoiceDescription the invoiceDescription to set
	 */
	public void setInvoiceDescription(String invoiceDescription)
	{
		this.invoiceDescription = invoiceDescription;
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
	 * @return the tranNo
	 */
	public String getTranNo()
	{
		return tranNo;
	}

	/**
	 * @param tranNo the tranNo to set
	 */
	public void setTranNo(String tranNo)
	{
		this.tranNo = tranNo;
	}

	/**
	 * @return the baseList
	 */
	public List<ZJRCBaseBean> getBaseList()
	{
		return baseList;
	}

	/**
	 * @param baseList the baseList to set
	 */
	public void setBaseList(List<ZJRCBaseBean> baseList)
	{
		this.baseList = baseList;
	}
}
