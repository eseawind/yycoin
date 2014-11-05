package com.china.center.oa.finance.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity(name = "申请预收退款")
@Table(name = "T_CENTER_BACKPREPAY_APPLY")
public class BackPrePayApplyBean implements Serializable
{
    @Id
    @Html(title = "标识", must = true, maxLength = 40)
    private String id = "";
    
    @Html(title = "目的", must = true, maxLength = 40)
    private String name = "";

    /**
     * backPrePay
     */
    private String flowKey = "";

    @Html(title = "申请人", name = "stafferName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Html(title = "部门", name = "departmentName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String departmentId = "";

    private String logTime = "";

    @Html(title = "备注", maxLength = 200, must = true, type = Element.TEXTAREA)
    private String description = "";

    @Html(title = "状态", must = true, type = Element.SELECT)
    private int status = 0;

    /**
     * TCP 类型 - 申请预收退款
     */
    private int type = 23;

    /**
     * 0:销售 1:职能 2:管理 (这里忽略)
     */
    @Html(title = "申请系列", must = true, type = Element.SELECT)
    private int stype = 0;

    @Html(title = "付款方式", type = Element.SELECT, must = true)
    private int payType = FinanceConstant.OUTBILL_PAYTYPE_MONEY;
    
    private String customerId = "";
    
    @Html(title = "客户", must = true, maxLength = 200)
    private String customerName = "";
    
    /**
     * 申请返利金额
     */
    @Html(title = "预收金额", readonly = true, type = Element.DOUBLE)
    private long total = 0L;
    
    @Html(title = "退款金额", must = true, type = Element.DOUBLE)
    private long backMoney = 0L;
    
    /**
     * 取黑名单上当时的应收
     */
    private long ownMoney = 0L;
    
    @Html(title = "收款人", must = true, type = Element.INPUT)
    private String receiver = "";
    
    @Html(title = "收款银行", must = true)
    private String receiveBank = "";
    
    @Html(title = "收款账号", must = true)
    private String receiveAccount = "";
    
    @Html(title = "收款单(SF)", must = true, readonly = true)
    private String billId = "";
    
    /**
     * 退部分预收时，退款部分产生一个新的收款单，状态为 关联。如果流程驳回时，要更新为预收。
     */
    private String newBillId = "";
    
    @Html(title = "回款单(HK)", readonly = true)
    private String paymentId = "";
    
    /**
     * 客户的账号
     */
    @Html(title = "原汇款账号", readonly = true)
    private String customerAccount = "";
    
    @Html(title = "原收款公司账户", readonly = true)
    private String bankName = "";

    private String bankId = "";
    
    /**
     * 附件列表
     */
    @Ignore
    private List<AttachmentBean> attachmentList = null;
    
	/**
	 * 
	 */
	public BackPrePayApplyBean()
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFlowKey()
	{
		return flowKey;
	}

	public void setFlowKey(String flowKey)
	{
		this.flowKey = flowKey;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getStype()
	{
		return stype;
	}

	public void setStype(int stype)
	{
		this.stype = stype;
	}

	/**
	 * @return the payType
	 */
	public int getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(int payType) {
		this.payType = payType;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the backMoney
	 */
	public long getBackMoney() {
		return backMoney;
	}

	/**
	 * @param backMoney the backMoney to set
	 */
	public void setBackMoney(long backMoney) {
		this.backMoney = backMoney;
	}

	/**
	 * @return the ownMoney
	 */
	public long getOwnMoney() {
		return ownMoney;
	}

	/**
	 * @param ownMoney the ownMoney to set
	 */
	public void setOwnMoney(long ownMoney) {
		this.ownMoney = ownMoney;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the receiveBank
	 */
	public String getReceiveBank() {
		return receiveBank;
	}

	/**
	 * @param receiveBank the receiveBank to set
	 */
	public void setReceiveBank(String receiveBank) {
		this.receiveBank = receiveBank;
	}

	/**
	 * @return the billId
	 */
	public String getBillId() {
		return billId;
	}

	/**
	 * @param billId the billId to set
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the customerAccount
	 */
	public String getCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the receiveAccount
	 */
	public String getReceiveAccount() {
		return receiveAccount;
	}

	/**
	 * @param receiveAccount the receiveAccount to set
	 */
	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

	/**
	 * @return the newBillId
	 */
	public String getNewBillId() {
		return newBillId;
	}

	/**
	 * @param newBillId the newBillId to set
	 */
	public void setNewBillId(String newBillId) {
		this.newBillId = newBillId;
	}

	/**
	 * @return the attachmentList
	 */
	public List<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}

	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(List<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
	}
}
