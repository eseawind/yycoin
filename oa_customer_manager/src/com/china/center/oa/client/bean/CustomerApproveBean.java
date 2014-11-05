package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * 
 * 客户审批
 *
 * @author fangliwen 2013-11-13
 */
@SuppressWarnings("serial")
@Entity(name="客户申请审批")
@Table(name = "T_CENTER_CUSTAPPROVE")
public class CustomerApproveBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 客户名称
	 */
	@Unique
	private String name = "";
	
	private String code = "";
	
	private int type = 0;
	
	/**
	 * 0: 客户操作申请;1:客户分配操作申请
	 */
	private int applyType = 0;
	
	/**
	 * 101 客户类型
	 */
	private int selltype = CustomerConstant.SELLTYPE_TER;

	/**
	 * 102 客户分类1
	 */
	private int protype = 0;

	/**
	 * 103 客户分类2
	 */
	private int protype2 = 0;

    /**
     * 104 客户等级
     */
    private int qqtype = 0;
    
    /**
     * 105 开发进程
     */
    private int rtype = 0;
	
	/**
	 * 106 客户来源
	 */
	private int fromType = 0;
	
	/**
	 * 状态 0:ok 1:申请 2:删除
	 */
	private int status = CustomerConstant.STATUS_OK;
	
	private String logTime = "";
	
    /**
     * 0:add 1:update 2:delete 3:update2
     */
    private int opr = CustomerConstant.OPR_ADD;

    /**
     * updaterId
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String applyId = "";

    private String oprReson = "";

    private String reson = "";

    private String operator = "";
    
    private String operatorName = "";

	/**
	 * 
	 */
	public CustomerApproveBean()
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
	 * @return the applyType
	 */
	public int getApplyType()
	{
		return applyType;
	}

	/**
	 * @param applyType the applyType to set
	 */
	public void setApplyType(int applyType)
	{
		this.applyType = applyType;
	}

	/**
	 * @return the selltype
	 */
	public int getSelltype()
	{
		return selltype;
	}

	/**
	 * @param selltype the selltype to set
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
	 * @param protype the protype to set
	 */
	public void setProtype(int protype)
	{
		this.protype = protype;
	}

	/**
	 * @return the protype2
	 */
	public int getProtype2()
	{
		return protype2;
	}

	/**
	 * @param protype2 the protype2 to set
	 */
	public void setProtype2(int protype2)
	{
		this.protype2 = protype2;
	}

	/**
	 * @return the qqtype
	 */
	public int getQqtype()
	{
		return qqtype;
	}

	/**
	 * @param qqtype the qqtype to set
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
	 * @param rtype the rtype to set
	 */
	public void setRtype(int rtype)
	{
		this.rtype = rtype;
	}

	/**
	 * @return the fromType
	 */
	public int getFromType()
	{
		return fromType;
	}

	/**
	 * @param fromType the fromType to set
	 */
	public void setFromType(int fromType)
	{
		this.fromType = fromType;
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
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the opr
	 */
	public int getOpr()
	{
		return opr;
	}

	/**
	 * @param opr the opr to set
	 */
	public void setOpr(int opr)
	{
		this.opr = opr;
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
	 * @return the oprReson
	 */
	public String getOprReson()
	{
		return oprReson;
	}

	/**
	 * @param oprReson the oprReson to set
	 */
	public void setOprReson(String oprReson)
	{
		this.oprReson = oprReson;
	}

	/**
	 * @return the reson
	 */
	public String getReson()
	{
		return reson;
	}

	/**
	 * @param reson the reson to set
	 */
	public void setReson(String reson)
	{
		this.reson = reson;
	}

	/**
	 * @return the operator
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName()
	{
		return operatorName;
	}

	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
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
