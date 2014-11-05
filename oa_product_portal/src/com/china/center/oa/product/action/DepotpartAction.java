/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;


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
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.publics.Helper;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * DepotAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DepotpartAction
 * @since 1.0
 */
public class DepotpartAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductFacade productFacade = null;

    private DepotpartDAO depotpartDAO = null;

    private DepotDAO depotDAO = null;

    private static final String QUERYDEPOTPART = "queryDepotpart";

    /**
     * default constructor
     */
    public DepotpartAction()
    {
    }

    public ActionForward queryDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYDEPOTPART, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYDEPOTPART, request, condtion, this.depotpartDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addDepotpart
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response)
        throws ServletException
    {
        DepotpartBean bean = new DepotpartBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.addDepotpartBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加仓区:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDepotpart");
    }

    /**
     * updateDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        DepotpartBean bean = new DepotpartBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.updateDepotpartBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作仓区:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDepotpart");
    }

    /**
     * preForAddDepotpart
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        List<DepotBean> depotList = depotDAO.queryCommonDepotBean();

        request.setAttribute("depotList", depotList);

        return mapping.findForward("addDepotpart");
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
    public ActionForward deleteDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.deleteDepotpartBean(user.getId(), id);

            ajax.setSuccess("成功删除仓区");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        DepotpartBean bean = depotpartDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryDepot");
        }

        List<DepotBean> depotList = depotDAO.queryCommonDepotBean();

        request.setAttribute("depotList", depotList);

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            return mapping.findForward("updateDepotpart");
        }

        return mapping.findForward("detailDepotpart");
    }

    /**
     * rptQueryDepotpart
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryDepotpart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String depotId = request.getParameter("depotId");

        List<DepotpartBean> list = depotpartDAO.queryEntityBeansByFK(depotId);

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryDepotpart");
    }

    /**
     * @return the productFacade
     */
    public ProductFacade getProductFacade()
    {
        return productFacade;
    }

    /**
     * @param productFacade
     *            the productFacade to set
     */
    public void setProductFacade(ProductFacade productFacade)
    {
        this.productFacade = productFacade;
    }

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }
}
