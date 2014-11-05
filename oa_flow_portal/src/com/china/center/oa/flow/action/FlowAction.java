/**
 * File Name: FlowAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowDefineDAO;
import com.china.center.oa.flow.dao.FlowTokenDAO;
import com.china.center.oa.flow.dao.FlowVSTemplateDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.TemplateFileDAO;
import com.china.center.oa.flow.facade.WorkFlowFacade;
import com.china.center.oa.flow.manager.FlowManager;
import com.china.center.oa.flow.vo.FlowDefineVO;
import com.china.center.oa.flow.vo.FlowViewerVO;
import com.china.center.oa.flow.vs.FlowVSTemplateBean;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.flow.vs.TokenVSOperationBean;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * FlowAction
 * 
 * @author ZHUZHU
 * @version 2009-4-26
 * @see FlowAction
 * @since 1.0
 */
public class FlowAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private WorkFlowFacade workFlowFacade = null;

    private FlowManager flowManager = null;

    private FlowDefineDAO flowDefineDAO = null;

    private FlowVSTemplateDAO flowVSTemplateDAO = null;

    private FlowTokenDAO flowTokenDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private CommonDAO commonDAO = null;

    private TemplateFileDAO templateFileDAO = null;

    private static String QUERYFLOWDEFINE = "queryFlowDefine";

    private static String QUERYFLOWDEFINE2 = "queryFlowDefine2";

    /**
     * default constructor
     */
    public FlowAction()
    {
    }

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
    public ActionForward queryFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYFLOWDEFINE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWDEFINE, request, condtion, this.flowDefineDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * query realease flow define
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryFlowDefine2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("FlowDefineBean.status", "=", FlowConstant.FLOW_STATUS_REALSE);

        condtion.addIntCondition("FlowDefineBean.parentType", "=", FlowConstant.FLOW_PARENTTYPE_ROOT);

        ActionTools.processJSONQueryCondition(QUERYFLOWDEFINE2, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFLOWDEFINE2, request, condtion, this.flowDefineDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 增加流程定义
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse reponse)
        throws ServletException
    {
        FlowDefineBean bean = new FlowDefineBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            setTemplate(bean, request);

            workFlowFacade.addFlowDefine(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加流程:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        return mapping.findForward("queryFlowDefine");
    }

    /**
     * updateFlowDefine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse reponse)
        throws ServletException
    {
        FlowDefineBean bean = new FlowDefineBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            workFlowFacade.updateFlowDefine(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改流程定义:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改流程定义失败:" + e.getMessage());
        }

        return mapping.findForward("queryFlowDefine");
    }

    /**
     * delFlowDefine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            workFlowFacade.deleteFlowDefine(user.getId(), id);

            ajax.setSuccess("成功删除流程定义");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除流程定义失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * dropFlowDefine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward dropFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String force = request.getParameter("force");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            workFlowFacade.dropFlowDefine(user.getId(), id, "1".equals(force));

            ajax.setSuccess("成功废弃流程定义");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("废弃流程定义失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findFlowDefine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findFlowDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        FlowDefineBean bean = flowDefineDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("流程定义不存在", mapping, request);
        }

        request.setAttribute("bean", bean);

        request.setAttribute("subFlow", bean.getParentType() == FlowConstant.FLOW_PARENTTYPE_SUB);

        if ("1".equals(update))
        {
            return mapping.findForward("updateFlowDefine");
        }

        try
        {
            FlowDefineVO vo = flowManager.findFlowDefine(id);

            if (vo.getMode() == FlowConstant.FLOW_MODE_TEMPLATE)
            {
                List<FlowVSTemplateBean> vs = vo.getTemplates();

                List<TemplateFileBean> tList = new ArrayList<TemplateFileBean>();

                for (FlowVSTemplateBean flowVSTemplateBean : vs)
                {
                    tList.add(templateFileDAO.find(flowVSTemplateBean.getTemplateId()));
                }

                request.setAttribute("templateList", tList);
            }

            // FIX bug
            if (vo.getTokenVOs().size() > 0)
            {
                vo.getTokenVOs().remove(vo.getTokenVOs().size() - 1);
            }

            request.setAttribute("bean", vo);

            List<FlowViewerBean> viewList = flowViewerDAO.queryEntityBeansByFK(id);

            List<FlowViewerVO> viewVOList = new ArrayList();

            for (FlowViewerBean flowViewerBean : viewList)
            {
                viewVOList.add(flowManager.findFlowViewer(flowViewerBean.getId()));
            }

            request.setAttribute("viewList", viewVOList);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            return ActionTools.toError(e.toString(), mapping, request);
        }

        return mapping.findForward("detailFlowDefine");
    }

    /**
     * 给流程配置环节做准备
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForConfigToken(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String totalTokens = request.getParameter("totalTokens");

        FlowDefineBean bean = flowDefineDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("流程定义不存在", mapping, request);
        }

        if (bean.getStatus() != FlowConstant.FLOW_STATUS_INIT)
        {
            return ActionTools.toError("流程不在初始态,不能配置环节", "queryFlowDefine", mapping, request);
        }

        List<FlowVSTemplateBean> vs = flowVSTemplateDAO.queryEntityBeansByFK(id);

        List<TemplateFileBean> templates = new ArrayList<TemplateFileBean>();

        for (FlowVSTemplateBean flowVSTemplateBean : vs)
        {
            TemplateFileBean eacItem = templateFileDAO.find(flowVSTemplateBean.getTemplateId());

            if (eacItem != null)
            {
                templates.add(eacItem);
            }
        }

        int tokens = CommonTools.parseInt(totalTokens);

        tokens = tokens < 2 ? 2 : tokens;

        tokens = tokens > 99 ? 99 : tokens;

        request.setAttribute("bean", bean);

        request.setAttribute("subFlow", bean.getParentType() == FlowConstant.FLOW_PARENTTYPE_SUB);

        request.setAttribute("templates", templates);

        request.setAttribute("tokens", tokens);

        return mapping.findForward("configToken");
    }

    /**
     * preForConfigView
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForConfigView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        request.setAttribute("flowId", id);

        List<FlowViewerBean> views = flowViewerDAO.queryEntityBeansByFK(id);

        FlowViewerVO view = null;

        if ( !views.isEmpty())
        {
            try
            {
                view = flowManager.findFlowViewer(views.get(0).getId());
            }
            catch (MYException e)
            {
            }
        }

        request.setAttribute("view", view);

        return mapping.findForward("configView");
    }

    /**
     * configToken
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configToken(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException
    {
        FlowDefineBean bean = new FlowDefineBean();

        try
        {
            BeanUtil.getBean(bean, request);

            List<FlowVSTemplateBean> templates = flowVSTemplateDAO.queryEntityBeansByFK(bean.getId());

            // set template
            bean.setTemplates(templates);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            // NOTE zhuzhu set token
            setFlowToken(bean, request);

            workFlowFacade.configFlowToken(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功配置环节,同时提交:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "配置环节失败:" + e.getMessage());
        }

        return mapping.findForward("queryFlowDefine");
    }

    /**
     * configToken
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        FlowViewerBean bean = new FlowViewerBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            workFlowFacade.configFlowView(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功配置流程查阅");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "配置流程查阅失败:" + e.getMessage());
        }

        return mapping.findForward("queryFlowDefine");
    }

    /**
     * 设置模板
     * 
     * @param pbean
     * @param request
     */
    private void setTemplate(FlowDefineBean pbean, HttpServletRequest request)
    {
        String templateIds = request.getParameter("templateIds");

        if (StringTools.isNullOrNone(templateIds))
        {
            return;
        }

        String[] ids = templateIds.split(";");

        List<FlowVSTemplateBean> tepList = new ArrayList<FlowVSTemplateBean>();

        pbean.setTemplates(tepList);

        for (String string : ids)
        {
            if ( !StringTools.isNullOrNone(string))
            {
                FlowVSTemplateBean vs = new FlowVSTemplateBean();

                vs.setTemplateId(string);

                tepList.add(vs);
            }
        }
    }

    /**
     * setFlowToken
     * 
     * @param pbean
     * @param request
     */
    private void setFlowToken(FlowDefineBean pbean, HttpServletRequest request)
    {
        String[] providers = request.getParameterValues("check_init");

        List<FlowTokenBean> item = pbean.getTokens();

        List<FlowVSTemplateBean> templates = pbean.getTemplates();

        int orders = FlowConstant.FLOW_INSTANCE_BEGIN;

        for (int i = 0; i < providers.length; i++ )
        {
            if (StringTools.isNullOrNone(providers[i]))
            {
                continue;
            }

            FlowTokenBean tokenItem = new FlowTokenBean();

            createTokenBaseInfo(pbean, request, providers, orders, i, tokenItem);

            setBeginEnd(orders, tokenItem);

            // config REALTOKEN
            if (tokenItem.getType() == FlowConstant.TOKEN_TYPE_REALTOKEN)
            {
                // set handle
                setHandle(request, providers[i], tokenItem);

                // set TokenVSOperationBean
                setTemplateOperation(request, templates, i, tokenItem);

                // set operation
                setOperation(request, i, tokenItem);

                tokenItem.setPluginType(CommonTools.parseInt(request.getParameter("tokenType_" + providers[i])));

            }
            else
            {
                // config abstract token
                tokenItem.setSubFlowId(request.getParameter("tokenSubFlowId_" + providers[i]));

                tokenItem.setPluginType(FlowConstant.FLOW_PLUGIN_SUBFLOW);
            }

            orders++ ;

            item.add(tokenItem);
        }

        // 最后一个环节的下一个指向结束
        createLastToken(pbean, item, orders);
    }

    /**
     * createTokenBaseInfo
     * 
     * @param pbean
     * @param request
     * @param providers
     * @param orders
     * @param i
     * @param tokenItem
     */
    private void createTokenBaseInfo(FlowDefineBean pbean, HttpServletRequest request, String[] providers, int orders,
                                     int i, FlowTokenBean tokenItem)
    {
        tokenItem.setFlowId(pbean.getId());

        // set token type
        tokenItem.setType(CommonTools.parseInt(request.getParameter("tokenSelfType_" + providers[i])));

        tokenItem.setName(request.getParameter("tokenName_" + providers[i]));

        tokenItem.setPreOrders(orders - 1);

        tokenItem.setOrders(orders);

        tokenItem.setNextOrders(orders + 1);
    }

    /**
     * setBeginEnd
     * 
     * @param orders
     * @param tokenItem
     */
    private void setBeginEnd(int orders, FlowTokenBean tokenItem)
    {
        if (orders == FlowConstant.FLOW_INSTANCE_BEGIN)
        {
            tokenItem.setBegining(true);

            tokenItem.setEnding(false);
        }
        else
        {
            tokenItem.setBegining(false);

            tokenItem.setEnding(false);
        }
    }

    /**
     * setTemplateOperation
     * 
     * @param request
     * @param templates
     * @param i
     * @param tokenItem
     */
    private void setTemplateOperation(HttpServletRequest request, List<FlowVSTemplateBean> templates, int i,
                                      FlowTokenBean tokenItem)
    {
        for (FlowVSTemplateBean flowVSTemplateBean : templates)
        {
            TokenVSTemplateBean tvt = new TokenVSTemplateBean();

            tvt.setFlowId(tokenItem.getFlowId());

            tvt.setTemplateId(flowVSTemplateBean.getTemplateId());

            // t_v_${item}_${itemTemp.id}
            String id = "t_v_" + i + "_" + flowVSTemplateBean.getTemplateId();

            String ivalue = request.getParameter(id);

            if ("1".equals(ivalue))
            {
                tvt.setViewTemplate(FlowConstant.TEMPLATE_READONLY_YES);
            }
            else
            {
                tvt.setViewTemplate(FlowConstant.TEMPLATE_READONLY_NO);
            }

            id = "t_e_" + i + "_" + flowVSTemplateBean.getTemplateId();

            ivalue = request.getParameter(id);

            if ("1".equals(ivalue))
            {
                tvt.setEditTemplate(FlowConstant.TEMPLATE_EDIT_YES);
            }
            else
            {
                tvt.setEditTemplate(FlowConstant.TEMPLATE_EDIT_NO);
            }

            tokenItem.getTempaltes().add(tvt);
        }
    }

    /**
     * setHandle
     * 
     * @param request
     * @param provider
     * @param tokenItem
     * @return
     */
    private void setHandle(HttpServletRequest request, String provider, FlowTokenBean tokenItem)
    {
        // 从结构上支持多个,但是运行在暂时支持一个
        TokenVSHanderBean bean = new TokenVSHanderBean();

        tokenItem.getHandles().add(bean);

        bean.setType(CommonTools.parseInt(request.getParameter("tokenType_" + provider)));

        bean.setProcesser(request.getParameter("processerId_" + provider));

        bean.setFlowId(tokenItem.getFlowId());
    }

    /**
     * setOperation
     * 
     * @param request
     * @param provider
     * @param tokenItem
     * @return
     */
    private void setOperation(HttpServletRequest request, int i, FlowTokenBean tokenItem)
    {
        TokenVSOperationBean operation = new TokenVSOperationBean();

        operation.setFlowId(tokenItem.getFlowId());

        tokenItem.setOperation(operation);

        // pass is default operation
        operation.setPass(FlowConstant.OPERATION_YES);

        // operation_${item}_reject
        String id = "operation_" + i + "_reject";

        String ivalue = request.getParameter(id);

        if ("1".equals(ivalue))
        {
            operation.setReject(FlowConstant.OPERATION_YES);
        }
        else
        {
            operation.setReject(FlowConstant.OPERATION_NO);
        }

        id = "operation_" + i + "_rejectAll";

        ivalue = request.getParameter(id);

        if ("1".equals(ivalue))
        {
            operation.setRejectAll(FlowConstant.OPERATION_YES);
        }
        else
        {
            operation.setRejectAll(FlowConstant.OPERATION_NO);
        }

        id = "operation_" + i + "_exends";

        ivalue = request.getParameter(id);

        if ("1".equals(ivalue))
        {
            operation.setExends(FlowConstant.OPERATION_YES);
        }
        else
        {
            operation.setExends(FlowConstant.OPERATION_NO);
        }

        // set rejectParent
        id = "operation_" + i + "_rejectParent";

        ivalue = request.getParameter(id);

        if ("1".equals(ivalue))
        {
            operation.setRejectParent(FlowConstant.OPERATION_YES);
        }
        else
        {
            operation.setRejectParent(FlowConstant.OPERATION_NO);
        }

        // when i == 0,the liminal will be invalid
        if (i != 0)
        {
            // set liminal
            id = "liminal_" + i;

            ivalue = request.getParameter(id);

            operation.setLiminal(CommonTools.parseFloat(ivalue));
        }
    }

    /**
     * createLastToken
     * 
     * @param item
     * @param orders
     */
    private void createLastToken(FlowDefineBean pbean, List<FlowTokenBean> item, int orders)
    {
        item.get(item.size() - 1).setNextOrders(FlowConstant.FLOW_INSTANCE_END);

        FlowTokenBean bean = new FlowTokenBean();

        bean.setFlowId(pbean.getId());

        bean.setName("结束环节");

        bean.setPreOrders(orders - 1);

        bean.setOrders(FlowConstant.FLOW_INSTANCE_END);

        bean.setPluginType(FlowConstant.FLOW_PLUGIN_END);

        bean.setNextOrders(FlowConstant.FLOW_INSTANCE_END + 1);

        bean.setBegining(false);

        bean.setEnding(true);

        item.add(bean);
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
}
