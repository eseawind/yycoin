/**
 * File Name: CustomerHisDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customer.bean.CustomerHisBean;
import com.china.center.oa.customer.vo.CustomerHisVO;


/**
 * CustomerHisDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerHisDAO
 * @since 1.0
 */
public interface CustomerHisDAO extends DAO<CustomerHisBean, CustomerHisVO>
{
    boolean updateCheckStatus(String id, int checkStatus);
}
