/**
 * File Name: RoleHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * RoleHelper
 * 
 * @author ZHUZHU
 * @version 2009-8-23
 * @see RoleHelper
 * @since 1.0
 */
public abstract class RoleHelper
{
    /**
     * isSuperManager
     * 
     * @param user
     * @return
     */
    public static boolean isSuperManager(User user)
    {
        return PublicConstant.SUPER_MANAGER.equals(user.getRoleId());
    }
}
