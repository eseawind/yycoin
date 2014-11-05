/**
 * File Name: AuthListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.china.center.oa.publics.bean.AuthBean;


/**
 * AuthListener
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see AuthListener
 * @since 3.0
 */
public interface AuthListener extends ParentListener
{
    /**
     * onFindExpandAuth(查询动态权限80-98)
     * 
     * @return
     */
    List<AuthBean> onFindExpandAuth();
}
