/**
 * File Name: ShowManagerIMpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Collection;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.listener.ShowListener;
import com.china.center.oa.publics.manager.ShowManager;
import com.china.center.tools.JudgeTools;


/**
 * ShowManagerIMpl
 * 
 * @author ZHUZHU
 * @version 2010-7-10
 * @see ShowManagerIMpl
 * @since 1.0
 */
@Exceptional
public class ShowManagerImpl extends AbstractListenerManager<ShowListener> implements ShowManager
{
    private ShowDAO showDAO = null;

    private CommonDAO commonDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.ShowManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.ShowBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, ShowBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name && #dutyId &unique @showDAO", "名称已经存在");

        showDAO.saveEntityBean(bean);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.ShowManager#delBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        ShowBean old = showDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Collection<ShowListener> values = this.listenerMapValues();

        for (ShowListener showListener : values)
        {
            showListener.onDeleteShow(user, id);
        }

        showDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.ShowManager#udateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.ShowBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, ShowBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        ShowBean old = showDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name && #dutyId &unique2 @showDAO", "名称或者税务证号已经存在");

        showDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * @return the showDAO
     */
    public ShowDAO getShowDAO()
    {
        return showDAO;
    }

    /**
     * @param showDAO
     *            the showDAO to set
     */
    public void setShowDAO(ShowDAO showDAO)
    {
        this.showDAO = showDAO;
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

}
