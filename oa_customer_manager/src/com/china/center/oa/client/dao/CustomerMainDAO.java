/**
 * File Name: CustomerApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.vo.CustomerVO;
import com.china.center.oa.client.wrap.CustomerAssignWrap;


/**
 * CustomerDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see #com.china.center.oa.client.dao.CustomerMainDAO#
 * @since 1.0
 */
public interface CustomerMainDAO extends DAO<CustomerBean, CustomerVO>
{
    int countByLocationId(String locationId);

    int countByCreditLevelId(String creditLevelId);

    List<CustomerBean> querySelfCusByStafferId(String stafid);
    
    int countByCode(String code);

    CustomerBean findCustomerByCode(String code);

    boolean updateCustomerLocation(String srcLocationId, String dirLocationId);

    boolean updateCustomerstatus(String id, int status);

    boolean updateCustomerLever(String id, int lever);

    boolean updateCustomerCreditUpdateTime(String id, int creditUpdateTime);

    List<String> listNotPayCustomerIds();

    boolean updateCustomerCredit(String id, String creditLevelId, int creditVal);

    boolean updateCustomerNewTypeToOld(String id);

    boolean updateCustomerHasNewToOld(String id);

    boolean updateCustomerLocationByCity(String cityId, String dirLocationId);

    boolean initCustomerLocation();

    int countCustomerAssignByConstion(ConditionParse condition);

    List<CustomerAssignWrap> queryCustomerAssignByConstion(ConditionParse condition,
                                                           PageSeparate page);

    int countSelfCustomerByConstion(String stafferId, ConditionParse condition);

    List<CustomerBean> querySelfCustomerByConstion(String stafferId, ConditionParse condition,
                                                   PageSeparate page);

    int autoUpdateCustomerStatus();

    boolean updateCityCustomerToInit(String cityId);

    boolean updateCustomerByStafferId(String stafferId, int status, int flag);

    boolean delCustomerByStafferId(String stafferId, int flag);

    boolean updateApplyCityCustomerToInit(String cityId);

    int synCustomerNewTypeYear(String begin, String end);

    int updayeCustomerNewTypeInTer();
    
    List<CustomerAssignWrap> queryCustomerBySubStaffer(ConditionParse condition, PageSeparate page);
    
    int countCustomerBySubStaffer(ConditionParse condition);
    
    // 查询职员下的客户
    int countCustomerByStaff(ConditionParse condition);
    
    List<CustomerVO> queryCustomerByStaff(ConditionParse con, PageSeparate page);
    
    int countCustomerLocationByCondition(ConditionParse condition);
    
    List<CustomerVO> queryCustomerLocationByCondition(ConditionParse condition,
            PageSeparate page);
}
