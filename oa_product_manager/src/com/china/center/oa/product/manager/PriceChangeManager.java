/**
 * File Name: PriceChangeManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.listener.PriceChangeListener;
import com.china.center.oa.product.vo.PriceChangeVO;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * PriceChangeManager
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeManager
 * @since 1.0
 */
public interface PriceChangeManager extends ListenerManager<PriceChangeListener>
{
    /**
     * 产品调价(CORE)(这个事务比较大)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addPriceChange(User user, PriceChangeBean bean)
        throws MYException;

    /**
     * rollbackPriceChange(回滚调价)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rollbackPriceChange(User user, String id)
        throws MYException;

    /**
     * findById
     * 
     * @param id
     * @return
     */
    PriceChangeVO findById(String id);

    /**
     * onPriceChange
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    boolean onPriceChange(User user, ProductBean bean);

    int onPriceChange2(User user, StorageRelationBean bean);
}
