/**
 * File Name: RoleManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.dao.RoleDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.vo.RoleVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;


/**
 * RoleManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see RoleManagerImpl
 * @since 1.0
 */
@Exceptional
public class RoleManagerImpl implements RoleManager
{
    private RoleDAO roleDAO = null;

    private CommonDAO commonDAO = null;

    private UserDAO userDAO = null;

    private RoleAuthDAO roleAuthDAO = null;

    /**
     * default constructor
     */
    public RoleManagerImpl()
    {}

    /**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO.getSquenceString());

        roleDAO.saveEntityBean(bean);

        List<RoleAuthBean> auth = bean.getAuth();

        if ( !ListTools.isEmptyOrNull(auth))
        {
            // 增加角色的权限
            for (RoleAuthBean roleAuthBean : auth)
            {
                roleAuthBean.setRoleId(bean.getId());

                roleAuthDAO.saveEntityBean(roleAuthBean);
            }
        }

        return true;
    }

    /**
     * updateBean(只更新权限)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        RoleBean oldBean = roleDAO.find(bean.getId());

        if (oldBean == null)
        {
            throw new MYException("人员不存在");
        }

        List<RoleAuthBean> auth = bean.getAuth();

        roleAuthDAO.deleteEntityBeansByFK(bean.getId());

        if ( !ListTools.isEmptyOrNull(auth))
        {
            // 增加角色的权限
            for (RoleAuthBean roleAuthBean : auth)
            {
                roleAuthBean.setRoleId(bean.getId());

                roleAuthDAO.saveEntityBean(roleAuthBean);
            }
        }

        return true;
    }

    /**
     * delBean
     * 
     * @param user
     * @param stafferId
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        RoleBean bean = roleDAO.find(id);

        if (bean == null)
        {
            throw new MYException("人员不存在");
        }

        checkDelBean(id);

        roleDAO.deleteEntityBean(id);

        roleAuthDAO.deleteEntityBeansByFK(id);

        return true;
    }

    public RoleVO findVO(String id)
        throws MYException
    {
        RoleVO vo = roleDAO.findVO(id);

        if (vo == null)
        {
            throw new MYException("角色不存在");
        }

        vo.setAuth(roleAuthDAO.queryEntityBeansByFK(id));

        return vo;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(RoleBean bean)
        throws MYException
    {
        if (roleDAO.countByUnique(bean.getName(), bean.getLocationId()) > 0)
        {
            throw new MYException("分公司下角色名称[%s]已经存在", bean.getName());
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkDelBean(String id)
        throws MYException
    {
        // 存在人员或者user
        if (userDAO.countByRoleId(id) > 0)
        {
            throw new MYException("角色被用户绑定无法删除");
        }
    }

    /**
     * @return the roleDAO
     */
    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }

    /**
     * @param roleDAO
     *            the roleDAO to set
     */
    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }

    /**
     * @return the roleAuthDAO
     */
    public RoleAuthDAO getRoleAuthDAO()
    {
        return roleAuthDAO;
    }

    /**
     * @param roleAuthDAO
     *            the roleAuthDAO to set
     */
    public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO)
    {
        this.roleAuthDAO = roleAuthDAO;
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

    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }
}
