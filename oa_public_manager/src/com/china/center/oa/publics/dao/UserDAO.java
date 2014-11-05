/**
 * File Name: UserDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.vo.UserVO;


/**
 * UserDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see UserDAO
 * @since 1.0
 */
public interface UserDAO extends DAO<UserBean, UserVO>
{
    UserVO findUserByName(String name);

    UserVO findFirstUserByStafferId(String stafferId);

    boolean updatePassword(String id, String password);

    boolean updateStatus(String id, int status);

    boolean updateStatusByStaffer(String staffer, int status);

    boolean updateLocation(String id, String locationId);

    boolean updateFail(String id, int fail);

    boolean updateLogTime(String id, String logTime);

    int countByLocationId(String locationId);

    int countByRoleId(String roleId);

    int countByStafferId(String stafferId);
}
