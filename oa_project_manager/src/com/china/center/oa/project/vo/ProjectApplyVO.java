package com.china.center.oa.project.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.ProjectApplyBean;

@Entity(inherit = true)
public class ProjectApplyVO extends ProjectApplyBean
{

	@Relationship(relationField = "applyerId")
    private String applyerName = "";

    /**
     * default constructor
     */
    public ProjectApplyVO()
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("TcpApplyVO ( ").append(super.toString()).append(TAB).append("applyerName = ").append(
            this.applyerName).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
