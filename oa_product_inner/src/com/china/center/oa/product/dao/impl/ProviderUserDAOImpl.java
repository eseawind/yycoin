/**
 * File Name: ProviderUserDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.dao.ProviderUserDAO;


/**
 * ProviderUserDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderUserDAOImpl
 * @since 1.0
 */
public class ProviderUserDAOImpl extends BaseDAO<ProviderUserBean, ProviderUserBean> implements ProviderUserDAO
{
    /**
     * updatePassword
     * 
     * @param id
     * @param newPassword
     * @return
     */
    public boolean updatePassword(String id, String newPassword)
    {
        this.jdbcOperation.updateField("password", newPassword, id, claz);

        return true;
    }

    /**
     * updatePwkey
     * 
     * @param id
     * @param newPwkey
     * @return
     */
    public boolean updatePwkey(String id, String newPwkey)
    {
        this.jdbcOperation.updateField("pwkey", newPwkey, id, claz);

        return true;
    }
}
