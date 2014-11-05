/**
 * File Name: AuthManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.dao.AuthDAO;
import com.china.center.oa.publics.listener.AuthListener;
import com.china.center.oa.publics.manager.AuthManager;


/**
 * AuthManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see AuthManagerImpl
 * @since 3.0
 */
public class AuthManagerImpl extends AbstractListenerManager<AuthListener> implements AuthManager
{
    private AuthDAO authDAO = null;

    /**
     * default constructor
     */
    public AuthManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.AuthManager#listAllConfigAuth()
     */
    public List<AuthBean> listAllConfigAuth()
    {
        List<AuthBean> result = new ArrayList<AuthBean>();

        result.addAll(authDAO.listLocationAuth());

        Collection<AuthListener> listenerMapValues = this.listenerMapValues();

        // 增加扩展权限
        for (AuthListener authListener : listenerMapValues)
        {
            result.addAll(authListener.onFindExpandAuth());
        }

        return result;
    }

    public List<AuthBean> querySubExpandAuth(String expandId)
    {
        List<AuthBean> tempList = listAllConfigAuth();

        List<AuthBean> result = new ArrayList<AuthBean>();

        for (AuthBean authBean : tempList)
        {
            if (authBean.getParentId().equals(expandId))
            {
                result.add(authBean);
            }
        }

        return result;
    }

    /**
     * @return the authDAO
     */
    public AuthDAO getAuthDAO()
    {
        return authDAO;
    }

    /**
     * @param authDAO
     *            the authDAO to set
     */
    public void setAuthDAO(AuthDAO authDAO)
    {
        this.authDAO = authDAO;
    }
}
