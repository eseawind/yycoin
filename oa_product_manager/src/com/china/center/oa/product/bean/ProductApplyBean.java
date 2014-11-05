package com.china.center.oa.product.bean;

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
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;

@Entity(name = "新产品申请")
@Table(name="T_PRODUCT_APPLY")
public class ProductApplyBean implements Serializable {

    @Id
    private String id = "";
    
    /**
     * 编码
     */
    @Html(title = "编码",must = true, maxLength = 20 )
    private String code = "";
    
    /**
     * 产品名称 (firstName + midname + ' ' + lastname) 
     */
    @Html(title = "名称", must = true, maxLength = 100)
    private String name = "";
    
    /**
     *  品名第一部分 如 YZ  （自动生成）
     */
    private String firstName = "";
    
    /**
     *  品名 成品配件数字编码  xxxxxyy， 如0000100(成品）  0000101 （自动生成）
     */
    private String midName = "";
    
    @Html(title = "真实名称", must = true, maxLength = 100)
    private String fullName = "";

    /**
     * 配件时，此处为关联的成品
     */
    @Html(title = "关联成品", name = "refProductName", maxLength = 100)
    private String refProductId = "";
    
    /**
     * 名称拼音全拼
     */
    private String fullspell = "";
    
    /**
     * 名称拼音简写
     */
    private String shortspell = "";
    
    /**
     * 1:纸币是z，2:金银币是j，3:古币是g，4:邮票是y，0:其他类型是q
     */
    @Html(title = "产品类型", type = Element.SELECT, must = true)
    private int type = ProductConstant.PRODUCT_TYPE_OTHER;
    
    /**
     * 材质类型 type = 201
     */
    @Html(title = "材质类型", must = true, type = Element.SELECT)
    private int materiaType = -1 ;
    
    /**
     * 渠道类型  - type = productSailType
     */
    @Html(title = "渠道类型", must = true, type = Element.SELECT)
    private int channelType = ProductConstant.SAILTYPE_SELF;
    
    /**
     * 管理类型  type = pubManagerType
     */
    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int  managerType = PublicConstant.MANAGER_TYPE_MANAGER;
    
    /**
     * 文化类型 type = 204
     */
    @Html(title = "文化类型", must = true, type = Element.SELECT)
    private int cultrueType = -1;
    
    /**
     * 折现率 type = 205
     */
    @Html(title = "折现率", must = true, type = Element.SELECT)
    private int discountRate = -1;
    
    /**
     * 价格区间 type = 206
     */
    @Html(title = "价格区间", must = true, type = Element.SELECT)
    private int priceRange = -1;
    
    /**
     * 销售周期 type = 207
     */
    @Html(title = "销售周期", must = true, type = Element.SELECT)
    private int salePeriod = -1;
    
    /**
     * 销售对象 type = 208
     */
    @Html(title = "销售对象", must = true, type = Element.SELECT)
    private int saleTarget = -1;
    
    /**
     * 产品性质 - 0:单元产品，1：合成产品
     */
    @Html(title = "产品性质", must = true, type = Element.SELECT)
    private int nature = ProductApplyConstant.NATURE_SINGLE;
    
    /**
     * 产品经理
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "s1")
    @Html(name = "productManagerName", title = "产品经理", readonly = true, must = true)
    private String productManagerId = "";
    
    /**
     * 纸币类型 type = 209
     */
    @Html(title = "纸币类型", type = Element.SELECT)
    private int currencyType = -1;
    
    /**
     * 旧货 type = 210
     */
    @Html(title = "旧货", must = true, type = Element.SELECT)
    private int secondhandGoods = -1;
    
    /**
     * 外型 type = 211
     */
    @Html(title = "外型", must = true, type = Element.SELECT)
    private int style = -1;
    
     /**
      * 国家 type = 212
      */
    @Html(title = "国家", must = true, type = Element.SELECT)
    private int country = -1;
    
    /**
     * 提成周期
     */
    @Html(title = "提成周期开始时间", type = Element.DATE)
    private String commissionBeginDate = "";
    
    /**
     * 提成周期
     */
    @Html(title = "提成周期截止时间", type = Element.DATE)
    private String commissionEndDate = "";

    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "s2")
    private String oprId = "";
    
    /**
     * 状态
     */    
    private int status = ProductApplyConstant.STATUS_SAVE;
    
    /**
     * 申请时间
     */
    private String logTime = "";
    
    /**
     * 事业部
     */
    private String industryId = "";
    
    /**
     * 备注
     */
    @Html(title = "备注", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";
    
    /**
     * 金（克）
     */
    @Html(title = "金（克）", type = Element.DOUBLE, must = true)
    private double gold = 0.0d;

    /**
     * 银（克）
     */
    @Html(title = "银（克）", type = Element.DOUBLE, must = true)
    private double silver = 0.0d;
    
    /**
     * 增值税类型(114)
     */
    @Html(title = "增值税类型", type = Element.SELECT)
    private String dutyType = "";
    
    /**
     * 选择进项发票
     */
    @Html(title = "进项发票", type = Element.SELECT, must = true)
    private String inputInvoice = "";
    
    /**
     * 
     */
    @Html(title = "销项发票", type = Element.SELECT, must = true)
    private String sailInvoice = "";
    
    @Ignore
    private List<ProductSubApplyBean> productSubApplyList = null;
    
    @Ignore
    private List<ProductVSStafferBean> vsList = null;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public int getManagerType() {
        return managerType;
    }

    public void setManagerType(int managerType) {
        this.managerType = managerType;
    }

    public int getCultrueType() {
        return cultrueType;
    }

    public void setCultrueType(int cultrueType) {
        this.cultrueType = cultrueType;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public int getSalePeriod() {
        return salePeriod;
    }

    public void setSalePeriod(int salePeriod) {
        this.salePeriod = salePeriod;
    }

    public int getSaleTarget() {
        return saleTarget;
    }

    public void setSaleTarget(int saleTarget) {
        this.saleTarget = saleTarget;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
       
    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public List<ProductSubApplyBean> getProductSubApplyList() {
        return productSubApplyList;
    }

    public void setProductSubApplyList(List<ProductSubApplyBean> productSubApplyList) {
        this.productSubApplyList = productSubApplyList;
    }

    public String getFullspell() {
        return fullspell;
    }

    public void setFullspell(String fullspell) {
        this.fullspell = fullspell;
    }

    public String getShortspell() {
        return shortspell;
    }

    public void setShortspell(String shortspell) {
        this.shortspell = shortspell;
    }

    public int getMateriaType() {
        return materiaType;
    }

    public void setMateriaType(int materiaType) {
        this.materiaType = materiaType;
    }

    public int getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(int currencyType) {
        this.currencyType = currencyType;
    }

    public int getSecondhandGoods() {
        return secondhandGoods;
    }

    public void setSecondhandGoods(int secondhandGoods) {
        this.secondhandGoods = secondhandGoods;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getCommissionBeginDate() {
        return commissionBeginDate;
    }

    public void setCommissionBeginDate(String commissionBeginDate) {
        this.commissionBeginDate = commissionBeginDate;
    }

    public String getCommissionEndDate() {
        return commissionEndDate;
    }

    public void setCommissionEndDate(String commissionEndDate) {
        this.commissionEndDate = commissionEndDate;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGold()
	{
		return gold;
	}

	public void setGold(double gold)
	{
		this.gold = gold;
	}

	public double getSilver()
	{
		return silver;
	}

	public void setSilver(double silver)
	{
		this.silver = silver;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the midName
	 */
	public String getMidName()
	{
		return midName;
	}

	/**
	 * @param midName the midName to set
	 */
	public void setMidName(String midName)
	{
		this.midName = midName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * @return the refProductId
	 */
	public String getRefProductId()
	{
		return refProductId;
	}

	/**
	 * @return the dutyType
	 */
	public String getDutyType()
	{
		return dutyType;
	}

	/**
	 * @param dutyType the dutyType to set
	 */
	public void setDutyType(String dutyType)
	{
		this.dutyType = dutyType;
	}

	/**
	 * @return the inputInvoice
	 */
	public String getInputInvoice()
	{
		return inputInvoice;
	}

	/**
	 * @param inputInvoice the inputInvoice to set
	 */
	public void setInputInvoice(String inputInvoice)
	{
		this.inputInvoice = inputInvoice;
	}

	/**
	 * @return the sailInvoice
	 */
	public String getSailInvoice()
	{
		return sailInvoice;
	}

	/**
	 * @param sailInvoice the sailInvoice to set
	 */
	public void setSailInvoice(String sailInvoice)
	{
		this.sailInvoice = sailInvoice;
	}

	/**
	 * @param refProductId the refProductId to set
	 */
	public void setRefProductId(String refProductId)
	{
		this.refProductId = refProductId;
	}

	public List<ProductVSStafferBean> getVsList() {
        return vsList;
    }

    public void setVsList(List<ProductVSStafferBean> vsList) {
        this.vsList = vsList;
    }

    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("fullspell = ")
            .append(this.fullspell)
            .append(TAB)
            .append("shortspell = ")
            .append(this.shortspell)            
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("materiaType = ")
            .append(this.materiaType)            
            .append(TAB)
            .append("channelType = ")
            .append(this.channelType)
            .append(TAB)
            .append("managerType = ")
            .append(this.managerType)
            .append(TAB)
            .append("cultrueType = ")
            .append(this.cultrueType)
            .append(TAB)
            .append("discountRate = ")
            .append(this.discountRate)
            .append(TAB)
            .append("priceRange = ")
            .append(this.priceRange)
            .append(TAB)
            .append("salePeriod = ")
            .append(this.salePeriod)
            .append(TAB)
            .append("saleTarget = ")
            .append(this.saleTarget)
            .append(TAB)
            .append("nature = ")
            .append(this.nature)
            .append(TAB)
            .append("productManagerId = ")
            .append(this.productManagerId)
            .append(TAB)
            .append("currencyType = ")
            .append(this.currencyType)
            .append(TAB)
            .append("secondhandGoods = ")
            .append(this.secondhandGoods)
            .append(TAB)
            .append("style = ")
            .append(this.style)
            .append(TAB)
            .append("country = ")
            .append(this.country)
            .append(TAB)
            .append("commissionBeginDate = ")
            .append(this.commissionBeginDate)
            .append(TAB)
            .append("commissionEndDate = ")
            .append(this.commissionEndDate)
            .append(TAB)
            .append("oprId = ")
            .append(this.oprId)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("productSubApplyList = ")
            .append(this.productSubApplyList)
            .append(TAB)
            .append("vsList = ")
            .append(this.vsList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
