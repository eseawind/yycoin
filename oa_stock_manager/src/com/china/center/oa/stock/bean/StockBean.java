/**
 *
 */
package com.china.center.oa.stock.bean;


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
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.constant.StockConstant;


/**
 * CHECK StockBean
 * 
 * @author ZHUZHU
 * @version 2011-2-1
 * @see StockBean
 * @since 1.0
 */
@Entity(name = "采购单")
@Table(name = "T_CENTER_STOCK")
public class StockBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = UserBean.class, type = JoinType.LEFT)
    private String userId = "";

    /**
     * 开单人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    /**
     * 产品拥有者
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "owe")
    private String owerId = "";

    /**
     * 废弃
     */
    @Html(title = "纳税实体", type = Element.SELECT, must = true)
    private String dutyId = "";

    /**
     * 询价人所在分公司
     */
    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    /**
     * 事业部属性
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String industryId = "";

    private int status = StockConstant.STOCK_STATUS_INIT;

    private int exceptStatus = StockConstant.EXCEPTSTATUS_COMMON;

    @Html(title = "到货时间", type = Element.DATE, must = true)
    private String needTime = "";

    @Html(title = "预期帐期(天)", type = Element.NUMBER, must = true)
    private int willDate = 0;

    /**
     * 采购主管必填,早于这个日期是不能付款的
     */
    @Html(title = "最早付款日期", type = Element.DATE, must = true)
    private String nearlyPayDate = "";

    /**
     * 采购类型 0:销售采购 1:生产采购
     */
    @Html(title = "询价方式", type = Element.SELECT, must = true)
    private int type = PriceConstant.PRICE_ASK_TYPE_NET;

    /**
     * mtype
     */
    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    @Html(title = "采购模式", type = Element.SELECT, must = true)
    private int mode = StockConstant.STOCK_MODE_SAIL;

    /**
     * 采购类型
     */
    @Html(title = "采购类型", type = Element.SELECT, must = true)
    private int stype = StockConstant.STOCK_STYPE_COMMON;

    /**
     * 采购属性【公卖、自卖】
     */
    @Html(title = "采购属性", must = true, type = Element.SELECT)
    private int stockType = StockConstant.STOCK_SAILTYPE_PUBLIC;

    private String logTime = "";

    /**
     * 部门
     */
    @Html(title = "物流走向", must = true, type = Element.SELECT)
    private String flow = "";

    private double total = 0.0d;

    /**
     * 是否付款
     */
    private int pay = StockConstant.STOCK_PAY_NO;

    @Html(title = "发票选择", must = true, type = Element.SELECT)
    private int invoice = StockConstant.INVOICE_NO;

    /**
     * 废弃
     */
    @Html(title = "发票类型", type = Element.SELECT)
    private String invoiceType = "";

    /**
     * 指定供应商的片区
     */
    @Html(title = "供应商片区", type = Element.SELECT)
    private String areaId = "";

    /**
     * 发货备注
     */
    private String consign = "";

    @Html(title = "备注", type = Element.TEXTAREA, maxLength = 300)
    private String description = "";

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;
    
    private String operator = "";
    
    private String operatorName = "";
    
    /**
     * 成品 0 , 配件 1
     */
    @Html(title = "采购商品类别", type = Element.SELECT, must = true)
    private int ptype = ProductApplyConstant.NATURE_COMPOSE;

    @Html(title = "采购目标", type = Element.TEXTAREA, maxLength = 300, must = true)
    private String target = "";
    
    /**
     * 预确认状态（额外干预）
     * 行项目全部确认完后才置为1
     * 0:未确认  1：已确认
     */
    private int extraStatus = 0;
    
    @Ignore
    private List<StockItemBean> item = null;

    /**
     *
     */
    public StockBean()
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
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
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
     * @return the needTime
     */
    public String getNeedTime()
    {
        return needTime;
    }

    /**
     * @param needTime
     *            the needTime to set
     */
    public void setNeedTime(String needTime)
    {
        this.needTime = needTime;
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
     * @return the flow
     */
    public String getFlow()
    {
        return flow;
    }

    /**
     * @param flow
     *            the flow to set
     */
    public void setFlow(String flow)
    {
        this.flow = flow;
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
     * @return the item
     */
    public List<StockItemBean> getItem()
    {
        return item;
    }

    /**
     * @param item
     *            the item to set
     */
    public void setItem(List<StockItemBean> item)
    {
        this.item = item;
    }

    /**
     * @return the exceptStatus
     */
    public int getExceptStatus()
    {
        return exceptStatus;
    }

    /**
     * @param exceptStatus
     *            the exceptStatus to set
     */
    public void setExceptStatus(int exceptStatus)
    {
        this.exceptStatus = exceptStatus;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the pay
     */
    public int getPay()
    {
        return pay;
    }

    /**
     * @param pay
     *            the pay to set
     */
    public void setPay(int pay)
    {
        this.pay = pay;
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
     * @return the willDate
     */
    public int getWillDate()
    {
        return willDate;
    }

    /**
     * @param willDate
     *            the willDate to set
     */
    public void setWillDate(int willDate)
    {
        this.willDate = willDate;
    }

    /**
     * @return the nearlyPayDate
     */
    public String getNearlyPayDate()
    {
        return nearlyPayDate;
    }

    /**
     * @param nearlyPayDate
     *            the nearlyPayDate to set
     */
    public void setNearlyPayDate(String nearlyPayDate)
    {
        this.nearlyPayDate = nearlyPayDate;
    }

    /**
     * @return the stockType
     */
    public int getStockType()
    {
        return stockType;
    }

    /**
     * @param stockType
     *            the stockType to set
     */
    public void setStockType(int stockType)
    {
        this.stockType = stockType;
    }

    /**
     * @return the invoice
     */
    public int getInvoice()
    {
        return invoice;
    }

    /**
     * @param invoice
     *            the invoice to set
     */
    public void setInvoice(int invoice)
    {
        this.invoice = invoice;
    }

    /**
     * @return the invoiceType
     */
    public String getInvoiceType()
    {
        return invoiceType;
    }

    /**
     * @param invoiceType
     *            the invoiceType to set
     */
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof StockBean)) return false;
        final StockBean other = (StockBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
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
     * @return the owerId
     */
    public String getOwerId()
    {
        return owerId;
    }

    /**
     * @param owerId
     *            the owerId to set
     */
    public void setOwerId(String owerId)
    {
        this.owerId = owerId;
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

    /**
     * @return the areaId
     */
    public String getAreaId()
    {
        return areaId;
    }

    /**
     * @param areaId
     *            the areaId to set
     */
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    /**
     * @return the consign
     */
    public String getConsign()
    {
        return consign;
    }

    /**
     * @param consign
     *            the consign to set
     */
    public void setConsign(String consign)
    {
        this.consign = consign;
    }

    /**
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    /**
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    /**
     * @return the mode
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(int mode)
    {
        this.mode = mode;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
	 * @return the ptype
	 */
	public int getPtype()
	{
		return ptype;
	}

	/**
	 * @param ptype the ptype to set
	 */
	public void setPtype(int ptype)
	{
		this.ptype = ptype;
	}

	/**
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * @return the extraStatus
	 */
	public int getExtraStatus()
	{
		return extraStatus;
	}

	/**
	 * @param extraStatus the extraStatus to set
	 */
	public void setExtraStatus(int extraStatus)
	{
		this.extraStatus = extraStatus;
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
            .append("StockBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("userId = ")
            .append(this.userId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("owerId = ")
            .append(this.owerId)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("exceptStatus = ")
            .append(this.exceptStatus)
            .append(TAB)
            .append("needTime = ")
            .append(this.needTime)
            .append(TAB)
            .append("willDate = ")
            .append(this.willDate)
            .append(TAB)
            .append("nearlyPayDate = ")
            .append(this.nearlyPayDate)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("mode = ")
            .append(this.mode)
            .append(TAB)
            .append("stype = ")
            .append(this.stype)
            .append(TAB)
            .append("stockType = ")
            .append(this.stockType)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("flow = ")
            .append(this.flow)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("pay = ")
            .append(this.pay)
            .append(TAB)
            .append("invoice = ")
            .append(this.invoice)
            .append(TAB)
            .append("invoiceType = ")
            .append(this.invoiceType)
            .append(TAB)
            .append("areaId = ")
            .append(this.areaId)
            .append(TAB)
            .append("consign = ")
            .append(this.consign)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("operator = ")
            .append(this.operator)
            .append(TAB)
            .append("operatorName = ")
            .append(this.operatorName)
            .append(TAB)
            .append("item = ")
            .append(this.item)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
