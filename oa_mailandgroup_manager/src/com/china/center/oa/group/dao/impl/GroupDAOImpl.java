/**
 * File Name: GroupDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.dao.GroupDAO;


/**
 * GroupDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see GroupDAOImpl
 * @since 1.0
 */
public class GroupDAOImpl extends BaseDAO<GroupBean, GroupBean> implements GroupDAO
{
    public int countByNameAndStafferId(String name, String stafferId)
    {
        return super.countByCondition("where name = ? and stafferId = ?", name, stafferId);
    }

    /**
     * listPublicGroup
     * 
     * @return
     */
    public List<GroupBean> listPublicGroup()
    {
        return this.jdbcOperation.queryForList("where type = ?", claz, GroupConstant.GROUP_TYPE_PUBLIC);
    }
}
