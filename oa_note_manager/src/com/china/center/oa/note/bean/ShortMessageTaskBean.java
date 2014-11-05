/**
 * File Name: MSTaskBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;


/**
 * MSTaskBean
 * 
 * @author zhuzhu
 * @version 2009-7-28
 * @see ShortMessageTaskBean
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_SMTASK")
public class ShortMessageTaskBean extends AbstractShortMessageTask
{

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("MSTaskBean ( ").append(super.toString()).append(tab)

        .append(" )");

        return retValue.toString();
    }

}
