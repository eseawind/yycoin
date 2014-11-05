/**
 * File Name: SailManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;


/**
 * SailManager
 * 
 * @author ZHUZHU
 * @version 2012-1-2
 * @see SailManager
 * @since 1.0
 */
public interface SailManager
{
    boolean updateInvoiceStatus(User user, String id, double invoiceMoney, int invoiceStatus)
        throws MYException;
}
