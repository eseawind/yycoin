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
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.sail.bean.OutBean;


/**
 * PaymentListener
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see BillListener
 * @since 3.0
 */
public interface BillListener extends ParentListener
{
    /**
     * 销售单驳回/删除后,应收转预收
     * 
     * @param user
     * @param bean
     * @param list
     * @throws MYException
     */
    void onFeeInReceiveToPre(User user, OutBean bean, List<InBillBean> list)
        throws MYException;

    /**
     * 预收转移的监听
     * 
     * @param user
     * @param inBill
     * @param target
     * @throws MYException
     */
    void onChageBillToStaffer(User user, List<InBillBean> inBillList, StafferBean target)
        throws MYException;
    
    /**
     * 当创建付款单时针对手续费/转账自动生成凭证
     * @param user
     * @param bean
     */
    void onCreateOutBill(User user, OutBillBean bean)
        throws MYException;
    
    /**
     * 收款单创建时增加财务标记
     * @param user
     * @param inBillBean
     * @throws MYException
     */
    void onAddInBill(User user, InBillBean inBillBean)
    	throws MYException;
}
