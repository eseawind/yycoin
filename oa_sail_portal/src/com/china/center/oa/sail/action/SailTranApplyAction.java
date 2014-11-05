/**
 * File Name: SailTranApplyAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.action;


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
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.SailTranApplyDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.vo.SailTranApplyView;
import com.china.center.tools.CommonTools;


/**
 * SailTranApplyAction
 * 
 * @author ZHUZHU
 * @version 2012-5-6
 * @see SailTranApplyAction
 * @since 3.0
 */
public class SailTranApplyAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private SailTranApplyDAO sailTranApplyDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private OutManager outManager = null;

    private static final String QUERYSELFSAILTRANAPPLY = "querySelfSailTranApply";

    private static final String QUERYAPPROVESAILTRANAPPLY = "queryApproveSailTranApply";

    /**
     * default constructor
     */
    public SailTranApplyAction()
    {
    }

    /**
     * querySailTranApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfSailTranApply(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("SailTranApplyBean.stafferId", "=", Helper
            .getUser(request)
            .getStafferId());

        ActionTools.processJSONQueryCondition(QUERYSELFSAILTRANAPPLY, request, condtion);

        condtion.addCondition("order by SailTranApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFSAILTRANAPPLY, request,
            condtion, this.sailTranApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryApproveSailTranApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApproveSailTranApply(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion
            .addIntCondition("SailTranApplyBean.status", "=", SailConstant.SAILTRANAPPLY_SUBMIT);

        ActionTools.processJSONQueryCondition(QUERYAPPROVESAILTRANAPPLY, request, condtion);

        condtion.addCondition("order by SailTranApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPROVESAILTRANAPPLY, request,
            condtion, this.sailTranApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * findSailTranApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findSailTranApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        SailTranApplyView bean = sailTranApplyDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);

        List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

        request.setAttribute("loglist", loglist);

        return mapping.findForward("detailSailTranApply");
    }

    /**
     * passApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passApply(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            outManager.passTranApply(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * rejectApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectApply(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            String reason = request.getParameter("reason");

            User user = Helper.getUser(request);

            outManager.rejectTranApply(user, id, reason);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * @return the sailTranApplyDAO
     */
    public SailTranApplyDAO getSailTranApplyDAO()
    {
        return sailTranApplyDAO;
    }

    /**
     * @param sailTranApplyDAO
     *            the sailTranApplyDAO to set
     */
    public void setSailTranApplyDAO(SailTranApplyDAO sailTranApplyDAO)
    {
        this.sailTranApplyDAO = sailTranApplyDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

    /**
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
    }
}
