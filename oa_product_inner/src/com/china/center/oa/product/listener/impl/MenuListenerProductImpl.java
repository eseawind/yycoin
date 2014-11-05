/**
 * File Name: MenuListenerProductImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.MenuConstant;
import com.china.center.oa.publics.dao.AuthDAO;
import com.china.center.oa.publics.listener.MenuListener;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.ListTools;


/**
 * MenuListenerProductImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-8
 * @see MenuListenerProductImpl
 * @since 3.0
 */
public class MenuListenerProductImpl implements MenuListener
{
    private AuthDAO authDAO = null;

    private UserManager userManager = null;

    private String url = "../depot/queryDepotStorageRelation.jsp?depotId=";

    /**
     * default constructor
     */
    public MenuListenerProductImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.MenuListener#onFindExpandMenu(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    public List<MenuItemBean> onFindExpandMenu(User user, String parentId)
    {
        List<MenuItemBean> result = new ArrayList<MenuItemBean>();

        if ( !MenuConstant.EXPAND_MENU_DEPOT.equals(parentId))
        {
            return result;
        }

        List<AuthBean> depotAuthList = userManager.queryExpandAuthById(user.getId(),
            AuthConstant.EXPAND_AUTH_DEPOT);

        if (ListTools.isEmptyOrNull(depotAuthList))
        {
            return result;
        }

        int indexPos = 80;

        for (AuthBean authBean : depotAuthList)
        {
            MenuItemBean menu = new MenuItemBean();

            menu.setBottomFlag(MenuConstant.BOTTOMFLAG_ITEM);

            menu.setAuth(authBean.getId());

            menu.setParentId(parentId);

            menu.setMenuItemName("仓库-" + authBean.getName());

            menu.setIndexPos(indexPos++ );

            menu.setUrl(this.url + authBean.getId());

            result.add(menu);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "MenuListener.ProductImpl";
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

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

}
