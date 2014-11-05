/**
 * File Name: PaymentListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.OutBillBean;


/**
 * PaymentListener
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see BackPayApplyListener
 * @since 3.0
 */
public interface BackPayApplyListener extends ParentListener
{
    /**
     * 销售退款/预收退款 通过
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onEndBackPayApplyBean(User user, BackPayApplyBean bean, List<OutBillBean> outBillList)
        throws MYException;
}
