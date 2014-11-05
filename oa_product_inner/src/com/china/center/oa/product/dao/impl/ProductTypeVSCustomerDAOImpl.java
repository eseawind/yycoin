/**
 * File Name: ProductTypeVSCustomerDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.dao.ProductTypeVSCustomerDAO;
import com.china.center.oa.product.vo.ProductTypeVSCustomerVO;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;


/**
 * ProductTypeVSCustomerDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProductTypeVSCustomerDAOImpl
 * @since 1.0
 */
public class ProductTypeVSCustomerDAOImpl extends BaseDAO<ProductTypeVSCustomer, ProductTypeVSCustomerVO> implements ProductTypeVSCustomerDAO
{
    public boolean delVSByCustomerId(String customerId)
    {
        this.jdbcOperation.delete(customerId, "customerId", this.claz);

        return true;
    }

    public List<ProductTypeVSCustomer> queryVSByCustomerId(String customerId)
    {
        return this.jdbcOperation.queryForListByField("customerId", this.claz, customerId);
    }
}
