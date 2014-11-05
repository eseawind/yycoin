/**
 * File Name: CustomerCheckVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.customer.bean.CustomerCheckBean;


/**
 * CustomerCheckVO
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCheckVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CustomerCheckVO extends CustomerCheckBean
{
    @Relationship(relationField = "applyerId")
    private String applyerName = "";

    @Relationship(relationField = "checkerId")
    private String checkerName = "";

    @Relationship(relationField = "approverId")
    private String approverName = "";

    /**
     * default constructor
     */
    public CustomerCheckVO()
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
     * @return the checkerName
     */
    public String getCheckerName()
    {
        return checkerName;
    }

    /**
     * @param checkerName
     *            the checkerName to set
     */
    public void setCheckerName(String checkerName)
    {
        this.checkerName = checkerName;
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
}
