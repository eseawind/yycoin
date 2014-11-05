/**
 * File Name: PostManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.dao.PostDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.PostManager;
import com.china.center.tools.JudgeTools;


/**
 * PostManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see PostManagerImpl
 * @since 1.0
 */
@Exceptional
public class PostManagerImpl implements PostManager
{
    private PostDAO postDAO = null;

    private StafferDAO stafferDAO = null;

    /**
     * default constructor
     */
    public PostManagerImpl()
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
    public boolean addBean(User user, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        checkAddBean(bean);

        postDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * 更新职务
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean);

        postDAO.updateEntityBean(bean);

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

        checkDelBean(id);

        postDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(PostBean bean)
        throws MYException
    {
        if (postDAO.countByUnique(bean.getName().trim()) > 0)
        {
            throw new MYException("职务名称[%s]已经存在", bean.getName());
        }
    }

    private void checkUpdateBean(PostBean bean)
        throws MYException
    {
        PostBean dep = postDAO.find(bean.getId());

        if (dep == null)
        {
            throw new MYException("职务[%s]不存在", bean.getName());
        }

        if ( !dep.getName().equals(bean.getName()))
        {
            if (postDAO.countByUnique(bean.getName()) > 0)
            {
                throw new MYException("职务名称[%s]已经存在", bean.getName());
            }
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
        if (stafferDAO.countByPostId(id) > 0)
        {
            throw new MYException("职务被人员绑定无法删除");
        }
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

    /**
     * @return the postDAO
     */
    public PostDAO getPostDAO()
    {
        return postDAO;
    }

    /**
     * @param postDAO
     *            the postDAO to set
     */
    public void setPostDAO(PostDAO postDAO)
    {
        this.postDAO = postDAO;
    }

}
