/*
 * File Name: Product.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.vs.ProductCombinationBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 产品
 * 
 * @author ZHUZHU
 * @version 2007-3-25
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_PRODUCT")
public class ProductBeanbak implements Serializable
{
    @Id
    private String id = "";

    /**
     * 产品名称
     */
    @Html(title = "名称", must = true, maxLength = 100)
    private String name = "";

    /**
     * 产品编码(12位)
     */
    @Unique
    @Html(title = "编码", must = true, maxLength = 20)
    private String code = "";

    /**
     * 虚拟件 0:不是 1:是
     */
    @Html(title = "虚拟产品", type = Element.SELECT, must = true)
    private int abstractType = ProductConstant.ABSTRACT_TYPE_NO;

    /**
     * 暂时不使用
     */
    private String refId = "";

    /**
     * 1:纸币是z，2:金银币是j，3:古币是g，4:邮票是y，0:其他类型是q
     */
    @Html(title = "产品类型", type = Element.SELECT, must = true)
    private int type = ProductConstant.PRODUCT_TYPE_OTHER;

    /**
     * 0:产品 1:物料
     */
    @Html(title = "是否物料", type = Element.SELECT, must = true)
    private int ptype = ProductConstant.PTYPE_PRODUCT;

    /**
     * 0:普通产品 1:合成产品
     */
    @Html(title = "合成属性", type = Element.SELECT, must = true)
    private int ctype = ProductConstant.CTYPE_NO;

    /**
     * 品种
     */
    @Html(title = "品种", type = Element.SELECT, must = true)
    private String typeName = "";

    /**
     * 规格
     */
    @Html(title = "规格", maxLength = 200)
    private String specification = "";

    /**
     * 型号
     */
    @Html(title = "型号", maxLength = 200)
    private String model = "";

    /**
     * 计量单位
     */
    @Html(title = "计量单位", type = Element.SELECT, must = true)
    private String amountUnit = "";

    /**
     * 重量单位
     */
    @Html(title = "重量单位", type = Element.SELECT, must = true)
    private String weightUnit = "";

    /**
     * 体积单位
     */
    @Html(title = "体积单位", type = Element.SELECT, must = true)
    private String cubageUnit = "";

    /**
     * 版次
     */
    @Html(title = "版次", maxLength = 100)
    private String version = "";

    /**
     * 设计修改号
     */
    @Html(title = "设计修改号", maxLength = 100)
    private String design = "";

    /**
     * 0:不启用库存模型 1:启用库存模型
     */
    @Html(title = "库存模型", type = Element.SELECT, must = true)
    private int stockType = ProductConstant.STOCKTYPE_NO_USER;

    /**
     * 物料来源
     */
    @Html(title = "物料来源", maxLength = 100)
    private String materielSource = "";

    /**
     * 库存单位
     */
    @Html(title = "库存单位", type = Element.SELECT, must = true)
    private String storeUnit = "";

    /**
     * ABC码
     */
    @Html(title = "ABC码", maxLength = 100)
    private String abc = "";

    /**
     * 批量规则
     */
    @Html(title = "批量规则", type = Element.SELECT, must = true)
    private String batchModal = "";

    /**
     * 盘点周期
     */
    @Html(title = "盘点周期（天）", must = true, type = Element.SELECT)
    private int checkDays = 0;

    /**
     * 积压期限
     */
    @Html(title = "积压期限（天）", must = true, type = Element.SELECT)
    private int maxStoreDays = 0;

    /**
     * 安全库存量
     */
    @Html(title = "安全库存量", type = Element.SELECT, must = true)
    private int safeStoreDays = 0;

    /**
     * 生产期（天）
     */
    @Html(title = "生产期（天）", type = Element.SELECT, must = true)
    private int makeDays = 0;

    /**
     * 物流期（天）
     */
    @Html(title = "物流期期（天）", type = Element.SELECT, must = true)
    private int flowDays = 0;

    /**
     * 最小批量的个数
     */
    @Html(title = "最小批量的个数", type = Element.SELECT, must = true)
    private int minAmount = 0;

    /**
     * 0:正常 1:锁定 2:注销
     */
    private int status = ProductConstant.STATUS_COMMON;

    /**
     * 装配标识 
     */
    @Html(title = "装配标识")
    private String assembleFlag = "";

    /**
     * 上次订货日期
     */
    @Html(title = "上次订货日期")
    private String lastOrderDate = "";

    /**
     * 物品日耗量 
     */
    @Html(title = "物品日耗量", type = Element.NUMBER)
    private int consumeInDay = 0;

    /**
     * 订货点数量
     */
    @Html(title = "订货点数量", type = Element.NUMBER, must = true)
    private int orderAmount = 0;

    /**
     * 主供应商
     */
    @Html(name = "mainProviderName", title = "主供应商", readonly = true)
    private String mainProvider = "";

    /**
     * 次供应商1
     */
    @Html(name = "assistantProviderName1", title = "次供应商1", readonly = true)
    private String assistantProvider1 = "";

    /**
     * 次供应商2
     */
    @Html(name = "assistantProviderName2", title = "次供应商2", readonly = true)
    private String assistantProvider2 = "";

    /**
     * 次供应商3
     */
    @Html(name = "assistantProviderName3", title = "次供应商3", readonly = true)
    private String assistantProvider3 = "";

    /**
     * 次供应商4
     */
    @Html(name = "assistantProviderName4", title = "次供应商4", readonly = true)
    private String assistantProvider4 = "";

    /**
     * 销售类型【0:自卖、1:代销】
     */
    @Html(title = "销售类型", type = Element.SELECT, must = true)
    private int sailType = ProductConstant.SAILTYPE_SELF;

    /**
     * 是否可以调价(0:yes 1:no)
     */
    @Html(title = "是否可以调价", type = Element.SELECT, must = true)
    private int adjustPrice = ProductConstant.ADJUSTPRICE_YES;

    /**
     * 财务类别(113)
     */
    @Html(title = "财务类别", type = Element.SELECT, must = true)
    private String financeType = "";

    /**
     * 增值税类型(114)
     */
    @Html(title = "增值税类型", type = Element.SELECT, must = true)
    private String dutyType = "";

    /**
     * 成本 - 借用  金（克）
     */
    @Html(title = "金（克）", type = Element.DOUBLE, must = true)
    private double cost = 0.0d;

    /**
     * 计划成本 - 借用 银（克）
     */
    @Html(title = "银（克）", type = Element.DOUBLE, must = true)
    private double planCost = 0.0d;

    /**
     * 批发价(在销售的时候显示)
     */
    @Html(title = "批发价", type = Element.DOUBLE, must = true)
    private double batchPrice = 0.0d;

    /**
     * 结算价(销售价)
     */
    @Html(title = "结算价", type = Element.DOUBLE, must = true)
    private double sailPrice = 0.0d;

    /**
     * 检测标识
     */
    @Html(title = "检测标识")
    private String checkFlag = "";

    /**
     * 检查类型【0:抽检、1:全检】
     */
    @Html(title = "检查类型", type = Element.SELECT, must = true)
    private int checkType = 0;

    /**
     * 检验标准
     */
    @Html(title = "检验标准", type = Element.SELECT, must = true)
    private String checkStandard = "";

    /**
     * 产品图片的路径
     */
    @Html(name = "pfile", title = "产品图片", type = Element.FILE)
    private String picPath = "";

    private String logTime = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String createrId = "";

    @Html(title = "描述", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";

    /**
     * 全拼
     */
    private String fullspell = "";

    /**
     * 简拼
     */
    private String shortspell = "";

    @Html(title = "外箱尺寸")
    private String reserve1 = "";

    @Html(title = "单箱数量")
    private String reserve2 = "";

    @Html(title = "单箱毛重")
    private String reserve3 = "";

    @Html(title = "管理类型", type = Element.SELECT, must = true)
    private String reserve4 = "0";

    @Html(title = "产品阶段", type = Element.SELECT, must = true)
    private String reserve5 = ProductConstant.PRODUTC_STEP_NEW;

    @Html(title = "预留6")
    private String reserve6 = "";

    @Ignore
    private List<ProductCombinationBean> vsList = null;

    /**
     * default constructor
     */
    public ProductBeanbak()
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
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the abstractType
     */
    public int getAbstractType()
    {
        return abstractType;
    }

    /**
     * @param abstractType
     *            the abstractType to set
     */
    public void setAbstractType(int abstractType)
    {
        this.abstractType = abstractType;
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
     * @return the typeName
     */
    public String getTypeName()
    {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    /**
     * @return the specification
     */
    public String getSpecification()
    {
        return specification;
    }

    /**
     * @param specification
     *            the specification to set
     */
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    /**
     * @return the model
     */
    public String getModel()
    {
        return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(String model)
    {
        this.model = model;
    }

    /**
     * @return the amountUnit
     */
    public String getAmountUnit()
    {
        return amountUnit;
    }

    /**
     * @param amountUnit
     *            the amountUnit to set
     */
    public void setAmountUnit(String amountUnit)
    {
        this.amountUnit = amountUnit;
    }

    /**
     * @return the weightUnit
     */
    public String getWeightUnit()
    {
        return weightUnit;
    }

    /**
     * @param weightUnit
     *            the weightUnit to set
     */
    public void setWeightUnit(String weightUnit)
    {
        this.weightUnit = weightUnit;
    }

    /**
     * @return the cubageUnit
     */
    public String getCubageUnit()
    {
        return cubageUnit;
    }

    /**
     * @param cubageUnit
     *            the cubageUnit to set
     */
    public void setCubageUnit(String cubageUnit)
    {
        this.cubageUnit = cubageUnit;
    }

    /**
     * @return the version
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * @return the design
     */
    public String getDesign()
    {
        return design;
    }

    /**
     * @param design
     *            the design to set
     */
    public void setDesign(String design)
    {
        this.design = design;
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
     * @return the materielSource
     */
    public String getMaterielSource()
    {
        return materielSource;
    }

    /**
     * @param materielSource
     *            the materielSource to set
     */
    public void setMaterielSource(String materielSource)
    {
        this.materielSource = materielSource;
    }

    /**
     * @return the storeUnit
     */
    public String getStoreUnit()
    {
        return storeUnit;
    }

    /**
     * @param storeUnit
     *            the storeUnit to set
     */
    public void setStoreUnit(String storeUnit)
    {
        this.storeUnit = storeUnit;
    }

    /**
     * @return the abc
     */
    public String getAbc()
    {
        return abc;
    }

    /**
     * @param abc
     *            the abc to set
     */
    public void setAbc(String abc)
    {
        this.abc = abc;
    }

    /**
     * @return the batchModal
     */
    public String getBatchModal()
    {
        return batchModal;
    }

    /**
     * @param batchModal
     *            the batchModal to set
     */
    public void setBatchModal(String batchModal)
    {
        this.batchModal = batchModal;
    }

    /**
     * @return the checkDays
     */
    public int getCheckDays()
    {
        return checkDays;
    }

    /**
     * @param checkDays
     *            the checkDays to set
     */
    public void setCheckDays(int checkDays)
    {
        this.checkDays = checkDays;
    }

    /**
     * @return the maxStoreDays
     */
    public int getMaxStoreDays()
    {
        return maxStoreDays;
    }

    /**
     * @param maxStoreDays
     *            the maxStoreDays to set
     */
    public void setMaxStoreDays(int maxStoreDays)
    {
        this.maxStoreDays = maxStoreDays;
    }

    /**
     * @return the safeStoreDays
     */
    public int getSafeStoreDays()
    {
        return safeStoreDays;
    }

    /**
     * @param safeStoreDays
     *            the safeStoreDays to set
     */
    public void setSafeStoreDays(int safeStoreDays)
    {
        this.safeStoreDays = safeStoreDays;
    }

    /**
     * @return the makeDays
     */
    public int getMakeDays()
    {
        return makeDays;
    }

    /**
     * @param makeDays
     *            the makeDays to set
     */
    public void setMakeDays(int makeDays)
    {
        this.makeDays = makeDays;
    }

    /**
     * @return the flowDays
     */
    public int getFlowDays()
    {
        return flowDays;
    }

    /**
     * @param flowDays
     *            the flowDays to set
     */
    public void setFlowDays(int flowDays)
    {
        this.flowDays = flowDays;
    }

    /**
     * @return the minAmount
     */
    public int getMinAmount()
    {
        return minAmount;
    }

    /**
     * @param minAmount
     *            the minAmount to set
     */
    public void setMinAmount(int minAmount)
    {
        this.minAmount = minAmount;
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
     * @return the assembleFlag
     */
    public String getAssembleFlag()
    {
        return assembleFlag;
    }

    /**
     * @param assembleFlag
     *            the assembleFlag to set
     */
    public void setAssembleFlag(String assembleFlag)
    {
        this.assembleFlag = assembleFlag;
    }

    /**
     * @return the lastOrderDate
     */
    public String getLastOrderDate()
    {
        return lastOrderDate;
    }

    /**
     * @param lastOrderDate
     *            the lastOrderDate to set
     */
    public void setLastOrderDate(String lastOrderDate)
    {
        this.lastOrderDate = lastOrderDate;
    }

    /**
     * @return the consumeInDay
     */
    public int getConsumeInDay()
    {
        return consumeInDay;
    }

    /**
     * @param consumeInDay
     *            the consumeInDay to set
     */
    public void setConsumeInDay(int consumeInDay)
    {
        this.consumeInDay = consumeInDay;
    }

    /**
     * @return the orderAmount
     */
    public int getOrderAmount()
    {
        return orderAmount;
    }

    /**
     * @param orderAmount
     *            the orderAmount to set
     */
    public void setOrderAmount(int orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    /**
     * @return the mainProvider
     */
    public String getMainProvider()
    {
        return mainProvider;
    }

    /**
     * @param mainProvider
     *            the mainProvider to set
     */
    public void setMainProvider(String mainProvider)
    {
        this.mainProvider = mainProvider;
    }

    /**
     * @return the assistantProvider1
     */
    public String getAssistantProvider1()
    {
        return assistantProvider1;
    }

    /**
     * @param assistantProvider1
     *            the assistantProvider1 to set
     */
    public void setAssistantProvider1(String assistantProvider1)
    {
        this.assistantProvider1 = assistantProvider1;
    }

    /**
     * @return the assistantProvider2
     */
    public String getAssistantProvider2()
    {
        return assistantProvider2;
    }

    /**
     * @param assistantProvider2
     *            the assistantProvider2 to set
     */
    public void setAssistantProvider2(String assistantProvider2)
    {
        this.assistantProvider2 = assistantProvider2;
    }

    /**
     * @return the assistantProvider3
     */
    public String getAssistantProvider3()
    {
        return assistantProvider3;
    }

    /**
     * @param assistantProvider3
     *            the assistantProvider3 to set
     */
    public void setAssistantProvider3(String assistantProvider3)
    {
        this.assistantProvider3 = assistantProvider3;
    }

    /**
     * @return the sailType
     */
    public int getSailType()
    {
        return sailType;
    }

    /**
     * @param sailType
     *            the sailType to set
     */
    public void setSailType(int sailType)
    {
        this.sailType = sailType;
    }

    /**
     * @return the adjustPrice
     */
    public int getAdjustPrice()
    {
        return adjustPrice;
    }

    /**
     * @param adjustPrice
     *            the adjustPrice to set
     */
    public void setAdjustPrice(int adjustPrice)
    {
        this.adjustPrice = adjustPrice;
    }

    /**
     * @return the financeType
     */
    public String getFinanceType()
    {
        return financeType;
    }

    /**
     * @param financeType
     *            the financeType to set
     */
    public void setFinanceType(String financeType)
    {
        this.financeType = financeType;
    }

    /**
     * @return the dutyType
     */
    public String getDutyType()
    {
        return dutyType;
    }

    /**
     * @param dutyType
     *            the dutyType to set
     */
    public void setDutyType(String dutyType)
    {
        this.dutyType = dutyType;
    }

    /**
     * @return the cost
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * @param cost
     *            the cost to set
     */
    public void setCost(double cost)
    {
        this.cost = cost;
    }

    /**
     * @return the planCost
     */
    public double getPlanCost()
    {
        return planCost;
    }

    /**
     * @param planCost
     *            the planCost to set
     */
    public void setPlanCost(double planCost)
    {
        this.planCost = planCost;
    }

    /**
     * @return the batchPrice
     */
    public double getBatchPrice()
    {
        return batchPrice;
    }

    /**
     * @param batchPrice
     *            the batchPrice to set
     */
    public void setBatchPrice(double batchPrice)
    {
        this.batchPrice = batchPrice;
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
     * @return the checkFlag
     */
    public String getCheckFlag()
    {
        return checkFlag;
    }

    /**
     * @param checkFlag
     *            the checkFlag to set
     */
    public void setCheckFlag(String checkFlag)
    {
        this.checkFlag = checkFlag;
    }

    /**
     * @return the checkType
     */
    public int getCheckType()
    {
        return checkType;
    }

    /**
     * @param checkType
     *            the checkType to set
     */
    public void setCheckType(int checkType)
    {
        this.checkType = checkType;
    }

    /**
     * @return the checkStandard
     */
    public String getCheckStandard()
    {
        return checkStandard;
    }

    /**
     * @param checkStandard
     *            the checkStandard to set
     */
    public void setCheckStandard(String checkStandard)
    {
        this.checkStandard = checkStandard;
    }

    /**
     * @return the picPath
     */
    public String getPicPath()
    {
        return picPath;
    }

    /**
     * @param picPath
     *            the picPath to set
     */
    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
    }

    /**
     * @return the ptype
     */
    public int getPtype()
    {
        return ptype;
    }

    /**
     * @param ptype
     *            the ptype to set
     */
    public void setPtype(int ptype)
    {
        this.ptype = ptype;
    }

    /**
     * @return the ctype
     */
    public int getCtype()
    {
        return ctype;
    }

    /**
     * @param ctype
     *            the ctype to set
     */
    public void setCtype(int ctype)
    {
        this.ctype = ctype;
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
     * @return the createrId
     */
    public String getCreaterId()
    {
        return createrId;
    }

    /**
     * @param createrId
     *            the createrId to set
     */
    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    /**
     * @return the vsList
     */
    public List<ProductCombinationBean> getVsList()
    {
        return vsList;
    }

    /**
     * @param vsList
     *            the vsList to set
     */
    public void setVsList(List<ProductCombinationBean> vsList)
    {
        this.vsList = vsList;
    }

    /**
     * @return the reserve1
     */
    public String getReserve1()
    {
        return reserve1;
    }

    /**
     * @param reserve1
     *            the reserve1 to set
     */
    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }

    /**
     * @return the reserve2
     */
    public String getReserve2()
    {
        return reserve2;
    }

    /**
     * @param reserve2
     *            the reserve2 to set
     */
    public void setReserve2(String reserve2)
    {
        this.reserve2 = reserve2;
    }

    /**
     * @return the reserve3
     */
    public String getReserve3()
    {
        return reserve3;
    }

    /**
     * @param reserve3
     *            the reserve3 to set
     */
    public void setReserve3(String reserve3)
    {
        this.reserve3 = reserve3;
    }

    /**
     * @return the reserve4
     */
    public String getReserve4()
    {
        return reserve4;
    }

    /**
     * @param reserve4
     *            the reserve4 to set
     */
    public void setReserve4(String reserve4)
    {
        this.reserve4 = reserve4;
    }

    /**
     * @return the reserve5
     */
    public String getReserve5()
    {
        return reserve5;
    }

    /**
     * @param reserve5
     *            the reserve5 to set
     */
    public void setReserve5(String reserve5)
    {
        this.reserve5 = reserve5;
    }

    /**
     * @return the reserve6
     */
    public String getReserve6()
    {
        return reserve6;
    }

    /**
     * @param reserve6
     *            the reserve6 to set
     */
    public void setReserve6(String reserve6)
    {
        this.reserve6 = reserve6;
    }

    /**
     * @return the fullspell
     */
    public String getFullspell()
    {
        return fullspell;
    }

    /**
     * @param fullspell
     *            the fullspell to set
     */
    public void setFullspell(String fullspell)
    {
        this.fullspell = fullspell;
    }

    /**
     * @return the shortspell
     */
    public String getShortspell()
    {
        return shortspell;
    }

    /**
     * @param shortspell
     *            the shortspell to set
     */
    public void setShortspell(String shortspell)
    {
        this.shortspell = shortspell;
    }

    /**
     * @return the assistantProvider4
     */
    public String getAssistantProvider4()
    {
        return assistantProvider4;
    }

    /**
     * @param assistantProvider4
     *            the assistantProvider4 to set
     */
    public void setAssistantProvider4(String assistantProvider4)
    {
        this.assistantProvider4 = assistantProvider4;
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
            .append("ProductBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("abstractType = ")
            .append(this.abstractType)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("ptype = ")
            .append(this.ptype)
            .append(TAB)
            .append("ctype = ")
            .append(this.ctype)
            .append(TAB)
            .append("typeName = ")
            .append(this.typeName)
            .append(TAB)
            .append("specification = ")
            .append(this.specification)
            .append(TAB)
            .append("model = ")
            .append(this.model)
            .append(TAB)
            .append("amountUnit = ")
            .append(this.amountUnit)
            .append(TAB)
            .append("weightUnit = ")
            .append(this.weightUnit)
            .append(TAB)
            .append("cubageUnit = ")
            .append(this.cubageUnit)
            .append(TAB)
            .append("version = ")
            .append(this.version)
            .append(TAB)
            .append("design = ")
            .append(this.design)
            .append(TAB)
            .append("stockType = ")
            .append(this.stockType)
            .append(TAB)
            .append("materielSource = ")
            .append(this.materielSource)
            .append(TAB)
            .append("storeUnit = ")
            .append(this.storeUnit)
            .append(TAB)
            .append("abc = ")
            .append(this.abc)
            .append(TAB)
            .append("batchModal = ")
            .append(this.batchModal)
            .append(TAB)
            .append("checkDays = ")
            .append(this.checkDays)
            .append(TAB)
            .append("maxStoreDays = ")
            .append(this.maxStoreDays)
            .append(TAB)
            .append("safeStoreDays = ")
            .append(this.safeStoreDays)
            .append(TAB)
            .append("makeDays = ")
            .append(this.makeDays)
            .append(TAB)
            .append("flowDays = ")
            .append(this.flowDays)
            .append(TAB)
            .append("minAmount = ")
            .append(this.minAmount)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("assembleFlag = ")
            .append(this.assembleFlag)
            .append(TAB)
            .append("lastOrderDate = ")
            .append(this.lastOrderDate)
            .append(TAB)
            .append("consumeInDay = ")
            .append(this.consumeInDay)
            .append(TAB)
            .append("orderAmount = ")
            .append(this.orderAmount)
            .append(TAB)
            .append("mainProvider = ")
            .append(this.mainProvider)
            .append(TAB)
            .append("assistantProvider1 = ")
            .append(this.assistantProvider1)
            .append(TAB)
            .append("assistantProvider2 = ")
            .append(this.assistantProvider2)
            .append(TAB)
            .append("assistantProvider3 = ")
            .append(this.assistantProvider3)
            .append(TAB)
            .append("assistantProvider4 = ")
            .append(this.assistantProvider4)
            .append(TAB)
            .append("sailType = ")
            .append(this.sailType)
            .append(TAB)
            .append("adjustPrice = ")
            .append(this.adjustPrice)
            .append(TAB)
            .append("financeType = ")
            .append(this.financeType)
            .append(TAB)
            .append("dutyType = ")
            .append(this.dutyType)
            .append(TAB)
            .append("cost = ")
            .append(this.cost)
            .append(TAB)
            .append("planCost = ")
            .append(this.planCost)
            .append(TAB)
            .append("batchPrice = ")
            .append(this.batchPrice)
            .append(TAB)
            .append("sailPrice = ")
            .append(this.sailPrice)
            .append(TAB)
            .append("checkFlag = ")
            .append(this.checkFlag)
            .append(TAB)
            .append("checkType = ")
            .append(this.checkType)
            .append(TAB)
            .append("checkStandard = ")
            .append(this.checkStandard)
            .append(TAB)
            .append("picPath = ")
            .append(this.picPath)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("createrId = ")
            .append(this.createrId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("fullspell = ")
            .append(this.fullspell)
            .append(TAB)
            .append("shortspell = ")
            .append(this.shortspell)
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
            .append("reserve6 = ")
            .append(this.reserve6)
            .append(TAB)
            .append("vsList = ")
            .append(this.vsList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
