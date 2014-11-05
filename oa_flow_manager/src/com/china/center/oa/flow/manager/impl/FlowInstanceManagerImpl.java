/**
 * File Name: FlowInstanceManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager.impl;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.flow.bean.FlowBelongBean;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.FlowInstanceLogBean;
import com.china.center.oa.flow.bean.FlowInstanceViewBean;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.bean.InstanceTemplateBean;
import com.china.center.oa.flow.constant.DymFlowConstant;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowBelongDAO;
import com.china.center.oa.flow.dao.FlowDefineDAO;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceLogDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.FlowTokenDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.InstanceTemplateDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.dao.TokenVSOperationDAO;
import com.china.center.oa.flow.dao.TokenVSTemplateDAO;
import com.china.center.oa.flow.helper.FlowHelper;
import com.china.center.oa.flow.manager.FlowInstanceManager;
import com.china.center.oa.flow.manager.PluginManager;
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.flow.vs.TokenVSOperationBean;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.osgi.dym.DynamicBundleTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.FileTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * FlowInstanceManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceManagerImpl
 * @since 1.0
 */
@Exceptional
public class FlowInstanceManagerImpl implements FlowInstanceManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    /**
     * 触发器的日志
     */
    private final Log planLog = LogFactory.getLog("plan");

    private CommonDAO commonDAO = null;

    private MailMangaer mailMangaer = null;

    private FlowDefineDAO flowDefineDAO = null;

    private StafferDAO stafferDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private PluginManager pluginManager = null;

    private FlowTokenDAO flowTokenDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private FlowBelongDAO flowBelongDAO = null;

    private InstanceTemplateDAO instanceTemplateDAO = null;

    private TokenVSTemplateDAO tokenVSTemplateDAO = null;

    private TokenVSOperationDAO tokenVSOperationDAO = null;

    private FlowInstanceLogDAO flowInstanceLogDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    /**
     * default constructor
     */
    public FlowInstanceManagerImpl()
    {
    }

    /**
     * addFlowInstance(id has happend)
     * 
     * @param user
     * @param bean
     * @param operation
     *            0:save 1:submit
     * @param processers
     *            next token process
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addFlowInstance(User user, FlowInstanceBean bean, int operation, List<String> processers)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        FlowInstanceBean old = flowInstanceDAO.find(bean.getId());

        if (old == null)
        {
            flowInstanceDAO.saveEntityBean(bean);
        }
        else
        {
            flowInstanceDAO.updateEntityBean(bean);
        }

        // submit instance
        if (operation == 1)
        {
            nextFlowInstance("", user, bean, processers, FlowConstant.OPERATION_SUBMIT);
        }

        return true;
    }

    /**
     * @param user
     * @param files
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addFlowInstanceTemplate(User user, List<InstanceTemplateBean> files)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, files);

        List<InstanceTemplateBean> copy = new ArrayList();

        for (InstanceTemplateBean instanceTemplateBean : files)
        {
            copy.add(instanceTemplateBean.clones());
        }

        for (InstanceTemplateBean instanceTemplateBean : copy)
        {
            instanceTemplateBean.setId(commonDAO.getSquenceString20());
        }

        instanceTemplateDAO.saveAllEntityBeans(copy);

        return true;
    }

    /**
     * updateFlowInstance
     * 
     * @param user
     * @param bean
     * @param operation
     * @param processers
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateFlowInstance(User user, FlowInstanceBean bean, int operation, List<String> processers)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        flowInstanceDAO.updateEntityBean(bean);

        // submit instance
        if (operation == 1)
        {
            nextFlowInstance("", user, bean, processers, FlowConstant.OPERATION_SUBMIT);
        }

        return true;
    }

    /**
     * deleteFlowInstance
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteFlowInstance(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        FlowInstanceBean old = checkDel(user, id);

        flowInstanceLogDAO.deleteEntityBeansByFK(id);

        flowInstanceViewDAO.deleteEntityBeansByFK(id);

        if ( !FlowHelper.isSubInstance(old))
        {
            // delete template files
            deleteTemplateFiles(id);

            // delete attachment
            deleteAttachment(old);
        }

        flowInstanceDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * @param old
     */
    private void deleteAttachment(FlowInstanceBean old)
    {
        if ( !StringTools.isNullOrNone(old.getAttachment()))
        {
            String allPath = FileTools.formatPath2(this.getFlowAtt()) + old.getAttachment();

            try
            {
                FileTools.delete(allPath);

                _logger.info("delete file:" + allPath);
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }
    }

    /**
     * @param id
     */
    private void deleteTemplateFiles(Serializable id)
    {
        List<InstanceTemplateBean> templateFiles = instanceTemplateDAO.queryEntityBeansByFK(id);

        // delete template files
        for (InstanceTemplateBean instanceTemplateBean : templateFiles)
        {
            deleteInstanceTemplateAndFile(instanceTemplateBean);
        }
    }

    /**
     * deleteInstanceTemplateAndFile
     * 
     * @param instanceTemplateBean
     */
    private void deleteInstanceTemplateAndFile(InstanceTemplateBean instanceTemplateBean)
    {
        String allPath = "";

        if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES)
        {
            allPath = FileTools.formatPath2(this.getInstanceReadonlyAttachmentRoot()) + instanceTemplateBean.getPath();
        }
        else
        {
            allPath = FileTools.formatPath2(this.getInstanceAttachmentRoot()) + instanceTemplateBean.getPath();
        }

        try
        {
            FileTools.delete(allPath);

            _logger.info("delete file:" + allPath);
        }
        catch (IOException e)
        {
            _logger.error(e, e);
        }

        // delete data in db
        instanceTemplateDAO.deleteEntityBean(instanceTemplateBean.getId());
    }

    /**
     * pass instance
     * 
     * @param user
     * @param instanceId
     * @param opinion
     * @param processers
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean passFlowInstance(User user, String instanceId, String opinion, List<String> processers)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = checkPass(user, instanceId);

        // 下一个环节
        nextFlowInstance(opinion, user, bean, processers, FlowConstant.OPERATION_PASS);

        return true;
    }

    /**
     * reject instance
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = checkReject(instanceId);

        // 驳回到上一环节
        rejectFlowInstanceInner(opinion, user, bean, FlowConstant.OPERATION_REJECT);

        return true;
    }

    /**
     * reject instance
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectToRootFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = checkReject(instanceId);

        // 驳回到上一环节
        rejectFlowInstanceInner(opinion, user, bean, FlowConstant.OPERATION_PARENT_REJECT);

        return true;
    }

    /**
     * rejectAllFlowInstance
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectAllFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = checkReject(instanceId);

        // 驳回到上一环节
        rejectFlowInstanceInner(opinion, user, bean, FlowConstant.OPERATION_REJECTALL);

        return true;
    }

    /**
     * exceptionEndFlowInstance
     * 
     * @param user
     * @param instanceId
     * @param opinion
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean exceptionEndFlowInstance(User user, String instanceId, String opinion)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, instanceId);

        FlowInstanceBean bean = checkEx(instanceId);

        // exception end instance
        exceptionEndFlowInstanceInner(opinion, user, bean);

        return true;
    }

    /**
     * delete temp file in create instance(0 0 2 * * ?)
     */
    @Transactional(rollbackFor = {MYException.class})
    public void deleteTempTemplateFile()
    {
        // query -10 and -1 InstanceTemplateBeans
        List<InstanceTemplateBean> templateList = instanceTemplateDAO.queryByDuration(TimeTools.now( -7),
            TimeTools.now( -1));

        for (InstanceTemplateBean instanceTemplateBean : templateList)
        {
            if ( !flowInstanceDAO.isExist(instanceTemplateBean.getInstanceId()))
            {
                planLog.info("delete temp InstanceTemplateBean:" + BeanUtil.toStrings(instanceTemplateBean));

                deleteInstanceTemplateAndFile(instanceTemplateBean);
            }
        }
    }

    /**
     * checkReject
     * 
     * @param instanceId
     * @return
     * @throws MYException
     */
    private FlowInstanceBean checkReject(String instanceId)
        throws MYException
    {
        FlowInstanceBean bean = flowInstanceDAO.find(instanceId);

        if (bean == null)
        {
            throw new MYException("流程实例不存在");
        }

        return bean;
    }

    /**
     * checkDel
     * 
     * @param instanceId
     * @return
     * @throws MYException
     */
    private FlowInstanceBean checkDel(User user, Serializable instanceId)
        throws MYException
    {
        FlowInstanceBean bean = flowInstanceDAO.find(instanceId);

        if (bean == null)
        {
            throw new MYException("流程实例不存在");
        }

        if ( !FlowHelper.isSubInstance(bean))
        {
            if (bean.getStatus() != FlowConstant.FLOW_INSTANCE_BEGIN)
            {
                throw new MYException("流程实例不在初始态,不能删除");
            }

            if ( !user.getStafferId().equals(bean.getStafferId()))
            {
                throw new MYException("流程实例不能被删除");
            }
        }

        return bean;
    }

    /**
     * checkEx
     * 
     * @param instanceId
     * @return
     * @throws MYException
     */
    private FlowInstanceBean checkEx(String instanceId)
        throws MYException
    {
        FlowInstanceBean bean = flowInstanceDAO.find(instanceId);

        if (bean == null)
        {
            throw new MYException("流程实例不存在");
        }

        if (bean.getType() == FlowConstant.FLOW_PARENTTYPE_SUB)
        {
            throw new MYException("子流程不能进行异常结束的操作");
        }

        return bean;
    }

    /**
     * 驳回流程
     * 
     * @param option
     * @param user
     * @param bean
     * @param operation
     * @throws MYException
     */
    private void rejectFlowInstanceInner(String option, User user, FlowInstanceBean bean, int operation)
        throws MYException
    {
        FlowTokenBean token = flowTokenDAO.find(bean.getCurrentTokenId());

        if (token == null)
        {
            throw new MYException("流程没有开始环节");
        }

        hasCurrentAuth(user, bean.getId(), operation);

        FlowTokenBean preOrders = checkRejectAuth(user, bean, operation, token);

        // have reject to root flow
        if ( !preOrders.getFlowId().equals(bean.getFlowId()))
        {
            bean = getRootInstance(bean);
        }

        List<String> process = new ArrayList<String>();

        if (operation == FlowConstant.OPERATION_REJECT)
        {
            FlowInstanceLogBean lastLog = flowInstanceLogDAO.findLastLog(bean.getId(), preOrders.getId(), token.getId());

            if (lastLog == null)
            {
                // may be reject jump a abstract token,so must query pre token's next token
                FlowTokenBean preNext = flowTokenDAO.findToken(bean.getFlowId(), preOrders.getNextOrders());

                if (preNext != null)
                {
                    lastLog = flowInstanceLogDAO.findLastLog(bean.getId(), preOrders.getId(), preNext.getId());
                }

                if (lastLog == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }
            }

            process.add(lastLog.getStafferId());
        }
        else
        {
            process.add(bean.getStafferId());
        }

        processBelong(bean, preOrders, process);

        // update status and currentTokenId
        flowInstanceDAO.updateCurrentTokenId(bean.getId(), preOrders.getId());

        flowInstanceDAO.updateStatus(bean.getId(), preOrders.getOrders());

        addFlowLog(option, operation, token.getId(), preOrders.getId(), bean, user, process.get(0));

        // send mail
        sendMailInFlow(option, user, bean, process, operation, preOrders, null);

        updateTemplateFile(bean.getId());
    }

    /**
     * @param bean
     * @param operation
     * @param token
     * @return
     * @throws MYException
     */
    private FlowTokenBean checkRejectAuth(User user, FlowInstanceBean bean, int operation, FlowTokenBean token)
        throws MYException
    {
        TokenVSOperationBean operationVS = tokenVSOperationDAO.findByUnique(token.getId());

        if (operationVS == null)
        {
            throw new MYException("数据不完备,请重新操作");
        }

        if (operation == FlowConstant.OPERATION_REJECT && operationVS.getReject() != FlowConstant.OPERATION_YES)
        {
            throw new MYException("没有此环节的驳回权限");
        }

        if (operation == FlowConstant.OPERATION_REJECTALL && operationVS.getRejectAll() != FlowConstant.OPERATION_YES)
        {
            throw new MYException("没有此环节的驳回到初始的权限");
        }

        FlowTokenBean preOrders = null;

        boolean subInstance = FlowHelper.isSubInstance(bean);

        if ( !subInstance)
        {
            if (operation == FlowConstant.OPERATION_REJECT)
            {
                preOrders = flowTokenDAO.findToken(bean.getFlowId(), token.getPreOrders());
            }
            else if (operation == FlowConstant.OPERATION_REJECTALL)
            {
                preOrders = flowTokenDAO.findBeginToken(bean.getFlowId());

                // delete all child instance
                deleteAllChildInstance(user, bean);
            }
        }
        else
        {
            // handle subInstance
            if (operation == FlowConstant.OPERATION_REJECT)
            {
                throw new MYException("子流程内不支持驳回到上一环节,请确认操作");
            }
            else if (operation == FlowConstant.OPERATION_REJECTALL)
            {
                if (token.isBegining())
                {
                    throw new MYException("已经是初始环节,请确认操作");
                }

                preOrders = flowTokenDAO.findBeginToken(bean.getFlowId());
            }
            else if (operation == FlowConstant.OPERATION_PARENT_REJECT)
            {
                preOrders = handleReturnToRoot(user, bean);
            }
        }

        if (preOrders == null)
        {
            throw new MYException("上一环节为空,请确认操作");
        }

        // if pre is ABSTOKEN,system can make sure the token in root flow
        if (preOrders.getType() == FlowConstant.TOKEN_TYPEE_ABSTOKEN)
        {
            FlowInstanceBean rootInstance = getRootInstance(bean);

            // get the last real token
            while (preOrders.getType() == FlowConstant.TOKEN_TYPEE_ABSTOKEN)
            {
                FlowInstanceBean childInstance = flowInstanceDAO.findByParentIdAndTokenId(rootInstance.getId(),
                    preOrders.getId());

                if (childInstance != null && FlowHelper.isSubInstance(childInstance))
                {
                    // delete it
                    deleteFlowInstance(user, childInstance.getId());
                }

                // MAYBUG this is a bug,may it will lost delete some child instance
                preOrders = flowTokenDAO.findToken(preOrders.getFlowId(), preOrders.getPreOrders());
            }
        }

        return preOrders;
    }

    /**
     * deleteAllChildInstance
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void deleteAllChildInstance(User user, FlowInstanceBean bean)
        throws MYException
    {
        List<FlowTokenBean> allTokenList = flowTokenDAO.queryEntityBeansByFK(bean.getFlowId());

        for (FlowTokenBean flowTokenBean : allTokenList)
        {
            if (FlowHelper.isAbstactToken(flowTokenBean))
            {
                FlowInstanceBean childInstance = flowInstanceDAO.findByParentIdAndTokenId(bean.getId(),
                    flowTokenBean.getId());

                if (childInstance != null && FlowHelper.isSubInstance(childInstance))
                {
                    // delete it
                    deleteFlowInstance(user, childInstance.getId());
                }
            }
        }
    }

    /**
     * handleReturnToRoot
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    private FlowTokenBean handleReturnToRoot(User user, FlowInstanceBean bean)
        throws MYException
    {
        FlowTokenBean preOrders;
        // return parent flow
        FlowInstanceBean rootInstanceBean = getRootInstance(bean);

        FlowTokenBean pctoken = flowTokenDAO.find(rootInstanceBean.getCurrentTokenId());

        if (pctoken == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // get parent pre token
        preOrders = flowTokenDAO.findToken(rootInstanceBean.getFlowId(), pctoken.getPreOrders());

        // delete all subflow
        deleteAllSubInstance(user, bean);

        return preOrders;
    }

    /**
     * copy edit to read
     * 
     * @param instanceId
     */
    private void updateTemplateFile(String instanceId)
    {
        List<InstanceTemplateBean> list = instanceTemplateDAO.queryEntityBeansByFK(instanceId);

        List<InstanceTemplateBean> editList = new ArrayList<InstanceTemplateBean>();

        List<InstanceTemplateBean> readList = new ArrayList<InstanceTemplateBean>();

        for (InstanceTemplateBean instanceTemplateBean : list)
        {
            if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES)
            {
                readList.add(instanceTemplateBean);
            }
            else
            {
                editList.add(instanceTemplateBean);
            }
        }

        // copy edit to read
        for (InstanceTemplateBean edit : editList)
        {
            for (InstanceTemplateBean read : readList)
            {
                if (edit.getTemplateId().equals(read.getTemplateId()))
                {
                    String src = FileTools.formatPath2(this.getInstanceAttachmentRoot()) + edit.getPath();

                    String dir = FileTools.formatPath2(this.getInstanceReadonlyAttachmentRoot()) + read.getPath();
                    // copy file
                    try
                    {
                        FileTools.copyFile2(src, dir);
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }
    }

    /**
     * exceptionEndFlowInstanceInner
     * 
     * @param option
     * @param user
     * @param bean
     * @param operation
     * @throws MYException
     */
    private void exceptionEndFlowInstanceInner(String option, User user, FlowInstanceBean bean)
        throws MYException
    {
        FlowTokenBean token = flowTokenDAO.find(bean.getCurrentTokenId());

        if (token == null)
        {
            throw new MYException("流程没有开始环节");
        }

        hasCurrentAuth(user, bean.getId(), FlowConstant.OPERATION_EXEND);

        TokenVSOperationBean operationVS = tokenVSOperationDAO.findByUnique(token.getId());

        if (operationVS == null)
        {
            throw new MYException("数据不完备,请重新操作");
        }

        if (operationVS.getExends() != FlowConstant.OPERATION_YES)
        {
            throw new MYException("没有此环节的异常结束权限");
        }

        FlowTokenBean end = flowTokenDAO.findEndToken(bean.getFlowId());

        if (end == null)
        {
            throw new MYException("结束环节为空,请确认操作");
        }

        // 流程结束的处理
        flowBelongDAO.deleteEntityBeansByFK(bean.getId());

        // 处理查阅的人员
        processViewer(bean, end);

        // update status and currentTokenId
        flowInstanceDAO.updateCurrentTokenId(bean.getId(), end.getId());

        flowInstanceDAO.updateStatus(bean.getId(), end.getOrders());

        updateTemplateFile(bean.getId());

        addFlowLog(option, FlowConstant.OPERATION_EXEND, token.getId(), end.getId(), bean, user, "");

        List<String> processers = new ArrayList<String>();

        processers.add(bean.getStafferId());

        // send mail
        sendMailInFlow(option, user, bean, processers, FlowConstant.OPERATION_EXEND, null, null);
    }

    /**
     * @param instanceId
     * @return
     * @throws MYException
     */
    private FlowInstanceBean checkPass(User user, String instanceId)
        throws MYException
    {
        FlowInstanceBean bean = checkReject(instanceId);

        // 检验日志最后的处理人是当前人

        List<FlowInstanceLogBean> logList = flowInstanceLogDAO.queryEntityBeansByFK(instanceId);

        if ( !ListTools.isEmptyOrNull(logList))
        {
            // 最后一个日志
            Collections.sort(logList, new Comparator<FlowInstanceLogBean>()
            {
                public int compare(FlowInstanceLogBean o1, FlowInstanceLogBean o2)
                {
                    return (int) (Long.parseLong(o2.getId()) - Long.parseLong(o1.getId()));
                }
            });

            if ( !logList.get(0).getNextStafferId().equals(user.getStafferId()))
            {
                throw new MYException("流程出现严重错误,您不是上环节指定处理人,请联系管理员!");
            }
        }

        return bean;
    }

    /**
     * 开始流程下一个环节
     * 
     * @param bean
     */
    private int nextFlowInstance(String option, User user, FlowInstanceBean bean, List<String> processers, int operation)
        throws MYException
    {
        FlowTokenBean token = null;

        FlowTokenBean next = null;

        if ( !StringTools.isNullOrNone(bean.getCurrentTokenId()))
        {
            // get current token
            token = flowTokenDAO.find(bean.getCurrentTokenId());

            if (token == null)
            {
                throw new MYException("当前环节不存在");
            }

            if (token.isEnding())
            {
                throw new MYException("流程实例已经结束");
            }

            // get current token TokenVSOperationBean
            TokenVSOperationBean tokenOperation = tokenVSOperationDAO.findByUnique(token.getId());

            if (tokenOperation != null)
            {
                if (tokenOperation.getLiminal() > 0.0 && bean.getLiminal() > 0.0
                    && tokenOperation.getLiminal() >= bean.getLiminal())
                {
                    // NOTE zhuzhu
                    next = flowTokenDAO.findEndToken(bean.getFlowId());

                    option = "[阈值内自动结束]" + option;
                }
            }

            if (next == null)
            {
                next = flowTokenDAO.findToken(bean.getFlowId(), token.getNextOrders());
            }
        }
        else
        {
            // abstract instance
            FlowTokenBean beginToken = flowTokenDAO.findBeginToken(bean.getFlowId());

            if (beginToken == null)
            {
                throw new MYException("开始环不存在,请重新操作");
            }

            next = beginToken;
        }

        if (next == null)
        {
            throw new MYException("下一环节为空,请确认操作");
        }

        if ( !next.isEnding())
        {
            hasAuth(user, token, bean.getId(), processers);
        }

        if (next.getType() == FlowConstant.TOKEN_TYPE_REALTOKEN)
        {
            handleRealToken(option, user, bean, processers, operation, token, next);
        }
        else
        {
            processAbstractToken(option, user, bean, processers, operation, token, next);
        }

        // update status and currentTokenId
        flowInstanceDAO.updateCurrentTokenId(bean.getId(), next.getId());

        flowInstanceDAO.updateStatus(bean.getId(), next.getOrders());

        updateTemplateFile(bean.getId());

        // 如果是子流程到了最后的环节应该进入下一环
        if (FlowHelper.isSubInstance(bean) && next.isEnding())
        {
            FlowInstanceBean parentInstance = getParentInstance(bean);

            nextFlowInstance(option, user, parentInstance, processers, operation);
        }

        return next.getOrders();
    }

    /**
     * handleRealToken
     * 
     * @param option
     * @param user
     * @param bean
     * @param processers
     * @param operation
     * @param token
     * @param next
     * @throws MYException
     */
    private void handleRealToken(String option, User user, FlowInstanceBean bean, List<String> processers,
                                 int operation, FlowTokenBean token, FlowTokenBean next)
        throws MYException
    {
        // process liminal
        if (next.isEnding())
        {
            // 流程结束的处理
            flowBelongDAO.deleteEntityBeansByFK(bean.getId());

            if ( !FlowHelper.isSubInstance(bean))
            {
                // 处理查阅的人员
                processViewer(bean, next);
            }
        }
        else
        {
            processBelong(bean, next, processers);

            if (FlowHelper.isSubInstance(bean))
            {
                // 处理父流程所属关系
                processParentBelong(bean, processers);
            }
        }

        if (processers.isEmpty())
        {
            processers.add("");
        }

        String cid = "";

        if (token != null)
        {
            cid = token.getId();
        }
        else
        {
            cid = next.getId();
        }

        if (FlowHelper.isSubInstance(bean))
        {
            // add root flow
            FlowInstanceBean rootInstance = getRootInstance(bean);

            addFlowLog(option, operation, cid, next.getId(), rootInstance, user, processers.get(0));
        }

        if (next.isEnding())
        {
            // 结束的人指定为系统管理员
            addFlowLog(option, operation, cid, next.getId(), bean, user, StafferConstant.SUPER_STAFFER);
        }
        else
        {
            addFlowLog(option, operation, cid, next.getId(), bean, user, processers.get(0));
        }

        // send mail
        if ( !next.isEnding())
        {
            sendMailInFlow(option, user, getRootInstance(bean), processers, operation, next, bean);
        }
        else
        {
            List<String> pro = new ArrayList<String>();

            pro.add(bean.getStafferId());

            sendMailInFlow(option, user, getRootInstance(bean), pro, operation, next, bean);
        }
    }

    /**
     * processAbstractToken
     * 
     * @param user
     * @param bean
     * @param processers
     * @param operation
     * @param next
     * @throws MYException
     */
    private void processAbstractToken(String option, User user, FlowInstanceBean bean, List<String> processers,
                                      int operation, FlowTokenBean token, FlowTokenBean next)
        throws MYException
    {
        // handle abstract token
        String subFlowId = next.getSubFlowId();

        FlowDefineBean subFlow = flowDefineDAO.find(subFlowId);

        if (subFlow == null)
        {
            throw new MYException("子流程不存在,请确认操作");
        }

        FlowInstanceBean subInstance = new FlowInstanceBean();

        BeanUtil.copyProperties(subInstance, bean);

        subInstance.setId(commonDAO.getSquenceString20());

        subInstance.setType(FlowConstant.FLOW_PARENTTYPE_SUB);

        subInstance.setParentId(bean.getId());

        subInstance.setFlowId(subFlowId);

        subInstance.setCurrentTokenId("");

        // 设置父的token id
        subInstance.setParentTokenId(next.getId());

        // NOTE copy file to subinstance
        List<InstanceTemplateBean> queryEntityBeansByFK = instanceTemplateDAO.queryEntityBeansByFK(bean.getId());

        List<TokenVSTemplateBean> subTemplateList = tokenVSTemplateDAO.queryByFlowId(subFlowId);

        // filter operation
        for (Iterator iterator = queryEntityBeansByFK.iterator(); iterator.hasNext();)
        {
            InstanceTemplateBean instanceTemplateBean = (InstanceTemplateBean)iterator.next();

            if (containTemplate(instanceTemplateBean.getTemplateId(), subTemplateList))
            {
                instanceTemplateBean.setId(commonDAO.getSquenceString20());

                instanceTemplateBean.setFlowId(subFlowId);

                instanceTemplateBean.setInstanceId(subInstance.getId());
            }
            else
            {
                iterator.remove();
            }
        }

        instanceTemplateDAO.saveAllEntityBeans(queryEntityBeansByFK);

        addFlowLog(option, operation, token.getId(), next.getId(), bean, user, processers.get(0));

        addFlowInstance(user, subInstance, FlowConstant.OPERATION_PASS, processers);
    }

    /**
     * containTemplate
     * 
     * @param templateId
     * @param subTemplateList
     * @return
     */
    private boolean containTemplate(String templateId, List<TokenVSTemplateBean> subTemplateList)
    {
        for (TokenVSTemplateBean tokenVSTemplateBean : subTemplateList)
        {
            if (tokenVSTemplateBean.getTemplateId().equals(templateId))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * getRootInstance
     * 
     * @param sub
     * @return
     * @throws MYException
     */
    private FlowInstanceBean getRootInstance(FlowInstanceBean sub)
        throws MYException
    {
        if ( !FlowHelper.isSubInstance(sub))
        {
            return sub;
        }

        FlowInstanceBean parent = flowInstanceDAO.find(sub.getParentId());

        if (parent == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        return getRootInstance(parent);
    }

    /**
     * deleteAllSubInstance
     * 
     * @param user
     * @param sub
     * @throws MYException
     */
    private void deleteAllSubInstance(User user, FlowInstanceBean sub)
        throws MYException
    {
        if ( !FlowHelper.isSubInstance(sub))
        {
            return;
        }

        deleteFlowInstance(user, sub.getId());

        FlowInstanceBean parent = flowInstanceDAO.find(sub.getParentId());

        if (parent == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        deleteAllSubInstance(user, parent);
    }

    /**
     * getParentInstance
     * 
     * @param sub
     * @return
     * @throws MYException
     */
    private FlowInstanceBean getParentInstance(FlowInstanceBean sub)
        throws MYException
    {
        FlowInstanceBean parent = flowInstanceDAO.find(sub.getParentId());

        if (parent == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        return parent;

    }

    /**
     * processParentBelong
     * 
     * @param bean
     * @param processers
     * @throws MYException
     */
    private void processParentBelong(FlowInstanceBean bean, List<String> processers)
        throws MYException
    {
        FlowInstanceBean parentBean = flowInstanceDAO.find(bean.getParentId());

        if (parentBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        FlowTokenBean pct = flowTokenDAO.find(parentBean.getCurrentTokenId());

        if (pct == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        processBelong(parentBean, pct, processers);

        // 递归处理归属,一直到主流程
        if (FlowHelper.isSubInstance(parentBean))
        {
            processParentBelong(parentBean, processers);
        }
    }

    /**
     * @param option
     * @param user
     * @param bean
     * @param processers
     * @param operation
     * @throws MYException
     */
    private void sendMailInFlow(String option, User user, FlowInstanceBean bean, List<String> processers,
                                int operation, FlowTokenBean next, FlowInstanceBean srcInstance)
        throws MYException
    {
        MailBean mail = new MailBean();

        Formatter formatter = new Formatter();

        StafferBean sb = stafferDAO.find(bean.getStafferId());

        if (sb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String srcName = sb.getName();

        FlowDefineBean defined = flowDefineDAO.find(bean.getFlowId());

        String sfafferName = "";

        String definedName = "";

        if (definedName != null)
        {
            definedName = defined.getName();
        }

        mail.setTitle(formatter.format("系统通知:%s发出的[%s]需要您处理--%s", srcName, definedName, bean.getTitle()).toString());

        StringBuilder builder = new StringBuilder();

        sb = stafferDAO.find(user.getStafferId());

        sfafferName = "";

        if (sb != null)
        {
            sfafferName = sb.getName();
        }

        builder.append("处理人:" + sfafferName).append("\r\n");

        builder.append("当前操作:" + DefinedCommon.getValue("instanceOper", operation)).append("\r\n");

        builder.append("处理意见:").append(option).append("\r\n");

        builder.append("处理时间:").append(TimeTools.now()).append("\r\n");

        builder.append("\r\n");

        builder.append("此邮件系统自动发出,无需回复").append("\r\n");

        mail.setContent(builder.toString());

        mail.setSenderId(StafferConstant.SUPER_STAFFER);

        mail.setReveiveIds(listToString(processers));

        // cc mail to creater
        mail.setReveiveIds2(bean.getStafferId());

        mail.setHref("../flow/instance.do?method=findFlowInstance&id=" + bean.getId());

        // send mail
        mailMangaer.addMailWithoutTransactional(user, mail);

        sb = stafferDAO.find(processers.get(0));

        if (srcInstance == null)
        {
            // send sms
            sendSMS(bean, operation, next, sb, bean.getId(), srcName);
        }
        else
        {
            sendSMS(bean, operation, next, sb, srcInstance.getId(), srcName);
        }
    }

    /**
     * sendSMS
     * 
     * @param bean
     * @param operation
     * @param next
     * @param sb
     * @param fkId
     */
    private void sendSMS(FlowInstanceBean bean, int operation, FlowTokenBean next, StafferBean sb, String fkId,
                         String srcName)
    {
        if (next == null)
        {
            return;
        }

        boolean comeIn = next.isBegining();

        if (operation == FlowConstant.OPERATION_PASS || operation == FlowConstant.OPERATION_SUBMIT)
        {
            comeIn = true;
        }
        else
        {
            comeIn = false;
        }

        List<TokenVSHanderBean> tvsh = tokenVSHanderDAO.queryEntityBeansByFK(next.getId());

        if (tvsh.isEmpty())
        {
            return;
        }

        FlowDefineBean defind = flowDefineDAO.find(bean.getFlowId());

        if (defind == null)
        {
            return;
        }

        TokenVSHanderBean view = tvsh.get(0);

        HandlePlugin plugin = pluginManager.getHandlePlugin(view.getType());

        if (plugin == null)
        {
            return;
        }

        if (plugin.getType() == FlowConstant.FLOW_PLUGIN_SELF)
        {
            return;
        }

        List<StafferBean> staffList = plugin.listNextHandler(bean.getId(), next.getId());

        // if not exception,send sms
        if (next != null && operation != FlowConstant.OPERATION_EXEND && !StringTools.isNullOrNone(sb.getHandphone())
            && comeIn && !next.isEnding())
        {
            // 动态引入的判断
            if ( !DynamicBundleTools.isServiceExist(DymFlowConstant.SMSDAO))
            {
                return;
            }

            ShortMessageTaskDAO shortMessageTaskDAO = DynamicBundleTools.getService(ShortMessageTaskDAO.class);

            if (shortMessageTaskDAO == null)
            {
                return;
            }

            // send short message
            ShortMessageTaskBean sms = new ShortMessageTaskBean();

            sms.setId(commonDAO.getSquenceString20());

            sms.setFk(fkId);

            sms.setType(HandleMessage.TYPE_FLOW);

            sms.setHandId(RandomTools.getRandomMumber(4));

            sms.setStatus(ShortMessageConstant.STATUS_INIT);

            sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND_RECEIVE);

            sms.setFktoken(next.getId());

            sms.setMenuReceives(toStr(staffList));

            sms.setMessage(srcName + "发起" + defind.getName() + "[" + bean.getTitle() + "]" + "需您审批(" + next.getName()
                           + ").0通过,1驳回.回复格式[" + sms.getHandId() + ":0:处理代号:理由]或[" + sms.getHandId() + ":1:理由].处理代号("
                           + sms.getMenuReceives() + ")");

            sms.setReceiver(sb.getHandphone());

            sms.setStafferId(sb.getId());

            sms.setLogTime(TimeTools.now());

            // 24 hour
            sms.setEndTime(TimeTools.now(1));

            sms.setSendTime(TimeTools.getDateTimeString(this.getInternal()));

            // add sms
            shortMessageTaskDAO.saveEntityBean(sms);
        }
    }

    private String toStr(List<StafferBean> staffList)
    {
        StringBuffer buffer = new StringBuffer();

        if ( !staffList.isEmpty())
        {
            for (int i = 0; i < staffList.size(); i++ )
            {
                buffer.append(i).append(':').append(staffList.get(i).getName()).append(';');
            }
        }
        else
        {
            buffer.append("0:默认系统;");
        }

        return buffer.toString();
    }

    /**
     * @param processers
     * @return
     */
    private String listToString(List<String> processers)
    {
        StringBuilder builder = new StringBuilder();

        for (String string : processers)
        {
            builder.append(string).append(';');
        }

        return builder.toString();
    }

    /**
     * add log
     * 
     * @param opinion
     * @param oprMode
     * @param currentTokenId
     * @param nextTokenId
     * @param bean
     * @param user
     */
    private void addFlowLog(String opinion, int oprMode, String currentTokenId, String nextTokenId,
                            FlowInstanceBean bean, User user, String nextProcessId)
    {
        FlowInstanceLogBean log = new FlowInstanceLogBean();

        log.setOpinion(opinion);

        log.setOprMode(oprMode);

        log.setFlowId(bean.getFlowId());

        log.setInstanceId(bean.getId());

        log.setCreateId(bean.getStafferId());

        log.setStafferId(user.getStafferId());

        log.setLogTime(TimeTools.now());

        log.setTokenId(currentTokenId);

        log.setNextTokenId(nextTokenId);

        log.setNextStafferId(nextProcessId);

        flowInstanceLogDAO.saveEntityBean(log);
    }

    /**
     * NOTE zhuzhu processViewer
     * 
     * @param instanceId
     * @param token
     * @throws MYException
     */
    private void processViewer(FlowInstanceBean fi, FlowTokenBean token)
        throws MYException
    {
        List<FlowViewerBean> views = flowViewerDAO.queryEntityBeansByFK(token.getFlowId());

        if (views.isEmpty())
        {
            return;
        }

        FlowViewerBean view = views.get(0);

        HandlePlugin plugin = pluginManager.getHandlePlugin(view.getType());

        if (plugin == null)
        {
            return;
        }

        List<StafferBean> stafferList = plugin.listInstanceViewer(fi.getId());

        for (StafferBean stafferBean : stafferList)
        {
            FlowInstanceViewBean bean = new FlowInstanceViewBean();

            bean.setFlowId(token.getFlowId());

            bean.setInstanceId(fi.getId());

            bean.setLogTime(TimeTools.now());

            bean.setViewer(stafferBean.getId());

            bean.setCreateId(fi.getStafferId());

            flowInstanceViewDAO.saveEntityBean(bean);
        }
    }

    /**
     * processBelong
     * 
     * @param instanceId
     * @param token
     * @throws MYException
     */
    private void processBelong(FlowInstanceBean instance, FlowTokenBean token, List<String> processers)
        throws MYException
    {
        // delete old belongs
        flowBelongDAO.deleteEntityBeansByFK(instance.getId());

        for (String eitem : processers)
        {
            FlowBelongBean bean = new FlowBelongBean();

            bean.setInstanceId(instance.getId());

            bean.setFlowId(token.getFlowId());

            bean.setTokenId(token.getId());

            bean.setStafferId(eitem);

            bean.setCreateId(instance.getStafferId());

            bean.setLogTime(TimeTools.now());

            flowBelongDAO.saveEntityBean(bean);
        }
    }

    /**
     * 是否有操作权限
     * 
     * @param user
     * @param token
     * @throws MYException
     */
    private void hasAuth(User user, FlowTokenBean token, String instanceId, List<String> processers)
        throws MYException
    {
        if (token == null)
        {
            return;
        }

        if (token.getType() == FlowConstant.TOKEN_TYPEE_ABSTOKEN)
        {
            return;
        }

        hasCurrentAuth(user, instanceId, FlowConstant.OPERATION_PASS);

        if ( !pluginManager.hasAuth(instanceId, token.getPluginType(), processers))
        {
            throw new MYException("指定下一环节的处理者没有操作权限,请确认操作");
        }
    }

    /**
     * @param user
     * @param instanceId
     * @throws MYException
     */
    private void hasCurrentAuth(User user, String instanceId, int operation)
        throws MYException
    {
        // user can handler this instanceId
        List<FlowBelongBean> list = flowBelongDAO.queryEntityBeansByFK(instanceId);

        if (list.isEmpty())
        {
            return;
        }

        boolean auth = false;

        for (FlowBelongBean flowBelongBean : list)
        {
            if (flowBelongBean.getStafferId().equals(user.getStafferId()))
            {
                auth = true;
                break;
            }
        }

        if ( !auth)
        {
            throw new MYException("没有操作权限,请确认操作");
        }
    }

    /**
     * hasOwenInstance
     * 
     * @param user
     * @param instanceId
     * @return
     */
    public boolean hasOwenInstance(User user, String instanceId)
    {
        // user can handler this instanceId
        List<FlowBelongBean> list = flowBelongDAO.queryEntityBeansByFK(instanceId);

        if (list.isEmpty())
        {
            FlowInstanceBean ins = flowInstanceDAO.find(instanceId);

            if (ins == null)
            {
                return false;
            }

            if (ins.getStatus() == FlowConstant.FLOW_INSTANCE_BEGIN)
            {
                return ins.getStafferId().equals(user.getStafferId());
            }
            else
            {
                return false;
            }
        }

        boolean auth = false;

        for (FlowBelongBean flowBelongBean : list)
        {
            if (flowBelongBean.getStafferId().equals(user.getStafferId()))
            {
                auth = true;
                break;
            }
        }

        return auth;
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
     * @return the flowInstanceDAO
     */
    public FlowInstanceDAO getFlowInstanceDAO()
    {
        return flowInstanceDAO;
    }

    /**
     * @param flowInstanceDAO
     *            the flowInstanceDAO to set
     */
    public void setFlowInstanceDAO(FlowInstanceDAO flowInstanceDAO)
    {
        this.flowInstanceDAO = flowInstanceDAO;
    }

    /**
     * @return the flowViewerDAO
     */
    public FlowViewerDAO getFlowViewerDAO()
    {
        return flowViewerDAO;
    }

    /**
     * @param flowViewerDAO
     *            the flowViewerDAO to set
     */
    public void setFlowViewerDAO(FlowViewerDAO flowViewerDAO)
    {
        this.flowViewerDAO = flowViewerDAO;
    }

    /**
     * @return the flowInstanceLogDAO
     */
    public FlowInstanceLogDAO getFlowInstanceLogDAO()
    {
        return flowInstanceLogDAO;
    }

    /**
     * @param flowInstanceLogDAO
     *            the flowInstanceLogDAO to set
     */
    public void setFlowInstanceLogDAO(FlowInstanceLogDAO flowInstanceLogDAO)
    {
        this.flowInstanceLogDAO = flowInstanceLogDAO;
    }

    /**
     * @return the flowInstanceViewDAO
     */
    public FlowInstanceViewDAO getFlowInstanceViewDAO()
    {
        return flowInstanceViewDAO;
    }

    /**
     * @param flowInstanceViewDAO
     *            the flowInstanceViewDAO to set
     */
    public void setFlowInstanceViewDAO(FlowInstanceViewDAO flowInstanceViewDAO)
    {
        this.flowInstanceViewDAO = flowInstanceViewDAO;
    }

    /**
     * @return the flowTokenDAO
     */
    public FlowTokenDAO getFlowTokenDAO()
    {
        return flowTokenDAO;
    }

    /**
     * @param flowTokenDAO
     *            the flowTokenDAO to set
     */
    public void setFlowTokenDAO(FlowTokenDAO flowTokenDAO)
    {
        this.flowTokenDAO = flowTokenDAO;
    }

    /**
     * @return the flowBelongDAO
     */
    public FlowBelongDAO getFlowBelongDAO()
    {
        return flowBelongDAO;
    }

    /**
     * @param flowBelongDAO
     *            the flowBelongDAO to set
     */
    public void setFlowBelongDAO(FlowBelongDAO flowBelongDAO)
    {
        this.flowBelongDAO = flowBelongDAO;
    }

    /**
     * @return the pluginManager
     */
    public PluginManager getPluginManager()
    {
        return pluginManager;
    }

    /**
     * @param pluginManager
     *            the pluginManager to set
     */
    public void setPluginManager(PluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
    }

    /**
     * @return the tokenVSOperationDAO
     */
    public TokenVSOperationDAO getTokenVSOperationDAO()
    {
        return tokenVSOperationDAO;
    }

    /**
     * @param tokenVSOperationDAO
     *            the tokenVSOperationDAO to set
     */
    public void setTokenVSOperationDAO(TokenVSOperationDAO tokenVSOperationDAO)
    {
        this.tokenVSOperationDAO = tokenVSOperationDAO;
    }

    /**
     * @return the instanceTemplateDAO
     */
    public InstanceTemplateDAO getInstanceTemplateDAO()
    {
        return instanceTemplateDAO;
    }

    /**
     * @param instanceTemplateDAO
     *            the instanceTemplateDAO to set
     */
    public void setInstanceTemplateDAO(InstanceTemplateDAO instanceTemplateDAO)
    {
        this.instanceTemplateDAO = instanceTemplateDAO;
    }

    /**
     * @return the instanceAttachmentRoot
     */
    public String getInstanceAttachmentRoot()
    {
        return ConfigLoader.getProperty("instanceAttachmentRoot");
    }

    /**
     * @return the instanceReadonlyAttachmentRoot
     */
    public String getInstanceReadonlyAttachmentRoot()
    {
        return ConfigLoader.getProperty("instanceReadonlyAttachmentRoot");
    }

    /**
     * @return the flowAtt
     */
    public String getFlowAtt()
    {
        return ConfigLoader.getProperty("flowAtt");
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
     * @return the flowDefineDAO
     */
    public FlowDefineDAO getFlowDefineDAO()
    {
        return flowDefineDAO;
    }

    /**
     * @param flowDefineDAO
     *            the flowDefineDAO to set
     */
    public void setFlowDefineDAO(FlowDefineDAO flowDefineDAO)
    {
        this.flowDefineDAO = flowDefineDAO;
    }

    /**
     * @return the tokenVSTemplateDAO
     */
    public TokenVSTemplateDAO getTokenVSTemplateDAO()
    {
        return tokenVSTemplateDAO;
    }

    /**
     * @param tokenVSTemplateDAO
     *            the tokenVSTemplateDAO to set
     */
    public void setTokenVSTemplateDAO(TokenVSTemplateDAO tokenVSTemplateDAO)
    {
        this.tokenVSTemplateDAO = tokenVSTemplateDAO;
    }

    /**
     * @return the tokenVSHanderDAO
     */
    public TokenVSHanderDAO getTokenVSHanderDAO()
    {
        return tokenVSHanderDAO;
    }

    /**
     * @param tokenVSHanderDAO
     *            the tokenVSHanderDAO to set
     */
    public void setTokenVSHanderDAO(TokenVSHanderDAO tokenVSHanderDAO)
    {
        this.tokenVSHanderDAO = tokenVSHanderDAO;
    }

    /**
     * @return the internal
     */
    public int getInternal()
    {
        return ConfigLoader.getIntProperty("internal");
    }
}
