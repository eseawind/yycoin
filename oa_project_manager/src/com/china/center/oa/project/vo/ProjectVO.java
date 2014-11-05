/**
 * File Name: ProductVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.project.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.project.bean.ProjectBean;
import com.china.center.oa.project.bean.StafferProjectBean;



@Entity(inherit = true)
public class ProjectVO extends ProjectBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Relationship(relationField = "customerId")
    private String customerName = "";
	
	@Relationship(relationField = "creater")
    private String createrName = "";
	
	@Relationship(relationField = "processid")
    private String processer = "";

	 /**
     * 人员行项目
     */
    @Ignore
    protected List<StafferProjectVO> stafferProjectList = null;
    
    /**
     * 产品行项目
     */
    @Ignore
    protected List<ProLineProjectVO> proLineProjectList = null;

	/**
     * 流程描述
     */
    @Ignore
    private String flowDescription = "";
    
    
    /**
     * default constructor
     */
    public ProjectVO()
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
            .append("ProjectVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("processer = ")
            .append(this.processer)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append("createrName = ")
            .append(this.createrName)
            .append(TAB)
            .append("flowDescription = ")
            .append(this.flowDescription)
            .append(TAB)
            .append("stafferProjectList = ")
            .append(this.stafferProjectList)
            .append(TAB)
             .append("proLineProjectList = ")
            .append(this.proLineProjectList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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


	public List<StafferProjectVO> getStafferProjectList() {
		return stafferProjectList;
	}


	public void setStafferProjectList(List<StafferProjectVO> stafferProjectList) {
		this.stafferProjectList = stafferProjectList;
	}


	public List<ProLineProjectVO> getProLineProjectList() {
		return proLineProjectList;
	}


	public void setProLineProjectList(List<ProLineProjectVO> proLineProjectList) {
		this.proLineProjectList = proLineProjectList;
	}


	public String getCreaterName() {
		return createrName;
	}


	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
    
    
    
}
