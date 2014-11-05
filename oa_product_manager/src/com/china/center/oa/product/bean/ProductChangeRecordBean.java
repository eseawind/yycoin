package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * 仓区产品移交 记录
 * 
 * @author zhangyang
 * @version 2008-1-19
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_PRODUCTCHANGERECORD")
public class ProductChangeRecordBean implements Serializable 
{
	@Id
    private String id = "";
	
	private String flowid = "";
	
	/**
     * 原仓区ID
     */
    @Join(tagClass = DepotpartBean.class, type = JoinType.LEFT)
    private String oldStore = "";
    
    /**
     * 新仓区ID
     */
    @Join(tagClass = DepotpartBean.class,alias="depBean", type = JoinType.LEFT)
    private String newStore = "";
	
	
	
	/**
     * 产品ID
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = "";
    
    /**
     * 移动产品数量
     */
    private int amount=0;
    
    /**
     * 转移时间
     */
    private  String moveTime = "";
    
    /**
     * 移交人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String moveStaffer="";
    
    /**
     * 备注
     */
    
    private String reason;
    
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("productChangeRecordBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("flowid = ")
            .append(this.flowid)
            .append(TAB)
            .append("oldStore = ")
            .append(this.oldStore)
            .append(TAB)
            .append("newStore = ")
            .append(this.newStore)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("moveTime = ")
            .append(this.moveTime)
            .append(TAB)
            .append("reason = ")
            .append(this.reason)
            .append(TAB)
            .append("moveStaffer = ")
            .append(this.moveStaffer)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldStore() {
		return oldStore;
	}

	public void setOldStore(String oldStore) {
		this.oldStore = oldStore;
	}

	public String getNewStore() {
		return newStore;
	}

	public void setNewStore(String newStore) {
		this.newStore = newStore;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(String moveTime) {
		this.moveTime = moveTime;
	}


	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMoveStaffer() {
		return moveStaffer;
	}

	public void setMoveStaffer(String moveStaffer) {
		this.moveStaffer = moveStaffer;
	}
    
    
}
