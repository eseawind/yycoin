/**
 * File Name: ProductVSLocationDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.vo.ProductVSLocationVO;
import com.china.center.oa.product.vs.ProductVSLocationBean;


/**
 * ProductVSLocationDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductVSLocationDAO
 * @since 1.0
 */
public interface ProductVSLocationDAO extends DAO<ProductVSLocationBean, ProductVSLocationVO>
{
    int countByProductIdAndLocationId(String productId, String locationId);
}
