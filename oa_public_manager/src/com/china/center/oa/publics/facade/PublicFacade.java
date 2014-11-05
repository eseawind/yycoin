/**
 * File Name: PublicFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.facade;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * PublicFacade
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicFacade
 * @since 1.0
 */
public interface PublicFacade
{
    boolean addLocationBean(User user, LocationBean bean)
        throws MYException;

    boolean delLocationBean(User user, String locationId)
        throws MYException;

    boolean addStafferBean(User user, StafferBean bean)
        throws MYException;

    boolean updateStafferBean(User user, StafferBean bean)
        throws MYException;

    boolean updateStafferPwkey(User user, StafferBean bean)
        throws MYException;

    boolean updateStafferLever(User user, StafferBean bean)
        throws MYException;

    boolean delStafferBean(User user, String id)
        throws MYException;

    boolean dropStafferBean(User user, String id)
        throws MYException;

    boolean addRoleBean(String userId, RoleBean bean)
        throws MYException;

    boolean updateRoleBean(String userId, RoleBean bean)
        throws MYException;

    boolean delRoleBean(String userId, String id)
        throws MYException;

    boolean addUserBean(String userId, UserBean bean)
        throws MYException;

    boolean updateUserBean(String userId, UserBean bean)
        throws MYException;

    boolean delUserBean(String userId, String id)
        throws MYException;

    boolean updateUserPassword(String userId, String id, String password)
        throws MYException;

    boolean updateUserStatus(String userId, String id, int status)
        throws MYException;

    boolean updateUserLocation(String userId, String id, String locationId)
        throws MYException;

    boolean addOrgBean(String userId, PrincipalshipBean bean)
        throws MYException;

    boolean updateOrgBean(String userId, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException;

    boolean delOrgBean(String userId, String id)
        throws MYException;

    boolean addDepartmentBean(String userId, DepartmentBean bean)
        throws MYException;

    boolean updateDepartmentBean(String userId, DepartmentBean bean)
        throws MYException;

    boolean delDepartmentBean(String userId, String id)
        throws MYException;

    boolean addPostBean(String userId, PostBean bean)
        throws MYException;

    boolean updatePostBean(String userId, PostBean bean)
        throws MYException;

    boolean delPostBean(String userId, String id)
        throws MYException;

    boolean addLocationVSCity(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException;

    boolean addDutyBean(User user, DutyBean bean)
        throws MYException;

    boolean updateDutyBean(User user, DutyBean bean)
        throws MYException;

    boolean deleteDutyBean(User user, String id)
        throws MYException;

    boolean addEnumBean(String userId, EnumBean bean)
        throws MYException;

    boolean updateEnumBean(String userId, EnumBean bean)
        throws MYException;

    boolean deleteEnumBean(String userId, String id)
        throws MYException;

    boolean updateInvoiceBean(String userId, InvoiceBean bean)
        throws MYException;

    boolean addShowBean(String userId, ShowBean bean)
        throws MYException;

    boolean updateShowBean(String userId, ShowBean bean)
        throws MYException;

    boolean deleteShowBean(String userId, String id)
        throws MYException;

    boolean updateCredit(String userId, String id, double credit)
        throws MYException;

}
