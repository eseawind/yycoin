/**
 * File Name: TemplateFileManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager.impl;


import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.dao.FlowVSTemplateDAO;
import com.china.center.oa.flow.dao.TemplateFileDAO;
import com.china.center.oa.flow.manager.TemplateFileManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;


/**
 * TemplateFileManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TemplateFileManagerImpl
 * @since 1.0
 */
@Exceptional
public class TemplateFileManagerImpl implements TemplateFileManager
{
    private TemplateFileDAO templateFileDAO = null;

    private FlowVSTemplateDAO flowVSTemplateDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public TemplateFileManagerImpl()
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
    public boolean addBean(User user, TemplateFileBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAdd(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        bean.setLogTime(TimeTools.now());

        templateFileDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkAdd(User user, TemplateFileBean bean)
        throws MYException
    {
        if (templateFileDAO.countByUnique(bean.getName()) != 0)
        {
            throw new MYException("模板名称[%s]重复", bean.getName());
        }
    }

    /**
     * deleteBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelete(user, id);

        templateFileDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkDelete(User user, String id)
        throws MYException
    {
        // check if ref by flow
        if (flowVSTemplateDAO.countByFK(id, AnoConstant.FK_FIRST) > 0)
        {
            throw new MYException("模板已经被流程使用,不能删除");
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
    public boolean updateBean(User user, TemplateFileBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdate(user, bean);

        bean.setLogTime(TimeTools.now());

        templateFileDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkUpdate(User user, TemplateFileBean bean)
        throws MYException
    {
        TemplateFileBean old = templateFileDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("模板[%s]不存在", bean.getName());
        }

        if ( !old.getName().equals(bean.getName()))
        {
            if (templateFileDAO.countByUnique(bean.getName()) != 0)
            {
                throw new MYException("模板名称[%s]重复", bean.getName());
            }
        }

        // 不能修改下面的属性
        bean.setPath(old.getPath());

        bean.setFileName(old.getFileName());
    }

    /**
     * @return the templateFileDAO
     */
    public TemplateFileDAO getTemplateFileDAO()
    {
        return templateFileDAO;
    }

    /**
     * @param templateFileDAO
     *            the templateFileDAO to set
     */
    public void setTemplateFileDAO(TemplateFileDAO templateFileDAO)
    {
        this.templateFileDAO = templateFileDAO;
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
     * @return the flowVSTemplateDAO
     */
    public FlowVSTemplateDAO getFlowVSTemplateDAO()
    {
        return flowVSTemplateDAO;
    }

    /**
     * @param flowVSTemplateDAO
     *            the flowVSTemplateDAO to set
     */
    public void setFlowVSTemplateDAO(FlowVSTemplateDAO flowVSTemplateDAO)
    {
        this.flowVSTemplateDAO = flowVSTemplateDAO;
    }
}
