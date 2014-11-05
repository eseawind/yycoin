/**
 * File Name: PaymentManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.listener.PaymentListener;


/**
 * PaymentManager
 * 
 * @author ZHUZHU
 * @version 2010-12-22
 * @see PaymentManager
 * @since 3.0
 */
public interface PaymentManager extends ListenerManager<PaymentListener>
{
    boolean addBean(User user, PaymentBean bean)
        throws MYException;

    boolean addBeanList(User user, List<PaymentBean> beanList)
        throws MYException;

    boolean updateBean(User user, PaymentBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;

    /**
     * 回款核对
     * 
     * @param user
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    boolean checkBean1(User user, String id, String reason)
        throws MYException;

    /**
     * 认领核对
     * 
     * @param user
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    boolean checkBean2(User user, String id, String reason)
        throws MYException;

    /**
     * 删除核对
     * 
     * @param user
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    boolean checkBean3(User user, String id, String reason)
        throws MYException;

    /**
     * batchDeleteBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean batchDeleteBean(User user, String id)
        throws MYException;

    /**
     * 领取回款(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean drawBean(User user, String id, String customerId,String description)
        throws MYException;

    /**
     * 退领(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean dropBean(User user, String id)
        throws MYException;
}
