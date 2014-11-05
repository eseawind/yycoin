/**
 * File Name: PaymentListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PaymentBean;


/**
 * PaymentListener
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see PaymentListener
 * @since 3.0
 */
public interface PaymentListener extends ParentListener
{
    /**
     * 回款导入和增加
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onAddBean(User user, PaymentBean bean)
        throws MYException;

    /**
     * 回款删除
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onDeleteBean(User user, PaymentBean bean)
        throws MYException;

    /**
     * 回款退领
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onDropBean(User user, PaymentBean bean)
        throws MYException;
}
