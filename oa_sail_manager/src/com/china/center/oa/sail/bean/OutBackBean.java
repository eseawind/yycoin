package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.sail.constanst.OutConstant;

@SuppressWarnings("serial")
@Entity(name = "退货收货单")
@Table(name = "T_CENTER_OUTBACK")
public class OutBackBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 快递公司
	 */
	@Html(title = "快递公司", must = true, type = Element.SELECT)
	@Join(tagClass = TransportBean.class, type = JoinType.LEFT)
	private String expressCompany = "";
	
	/**
	 * 运单号
	 */
	@Html(title = "运单号", must = true, maxLength = 40)
	private String transportNo = "";
	
	/**
	 * 发件人省
	 */
	@Html(title = "发件人省", must = true, type= Element.SELECT)
	@Join(tagClass = ProvinceBean.class, type = JoinType.LEFT, alias = "P1")
	private String fromProvince = "";
	
	/**
	 * 发件人市
	 */
	@Html(title = "发件人市", must = true, type= Element.SELECT)
	@Join(tagClass = CityBean.class, type = JoinType.LEFT, alias = "C1")
	private String fromCity = "";
	
	/**
	 * 发件人地址
	 */
	@Html(title = "发件人地址", maxLength = 200)
	private String fromAddress = "";
	
	/**
	 * 发件人
	 */
	@Html(title = "发件人", maxLength = 20)
	private String fromer = "";
	
	/**
	 * 发件人电话
	 */
	@Html(title = "发件人电话", maxLength = 20)
	private String fromMobile = "";
	
	/**
	 * 收件人省
	 */
	@Html(title = "收件人省", must = true, type= Element.SELECT)
	@Join(tagClass = ProvinceBean.class, type = JoinType.LEFT, alias = "P2")
	private String toProvince = "";
	
	/**
	 * 收件人市
	 */
	@Html(title = "收件人市", must = true, type= Element.SELECT)
	@Join(tagClass = CityBean.class, type = JoinType.LEFT, alias = "C2")
	private String toCity = "";
	
	/**
	 * 发件人地址
	 */
	@Html(title = "收件人地址", maxLength = 200, must = true)
	private String toAddress = "";
	
	/**
	 * 收件人
	 */
	@Html(title = "收件人", maxLength = 20, must = true)
	@Column(name = "tos")
	private String to = "";
	
	/**
	 * 收件人电话
	 */
	@Html(title = "收件人电话", maxLength = 20, must = true)
	private String toMobile = "";
	
	/**
	 * 货物件数
	 */
	@Html(title = "货物件数", maxLength = 4, oncheck = JCheck.ONLY_NUMBER, must = true)
	private int goods = 0;
	
	/**
	 * 库管收货人
	 */
	private String receiverId = "";
	
	@Html(title = "库管收货人", maxLength = 20, must = true)
	private String receiver = "";
	
	/**
	 * 收货日期
	 */
	@Html(title = "收货日期", must = true, type = Element.DATE)
	private String receiverDate = "";
	
	/**
	 * 认领人 
	 */
	@Html(title = "认领人 ")
	private String claimer = "";
	
	/**
	 * 认领时间
	 */
	@Html(title = "认领时间 ")
	private String claimTime = "";
	
	/**
	 * 验货人
	 */
	@Html(title = "验货人 ")
	private String checker = "";
	
	/**
	 * 验货时间
	 */
	@Html(title = "验货时间 ")
	private String checkTime = "";
	
	@Html(title = "验货描述", maxLength = 100, type = Element.TEXTAREA)
	private String checkReason = "";
	
	/**
	 * 入库人
	 */
	private String stocker = "";
	
	/**
	 * 入库时间
	 */
	private String stockTime = "";
	
	/**
	 * 经办人
	 */
	private String stafferId = "";
	
	/**
	 * 经办人
	 */
	private String stafferName = "";
	
	/**
	 * 经办时间
	 */
	private String logTime = "";
	
	/**
	 * 退货客户
	 */
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
	@Html(title = "退货客户", name = "customerName", must = true)
	private String customerId = "";
	
	/**
	 * 状态
	 */
	private int status = OutConstant.OUTBACK_STATUS_SAVE;
	
	/**
	 * 关联退货单，多个以;间隔
	 */
	private String refOutId = "";
	
	@Html(title = "描述", maxLength = 100, type = Element.TEXTAREA)
    private String description = "";
	
	 /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;

	public OutBackBean()
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
	 * @return the expressCompany
	 */
	public String getExpressCompany()
	{
		return expressCompany;
	}

	/**
	 * @param expressCompany the expressCompany to set
	 */
	public void setExpressCompany(String expressCompany)
	{
		this.expressCompany = expressCompany;
	}

	/**
	 * @return the transportNo
	 */
	public String getTransportNo()
	{
		return transportNo;
	}

	/**
	 * @param transportNo the transportNo to set
	 */
	public void setTransportNo(String transportNo)
	{
		this.transportNo = transportNo;
	}

	/**
	 * @return the fromProvince
	 */
	public String getFromProvince()
	{
		return fromProvince;
	}

	/**
	 * @param fromProvince the fromProvince to set
	 */
	public void setFromProvince(String fromProvince)
	{
		this.fromProvince = fromProvince;
	}

	/**
	 * @return the fromCity
	 */
	public String getFromCity()
	{
		return fromCity;
	}

	/**
	 * @param fromCity the fromCity to set
	 */
	public void setFromCity(String fromCity)
	{
		this.fromCity = fromCity;
	}

	/**
	 * @return the fromAddress
	 */
	public String getFromAddress()
	{
		return fromAddress;
	}

	/**
	 * @param fromAddress the fromAddress to set
	 */
	public void setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
	}

	/**
	 * @return the fromer
	 */
	public String getFromer()
	{
		return fromer;
	}

	/**
	 * @param fromer the fromer to set
	 */
	public void setFromer(String fromer)
	{
		this.fromer = fromer;
	}

	/**
	 * @return the fromMobile
	 */
	public String getFromMobile()
	{
		return fromMobile;
	}

	/**
	 * @param fromMobile the fromMobile to set
	 */
	public void setFromMobile(String fromMobile)
	{
		this.fromMobile = fromMobile;
	}

	/**
	 * @return the toProvince
	 */
	public String getToProvince()
	{
		return toProvince;
	}

	/**
	 * @param toProvince the toProvince to set
	 */
	public void setToProvince(String toProvince)
	{
		this.toProvince = toProvince;
	}

	/**
	 * @return the toCity
	 */
	public String getToCity()
	{
		return toCity;
	}

	/**
	 * @param toCity the toCity to set
	 */
	public void setToCity(String toCity)
	{
		this.toCity = toCity;
	}

	/**
	 * @return the to
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to)
	{
		this.to = to;
	}

	/**
	 * @return the toMobile
	 */
	public String getToMobile()
	{
		return toMobile;
	}

	/**
	 * @param toMobile the toMobile to set
	 */
	public void setToMobile(String toMobile)
	{
		this.toMobile = toMobile;
	}

	/**
	 * @return the goods
	 */
	public int getGoods()
	{
		return goods;
	}

	/**
	 * @param goods the goods to set
	 */
	public void setGoods(int goods)
	{
		this.goods = goods;
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
	 * @return the receiverDate
	 */
	public String getReceiverDate()
	{
		return receiverDate;
	}

	/**
	 * @param receiverDate the receiverDate to set
	 */
	public void setReceiverDate(String receiverDate)
	{
		this.receiverDate = receiverDate;
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
	 * @return the refOutId
	 */
	public String getRefOutId()
	{
		return refOutId;
	}

	/**
	 * @param refOutId the refOutId to set
	 */
	public void setRefOutId(String refOutId)
	{
		this.refOutId = refOutId;
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
	 * @return the claimer
	 */
	public String getClaimer()
	{
		return claimer;
	}

	/**
	 * @param claimer the claimer to set
	 */
	public void setClaimer(String claimer)
	{
		this.claimer = claimer;
	}

	/**
	 * @return the claimTime
	 */
	public String getClaimTime()
	{
		return claimTime;
	}

	/**
	 * @param claimTime the claimTime to set
	 */
	public void setClaimTime(String claimTime)
	{
		this.claimTime = claimTime;
	}

	/**
	 * @return the checker
	 */
	public String getChecker()
	{
		return checker;
	}

	/**
	 * @param checker the checker to set
	 */
	public void setChecker(String checker)
	{
		this.checker = checker;
	}

	/**
	 * @return the checkTime
	 */
	public String getCheckTime()
	{
		return checkTime;
	}

	/**
	 * @param checkTime the checkTime to set
	 */
	public void setCheckTime(String checkTime)
	{
		this.checkTime = checkTime;
	}

	/**
	 * @return the stocker
	 */
	public String getStocker()
	{
		return stocker;
	}

	/**
	 * @param stocker the stocker to set
	 */
	public void setStocker(String stocker)
	{
		this.stocker = stocker;
	}

	/**
	 * @return the stockTime
	 */
	public String getStockTime()
	{
		return stockTime;
	}

	/**
	 * @param stockTime the stockTime to set
	 */
	public void setStockTime(String stockTime)
	{
		this.stockTime = stockTime;
	}

	/**
	 * @return the toAddress
	 */
	public String getToAddress()
	{
		return toAddress;
	}

	/**
	 * @param toAddress the toAddress to set
	 */
	public void setToAddress(String toAddress)
	{
		this.toAddress = toAddress;
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
	 * @return the receiverId
	 */
	public String getReceiverId()
	{
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId)
	{
		this.receiverId = receiverId;
	}

	/**
	 * @return the checkReason
	 */
	public String getCheckReason()
	{
		return checkReason;
	}

	/**
	 * @param checkReason the checkReason to set
	 */
	public void setCheckReason(String checkReason)
	{
		this.checkReason = checkReason;
	}

	/**
	 * @return the attachmentList
	 */
	public List<AttachmentBean> getAttachmentList()
	{
		return attachmentList;
	}

	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(List<AttachmentBean> attachmentList)
	{
		this.attachmentList = attachmentList;
	}
}
