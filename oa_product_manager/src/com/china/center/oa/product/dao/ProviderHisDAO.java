/**
 * File Name: ProviderHisDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.ProviderHisBean;


/**
 * ProviderHisDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderHisDAO
 * @since 1.0
 */
public interface ProviderHisDAO extends DAO<ProviderHisBean, ProviderHisBean>
{
    boolean updateCheckStatus(String id, int checkStatus);
}
