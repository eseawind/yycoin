package com.china.center.oa.tcp.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.RebateApplyBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class RebateApplyVO extends RebateApplyBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    /**
     * 当前处理人
     */
    @Ignore
    private String processer = "";

    /**
     * 流程描述
     */
    @Ignore
    private String flowDescription = "";

    @Ignore
    private String showTotal = "";
    
    @Ignore
    private String showLastMoney = "";
    
	/**
	 * 
	 */
	public RebateApplyVO()
	{
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName()
	{
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	/**
	 * @return the processer
	 */
	public String getProcesser()
	{
		return processer;
	}

	/**
	 * @param processer the processer to set
	 */
	public void setProcesser(String processer)
	{
		this.processer = processer;
	}

	/**
	 * @return the flowDescription
	 */
	public String getFlowDescription()
	{
		return flowDescription;
	}

	/**
	 * @param flowDescription the flowDescription to set
	 */
	public void setFlowDescription(String flowDescription)
	{
		this.flowDescription = flowDescription;
	}

	/**
	 * @return the showTotal
	 */
	public String getShowTotal()
	{
		return showTotal;
	}

	/**
	 * @param showTotal the showTotal to set
	 */
	public void setShowTotal(String showTotal)
	{
		this.showTotal = showTotal;
	}

	/**
	 * @return the showLastMoney
	 */
	public String getShowLastMoney()
	{
		return showLastMoney;
	}

	/**
	 * @param showLastMoney the showLastMoney to set
	 */
	public void setShowLastMoney(String showLastMoney)
	{
		this.showLastMoney = showLastMoney;
	}
}
