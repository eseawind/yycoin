package com.china.center.oa.sail.wrap;

import java.io.Serializable;

public class BatchBackWrap implements Serializable
{
	/** 批量退货的产品  */
	private String productId = "";
	
	/** 委托代销 行项目ID */
	private String baseId = "";
	
	/** 产品所在的原销售单 */
	private String refOutFullId = "";
	
	/** 结算单退货时关联原结算单号 */
	private String refId = "";
	
	/** 针对委托代销，类型：0,未结算的产品 1,已结算的产品 */
	private int type = 0;
	
	/** 参与退货的数量 */
	private int amount = 0;
	
	/** 产品 */
	private String productName = "";

	public BatchBackWrap()
	{
		
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getRefOutFullId()
	{
		return refOutFullId;
	}

	public void setRefOutFullId(String refOutFullId)
	{
		this.refOutFullId = refOutFullId;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public String getBaseId()
	{
		return baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
}
