/**
 * File Name: TemplateFileAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.bean.InstanceTemplateBean;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowBelongDAO;
import com.china.center.oa.flow.dao.FlowDefineDAO;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceLogDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.FlowTokenDAO;
import com.china.center.oa.flow.dao.FlowVSTemplateDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.InstanceTemplateDAO;
import com.china.center.oa.flow.dao.TemplateFileDAO;
import com.china.center.oa.flow.dao.TokenVSOperationDAO;
import com.china.center.oa.flow.dao.TokenVSTemplateDAO;
import com.china.center.oa.flow.facade.WorkFlowFacade;
import com.china.center.oa.flow.helper.FlowHelper;
import com.china.center.oa.flow.manager.FlowInstanceManager;
import com.china.center.oa.flow.manager.FlowManager;
import com.china.center.oa.flow.vo.FlowBelongVO;
import com.china.center.oa.flow.vo.FlowDefineVO;
import com.china.center.oa.flow.vo.FlowInstanceLogVO;
import com.china.center.oa.flow.vo.FlowInstanceVO;
import com.china.center.oa.flow.vo.FlowTokenVO;
import com.china.center.oa.flow.vo.TokenVSTemplateVO;
import com.china.center.oa.flow.vs.FlowVSTemplateBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.HTTPTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;


/**
 * FlowInstanceAction
 * 
 * @author ZHUZHU
 * @version 2009-4-21
 * @see FlowInstanceAction
 * @since 1.0
 */
public class FlowInstanceAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private WorkFlowFacade workFlowFacade = null;

    private TemplateFileDAO templateFileDAO = null;

    private FlowManager flowManager = null;

    private StafferDAO stafferDAO = null;

    private CommonDAO commonDAO = null;

    private FlowInstanceManager flowInstanceManager = null;

    private FlowDefineDAO flowDefineDAO = null;

    private TokenVSTemplateDAO tokenVSTemplateDAO = null;

    private InstanceTemplateDAO instanceTemplateDAO = null;

    private FlowVSTemplateDAO flowVSTemplateDAO = null;

    private FlowTokenDAO flowTokenDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    private TokenVSOperationDAO tokenVSOperationDAO = null;

    private FlowBelongDAO flowBelongDAO = null;

    private FlowInstanceLogDAO flowInstanceLogDAO = null;
    
    protected CommonMailManager commonMailManager = null;
    
    private static String QUERYFLOWINSTANCE0 = "queryFlowInstance0";

    private static String QUERYFLOWINSTANCE1 = "queryFlowInstance1";

    private static String QUERYFLOWINSTANCE2 = "queryFlowInstance2";

    private static String QUERYFLOWINSTANCE3 = "queryFlowInstance3";

    /**
     * queryTemplateFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryFlowInstance(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String operationMode = request.getParameter("operationMode");

        String jsonstr = "";

        User user = Helper.getUser(request);

        // query self inatance
        if ("0".equals(operationMode))
        {
            condtion.addCondition("FlowInstanceBean.stafferId", "=", user.getStafferId());

            condtion.addIntCondition("FlowInstanceBean.type", "=",
                FlowConstant.FLOW_PARENTTYPE_ROOT);

            ActionTools.processJSONQueryCondition(QUERYFLOWINSTANCE0, request, condtion);

            condtion.addCondition("order by FlowInstanceBean.logTime desc");

            jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWINSTANCE0, request, condtion,
                this.flowInstanceDAO);
        }

        // query instance which need you handle
        if ("1".equals(operationMode))
        {
            condtion.addCondition("FlowBelongBean.stafferId", "=", user.getStafferId());

            condtion.addIntCondition("FlowDefineBean.parentType", "=",
                FlowConstant.FLOW_PARENTTYPE_ROOT);

            ActionTools.processJSONQueryCondition(QUERYFLOWINSTANCE1, request, condtion);

            condtion.addCondition("order by FlowBelongBean.logTime desc");

            jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWINSTANCE1, request, condtion,
                this.flowBelongDAO);
        }

        // query instance which i can view
        if ("2".equals(operationMode))
        {
            condtion.addCondition("FlowInstanceViewBean.viewer", "=", user.getStafferId());

            ActionTools.processJSONQueryCondition(QUERYFLOWINSTANCE2, request, condtion);

            jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWINSTANCE2, request, condtion,
                this.flowInstanceViewDAO);
        }

        // query instance which i had handle
        if ("3".equals(operationMode))
        {
            condtion.addCondition("FlowInstanceLogBean.stafferId", "=", user.getStafferId());

            ActionTools.processJSONQueryCondition(QUERYFLOWINSTANCE3, request, condtion);

            jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWINSTANCE3, request, condtion,
                this.flowInstanceLogDAO);
        }

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddFlowInstance
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddFlowInstance(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        FlowDefineVO bean = null;

        try
        {
            bean = flowManager.findFlowDefine(id);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            return ActionTools.toError(e.toString(), "queryFlowDefine2", mapping, request);
        }

        if (bean == null)
        {
            return ActionTools.toError("流程定义不存在", mapping, request);
        }

        if ( !FlowHelper.isRelease(bean))
        {
            return ActionTools.toError("流程没有发布不能实例化", "queryFlowDefine2", mapping, request);
        }

        // delete last token
        bean.getTokenVOs().remove(bean.getTokenVOs().size() - 1);

        String instanceId = commonDAO.getSquenceString20();

        request.setAttribute("instanceId", instanceId);

        request.setAttribute("bean", bean);

        request.setAttribute("flowId", id);

        request.setAttribute("currentIndex", 0);

        // if define is template,copy template file to instance and readonly
        if (bean.getMode() == FlowConstant.FLOW_MODE_TEMPLATE)
        {
            List<FlowVSTemplateBean> vs = flowVSTemplateDAO.queryEntityBeansByFK(id);

            List<InstanceTemplateBean> templates = new ArrayList<InstanceTemplateBean>();

            List<InstanceTemplateBean> result = new ArrayList<InstanceTemplateBean>();

            for (FlowVSTemplateBean flowVSTemplateBean : vs)
            {
                TemplateFileBean eacItem = templateFileDAO.find(flowVSTemplateBean.getTemplateId());

                // readonly attachment
                createTemplateFile(id, instanceId, templates, eacItem, this
                    .getInstanceReadonlyAttachmentRoot(), FlowConstant.TEMPLATE_READONLY_YES);

                // edit attachment
                createTemplateFile(id, instanceId, templates, eacItem, this
                    .getInstanceAttachmentRoot(), FlowConstant.TEMPLATE_READONLY_NO);
            }

            User user = Helper.getUser(request);

            try
            {
                flowInstanceManager.addFlowInstanceTemplate(user, templates);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                return ActionTools.toError(e.toString(), "queryFlowDefine2", mapping, request);
            }

            // fiter TEMPLATE_READONLY_YES
            for (Iterator<InstanceTemplateBean> iterator = templates.iterator(); iterator.hasNext();)
            {
                InstanceTemplateBean instanceTemplateBean = iterator.next();

                if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES)
                {
                    iterator.remove();
                }
            }

            List<TokenVSTemplateVO> tvos = tokenVSTemplateDAO.queryEntityVOsByFK(bean
                .getTokens()
                .get(0)
                .getId());

            addItemToResult(templates, result, tvos);

            request.setAttribute("templates", result);

            setUrl(request);
        }

        return mapping.findForward("addFlowInstance");
    }

    /**
     * addItemToResult
     * 
     * @param templates
     * @param result
     * @param tvos
     */
    private void addItemToResult(List<InstanceTemplateBean> templates,
                                 List<InstanceTemplateBean> result, List<TokenVSTemplateVO> tvos)
    {
        for (InstanceTemplateBean instanceTemplateBean : templates)
        {
            for (TokenVSTemplateVO tokenVSTemplateVO : tvos)
            {
                if (instanceTemplateBean.getTemplateId().equals(tokenVSTemplateVO.getTemplateId()))
                {
                    if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES
                        && tokenVSTemplateVO.getViewTemplate() == FlowConstant.TEMPLATE_READONLY_YES)
                    {
                        result.add(instanceTemplateBean);
                    }

                    if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_NO
                        && tokenVSTemplateVO.getEditTemplate() == FlowConstant.TEMPLATE_EDIT_YES)
                    {
                        result.add(instanceTemplateBean);
                    }
                }
            }
        }
    }

    /**
     * comeIntoSub
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward comeIntoSub(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String tokenId = request.getParameter("tokenId");

        String instanceId = request.getParameter("id");

        String update = request.getParameter("update");

        CommonTools.removeParamers(request);

        FlowInstanceBean instance = flowInstanceDAO.find(instanceId);

        if (instance == null)
        {
            return ActionTools.toError("数据错误", "queryFlowDefine2", mapping, request);
        }

        FlowInstanceBean subInstance = flowInstanceDAO
            .findByParentIdAndTokenId(instanceId, tokenId);

        if (subInstance == null)
        {
            return ActionTools.toError("数据错误", "queryFlowDefine2", mapping, request);
        }

        request.setAttribute("id", subInstance.getId());

        request.setAttribute("update", update);

        return findFlowInstance(mapping, form, request, response);
    }

    /**
     * findFlowInstance
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findFlowInstance(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String instanceId = request.getParameter("id");

        if (StringTools.isNullOrNone(instanceId))
        {
            instanceId = request.getAttribute("id").toString();
        }

        request.setAttribute("instanceId", instanceId);

        String update = request.getParameter("update");

        if (StringTools.isNullOrNone(update))
        {
            Object oo = request.getAttribute("update");

            if (oo != null)
            {
                update = oo.toString();
            }
        }

        FlowInstanceVO inatanceVO = flowInstanceDAO.findVO(instanceId);

        if (inatanceVO == null)
        {
            return ActionTools.toError("流程实例不存在", mapping, request);
        }

        FlowDefineVO bean = null;

        try
        {
            bean = flowManager.findFlowDefine(inatanceVO.getFlowId());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            return ActionTools.toError(e.toString(), "queryFlowDefine2", mapping, request);
        }

        if (bean == null)
        {
            return ActionTools.toError("流程定义不存在", mapping, request);
        }

        // delete last token
        bean.getTokenVOs().remove(bean.getTokenVOs().size() - 1);

        request.setAttribute("define", bean);

        request.setAttribute("flowId", inatanceVO.getFlowId());

        FlowTokenBean currentToken = flowTokenDAO.find(inatanceVO.getCurrentTokenId());

        if (currentToken == null)
        {
            return ActionTools.toError("流程环节不存在", mapping, request);
        }

        User user = Helper.getUser(request);

        request.setAttribute("realIndex", currentToken.getOrders());

        boolean hasOwen = false;

        if (flowInstanceManager.hasOwenInstance(user, instanceId))
        {
            hasOwen = true;
            request.setAttribute("currentIndex", currentToken.getOrders());
            request.setAttribute("hasOwen", hasOwen);
        }
        else
        {
            request.setAttribute("currentIndex", FlowConstant.FLOW_INSTANCE_END);
            request.setAttribute("hasOwen", hasOwen);
        }

        // handle templates
        if (bean.getMode() == FlowConstant.FLOW_MODE_TEMPLATE)
        {
            processTemplateView(request, instanceId, currentToken, user);
        }

        List<FlowInstanceLogVO> log = flowInstanceLogDAO.queryEntityVOsByFK(instanceId);

        request.setAttribute("logsVO", log);

        // 定义的环节
        List<FlowTokenVO> tokenVOS = bean.getTokenVOs();

        List<FlowInstanceLogVO> lastLogList = new ArrayList<FlowInstanceLogVO>();

        for (FlowTokenVO flowTokenVO : tokenVOS)
        {
            // 当前的环节之后不显示审批信息
            if (inatanceVO.getCurrentTokenId().equals(flowTokenVO.getId()))
            {
                break;
            }

            // instanceId
            FlowInstanceLogVO lastLog = flowInstanceLogDAO.findLastLogVO(instanceId, flowTokenVO
                .getId());

            lastLogList.add(lastLog);
        }

        List<FlowBelongVO> belongs = flowBelongDAO.queryEntityVOsByFK(instanceId);

        String currentHandles = "";

        for (FlowBelongVO flowBelongVO : belongs)
        {
            currentHandles += flowBelongVO.getStafferName() + " ";
        }

        request.setAttribute("currentHandles", currentHandles);
        request.setAttribute("bean", inatanceVO);
        request.setAttribute("subFlow", inatanceVO.getType() == FlowConstant.FLOW_PARENTTYPE_SUB);

        request.setAttribute("lastLogList", lastLogList);

        if ("1".equals(update))
        {
            if (hasOwen)
            {
                return mapping.findForward("handleFlowInstance");
            }
            else
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                return mapping.findForward("queryFlowInstance2");
            }
        }

        return mapping.findForward("detailFlowInstance");
    }

    /**
     * @param request
     * @param instanceId
     * @param currentToken
     * @param user
     */
    private void processTemplateView(HttpServletRequest request, String instanceId,
                                     FlowTokenBean currentToken, User user)
    {
        List<InstanceTemplateBean> itList = instanceTemplateDAO.queryEntityBeansByFK(instanceId);

        Set<InstanceTemplateBean> result = new HashSet<InstanceTemplateBean>();

        List<InstanceTemplateBean> readonlyTemplates = new ArrayList<InstanceTemplateBean>();

        if (flowInstanceManager.hasOwenInstance(user, instanceId))
        {
            List<TokenVSTemplateVO> tvos = tokenVSTemplateDAO.queryEntityVOsByFK(currentToken
                .getId());

            for (InstanceTemplateBean instanceTemplateBean : itList)
            {
                for (TokenVSTemplateVO tokenVSTemplateVO : tvos)
                {
                    if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES
                        && tokenVSTemplateVO.getViewTemplate() == FlowConstant.TEMPLATE_READONLY_YES)
                    {
                        result.add(instanceTemplateBean);
                    }

                    if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_NO
                        && tokenVSTemplateVO.getEditTemplate() == FlowConstant.TEMPLATE_EDIT_YES)
                    {
                        result.add(instanceTemplateBean);
                    }
                }
            }
        }

        for (InstanceTemplateBean instanceTemplateBean : itList)
        {
            if (instanceTemplateBean.getReadonly() == FlowConstant.TEMPLATE_READONLY_YES)
            {
                readonlyTemplates.add(instanceTemplateBean);
            }
        }

        request.setAttribute("templates", result);

        request.setAttribute("readonlyTemplates", readonlyTemplates);

        setUrl(request);
    }

    /**
     * @param request
     */
    private void setUrl(HttpServletRequest request)
    {
        String eurl = HTTPTools.getHTTPURL(request) + this.getEditServerName() + "/"
                      + request.getSession().getId() + "/instance";

        request.setAttribute("eurl", eurl);

        String rurl = HTTPTools.getHTTPURL(request) + this.getReadonlyServerName() + "/"
                      + request.getSession().getId() + "/instance";

        request.setAttribute("rurl", rurl);
    }

    /**
     * @param id
     * @param instanceId
     * @param templates
     * @param eacItem
     */
    private void createTemplateFile(String id, String instanceId,
                                    List<InstanceTemplateBean> templates, TemplateFileBean eacItem,
                                    String root, int readMode)
    {
        InstanceTemplateBean it = new InstanceTemplateBean();

        it.setFlowId(id);
        it.setInstanceId(instanceId);
        it.setLogTime(TimeTools.now());
        it.setName(eacItem.getName());
        it.setTemplateId(eacItem.getId());
        it.setReadonly(readMode);

        // copy file to dir
        String src = FileTools.formatPath2(this.getTemplateFilePath()) + eacItem.getPath();

        String savePath = mkdir(root);

        String rabsPath = '/' + savePath + '/' + SequenceTools.getSequence(10) + "."
                          + FileTools.getFilePostfix(eacItem.getFileName()).toLowerCase();

        String dir = root + '/' + rabsPath;

        it.setPath(rabsPath);

        try
        {
            FileTools.copyFile2(src, dir);
        }
        catch (IOException e)
        {
            _logger.error(e, e);
        }

        templates.add(it);
    }

    /**
     * addFlowInstance
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addFlowInstance(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        FlowInstanceBean bean = new FlowInstanceBean();

        // 模板最多10M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("queryFlowDefine2");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("queryFlowDefine2");
        }

        BeanUtil.getBean(bean, rds.getParmterMap());

        FlowInstanceBean old = flowInstanceDAO.find(bean.getId());
        // only old is null is add,or else update instance
        if (old == null)
        {
            ActionForward afor = parserAttachment(mapping, request, rds, bean);

            if (afor != null)
            {
                return afor;
            }
        }
        else
        {
            bean.setAttachment(old.getAttachment());

            bean.setFileName(old.getFileName());
        }

        rds.close();

        try
        {
            User user = Helper.getUser(request);

            bean.setLocationId(user.getLocationId());

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            bean.setType(FlowConstant.FLOW_PARENTTYPE_ROOT);

            String processId = rds.getParmterMap().get("processId");

            FlowTokenBean beginToken = flowTokenDAO.findBeginToken(bean.getFlowId());

            if (beginToken == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "流程定义错误");

                return mapping.findForward("queryFlowDefine2");
            }

            bean.setCurrentTokenId(beginToken.getId());

            List<String> process = new ArrayList<String>();

            process.add(processId);

            flowInstanceManager.addFlowInstance(user, bean, CommonTools.parseInt(rds
                .getParmterMap()
                .get("operation")), process);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存流程实例");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存流程实例失败:" + e.getMessage());
        }

        return mapping.findForward("queryFlowInstance");
    }

    /**
     * updatedTemplateFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateTemplateFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        TemplateFileBean bean = new TemplateFileBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            workFlowFacade.updateTemplateFile(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存模板");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存模板失败:" + e.getMessage());
        }

        return mapping.findForward("queryTemplateFile");
    }

    /**
     * deleteMail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteFlowInstance(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            flowInstanceManager.deleteFlowInstance(user, id);

            ajax.setSuccess("成功删除流程实例");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除流程实例失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 流程实例的操作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward handleFlowInstance(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String cmd = request.getParameter("cmd");

        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String nextStafferId = request.getParameter("nextStafferId");

        try
        {
            User user = Helper.getUser(request);
            FlowInstanceBean flowInstanceBean = flowInstanceDAO.find(id);

            if ("pass".equals(cmd))
            {
                List<String> processers = new ArrayList<String>();

                processers.add(nextStafferId);

                flowInstanceManager.passFlowInstance(user, id, reason, processers);
            }

            if ("reject".equals(cmd))
            {
            	sendRejectMail(id,user,reason,flowInstanceBean,"流程单驳回");
                flowInstanceManager.rejectFlowInstance(user, id, reason);
            }

            if ("rejectAll".equals(cmd))
            {
            	sendRejectMail(id,user,reason,flowInstanceBean,"流程单驳回");
                flowInstanceManager.rejectAllFlowInstance(user, id, reason);
            }

            if ("rejectParent".equals(cmd))
            {
                flowInstanceManager.rejectToRootFlowInstance(user, id, reason);
            }

            if ("exception".equals(cmd))
            {
                flowInstanceManager.exceptionEndFlowInstance(user, id, reason);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理流程成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理流程失败:" + e.getErrorContent());
        }

        return mapping.findForward("queryFlowInstance2");
    }

    /**
     * down mail attachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downFlowInstanceAttachment(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response)
        throws ServletException, IOException
    {
        String id = request.getParameter("id");

        FlowInstanceBean attachment = flowInstanceDAO.find(id);

        if (attachment == null)
        {
            return null;
        }

        String path = FileTools.formatPath2(this.getFlowAtt()) + attachment.getAttachment();

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setContentLength((int)file.length());

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(attachment
                                                      .getFileName(), "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param bean
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, FlowInstanceBean bean)
    {
        // parser attachment
        if (rds.haveStream())
        {
            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir(this.getFlowAtt());

                String fileAlais = SequenceTools.getSequence();

                String rabsPath = '/'
                                  + savePath
                                  + '/'
                                  + fileAlais
                                  + "."
                                  + FileTools.getFilePostfix(
                                      FileTools.getFileName(rds.getUniqueFileName())).toLowerCase();

                String filePath = this.getFlowAtt() + '/' + rabsPath;

                bean.setFileName(FileTools.getFileName(rds.getUniqueFileName()));

                bean.setAttachment(rabsPath);

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(rds.getUniqueInputStream(), out);

                ustream.copyStream();
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("queryFlowDefine2");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }

        return null;
    }

    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }

    /**
     * default constructor
     */
    public FlowInstanceAction()
    {
    }

    /**
     * @return the workFlowFacade
     */
    public WorkFlowFacade getWorkFlowFacade()
    {
        return workFlowFacade;
    }

    /**
     * @param workFlowFacade
     *            the workFlowFacade to set
     */
    public void setWorkFlowFacade(WorkFlowFacade workFlowFacade)
    {
        this.workFlowFacade = workFlowFacade;
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
     * @return the templateFilePath
     */
    public String getTemplateFilePath()
    {
        return ConfigLoader.getProperty("templateFilePath");
    }

    /**
     * @return the editServerName
     */
    public String getEditServerName()
    {
        return ConfigLoader.getProperty("editServerName");
    }

    /**
     * @return the readonlyServerName
     */
    public String getReadonlyServerName()
    {
        return ConfigLoader.getProperty("readonlyServerName");
    }

    /**
     * @return the flowManager
     */
    public FlowManager getFlowManager()
    {
        return flowManager;
    }

    /**
     * @param flowManager
     *            the flowManager to set
     */
    public void setFlowManager(FlowManager flowManager)
    {
        this.flowManager = flowManager;
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
     * @return the flowAtt
     */
    public String getFlowAtt()
    {
        return ConfigLoader.getProperty("flowAtt");
    }

    /**
     * @return the flowInstanceManager
     */
    public FlowInstanceManager getFlowInstanceManager()
    {
        return flowInstanceManager;
    }

    /**
     * @param flowInstanceManager
     *            the flowInstanceManager to set
     */
    public void setFlowInstanceManager(FlowInstanceManager flowInstanceManager)
    {
        this.flowInstanceManager = flowInstanceManager;
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
    
    private void sendRejectMail(String fullId, User user, String reason, FlowInstanceBean flowInstanceBean,String subject) 
    {
        StafferBean rejectorBean = stafferDAO.find(user.getStafferId());        
        
        StafferBean approverBean = stafferDAO.find(flowInstanceBean.getStafferId());
        
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("\r\n").append("流程类型:"+ flowInstanceBean.getType()).append(",")
            .append("\r\n").append("单号:"+ fullId).append(",")
            .append("\r\n").append("流程标题:"+  flowInstanceBean.getTitle()).append(",")
            .append("\r\n").append("审批人:"+ rejectorBean.getName()).append(",")
            .append("\r\n").append("审批结果:驳回").append(",")
            .append("\r\n").append("审批意见:" + reason).append(",")
            .append("\r\n").append("审批人电话:"+ rejectorBean.getHandphone());
            
            String message = sb.toString();
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }

	public CommonMailManager getCommonMailManager() {
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager) {
		this.commonMailManager = commonMailManager;
	}
    
    
}
