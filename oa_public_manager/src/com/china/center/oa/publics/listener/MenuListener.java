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
import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.MenuItemBean;


/**
 * MenuListener
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see MenuListener
 * @since 3.0
 */
public interface MenuListener extends ParentListener
{
    /**
     * onFindExpandMenu(查询动态菜单80-98)
     * 
     * @return
     */
    List<MenuItemBean> onFindExpandMenu(User user, String parentId);
}
