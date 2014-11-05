package com.china.center.oa.sail.bean;

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
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.PromotionConstant;
import com.china.center.oa.sail.constanst.SailConstant;

/**
 * 
 * 促销规则
 *
 * @author 2012-8-16
 */
@Entity
@Table(name = "T_CENTER_PROMOTION")
public class PromotionBean implements Serializable {

    @Id
    @Html(title = "活动ID")
    private String id = "";
    
    /**
     * 促销名称
     */
    @Html(title = "活动名称", must = true, maxLength = 100)
    private String name = "";
           
    /**
     * 最小数量
     */
    @Html(title = "最小数量", must = true, type = Element.INPUT)
    private int minAmount = 0;
    
    /**
     *最大数量 
     */
    @Html(title = "最大数量", must = true, type = Element.INPUT)
    private int maxAmount = 0;
    
    /**
     *最小金额 
     */
    @Html(title = "最小金额", must = true, type = Element.INPUT)
    private double minMoney = 0.0d;
    
    /**
     * 最大金额
     */
    @Html(title = "最大金额", must = true, type = Element.INPUT)
    private double maxMoney = 0.0d;   
    
    /**
     * 订单是否累计
     * 0:是, 1:否
     */
    @Html(title = "订单是否累计", must = true, type = Element.SELECT)
    private int isAddUp = PromotionConstant.ISADDUP_YES;
    
    /**
     * 折扣类型, 0:直降， 1：折扣， 2：返物
     */
    @Html(title = "折扣类型", must = true, type = Element.SELECT)
    private int rebateType = PromotionConstant.REBATETYPE_DOWN;
    
    /**
     * 折扣金额
     */
    @Html(title = "折扣金额", must = true, type = Element.INPUT)
    private double rebateMoney = 0.0d;
    
    /**
     * 客户信用分
     */
    @Html(title = "客户信用分", must = true, type = Element.INPUT)
    private int custCredit = 0;
    
    /**
     * 是否允许退货
     */
    @Html(title = "是否允许退货", must = true, type = Element.SELECT)
    private int isReturn = PromotionConstant.ISRETURN_NO;
    
    /**
     * 业务员信用金额
     */
    @Html(title = "业务员信用金额", must = true, type = Element.INPUT)
    private int busiCredit = 0;
    
    /**
     * 折扣比例 0~ 1000， 10.5% -> 105‰ 
     */
    @Html(title = "折扣比例(‰)", type = Element.INPUT)
    private int rebateRate = 0;
    
    /**
     * 最大折扣金额
     */
    @Html(title = "最大折扣金额", must = true, type = Element.INPUT)
    private double maxRebateMoney = 0.0d;
    
    /**
     * 礼包（类似套餐）
     */
    @Html(title = "是否为礼包", must = true, type = Element.SELECT)
    private int giftBag = PromotionConstant.GIFTBAG_UNLIMIT;        
    
    /**
     * 是否开票
     */
    @Html(title = "是否开票", must = true, type = Element.SELECT)
    private int isInvoice = PromotionConstant.ISINVOICE_YES;
    
    /**
     * 付款方式
     * 0:货到收款 1:款到发货(黑名单) 2:使用业务员信用额度 3:使用了事业部经理的信用
     */
    @Html(title = "付款方式", type = Element.SELECT)
    private int payType = -1;
    
    /**
     * 事业部, 用逗号间隔
     */
    @Html(title = "事业部", must = true, type = Element.SELECT)   
    private String industryId = "";
    
    /**
     * 客户类型 102
     */
    @Html(title = "客户类型", must = true, type = Element.SELECT)
    private int cType = 0;
    
    /**
     * 是否含黑名单客户
     */
    @Html(title = "是否含黑名单客户", must = true, type = Element.SELECT)
    private int isBlack = PromotionConstant.ISBLACK_INCLUDE;
    
    /**
     * 参照时间
     */
    @Html(title = "参照时间", type = Element.SELECT)
    private int refTime = PromotionConstant.REFTIME_ORDERTIME;
    
    /**
     * 付款时间  先款后开单/不限
     */
    @Html(title = "付款时间", type = Element.SELECT)    
    private int payTime = PromotionConstant.PAYTIME_UNLIMIT;
    
    /**
     * 开始日期
     */
    @Html(title = "开始日期", must = true, type=Element.DATE)
    private String beginDate = "";
    
    /**
     * 结束日期
     */
    @Html(title = "结束日期", must = true,type=Element.DATE)
    private String endDate = "";
    
    private int inValid = PromotionConstant.INVALID_YES;
    
    /**
     * 备注
     */
    @Html(title = "活动简介", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";
    
    /**
     * 更新时间
     */
    private String updateTime = "";
    
    /**
     * 更新人
     */
    private String updater = "";
    
    /**
     * 备用1
     */
    private String reserve1 = "";
    
    /**
     * 备用2
     */
    private String reserve2 = "";
    
    /**
     * 备用3
     */
    private String reserve3 = "";
    
    /**
     * 备用4
     */
    private String reserve4 = "";
    
    /**
     * 备用5
     */
    private String reserve5 = "";
    
    @Ignore    
    private List<PromotionItemBean> itemList = null;
    
    /**
     * default constructor
     */
    public PromotionBean()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(double minMoney) {
        this.minMoney = minMoney;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public int getIsAddUp() {
        return isAddUp;
    }

    public void setIsAddUp(int isAddUp) {
        this.isAddUp = isAddUp;
    }

    public int getRebateType() {
        return rebateType;
    }

    public void setRebateType(int rebateType) {
        this.rebateType = rebateType;
    }

    public double getRebateMoney() {
        return rebateMoney;
    }

    public void setRebateMoney(double rebateMoney) {
        this.rebateMoney = rebateMoney;
    }

    public int getRebateRate() {
        return rebateRate;
    }

    public void setRebateRate(int rebateRate) {
        this.rebateRate = rebateRate;
    }

    public double getMaxRebateMoney() {
        return maxRebateMoney;
    }

    public void setMaxRebateMoney(double maxRebateMoney) {
        this.maxRebateMoney = maxRebateMoney;
    }

    public int getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(int isInvoice) {
        this.isInvoice = isInvoice;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public int getcType() {
        return cType;
    }

    public void setcType(int cType) {
        this.cType = cType;
    }

    public int getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(int isBlack) {
        this.isBlack = isBlack;
    }

    public int getRefTime() {
        return refTime;
    }

    public void setRefTime(int refTime) {
        this.refTime = refTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }       
    
    public int getPayTime() {
        return payTime;
    }

    public void setPayTime(int payTime) {
        this.payTime = payTime;
    }

    public int getGiftBag() {
        return giftBag;
    }

    public void setGiftBag(int giftBag) {
        this.giftBag = giftBag;
    }

    public int getInValid() {
        return inValid;
    }

    public void setInValid(int inValid) {
        this.inValid = inValid;
    }

    public List<PromotionItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<PromotionItemBean> itemList) {
        this.itemList = itemList;
    }

    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("PromotionBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("minAmount = ")
            .append(this.minAmount)
            .append(TAB)
            .append("maxAmount = ")
            .append(this.maxAmount)   
            .append(TAB)
            .append("minMoney = ")
            .append(this.minMoney)
            .append(TAB)
            .append("maxMoney = ")
            .append(this.maxMoney)
            .append(TAB)
            .append("isAddUp = ")
            .append(this.isAddUp)
            .append(TAB)
            .append("rebateType = ")
            .append(this.rebateType)
            .append(TAB)
            .append("rebateMoney = ")
            .append(this.rebateMoney)
            .append(TAB)
            .append("rebateRate = ")
            .append(this.rebateRate)   
            .append(TAB)
            .append("maxRebateMoney = ")
            .append(this.maxRebateMoney)   
            .append(TAB)
            .append("isInvoice = ")
            .append(this.isInvoice)
            .append(TAB)
            .append("isReturn = ")
            .append(this.isReturn)
            .append(TAB)
            .append("payType = ")
            .append(this.payType)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("cType = ")
            .append(this.cType)
            .append(TAB)
            .append("isBlack = ")
            .append(this.isBlack)
            .append(TAB)
            .append("refTime = ")
            .append(this.refTime)   
            .append(TAB)
            .append("payTime = ")
            .append(this.payTime)             
            .append(TAB)
            .append("beginDate = ")
            .append(this.beginDate)
            .append(TAB)
            .append("endDate = ")
            .append(this.endDate)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("updateTime = ")
            .append(this.updateTime)
            .append(TAB)
            .append("updater = ")
            .append(this.updater)     
            .append(TAB)
            .append("reserve1 = ")
            .append(this.reserve1)
            .append(TAB)
            .append("reserve2 = ")
            .append(this.reserve2)
            .append(TAB)
            .append("reserve3 = ")
            .append(this.reserve3)
            .append(TAB)
            .append("reserve4 = ")
            .append(this.reserve4)
            .append(TAB)
            .append("reserve5 = ")
            .append(this.reserve5)  
            .append(TAB)
            .append("giftBag = ")
            .append(this.giftBag) 
            .append(TAB)
            .append("inValid = ")
            .append(this.inValid) 
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)               
            .append(TAB)
            .append("busiCredit = ")
            .append(this.busiCredit)               
            .append(TAB)
            .append("custCredit = ")
            .append(this.custCredit)               
            .append(TAB)
            .append(" )");            
            ;
        
        return retValue.toString();
    }

	public int getCustCredit() {
		return custCredit;
	}

	public void setCustCredit(int custCredit) {
		this.custCredit = custCredit;
	}

	public int getBusiCredit() {
		return busiCredit;
	}

	public void setBusiCredit(int busiCredit) {
		this.busiCredit = busiCredit;
	}

	public int getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(int isReturn) {
		this.isReturn = isReturn;
	}
    
    
}
