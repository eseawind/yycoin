/**
 * File Name: ProductManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.CiticVSOAProductBean;
import com.china.center.oa.product.bean.GoldSilverPriceBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.listener.ProductListener;
import com.china.center.oa.product.vs.ProductVSLocationBean;


/**
 * ProductManager
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductManager
 * @since 1.0
 */
public interface ProductManager extends ListenerManager<ProductListener>
{
    boolean addProductBean(User user, ProductBean bean)
        throws MYException;

    boolean updateProductBean(User user, ProductBean bean)
        throws MYException;

    boolean deleteProductBean(User user, String id)
        throws MYException;

    boolean configProductVSLocation(User user, String productId, List<ProductVSLocationBean> vsList)
        throws MYException;

    boolean changeProductStatus(User user, String productId, int oldStatus, int newStatus)
        throws MYException;
    
    boolean configGoldSilverPrice(User user, GoldSilverPriceBean bean)
    	throws MYException;
    
    boolean importCiticProduct(User user, List<CiticVSOAProductBean> list)
    	throws MYException;
    
    boolean deleteCiticProductBean(User user, String id)
    throws MYException;
}
