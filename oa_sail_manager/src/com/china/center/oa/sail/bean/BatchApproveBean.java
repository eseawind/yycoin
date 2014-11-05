package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 
 * 中信批量导入审核
 * scope: 中信；
 * 待结算中心审批；待库管审批
 *
 * @author fangliwen 2013-10-14
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_BATCHAPPROVE")
public class BatchApproveBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String batchId = "";
	
	private String outId = "";
	
	private String applyId = "";
	
	/**
	 * 销信单状态
	 */
	private int status = 0;
	
	/**
	 * 通过/驳回
	 */
	private String action = "";
	
	private String reason = "";
	
	/**
	 * 检查是否通过检查
	 * 0:通过；1：未通过
	 */
	private int ret = 0;
	
	/**
	 * 检查结果
	 */
	private String result = "OK";
	
	/**
	 * 0:结算批量审批 ；1：库管批量审批
	 */
	private int type = -1;

	/**
	 * default construct
	 */
	public BatchApproveBean()
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
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	/**
	 * @return the applyId
	 */
	public String getApplyId()
	{
		return applyId;
	}

	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	/**
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return the reason
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
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
	 * @return the ret
	 */
	public int getRet()
	{
		return ret;
	}

	/**
	 * @param ret the ret to set
	 */
	public void setRet(int ret)
	{
		this.ret = ret;
	}

	/**
	 * @return the result
	 */
	public String getResult()
	{
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result)
	{
		this.result = result;
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
}
