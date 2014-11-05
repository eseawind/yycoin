/**
 * File Name: CustomerHisDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customer.bean.CustomerHisBean;
import com.china.center.oa.customer.dao.CustomerHisDAO;
import com.china.center.oa.customer.vo.CustomerHisVO;


/**
 * CustomerHisDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerHisDAOImpl
 * @since 1.0
 */
public class CustomerHisDAOImpl extends BaseDAO<CustomerHisBean, CustomerHisVO> implements CustomerHisDAO
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
