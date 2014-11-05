/**
 * File Name: TaxAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.action;


import java.io.IOException;
import java.io.OutputStream;
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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.bean.TaxTypeBean;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.dao.TaxTypeDAO;
import com.china.center.oa.tax.facade.TaxFacade;
import com.china.center.oa.tax.manager.TaxManager;
import com.china.center.oa.tax.vo.TaxVO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;


/**
 * TaxAction
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see TaxAction
 * @since 1.0
 */
public class TaxAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private TaxDAO taxDAO = null;

    private TaxTypeDAO taxTypeDAO = null;

    private BankDAO bankDAO = null;

    private TaxFacade taxFacade = null;

    private TaxManager taxManager = null;

    private static final String QUERYTAX = "queryTax";

    private static final String RPTQUERYTAX = "rptQueryTax";

    /**
     * default constructor
     */
    public TaxAction()
    {
    }

    /**
     * queryTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYTAX, request, condtion);

        condtion.addCondition("order by TaxBean.code asc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYTAX, request, condtion, this.taxDAO,
            new HandleResult<TaxVO>()
            {
                public void handle(TaxVO obj)
                {
                    taxManager.findVO(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * rptQueryTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<TaxVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setCondition(request, condtion);

            int total = taxDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYTAX);

            list = taxDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYTAX);

            list = taxDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request, RPTQUERYTAX),
                PageSeparateTools.getPageSeparate(request, RPTQUERYTAX));
        }

        request.setAttribute("beanList", list);

        request.setAttribute("rptMethod", RPTQUERYTAX);

        return mapping.findForward("rptQueryTax");
    }

    private void setCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        // 父节点
        String pid = request.getParameter("pid");

        String code = request.getParameter("code");

        String bottomFlag = request.getParameter("bottomFlag");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("TaxBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("TaxBean.code", "like", code);
        }

        if ( !StringTools.isNullOrNone(bottomFlag))
        {
            condtion.addIntCondition("TaxBean.bottomFlag", "=", bottomFlag);
        }

        if ( !StringTools.isNullOrNone(pid))
        {
            TaxBean parent = taxDAO.find(pid);

            condtion.addCondition("TaxBean.parentId" + parent.getLevel(), "=", pid);
        }
    }

    public ActionForward preForAddTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response)
        throws ServletException
    {
        List<TaxTypeBean> taxTypeList = taxTypeDAO.listEntityBeans();

        request.setAttribute("taxTypeList", taxTypeList);

        List<BankBean> bankList = bankDAO.listEntityBeansByOrder("order by name");

        request.setAttribute("bankList", bankList);

        return mapping.findForward("addTax");
    }

    /**
     * addTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException
    {
        TaxBean bean = new TaxBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            taxFacade.addTaxBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryTax");
    }

    /**
     * findTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        TaxVO bean = taxDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        taxManager.findVO(bean);

        request.setAttribute("bean", bean);

        if ("1".equals(update))
        {
            return mapping.findForward("updateTax");
        }

        List<TaxTypeBean> taxTypeList = taxTypeDAO.listEntityBeans();

        request.setAttribute("taxTypeList", taxTypeList);

        List<BankBean> bankList = bankDAO.listEntityBeansByOrder("order by name");

        request.setAttribute("bankList", bankList);

        return mapping.findForward("detailTax");
    }

    /**
     * updateTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        TaxBean bean = new TaxBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            taxFacade.updateTaxBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryTax");
    }

    /**
     * deleteTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            taxFacade.deleteTaxBean(user.getId(), id);

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
     * exportTax
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "Tax_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("编码,名称,分类,借贷,类型,级别,辅助核算");

            List<TaxVO> voList = this.taxDAO.listEntityVOsByOrder("order by id");

            for (TaxVO each : voList)
            {
                WriteFileBuffer line = new WriteFileBuffer(write);

                line.writeColumn("[" + each.getCode() + "]");
                line.writeColumn(each.getName());
                line.writeColumn(each.getPtypeName());
                line.writeColumn(ElTools.get("taxForward", each.getForward()));
                line.writeColumn(ElTools.get("taxType", each.getType()));
                line.writeColumn(ElTools.get("taxBottomFlag", each.getBottomFlag()));
                line.writeColumn(each.getOther());

                line.writeLine();
            }
            write.close();

        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            return null;
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                }
            }

            if (write != null)
            {

                try
                {
                    write.close();
                }
                catch (IOException e1)
                {
                }
            }

        }

        return null;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
    }

    /**
     * @return the taxFacade
     */
    public TaxFacade getTaxFacade()
    {
        return taxFacade;
    }

    /**
     * @param taxFacade
     *            the taxFacade to set
     */
    public void setTaxFacade(TaxFacade taxFacade)
    {
        this.taxFacade = taxFacade;
    }

    /**
     * @return the taxTypeDAO
     */
    public TaxTypeDAO getTaxTypeDAO()
    {
        return taxTypeDAO;
    }

    /**
     * @param taxTypeDAO
     *            the taxTypeDAO to set
     */
    public void setTaxTypeDAO(TaxTypeDAO taxTypeDAO)
    {
        this.taxTypeDAO = taxTypeDAO;
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
    }

    /**
     * @return the taxManager
     */
    public TaxManager getTaxManager()
    {
        return taxManager;
    }

    /**
     * @param taxManager
     *            the taxManager to set
     */
    public void setTaxManager(TaxManager taxManager)
    {
        this.taxManager = taxManager;
    }
}
