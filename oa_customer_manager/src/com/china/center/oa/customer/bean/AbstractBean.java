/**
 * File Name: AbstractBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * AbstractBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see AbstractBean
 * @since 1.0
 */
public abstract class AbstractBean implements Serializable
{
    @Html(title = "客户编码", must = true, maxLength = 40, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String code = "";

    @Html(title = "省", must = true, type = Element.SELECT)
    @Join(tagClass = ProvinceBean.class)
    private String provinceId = "";

    @Html(title = "地市", must = true, type = Element.SELECT)
    @Join(tagClass = CityBean.class)
    private String cityId = "";

    @Html(title = "县区", type = Element.SELECT)
    @Join(tagClass = AreaBean.class, type = JoinType.LEFT)
    private String areaId = "";

    @Html(title = "性别", must = true, type = Element.SELECT)
    private int sex = PublicConstant.SEX_MAN;

    /**
     * 1.个人购买 2.团购
     */
    @Html(title = "客户性质", must = true, type = Element.SELECT)
    private int mtype = 0;

    @Html(title = "行业属性", must = true, type = Element.SELECT)
    private int htype = 0;

    /**
     * 事业部行业属性
     */
    @Html(title = "事业部属性", must = true, type = Element.SELECT)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String hlocal = "";

    @Html(title = "联系时间", type = Element.DATE)
    private String beginConnectTime = "";

    /**
     * 0:没有 1：有
     */
    @Html(title = "历史成交", must = true, type = Element.SELECT)
    private int blog = 0;

    @Html(title = "有无名片", must = true, type = Element.SELECT)
    private int card = 0;

    /**
     * 是否已经被算成新客户了 0:还没有 1：已经是
     */
    private int hasNew = 0;

    @Html(title = "职务", maxLength = 100)
    private String post = "";

    @Html(title = "QQ号码", must = true, maxLength = 40)
    private String qq = "";

    @Html(title = "MSN号码", maxLength = 40)
    private String msn = "";

    @Html(title = "网址", maxLength = 255)
    private String web = "";

    @Html(title = "生日", type = Element.DATE)
    private String birthday = "";

    @Html(title = "联系人", must = true)
    private String connector = "";

    @Html(title = "联系人部门")
    private String cdepartement = "";

    /**
     * locationId
     */
    @Join(tagClass = LocationBean.class)
    private String locationId = "999";

    /**
     * 0:终端 1:拓展
     */
    @Html(title = "客户类型", must = true, type = Element.SELECT)
    private int selltype = CustomerConstant.SELLTYPE_TER;

    /**
     * 0个人、1企业、2机关
     */
    @Html(title = "客户分类1", must = true, type = Element.SELECT)
    private int protype = 0;

    /**
     * 0新客户、1老客户
     */
    @Html(title = "客户分类2", must = true, type = Element.SELECT)
    private int newtype = CustomerConstant.NEWTYPE_NEW;

    /**
     * 0优质客户、1一般客户、2维护客户、3垃圾客户
     */
    @Html(title = "客户等级", must = true, type = Element.SELECT)
    private int qqtype = 0;

    /**
     * 0潜在客户、1意向客户、2确定客户、3谈判客户、4成交客户、5连续成交客户(real)
     */
    @Html(title = "开发进程", must = true, type = Element.SELECT)
    private int rtype = 0;

    /**
     * 0网络、1电话、2展会、3客户介绍、4朋友介绍 99其他
     */
    @Html(title = "客户来源", must = true, type = Element.SELECT)
    private int formtype = 0;

    /**
     * 客户信用的杠杆倍数(职员的信用*lever)(废弃此属性)
     */
    @Deprecated
    @Html(title = "信用杠杆倍数", type = Element.NUMBER)
    private int lever = CustomerConstant.DEFAULT_LEVER;

    /**
     * 0:ok 1:申请 2:删除
     */
    private int status = CustomerConstant.STATUS_OK;

    private String createrId = "";

    @Html(title = "公司/机构", maxLength = 200, must = true)
    private String company = "";

    @Html(title = "地址", maxLength = 200, must = true)
    private String address = "";

    @Html(title = "移动电话", maxLength = 200, must = true)
    private String handphone = "";

    @Html(title = "固话", maxLength = 200)
    private String tel = "";

    @Html(title = "电子邮箱", must = true, maxLength = 100)
    private String mail = "";

    @Html(title = "传真", maxLength = 40)
    private String fax = "";

    @Html(title = "邮编", maxLength = 40)
    private String postcode = "";

    @Html(title = "银行", maxLength = 40)
    private String bank = "";

    @Html(title = "银行帐号", maxLength = 40)
    private String accounts = "";

    @Html(title = "税号", maxLength = 100)
    private String dutycode = "";

    @Html(title = "指定物流", maxLength = 100)
    private String flowcom = "";

    private String loginTime = "";

    private String createTime = "";

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Join(tagClass = CreditLevelBean.class, type = JoinType.LEFT)
    private String creditLevelId = CustomerConstant.CREDITLEVELID_DEFAULT;

    private double creditVal = 30.0d;

    /**
     * 分配比例
     */
    @Html(title = "分配比例(事业部)", type = Element.NUMBER, maxLength = 6)
    private double assignPer1 = 0.0d;

    @Html(title = "分配比例(分公司)", type = Element.NUMBER, maxLength = 6)
    private double assignPer2 = 0.0d;

    @Html(title = "分配比例(产品部)", type = Element.NUMBER, maxLength = 6)
    private double assignPer3 = 0.0d;

    @Html(title = "分配比例(其他)", type = Element.NUMBER, maxLength = 6)
    private double assignPer4 = 0.0d;

    /**
     * 更新次数
     */
    private int creditUpdateTime = 0;

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;

    @Html(title = "预留1")
    private String reserve1 = "";

    @Html(title = "预留2")
    private String reserve2 = "";

    @Html(title = "预留3")
    private String reserve3 = "";

    @Html(title = "预留4")
    private String reserve4 = "";

    @Html(title = "预留5")
    private String reserve5 = "";

    @Html(title = "预留6")
    private String reserve6 = "";

    /**
     * 0:金融 1:非金融
     */
    @Html(title = "客户类型1", must = true, type = Element.SELECT)
    private int selltype1 = CustomerConstant.SELLTYPE1_FINANCE_OTHER;
    
    @Html(title = "银行层级", type = Element.SELECT)
    private int bankClass = 0;
    
    /**
     * default constructor
     */
    public AbstractBean()
    {
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
     * @return the provinceId
     */
    public String getProvinceId()
    {
        return provinceId;
    }

    /**
     * @param provinceId
     *            the provinceId to set
     */
    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    /**
     * @return the cityId
     */
    public String getCityId()
    {
        return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    /**
     * @return the sex
     */
    public int getSex()
    {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(int sex)
    {
        this.sex = sex;
    }

    /**
     * @return the selltype
     */
    public int getSelltype()
    {
        return selltype;
    }

    /**
     * @param selltype
     *            the selltype to set
     */
    public void setSelltype(int selltype)
    {
        this.selltype = selltype;
    }

    /**
     * @return the protype
     */
    public int getProtype()
    {
        return protype;
    }

    /**
     * @param protype
     *            the protype to set
     */
    public void setProtype(int protype)
    {
        this.protype = protype;
    }

    /**
     * @return the newtype
     */
    public int getNewtype()
    {
        return newtype;
    }

    /**
     * @param newtype
     *            the newtype to set
     */
    public void setNewtype(int newtype)
    {
        this.newtype = newtype;
    }

    /**
     * @return the qqtype
     */
    public int getQqtype()
    {
        return qqtype;
    }

    /**
     * @param qqtype
     *            the qqtype to set
     */
    public void setQqtype(int qqtype)
    {
        this.qqtype = qqtype;
    }

    /**
     * @return the rtype
     */
    public int getRtype()
    {
        return rtype;
    }

    /**
     * @param rtype
     *            the rtype to set
     */
    public void setRtype(int rtype)
    {
        this.rtype = rtype;
    }

    /**
     * @return the formtype
     */
    public int getFormtype()
    {
        return formtype;
    }

    /**
     * @param formtype
     *            the formtype to set
     */
    public void setFormtype(int formtype)
    {
        this.formtype = formtype;
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
     * @return the handphone
     */
    public String getHandphone()
    {
        return handphone;
    }

    /**
     * @param handphone
     *            the handphone to set
     */
    public void setHandphone(String handphone)
    {
        this.handphone = handphone;
    }

    /**
     * @return the tel
     */
    public String getTel()
    {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    /**
     * @return the mail
     */
    public String getMail()
    {
        return mail;
    }

    /**
     * @param mail
     *            the mail to set
     */
    public void setMail(String mail)
    {
        this.mail = mail;
    }

    /**
     * @return the fax
     */
    public String getFax()
    {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * @return the postcode
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    /**
     * @return the accounts
     */
    public String getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(String accounts)
    {
        this.accounts = accounts;
    }

    /**
     * @return the dutycode
     */
    public String getDutycode()
    {
        return dutycode;
    }

    /**
     * @param dutycode
     *            the dutycode to set
     */
    public void setDutycode(String dutycode)
    {
        this.dutycode = dutycode;
    }

    /**
     * @return the flowcom
     */
    public String getFlowcom()
    {
        return flowcom;
    }

    /**
     * @param flowcom
     *            the flowcom to set
     */
    public void setFlowcom(String flowcom)
    {
        this.flowcom = flowcom;
    }

    /**
     * @return the loginTime
     */
    public String getLoginTime()
    {
        return loginTime;
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLoginTime(String loginTime)
    {
        this.loginTime = loginTime;
    }

    /**
     * @return the bank
     */
    public String getBank()
    {
        return bank;
    }

    /**
     * @param bank
     *            the bank to set
     */
    public void setBank(String bank)
    {
        this.bank = bank;
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
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(String company)
    {
        this.company = company;
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
     * @return the htype
     */
    public int getHtype()
    {
        return htype;
    }

    /**
     * @param htype
     *            the htype to set
     */
    public void setHtype(int htype)
    {
        this.htype = htype;
    }

    /**
     * @return the beginConnectTime
     */
    public String getBeginConnectTime()
    {
        return beginConnectTime;
    }

    /**
     * @param beginConnectTime
     *            the beginConnectTime to set
     */
    public void setBeginConnectTime(String beginConnectTime)
    {
        this.beginConnectTime = beginConnectTime;
    }

    /**
     * @return the blog
     */
    public int getBlog()
    {
        return blog;
    }

    /**
     * @param blog
     *            the blog to set
     */
    public void setBlog(int blog)
    {
        this.blog = blog;
    }

    /**
     * @return the post
     */
    public String getPost()
    {
        return post;
    }

    /**
     * @param post
     *            the post to set
     */
    public void setPost(String post)
    {
        this.post = post;
    }

    /**
     * @return the qq
     */
    public String getQq()
    {
        return qq;
    }

    /**
     * @param qq
     *            the qq to set
     */
    public void setQq(String qq)
    {
        this.qq = qq;
    }

    /**
     * @return the msn
     */
    public String getMsn()
    {
        return msn;
    }

    /**
     * @param msn
     *            the msn to set
     */
    public void setMsn(String msn)
    {
        this.msn = msn;
    }

    /**
     * @return the web
     */
    public String getWeb()
    {
        return web;
    }

    /**
     * @param web
     *            the web to set
     */
    public void setWeb(String web)
    {
        this.web = web;
    }

    /**
     * @return the birthday
     */
    public String getBirthday()
    {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    /**
     * @return the card
     */
    public int getCard()
    {
        return card;
    }

    /**
     * @param card
     *            the card to set
     */
    public void setCard(int card)
    {
        this.card = card;
    }

    /**
     * @return the connector
     */
    public String getConnector()
    {
        return connector;
    }

    /**
     * @param connector
     *            the connector to set
     */
    public void setConnector(String connector)
    {
        this.connector = connector;
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
     * @return the hasNew
     */
    public int getHasNew()
    {
        return hasNew;
    }

    /**
     * @param hasNew
     *            the hasNew to set
     */
    public void setHasNew(int hasNew)
    {
        this.hasNew = hasNew;
    }

    /**
     * @return the cdepartement
     */
    public String getCdepartement()
    {
        return cdepartement;
    }

    /**
     * @param cdepartement
     *            the cdepartement to set
     */
    public void setCdepartement(String cdepartement)
    {
        this.cdepartement = cdepartement;
    }

    /**
     * @return the creditLevelId
     */
    public String getCreditLevelId()
    {
        return creditLevelId;
    }

    /**
     * @param creditLevelId
     *            the creditLevelId to set
     */
    public void setCreditLevelId(String creditLevelId)
    {
        this.creditLevelId = creditLevelId;
    }

    /**
     * @return the creditVal
     */
    public double getCreditVal()
    {
        return creditVal;
    }

    /**
     * @param creditVal
     *            the creditVal to set
     */
    public void setCreditVal(double creditVal)
    {
        this.creditVal = creditVal;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime()
    {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    /**
     * @return the creditUpdateTime
     */
    public int getCreditUpdateTime()
    {
        return creditUpdateTime;
    }

    /**
     * @param creditUpdateTime
     *            the creditUpdateTime to set
     */
    public void setCreditUpdateTime(int creditUpdateTime)
    {
        this.creditUpdateTime = creditUpdateTime;
    }

    /**
     * @return the hlocal
     */
    public String getHlocal()
    {
        return hlocal;
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
     * @return the assignPer1
     */
    public double getAssignPer1()
    {
        return assignPer1;
    }

    /**
     * @param assignPer1
     *            the assignPer1 to set
     */
    public void setAssignPer1(double assignPer1)
    {
        this.assignPer1 = assignPer1;
    }

    /**
     * @return the assignPer2
     */
    public double getAssignPer2()
    {
        return assignPer2;
    }

    /**
     * @param assignPer2
     *            the assignPer2 to set
     */
    public void setAssignPer2(double assignPer2)
    {
        this.assignPer2 = assignPer2;
    }

    /**
     * @return the assignPer3
     */
    public double getAssignPer3()
    {
        return assignPer3;
    }

    /**
     * @param assignPer3
     *            the assignPer3 to set
     */
    public void setAssignPer3(double assignPer3)
    {
        this.assignPer3 = assignPer3;
    }

    /**
     * @return the assignPer4
     */
    public double getAssignPer4()
    {
        return assignPer4;
    }

    /**
     * @param assignPer4
     *            the assignPer4 to set
     */
    public void setAssignPer4(double assignPer4)
    {
        this.assignPer4 = assignPer4;
    }

    /**
     * @param hlocal
     *            the hlocal to set
     */
    public void setHlocal(String hlocal)
    {
        this.hlocal = hlocal;
    }

    /**
     * @return the lever
     */
    @Deprecated
    public int getLever()
    {
        return lever;
    }

    /**
     * @param lever
     *            the lever to set
     */
    @Deprecated
    public void setLever(int lever)
    {
        this.lever = lever;
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

    public int getSelltype1() {
        return selltype1;
    }

    public void setSelltype1(int selltype1) {
        this.selltype1 = selltype1;
    }

    public int getBankClass()
	{
		return bankClass;
	}

	public void setBankClass(int bankClass)
	{
		this.bankClass = bankClass;
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
            .append("AbstractBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("provinceId = ")
            .append(this.provinceId)
            .append(TAB)
            .append("cityId = ")
            .append(this.cityId)
            .append(TAB)
            .append("areaId = ")
            .append(this.areaId)
            .append(TAB)
            .append("sex = ")
            .append(this.sex)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("htype = ")
            .append(this.htype)
            .append(TAB)
            .append("hlocal = ")
            .append(this.hlocal)
            .append(TAB)
            .append("beginConnectTime = ")
            .append(this.beginConnectTime)
            .append(TAB)
            .append("blog = ")
            .append(this.blog)
            .append(TAB)
            .append("card = ")
            .append(this.card)
            .append(TAB)
            .append("hasNew = ")
            .append(this.hasNew)
            .append(TAB)
            .append("post = ")
            .append(this.post)
            .append(TAB)
            .append("qq = ")
            .append(this.qq)
            .append(TAB)
            .append("msn = ")
            .append(this.msn)
            .append(TAB)
            .append("web = ")
            .append(this.web)
            .append(TAB)
            .append("birthday = ")
            .append(this.birthday)
            .append(TAB)
            .append("connector = ")
            .append(this.connector)
            .append(TAB)
            .append("cdepartement = ")
            .append(this.cdepartement)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("selltype = ")
            .append(this.selltype)
            .append(TAB)
            .append("protype = ")
            .append(this.protype)
            .append(TAB)
            .append("newtype = ")
            .append(this.newtype)
            .append(TAB)
            .append("qqtype = ")
            .append(this.qqtype)
            .append(TAB)
            .append("rtype = ")
            .append(this.rtype)
            .append(TAB)
            .append("formtype = ")
            .append(this.formtype)
            .append(TAB)
            .append("lever = ")
            .append(this.lever)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("createrId = ")
            .append(this.createrId)
            .append(TAB)
            .append("company = ")
            .append(this.company)
            .append(TAB)
            .append("address = ")
            .append(this.address)
            .append(TAB)
            .append("handphone = ")
            .append(this.handphone)
            .append(TAB)
            .append("tel = ")
            .append(this.tel)
            .append(TAB)
            .append("mail = ")
            .append(this.mail)
            .append(TAB)
            .append("fax = ")
            .append(this.fax)
            .append(TAB)
            .append("postcode = ")
            .append(this.postcode)
            .append(TAB)
            .append("bank = ")
            .append(this.bank)
            .append(TAB)
            .append("accounts = ")
            .append(this.accounts)
            .append(TAB)
            .append("dutycode = ")
            .append(this.dutycode)
            .append(TAB)
            .append("flowcom = ")
            .append(this.flowcom)
            .append(TAB)
            .append("loginTime = ")
            .append(this.loginTime)
            .append(TAB)
            .append("createTime = ")
            .append(this.createTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("creditLevelId = ")
            .append(this.creditLevelId)
            .append(TAB)
            .append("creditVal = ")
            .append(this.creditVal)
            .append(TAB)
            .append("assignPer1 = ")
            .append(this.assignPer1)
            .append(TAB)
            .append("assignPer2 = ")
            .append(this.assignPer2)
            .append(TAB)
            .append("assignPer3 = ")
            .append(this.assignPer3)
            .append(TAB)
            .append("assignPer4 = ")
            .append(this.assignPer4)
            .append(TAB)
            .append("creditUpdateTime = ")
            .append(this.creditUpdateTime)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
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
            .append("selltype1 = ")
            .append(this.selltype1)            
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
