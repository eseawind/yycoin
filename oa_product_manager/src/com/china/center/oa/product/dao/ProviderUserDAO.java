/**
 * File Name: ProviderUserDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.ProviderUserBean;


/**
 * ProviderUserDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderUserDAO
 * @since 1.0
 */
public interface ProviderUserDAO extends DAO<ProviderUserBean, ProviderUserBean>
{
    boolean updatePassword(String id, String newPassword);

    boolean updatePwkey(String id, String newPwkey);
}
