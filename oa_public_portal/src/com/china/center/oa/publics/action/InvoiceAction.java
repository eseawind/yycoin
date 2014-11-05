/**
 * File Name: LocationAction.java<br>
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
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * InvoiceAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see InvoiceAction
 * @since 1.0
 */
public class InvoiceAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PublicFacade publicFacade = null;

    private InvoiceDAO invoiceDAO = null;

    private static final String QUERYINVOICE = "queryInvoice";

    /**
     * default constructor
     */
    public InvoiceAction()
    {
    }

    /**
     * queryInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYINVOICE, request, condtion);

        String jsonstr = ActionTools.queryBeanByJSONAndToString(QUERYINVOICE, request, condtion, this.invoiceDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        InvoiceBean bean = new InvoiceBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.updateInvoiceBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加发票实体:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryInvoice");
    }

    /**
     * updateInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException
    {
        InvoiceBean bean = new InvoiceBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.updateInvoiceBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作发票实体:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryInvoice");
    }

    /**
     * findInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        InvoiceBean bean = invoiceDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryInvoice");
        }

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            return mapping.findForward("updateInvoice");
        }

        return mapping.findForward("detailInvoice");
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
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO()
    {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO
     *            the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO)
    {
        this.invoiceDAO = invoiceDAO;
    }
}
