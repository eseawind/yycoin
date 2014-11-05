/**
 * File Name: MenuManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.listener.MenuListener;


/**
 * MenuManager
 * 
 * @author ZHUZHU
 * @version 2010-12-8
 * @see MenuManager
 * @since 3.0
 */
public interface MenuManager extends ListenerManager<MenuListener>
{
    /**
     * onFindExpandMenu(查询动态菜单80-98)
     * 
     * @return
     */
    List<MenuItemBean> onFindExpandMenu(User user, String parentId);
}
