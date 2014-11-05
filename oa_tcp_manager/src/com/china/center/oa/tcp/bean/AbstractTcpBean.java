/**
 * File Name: AnstractTcpBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;


/**
 * AnstractTcpBean
 * 
 * @author ZHUZHU
 * @version 2011-9-22
 * @see AbstractTcpBean
 * @since 3.0
 */
public abstract class AbstractTcpBean implements Serializable
{
    @Html(title = "目的", must = true, maxLength = 40)
    protected String name = "";

    protected String flowKey = "";

    @Html(title = "申请人", name = "stafferName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = StafferBean.class)
    protected String stafferId = "";

    /**
     * 借款人/被付款人
     */
    @Html(title = "借款人", name = "borrowStafferName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = StafferBean.class, alias = "BSB")
    protected String borrowStafferId = "";

    @Html(title = "部门", name = "departmentName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    protected String departmentId = "";

    protected String logTime = "";

    @FK
    @Html(title = "关联申请", must = true, readonly = true)
    protected String refId = "";

    @Html(title = "申请事由", maxLength = 200, must = true, type = Element.TEXTAREA)
    protected String description = "";

    @Html(title = "状态", must = true, type = Element.SELECT)
    protected int status = TcpConstanst.TCP_STATUS_INIT;

    /**
     * 申请类型
     */
    protected int type = TcpConstanst.TCP_APPLYTYPE_TRAVEL;

    /**
     * 0:销售 1:职能 2:管理
     */
    @Html(title = "申请系列", must = true, type = Element.SELECT)
    protected int stype = TcpConstanst.TCP_STYPE_SAIL;

    @Html(title = "开始日期", must = true, type = Element.DATE)
    protected String beginDate = "";

    @Html(title = "结束日期", must = true, type = Element.DATE)
    protected String endDate = "";

    /**
     * 准确到分
     */
    protected long total = 0L;

    /**
     * 借款总金额/报销公司支付金额
     */
    protected long borrowTotal = 0L;

    @Html(title = "纳税实体", must = true, type = Element.SELECT)
    @Join(tagClass = DutyBean.class, type = JoinType.LEFT)
    protected String dutyId = "";
    
    @Html(title = "合规标识", must = true, type = Element.SELECT)
    protected String compliance = "";

    /**
     * 费用子项
     */
    @Ignore
    protected List<TravelApplyItemBean> itemList = null;

    /**
     * 付款列表
     */
    @Ignore
    protected List<TravelApplyPayBean> payList = null;

    /**
     * 分担列表
     */
    @Ignore
    protected List<TcpShareBean> shareList = null;

    /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;

    // ////////////////////////差旅费申请及借款/////////////////////
    @Html(title = "出发城市", must = true, readonly = true)
    protected String srcCity = "";

    @Html(title = "目的城市", must = true, readonly = true)
    protected String destCity = "";

    /**
     * 飞机票-其他费用二需要合计到差旅费里面
     */
    @Html(title = "飞机票", must = true, type = Element.DOUBLE)
    protected long airplaneCharges = 0L;

    @Html(title = "火车票", must = true, type = Element.DOUBLE)
    protected long trainCharges = 0L;

    @Html(title = "汽车票", must = true, type = Element.DOUBLE)
    protected long busCharges = 0L;

    @Html(title = "住宿费", must = true, type = Element.DOUBLE)
    protected long hotelCharges = 0L;

    @Html(title = "业务招待费", must = true, type = Element.DOUBLE)
    protected long entertainCharges = 0L;

    @Html(title = "补贴", must = true, type = Element.DOUBLE)
    protected long allowanceCharges = 0L;

    @Html(title = "其他费用一", must = true, type = Element.DOUBLE)
    protected long other1Charges = 0L;

    @Html(title = "其他费用二", must = true, type = Element.DOUBLE)
    protected long other2Charges = 0L;

    // ////////////////////////差旅费申请及借款/////////////////////

    // ////////////////////////业务招待费申请及借款/////////////////////
    @Html(title = "来客单位", must = true)
    protected String unitName = "";

    @Html(title = "招待地点", must = true)
    protected String address = "";

    @Html(title = "来客姓名及职位", must = true, type = Element.TEXTAREA)
    protected String customerNames = "";

    @Html(title = "顾客方面同席者")
    protected String aroundNames = "";

    @Html(title = "本公司同席者", must = true)
    protected String companyStafferNames = "";

    // ////////////////////////业务招待费申请及借款/////////////////////

    /**
     * default constructor
     */
    public AbstractTcpBean()
    {
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the srcCity
     */
    public String getSrcCity()
    {
        return srcCity;
    }

    /**
     * @param srcCity
     *            the srcCity to set
     */
    public void setSrcCity(String srcCity)
    {
        this.srcCity = srcCity;
    }

    /**
     * @return the destCity
     */
    public String getDestCity()
    {
        return destCity;
    }

    /**
     * @param destCity
     *            the destCity to set
     */
    public void setDestCity(String destCity)
    {
        this.destCity = destCity;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate
     *            the beginDate to set
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the total
     */
    public long getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(long total)
    {
        this.total = total;
    }

    /**
     * @return the itemList
     */
    public List<TravelApplyItemBean> getItemList()
    {
        return itemList;
    }

    /**
     * @param itemList
     *            the itemList to set
     */
    public void setItemList(List<TravelApplyItemBean> itemList)
    {
        this.itemList = itemList;
    }

    /**
     * @return the payList
     */
    public List<TravelApplyPayBean> getPayList()
    {
        return payList;
    }

    /**
     * @param payList
     *            the payList to set
     */
    public void setPayList(List<TravelApplyPayBean> payList)
    {
        this.payList = payList;
    }

    /**
     * @return the shareList
     */
    public List<TcpShareBean> getShareList()
    {
        return shareList;
    }

    /**
     * @param shareList
     *            the shareList to set
     */
    public void setShareList(List<TcpShareBean> shareList)
    {
        this.shareList = shareList;
    }

    /**
     * @return the flowKey
     */
    public String getFlowKey()
    {
        return flowKey;
    }

    /**
     * @param flowKey
     *            the flowKey to set
     */
    public void setFlowKey(String flowKey)
    {
        this.flowKey = flowKey;
    }

    /**
     * @return the attachmentList
     */
    public List<AttachmentBean> getAttachmentList()
    {
        return attachmentList;
    }

    /**
     * @param attachmentList
     *            the attachmentList to set
     */
    public void setAttachmentList(List<AttachmentBean> attachmentList)
    {
        this.attachmentList = attachmentList;
    }

    /**
     * @return the airplaneCharges
     */
    public long getAirplaneCharges()
    {
        return airplaneCharges;
    }

    /**
     * @param airplaneCharges
     *            the airplaneCharges to set
     */
    public void setAirplaneCharges(long airplaneCharges)
    {
        this.airplaneCharges = airplaneCharges;
    }

    /**
     * @return the trainCharges
     */
    public long getTrainCharges()
    {
        return trainCharges;
    }

    /**
     * @param trainCharges
     *            the trainCharges to set
     */
    public void setTrainCharges(long trainCharges)
    {
        this.trainCharges = trainCharges;
    }

    /**
     * @return the busCharges
     */
    public long getBusCharges()
    {
        return busCharges;
    }

    /**
     * @param busCharges
     *            the busCharges to set
     */
    public void setBusCharges(long busCharges)
    {
        this.busCharges = busCharges;
    }

    /**
     * @return the hotelCharges
     */
    public long getHotelCharges()
    {
        return hotelCharges;
    }

    /**
     * @param hotelCharges
     *            the hotelCharges to set
     */
    public void setHotelCharges(long hotelCharges)
    {
        this.hotelCharges = hotelCharges;
    }

    /**
     * @return the entertainCharges
     */
    public long getEntertainCharges()
    {
        return entertainCharges;
    }

    /**
     * @param entertainCharges
     *            the entertainCharges to set
     */
    public void setEntertainCharges(long entertainCharges)
    {
        this.entertainCharges = entertainCharges;
    }

    /**
     * @return the allowanceCharges
     */
    public long getAllowanceCharges()
    {
        return allowanceCharges;
    }

    /**
     * @param allowanceCharges
     *            the allowanceCharges to set
     */
    public void setAllowanceCharges(long allowanceCharges)
    {
        this.allowanceCharges = allowanceCharges;
    }

    /**
     * @return the other1Charges
     */
    public long getOther1Charges()
    {
        return other1Charges;
    }

    /**
     * @param other1Charges
     *            the other1Charges to set
     */
    public void setOther1Charges(long other1Charges)
    {
        this.other1Charges = other1Charges;
    }

    /**
     * @return the other2Charges
     */
    public long getOther2Charges()
    {
        return other2Charges;
    }

    /**
     * @param other2Charges
     *            the other2Charges to set
     */
    public void setOther2Charges(long other2Charges)
    {
        this.other2Charges = other2Charges;
    }

    /**
     * @return the borrowTotal
     */
    public long getBorrowTotal()
    {
        return borrowTotal;
    }

    /**
     * @param borrowTotal
     *            the borrowTotal to set
     */
    public void setBorrowTotal(long borrowTotal)
    {
        this.borrowTotal = borrowTotal;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the unitName
     */
    public String getUnitName()
    {
        return unitName;
    }

    /**
     * @param unitName
     *            the unitName to set
     */
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the customerNames
     */
    public String getCustomerNames()
    {
        return customerNames;
    }

    /**
     * @param customerNames
     *            the customerNames to set
     */
    public void setCustomerNames(String customerNames)
    {
        this.customerNames = customerNames;
    }

    /**
     * @return the aroundNames
     */
    public String getAroundNames()
    {
        return aroundNames;
    }

    /**
     * @param aroundNames
     *            the aroundNames to set
     */
    public void setAroundNames(String aroundNames)
    {
        this.aroundNames = aroundNames;
    }

    /**
     * @return the companyStafferNames
     */
    public String getCompanyStafferNames()
    {
        return companyStafferNames;
    }

    /**
     * @param companyStafferNames
     *            the companyStafferNames to set
     */
    public void setCompanyStafferNames(String companyStafferNames)
    {
        this.companyStafferNames = companyStafferNames;
    }

    /**
     * @return the borrowStafferId
     */
    public String getBorrowStafferId()
    {
        return borrowStafferId;
    }

    /**
     * @param borrowStafferId
     *            the borrowStafferId to set
     */
    public void setBorrowStafferId(String borrowStafferId)
    {
        this.borrowStafferId = borrowStafferId;
    }

    /**
     * @return the stype
     */
    public int getStype()
    {
        return stype;
    }

    /**
     * @param stype
     *            the stype to set
     */
    public void setStype(int stype)
    {
        this.stype = stype;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AbstractTcpBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("borrowStafferId = ")
            .append(this.borrowStafferId)
            .append(TAB)
            .append("departmentId = ")
            .append(this.departmentId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("stype = ")
            .append(this.stype)
            .append(TAB)
            .append("beginDate = ")
            .append(this.beginDate)
            .append(TAB)
            .append("endDate = ")
            .append(this.endDate)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("borrowTotal = ")
            .append(this.borrowTotal)
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)
            .append(TAB)
            .append("payList = ")
            .append(this.payList)
            .append(TAB)
            .append("shareList = ")
            .append(this.shareList)
            .append(TAB)
            .append("attachmentList = ")
            .append(this.attachmentList)
            .append(TAB)
            .append("srcCity = ")
            .append(this.srcCity)
            .append(TAB)
            .append("destCity = ")
            .append(this.destCity)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("compliance = ")
            .append(this.compliance)                        
            .append(TAB)
            .append("airplaneCharges = ")
            .append(this.airplaneCharges)
            .append(TAB)
            .append("trainCharges = ")
            .append(this.trainCharges)
            .append(TAB)
            .append("busCharges = ")
            .append(this.busCharges)
            .append(TAB)
            .append("hotelCharges = ")
            .append(this.hotelCharges)
            .append(TAB)
            .append("entertainCharges = ")
            .append(this.entertainCharges)
            .append(TAB)
            .append("allowanceCharges = ")
            .append(this.allowanceCharges)
            .append(TAB)
            .append("other1Charges = ")
            .append(this.other1Charges)
            .append(TAB)
            .append("other2Charges = ")
            .append(this.other2Charges)
            .append(TAB)
            .append("unitName = ")
            .append(this.unitName)
            .append(TAB)
            .append("address = ")
            .append(this.address)
            .append(TAB)
            .append("customerNames = ")
            .append(this.customerNames)
            .append(TAB)
            .append("aroundNames = ")
            .append(this.aroundNames)
            .append(TAB)
            .append("companyStafferNames = ")
            .append(this.companyStafferNames)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
