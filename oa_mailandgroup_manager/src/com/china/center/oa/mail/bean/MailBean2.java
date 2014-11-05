/**
 * File Name: MailBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * MailBean
 * 
 * @author ZHUZHU
 * @version 2009-4-12
 * @see MailBean2
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_MAIL2")
public class MailBean2 extends AbstractMail
{
    @Id
    private String id = "";

    /**
     * default constructor
     */
    public MailBean2()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
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
            .append("MailBean2 ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
