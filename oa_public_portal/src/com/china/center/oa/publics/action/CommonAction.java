/**
 * File Name: CommonAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.PostDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * CommonAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see CommonAction
 * @since 1.0
 */
public class CommonAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PostDAO postDAO = null;

    private DepartmentDAO departmentDAO = null;

    private PublicFacade publicFacade = null;

    private static String QUERYPOST = "queryPost";

    private static String QUERYDEPARTMENT = "queryDepartment";

    /**
     * default constructor
     */
    public CommonAction()
    {}

    /**
     * listPost
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPOST, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPOST, request, condtion, this.postDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * listDepartment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYDEPARTMENT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYDEPARTMENT, request, condtion, this.departmentDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addOrUpdatePost
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdatePost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        PostBean bean = new PostBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            if (StringTools.isNullOrNone(bean.getId()))
            {
                publicFacade.addPostBean(user.getId(), bean);
            }
            else
            {
                publicFacade.updatePostBean(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功操作职务:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作职务失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryPost");
    }

    /**
     * addOrUpdateDepartment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        DepartmentBean bean = new DepartmentBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            if (StringTools.isNullOrNone(bean.getId()))
            {
                publicFacade.addDepartmentBean(user.getId(), bean);
            }
            else
            {
                publicFacade.updateDepartmentBean(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功操作部门:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作职务部门:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDepartment");
    }

    /**
     * findPost
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        try
        {
            PostBean post = postDAO.find(id);

            if (post == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "职务不存在");

                return mapping.findForward("queryPost");
            }

            request.setAttribute("bean", post);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward("queryRole");
        }

        return mapping.findForward("updatePost");
    }

    /**
     * findDepartment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        try
        {
            DepartmentBean bean = departmentDAO.find(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "部门不存在");

                return mapping.findForward("queryDepartment");
            }

            request.setAttribute("bean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward("queryDepartment");
        }

        return mapping.findForward("updateDepartment");
    }

    /**
     * delPost
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.delPostBean(user.getId(), id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delDepartment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delDepartment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.delDepartmentBean(user.getId(), id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
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

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
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
}
