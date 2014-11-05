/**
 * File Name: CustomerCreditBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vs;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;


/**
 * CustomerCreditBean
 * 
 * @author ZHUZHU
 * @version 2009-11-8
 * @see CustomerCreditApplyBean
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_VS_CURCRE_APPLY")
public class CustomerCreditApplyBean extends AbstractCustomerCredit
{
}
