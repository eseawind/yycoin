/**
 * File Name: InvoiceManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.InvoiceBean;


/**
 * InvoiceManager
 * 
 * @author ZHUZHU
 * @version 2010-9-19
 * @see InvoiceManager
 * @since 1.0
 */
public interface InvoiceManager
{
    boolean updateBean(User user, InvoiceBean bean)
        throws MYException;
}
