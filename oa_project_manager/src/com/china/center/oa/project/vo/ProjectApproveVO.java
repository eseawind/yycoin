package com.china.center.oa.project.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.ProjectApproveBean;

@Entity(inherit = true)
public class ProjectApproveVO extends ProjectApproveBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5740341319655621904L;

	@Relationship(relationField = "applyerId")
    private String applyerName = "";

    @Relationship(relationField = "approverId")
    private String approverName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    @Ignore
    private String url = "";

    /**
     * default constructor
     */
    public ProjectApproveVO()
    {
    }

    /**
     * @return the applyerName
     */
    public String getApplyerName()
    {
        return applyerName;
    }

    /**
     * @param applyerName
     *            the applyerName to set
     */
    public void setApplyerName(String applyerName)
    {
        this.applyerName = applyerName;
    }

    /**
     * @return the approverName
     */
    public String getApproverName()
    {
        return approverName;
    }

    /**
     * @param approverName
     *            the approverName to set
     */
    public void setApproverName(String approverName)
    {
        this.approverName = approverName;
    }


    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
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
            .append("TcpApproveVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("applyerName = ")
            .append(this.applyerName)
            .append(TAB)
            .append("approverName = ")
            .append(this.approverName)
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("url = ")
            .append(this.url)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
