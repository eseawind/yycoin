/**
 *
 */
package com.china.center.oa.stock.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.constant.StockConstant;


/**
 * 询价的bean
 * 
 * @author Administrator
 */
@Entity(name = "询价")
@Table(name = "T_CENTER_PRICEASK")
public class PriceAskBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "产品名称", name = "productName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = ProductBean.class, type = JoinType.EQUAL)
    private String productId = "";

    @Html(title = "数量", tip = "只能填写数字", must = true, maxLength = 40, oncheck = JCheck.ONLY_NUMBER)
    private int amount = 0;

    /**
     * 原始询价数量
     */
    private int srcamount = 0;

    private double price = 0.0d;

    private String logTime = "";

    @Html(title = "处理时间", type = Element.DATETIME, must = true, maxLength = 40, readonly = true)
    private String processTime = "";

    private String endTime = "";

    /**
     * 
     */
    @Join(tagClass = UserBean.class, type = JoinType.LEFT)
    private String userId = "";

    /**
     * 供应商选择分区
     */
    private String areaId = "";

    /**
     * 关联的询价
     */
    @FK
    private String refStock = "";

    /**
     * puserId
     */
    @Join(tagClass = UserBean.class, type = JoinType.LEFT, alias = "user1")
    private String puserId = "";

    @Html(title = "紧急程度", type = Element.SELECT, must = true)
    private int instancy = 0;

    private int status = PriceConstant.PRICE_ASK_STATUS_INIT;

    /**
     * 询价类型
     */
    private int type = PriceConstant.PRICE_ASK_TYPE_INNER;

    /**
     * 询价来源
     */
    private int src = PriceConstant.PRICE_ASK_SRC_ASK;

    /**
     * amountStatus
     */
    private int amountStatus = 0;

    /**
     * 询价产品类型
     */
    private int productType = 0;

    /**
     * 采购询价类型
     */
    private int stockMode = StockConstant.STOCK_MODE_SAIL;

    private int overTime = PriceConstant.OVERTIME_NO;

    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    @Html(title = "备注", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";

    private String reason = "";

    /**
     * 询价日期yyyyHHmmdd
     */
    private String askDate = "";

    private String parentAsk = "";

    /**
     * 保存类型
     */
    private int saveType = PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON;

    @Ignore
    private List<PriceAskProviderBean> item = null;

    /**
     *
     */
    public PriceAskBean()
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
     * @return the processTime
     */
    public String getProcessTime()
    {
        return processTime;
    }

    /**
     * @param processTime
     *            the processTime to set
     */
    public void setProcessTime(String processTime)
    {
        this.processTime = processTime;
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
     * @return the puserId
     */
    public String getPuserId()
    {
        return puserId;
    }

    /**
     * @param puserId
     *            the puserId to set
     */
    public void setPuserId(String puserId)
    {
        this.puserId = puserId;
    }

    /**
     * @return the instancy
     */
    public int getInstancy()
    {
        return instancy;
    }

    /**
     * @param instancy
     *            the instancy to set
     */
    public void setInstancy(int instancy)
    {
        this.instancy = instancy;
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
     * @return the item
     */
    public List<PriceAskProviderBean> getItem()
    {
        return item;
    }

    /**
     * @param item
     *            the item to set
     */
    public void setItem(List<PriceAskProviderBean> item)
    {
        this.item = item;
    }

    /**
     * @return the overTime
     */
    public int getOverTime()
    {
        return overTime;
    }

    /**
     * @param overTime
     *            the overTime to set
     */
    public void setOverTime(int overTime)
    {
        this.overTime = overTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return the reason
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason)
    {
        this.reason = reason;
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
     * @return the productType
     */
    public int getProductType()
    {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    /**
     * @return the askDate
     */
    public String getAskDate()
    {
        return askDate;
    }

    /**
     * @param askDate
     *            the askDate to set
     */
    public void setAskDate(String askDate)
    {
        this.askDate = askDate;
    }

    /**
     * @return the parentAsk
     */
    public String getParentAsk()
    {
        return parentAsk;
    }

    /**
     * @param parentAsk
     *            the parentAsk to set
     */
    public void setParentAsk(String parentAsk)
    {
        this.parentAsk = parentAsk;
    }

    /**
     * @return the saveType
     */
    public int getSaveType()
    {
        return saveType;
    }

    /**
     * @param saveType
     *            the saveType to set
     */
    public void setSaveType(int saveType)
    {
        this.saveType = saveType;
    }

    /**
     * @return the srcamount
     */
    public int getSrcamount()
    {
        return srcamount;
    }

    /**
     * @param srcamount
     *            the srcamount to set
     */
    public void setSrcamount(int srcamount)
    {
        if (srcamount > 0)
        {
            this.srcamount = srcamount;
        }
    }

    /**
     * @return the amountStatus
     */
    public int getAmountStatus()
    {
        return amountStatus;
    }

    /**
     * @param amountStatus
     *            the amountStatus to set
     */
    public void setAmountStatus(int amountStatus)
    {
        this.amountStatus = amountStatus;
    }

    /**
     * @return the src
     */
    public int getSrc()
    {
        return src;
    }

    /**
     * @param src
     *            the src to set
     */
    public void setSrc(int src)
    {
        this.src = src;
    }

    /**
     * @return the refStock
     */
    public String getRefStock()
    {
        return refStock;
    }

    /**
     * @param refStock
     *            the refStock to set
     */
    public void setRefStock(String refStock)
    {
        this.refStock = refStock;
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
     * @return the stockMode
     */
    public int getStockMode()
    {
        return stockMode;
    }

    /**
     * @param stockMode
     *            the stockMode to set
     */
    public void setStockMode(int stockMode)
    {
        this.stockMode = stockMode;
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
            .append("PriceAskBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("srcamount = ")
            .append(this.srcamount)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("processTime = ")
            .append(this.processTime)
            .append(TAB)
            .append("endTime = ")
            .append(this.endTime)
            .append(TAB)
            .append("userId = ")
            .append(this.userId)
            .append(TAB)
            .append("areaId = ")
            .append(this.areaId)
            .append(TAB)
            .append("refStock = ")
            .append(this.refStock)
            .append(TAB)
            .append("puserId = ")
            .append(this.puserId)
            .append(TAB)
            .append("instancy = ")
            .append(this.instancy)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("src = ")
            .append(this.src)
            .append(TAB)
            .append("amountStatus = ")
            .append(this.amountStatus)
            .append(TAB)
            .append("productType = ")
            .append(this.productType)
            .append(TAB)
            .append("stockMode = ")
            .append(this.stockMode)
            .append(TAB)
            .append("overTime = ")
            .append(this.overTime)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("reason = ")
            .append(this.reason)
            .append(TAB)
            .append("askDate = ")
            .append(this.askDate)
            .append(TAB)
            .append("parentAsk = ")
            .append(this.parentAsk)
            .append(TAB)
            .append("saveType = ")
            .append(this.saveType)
            .append(TAB)
            .append("item = ")
            .append(this.item)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
