/**
 * File Name: RoleDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.dao.RoleDAO;
import com.china.center.oa.publics.vo.RoleVO;


/**
 * RoleDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see RoleDAOImpl
 * @since 1.0
 */
public class RoleDAOImpl extends BaseDAO<RoleBean, RoleVO> implements RoleDAO
{
    /**
     * 查询区域下的角色
     * 
     * @param locationId
     * @return
     */
    public List<RoleBean> queryRoleByLocationId(String locationId)
    {
        return this.jdbcOperation.queryForList("where locationId = ?", claz, locationId);
    }
}
