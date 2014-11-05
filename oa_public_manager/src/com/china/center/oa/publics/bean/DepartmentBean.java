/**
 * File Name: DepartmentBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;


/**
 * DepartmentBean
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_OADEPARTMENT")
public class DepartmentBean extends BaseBean
{
    /**
     * default constructor
     */
    public DepartmentBean()
    {
    }
}
