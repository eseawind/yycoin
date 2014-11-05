/**
 * File Name: TcpApproveVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.TcpApproveBean;


/**
 * TcpApproveVO
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see TcpApproveVO
 * @since 3.0
 */
@Entity(inherit = true)
public class TcpApproveVO extends TcpApproveBean
{
    @Relationship(relationField = "applyerId")
    private String applyerName = "";

    @Relationship(relationField = "approverId")
    private String approverName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    @Ignore
    private String showTotal = "";

    @Ignore
    private String showCheckTotal = "";

    @Ignore
    private String url = "";

    /**
     * default constructor
     */
    public TcpApproveVO()
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
     * @return the showTotal
     */
    public String getShowTotal()
    {
        return showTotal;
    }

    /**
     * @param showTotal
     *            the showTotal to set
     */
    public void setShowTotal(String showTotal)
    {
        this.showTotal = showTotal;
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
     * @return the showCheckTotal
     */
    public String getShowCheckTotal()
    {
        return showCheckTotal;
    }

    /**
     * @param showCheckTotal
     *            the showCheckTotal to set
     */
    public void setShowCheckTotal(String showCheckTotal)
    {
        this.showCheckTotal = showCheckTotal;
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
            .append("showTotal = ")
            .append(this.showTotal)
            .append(TAB)
            .append("showCheckTotal = ")
            .append(this.showCheckTotal)
            .append(TAB)
            .append("url = ")
            .append(this.url)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
