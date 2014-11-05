/**
 * File Name: DutyManagerIMpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.listener.DutyListener;
import com.china.center.oa.publics.manager.DutyManager;
import com.china.center.tools.JudgeTools;


/**
 * DutyManagerIMpl
 * 
 * @author ZHUZHU
 * @version 2010-7-10
 * @see DutyManagerIMpl
 * @since 1.0
 */
@Exceptional
public class DutyManagerImpl extends AbstractListenerManager<DutyListener> implements DutyManager
{
    private final Log operationLog = LogFactory.getLog("opr");

    private DutyDAO dutyDAO = null;

    private CommonDAO commonDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.DutyManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.DutyBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, DutyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name || #icp &unique @dutyDAO", "名称或者税务证号已经存在");

        dutyDAO.saveEntityBean(bean);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.DutyManager#delBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        DutyBean old = dutyDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Collection<DutyListener> values = this.listenerMapValues();

        for (DutyListener dutyListener : values)
        {
            dutyListener.onDeleteDuty(user, old);
        }

        operationLog.info(user.getStafferName() + "删除了纳税实体:" + old);

        dutyDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.DutyManager#udateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.DutyBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, DutyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        DutyBean old = dutyDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name || #icp &unique2 @dutyDAO", "名称或者税务证号已经存在");

        dutyDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
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
