package com.china.center.oa.sail.bean;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.publics.constant.PublicConstant;

public class TmpBackBean 
{
	@Id
    private String id = "";

    @FK
    private String outId = "";

    private String productId = "";

    private String productName = "";

    private String showName = "";

    private String showId = "";

    private String locationId = "";

    private String unit = "";

    private int amount = 0;

    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int inway = 0;

    /**
     * 储位(仓库下面是通过 仓区+产品+价格+产品所有者获取具体的信息的,所以storageId不使用了)
     */
    private String storageId = "";

    /**
     * 产品所在的仓区
     */
    private String depotpartId = "";

    private String depotpartName = "";

    /**
     * 产品的所有者
     */
    private String owner = "0";

    /**
     * 产品的所有者名称
     */
    private String ownerName = "公共";

    /**
     * 业务员销售价格
     */
    private double price = 0.0d;

    /**
     * 总部结算价格
     */
    private double pprice = 0.0d;

    /**
     * 事业部结算价格
     */
    private double iprice = 0.0d;

    /**
     * 输入价格(销售里面:业务员结算价)
     */
    private double inputPrice = 0.0d;

    /**
     * 成本
     */
    private double costPrice = 0.0d;

    /**
     * 成本的string值
     */
    private String costPriceKey = "";

    /**
     * 总销售价
     */
    private double value = 0.0d;

    /**
     * 开发票的金额(已经开票的金额)
     */
    private double invoiceMoney = 0.0d;

    /**
     * 成本(和costPrice一样)
     */
    private String description = "";

    /**
	 * 毛利
	 */
	private double profit = 0.0d;
	
	 /**
	  * 毛利率
	  */
	private double profitRatio = 0.0d;
    
    /**
     * 配送方式 deliverType
     */
    private int deliverType = -1;
    
    /**
     * 快递运费支付方式 - deliverPay(停用)
     */
    private int expressPay = -1;
    
    /**
     * 货运运费支付方式 - deliverPay(停用)
     */
    private int transportPay = -1;
    
    /**
     * 销售的产品类型
     */
    @Ignore
    private int productType = -1;
    
		public TmpBackBean()
		{
			
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOutId() {
			return outId;
		}

		public void setOutId(String outId) {
			this.outId = outId;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getShowName() {
			return showName;
		}

		public void setShowName(String showName) {
			this.showName = showName;
		}

		public String getShowId() {
			return showId;
		}

		public void setShowId(String showId) {
			this.showId = showId;
		}

		public String getLocationId() {
			return locationId;
		}

		public void setLocationId(String locationId) {
			this.locationId = locationId;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public int getMtype() {
			return mtype;
		}

		public void setMtype(int mtype) {
			this.mtype = mtype;
		}

		public int getInway() {
			return inway;
		}

		public void setInway(int inway) {
			this.inway = inway;
		}

		public String getStorageId() {
			return storageId;
		}

		public void setStorageId(String storageId) {
			this.storageId = storageId;
		}

		public String getDepotpartId() {
			return depotpartId;
		}

		public void setDepotpartId(String depotpartId) {
			this.depotpartId = depotpartId;
		}

		public String getDepotpartName() {
			return depotpartName;
		}

		public void setDepotpartName(String depotpartName) {
			this.depotpartName = depotpartName;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getOwnerName() {
			return ownerName;
		}

		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getPprice() {
			return pprice;
		}

		public void setPprice(double pprice) {
			this.pprice = pprice;
		}

		public double getIprice() {
			return iprice;
		}

		public void setIprice(double iprice) {
			this.iprice = iprice;
		}

		public double getInputPrice() {
			return inputPrice;
		}

		public void setInputPrice(double inputPrice) {
			this.inputPrice = inputPrice;
		}

		public double getCostPrice() {
			return costPrice;
		}

		public void setCostPrice(double costPrice) {
			this.costPrice = costPrice;
		}

		public String getCostPriceKey() {
			return costPriceKey;
		}

		public void setCostPriceKey(String costPriceKey) {
			this.costPriceKey = costPriceKey;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public double getInvoiceMoney() {
			return invoiceMoney;
		}

		public void setInvoiceMoney(double invoiceMoney) {
			this.invoiceMoney = invoiceMoney;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public double getProfit() {
			return profit;
		}

		public void setProfit(double profit) {
			this.profit = profit;
		}

		public double getProfitRatio() {
			return profitRatio;
		}

		public void setProfitRatio(double profitRatio) {
			this.profitRatio = profitRatio;
		}

		public int getDeliverType() {
			return deliverType;
		}

		public void setDeliverType(int deliverType) {
			this.deliverType = deliverType;
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

		public int getProductType() {
			return productType;
		}

		public void setProductType(int productType) {
			this.productType = productType;
		}

		
		
}
