/**
 * File Name: UserManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;
import java.util.Map;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.UserBean;


/**
 * UserManager
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see UserManager
 * @since 1.0
 */
public interface UserManager
{
    boolean addBean(User user, UserBean bean)
        throws MYException;

    boolean updateBean(User user, UserBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    User findUser(String id);

    boolean containAuth(User user, String... authId);

    boolean containAuth(String id, String authId);

    boolean updatePassword(String id, String password);

    boolean updateStatus(String id, int status);

    boolean updateLocation(String id, String locationId);

    boolean updateFail(String id, int fail);

    boolean updateLogTime(String id, String logTime);

    /**
     * 过滤不正常的职员
     * 
     * @param locationId
     * @return
     */
    Map queryStafferAndRoleByLocationId(String locationId);

    /**
     * 查询用户名下的扩展权限
     * 
     * @param userId
     * @param expandKey
     * @return
     */
    List<AuthBean> queryExpandAuthById(String userId, String expandKey);
}
