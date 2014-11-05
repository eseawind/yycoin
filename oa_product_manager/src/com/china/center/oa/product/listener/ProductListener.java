/**
 * File Name: GroupListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductBean;


/**
 * GroupListener
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see ProductListener
 * @since 1.0
 */
public interface ProductListener extends ParentListener
{
    /**
     * onAddProduct
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onAddProduct(User user, ProductBean bean)
        throws MYException;

    /**
     * onAddProduct
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onUpdateProduct(User user, ProductBean bean)
        throws MYException;

    /**
     * onDeleteGroup
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    void onDeleteProduct(User user, ProductBean bean)
        throws MYException;

    /**
     * onProductStatusChange
     * 
     * @param user
     * @param productId
     * @param oldStatus
     * @param newStatus
     * @throws MYException
     */
    void onProductStatusChange(User user, String productId, int oldStatus, int newStatus)
        throws MYException;
}
