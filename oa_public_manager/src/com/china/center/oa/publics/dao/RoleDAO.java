/**
 * File Name: RoleDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.vo.RoleVO;


/**
 * RoleDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see RoleDAO
 * @since 1.0
 */
public interface RoleDAO extends DAO<RoleBean, RoleVO>
{
    List<RoleBean> queryRoleByLocationId(String locationId);
}
