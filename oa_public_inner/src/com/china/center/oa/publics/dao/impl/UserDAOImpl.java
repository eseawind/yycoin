/**
 * File Name: UserDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.tools.ListTools;


/**
 * UserDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see UserDAOImpl
 * @since 1.0
 */
public class UserDAOImpl extends BaseDAO<UserBean, UserVO> implements UserDAO
{
    public UserVO findUserByName(String name)
    {
        return this.jdbcOperation.find(name, "name", this.clazVO);
    }

    public UserVO findFirstUserByStafferId(String stafferId)
    {
        List<UserVO> list = this.queryEntityVOsByFK(stafferId);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        // 获取为0的人
        Collections.sort(list, new Comparator<UserVO>()
        {
            public int compare(UserVO o1, UserVO o2)
            {
                return o1.getStatus() - o2.getStatus();
            }
        });

        return list.get(0);
    }

    public boolean updatePassword(String id, String password)
    {
        int i = jdbcOperation.updateField("password", password, id, this.claz);

        return i != 0;
    }

    public boolean updateStatus(String id, int status)
    {
        int i = jdbcOperation.updateField("status", status, id, this.claz);

        return i != 0;
    }

    public boolean updateStatusByStaffer(String staffer, int status)
    {
        int i = jdbcOperation.update(BeanTools.getUpdateHead(claz)
                                     + "set status = ? where stafferId = ?", status, staffer);

        return i != 0;
    }

    /**
     * updateLocation
     * 
     * @param id
     * @param locationId
     * @return
     */
    public boolean updateLocation(String id, String locationId)
    {
        int i = jdbcOperation.updateField("locationId", locationId, id, this.claz);

        return i != 0;
    }

    public boolean updateFail(String id, int fail)
    {
        int i = jdbcOperation.updateField("fail", fail, id, this.claz);

        return i != 0;
    }

    public boolean updateLogTime(String id, String logTime)
    {
        int i = jdbcOperation.updateField("loginTime", logTime, id, this.claz);

        return i != 0;
    }

    public int countByLocationId(String locationId)
    {
        return jdbcOperation.queryForInt("where locationId = ? ", this.claz, locationId);
    }

    public int countByRoleId(String roleId)
    {
        return jdbcOperation.queryForInt("where roleId = ? ", this.claz, roleId);
    }

    public int countByStafferId(String stafferId)
    {
        return jdbcOperation.queryForInt("where stafferId = ? ", this.claz, stafferId);
    }

}
