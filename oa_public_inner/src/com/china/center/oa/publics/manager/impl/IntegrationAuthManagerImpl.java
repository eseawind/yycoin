/**
 * File Name: IntegrationAuthManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAuthManager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.StringTools;


/**
 * IntegrationAuthManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see IntegrationAuthManagerImpl
 * @since 3.0
 */
public class IntegrationAuthManagerImpl implements IntegrationAuthManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private UserManager userManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.china.center.spring.iaop.annotation.IntegrationAuthManager#auth(java.lang.String[], java.lang.Object[])
     */
    public void auth(Method paramMethod, String[] authIds, Object[] parameter)
        throws Exception
    {
        if (parameter == null || parameter.length == 0)
        {
            throw new MYException("没有操作权限");
        }

        Object par0 = parameter[0];

        if ( ! (par0 instanceof User))
        {
            _logger.error(paramMethod.getDeclaringClass() + "." + paramMethod.getName()
                          + ":不符合权限校验规则(User)");

            throw new MYException("不符合权限校验规则");
        }

        User real = (User)par0;

        User user = userManager.findUser(real.getId());

        checkUser(user);

        boolean containAuth = false;

        if (authIds.length == 1 && authIds[0].indexOf("&") != -1)
        {
            String[] split = authIds[0].split("&");

            containAuth = containAllAuth(user, split);
        }
        else
        {
            containAuth = containAuth(user, authIds);
        }

        if ( !containAuth)
        {
            throw noAuth();
        }
    }

    private MYException noAuth()
    {
        return new MYException("没有权限");
    }

    private void checkUser(User user)
        throws MYException
    {
        if (user == null)
        {
            throw new MYException("用户不存在");
        }

        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK)
        {
            throw new MYException("用户被锁定,没有任何操作权限");
        }
    }

    protected boolean containAllAuth(User user, String... authId)
    {
        for (String auth : authId)
        {
            if ( !containAuth(user, auth))
            {
                return false;
            }
        }

        return true;
    }

    private boolean containAuth(User user, String... authId)
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

    protected boolean containAuth(User user, String authId)
    {
        if (StringTools.isNullOrNone(authId))
        {
            return true;
        }

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

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }
}
