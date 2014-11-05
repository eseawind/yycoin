/**
 * File Name: CustomerApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.bean;

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
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.constant.PublicConstant;

/**
 * CustomerBean
 * 
 * @author ZHUZHU
 * @version 2013-11-9
 * @see #com.china.center.oa.client.bean.CustomerBean#
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_CUSTOMER_MAIN")
public class CustomerBean extends AbstractCustomerBean 
{
	@Id
	private String id = "";

	@Unique
	@Html(title = "客户全称", must = true, maxLength = 100)
	private String name = "";
	
	@Html(title = "客户性质", must = true, type = Element.SELECT)
	private int type = 0;
	
	/**
	 * 0:没有 1：有
	 */
	@Html(title = "历史成交", type = Element.SELECT)
	private int historySales;

	@Html(title = "交易金额", type = Element.NUMBER)
	private double salesAmount = 0.0d;
	
	@Html(title = "联系次数", type = Element.NUMBER)
	private int contactTimes = 0;

	@Html(title = "最近联系时间")
	private String lastContactTime = "";
	
    @Join(tagClass = CreditLevelBean.class, type = JoinType.LEFT)
    private String creditLevelId = CustomerConstant.CREDITLEVELID_DEFAULT;

    private double creditVal = 30.0d;
    
    /**
     * 更新次数
     */
    private int creditUpdateTime = 0;
    
    /**
     * 空闲/使用/申请中/移交中
     */
    private int status = CustomerConstant.REAL_STATUS_IDLE;
    
    /**
     * 是否停用 0在用， 1停用
     */
    private int ostatus = 0;
    
    private String createTime = "";
    
    /**
     * 是否已经被算成新客户了 0:还没有 1：已经是
     */
    private int hasNew = 0;
    
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

	@Html(title = "预留7")
	private String reserve7 = "";

	@Html(title = "预留8")
	private String reserve8 = "";

	@Html(title = "预留9")
	private String reserve9 = "";

	@Html(title = "预留10")
	private String reserve10 = "";
	
    @Ignore
    private String handphone = "";

    @Ignore
    private String connector = "";
	
    @Ignore
    private List<CustomerBankBean> bankList = null;
	
	/**
	 * default constructor
	 */
	public CustomerBean() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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

	/**
	 * @return the creditLevelId
	 */
	public String getCreditLevelId()
	{
		return creditLevelId;
	}

	/**
	 * @param creditLevelId the creditLevelId to set
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
	 * @param creditVal the creditVal to set
	 */
	public void setCreditVal(double creditVal)
	{
		this.creditVal = creditVal;
	}

	/**
	 * @return the creditUpdateTime
	 */
	public int getCreditUpdateTime()
	{
		return creditUpdateTime;
	}

	/**
	 * @param creditUpdateTime the creditUpdateTime to set
	 */
	public void setCreditUpdateTime(int creditUpdateTime)
	{
		this.creditUpdateTime = creditUpdateTime;
	}

	/**
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * @return the ostatus
	 */
	public int getOstatus()
	{
		return ostatus;
	}

	/**
	 * @param ostatus the ostatus to set
	 */
	public void setOstatus(int ostatus)
	{
		this.ostatus = ostatus;
	}

	public String getReserve1()
	{
		return reserve1;
	}

	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}

	public String getReserve2()
	{
		return reserve2;
	}

	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}

	public String getReserve3()
	{
		return reserve3;
	}

	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}

	public String getReserve4()
	{
		return reserve4;
	}

	public void setReserve4(String reserve4)
	{
		this.reserve4 = reserve4;
	}

	public String getReserve5()
	{
		return reserve5;
	}

	public void setReserve5(String reserve5)
	{
		this.reserve5 = reserve5;
	}

	public String getReserve6()
	{
		return reserve6;
	}

	public void setReserve6(String reserve6)
	{
		this.reserve6 = reserve6;
	}

	public String getReserve7()
	{
		return reserve7;
	}

	public void setReserve7(String reserve7)
	{
		this.reserve7 = reserve7;
	}

	public String getReserve8()
	{
		return reserve8;
	}

	public void setReserve8(String reserve8)
	{
		this.reserve8 = reserve8;
	}

	public String getReserve9()
	{
		return reserve9;
	}

	public void setReserve9(String reserve9)
	{
		this.reserve9 = reserve9;
	}

	public String getReserve10()
	{
		return reserve10;
	}

	public void setReserve10(String reserve10)
	{
		this.reserve10 = reserve10;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the historySales
	 */
	public int getHistorySales()
	{
		return historySales;
	}

	/**
	 * @param historySales the historySales to set
	 */
	public void setHistorySales(int historySales)
	{
		this.historySales = historySales;
	}

	/**
	 * @return the salesAmount
	 */
	public double getSalesAmount()
	{
		return salesAmount;
	}

	/**
	 * @param salesAmount the salesAmount to set
	 */
	public void setSalesAmount(double salesAmount)
	{
		this.salesAmount = salesAmount;
	}

	/**
	 * @return the contactTimes
	 */
	public int getContactTimes()
	{
		return contactTimes;
	}

	/**
	 * @param contactTimes the contactTimes to set
	 */
	public void setContactTimes(int contactTimes)
	{
		this.contactTimes = contactTimes;
	}

	/**
	 * @return the lastContactTime
	 */
	public String getLastContactTime()
	{
		return lastContactTime;
	}

	/**
	 * @param lastContactTime the lastContactTime to set
	 */
	public void setLastContactTime(String lastContactTime)
	{
		this.lastContactTime = lastContactTime;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime()
	{
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * @return the handphone
	 */
	public String getHandphone()
	{
		return handphone;
	}

	/**
	 * @param handphone the handphone to set
	 */
	public void setHandphone(String handphone)
	{
		this.handphone = handphone;
	}

	/**
	 * @return the connector
	 */
	public String getConnector()
	{
		return connector;
	}

	/**
	 * @param connector the connector to set
	 */
	public void setConnector(String connector)
	{
		this.connector = connector;
	}

	/**
	 * @return the bankList
	 */
	public List<CustomerBankBean> getBankList()
	{
		return bankList;
	}

	/**
	 * @param bankList the bankList to set
	 */
	public void setBankList(List<CustomerBankBean> bankList)
	{
		this.bankList = bankList;
	}

	/**
	 * @return the hasNew
	 */
	public int getHasNew()
	{
		return hasNew;
	}

	/**
	 * @param hasNew the hasNew to set
	 */
	public void setHasNew(int hasNew)
	{
		this.hasNew = hasNew;
	}

	/**
	 * @return the checks
	 */
	public String getChecks()
	{
		return checks;
	}

	/**
	 * @param checks the checks to set
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
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(int checkStatus)
	{
		this.checkStatus = checkStatus;
	}
}
