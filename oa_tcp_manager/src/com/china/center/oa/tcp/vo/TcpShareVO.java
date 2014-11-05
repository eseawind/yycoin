/**
 * File Name: TcpShareVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.TcpShareBean;


/**
 * TcpShareVO
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TcpShareVO
 * @since 3.0
 */
@Entity(inherit = true)
public class TcpShareVO extends TcpShareBean
{
    @Relationship(relationField = "approverId")
    private String approverName = "";

    @Relationship(relationField = "bearId")
    private String bearName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    @Relationship(relationField = "budgetId")
    private String budgetName = "";

    @Ignore
    private String showRealMonery = "";

    /**
     * default constructor
     */
    public TcpShareVO()
    {
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
     * @return the budgetName
     */
    public String getBudgetName()
    {
        return budgetName;
    }

    /**
     * @param budgetName
     *            the budgetName to set
     */
    public void setBudgetName(String budgetName)
    {
        this.budgetName = budgetName;
    }

    /**
     * @return the showRealMonery
     */
    public String getShowRealMonery()
    {
        return showRealMonery;
    }

    /**
     * @param showRealMonery
     *            the showRealMonery to set
     */
    public void setShowRealMonery(String showRealMonery)
    {
        this.showRealMonery = showRealMonery;
    }

    /**
     * @return the bearName
     */
    public String getBearName()
    {
        return bearName;
    }

    /**
     * @param bearName
     *            the bearName to set
     */
    public void setBearName(String bearName)
    {
        this.bearName = bearName;
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
            .append("TcpShareVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("approverName = ")
            .append(this.approverName)
            .append(TAB)
            .append("bearName = ")
            .append(this.bearName)
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("budgetName = ")
            .append(this.budgetName)
            .append(TAB)
            .append("showRealMonery = ")
            .append(this.showRealMonery)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
