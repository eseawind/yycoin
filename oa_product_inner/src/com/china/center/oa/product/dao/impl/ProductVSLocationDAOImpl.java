/**
 * File Name: ProductVSLocationDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.product.vo.ProductVSLocationVO;
import com.china.center.oa.product.vs.ProductVSLocationBean;


/**
 * ProductVSLocationDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductVSLocationDAOImpl
 * @since 1.0
 */
public class ProductVSLocationDAOImpl extends BaseDAO<ProductVSLocationBean, ProductVSLocationVO> implements ProductVSLocationDAO
{
    public int countByProductIdAndLocationId(String productId, String locationId)
    {
        return this.countByCondition("where productId = ? and locationId = ?", productId,
            locationId);
    }
}
