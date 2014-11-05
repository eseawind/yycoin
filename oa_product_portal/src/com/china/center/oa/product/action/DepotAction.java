/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;


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
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * DepotAction
 * 
 * @author ZHUZHU
 * @version 2010-6-28
 * @see DepotAction
 * @since 1.0
 */
public class DepotAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductFacade productFacade = null;

    private DepotDAO depotDAO = null;

    private OrgManager orgManager = null;

    private static final String QUERYDEPOT = "queryDepot";

    /**
     * default constructor
     */
    public DepotAction()
    {
    }

    public ActionForward queryDepot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYDEPOT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYDEPOT, request, condtion, this.depotDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addDepot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        DepotBean bean = new DepotBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.addDepotBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加仓库:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDepot");
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
    public ActionForward updateDepot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException
    {
        DepotBean bean = new DepotBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.updateDepotBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作仓库:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDepot");
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
    public ActionForward deleteDepot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.deleteDepotBean(user.getId(), id);

            ajax.setSuccess("成功删除仓库");
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
    public ActionForward findDepot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        DepotBean bean = depotDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryDepot");
        }

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            return mapping.findForward("updateDepot");
        }

        return mapping.findForward("detailDepot");
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

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

}
