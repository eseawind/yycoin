/**
 * File Name: GroupDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.group.bean.GroupBean;


/**
 * GroupDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see GroupDAO
 * @since 1.0
 */
public interface GroupDAO extends DAO<GroupBean, GroupBean>
{
    int countByNameAndStafferId(String name, String stafferId);

    List<GroupBean> listPublicGroup();
}
