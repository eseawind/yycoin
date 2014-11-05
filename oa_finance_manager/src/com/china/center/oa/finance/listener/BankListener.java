/**
 * File Name: BankListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BankBean;


/**
 * BankListener
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see BankListener
 * @since 1.0
 */
public interface BankListener extends ParentListener
{
    void onAddBank(User user, BankBean bank)
        throws MYException;

    void onDeleteBank(User user, BankBean bank)
        throws MYException;
}
