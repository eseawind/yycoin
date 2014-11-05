/**
 * File Name: CustomerCheckAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.action;


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
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;
import com.china.center.oa.customer.dao.CustomerCheckDAO;
import com.china.center.oa.customer.dao.CustomerCheckItemDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.CommonConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerCheckAction
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCheckAction
 * @since 1.0
 */
public class CustomerCheckAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ClientFacade clientFacade = null;

    private CustomerCheckDAO customerCheckDAO = null;

    private CustomerCheckItemDAO customerCheckItemDAO = null;

    private UserManager userManager = null;

    private StafferDAO stafferDAO = null;

    private static String QUERYCUSTOMERCHECK = "queryCustomerCheck";

    private static String QUERYCHECKITEM = "queryCheckItem";

    /**
     * default constructor
     */
    public CustomerCheckAction()
    {
    }

    /**
     * 查询客户检查(不分区域)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCustomerCheck(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK_CHECK))
        {
            condtion.addCondition("CustomerCheckBean.applyerId", "=", user.getStafferId());
        }

        ActionTools.processJSONQueryCondition(QUERYCUSTOMERCHECK, request, condtion);

        condtion.addCondition("order by CustomerCheckBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCUSTOMERCHECK, request,
            condtion, this.customerCheckDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCheckItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckItem(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String look = request.getParameter("look");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 是申请人看
        if (StringTools.isNullOrNone(look))
        {
            CustomerCheckBean bean = customerCheckDAO.find(id);

            if (bean == null)
            {
                return JSONTools.writeErrorResponse(response, "数据不完备");
            }

            if ( !bean.getApplyerId().equals(user.getStafferId()))
            {
                return JSONTools.writeErrorResponse(response, "没有权限操作");
            }

            if (bean.getStatus() != CommonConstant.STATUS_PASS)
            {
                return JSONTools.writeErrorResponse(response, "没有权限操作");
            }

            if (bean.getBeginTime().compareTo(TimeTools.now()) > 0
                || bean.getEndTime().compareTo(TimeTools.now()) < 0)
            {
                // responseText
                return JSONTools.writeErrorResponse(response, "超过指定的时间,无法操作");
            }

            condtion.addCondition("CustomerCheckItemBean.parentId", "=", id);
        }
        else
        {
            // 只能看到有结果的(过滤自己的)
            if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK_ALLQUERY))
            {
                condtion.addCondition("CustomerCheckItemBean.stafferId", "=", user.getStafferId());
            }

            condtion
                .addIntCondition("CustomerCheckItemBean.status", "=", CommonConstant.STATUS_END);
        }

        ActionTools.processJSONQueryCondition(QUERYCHECKITEM + look, request, condtion);

        condtion.addCondition("order by CustomerCheckItemBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKITEM + look, request,
            condtion, this.customerCheckItemDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddCheck(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        List<StafferBean> list = stafferDAO.listEntityBeans();

        request.setAttribute("list", list);

        return mapping.findForward("addCheckCustomer");
    }

    /**
     * addCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addCheck(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CustomerCheckBean bean = new CustomerCheckBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setApplyerId(user.getStafferId());

            bean.setLocationId(user.getLocationId());

            bean.setLogTime(TimeTools.now());

            clientFacade.addCheckBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryCheckCustomer");
    }

    /**
     * goonCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward goonCheck(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        CustomerCheckBean bean = customerCheckDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

            return mapping.findForward("queryCheckCustomer");
        }

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setApplyerId(user.getStafferId());

            bean.setLocationId(user.getLocationId());

            bean.setLogTime(TimeTools.now());

            clientFacade.goonBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryCheckCustomer");
    }

    /**
     * findCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCheck(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        CustomerCheckBean bean = customerCheckDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

            return mapping.findForward("queryCheckCustomer");
        }

        List<StafferBean> list = stafferDAO.listEntityBeans();

        request.setAttribute("list", list);

        request.setAttribute("bean", bean);

        if ("1".equals(update))
        {
            return mapping.findForward("updateCheckCustomer");
        }

        return mapping.findForward("detailCheckCustomer");
    }

    /**
     * delCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delCheck(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientFacade.delCheckBean(user.getId(), id);

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
     * passCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passCheck(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientFacade.passCheckBean(user.getId(), id);

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
     * passItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passItem(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            CustomerCheckItemBean bean = customerCheckItemDAO.find(id);

            if (bean == null)
            {
                ajax.setError("数据不完备");

                return JSONTools.writeResponse(response, ajax);
            }

            bean.setRet(CommonConstant.RESULT_OK);

            User user = Helper.getUser(request);

            clientFacade.updateCheckItem(user.getId(), bean);

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
     * rejectItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectItem(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            CustomerCheckItemBean bean = customerCheckItemDAO.find(id);

            if (bean == null)
            {
                ajax.setError("数据不完备");

                return JSONTools.writeResponse(response, ajax);
            }

            bean.setRet(CommonConstant.RESULT_ERROR);

            bean.setDescription(reason);

            User user = Helper.getUser(request);

            clientFacade.updateCheckItem(user.getId(), bean);

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
     * rejectCheck
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectCheck(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientFacade.rejectCheckBean(user.getId(), id);

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
     * @return the customerCheckDAO
     */
    public CustomerCheckDAO getCustomerCheckDAO()
    {
        return customerCheckDAO;
    }

    /**
     * @param customerCheckDAO
     *            the customerCheckDAO to set
     */
    public void setCustomerCheckDAO(CustomerCheckDAO customerCheckDAO)
    {
        this.customerCheckDAO = customerCheckDAO;
    }

    /**
     * @return the customerCheckItemDAO
     */
    public CustomerCheckItemDAO getCustomerCheckItemDAO()
    {
        return customerCheckItemDAO;
    }

    /**
     * @param customerCheckItemDAO
     *            the customerCheckItemDAO to set
     */
    public void setCustomerCheckItemDAO(CustomerCheckItemDAO customerCheckItemDAO)
    {
        this.customerCheckItemDAO = customerCheckItemDAO;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
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
}
