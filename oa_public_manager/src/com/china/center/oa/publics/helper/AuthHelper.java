/**
 * File Name: AuthHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.vs.RoleAuthBean;


/**
 * AuthHelper
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see AuthHelper
 * @since 1.0
 */
public abstract class AuthHelper
{
    public static boolean containAuth(User user, String authId)
    {
        if (authId.equals(AuthConstant.PUNLIC_AUTH))
        {
            return true;
        }

        List<RoleAuthBean> authList = user.getAuth();

        for (RoleAuthBean roleAuthBean : authList)
        {
            if (roleAuthBean.getAuthId().equals(authId))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean containAuth(User user, String... authId)
    {
        for (String auth : authId)
        {
            if (containAuth(user, auth))
            {
                return true;
            }
        }

        return false;
    }
}
