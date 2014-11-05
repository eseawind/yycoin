/**
 * File Name: CustomerCreditListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener;


import com.center.china.osgi.publics.ParentListener;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProviderBean;


/**
 * 供应商操作监听
 * 
 * @author ZHUZHU
 * @version 2010-11-20
 * @see ProviderListener
 * @since 3.0
 */
public interface ProviderListener extends ParentListener
{
    /**
     * 删除监听
     * 
     * @param bean
     * @throws MYException
     */
    void onDelete(ProviderBean bean)
        throws MYException;
}
