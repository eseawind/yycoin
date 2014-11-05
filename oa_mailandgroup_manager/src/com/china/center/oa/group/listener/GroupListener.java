/**
 * File Name: GroupListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;


/**
 * GroupListener
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see GroupListener
 * @since 1.0
 */
public interface GroupListener extends ParentListener
{
    /**
     * onDeleteGroup
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    void onDeleteGroup(User user, String id)
        throws MYException;
}
