package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.publics.bean.StafferBean;

@Entity
@Table(name = "T_CENTER_PRICE_CONFIG")
public class PriceConfigBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 产品ID
	 */
	@Join(tagClass = ProductBean.class)
	@Html(name = "productName", title = "产品", readonly = true, must = true)
	private String productId = "";
	
	/**
	 * 是否随金银价波动 0:否 1：金价 2：银价 (停用）
	 */
	@Html(title = "是否随金银价波动", must = true, type = Element.SELECT)
	private int isWave = 0;
	
	/**
	 * 辅料费用
	 */
	private double gsPriceUp = 0.0d;
	
	/**
	 * 金银价下限(停用）
	 */
	@Html(title = "金银价下限", type = Element.DOUBLE)
	private double gsPriceDown = 0.0d;
	
	/**
	 * 开始时间(停用）
	 */
	@Html(title = "开始时间", type = Element.DATE, must = false)
	private String beginDate = "";
	
	/**
	 * 结束时间(停用）
	 */
	@Html(title = "结束时间", type = Element.DATE, must = false)
	private String endDate = "";
	
	/**
	 * 事业部
	 */
	private String industryId = "";
	
	/**
	 * 职员
	 */
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	@Html(name = "stafferName", title = "职员", readonly = true)
	private String stafferId = "";
	
	/**
	 * 结算价 (合成产品不可输入)
	 */
	@Html(title = "结算价", type = Element.DOUBLE, must = true)
	private double price = 0.0d;
	
	/**
	 * 最低销售价
	 */
	@Html(title = "最低销售价", type = Element.DOUBLE, must = true)
	private double minPrice = 0.0d;
	
	/**
	 * 0:产品价格配置   1:产品结算价配置
	 */
	private int type = ProductConstant.PRICECONFIG_SAIL;

	/**
	 * 是否受金银价波动影响
	 */
	@Html(title = "受金银价波动影响", type = Element.CHECKBOX)
	private int ftype = 0;
	
	/**
	 * 金价
	 */
	@Ignore
	private double gold = 0.0d;
	
	/**
	 * 银价
	 */
	@Ignore
	private double silver = 0.0d;
	
	/**
	 * 结算价
	 */
	@Ignore
	private double sailPrice = 0.0d;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public int getIsWave()
	{
		return isWave;
	}

	public void setIsWave(int isWave)
	{
		this.isWave = isWave;
	}

	public double getGsPriceUp()
	{
		return gsPriceUp;
	}

	public void setGsPriceUp(double gsPriceUp)
	{
		this.gsPriceUp = gsPriceUp;
	}

	public double getGsPriceDown()
	{
		return gsPriceDown;
	}

	public void setGsPriceDown(double gsPriceDown)
	{
		this.gsPriceDown = gsPriceDown;
	}

	public String getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getMinPrice()
	{
		return minPrice;
	}

	public void setMinPrice(double minPrice)
	{
		this.minPrice = minPrice;
	}
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
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

	public double getSailPrice()
	{
		return sailPrice;
	}

	public void setSailPrice(double sailPrice)
	{
		this.sailPrice = sailPrice;
	}

	/**
	 * @return the ftype
	 */
	public int getFtype()
	{
		return ftype;
	}

	/**
	 * @param ftype the ftype to set
	 */
	public void setFtype(int ftype)
	{
		this.ftype = ftype;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductPriceConfigBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("isWave = ")
            .append(this.isWave)
            .append(TAB)
            .append("gsPriceUp = ")
            .append(this.gsPriceUp)
            .append(TAB)
            .append("gsPriceDown = ")
            .append(this.gsPriceDown)
            .append(TAB)
            .append("beginDate = ")
            .append(this.beginDate)
            .append(TAB)
            .append("endDate = ")
            .append(this.endDate)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("minPrice = ")
            .append(this.minPrice)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
