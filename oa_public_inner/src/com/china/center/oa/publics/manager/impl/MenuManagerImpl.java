/**
 * File Name: MenuManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.listener.MenuListener;
import com.china.center.oa.publics.manager.MenuManager;
import com.china.center.tools.ListTools;


/**
 * MenuManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-8
 * @see MenuManagerImpl
 * @since 3.0
 */
public class MenuManagerImpl extends AbstractListenerManager<MenuListener> implements MenuManager
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.MenuManager#onFindExpandMenu(com.center.china.osgi.publics.User)
     */
    public List<MenuItemBean> onFindExpandMenu(User user, String parentId)
    {
        Collection<MenuListener> listenerMapValues = this.listenerMapValues();

        List<MenuItemBean> result = new ArrayList<MenuItemBean>();

        for (MenuListener menuListener : listenerMapValues)
        {
            List<MenuItemBean> onFindExpandMenu = menuListener.onFindExpandMenu(user, parentId);

            if ( !ListTools.isEmptyOrNull(onFindExpandMenu))
            {
                result.addAll(onFindExpandMenu);
            }
        }

        return result;
    }
}
