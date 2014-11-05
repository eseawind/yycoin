package com.china.center.oa.customerservice.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_FEEDBACK")
public class FeedBackBean implements Serializable
{
	/** 回访、对账任务号 */
	@Id
	private String id = "";
	
	/** 客户 */
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
	private String customerId = "";
	
	/** 业务员 */
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	private String stafferId = "";
	
	/** 销售单数量 */
	private int outCount = 0;
	
	/** 产品数量 */
	private int productCount = 0;
	
	/** 销售金额 */
	private double moneys = 0.0d;
	
	private double hadpay = 0.0d;
	
	/** 未付款金额 */
	private double noPayMoneys = 0.0d;
	
	/** 任务类型 1回访 2对帐 3异常处理 4异常回访 */
	private int type = 0;
	
	/** 任务状态 1待分配，2待接受，3处理中，4 异常待处理中 5已完成 */
	private int status;
	
	/** 负责人  */
	private String bear = "";
	
	/** 负责人  */
	private String bearName = "";

	/** 客户信用  - 实时获取*/
	@Ignore
	private double custCredit = 0.0d;
	
	/** 业务员信用 - 实时获取 */
	@Ignore
	private double staffCredit = 0.0d;
	
	/** 客户预收  - 实时获取*/
	@Ignore
	private double preMoney = 0.0d;

	private String logTime = "";
	
	/** 最后一次回访异常ID*/
	private String refVisitId = "";
	
	/** 异常回访 预计处理 日期*/
	private String forecastDate = "";
	
	private String statsStar = "";
	
	private String statsEnd = "";
	
	private String industryIdName = "";
	
	private int pstatus = 99; 
	
	@Html(title = "客户Email")
	private String mailAddress = "";
	
	@Ignore
	private List<FeedBackVSOutBean> vsList = null;
	
	@Ignore
	private List<FeedBackDetailBean> detailList = null;
	
	public FeedBackBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public int getOutCount()
	{
		return outCount;
	}

	public void setOutCount(int outCount)
	{
		this.outCount = outCount;
	}

	public int getProductCount()
	{
		return productCount;
	}

	public void setProductCount(int productCount)
	{
		this.productCount = productCount;
	}

	public double getMoneys()
	{
		return moneys;
	}

	public void setMoneys(double moneys)
	{
		this.moneys = moneys;
	}

	public double getNoPayMoneys()
	{
		return noPayMoneys;
	}

	public void setNoPayMoneys(double noPayMoneys)
	{
		this.noPayMoneys = noPayMoneys;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getBear()
	{
		return bear;
	}

	public void setBear(String bear)
	{
		this.bear = bear;
	}

	public double getCustCredit()
	{
		return custCredit;
	}

	public void setCustCredit(double custCredit)
	{
		this.custCredit = custCredit;
	}

	public double getStaffCredit()
	{
		return staffCredit;
	}

	public void setStaffCredit(double staffCredit)
	{
		this.staffCredit = staffCredit;
	}

	public double getPreMoney()
	{
		return preMoney;
	}

	public void setPreMoney(double preMoney)
	{
		this.preMoney = preMoney;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public List<FeedBackVSOutBean> getVsList()
	{
		return vsList;
	}

	public void setVsList(List<FeedBackVSOutBean> vsList)
	{
		this.vsList = vsList;
	}

	public List<FeedBackDetailBean> getDetailList()
	{
		return detailList;
	}

	public void setDetailList(List<FeedBackDetailBean> detailList)
	{
		this.detailList = detailList;
	}

	public String getBearName()
	{
		return bearName;
	}

	public void setBearName(String bearName)
	{
		this.bearName = bearName;
	}

	public String getRefVisitId()
	{
		return refVisitId;
	}

	public void setRefVisitId(String refVisitId)
	{
		this.refVisitId = refVisitId;
	}

	public String getForecastDate()
	{
		return forecastDate;
	}

	public void setForecastDate(String forecastDate)
	{
		this.forecastDate = forecastDate;
	}

	public String getMailAddress()
	{
		return mailAddress;
	}

	public void setMailAddress(String mailAddress)
	{
		this.mailAddress = mailAddress;
	}

	public String getStatsStar()
	{
		return statsStar;
	}

	public void setStatsStar(String statsStar)
	{
		this.statsStar = statsStar;
	}

	public String getStatsEnd()
	{
		return statsEnd;
	}

	public void setStatsEnd(String statsEnd)
	{
		this.statsEnd = statsEnd;
	}

	public String getIndustryIdName()
	{
		return industryIdName;
	}

	public void setIndustryIdName(String industryIdName)
	{
		this.industryIdName = industryIdName;
	}

	public double getHadpay()
	{
		return hadpay;
	}

	public void setHadpay(double hadpay)
	{
		this.hadpay = hadpay;
	}

	public int getPstatus()
	{
		return pstatus;
	}

	public void setPstatus(int pstatus)
	{
		this.pstatus = pstatus;
	}
}
