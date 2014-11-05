package com.china.center.oa.project.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.TaskBean;

@Entity(inherit = true)
public class TaskVO extends TaskBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Relationship(relationField = "dutyStaffer")
    private String dutyName = "";
	
	@Relationship(relationField = "receiver")
    private String receiverName = "";
	
	@Relationship(relationField = "processid")
    private String processer = "";
	
	@Relationship(relationField = "applyer")
	private String applyerName = "";
	
	/**
     * 流程描述
     */
    @Ignore
    private String flowDescription = "";

	public TaskVO()
	{
		
	}
	
	 /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("TaskVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("processer = ")
            .append(this.processer)
            .append(TAB)
            .append("receiverName = ")
            .append(this.receiverName)
            .append(TAB)
             .append("flowDescription = ")
            .append(this.flowDescription)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getProcesser() {
		return processer;
	}

	public void setProcesser(String processer) {
		this.processer = processer;
	}

	public String getFlowDescription() {
		return flowDescription;
	}

	public void setFlowDescription(String flowDescription) {
		this.flowDescription = flowDescription;
	}

	/**
	 * @return the applyerName
	 */
	public String getApplyerName() {
		return applyerName;
	}

	/**
	 * @param applyerName the applyerName to set
	 */
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}
}
