/**
 * File Name: GroupManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.manager.impl;


import java.util.Collection;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.listener.GroupListener;
import com.china.center.oa.group.manager.GroupManager;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;


/**
 * GroupManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see GroupManagerImpl
 * @since 1.0
 */
@Exceptional
public class GroupManagerImpl extends AbstractListenerManager<GroupListener> implements GroupManager
{
    private GroupDAO groupDAO = null;

    private CommonDAO commonDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    /**
     * default constructor
     */
    public GroupManagerImpl()
    {
    }

    /**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, GroupBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        checkAdd(user, bean);

        bean.setStafferId(user.getStafferId());

        bean.setId(commonDAO.getSquenceString20());

        groupDAO.saveEntityBean(bean);

        List<GroupVSStafferBean> items = bean.getItems();

        // save items
        if ( !ListTools.isEmptyOrNull(items))
        {
            for (GroupVSStafferBean groupVSStafferBean : items)
            {
                groupVSStafferBean.setGroupId(bean.getId());

                groupVSStafferDAO.saveEntityBean(groupVSStafferBean);
            }
        }

        return true;
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkAdd(User user, GroupBean bean)
        throws MYException
    {
        // handle duplicate name
        if (groupDAO.countByNameAndStafferId(bean.getName(), user.getStafferId()) > 0)
        {
            throw new MYException("群组名称[%s]重复", bean.getName());
        }
    }

    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, GroupBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        checkUpdate(user, bean);

        groupDAO.updateEntityBean(bean);

        List<GroupVSStafferBean> items = bean.getItems();

        // save items
        if ( !ListTools.isEmptyOrNull(items))
        {
            // delete old items
            groupVSStafferDAO.deleteEntityBeansByFK(bean.getId());

            // save new items
            for (GroupVSStafferBean groupVSStafferBean : items)
            {
                groupVSStafferBean.setGroupId(bean.getId());

                groupVSStafferDAO.saveEntityBean(groupVSStafferBean);
            }
        }

        return true;
    }

    /**
     * checkUpdate
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkUpdate(User user, GroupBean bean)
        throws MYException
    {
        GroupBean old = groupDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("群组[%s]不存在,请核实", bean.getName());
        }

        bean.setStafferId(old.getStafferId());

        if ( !user.getStafferId().equals(old.getStafferId()) && bean.getType() != GroupConstant.GROUP_TYPE_SYSTEM)
        {
            throw new MYException("只能操作自己创建的群组");
        }

        // handle duplicate name
        if ( !old.getName().equals(bean.getName()))
        {
            if (groupDAO.countByNameAndStafferId(bean.getName(), user.getStafferId()) > 0)
            {
                throw new MYException("群组名称[%s]重复", bean.getName());
            }
        }

        bean.setType(old.getType());
    }

    /**
     * delBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDel(user, id);

        groupDAO.deleteEntityBean(id);

        groupVSStafferDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * checkDel
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDel(User user, String id)
        throws MYException
    {
        GroupBean old = groupDAO.find(id);

        if (old == null)
        {
            throw new MYException("群组不存在,请核实");
        }

        if ( !user.getStafferId().equals(old.getStafferId()))
        {
            throw new MYException("只能操作自己创建的群组");
        }

        if (old.getType() == GroupConstant.GROUP_TYPE_SYSTEM)
        {
            throw new MYException("不能删除系统群组");
        }

        Collection<GroupListener> listeners = this.listenerMapValues();

        for (GroupListener groupListener : listeners)
        {
            groupListener.onDeleteGroup(user, id);
        }
    }

    /**
     * @return the groupDAO
     */
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }
}
