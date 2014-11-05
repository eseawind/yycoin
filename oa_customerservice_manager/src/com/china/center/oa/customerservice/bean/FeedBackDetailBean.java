package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_FEEDBACK_DETAIL")
public class FeedBackDetailBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String taskId = "";
	
	/** 订单 */
	private String outId = "";
	
	private String productId = "";
	
	private String productName = "";
	
	private int pay = 0;
	
	/** 减去退货的数量 , 总数量=amount + hasBack */
	private int amount = 0;
	
	/** 退货数量 */
	private int hasBack = 0;
	
	private double price = 0.0d;
	
	private double money = 0.0d;
	
	/** 退货金额 */
	private double backMoney = 0.0d;
	
	/** 未退货部分未勾款数据*/
	private int noPayAmount = 0;
	
	/** 未退货部分未勾款金额*/
	private double  noPayMoneys = 0.0d;
	
	/**结算但未勾款的数量 */
	private int settleNoPayAmount = 0;
	
	/**结算但未勾款的金额 */
	private double settleNoPayMoney = 0.0d;
	
	@Ignore
	private String receiver = "";
	
	@Ignore
	private String mobile = "";

	public FeedBackDetailBean()
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

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public int getHasBack()
	{
		return hasBack;
	}

	public void setHasBack(int hasBack)
	{
		this.hasBack = hasBack;
	}

	public double getBackMoney()
	{
		return backMoney;
	}

	public void setBackMoney(double backMoney)
	{
		this.backMoney = backMoney;
	}

	public int getPay()
	{
		return pay;
	}

	public void setPay(int pay)
	{
		this.pay = pay;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public int getNoPayAmount()
	{
		return noPayAmount;
	}

	public void setNoPayAmount(int noPayAmount)
	{
		this.noPayAmount = noPayAmount;
	}

	public double getNoPayMoneys()
	{
		return noPayMoneys;
	}

	public void setNoPayMoneys(double noPayMoneys)
	{
		this.noPayMoneys = noPayMoneys;
	}

	public int getSettleNoPayAmount()
	{
		return settleNoPayAmount;
	}

	public void setSettleNoPayAmount(int settleNoPayAmount)
	{
		this.settleNoPayAmount = settleNoPayAmount;
	}

	public double getSettleNoPayMoney()
	{
		return settleNoPayMoney;
	}

	public void setSettleNoPayMoney(double settleNoPayMoney)
	{
		this.settleNoPayMoney = settleNoPayMoney;
	}
}
