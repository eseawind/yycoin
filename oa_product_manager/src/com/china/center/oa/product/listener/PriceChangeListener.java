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
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * PriceChangeListener
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see PriceChangeListener
 * @since 1.0
 */
public interface PriceChangeListener extends ParentListener
{
    /**
     * 产品调价的时候检查产品是否可以调价
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onPriceChange(User user, ProductBean bean)
        throws MYException;

    /**
     * onPriceChange(预占的库存,单据在审批中,但是没有发货呢)
     * 
     * @param user
     * @param relation
     * @throws MYException
     */
    int onPriceChange2(User user, StorageRelationBean relation);

    /**
     * 在途的产品(库存已经减去)
     * 
     * @param user
     * @param relation
     * @throws MYException
     */
    int onPriceChange3(User user, StorageRelationBean relation);

    /**
     * 调价确认(凭证)
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onConfirmPriceChange(User user, PriceChangeBean bean)
        throws MYException;

    /**
     * 调价回滚(凭证)
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onRollbackPriceChange(User user, PriceChangeBean bean)
        throws MYException;
}
