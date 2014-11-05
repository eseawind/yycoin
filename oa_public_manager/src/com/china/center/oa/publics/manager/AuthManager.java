/**
 * File Name: AuthManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.listener.AuthListener;


/**
 * AuthManager
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see AuthManager
 * @since 3.0
 */
public interface AuthManager extends ListenerManager<AuthListener>
{
    /**
     * 可配置权限(不包括超级管理员特有的权限)
     * 
     * @return
     */
    List<AuthBean> listAllConfigAuth();

    List<AuthBean> querySubExpandAuth(String expandId);
}
