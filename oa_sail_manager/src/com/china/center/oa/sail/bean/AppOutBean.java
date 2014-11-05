package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.AppUserBean;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_APPOUT")
public class AppOutBean implements Serializable
{
    /**
     * 排序ID
     */
    private String id = "";
	
    @Id
	private String orderNo = "";
	
	private String outTime = "";
	
	private String outDate = "";
	
	private int status = 0;
	
	@Join(tagClass=AppUserBean.class, type = JoinType.LEFT)
	private String userId = "";
	
	private String activity = "";
	
	/**
	 * 优惠金额
	 */
	private double sale = 0.0d;
	
	/**
	 * 付款金额
	 */
	private double pay = 0.0d;
	
	private double total = 0.0d;
	
	private String payAccount = "";
	
	private int payStatus = 0;
	
	private String description = "";

	/**
	 * OA 库单号 TM....
	 */
	private String outId = "";
	
	/**
	 * 0：未生成OA 订单， 1：生成OA 订单
	 */
	private int oStatus = 0;
	
	@Ignore
	private List<AppBaseBean> items = null;
	
	@Ignore
	private AppInvoiceBean invoice = null;
	
	@Ignore
	private AppDistributionBean distribution = null;

	/**
	 * 
	 */
	public AppOutBean()
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

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getOutTime()
	{
		return outTime;
	}

	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}

	public String getOutDate()
	{
		return outDate;
	}

	public void setOutDate(String outDate)
	{
		this.outDate = outDate;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getActivity()
	{
		return activity;
	}

	public void setActivity(String activity)
	{
		this.activity = activity;
	}

	public double getSale()
	{
		return sale;
	}

	public void setSale(double sale)
	{
		this.sale = sale;
	}

	public double getPay()
	{
		return pay;
	}

	public void setPay(double pay)
	{
		this.pay = pay;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public String getPayAccount()
	{
		return payAccount;
	}

	public void setPayAccount(String payAccount)
	{
		this.payAccount = payAccount;
	}

	public int getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(int payStatus)
	{
		this.payStatus = payStatus;
	}

	public List<AppBaseBean> getItems()
	{
		return items;
	}

	public void setItems(List<AppBaseBean> items)
	{
		this.items = items;
	}

	public AppInvoiceBean getInvoice()
	{
		return invoice;
	}

	public void setInvoice(AppInvoiceBean invoice)
	{
		this.invoice = invoice;
	}

	public AppDistributionBean getDistribution()
	{
		return distribution;
	}

	public void setDistribution(AppDistributionBean distribution)
	{
		this.distribution = distribution;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getoStatus()
	{
		return oStatus;
	}

	public void setoStatus(int oStatus)
	{
		this.oStatus = oStatus;
	}
}
