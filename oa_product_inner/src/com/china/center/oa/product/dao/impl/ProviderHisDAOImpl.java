/**
 * File Name: ProviderHisDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.ProviderHisBean;
import com.china.center.oa.product.dao.ProviderHisDAO;


/**
 * ProviderHisDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderHisDAOImpl
 * @since 1.0
 */
public class ProviderHisDAOImpl extends BaseDAO<ProviderHisBean, ProviderHisBean> implements ProviderHisDAO
{

    /**
     * updateCheckStatus
     * 
     * @param id
     * @param checkStatus
     * @return
     */
    public boolean updateCheckStatus(String id, int checkStatus)
    {
        this.jdbcOperation.updateField("checkStatus", checkStatus, id, claz);

        return true;
    }

}
