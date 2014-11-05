/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * ShowAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see ShowAction
 * @since 1.0
 */
public class ShowAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PublicFacade publicFacade = null;

    private ShowDAO showDAO = null;

    private DutyDAO dutyDAO = null;

    private static final String QUERYSHOW = "queryShow";

    /**
     * default constructor
     */
    public ShowAction()
    {
    }

    public ActionForward preForAddShow(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        request.setAttribute("dutyList", dutyList);

        return mapping.findForward("addShow");
    }

    public ActionForward queryShow(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSHOW, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSHOW, request, condtion,
            this.showDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addShow
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addShow(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ShowBean bean = new ShowBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.addShowBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加品名:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryShow");
    }

    /**
     * updateShow
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateShow(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ShowBean bean = new ShowBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.updateShowBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作品名:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryShow");
    }

    /**
     * delLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteShow(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.deleteShowBean(user.getId(), id);

            ajax.setSuccess("成功删除品名");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findShow
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findShow(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        ShowBean bean = showDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryShow");
        }

        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        request.setAttribute("dutyList", dutyList);

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            return mapping.findForward("updateShow");
        }

        return mapping.findForward("detailShow");
    }

    /**
     * @return the publicFacade
     */
    public PublicFacade getPublicFacade()
    {
        return publicFacade;
    }

    /**
     * @param publicFacade
     *            the publicFacade to set
     */
    public void setPublicFacade(PublicFacade publicFacade)
    {
        this.publicFacade = publicFacade;
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
}
