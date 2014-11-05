/**
 * File Name: AuthListenerProductImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.listener.AuthListener;


/**
 * AuthListenerProductImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see AuthListenerProductImpl
 * @since 3.0
 */
public class AuthListenerProductImpl implements AuthListener
{
    private DepotDAO depotDAO = null;

    /**
     * default constructor
     */
    public AuthListenerProductImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.AuthListener#onFindExpandAuth()
     */
    public List<AuthBean> onFindExpandAuth()
    {
        List<AuthBean> result = new ArrayList<AuthBean>();

        List<DepotBean> listEntityBeans = depotDAO.queryCommonDepotBean();

        AuthBean root = new AuthBean();

        root.setId(AuthConstant.EXPAND_AUTH_DEPOT);

        root.setLevel(PublicConstant.ROLE_LEVEL_ROOT);

        root.setBottomFlag(PublicConstant.BOTTOMFLAG_NO);

        root.setName("仓库权限(扩展)");

        root.setParentId("0");

        root.setType(PublicConstant.AUTH_TYPE_LOCATION);

        result.add(root);

        for (DepotBean depotBean : listEntityBeans)
        {
            AuthBean auth = new AuthBean();

            auth.setId(depotBean.getId());

            auth.setLevel(1);

            auth.setBottomFlag(PublicConstant.BOTTOMFLAG_YES);

            auth.setName(depotBean.getName());

            auth.setParentId(AuthConstant.EXPAND_AUTH_DEPOT);

            auth.setType(PublicConstant.AUTH_TYPE_LOCATION);

            result.add(auth);
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
        return "AuthListener.ProductImpl";
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }

}
