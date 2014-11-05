/**
 * File Name: CustomerCheckItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;


/**
 * CustomerCheckItemVO
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCheckItemVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CustomerCheckItemVO extends CustomerCheckItemBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * default constructor
     */
    public CustomerCheckItemVO()
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
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }
}
