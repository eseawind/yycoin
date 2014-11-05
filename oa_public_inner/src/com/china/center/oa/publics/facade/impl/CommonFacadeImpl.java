/**
 * File Name: CommonFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.facade.impl;


import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.facade.CommonFacade;
import com.china.center.oa.publics.helper.LoginHelper;
import com.china.center.tools.Security;
import com.china.center.webportal.listener.MySessionListener;


/**
 * CommonFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-18
 * @see CommonFacadeImpl
 * @since 1.0
 */
public class CommonFacadeImpl implements CommonFacade
{
    private UserDAO userDAO = null;

    private StafferDAO stafferDAO = null;

    /**
     * auth
     */
    public boolean auth(String key, String value)
    {
        // /session/template
        boolean auth = MySessionListener.getSessionSets().contains(getSession(value));

        return auth;
    }

    private String getSession(String url)
    {
        String head = url.substring(0, url.indexOf("/", 1) + 1);

        // sessionId/template/TemplateFile.jsp
        String url2 = url.substring(head.length());

        String end = url2.substring(0, url2.indexOf("/"));

        return end;
    }

    /**
     * 实现
     */
    public User login(String name, String password)
    {
        User user = userDAO.findUserByName(name);

        if (user == null)
        {
            return null;
        }

        // 锁定处理
        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK)
        {
            return null;
        }

        // 验证密码
        if (user.getPassword().equals(Security.getSecurity(password)))
        {
            return user;
        }
        else
        {
            if ( (user.getFail() + 1) >= PublicConstant.LOGIN_FAIL_MAX)
            {
                userDAO.updateStatus(user.getName(), PublicConstant.LOGIN_STATUS_LOCK);

                userDAO.updateFail(user.getName(), 0);
            }
            else
            {
                userDAO.updateFail(user.getName(), user.getFail() + 1);
            }

            return null;
        }
    }

    public User login2(String name, String password, String rand, String key, String randKey)
    {
        User user = userDAO.findUserByName(name);

        String randVal = rand.toUpperCase();

        if (user == null)
        {
            return null;
        }

        // 锁定处理
        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK)
        {
            return null;
        }

        StafferBean stafferBean = stafferDAO.find(user.getStafferId());

        if (stafferBean == null)
        {
            return null;
        }

        // 验证密码
        if (user.getPassword().equals(Security.getSecurity(password))
            && handleEncLock(key, randKey, randVal, true, stafferBean.getPwkey()))
        {
            return user;
        }
        else
        {
            if ( (user.getFail() + 1) >= PublicConstant.LOGIN_FAIL_MAX)
            {
                userDAO.updateStatus(user.getName(), PublicConstant.LOGIN_STATUS_LOCK);

                userDAO.updateFail(user.getName(), 0);
            }
            else
            {
                userDAO.updateFail(user.getName(), user.getFail() + 1);
            }

            return null;
        }
    }

    private boolean handleEncLock(String key, String randKey, String randVal, boolean hasEncLock,
                                  String pwkey)
    {
        return !hasEncLock || LoginHelper.encRadomStr(pwkey, key, randVal).equals(randKey);
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }
}
