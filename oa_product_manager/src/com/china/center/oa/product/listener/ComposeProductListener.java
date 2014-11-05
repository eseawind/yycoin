/**
 * File Name: ComposeProductListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DecomposeProductBean;


/**
 * ComposeProductListener
 * 
 * @author ZHUZHU
 * @version 2011-5-8
 * @see ComposeProductListener
 * @since 3.0
 */
public interface ComposeProductListener extends ParentListener
{

    /**
     * 合成/分解最终确认通知
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onConfirmCompose(User user, ComposeProductBean bean)
        throws MYException;
    
    void onConfirmDecompose(User user, DecomposeProductBean bean)
    throws MYException;
}
