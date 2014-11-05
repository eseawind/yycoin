/**
 * File Name: CreditItemManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.manager;


import java.io.Serializable;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CreditLevelBean;


/**
 * CreditItemManager
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditItemManager
 * @since 1.0
 */
public interface CreditItemManager
{
    boolean updateCreditItemSec(User user, CreditItemSecBean bean)
        throws MYException;

    boolean updateCreditLevel(User user, CreditLevelBean bean)
        throws MYException;

    boolean updateCreditItem(User user, CreditItemBean bean)
        throws MYException;

    boolean addCreditItemThr(User user, CreditItemThrBean bean)
        throws MYException;

    boolean updateCreditItemThr(User user, CreditItemThrBean bean)
        throws MYException;

    boolean deleteCreditItemThr(User user, Serializable id)
        throws MYException;
}
