/**
 * File Name: StafferVSCustomerDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;


/**
 * StafferVSCustomerDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see StafferVSCustomerDAO
 * @since 1.0
 */
public interface StafferVSCustomerDAO extends DAO<StafferVSCustomerBean, StafferVSCustomerVO>
{
    boolean updateNewByCityId(String cityId);

    boolean delVSByCityId(String cityId);

    int countByStafferId(String stafferId);

    int countByStafferIdAndCustomerId(String stafferId, String customerId);
}
