/**
 * File Name: GroupManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.listener.GroupListener;


/**
 * GroupManager
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see GroupManager
 * @since 1.0
 */
public interface GroupManager extends ListenerManager<GroupListener>
{
    boolean addBean(User user, GroupBean bean)
        throws MYException;

    boolean updateBean(User user, GroupBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;
}
