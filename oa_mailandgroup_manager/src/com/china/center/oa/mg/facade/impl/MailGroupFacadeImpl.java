/**
 * File Name: MailGroupFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mg.facade.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.manager.GroupManager;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.mg.facade.MailGroupFacade;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.JudgeTools;


/**
 * MailGroupFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailGroupFacadeImpl
 * @since 1.0
 */
public class MailGroupFacadeImpl extends AbstarctFacade implements MailGroupFacade
{
    private GroupManager groupManager = null;

    private UserManager userManager = null;

    private MailMangaer mailMangaer = null;

    public MailGroupFacadeImpl()
    {
    }

    /**
     * 增加GroupBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addGroup(String userId, GroupBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        String opr = AuthConstant.PUNLIC_AUTH;

        if (bean.getType() == GroupConstant.GROUP_TYPE_PUBLIC)
        {
            opr = AuthConstant.GROUP_PUBLIC_OPR;
        }

        if (containAuth(user, opr))
        {
            return groupManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * update GroupBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateGroup(String userId, GroupBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        String opr = AuthConstant.PUNLIC_AUTH;

        if (bean.getType() == GroupConstant.GROUP_TYPE_PUBLIC)
        {
            opr = AuthConstant.GROUP_PUBLIC_OPR;
        }

        if (bean.getType() == GroupConstant.GROUP_TYPE_SYSTEM)
        {
            opr = AuthConstant.GROUP_SYSTEM_OPR;
        }

        if (containAuth(user, opr))
        {
            return groupManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 删除GroupBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delGroup(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return groupManager.delBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * addMail
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addMail(String userId, MailBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return mailMangaer.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * deleteMail
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean deleteMail(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return mailMangaer.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * deleteMail2
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean deleteMail2(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return mailMangaer.deleteBean2(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateStatusToRead
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean updateStatusToRead(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return mailMangaer.updateStatusToRead(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateFeebackToYes
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean updateFeebackToYes(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PUNLIC_AUTH))
        {
            return mailMangaer.updateFeebackToYes(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * @return the groupManager
     */
    public GroupManager getGroupManager()
    {
        return groupManager;
    }

    /**
     * @param groupManager
     *            the groupManager to set
     */
    public void setGroupManager(GroupManager groupManager)
    {
        this.groupManager = groupManager;
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
     * @return the mailMangaer
     */
    public MailMangaer getMailMangaer()
    {
        return mailMangaer;
    }

    /**
     * @param mailMangaer
     *            the mailMangaer to set
     */
    public void setMailMangaer(MailMangaer mailMangaer)
    {
        this.mailMangaer = mailMangaer;
    }
}
