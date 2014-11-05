/**
 * File Name: ProductListenerStockImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-4-24<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.listener.ProductListener;
import com.china.center.oa.stock.dao.StockItemDAO;


/**
 * ProductListenerStockImpl
 * 
 * @author ZHUZHU
 * @version 2011-4-24
 * @see ProductListenerStockImpl
 * @since 3.0
 */
public class ProductListenerStockImpl implements ProductListener
{
    private StockItemDAO stockItemDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProductListener#onAddProduct(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public void onAddProduct(User user, ProductBean bean)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProductListener#onDeleteProduct(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public void onDeleteProduct(User user, ProductBean bean)
        throws MYException
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        conditionParse.addCondition("productId", "=", bean.getId());

        int count = stockItemDAO.countByCondition(conditionParse.toString());

        if (count > 0)
        {
            throw new MYException("产品曾经被采购,不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProductListener#onProductStatusChange(com.center.china.osgi.publics.User,
     *      java.lang.String, int, int)
     */
    public void onProductStatusChange(User user, String productId, int oldStatus, int newStatus)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProductListener#onUpdateProduct(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public void onUpdateProduct(User user, ProductBean bean)
        throws MYException
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "ProductListener.StockImpl";
    }

    /**
     * @return the stockItemDAO
     */
    public StockItemDAO getStockItemDAO()
    {
        return stockItemDAO;
    }

    /**
     * @param stockItemDAO
     *            the stockItemDAO to set
     */
    public void setStockItemDAO(StockItemDAO stockItemDAO)
    {
        this.stockItemDAO = stockItemDAO;
    }

}
