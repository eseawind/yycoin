package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;

@Entity
@Table(name = "T_CENTER_OUT_AUDITRULE_ITEM")
public class AuditRuleItemBean implements Serializable 
{

	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 订单审批关联ID
	 */
	@FK
	private String refId = "";
	
	/**
	 * 产品管理类型
	 */
	private int managerType = -1;
	
	/**
	 * 产品类型
	 */
	private int productType = -1;
	
	/**
	 * 材质类型
	 */
	private int materiaType = -1;
	
	/**
	 * 纸币类型
	 */
	private int currencyType = -1;
	
	/**
	 * 产品
	 */
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT)
	private String productId = "";

	/**
	 * 付款条件
	 */
	private int payCondition = -1;
	
	/**
	 * 退货条件
	 */
	private int returnCondition = -1;
	
	/**
	 * 产品账期
	 */
	private int productPeriod = 0;
	
	/**
	 * 毛利率下限
	 */
	private int ratioDown = 0;
	
	/**
	 * 毛利率上限[0,100]
	 */
	private int ratioUp = 0;
	
	/**
	 * 利润周期[0,100]
	 */
	private int profitPeriod = 0;
	
	/**
	 * 小于销售价 0:是 1:否
	 */
	private int ltSailPrice = -1;
	
	/**
	 * 差异比例
	 */
	private double diffRatio = 0.0d;
	
	/**
	 * 最小毛利率
	 */
	private double minRatio = 0.0d;
	
	/**
	 * 销售账期  [1,180]
	 */
	private int accountPeriod = 0;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public int getManagerType()
	{
		return managerType;
	}

	public void setManagerType(int managerType)
	{
		this.managerType = managerType;
	}

	public int getProductType()
	{
		return productType;
	}

	public void setProductType(int productType)
	{
		this.productType = productType;
	}

	public int getMateriaType()
	{
		return materiaType;
	}

	public void setMateriaType(int materiaType)
	{
		this.materiaType = materiaType;
	}

	public int getCurrencyType()
	{
		return currencyType;
	}

	public void setCurrencyType(int currencyType)
	{
		this.currencyType = currencyType;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}
	
	public int getPayCondition()
	{
		return payCondition;
	}

	public void setPayCondition(int payCondition)
	{
		this.payCondition = payCondition;
	}

	public int getReturnCondition()
	{
		return returnCondition;
	}

	public void setReturnCondition(int returnCondition)
	{
		this.returnCondition = returnCondition;
	}

	public int getProductPeriod()
	{
		return productPeriod;
	}

	public void setProductPeriod(int productPeriod)
	{
		this.productPeriod = productPeriod;
	}

	public int getRatioDown()
	{
		return ratioDown;
	}

	public void setRatioDown(int ratioDown)
	{
		this.ratioDown = ratioDown;
	}

	public int getRatioUp()
	{
		return ratioUp;
	}

	public void setRatioUp(int ratioUp)
	{
		this.ratioUp = ratioUp;
	}

	public int getProfitPeriod()
	{
		return profitPeriod;
	}

	public void setProfitPeriod(int profitPeriod)
	{
		this.profitPeriod = profitPeriod;
	}

	public int getLtSailPrice()
	{
		return ltSailPrice;
	}

	public void setLtSailPrice(int ltSailPrice)
	{
		this.ltSailPrice = ltSailPrice;
	}

	public double getDiffRatio()
	{
		return diffRatio;
	}

	public void setDiffRatio(double diffRatio)
	{
		this.diffRatio = diffRatio;
	}

	public double getMinRatio()
	{
		return minRatio;
	}

	public void setMinRatio(double minRatio)
	{
		this.minRatio = minRatio;
	}

	public int getAccountPeriod()
	{
		return accountPeriod;
	}

	public void setAccountPeriod(int accountPeriod)
	{
		this.accountPeriod = accountPeriod;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AuditRuleItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("managerType = ")
            .append(this.managerType)
            .append(TAB)
            .append("productType = ")
            .append(this.productType)
            .append(TAB)
            .append("materiaType = ")
            .append(this.materiaType)
            .append(TAB)
            .append("currencyType = ")
            .append(this.currencyType)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("payCondition = ")
            .append(this.payCondition)
            .append(TAB)
            .append("returnCondition = ")
            .append(this.returnCondition)
            .append(TAB)
            .append("productPeriod = ")
            .append(this.productPeriod)
            .append(TAB)
            .append("ratioDown = ")
            .append(this.ratioDown)
            .append(TAB)
            .append("ratioUp = ")
            .append(this.ratioUp)
            .append(TAB)
            .append("profitPeriod = ")
            .append(this.profitPeriod)
            .append(TAB)
            .append("ltSailPrice = ")
            .append(this.ltSailPrice)
            .append(TAB)
            .append("diffRatio = ")
            .append(this.diffRatio)
            .append(TAB)
            .append("minRatio = ")
            .append(this.minRatio)
            .append(TAB)
            .append("accountPeriod = ")
            .append(this.accountPeriod)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
