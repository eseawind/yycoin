/**
 * File Name: ProductTypeVSCustomerDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.vo.ProductTypeVSCustomerVO;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;


/**
 * ProductTypeVSCustomerDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProductTypeVSCustomerDAO
 * @since 1.0
 */
public interface ProductTypeVSCustomerDAO extends DAO<ProductTypeVSCustomer, ProductTypeVSCustomerVO>
{
    boolean delVSByCustomerId(String customerId);

    List<ProductTypeVSCustomer> queryVSByCustomerId(String customerId);
}
