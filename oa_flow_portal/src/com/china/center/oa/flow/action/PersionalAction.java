/**
 * File Name: PersionalAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.action;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.flow.dao.FlowBelongDAO;
import com.china.center.oa.flow.vo.FlowBelongVO;
import com.china.center.oa.gm.constant.MailConstant;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.tools.TimeTools;


/**
 * PersionalAction
 * 
 * @author ZHUZHU
 * @version 2009-6-10
 * @see PersionalAction
 * @since 1.0
 */
public class PersionalAction extends DispatchAction
{
    private MailDAO mailDAO = null;

    private FlowBelongDAO flowBelongDAO = null;

    /**
     * default constructor
     */
    public PersionalAction()
    {
    }

    /**
     * queryPersionalDeskTop
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPersionalDeskTop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        handleMail(request, user);

        handleFlow(request, user);

        return mapping.findForward("desktop");
    }

    /**
     * handleFlow
     * 
     * @param request
     * @param user
     */
    private void handleFlow(HttpServletRequest request, User user)
    {
        final ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        condtition.addCondition("FlowBelongBean.stafferId", "=", user.getStafferId());

        condtition.addCondition("order by FlowBelongBean.logTime asc");

        List<FlowBelongVO> flowList = flowBelongDAO.queryEntityVOsByLimit(condtition, 5);

        request.setAttribute("flowList", flowList);
    }

    /**
     * handleMail
     * 
     * @param request
     */
    private void handleMail(HttpServletRequest request, User user)
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("MailBean.reveiveId", "=", user.getStafferId());

        condtion.addIntCondition("MailBean.status", "=", MailConstant.STATUS_INIT);

        // the nearest five days
        condtion.addCondition("MailBean.logTime", ">=", TimeTools.now( -5));

        condtion.addCondition("order by MailBean.logTime desc");

        List<MailBean> mailList = mailDAO.queryEntityBeansByLimit(condtion, 5);

        request.setAttribute("mailList", mailList);
    }

    public MailDAO getMailDAO()
    {
        return mailDAO;
    }

    public void setMailDAO(MailDAO mailDAO)
    {
        this.mailDAO = mailDAO;
    }

    public FlowBelongDAO getFlowBelongDAO()
    {
        return flowBelongDAO;
    }

    public void setFlowBelongDAO(FlowBelongDAO flowBelongDAO)
    {
        this.flowBelongDAO = flowBelongDAO;
    }
}
