/**
 *
 */
package com.china.center.oa.stock.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.stock.constant.StockConstant;


/**
 * @author Administrator
 */
@Entity(name = "采购ITEM")
@Table(name = "T_CENTER_STOCKITEM")
public class StockItemBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = StockBean.class)
    private String stockId = "";

    @Join(tagClass = ProductBean.class)
    private String productId = "";

    /**
     * REF的入库单
     */
    private String refOutId = "";

    /**
     * 是否入库
     */
    private int hasRef = StockConstant.STOCK_ITEM_HASREF_NO;

    /**
     * 供应商
     */
    @Join(tagClass = ProviderBean.class, type = JoinType.LEFT)
    private String providerId = "";

    /**
     * 采用谁的询价
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    /**
     * 采购数量
     */
    private int amount = 0;

    /**
     * 生成采购单当时产品的库存
     */
    private int productNum = 0;

    private int status = StockConstant.STOCK_ITEM_STATUS_INIT;

    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    /**
     * 拿货
     */
    private int fechProduct = StockConstant.STOCK_ITEM_FECH_NO;

    private String netAskId = "";

    /**
     * 平台的产品
     */
    private String showId = "";

    @Join(tagClass = DutyBean.class, type = JoinType.LEFT)
    private String dutyId = "";

    /**
     * 发票类型
     */
    private String invoiceType = "";

    /**
     * 最终的仓区
     */
    private String depotpartId = "";

    private double price = 0.0d;

    private double prePrice = 0.0d;

    /**
     * 产品预期销售额
     */
    private double sailPrice = 0.0d;

    private double total = 0.0d;

    /**
     * 采购主管必填,早于这个日期是不能付款的
     */
    private String nearlyPayDate = "";

    private String logTime = "";

    /**
     * T_CENTER_PRICEASKPROVIDER(外网询价的ID)
     */
    private String priceAskProviderId = "";

    private String description = "";

    /**
     * 是否付款
     */
    private int pay = StockConstant.STOCK_PAY_NO;
    
    /**
     * 预确认状态（额外干预）
     * 行项目全部确认完后才置为1
     * 0:未确认  1：已确认
     */
    private int extraStatus = 0;

    @Ignore
    private List<PriceAskProviderBean> asks = null;

    /**
     *
     */
    public StockItemBean()
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
     * @return the stockId
     */
    public String getStockId()
    {
        return stockId;
    }

    /**
     * @param stockId
     *            the stockId to set
     */
    public void setStockId(String stockId)
    {
        this.stockId = stockId;
    }

    /**
     * @return the productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
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
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
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
     * @return the asks
     */
    public List<PriceAskProviderBean> getAsks()
    {
        return asks;
    }

    /**
     * @param asks
     *            the asks to set
     */
    public void setAsks(List<PriceAskProviderBean> asks)
    {
        this.asks = asks;
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
     * @return the providerId
     */
    public String getProviderId()
    {
        return providerId;
    }

    /**
     * @param providerId
     *            the providerId to set
     */
    public void setProviderId(String providerId)
    {
        this.providerId = providerId;
    }

    /**
     * @return the prePrice
     */
    public double getPrePrice()
    {
        return prePrice;
    }

    /**
     * @param prePrice
     *            the prePrice to set
     */
    public void setPrePrice(double prePrice)
    {
        this.prePrice = prePrice;
    }

    /**
     * @return the refOutId
     */
    public String getRefOutId()
    {
        return refOutId;
    }

    /**
     * @param refOutId
     *            the refOutId to set
     */
    public void setRefOutId(String refOutId)
    {
        this.refOutId = refOutId;
    }

    /**
     * @return the hasRef
     */
    public int getHasRef()
    {
        return hasRef;
    }

    /**
     * @param hasRef
     *            the hasRef to set
     */
    public void setHasRef(int hasRef)
    {
        this.hasRef = hasRef;
    }

    /**
     * @return the productNum
     */
    public int getProductNum()
    {
        return productNum;
    }

    /**
     * @param productNum
     *            the productNum to set
     */
    public void setProductNum(int productNum)
    {
        this.productNum = productNum;
    }

    /**
     * @return the priceAskProviderId
     */
    public String getPriceAskProviderId()
    {
        return priceAskProviderId;
    }

    /**
     * @param priceAskProviderId
     *            the priceAskProviderId to set
     */
    public void setPriceAskProviderId(String priceAskProviderId)
    {
        this.priceAskProviderId = priceAskProviderId;
    }

    /**
     * @return the netAskId
     */
    public String getNetAskId()
    {
        return netAskId;
    }

    /**
     * @param netAskId
     *            the netAskId to set
     */
    public void setNetAskId(String netAskId)
    {
        this.netAskId = netAskId;
    }

    /**
     * @return the sailPrice
     */
    public double getSailPrice()
    {
        return sailPrice;
    }

    /**
     * @param sailPrice
     *            the sailPrice to set
     */
    public void setSailPrice(double sailPrice)
    {
        this.sailPrice = sailPrice;
    }

    /**
     * @return the showId
     */
    public String getShowId()
    {
        return showId;
    }

    /**
     * @param showId
     *            the showId to set
     */
    public void setShowId(String showId)
    {
        this.showId = showId;
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
     * @return the fechProduct
     */
    public int getFechProduct()
    {
        return fechProduct;
    }

    /**
     * @param fechProduct
     *            the fechProduct to set
     */
    public void setFechProduct(int fechProduct)
    {
        this.fechProduct = fechProduct;
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
     * @return the depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param depotpartId
     *            the depotpartId to set
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
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
            .append("StockItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stockId = ")
            .append(this.stockId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("refOutId = ")
            .append(this.refOutId)
            .append(TAB)
            .append("hasRef = ")
            .append(this.hasRef)
            .append(TAB)
            .append("providerId = ")
            .append(this.providerId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("productNum = ")
            .append(this.productNum)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("fechProduct = ")
            .append(this.fechProduct)
            .append(TAB)
            .append("netAskId = ")
            .append(this.netAskId)
            .append(TAB)
            .append("showId = ")
            .append(this.showId)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("invoiceType = ")
            .append(this.invoiceType)
            .append(TAB)
            .append("depotpartId = ")
            .append(this.depotpartId)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("prePrice = ")
            .append(this.prePrice)
            .append(TAB)
            .append("sailPrice = ")
            .append(this.sailPrice)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("nearlyPayDate = ")
            .append(this.nearlyPayDate)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("priceAskProviderId = ")
            .append(this.priceAskProviderId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("pay = ")
            .append(this.pay)
            .append(TAB)
            .append("asks = ")
            .append(this.asks)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
