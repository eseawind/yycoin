/**
 * File Name: FechProduct.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stockvssail.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemBean;


/**
 * FechProduct
 * 
 * @author ZHUZHU
 * @version 2011-6-12
 * @see FechProductListener
 * @since 3.0
 */
public interface FechProductListener extends ParentListener
{
    /**
     * 采购拿货的时候生成凭证
     * 
     * @param user
     * @param bean
     * @param each
     * @param out
     * @throws MYException
     */
    void onFechProduct(final User user, final StockBean bean, final StockItemBean each,
                       final OutBean out)
        throws MYException;
}
