/**
 * File Name: BankManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.io.IOException;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.listener.BankListener;


/**
 * BankManager
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see BankManager
 * @since 3.0
 */
public interface BankManager extends ListenerManager<BankListener>
{
    boolean addBean(User user, BankBean bean)
        throws MYException;

    boolean updateBean(User user, BankBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;

    void wirteBankStat(WriteFile write, BankBean bank)
        throws IOException;

    void exportAllCurrentBankStat();
}
