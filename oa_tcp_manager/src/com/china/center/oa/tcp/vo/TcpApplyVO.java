/**
 * File Name: TcpApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.TcpApplyBean;


/**
 * TcpApplyVO
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TcpApplyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class TcpApplyVO extends TcpApplyBean
{
    @Relationship(relationField = "applyerId")
    private String applyerName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    /**
     * default constructor
     */
    public TcpApplyVO()
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

        StringBuffer retValue = new StringBuffer();

        retValue.append("TcpApplyVO ( ").append(super.toString()).append(TAB).append("applyerName = ").append(
            this.applyerName).append(TAB).append("departmentName = ").append(this.departmentName).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
