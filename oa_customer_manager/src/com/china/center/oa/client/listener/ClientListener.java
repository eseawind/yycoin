/**
 * File Name: CustomerCreditListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;


/**
 * 客户信用监听,主要是实现信用的使用
 * 
 * @author ZHUZHU
 * @version 2010-11-20
 * @see ClientListener
 * @since 3.0
 */
public interface ClientListener extends ParentListener
{
    /**
     * 客户没有支付的金额(每个模块独立核算自己的，多个模块最终合计)
     * 
     * @param bean
     * @return
     */
    double onNoPayBusiness(CustomerBean bean);

    /**
     * 删除监听
     * 
     * @param bean
     * @throws MYException
     */
    void onDelete(CustomerBean bean)
        throws MYException;

    /**
     * 变更客户关系
     * 
     * @param user
     * @param apply
     * @param cus
     * @throws MYException
     */
    void onChangeCustomerRelation(User user, AssignApplyBean apply, CustomerBean cus)
        throws MYException;
}
