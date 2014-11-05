package com.china.center.oa.sail.action;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.customervssail.dao.OutQueryDAO;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vo.OutBillVO;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.PublicLock;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.DutyVSInvoiceDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceCreditDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.LockHelper;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.AuthManager;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.FatalNotify;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.publics.vo.InvoiceCreditVO;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.AppOutVSOutBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BaseRepaireBean;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutRepaireBean;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.StatsDeliveryRankBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.constanst.PromotionConstant;
import com.china.center.oa.sail.dao.AppOutDAO;
import com.china.center.oa.sail.dao.AppOutVSOutDAO;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BaseRepaireDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutRepaireDAO;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.PromotionItemDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.dao.StatsDeliveryRankDAO;
import com.china.center.oa.sail.helper.FlowLogHelper;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailManager;
import com.china.center.oa.sail.vo.AppOutVO;
import com.china.center.oa.sail.vo.BaseVO;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.sail.vo.OutBalanceVO;
import com.china.center.oa.sail.vo.OutRepaireVO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.SailConfigVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;
import com.china.center.oa.sail.wrap.CreditWrap;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

import edu.emory.mathcs.backport.java.util.Collections;


/**
 * 增加出库单
 * 
 * @author ZHUZHU
 * @version 2007-4-1
 * @see
 * @since
 */
public class OutAction extends ParentOutAction
{
	/**
     * rejectBack
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rejectBack(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        String queryType = request.getParameter("queryType");

        User user = (User)request.getSession().getAttribute("user");

        if (StringTools.isNullOrNone(fullId))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

            return mapping.findForward("error");
        }

        OutBean bean = outDAO.find(fullId);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

            return mapping.findForward("error");
        }

        if (bean.getStatus() == OutConstant.STATUS_SAVE
            || bean.getStatus() == OutConstant.STATUS_REJECT
            || bean.getStatus() == OutConstant.BUY_STATUS_SUBMIT)
        {
            try
            {
            	sendOutRejectMail(fullId, user, "无", bean,"退库单驳回");
            	
                outManager.delOut(user, fullId);
                
                request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + fullId);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "流程异常，请重新操作:" + e.toString());

                return mapping.findForward("error");
            }
        }
        else
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "只有保存态的库单才可以驳回");

            return mapping.findForward("error");
        }

        CommonTools.removeParamers(request);

        RequestTools.menuInitQuery(request);

        request.setAttribute("queryType", queryType);

        return queryBuy(mapping, form, request, reponse);
    }

    /**
     * 处理调出的库单(入库单的处理)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward processInvoke(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        String flag = request.getParameter("flag");

        String depotpartId = request.getParameter("depotpartId");

        User user = (User)request.getSession().getAttribute("user");

        if (StringTools.isNullOrNone(fullId))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

            return mapping.findForward("error");
        }

        OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

            return mapping.findForward("error");
        }

        if ( ! ((outBean.getType() == OutConstant.OUT_TYPE_INBILL && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
        		||outBean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不能转调，请核实");

            return mapping.findForward("error");
        }

        if (outBean.getInway() != OutConstant.IN_WAY)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不在在途中，不能处理");

            return mapping.findForward("error");
        }

        // 直接接受自动生成一个调入的库单
        if ("1".equals(flag))
        {
            OutBean newOut = new OutBean(outBean);

            newOut.setStatus(0);

            newOut.setLocationId(user.getLocationId());

            // 仓库就是调出的目的仓区
            newOut.setLocation(outBean.getDestinationId());

            newOut.setOutType(OutConstant.OUTTYPE_IN_MOVEOUT);

            newOut.setFullId("");

            newOut.setRefOutFullId(fullId);

            newOut.setDestinationId(outBean.getLocation());

            newOut.setDescription("自动接收调拨单:" + fullId + ".生成的调入单据");

            newOut.setInway(OutConstant.IN_WAY_NO);

            newOut.setChecks("");

            // 调入的单据
            newOut.setReserve1(OutConstant.MOVEOUT_IN);

            newOut.setPay(OutConstant.PAY_NOT);

            newOut.setTotal( -newOut.getTotal());

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

            if (StringTools.isNullOrNone(depotpartId))
            {
                DepotpartBean defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(outBean
                    .getDestinationId());

                if (defaultOKDepotpart == null)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓，请核实");

                    return mapping.findForward("error");
                }

                depotpartId = defaultOKDepotpart.getId();
            }

            DepotpartBean depotpart = depotpartDAO.find(depotpartId);

            if (depotpart == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓，请核实");

                return mapping.findForward("error");
            }

            for (BaseBean baseBean : baseList)
            {
                // 获得仓库默认的仓区
                baseBean.setDepotpartId(depotpartId);
                baseBean.setValue( -baseBean.getValue());
                baseBean.setLocationId(outBean.getDestinationId());
                baseBean.setAmount( -baseBean.getAmount());
                baseBean.setDepotpartName(depotpart.getName());
            }

            List<BaseBean> lastList = OutHelper.trimBaseList(baseList);

            newOut.setBaseList(lastList);

            try
            {
                String ful = outManager.coloneOutAndSubmitAffair(newOut, user,
                    StorageConstant.OPR_STORAGE_REDEPLOY);

                request.setAttribute(KeyConstant.MESSAGE, fullId + "成功自动接收:" + ful);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不能自动接收，请核实:"
                                                                + e.getErrorContent());

                return mapping.findForward("error");
            }

        }

        // 转调处理
        if ("2".equals(flag))
        {
            String changeLocationId = request.getParameter("changeLocationId");

            if (outBean.getLocation().equals(changeLocationId))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "转调区域不能是产品调出区域，请核实");

                return mapping.findForward("error");
            }

            outBean.setDestinationId(changeLocationId);

            try
            {
                outManager.updateOut(outBean);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request
                    .setAttribute(KeyConstant.ERROR_MESSAGE, "库单不能转调，请核实:" + e.getErrorContent());

                return mapping.findForward("error");
            }

            request.setAttribute(KeyConstant.MESSAGE, fullId + "成功转调");
        }

        // 直接驳回
        if ("3".equals(flag))
        {
            try
            {
                outManager.reject(fullId, user, "调出驳回");

                request.setAttribute(KeyConstant.MESSAGE, fullId + "成功驳回:" + fullId);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                return mapping.findForward("error");
            }
        }

        request.setAttribute("forward", "10");

        request.setAttribute("queryType", "4");

        return queryBuy(mapping, form, request, reponse);
    }

    /**
     * querySelfCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfCredit(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StafferBean staffer = Helper.getStaffer(request);

        List<CreditWrap> allWrap = outDAO.queryAllNoPay(staffer.getId(), staffer.getIndustryId(),
            YYTools.getStatBeginDate(), YYTools.getStatEndDate());

        for (CreditWrap creditWrap : allWrap)
        {
            PrincipalshipBean pri = principalshipDAO.find(creditWrap.getIndustryId());

            if (pri != null)
            {
                creditWrap.setIndustryId(pri.getName());
            }

            StafferBean sb = stafferDAO.find(creditWrap.getStafferId());

            if (sb != null)
            {
                creditWrap.setStafferId(sb.getName());
            }
        }

        String jsonstr = JSONTools.getJSONString(allWrap);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * findCreditDetail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCreditDetail(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        StafferBean staffer = Helper.getStaffer(request);

        // 自己使用
        double st = outDAO.sumNoPayAndAvouchBusinessByStafferId(staffer.getId(), staffer
            .getIndustryId(), YYTools.getStatBeginDate(), YYTools.getStatEndDate());

        // 担保
        double mt = outDAO.sumNoPayAndAvouchBusinessByManagerId2(staffer.getId(), YYTools
            .getStatBeginDate(), YYTools.getStatEndDate());

        // 被担保
        double bei = outDAO.sumNoPayAndAvouchBusinessByManagerId3(staffer.getId(), YYTools
            .getStatBeginDate(), YYTools.getStatEndDate());

        double total = staffer.getCredit() * staffer.getLever();

        StringBuffer buffer = new StringBuffer();

        List<InvoiceCreditVO> vsList = invoiceCreditDAO.queryEntityVOsByFK(staffer.getId());

        for (InvoiceCreditVO invoiceCreditVO : vsList)
        {
            buffer.append(invoiceCreditVO.getInvoiceName()).append("下的信用额度:").append(
                MathTools.formatNum(invoiceCreditVO.getCredit() * staffer.getLever())).append(
                "<br>");
        }

        String msg0 = "总信用额度:" + MathTools.formatNum(total) + "<br>";
        String msg00 = "原始信用:" + MathTools.formatNum(staffer.getCredit()) + "<br>";
        String msg01 = "信用杠杆:" + staffer.getLever() + "<br>";
        String msg1 = "开单使用额度:" + MathTools.formatNum(st) + "<br>";
        String msg2 = "担保使用额度:" + MathTools.formatNum(mt) + "<br>";
        String msg22 = "被担保额度:" + MathTools.formatNum(bei) + "<br>";
        String msg3 = "剩余额度:" + MathTools.formatNum(total - st - mt);

        ajax.setSuccess(msg0 + msg00 + msg01 + buffer + msg1 + msg2 + msg22 + msg3);

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * mark(标记单据)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward mark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        OutBean bean = outDAO.find(fullId);

        outManager.mark(fullId, !bean.isMark());

        request.setAttribute("forward", "1");

        return querySelfOut(mapping, form, request, reponse);
    }

    /**
     * 通过委托结算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward passOutBalance(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            outManager.passOutBalance(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());
        }

        RequestTools.actionInitQuery(request);

        request.setAttribute("queryType", "2");

        return queryOutBalance(mapping, form, request, reponse);
    }

    /**
     * passOutBalanceToDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward passOutBalanceToDepot(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = (User)request.getSession().getAttribute("user");

        if ( !userManager.containAuth(user.getId(), AuthConstant.BUY_SUBMIT))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限操作");

            return mapping.findForward("error");
        }

        // LOCK 委托代销结算单通过(退库)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            try
            {
                outManager.passOutBalanceToDepot(user, id);

                request.setAttribute(KeyConstant.MESSAGE, "成功操作");
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());
            }
        }

        RequestTools.actionInitQuery(request);

        request.setAttribute("queryType", "3");

        return queryOutBalance(mapping, form, request, reponse);
    }

    /**
     * 驳回委托结算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rejectOutBalance(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            outManager.rejectOutBalance(user, id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());
        }

        RequestTools.actionInitQuery(request);

        return queryOutBalance(mapping, form, request, reponse);
    }

    /**
     * 删除委托结算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward deleteOutBalance(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            outManager.deleteOutBalance(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());
        }

        RequestTools.actionInitQuery(request);

        return queryOutBalance(mapping, form, request, reponse);
    }

    /**
     * 总部核对(结束销售单)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward checks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse reponse)
        throws ServletException
    {
        String formDetail = request.getParameter("formDetail");

        String fullId = request.getParameter("outId");

        OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误,请确认操作");

            return mapping.findForward("error");
        }

        if ( !OutHelper.isSailEnd(outBean))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误,请确认操作");

            return mapping.findForward("error");
        }

        String checks = request.getParameter("reason");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            outManager.check(fullId, user, checks);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功核对单据:" + fullId);

        if ("1".equals(formDetail))
        {
            request.setAttribute("holdCondition", formDetail);

            request.setAttribute("forward", "1");
        }

        CommonTools.saveParamers(request);

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            return queryOut(mapping, form, request, reponse);
        }
        else
        {
            return queryBuy(mapping, form, request, reponse);
        }
    }

    /**
     * checksOutBalance总部核对委托退单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward checksOutBalance(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("id");

        String checks = request.getParameter("reason");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            outManager.checkOutBalance(fullId, user, checks);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功核对单据:" + fullId);

        request.setAttribute("forward", "1");

        return queryOutBalance(mapping, form, request, reponse);
    }

    /**
     * 付款(结算中心)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward payOut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getReserve3() != OutConstant.OUT_SAIL_TYPE_MONEY)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "只能是款到发货的单据可以操作");

            return mapping.findForward("error");
        }

        if (out.getStatus() != OutConstant.STATUS_SUBMIT)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getPay() == OutConstant.PAY_YES)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经付款");

            return mapping.findForward("error");
        }

        try
        {
            outManager.payOut(user, fullId, "结算中心确认收款");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        request.setAttribute(KeyConstant.MESSAGE, "成功确认单据:" + fullId);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * TEMPIMPL 强制通过付款
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward fourcePayOut(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getPay() == OutConstant.PAY_YES)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经付款");

            return mapping.findForward("error");
        }

        try
        {
            outManager.fourcePayOut(user, fullId, "财务确认收款");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功核对单据:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * 付款(财务收款心--往来核对)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward payOut2(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getPay() == OutConstant.PAY_YES)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经付款");

            return mapping.findForward("error");
        }

        try
        {
            outManager.payOut(user, fullId, "财务确认收款");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功核对单据:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * 产生坏账
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward payOut3(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        String baddebts = request.getParameter("baddebts");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getPay() == OutConstant.PAY_YES)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经付款");

            return mapping.findForward("error");
        }

    	// 正在对账
    	if (out.getFeedBackCheck() == 1)
    	{
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许操作坏账:" + out.getFullId());

            return mapping.findForward("error");
    	}
        
        try
        {
            outManager.payBaddebts(user, fullId, MathTools.parseDouble(baddebts));
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功记录坏账单据:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * updateInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateInvoice(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        String invoices = request.getParameter("invoices");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getInvoiceMoney() > 0)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        try
        {
            outManager.updateInvoice(user, fullId, invoices);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功更新发票类型:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        return querySelfOut(mapping, form, request, reponse);
    }

    /**
     * updateInvoiceStatus
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateInvoiceStatus(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        String invoiceMoney = request.getParameter("invoiceMoney");

        User user = (User)request.getSession().getAttribute("user");

        try
        {
            sailManager.updateInvoiceStatus(user, fullId, CommonTools.parseFloat(invoiceMoney),
                OutConstant.INVOICESTATUS_END);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功更新发票状态:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        String queryType = RequestTools.getValueFromRequest(request, "queryType");

        request.setAttribute("queryType", queryType);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * 坏账取消
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward payOut4(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String fullId = request.getParameter("outId");

        OutBean out = outDAO.find(fullId);

        User user = (User)request.getSession().getAttribute("user");

        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }

        if (out.getPay() != OutConstant.PAY_YES)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "还没确认付款");

            return mapping.findForward("error");
        }

        try
        {
            outManager.initPayOut(user, fullId);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功取消坏账单据:" + fullId);

        CommonTools.saveParamers(request);

        RequestTools.actionInitQuery(request);

        return queryOut(mapping, form, request, reponse);
    }

    /**
     * CORE 修改库单的状态(销售单的流程)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward modifyOutStatus(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        long begin = System.currentTimeMillis();

        String fullId = request.getParameter("outId");

        // 动态锁
        synchronized (LockHelper.getLock(fullId))
        {
            try
            {

                User user = (User)request.getSession().getAttribute("user");

                int statuss = Integer.parseInt(request.getParameter("statuss"));

                String oldStatus = request.getParameter("oldStatus");

                if (StringTools.isNullOrNone(oldStatus))
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有历史状态,请重新操作!");

                    return mapping.findForward("error");
                }

                int ioldStatus = Integer.parseInt(oldStatus);

                String reason = request.getParameter("reason");

                String selfQuery = request.getParameter("selfQuery");

                String depotpartId = request.getParameter("depotpartId");

                importLog.info(fullId + ":" + user.getStafferName() + ";oldStatus:" + oldStatus);

                importLog.info(fullId + ":" + user.getStafferName() + ";nextStatus:" + statuss);

                CommonTools.saveParamers(request);

                OutBean out = null;
                
                int resultStatus = -1;

                out = outDAO.find(fullId);
                
                if (out == null)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作!");

                    return mapping.findForward("error");
                }
                
                if (out.getStatus() == statuss)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "请重新操作!");

                    return mapping.findForward("error");
                }

                if (out.getStatus() != ioldStatus)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "单据已经被审批,请重新操作!");

                    return mapping.findForward("error");
                }

                // 入库单的提交(调拨)
                if (out.getType() == OutConstant.OUT_TYPE_INBILL
                    && statuss == OutConstant.STATUS_SUBMIT
                    && ! (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
                    		|| out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
                    		|| out.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
                {
                    try
                    {
                    	if (out.getOutType() == OutConstant.OUTTYPE_OUT_APPLY)
                    	{
                    		resultStatus = outManager.submitDiaoBo(out.getFullId(), user, StorageConstant.OPR_STORAGE_REDEPLOY);
                    	}
                    	else
                    	{
                            resultStatus = outManager.submit(out.getFullId(), user,
                                    StorageConstant.OPR_STORAGE_OUTBILLIN);
                    	}

                        request.setAttribute(KeyConstant.MESSAGE, out.getFullId() + "库单成功提交!");
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);

                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理异常："
                                                                        + e.getErrorContent());

                        return mapping.findForward("error");
                    }
                }
                // 领样退库或者是销售退库的事业部审批
                else if (out.getType() == OutConstant.OUT_TYPE_INBILL
                         && (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
                         	|| out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
                         	|| out.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
                {
                    try
                    {
                        resultStatus = outManager.pass(fullId, user, OutConstant.BUY_STATUS_SUBMIT,
                            reason, depotpartId);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);

                        request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                        return mapping.findForward("error");
                    }
                }
                // 进入库单 库管--分经理--总裁--董事长
                else if (out.getType() == OutConstant.OUT_TYPE_INBILL
                         && statuss != OutConstant.STATUS_SUBMIT)
                {
                    if (out.getOutType() == OutConstant.OUTTYPE_IN_COMMON
                        || out.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
                    {
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购入库和调拨没有此操作");

                        return mapping.findForward("error");
                    }

                    // 财务审核--事业部经理(其它入库)
                    if (statuss == OutConstant.BUY_STATUS_LOCATION_MANAGER_CHECK)
                    {
                        if (out.getOutType() != OutConstant.OUTTYPE_IN_OTHER)
                        {
                            request.setAttribute(KeyConstant.ERROR_MESSAGE, "只有其它入库才有财务审核的环节");

                            return mapping.findForward("error");
                        }

                        try
                        {
                            resultStatus = outManager.pass(fullId, user,
                                OutConstant.STATUS_LOCATION_MANAGER_CHECK, reason, depotpartId);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 进入待总裁审批
                    if (statuss == OutConstant.BUY_STATUS_CEO_CHECK)
                    {
                        try
                        {
                            resultStatus = outManager.pass(fullId, user,
                                OutConstant.STATUS_CEO_CHECK, reason, depotpartId);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 进入待董事长审批
                    if (statuss == OutConstant.BUY_STATUS_CHAIRMA_CHECK)
                    {
                        try
                        {
                            resultStatus = outManager.pass(fullId, user,
                                OutConstant.STATUS_CHAIRMA_CHECK, reason, depotpartId);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 进入待回款(结束了)
                    if (statuss == OutConstant.BUY_STATUS_PASS && out.getOutType() != OutConstant.OUTTYPE_OUT_APPLY)
                    {
                        try
                        {
                            resultStatus = outManager.pass(fullId, user, OutConstant.STATUS_PASS,
                                reason, depotpartId);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 驳回
                    if (statuss == OutConstant.BUY_STATUS_REJECT)
                    {
                        try
                        {
                            resultStatus = outManager.reject(fullId, user, reason);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }
                }
                // 销售单的审批流程
                else
                {
                    // 业务员提交销售单
                    if (statuss == OutConstant.STATUS_SUBMIT)
                    {
                        try
                        {
                            resultStatus = outManager.submit(fullId, user,
                                StorageConstant.OPR_STORAGE_OUTBILL);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 区域总经理信用审核通过
                    if (statuss == OutConstant.STATUS_TEMP)
                    {
                        try
                        {
                        	int nextStatus = OutConstant.STATUS_SUBMIT;
                        	
                        	// MANAGERPASS 表示下一步为结算中心
                        	if (!out.getFlowId().equals(OutConstant.FLOW_MANAGER))
                        	{
                        		nextStatus = OutConstant.STATUS_FLOW_PASS;
                        	}
                        	
                            resultStatus = outManager.pass(fullId, user, nextStatus,
                                reason, depotpartId);
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 结算中心通过 物流管理员 库管通过 总裁通过
                    if (statuss == OutConstant.STATUS_MANAGER_PASS
                        || statuss == OutConstant.STATUS_FLOW_PASS
                        || statuss == OutConstant.STATUS_PASS)
                    {
                        // 这里需要计算客户的信用金额-是否报送物流中心经理审批
                        boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

                        // 如果是黑名单的客户(且没有付款)
                        if (outCredit && out.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
                            && out.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && out.getPay() == OutConstant.PAY_NOT)
                        {
                            try
                            {
                                outManager.payOut(user, fullId, "结算中心确定已经回款");
                            }
                            catch (MYException e)
                            {
                                request
                                    .setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                                return mapping.findForward("error");
                            }

                            OutBean newOut = outDAO.find(fullId);

                            if (newOut.getPay() == OutConstant.PAY_NOT)
                            {
                                request.setAttribute(KeyConstant.ERROR_MESSAGE,
                                    "只有结算中心确定已经回款后才可以审批此销售单");

                                return mapping.findForward("error");
                            }
                        }
                        
                        // 结算中心通过  对参与促销活动的相关审核：
                        if (statuss == OutConstant.STATUS_FLOW_PASS)
                        {
                            try
                            {
                                processPromotion(fullId, user);
                            }
                            catch(MYException e)
                            {
                                _logger.warn(e, e);
                                
                                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                                return mapping.findForward("error");
                            }
                        }

                        try
                        {
                            resultStatus = outManager.pass(fullId, user, statuss, reason, depotpartId);
                            OutBean newOut = outDAO.find(fullId);
                            if(resultStatus == OutConstant.STATUS_PASS)
                            {
                            	outManager.updateCusAndBusVal(newOut,user.getId());	
//                            outDAO.updateEntityBean(newOut);
                            }
                            
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }
                    }

                    // 驳回
                    if (statuss == OutConstant.STATUS_REJECT)
                    {
                        try
                        {
                            resultStatus = outManager.reject(fullId, user, reason);
                            PaymentApplyBean pab = paymentApplyDAO.queryPaymentApplyBeanByfullId(fullId);
                            if(null != pab )
                            {
                            	financeFacade.rejectPaymentApply(user.getId(), pab.getId(), "驳回和销售单关联的收款单");
                            }
                        }
                        catch (MYException e)
                        {
                            _logger.warn(e, e);

                            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                            return mapping.findForward("error");
                        }

                        // 针对销 售的驳回，要向申请人发送邮件
                        sendOutRejectMail(fullId, user, reason, out,"销售单驳回");
                    }
                }

                // 核对状态方式发生异常
                OutBean realOut = outDAO.findRealOut(fullId);

                if (realOut != null && realOut.getStatus() != resultStatus && realOut.getOutType() != OutConstant.OUTTYPE_OUT_APPLY)
                {
                    String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(resultStatus) + ",而不是"
                                 + OutHelper.getStatus(realOut.getStatus()) + ".请联系管理员确认此单的正确状态!";

                    request.setAttribute(KeyConstant.ERROR_MESSAGE, msg);

                    loggerError(fullId + ":" + msg);

                    return mapping.findForward("error");

                }

                importLog.info(fullId + ":" + user.getStafferName() + ";form:" + oldStatus + ";to"
                               + resultStatus + "(SUCCESS)");

                RequestTools.actionInitQuery(request);

                try
                {
                	if(resultStatus==OutConstant.STATUS_PASS)
                    {
                		OutBean bean = outDAO.find(fullId);
                		if(bean.getOutType()==OutConstant.OUTTYPE_OUT_PRESENT)
                		{
                			outManager.updateOut(bean);
                		}
                    }
                }
                catch (MYException e)
                {
                    _logger.warn(e, e);

                    request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                    return mapping.findForward("error");
                }
                if (realOut.getType() == OutConstant.OUT_TYPE_OUTBILL)
                {
                    request.setAttribute(KeyConstant.MESSAGE, "单据["
                                                              + fullId
                                                              + "]操作成功,下一步是:"
                                                              + OutHelper.getStatus(realOut
                                                                  .getStatus()));
                }
                else
                {
                    request.setAttribute(KeyConstant.MESSAGE, "单据["
                                                              + fullId
                                                              + "]操作成功,下一步是:"
                                                              + OutHelper.getStatus2(realOut
                                                                  .getStatus()));
                }

                if (realOut.getType() == OutConstant.OUT_TYPE_OUTBILL)
                {
                    if ("1".equals(selfQuery))
                    {
                        return querySelfOut(mapping, form, request, reponse);
                    }

                    return queryOut(mapping, form, request, reponse);
                }
                else
                {
                    if (StringTools.isNullOrNone(request.getParameter("queryType")))
                    {
                        return querySelfBuy(mapping, form, request, reponse);
                    }

                    return queryBuy(mapping, form, request, reponse);
                }
            }
            finally
            {
                long end = System.currentTimeMillis();

                _logger.info("modifyOutStatus cost:" + (end - begin));
            }
        }
    }

    /**
     * 库管审批时，再次校验促销活动的参照时间与付款时间
     * @param fullId
     * @param user
     * @throws MYException
     */
    private void processPromotion(String fullId, User user)
    throws MYException
    {
        OutBean bean = outDAO.find(fullId);
        
        if (null == bean)
        {
            throw new MYException("数据错误");
        }
        
        // 参与了促销活动
        if (bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC && bean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
            
            String eventId = bean.getEventId();
            
            PromotionBean promBean = promotionDAO.find(eventId);
            
            if (null == promBean)
            {
                throw new MYException("数据错误");
            }
            
            int refTime = promBean.getRefTime();
            
            // 审核参照时间
            if (refTime == PromotionConstant.REFTIME_PAYTIME)
            {
                if (bean.getPay() == OutConstant.PAY_NOT)
                {
                    throw new MYException("促销活动参照时间为付款时间，当前单据为未付款状态，不能通过");
                }else
                {
                    String payDate = bean.getRedate();
                    
                    if (TimeTools.cdate(payDate, promBean.getBeginDate()) < 0 
                            || TimeTools.cdate(payDate, promBean.getEndDate())>0)
                    {
                        throw new MYException("促销活动参照时间为付款时间，但销售单的付款时间不在活动时间范围内");
                    }
                }
            }
            
            int payTime = promBean.getPayTime();
            
            // 审核付款时间，根据收款单中关联的销售单找到回款单，并取这些回款单中最大的时间来与活动时间比较。
            if (payTime == PromotionConstant.PAYTIME_MONEYBEFORDER)
            {
                String refBindOutId = bean.getRefBindOutId();
                                
                Set<String> outIdSet = new HashSet<String>();
                
                outIdSet.add(fullId);
                
                if (!StringTools.isNullOrNone(refBindOutId))
                {
                    String [] outIds = refBindOutId.split("~");
                    
                    if (null != outIds)
                    {
                        for (String outId : outIds)
                        {
                            outIdSet.add(outId);
                        }
                    }
                    
                }
                
                List<InBillBean> lists = new ArrayList<InBillBean>();
                
                for (String id : outIdSet)
                {
                    
                    List<InBillBean> inBillList = inBillDAO.queryEntityBeansByFK(id);
                    
                    lists.addAll(inBillList);
                }
                
                Set<String> paymentIdSet = new HashSet<String>();
                
                for (InBillBean inBean : lists)
                {
                    
                    if (!StringTools.isNullOrNone(inBean.getPaymentId()))
                    {
                        
                        if (!paymentIdSet.contains(inBean.getPaymentId()))
                            paymentIdSet.add(inBean.getPaymentId());
                            
                    }
                }
                
                List<PaymentBean> payBeanList = new ArrayList<PaymentBean>();
                
                for (String paymentId : paymentIdSet)
                {
                    
                    PaymentBean payBean = paymentDAO.find(paymentId);
                    
                    payBeanList.add(payBean);
                }
                
                if (ListTools.isEmptyOrNull(payBeanList))
                {
                    throw new MYException("活动规则付款时间是先款后开单，但参加活动的销售单没有关联回款单，不能通过");
                }
                
                // 从大到小排序
                Collections.sort(payBeanList, new Comparator<PaymentBean>() {
                    public int compare(PaymentBean o1, PaymentBean o2) {
                        return Integer.parseInt(o1.getId().substring(14)) - Integer.parseInt(o2.getId().substring(14));
                    }
                });
                
                // format = yyyy-MM-dd hh:mm:ss
                String paymentTime = payBeanList.get(0).getLogTime();
                
                String paymentDate = TimeTools.changeTimeToDate(paymentTime);
                
//                if (TimeTools.cdate(paymentDate, promBean.getBeginDate()) < 0 
//                        || TimeTools.cdate(paymentDate, promBean.getEndDate())>0)
                if (TimeTools.cdate(paymentDate, bean.getOutTime()) > 0)
                {
                    throw new MYException("活动规则付款时间是先款后开单，但参加活动的销售单关联的回款单时间不在活动时间范围内");
                }
                
            }
            
            
        }
        
    }
    
    /**
     * 领样转销售
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward swatchToSail(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
    	User user = Helper.getUser(request);
    	
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
    	
        synchronized (S_LOCK)
        {
            String outId = request.getParameter("outId");

            OutBean bean = outDAO.find(outId);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

                return mapping.findForward("error");
            }

            List<BaseBean> list = baseDAO.queryEntityBeansByFK(outId);

            bean.setBaseList(list);

            // 验证ref
            ConditionParse con = new ConditionParse();

            con.addWhereStr();

            con.addCondition("OutBean.refOutFullId", "=", outId);

            con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

            List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

            // 允许未审批结束的单子存在，同时进行其它的操作 2013.3.30
            if (false)
            {
            	for (OutBean outBean : refList)
                {
                    if (outManager.isSwatchToSail(outBean.getFullId()) && !OutHelper.isSailEnd(outBean))
                    {
                        // 异常不能增加,只能有一个当前的
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "领样转销售只能存在一个未审批结束的,未审批单据:"
                                                                        + outBean.getFullId());

                        return mapping.findForward("error");
                    }
                }
            }

            List<OutBean> refBuyList = queryRefOut4(request, outId);

            if ( !ListTools.isEmptyOrNull(refBuyList) && false)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此单存在退库,所以转销售只能全部退库后重新发起销售");

                return mapping.findForward("error");
            }

            List<BaseBean> baseList = bean.getBaseList();

            // 计算出已经退货的数量
            for (Iterator iterator = baseList.iterator(); iterator.hasNext();)
            {
                BaseBean baseBean = (BaseBean)iterator.next();

                int hasBack = 0;

                // 退库
                for (OutBean ref : refBuyList)
                {
                    List<BaseBean> refBaseList = ref.getBaseList();

                    for (BaseBean refBase : refBaseList)
                    {
                        if (refBase.equals(baseBean))
                        {
                            hasBack += refBase.getAmount();

                            break;
                        }
                    }
                }

                // 转销售的
                for (OutBean ref : refList)
                {
                    List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

                    for (BaseBean refBase : refBaseList)
                    {
                        if (refBase.equals(baseBean))
                        {
                            hasBack += refBase.getAmount();

                            break;
                        }
                    }
                }

                baseBean.setAmount(baseBean.getAmount() - hasBack);

                if (baseBean.getAmount() <= 0)
                {
                    iterator.remove();
                }
            }

            if (baseList.size() == 0)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "领样已经全部退库或者销售（含申请未审批结束的）,请确认");

                return mapping.findForward("error");
            }

            // 生成销售单,然后保存
            OutBean newOut = new OutBean();

            newOut.setOutTime(TimeTools.now_short());
            newOut.setType(OutConstant.OUT_TYPE_OUTBILL);
            newOut.setOutType(OutConstant.OUTTYPE_OUT_COMMON);
            newOut.setRefOutFullId(outId);
            newOut.setDutyId(bean.getDutyId());
            newOut.setMtype(bean.getMtype());
            newOut.setDescription("领样转销售,领样单据:" + outId);
            newOut.setDepartment(bean.getDepartment());
            newOut.setLocation(bean.getLocation());
            newOut.setLocationId(bean.getLocationId());
            newOut.setDepotpartId(bean.getDepotpartId());
            newOut.setStafferId(bean.getStafferId());
            newOut.setStafferName(bean.getStafferName());
            
            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW)
            {
            	newOut.setCustomerId(bean.getCustomerId());
            	newOut.setCustomerName(bean.getCustomerName());
            	newOut.setConnector(bean.getConnector());
            }

            // 商务
            User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
            
            String elogin = (String)request.getSession().getAttribute("g_elogin");
            
            String g_loginType = (String)request.getSession().getAttribute("g_loginType");
            
            if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

                return mapping.findForward("error");
            }
            
            // 当前切换用户登陆的且为商务登陆的，记录经办人
            if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
            {
            	newOut.setOperator(g_srcUser.getStafferId());
            	newOut.setOperatorName(g_srcUser.getStafferName());
            }
            else
            {
            	newOut.setOperator(user.getStafferId());
            	newOut.setOperatorName(user.getStafferName());
            }
            // 商务 - end
            
            newOut.setBaseList(baseList);

            try
            {
                String newFullId = outManager.addSwatchToSail(Helper.getUser(request), newOut);

                CommonTools.removeParamers(request);

                request.setAttribute("fow", "1");

                request.setAttribute("outId", newFullId);

                request.setAttribute("lock_sw", true);

                return this.findOut(mapping, form, request, reponse);
            }
            catch (MYException e)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

                return mapping.findForward("error");
            }
        }
    }

    /**
     * 查询库单（或者修改）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findOut(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String outId = RequestTools.getValueFromRequest(request, "outId");

        String fow = RequestTools.getValueFromRequest(request, "fow");
        
        String desc = RequestTools.getValueFromRequest(request, "desc");

        String radioIndex = RequestTools.getValueFromRequest(request, "radioIndex");

        CommonTools.saveParamers(request);

        String goback = request.getParameter("goback");

        if (StringTools.isNullOrNone(goback))
        {
            goback = "1";
        }

        request.setAttribute("goback", goback);

        User user = Helper.getUser(request);

        OutVO bean = null;
        try
        {
            bean = outDAO.findVO(outId);
            
            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

                return mapping.findForward("error");
            }

            List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(outId);

            bean.setAttachmentList(attachmentList);
            
            PromotionBean proBean = promotionDAO.find(bean.getEventId());
            
            List<BaseBean> list = baseDAO.queryEntityBeansByFK(outId);

            if (ListTools.isEmptyOrNull(list))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在子项,请重新操作");

                return mapping.findForward("error");
            }

            // 行项目中没有产品类型
            if ("1".equals(fow))
            {
                if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                {
                	for (BaseBean each : list)
                	{
                		ProductBean productBean = productDAO.find(each.getProductId());
                		
                		if (null != productBean)
                		{
                			each.setProductType(productBean.getType());
                			
                			each.setOldGoods(productBean.getConsumeInDay());
                		}
                	}
                }
            }
            
            
            bean.setBaseList(list);

            List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(outId);

            List<FlowLogVO> voList = ListTools.changeList(logs, FlowLogVO.class,
                FlowLogHelper.class, "getOutLogVO");

            String desc1 = bean.getDescription();
            
            int idx = desc1.indexOf("&&");
            
            if (idx != -1)
            {
            	String newDesc = desc1.substring(0, idx);
            	
            	bean.setDescription(newDesc);
            }
            
            request.setAttribute("bean", bean);
            
            request.setAttribute("proBean", proBean);

            request.setAttribute("fristBase", list.get(0));

            if (list.size() > 1)
            {
                request.setAttribute("lastBaseList", list.subList(1, list.size()));
            }

            PrincipalshipBean shiye = principalshipDAO.find(bean.getIndustryId());

            request.setAttribute("shiye", shiye);

            request.setAttribute("logList", voList);

            // 关联的
            List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByOutId(outId);

            request.setAttribute("financeBeanList", financeBeanList);

            List<InsVSOutBean> insList = insVSOutDAO.queryEntityBeansByFK(outId);

            request.setAttribute("insList", insList);

            // 预开票信息
            List<PreInvoiceVSOutBean> preInsList = preInvoiceVSOutDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);
            
            request.setAttribute("preInsList", preInsList);
            
            List<ShowBean> showList = showDAO.listEntityBeans();

            request.setAttribute("showList", showList);
            
            // 配送
            List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outId);
            
            request.setAttribute("distBeanList", distList);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询库单失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        if (bean.getType() == OutConstant.OUT_TYPE_INBILL)
        {
            request.setAttribute("flag", "1");
        }
        else
        {
            request.setAttribute("flag", "0");
        }
        
        boolean deepQuery = true;

        try
        {
            innerForPrepare(request, false, deepQuery);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
        }

        if ( !StringTools.isNullOrNone(radioIndex))
        {
            Map map = (Map)request.getSession().getAttribute("ppmap");

            if (map != null)
            {
                map.put("radioIndex", radioIndex);
            }
        }
        
        // 修改单据
        if ("1".equals(fow))
        {
            if ( !OutHelper.canDelete(bean))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此状态不能修改单据");

                return mapping.findForward("error");
            }

            if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                if (outManager.isSwatchToSail(outId))
                {
                    request.setAttribute("lock_sw", true);
                }

                setHasProm(request, Helper.getStaffer(request));
                
                showLastCredit(request, user, "0");
                
                if (OATools.isChangeToV5())
                {
                	if (outId.startsWith("TM"))
                	{
                		return mapping.findForward("updateOut501");
                	}
                	else
                	{
                		return mapping.findForward("updateOut50");
                	}
                }

                return mapping.findForward("updateOut4_bak");
            }
            else
            {
            	if (bean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY)
            	{
            		 return mapping.findForward("updateDiaoBo");
            	}
            	else
            	{
            		 return mapping.findForward("updateBuy");
            	}
                // 处理修改
               
            }
        }

        if ("4".equals(fow))
        {
            request.setAttribute("year", TimeTools.now("yyyy"));
            request.setAttribute("month", TimeTools.now("MM"));
            request.setAttribute("day", TimeTools.now("dd"));
            return mapping.findForward("print");
        }

        // 调出的处理
        if ("5".equals(fow))
        {
            if (bean.getInway() != OutConstant.IN_WAY)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不在在途中，不能处理");

                return mapping.findForward("error");
            }

            // 查询目的库的良品仓区
            List<DepotpartBean> depotpartList = depotpartDAO.queryOkDepotpartInDepot(bean
                .getDestinationId());

            request.setAttribute("depotpartList", depotpartList);

            return mapping.findForward("handerInvokeBuy");
        }

        // 修改发票类型
        if ("6".equals(fow))
        {
            List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(
                "where forward = ?", InvoiceConstant.INVOICE_FORWARD_OUT);

            request.setAttribute("invoiceList", invoiceList);

            return mapping.findForward("handerInvokeBuy");
        }

        // 处理个人领样退库
        if ("91".equals(fow))
        {
            synchronized (S_LOCK)
            {
                ConditionParse con = new ConditionParse();

                con.addWhereStr();

                con.addCondition("OutBean.refOutFullId", "=", outId);

                con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

                // 领样转销售
                List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

                if (false)
                {
                    for (OutBean outBean : refList)
                    {
                        if (outManager.isSwatchToSail(outBean.getFullId())
                            && !OutHelper.isSailEnd(outBean))
                        {
                            // 异常不能增加,只能有一个当前的
                            request.setAttribute(KeyConstant.ERROR_MESSAGE, "领样转销售只能存在一个未审批结束的,请重新操作");

                            return mapping.findForward("error");
                        }
                    }
                    
                 // 领样退库未审批的
                    con.clear();

                    con.addWhereStr();

                    con.addCondition("OutBean.refOutFullId", "=", outId);

                    con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

                    con.addCondition("and OutBean.status in (0, 1)");

                    con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_SWATCH);

                    int count = outDAO.countByCondition(con.toString());

                    if (count > 0)
                    {
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "此领样已经申请退货请处理结束后再申请");

                        return mapping.findForward("error");
                    }
                }

                List<OutBean> refBuyList = queryRefOut4(request, outId);

                List<BaseBean> baseList = bean.getBaseList();

                // 计算出已经退货的数量
                for (BaseBean baseBean : baseList)
                {
                    int hasBack = 0;

                    // 退库
                    for (OutBean ref : refBuyList)
                    {
                        List<BaseBean> refBaseList = ref.getBaseList();

                        for (BaseBean refBase : refBaseList)
                        {
                            if (refBase.equals(baseBean))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    // 转销售的
                    for (OutBean ref : refList)
                    {
                        List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

                        for (BaseBean refBase : refBaseList)
                        {
                            if (refBase.equals(baseBean))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    baseBean.setInway(hasBack);
                }

                return mapping.findForward("handerOutBack");
            }
        }

        // 处理销售退库
        if ("92".equals(fow))
        {
        	
        	OutBean out = outDAO.find(outId);
        	
        	// 正在对账
        	if (out.getFeedBackCheck() == 1)
        	{
        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许退货");

                return mapping.findForward("error");
        	}
        	
        	PromotionBean proBean = promotionDAO.find(out.getEventId());
	        if(null != proBean)
	        {
	        	if(proBean.getIsReturn()==0)
	        	{
	        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单参加"+proBean.getName()+"活动，不允许退货");

                    return mapping.findForward("error");
	        	}
	        }
            synchronized (S_LOCK)
            {
                ActionForward error = checkAddOutBack(mapping, request, outId);

                if (error != null)
                {
                    return error;
                }
                
                List<OutBean> refBuyList = queryRefOut(request, outId);

                // 这里是归类统计的哦
                List<BaseBean> baseList = OutHelper.trimBaseList2(bean.getBaseList());

                // 是否可以看到真实的成本
                boolean containAuth = userManager.containAuth(user.getId(),
                    AuthConstant.SAIL_QUERY_COST);

                // 计算出已经退货的数量
                int total = 0;
                for (BaseBean baseBean : baseList)
                {
                    int hasBack = 0;

                    // 退库
                    for (OutBean ref : refBuyList)
                    {
                        List<BaseBean> refBaseList = OutHelper.trimBaseList2(ref.getBaseList());

                        for (BaseBean refBase : refBaseList)
                        {
                            if (refBase.equals2(baseBean))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    baseBean.setInway(hasBack);

                    baseBean.setId(OutHelper.getKey2(baseBean));

                    if ( !containAuth)
                    {
                        // 屏蔽成本额
                        baseBean.setCostPrice(baseBean.getInputPrice());
                        baseBean.setDescription(MathTools.formatNum(baseBean.getInputPrice()));
                    }
                    else
                    {
                        baseBean
                            .setDescription(MathTools.formatNum(baseBean.getCostPrice()) + " / "
                                            + MathTools.formatNum(baseBean.getIprice()) + " / "
                                            + MathTools.formatNum(baseBean.getPprice()));
                    }

                    total += (baseBean.getAmount() - hasBack);
                }

                if (total == 0)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "销售单已经全部退库，不能处理");

                    return mapping.findForward("error");
                }

                // 合并后的列表
                bean.setBaseList(baseList);

                return mapping.findForward("handerOutBack2");
            }
        }

        // 处理销售空开空退
        if ("921".equals(fow))
        {
        	OutBean out = outDAO.find(outId);
        	
        	try
        	{
        		checkOutRepaire(out, true);
        	}
        	catch(MYException e)
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent() + ",所以无法空开空退.");

                return mapping.findForward("error");
        	}
        	
        	List<BaseBean> list = baseDAO.queryEntityBeansByFK(outId);
        	
        	out.setBaseList(list);

        	return mapping.findForward("handerOutRepaire");
        	
        }
        
        // 查看空开退明细
        if ("922".equals(fow))
        {
        	String repaireId = request.getParameter("repaireId");
        	
        	OutRepaireBean repaireBean = outRepaireDAO.find(repaireId);
        	
        	if (null == repaireBean)
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "空开空退申请单不存在.");

                return mapping.findForward("error");
        	}
        	
        	List<BaseRepaireBean> baseRepaireList = baseRepaireDAO.queryEntityBeansByFK(repaireId);
        	
        	OutVO out = outDAO.findVO(repaireBean.getOutId());
        	
        	if (null == out)
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "空开空退原销售单不存在.");

                return mapping.findForward("error");
        	}
        	
        	out.setStatus(repaireBean.getStatus());
        	
        	out.setEventId(repaireBean.getReason());
        	
        	out.setNewReday(repaireBean.getReday());
        	
        	out.setNewDutyId(repaireBean.getDutyId());
        	
        	out.setNewInvoiceId(repaireBean.getInvoiceId());
        	
        	out.setOperatorName(repaireBean.getOperatorName());
        	
        	out.setFullId(repaireBean.getId()+"/"+out.getFullId());
        	
        	out.setDescription(repaireBean.getDescription());
        	
        	List<BaseBean> list = baseDAO.queryEntityBeansByFK(repaireBean.getOutId());
        	
        	out.setBaseList(list);
        	
        	for (BaseBean each : list)
        	{
        		for (BaseRepaireBean eachr : baseRepaireList)
        		{
        			if (each.getId().equals(eachr.getBaseId()))
        			{
        				each.setCostPrice(eachr.getPrice());
        				
        				each.setInvoiceMoney(eachr.getInputPrice());
        				
        				each.setDescription(eachr.getShowName());
        			}
        		}
        	}

        	request.setAttribute("bean", out);
        	
            List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(repaireId);
            
            request.setAttribute("logList", logs);
            
        	return mapping.findForward("detailOutRepaireApply");
        	
        }
        
        // 申请退款
        if ("93".equals(fow))
        {
            double backTotal = outDAO.sumOutBackValue(outId);

            double hadOut = outBillDAO.sumByRefId(outId);

            request.setAttribute("hadOut", hadOut);

            request.setAttribute("backTotal", backTotal);

            ResultBean check = outManager.checkOutPayStatus(user, bean);

            if (check.getResult() != -1)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, check.getMessage() + ",所以无法退款.");

                return mapping.findForward("error");
            }

            request.setAttribute("check", check);

            return mapping.findForward("applyBackPay");
        }
        
        // 是否可以看到真实的成本
        boolean containAuth = userManager.containAuth(user.getId(), AuthConstant.SAIL_QUERY_COST);

        if (OATools.isChangeToV5())
        {
            List<BaseBean> baseList = bean.getBaseList();

            if ( !containAuth)
            {
                if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                {
                    for (BaseBean baseBean : baseList)
                    {
                        // 显示成本(输入成本)
                        baseBean.setCostPrice(baseBean.getInputPrice());

                        baseBean.setDescription(MathTools.formatNum(baseBean.getInputPrice()));
                    }
                }
                else
                {
                    String queryType = request.getParameter("queryType");

                    // 查询自己的退库单
                    if ("7".equals(queryType))
                    {
                        for (BaseBean baseBean : baseList)
                        {
                            // 显示成本(输入成本)
                            baseBean.setCostPrice(baseBean.getInputPrice());

                            baseBean.setDescription(MathTools.formatNum(baseBean.getInputPrice()));
                        }
                    }
                    else
                    {
                        // 这里是入库单
                        for (BaseBean baseBean : baseList)
                        {
                            baseBean.setDescription(MathTools.formatNum(baseBean.getCostPrice()));
                        }
                    }
                }
            }
            else
            {
                for (BaseBean baseBean : baseList)
                {
                    baseBean.setDescription(MathTools.formatNum(baseBean.getCostPrice()) + " / "
                                            + MathTools.formatNum(baseBean.getIprice()) + " / "
                                            + MathTools.formatNum(baseBean.getPprice()));
                }
            }
        }

        // 销售单明细
        if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            CustomerBean cus = customerMainDAO.find(bean.getCustomerId());

            if (cus != null)
            {
                // CustomerHelper.decryptCustomer(cus);
                bean.setCustomerAddress(cus.getAddress());
            }

            List<InBillVO> billList = inBillDAO.queryEntityVOsByFK(outId);

            request.setAttribute("billList", billList);

            List<OutBillVO> outBillList = outBillDAO.queryEntityVOsByFK(outId);

            request.setAttribute("outBillList", outBillList);

            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
            {
                // 委托代销的结算单
                List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(outId);

                for (Iterator iterator = balanceList.iterator(); iterator.hasNext();)
                {
                    OutBalanceBean outBalanceBean = (OutBalanceBean)iterator.next();

                    if (outBalanceBean.getStatus() != OutConstant.OUTBALANCE_STATUS_PASS
                        && outBalanceBean.getStatus() != OutConstant.OUTBALANCE_STATUS_END)
                    {
                        iterator.remove();
                    }
                }

                request.setAttribute("balanceList", balanceList);
            }

            // 个人领样
            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
            		|| bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
            		|| bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH)
            {
                queryRefOut(request, outId);

                queryRefOut2(request, outId);

                queryRefOut3(request, outId);
            }

            // 退单
            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
                || bean.getOutType() == OutConstant.OUTTYPE_OUT_RETAIL
                || bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
            {
                queryRefOut(request, outId);
            }

            ResultBean checkOutPayStatus = outManager.checkOutPayStatus(user, bean);

            request.setAttribute("checkOutPayStatus", checkOutPayStatus);
            
            // 详细
            return mapping.findForward("detailOut");
        }
        
        if (bean.getStatus() == OutConstant.STATUS_PASS)
        {
            // 标识为结束
            bean.setStatus(OutConstant.STATUS_SEC_PASS);
            bean.setPay(OutConstant.PAY_YES);
            try
            {
            outManager.updateOut(bean);
            }
            catch (Exception e)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "更新付款状态失败");

                _logger.error(e, e);

                return mapping.findForward("error");
            }
        }

        // 这里的发票应该是全部的
        List<InvoiceBean> invoiceList = invoiceDAO.listEntityBeans();

        request.setAttribute("invoiceList", invoiceList);

        // 入库单也需要控制显示
        if ( !containAuth)
        {
            if (bean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH
                || bean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
            {
                List<BaseBean> baseList = bean.getBaseList();

                for (BaseBean baseBean : baseList)
                {
                    // 显示成本(输入成本)
                    baseBean.setCostPrice(baseBean.getInputPrice());

                    baseBean.setDescription(MathTools.formatNum(baseBean.getInputPrice()));
                }
            }
        }

        return mapping.findForward("detailBuy");
    }
    
    /**
     * 其它入库,关联原单
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findOut1(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
        
        String outId = RequestTools.getValueFromRequest(request, "outId");

        String fow = RequestTools.getValueFromRequest(request, "fow");

        CommonTools.saveParamers(request);

        User user = Helper.getUser(request);

        OutVO bean = null;
        try
        {
            bean = outDAO.findVO(outId);
            
            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

                return mapping.findForward("error");
            }

            if (bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "不能关联参加了促销活动的销售单进行其它入库");
                
                return mapping.findForward("error");
            }
            
            List<BaseBean> list = baseDAO.queryEntityBeansByFK(outId);

            if (ListTools.isEmptyOrNull(list))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在子项,请重新操作");

                return mapping.findForward("error");
            }

            bean.setBaseList(list);

            List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(outId);

            List<FlowLogVO> voList = ListTools.changeList(logs, FlowLogVO.class,
                FlowLogHelper.class, "getOutLogVO");

            request.setAttribute("bean", bean);

            request.setAttribute("fristBase", list.get(0));

            if (list.size() > 1)
            {
                request.setAttribute("lastBaseList", list.subList(1, list.size()));
            }

            PrincipalshipBean shiye = null;
            
            if(null != bean.getIndustryId())
                shiye = principalshipDAO.find(bean.getIndustryId());

            request.setAttribute("shiye", shiye);

            request.setAttribute("logList", voList);

            // 关联的
            List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByOutId(outId);

            request.setAttribute("financeBeanList", financeBeanList);

            List<InsVSOutBean> insList = insVSOutDAO.queryEntityBeansByFK(outId);

            request.setAttribute("insList", insList);

            List<ShowBean> showList = showDAO.listEntityBeans();

            request.setAttribute("showList", showList);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询库单失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        if (bean.getType() == OutConstant.OUT_TYPE_INBILL)
        {
            request.setAttribute("flag", "1");
        }

        boolean deepQuery = true;

        try
        {
            innerForPrepare(request, false, deepQuery);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
        }
   
        
        
     // 其它入库关联原销售单时
        if ("992".equals(fow))
        {
            String location = RequestTools.getValueFromRequest(request, "location");

            String description = RequestTools.getValueFromRequest(request, "description");
            
            String forceBuyType = RequestTools.getValueFromRequest(request, "forceBuyType");
            
            String dutyId = RequestTools.getValueFromRequest(request, "dutyId");
                
            synchronized (S_LOCK)
            {
                ActionForward error = checkAddOutOtherBack(mapping, request, outId);

                if (error != null)
                {
                    return error;
                }

                List<OutBean> refBuyList = queryRefOut(request, outId);

                List<BaseBean> obaseList = bean.getBaseList();
                
                for (BaseBean obean : obaseList)
                {
                    obean.setDescription(obean.getId());
                }
                
                
                // 这里是归类统计的哦
                List<BaseBean> baseList = OutHelper.trimBaseList3(obaseList);

                // 是否可以看到真实的成本
                boolean containAuth = userManager.containAuth(user.getId(),
                    AuthConstant.SAIL_QUERY_COST);

                // 计算出已经退货的数量
                int total = 0;
                for (BaseBean baseBean : baseList)
                {
                    int hasBack = 0;

                    // 退库
                    for (OutBean ref : refBuyList)
                    {
                        List<BaseBean> refBaseList = OutHelper.trimBaseList3(ref.getBaseList());

                        for (BaseBean refBase : refBaseList)
                        {
                            if (refBase.getDescription().equals(baseBean.getDescription()))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    baseBean.setInway(hasBack);

                    baseBean.setId(OutHelper.getKey3(baseBean));

                    if ( !containAuth)
                    {
                        // 屏蔽成本额
                        baseBean.setCostPrice(baseBean.getInputPrice());
                        baseBean.setDescription(MathTools.formatNum(baseBean.getInputPrice()));
                    }
                    else
                    {
                        baseBean
                            .setDescription(MathTools.formatNum(baseBean.getCostPrice()) + " / "
                                            + MathTools.formatNum(baseBean.getIprice()) + " / "
                                            + MathTools.formatNum(baseBean.getPprice()));
                    }

                    total += (baseBean.getAmount() - hasBack);
                }

                if (total == 0)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "销售单已经全部退库，不能处理");

                    return mapping.findForward("error");
                }

                // 合并后的列表
                bean.setBaseList(baseList);

                request.setAttribute("location", location);
                
                request.setAttribute("description", description);
                
                request.setAttribute("forceBuyType", forceBuyType);
                
                request.setAttribute("dutyId", dutyId);
                
                return mapping.findForward("handerOutBack3");
            }
        
        }
        
        return querySelfBuy(mapping, form, request, reponse);
    }
    
    /**
     * preForUpdateOut2
     * 
     * @param request
     * @param bean
     */
    protected void preForUpdateOut2(HttpServletRequest request, OutVO bean)
    {
        // 处理更多逻辑
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String dutyId = bean.getDutyId();

        DutyBean duty = dutyDAO.find(dutyId);

        // 开票
        condtion.addIntCondition("finType" + duty.getType(), "=", 1);

        condtion.addIntCondition("ratio" + duty.getType(), "=", bean.getRatio());

        condtion.addIntCondition("sailType", "=", bean.getSailType());

        condtion.addIntCondition("productType", "=", bean.getProductType());

        List<SailConfigVO> resultList = sailConfigDAO.queryEntityVOsByCondition(condtion);

        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        request.setAttribute("dutyList", dutyList);

        // 查询开单品名(是过滤出来的)
        List<ShowBean> showList = new ArrayList();

        for (SailConfigVO sailConfigVO : resultList)
        {
            ShowBean show = showDAO.find(sailConfigVO.getShowId());

            show.setDutyId(bean.getDutyId());

            showList.add(show);
        }

        request.setAttribute("sailDuty", duty);

        // 发票的描述
        String ratio = bean.getRatio();

        request.setAttribute("invoiceDes", "销货发票,税点:" + ratio + "‰(千分值)");

        // 替换之前的
        JSONArray shows = new JSONArray(showList, true);

        // 替换过滤的show
        request.setAttribute("showJSON", shows.toString());
    }

    /**
     * 查询销售单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryOut(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String nopay = request.getParameter("nopay");
        
        if (StringTools.isNullOrNone(nopay))
        	nopay = "0";
        
        List<OutVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setInnerCondition2(request, condtion);
            
            int total = outDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYOUT);

            list = outDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYOUT);

            list = outDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYOUT), PageSeparateTools.getPageSeparate(request, RPTQUERYOUT));
        }

        // nopay 是勾款时打的标记
        if ("1".equals(nopay)){
        	for (Iterator<OutVO> iterator = list.iterator(); iterator.hasNext();)
        	{
        		OutVO vo = iterator.next();
        		
        		double retTotal = outDAO.sumOutBackValue(vo.getFullId());
        		
        		vo.setRetTotal(retTotal);
        		
        		if ((vo.getTotal() - vo.getHadPay() - retTotal - vo.getBadDebts() - vo.getPromValue()) <= 0)
        			iterator.remove();
        	}
        }
        
        request.setAttribute("list", list);

        return mapping.findForward("rptQueryOut");
    }

    /**
     * 查询委托代销的清单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryOutBalance(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String nopay = request.getParameter("nopay");
        
        if (StringTools.isNullOrNone(nopay))
        	nopay = "0";
        
        List<OutBalanceVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setInnerCondition3(request, condtion);

            int total = outBalanceDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYOUTBALANCE);

            list = outBalanceDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYOUTBALANCE);

            list = outBalanceDAO
                .queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                    RPTQUERYOUTBALANCE), PageSeparateTools.getPageSeparate(request,
                    RPTQUERYOUTBALANCE));
        }

        // process list
        for (OutBalanceVO each : list)
        {
        	// 结算单退款金额
        	double refMoneys = outBalanceDAO.sumByOutBalanceId(each.getId());
        	
        	each.setRefMoneys(refMoneys);
        	
        	// 已开的发票 = 已开票 + 未审批结束的金额
        	double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(each.getId());
        	
        	// 在途未审批结束的，未来可能要分开，以示说明
        	each.setInvoiceMoney(each.getInvoiceMoney() + notEndInvoice);
        	
        	each.setMayInvoiceMoneys(each.getTotal() - refMoneys - each.getInvoiceMoney());
        }
        
        if (nopay.equals("1")){
        	for (Iterator<OutBalanceVO> iterator = list.iterator(); iterator.hasNext();)
        	{
        		OutBalanceVO vo = iterator.next();
        		
        		if ((vo.getTotal() - vo.getPayMoney() - vo.getRefMoneys()) <= 0)
        			iterator.remove();
        	}
        }
        
        request.setAttribute("list", list);

        return mapping.findForward("rptQueryOutBalance");
    }

    /**
     * @param request
     * @param condtion
     */
    protected void setInnerCondition2(HttpServletRequest request, ConditionParse condtion)
    {
        // 条件查询
        String outTime = request.getParameter("outTime");

        String outTime1 = request.getParameter("outTime1");

        String customerName = request.getParameter("customerName");

        String fullId = request.getParameter("fullId");
        
        String pay = request.getParameter("pay");
        
        String mtype = request.getParameter("mtype");

        if ( !StringTools.isNullOrNone(outTime))
        {
            condtion.addCondition("OutBean.outTime", ">=", outTime);
        }
        else
        {
            condtion.addCondition("OutBean.outTime", ">=", TimeTools.now_short( -180));

            request.setAttribute("outTime", TimeTools.now_short( -180));
        }

        if ( !StringTools.isNullOrNone(outTime1))
        {
            condtion.addCondition("OutBean.outTime", "<=", outTime1);
        }
        else
        {
            condtion.addCondition("OutBean.outTime", "<=", TimeTools.now_short());

            request.setAttribute("outTime1", TimeTools.now_short());
        }

        if ( !StringTools.isNullOrNone(fullId))
        {
            condtion.addCondition("OutBean.fullId", "like", fullId);
        }

        if ( !StringTools.isNullOrNone(customerName))
        {
            condtion.addCondition("OutBean.customerName", "like", customerName);
        }
        
        // (url)固定查询
        String stafferId = request.getParameter("stafferId");

        String mode = request.getParameter("mode");

        String invoiceId = request.getParameter("invoiceId");

        String dutyId = request.getParameter("dutyId2");
        
        if (StringTools.isNullOrNone(dutyId))
        	dutyId = "";
        
        String customerId = request.getParameter("customerId");

        String invoiceStatus = request.getParameter("invoiceStatus");

        String bad = request.getParameter("bad");

        if ( !StringTools.isNullOrNone(invoiceId))
        {
            // CORE 限制销售单开票的时间点(2012前)
            condtion.addCondition("OutBean.outTime", "<=", "2011-12-31");

            condtion.addCondition("OutBean.invoiceId", "=", invoiceId);
        }

        // 2012后 - 不限制开票与开单一致了 modify 2013-05-27
       /* String invoiceId2 = request.getParameter("invoiceId2");

        if ( !StringTools.isNullOrNone(invoiceId2))
        {
        	if ((!dutyId.equals(OutConstant.EXCLUDE_DUTY1)) && (!dutyId.equals(OutConstant.EXCLUDE_DUTY2)))
        	{
        		condtion.addCondition("OutBean.invoiceId", "=", invoiceId2);
        	}
        }

        if ( !StringTools.isNullOrNone(dutyId))
        {
        	if ((!dutyId.equals(OutConstant.EXCLUDE_DUTY1)) && (!dutyId.equals(OutConstant.EXCLUDE_DUTY2)))
        	{
        		condtion.addCondition("OutBean.dutyId", "=", dutyId);
        	}
        }*/

        if ( !StringTools.isNullOrNone(customerId))
        {
            condtion.addCondition("OutBean.customerId", "=", customerId);
        }

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("OutBean.stafferId", "=", stafferId);
        }

        if ( !StringTools.isNullOrNone(invoiceStatus))
        {
            condtion.addIntCondition("OutBean.invoiceStatus", "=", invoiceStatus);
        }

        // 查询需要勾款的销售单
        if ("0".equals(mode))
        {
        	condtion.addIntCondition("OutBean.pay", "=", 0);
        	
            condtion.addCondition("and OutBean.status in (1, 3, 4, 6, 7, 8, 9)");

            // 过滤委托代销&领样&巡展
//            condtion.addIntCondition("OutBean.outType", "<>", OutConstant.OUTTYPE_OUT_CONSIGN);
            condtion.addCondition("and OutBean.outType not in (1, 3, 5, 6)");
        }

        // 需要开票的销售单
        if ("1".equals(mode))
        {
            condtion.addCondition(" and OutBean.status in (3, 4)");
            
            //condtion.addCondition("and OutBean.status not in (0, 2)");

            // 过滤委托代销
            condtion.addIntCondition("OutBean.outType", "<>", OutConstant.OUTTYPE_OUT_CONSIGN);
        }
        
        if ("2".equals(mode))
        {
            condtion.addCondition(" and OutBean.status in (3, 4)");
            
            // 过滤委托代销
            condtion.addCondition("and OutBean.outType not in (1, 3, 5, 6)");
        }

        String hasRebate = request.getParameter("hasRebate");
        
        if (StringTools.isNullOrNone(hasRebate))
        	hasRebate = "";
        
        // 只查未返利的单据
        if ("0".equals(hasRebate))
        {
        	condtion.addIntCondition("OutBean.hasRebate", "=", 0);
        }
        
        if ("1".equals(bad))
        {
            condtion.addCondition(" and OutBean.status in (3, 4)");

            condtion.addIntCondition("OutBean.badDebts", ">", 0);

            condtion.addIntCondition("OutBean.badDebtsCheckStatus", "=",
                OutConstant.BADDEBTSCHECKSTATUS_NO);
        }

        if (!StringTools.isNullOrNone(pay))
        {
        	int ipay = MathTools.parseInt(pay);
        	
        	condtion.addIntCondition("OutBean.pay", "=", ipay);
        }
        
        condtion.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        
        String nopay = request.getParameter("nopay");
        
        // 款勾单的标记
        if ("1".equals(nopay))
        {
        	setExtraCondition(condtion, mtype, dutyId, 0);
        }

        condtion.addCondition("order by OutBean.id desc");
    }

	private void setExtraCondition(ConditionParse condtion, String mtype, String dutyId, int type)
	{
		if (StringTools.isNullOrNone(dutyId))
		{
			_logger.info("查询销售单（弹出窗）时纳税实体为空");
			condtion.addFlaseCondition();
			
			return;
		}
		
		if (StringTools.isNullOrNone(mtype))
		{
			DutyBean duty = dutyDAO.find(dutyId);
			
			if (null == duty)
			{
				_logger.info("查询销售单（弹出窗）时纳税实体为空");
				condtion.addFlaseCondition();
				
				return;
			}
			
			mtype = String.valueOf(duty.getMtype());
		}
		
		// 销售
		if (type == 0)
		{
			if (PublicConstant.DEFAULR_DUTY_ID.equals(dutyId))
			{
				condtion.addCondition(" and (OutBean.piDutyId = '' or (OutBean.piMtype = 1 and OutBean.piStatus = 1))");
			}else if ("1".equals(mtype)) // 普通
			{
				condtion.addCondition(" and (OutBean.piDutyId = '' or " +
						"(OutBean.piMtype = 1 and OutBean.piStatus = 1 and outBean.piDutyId in ('COMMON','90201008080000000001','" + dutyId + "')))");
			}else{
				condtion.addCondition(" and (OutBean.piDutyId = '' or (OutBean.piMtype = 0 and OutBean.piStatus = 1))");
			}
		}else{
			if (PublicConstant.DEFAULR_DUTY_ID.equals(dutyId))
			{
				condtion.addCondition(" and (OutBalanceBean.piDutyId = '' or (OutBalanceBean.piMtype = 1 and OutBalanceBean.piStatus = 1))");
			}else if ("1".equals(mtype)) // 普通
			{
				condtion.addCondition(" and (OutBalanceBean.piDutyId = '' or " +
						"(OutBalanceBean.piMtype = 1 and OutBalanceBean.piStatus = 1 and OutBalanceBean.piDutyId in ('COMMON','90201008080000000001','" + dutyId + "')))");
			}else{
				condtion.addCondition(" and (OutBalanceBean.piDutyId = '' or (OutBalanceBean.piMtype = 0 and OutBalanceBean.piStatus = 1))");
			}
		}
	}

    /**
     * 委托代销清单过滤
     * 
     * @param request
     * @param condtion
     */
    protected void setInnerCondition3(HttpServletRequest request, ConditionParse condtion)
    {
        // 条件查询
        String alogTime = request.getParameter("alogTime");

        String blogTime = request.getParameter("blogTime");

        String fullId = request.getParameter("outId");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("OutBalanceBean.logTime", ">=", alogTime);
        }
        else
        {
            condtion.addCondition("OutBalanceBean.logTime", ">=", TimeTools.now( -180));

            request.setAttribute("alogTime", TimeTools.now( -180));
        }

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("OutBalanceBean.logTime", "<=", blogTime);
        }
        else
        {
            condtion.addCondition("OutBalanceBean.logTime", "<=", TimeTools.now());

            request.setAttribute("blogTime", TimeTools.now());
        }

        if ( !StringTools.isNullOrNone(fullId))
        {
            condtion.addCondition("OutBalanceBean.outId", "like", fullId);
        }

        // (url)固定查询
        String stafferId = request.getParameter("stafferId");

        String invoiceId = request.getParameter("invoiceId");

        String dutyId = request.getParameter("dutyId");

        String type = request.getParameter("type");

        String pay = request.getParameter("pay");

        String customerId = request.getParameter("customerId");

        String invoiceStatus = request.getParameter("invoiceStatus");

        if ( !StringTools.isNullOrNone(invoiceId))
        {
            // CORE 限制销售单开票的时间点(2012前)
            condtion.addCondition("OutBean.outTime", "<=", "2011-12-31");

            condtion.addCondition("OutBean.invoiceId", "=", invoiceId);
        }

        String invoiceId2 = request.getParameter("invoiceId2");

        // 2012后
        /*if ( !StringTools.isNullOrNone(invoiceId2))
        {
        	if ((!dutyId.equals(OutConstant.EXCLUDE_DUTY1)) && (!dutyId.equals(OutConstant.EXCLUDE_DUTY2)))
        		condtion.addCondition("OutBean.invoiceId", "=", invoiceId2);
        }

        if ( !StringTools.isNullOrNone(dutyId))
        {
        	if ((!dutyId.equals(OutConstant.EXCLUDE_DUTY1)) && (!dutyId.equals(OutConstant.EXCLUDE_DUTY2)))
        		condtion.addCondition("OutBean.dutyId", "=", dutyId);
        }*/

        // 客户不做为查询条件，就按人员查询
        if (StringTools.isNullOrNone(customerId)) {
        	if ( !StringTools.isNullOrNone(stafferId))
            {
                condtion.addCondition("OutBean.stafferId", "=", stafferId);
            }
        }

        if ( !StringTools.isNullOrNone(invoiceStatus))
        {
            condtion.addIntCondition("OutBalanceBean.invoiceStatus", "=", invoiceStatus);
        }

        if ( !StringTools.isNullOrNone(type))
        {
            condtion.addIntCondition("OutBalanceBean.type", "=", type);
        }

        if ( !StringTools.isNullOrNone(pay))
        {
        	if("1".equals(pay))
        		condtion.addCondition("OutBalanceBean.payMoney", ">", 0);
        	else
        		condtion.addCondition("OutBalanceBean.payMoney", "=", 0);
        }

        if ( !StringTools.isNullOrNone(customerId))
        {
            condtion.addCondition("OutBalanceBean.customerId", "=", customerId);
        }
        
        String mtype = request.getParameter("mtype");
        
        String hasRebate = request.getParameter("hasRebate");
        
        if (StringTools.isNullOrNone(hasRebate))
        	hasRebate = "";
        
        // 只查未返利的单据
        if ("0".equals(hasRebate))
        {
        	condtion.addIntCondition("OutBalanceBean.hasRebate", "=", 0);
        }
        
        condtion.addIntCondition("OutBalanceBean.status", "=", OutConstant.OUTBALANCE_STATUS_PASS);
        
        String nopay = request.getParameter("nopay");
        
        // 款勾单的标记
        if ("1".equals(nopay))
        {
        	setExtraCondition(condtion, mtype, dutyId, 1);
        }

        condtion.addCondition("order by OutBalanceBean.logTime desc");
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryOutForInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        CommonTools.saveParamers(request);

        List<OutVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            // 已付款
//            condtion.addIntCondition("OutBean.pay", "=", OutConstant.PAY_YES);
            
            setInnerCondition2(request, condtion);

            int total = outDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYOUTFORINVOICE);

            list = outDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYOUTFORINVOICE);

            list = outDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYOUTFORINVOICE), PageSeparateTools.getPageSeparate(request, RPTQUERYOUTFORINVOICE));
        }

        // process list.  计算退货，折扣，已开票金额
        for (Iterator<OutVO> iterator = list.iterator(); iterator.hasNext();)
        {
        	OutVO each = iterator.next();
        	
        	double retTotal = outDAO.sumOutBackValueIgnoreStatus(each.getFullId());
        	
        	List<InBillBean> inList = this.inBillDAO.queryEntityBeansByFK(each.getFullId());
        	
        	StringBuffer sb = new StringBuffer();
        	
        	for(InBillBean inbill : inList)
        	{
        		PaymentBean pb = this.paymentDAO.find(inbill.getPaymentId());
        		
        		if (null != pb)
        		{
            		if(!sb.toString().contains(pb.getFromer()))
            		{
            			sb.append(pb.getFromer()+",");
            		}
        		}
        	}
        	each.setDescription(sb.toString());
        	
        	// 已开的发票 = 已开票 + 未审批结束的金额
        	double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(each.getFullId());
        	
        	double hadInvoice = each.getInvoiceMoney() + notEndInvoice;
        	
        	each.setRetTotal(retTotal);
        	
        	each.setInvoiceMoney(hadInvoice);
        	
        	each.setMayInvoiceMoneys(each.getTotal() - retTotal - hadInvoice);
        	
        	if (each.getMayInvoiceMoneys() <= 0)
        		iterator.remove();
        }
        
        request.setAttribute("list", list);

        return mapping.findForward("rptQueryOutForInvoice");    
	}
    
    /**
     * 导出所有职员信用明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllStafferCredit(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "StafferCredit_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        List<StafferBean> stafferList = stafferDAO.listCommonEntityBeans();

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            // 获取统计
            outManager.writeStafferCredit(write, stafferList);

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
     * 历史销售
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryHisOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
        
        CommonTools.saveParamers(request);

        String customerId = request.getParameter("customerId");
        
        String eventDate = request.getParameter("eventDate");
        
        String beginDate = "2099-12-30";
        String endDate = "2099-12-30";
        
        if (!StringTools.isNullOrNone(eventDate))
        {
            String [] eventDates = eventDate.split("~");
            
            beginDate = eventDates[0];
            endDate = eventDates[1];
        }        
        
        User user = Helper.getUser(request);
        
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();

        condition.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        
        condition.addCondition("OutBean.outTime", ">=", beginDate);
        condition.addCondition("OutBean.outTime", "<=", endDate);
        
        condition.addCondition("OutBean.stafferId", "=", user.getStafferId());
        condition.addCondition("OutBean.customerId", "=", customerId);
        
        condition.addCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_COMMON);
        
        condition.addCondition("and OutBean.status in (3, 4)");  
        
        condition.addIntCondition("OutBean.promStatus", "=", -1);
        
        List<OutVO> list = outDAO.queryEntityVOsByCondition(condition);
                
        //  去掉全部退货的 
       
        for (OutVO outVO : list)
        {
            double refRetTotal = outDAO.sumOutBackValue(outVO.getFullId());
            
            outVO.setTotal(outVO.getTotal() - refRetTotal);            
        }
        
        //  全部退了
        for (Iterator<OutVO> iterator = list.iterator(); iterator.hasNext();)
        {
            OutVO vo = iterator.next();
            
            if (vo.getTotal() == 0)
            {
                iterator.remove();
            }
        }
        
        request.setAttribute("BeanList", list);
        
//        getDivs(request, list);        
                
        return mapping.findForward("rptQueryHisOut");
    }
    
    /**
     * 空开空退
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward outRepaire(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
        User user = Helper.getUser(request);
        
        String outId = request.getParameter("outId");
        
        OutBean out = outDAO.find(outId);
        
        if (out == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("error");
        }
        
        OutRepaireBean repaireBean = new OutRepaireBean();
        
        //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
        
        User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
        
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

            return mapping.findForward("error");
        }
        
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
        {
        	repaireBean.setOperator(g_srcUser.getStafferId());
        	repaireBean.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	repaireBean.setOperator(user.getStafferId());
        	repaireBean.setOperatorName(user.getStafferName());
        }
        // 商务 - end

        fillRepaireBean(request, user, outId, repaireBean);
        
        // 
        if (repaireBean.getReason().equals(OutConstant.OUT_REPAIREREASON_DONOTAUTOPAY))
        {
        	if (out.getPay() == OutConstant.PAY_NOT)
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "新单不自动勾款时，原单须为已付款态");

                return mapping.findForward("error");
        	}
        }
        
//		原帐期+新增帐期天数-（当天日期-原开单日期）
        
        int day = TimeTools.cdate(TimeTools.now_short(), out.getOutTime());
        
		long add = (out.getReday() + repaireBean.getReday() - day)  * 24 * 3600 * 1000L;
		
		String redate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");
		
		repaireBean.setRedate(redate);

		try
        {
        	checkOutRepaire(out, true);
        	
        	outManager.outRepaire(user, repaireBean);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            _logger.error(e, e);

            return mapping.findForward("error");
        }
        
        return mapping.findForward("querySelfOutRepaireApply");
    }

	private void fillRepaireBean(HttpServletRequest request, User user, String outId, OutRepaireBean repaireBean)
	{
        List<BaseRepaireBean> list = new ArrayList<BaseRepaireBean>();
        
        repaireBean.setList(list);
		
		int reday = MathTools.parseInt(request.getParameter("reday"));
        
        String dutyId = request.getParameter("dutyId");
        
        String invoiceId = request.getParameter("invoiceId");
        
        String description = request.getParameter("description");
        
        String reason = request.getParameter("adescription");
        
        repaireBean.setOutId(outId);
        
        repaireBean.setReday(reday);
        
        repaireBean.setDutyId(dutyId);
        
        repaireBean.setInvoiceId(invoiceId);
        
        repaireBean.setDescription(description);
        
        repaireBean.setReason(reason);
        
        repaireBean.setLogTime(TimeTools.now());
        
        repaireBean.setStafferId(user.getStafferId());
        
        repaireBean.setStafferName(user.getStafferName());
        
        String [] prices = request.getParameterValues("price");
        
        String [] inputPrices = request.getParameterValues("inputPrice");
        
        String [] baseIds = request.getParameterValues("baseId");

        for (int i = 0; i < baseIds.length; i++)
        {
        	BaseBean base = baseDAO.find(baseIds[i]);
        	
        	BaseRepaireBean baseRepaireBean = new BaseRepaireBean();
        	
        	baseRepaireBean.setBaseId(baseIds[i]);
        	
        	baseRepaireBean.setProductId(base.getProductId());
        	
        	baseRepaireBean.setProductName(base.getProductName());
        	
        	baseRepaireBean.setAmount(base.getAmount());
        	
        	baseRepaireBean.setPrice(MathTools.parseDouble(prices[i]));
        	
        	baseRepaireBean.setInputPrice(MathTools.parseDouble(inputPrices[i]));
        	
        	baseRepaireBean.setValue(MathTools.parseDouble(prices[i]) * base.getAmount());
        	
//        	baseRepaireBean.setShowId(showIds[i]);
        	
//        	ShowBean showBean = showDAO.find(baseRepaireBean.getShowId());
//        	
//        	if (null != showBean)
//        	{
//        		baseRepaireBean.setShowName(showBean.getName());
//        	}
        	
        	list.add(baseRepaireBean);
        }
	}
    
	/**
	 * 空开空退申请审批查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
    public ActionForward queryApproveOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion
            .addIntCondition("OutRepaireBean.status", "=", OutConstant.OUT_REPAIRE_PASS);

        ActionTools.processJSONQueryCondition(QUERYAPPROVEOUTREPAIREAPPLY, request, condtion);

        condtion.addCondition("order by OutRepaireBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPROVEOUTREPAIREAPPLY, request,
            condtion, this.outRepaireDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
	
    /**
     * 我的空开空退
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("OutRepaireBean.stafferId", "=", Helper
            .getUser(request)
            .getStafferId());

        ActionTools.processJSONQueryCondition(QUERYSELFOUTREPAIREAPPLY, request, condtion);

        condtion.addCondition("order by OutRepaireBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFOUTREPAIREAPPLY, request,
            condtion, this.outRepaireDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 所有
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLOUTREPAIREAPPLY, request, condtion);

        condtion.addCondition("order by OutRepaireBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLOUTREPAIREAPPLY, request,
            condtion, this.outRepaireDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
	/**
	 * 通过空开空退
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
    public ActionForward rejectOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            String reason = request.getParameter("reason");

            User user = Helper.getUser(request);

            outManager.rejectOutRepaireApply(user, id, reason);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(reponse, ajax);
    }
	
    /**
     * 驳回空开空退
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward passOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
    	long begin = System.currentTimeMillis();
    	
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            checkOutRepaire(id);
            
            outManager.passOutRepaireApply(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }
        finally
        {
            long end = System.currentTimeMillis();

            _logger.info("passOutRepaireApply cost:" + (end - begin));
        }

        return JSONTools.writeResponse(reponse, ajax);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findOutRepaireApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        OutRepaireVO bean = outRepaireDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);

        List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

        request.setAttribute("loglist", loglist);

        return mapping.findForward("detailOutRepaireApply");
    }
    
    /**
     * 根据空开空退ID检查原销售单有效性
     * @param outRepaireId
     * @return
     * @throws MYException
     */
    private boolean checkOutRepaire(String outRepaireId) throws MYException
    {
    	 OutRepaireBean bean = outRepaireDAO.find(outRepaireId);
         
    	 if (null == bean)
    	 {
    		 throw new MYException("空开空退单不存在");
    	 }
    	 
         OutBean out = outDAO.find(bean.getOutId());
         
         if (null == out)
         {
        	 throw new MYException("空开空退的销售单不存在");
         }
    	
         checkOutRepaire(out, false);
         
    	return true;
    }
    
    /**
     * 空开空退合理性检查
     * @param out
     * @return
     * @throws MYException
     */
    private boolean checkOutRepaire(OutBean out, boolean check) throws MYException
	{
		if (out.getOutType() != OutConstant.OUTTYPE_OUT_COMMON)
		{
			throw new MYException("不是销售出库单，不能操作");
		}
		
		if (out.getStatus() != 3 && out.getStatus() != 4)
		{
			throw new MYException("库管未通过");
		}
		
		if (out.getBadDebts() > 0)
		{
			throw new MYException("存在坏账，不能操作");
		}
		
		if (out.getEventId().length() > 0 || out.getRefBindOutId().length() > 0)
		{
			throw new MYException("参与促销不能操作");
		}
		
		if (out.getInvoiceMoney() > 0) {
			throw new MYException("原单已开过发票，请先退票，再做空开空退.");
		}
		
        ConditionParse con = new ConditionParse();
		
        if (check)
        {
        	// 是否有未审批结束的空开空退申请
    		con.clear();
    		
    		con.addWhereStr();
    		
    		con.addCondition("OutRepaireBean.outId", "=", out.getFullId());
    		
    		con.addIntCondition("OutRepaireBean.status", "=", OutConstant.OUT_REPAIRE_PASS);
    		
            int count = outRepaireDAO.countByCondition(con.toString());

            if (count > 0)
            {
            	throw new MYException("存在其它关联单据，请确认");
            }
        }
		
        // 查询是否被关联
		con.clear();
		
        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", out.getFullId());
      
        int count0 = outDAO.countByCondition(con.toString());

        if (count0 > 0)
        {
        	throw new MYException("存在其它关联单据，请确认");
        }
		
        // 收款申请中存在未结束的申请
        int count1 = paymentApplyDAO.countApplyByOutId2(out.getFullId());
        
        if (count1 > 0)
        {
        	throw new MYException("存在收款申请审批中，不能操作");
        }
        
		// 存在开票审批中的，status in (1,2)
		double invoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(out.getFullId());
		
		if (invoice > 0)
		{
			throw new MYException("存在开票申请中，不能操作");
		}
		
		return true;
	}
    
    /**
     * 获取TAB页任务列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTabOrder(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
			{
				CommonTools.saveParamers(request);
				
				ConditionParse condtion = new ConditionParse();
				
//				condtion.addCondition("AgreementBean.taskStatus", "=", ProjectConstant.TASK_STAGE_TWO);
				
//				condtion.addWhereStr();
//				List<AgreementBean> list = this.agreementDAO.queryEntityBeansByCondition(condtion.toString());
//				List<OutVO> list = this.outDAO.listEntityVOs();
				request.setAttribute("beanList", new ArrayList<OutVO>());
				
				return mapping.findForward("queryTabOrder");
			}
    
    
    /**
     * 查询配送信息
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findDistribution(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
    	String id = request.getParameter("id");

    	DistributionVO distributionVO = distributionDAO.findVO(id);
    	
    	if (null == distributionVO)
    	{
    		request.setAttribute(KeyConstant.MESSAGE, "【配送信息未填写】");
    	}
    	
    	List<BaseVO> baseList = null; 

    	List<BaseBean> bbaseList = baseDAO.queryBaseByDistribution(id);

    	if (ListTools.isEmptyOrNull(bbaseList))
    		baseList = baseDAO.queryEntityVOsByFK(distributionVO.getOutId());
    	else
    	{
    		baseList = new ArrayList<BaseVO>();
    		
    		for(BaseBean each : bbaseList)
    		{
    			BaseVO vo = new BaseVO();
    			
    			BeanUtil.copyProperties(vo, each);
    			
    			DepotBean dbean = depotDAO.find(each.getLocationId());
    			
    			if (null != dbean)
    			{
    				vo.setDepotName(dbean.getName());
    			}
    			
    			baseList.add(vo);
    		}
    	}
    	
    	// 获取发货单号及快递公司
    	ConsignBean consign = consignDAO.findDefaultConsignByDistId(id);
    	
    	if (null != consign) {
    		distributionVO.setTransport(consign.getTransport());
    		distributionVO.setTransportNo(consign.getTransportNo());
    	}
    	
    	request.setAttribute("baseList", baseList);
    	
    	request.setAttribute("distributionBean", distributionVO);
    	
    	OutVO outVO = outDAO.findVO(distributionVO.getOutId());
    	
    	request.setAttribute("outBean", outVO);
    	
    	return mapping.findForward("detailDistribution");
    
    }
    /***
     * 打印出库单
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward printOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
    	String id = request.getParameter("id");
    	String outId = request.getParameter("fullId");
    	
    	DistributionVO distributionVO = distributionDAO.findVO(id);

    	if (null != distributionVO)
    	{
    		distributionVO.setAddress(distributionVO.getProvinceName() +" "+ distributionVO.getCityName()+" "+ distributionVO.getAreaName()+" " + distributionVO.getAddress());
    		
    		distributionVO.setShippingName(OutHelper.getShippingName(distributionVO.getShipping()));
    		
    		distributionVO.setPrintCount(distributionVO.getPrintCount() + 1);
    	}
    	
    	List<BaseVO> baseList = null; 

    	List<BaseBean> bbaseList = baseDAO.queryBaseByDistribution(id);

    	if (ListTools.isEmptyOrNull(bbaseList))
    		baseList = baseDAO.queryEntityVOsByFK(distributionVO.getOutId());
    	else
    	{
    		baseList = new ArrayList<BaseVO>();
    		
    		for(BaseBean each : bbaseList)
    		{
    			BaseVO vo = new BaseVO();
    			
    			BeanUtil.copyProperties(vo, each);
    			
    			DepotBean dbean = depotDAO.find(each.getLocationId());
    			
    			if (null != dbean)
    			{
    				vo.setDepotName(dbean.getName());
    			}
    			
    			baseList.add(vo);
    		}
    	}
    	
    	request.setAttribute("baseList", baseList);
    	
    	request.setAttribute("distributionBean", distributionVO);
    	
    	OutVO outVO = outDAO.findVO(outId);
    	
    	request.setAttribute("out", outVO);
    	
        request.setAttribute("year", TimeTools.now("yyyy"));
        request.setAttribute("month", TimeTools.now("MM"));
        request.setAttribute("day", TimeTools.now("dd"));

        return mapping.findForward("print1");
    
    }
    
    /**
     * 更新配送打印次数
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateDistPrintCount(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
    {
    	String id = request.getParameter("id");
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	try{
    		outManager.updateDistPrintCount(id);
    		
    		ajax.setSuccess("更新成功");
    	}catch(MYException e){
    		_logger.warn(e, e);
    		
    		ajax.setError("更新失败");
    	}
    	
    	return JSONTools.writeResponse(reponse, ajax);
    }
    
    /**
     * App 订单查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryAppOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYAPPOUT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPOUT, request,
            condtion, this.appOutDAO, new HandleResult<AppOutVO>()
			{
				public void handle(AppOutVO vo)
				{
					List<AppOutVSOutBean> vsOutList = appOutVSOutDAO.queryEntityBeansByFK(vo.getOrderNo());
					
					for (AppOutVSOutBean each : vsOutList)
					{
						vo.setRefOutId(each.getOutId() + ";" + vo.getRefOutId());
					}
				}
			});

        return JSONTools.writeResponse(reponse, jsonstr);
    }

    /**
     * 查询可结算的委托单
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptCanSettle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	CommonTools.saveParamers(request);
    	
    	// 查询界面上的条件
    	String name = request.getParameter("name");
    	
    	String fullId = request.getParameter("fullId");
    	
    	String stafferId = request.getParameter("stafferId");
    	
    	String customerId = request.getParameter("customerId");
    	
    	if (StringTools.isNullOrNone(name) && StringTools.isNullOrNone(fullId))
    		return mapping.findForward("rptCanSettle");
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addCondition("OutBean.stafferId", "=", stafferId);
    	con.addCondition("OutBean.customerId", "=", customerId);
    	con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
    	con.addCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_CONSIGN);
    	con.addCondition(" and OutBean.status in (3,4)");
    	
    	if (!StringTools.isNullOrNone(fullId))
    	{
    		con.addCondition("OutBean.fullId", "like", fullId);	
    	}
    	
    	if (!StringTools.isNullOrNone(name))
    	{
    		con.addCondition("BaseBean.productName", "like", name);
    	}
    	
    	List<BaseBean> baseList = baseDAO.queryBaseByOneCondition(con);
    	
    	//去除已结算部分
    	for (Iterator<BaseBean> iterator = baseList.iterator(); iterator.hasNext();)
    	{
    		BaseBean baseBean = iterator.next();
    		
    		int hasPass = baseBalanceDAO.sumPassBaseBalance(baseBean.getId());

			baseBean.setInway(hasPass);
			
			int last = baseBean.getAmount() - baseBean.getInway();

			if (last <= 0)
				iterator.remove();
    	}
    	
    	request.setAttribute("baseList", baseList);
    	
    	return mapping.findForward("rptCanSettle");
    }
    
    /**
     * batchSettleOut
     * 批量生成结算单
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward batchSettleOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	User user = Helper.getUser(request);

		//暂不用
		//String outType = request.getParameter("outType");
		String [] outids = request.getParameterValues("p_outid");
		String [] baseids = request.getParameterValues("p_baseId");
		String [] amounts = request.getParameterValues("p_amount");
		String [] prices = request.getParameterValues("p_price");

		List<OutBalanceBean> list = new ArrayList<OutBalanceBean>();
		
		Map<String,List<BaseBalanceBean>> map = new HashMap<String,List<BaseBalanceBean>>();
		
		// 分组 ：一个委托单生成一个结算单
		for (int i = 0; i < outids.length; i++)
		{
			String key = outids[i];
			
			BaseBalanceBean baseBalanceBean = new BaseBalanceBean();
			
			baseBalanceBean.setOutId(outids[i]);
			baseBalanceBean.setBaseId(baseids[i]);
			baseBalanceBean.setAmount(MathTools.parseInt(amounts[i]));
			baseBalanceBean.setSailPrice(MathTools.parseDouble(prices[i]));
			
			if (map.containsKey(key))
			{
				List<BaseBalanceBean> blist = map.get(key);
				
				blist.add(baseBalanceBean);
			}else{
				List<BaseBalanceBean> blist = new ArrayList<BaseBalanceBean>();
				
				blist.add(baseBalanceBean);
				
				map.put(key, blist);
			}
		}
		
		try
		{
			for(Entry<String, List<BaseBalanceBean>> entry : map.entrySet())
			{
				String outId = entry.getKey();
				
				List<BaseBalanceBean> basebList = entry.getValue();
				
				setOutBalanceBean(outId, basebList, list, request);
			}
		
			outManager.batchSettleOut(user, list);
			
			request.setAttribute(KeyConstant.MESSAGE, "批量操作成功");
		}catch(MYException e)
		{
			_logger.warn(e, e);
			
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量操作失败."+ e.getErrorContent());
		}
		
		return mapping.findForward("batchSettleOut");
    }
    
    private void setOutBalanceBean(String outId, List<BaseBalanceBean> bList, List<OutBalanceBean> list, HttpServletRequest request)
    throws MYException
    {
    	OutBalanceBean bean = new OutBalanceBean();
    	
		User user = (User) request.getSession().getAttribute("user");

		String description = request.getParameter("description");

		OutBean out = outDAO.find(outId);

		if (out == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		bean.setCustomerId(out.getCustomerId());

		bean.setDescription(description+"(批量)");

		bean.setLogTime(TimeTools.now());

		bean.setStafferId(user.getStafferId());

		bean.setOutId(outId);

		bean.setDirDepot("");

		bean.setStatus(OutConstant.OUTBALANCE_STATUS_SUBMIT);

		bean.setType(OutConstant.OUTBALANCE_TYPE_SAIL);
		
		bean.setIndustryId(out.getIndustryId());

		// 商务
        User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
        
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            throw new MYException("登陆异常,请重新登陆");
        }
        
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
        {
        	bean.setOperator(g_srcUser.getStafferId());
        	bean.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	bean.setOperator(user.getStafferId());
        	bean.setOperatorName(user.getStafferName());
        }
        // 商务 - end
		
		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();

		double total = 0.0d;
		for (BaseBalanceBean each : bList)
		{
			BaseBalanceBean basebBean = new BaseBalanceBean();

			basebBean.setBaseId(each.getBaseId());
			basebBean.setAmount(each.getAmount());
			basebBean.setOutId(outId);
			basebBean.setSailPrice(each.getSailPrice());

			total += each.getAmount() * each.getSailPrice();

			baseBalanceList.add(each);
		}

		bean.setTotal(total);

		bean.setBaseBalanceList(baseBalanceList);
		
		list.add(bean);
    }
    
    /**
     * 查询可转销售的领样数据
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptCanSwatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	CommonTools.saveParamers(request);
    	
    	// 查询界面上的条件
    	String name = request.getParameter("name");
    	
    	String fullId = request.getParameter("fullId");
    	
    	String stafferId = request.getParameter("stafferId");
    	
    	String customerId = request.getParameter("customerId");
    	
    	if (StringTools.isNullOrNone(name) && StringTools.isNullOrNone(fullId))
    		return mapping.findForward("rptCanSwatch");
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addCondition("OutBean.stafferId", "=", stafferId);
    	con.addCondition("OutBean.customerId", "=", customerId);
    	con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
    	con.addCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_SWATCH);
    	con.addCondition(" and OutBean.status in (3,4)");
    	
    	if (!StringTools.isNullOrNone(fullId))
    	{
    		con.addCondition("OutBean.fullId", "like", fullId);	
    	}
    	
    	if (!StringTools.isNullOrNone(name))
    	{
    		con.addCondition("BaseBean.productName", "like", name);
    	}
    	
    	List<BaseBean> baseList = new ArrayList<BaseBean>(); 
    	
    	List<OutBean> outList = outDAO.queryOutByOneCondition(con);
    	
    	ConditionParse con1 = new ConditionParse();
    	
    	//去除已转算及已退货部分
    	for (OutBean out : outList)
    	{
    		List<BaseBean> bList = baseDAO.queryEntityBeansByFK(out.getFullId());
    		
    		// 查询已转的销售
            con1.clear();

            con1.addWhereStr();

            con1.addCondition("OutBean.refOutFullId", "=", out.getFullId());

            con1.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

            List<OutBean> refList = outDAO.queryEntityBeansByCondition(con1);
            
    		// 查询已退的退货
            List<OutBean> refBuyList = queryRefOut4(request, out.getFullId());
            
    		// 计算出已经退货的数量
            for (Iterator<BaseBean> iterator = bList.iterator(); iterator.hasNext();)
            {
                BaseBean baseBean = iterator.next();

                int hasBack = 0;

                // 退库
                for (OutBean ref : refBuyList)
                {
                    List<BaseBean> refBaseList = ref.getBaseList();

                    for (BaseBean refBase : refBaseList)
                    {
                        if (refBase.equals(baseBean))
                        {
                            hasBack += refBase.getAmount();

                            break;
                        }
                    }
                }

                // 转销售的
                for (OutBean ref : refList)
                {
                    List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

                    for (BaseBean refBase : refBaseList)
                    {
                        if (refBase.equals(baseBean))
                        {
                            hasBack += refBase.getAmount();

                            break;
                        }
                    }
                }

                baseBean.setInway(hasBack);

                int last = baseBean.getAmount() - baseBean.getInway();

    			if (last <= 0)
    				iterator.remove();
            }
            
            baseList.addAll(bList);
    	}
    	
    	request.setAttribute("baseList", baseList);
    	
    	return mapping.findForward("rptCanSwatch");
    }
    
    /**
     * batchSwatchOut
     * 批量转领样单,考虑到转成的销售单有一些要确认,故生成的销售单为保存态
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward batchSwatchOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	User user = Helper.getUser(request);

		//暂不用
		//String outType = request.getParameter("outType");
		String [] outids = request.getParameterValues("p_outid");
		String [] baseids = request.getParameterValues("p_baseId");
		String [] amounts = request.getParameterValues("p_amount");
		String [] prices = request.getParameterValues("p_price");

		List<OutBean> list = new ArrayList<OutBean>();
		
		Map<String,List<BaseBean>> map = new HashMap<String,List<BaseBean>>();
		
		// 分组 ：一个委托单生成一个结算单
		for (int i = 0; i < outids.length; i++)
		{
			String key = outids[i];
			
			BaseBean baseBean = new BaseBean();
			
			BaseBean obBean = baseDAO.find(baseids[i]);
			
			BeanUtil.copyProperties(baseBean, obBean);
			
			baseBean.setOutId(outids[i]);
			baseBean.setAmount(MathTools.parseInt(amounts[i]));
			baseBean.setPrice(MathTools.parseDouble(prices[i]));
			baseBean.setValue(baseBean.getAmount() * baseBean.getPrice());
			
			if (map.containsKey(key))
			{
				List<BaseBean> blist = map.get(key);
				
				blist.add(baseBean);
			}else{
				List<BaseBean> blist = new ArrayList<BaseBean>();
				
				blist.add(baseBean);
				
				map.put(key, blist);
			}
		}
		
		try
		{
			for(Entry<String, List<BaseBean>> entry : map.entrySet())
			{
				String outId = entry.getKey();
				
				List<BaseBean> basebList = entry.getValue();
				
				setOutBean(outId, basebList, list, request);
			}
		
			outManager.batchSwatchOut(user, list);
			
			request.setAttribute(KeyConstant.MESSAGE, "批量操作成功");
		}catch(MYException e)
		{
			_logger.warn(e, e);
			
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量操作失败."+ e.getErrorContent());
			
			return mapping.findForward("error");
		}
		
		request.setAttribute("load", 1);
		
		return querySelfOut(mapping, form, request, reponse);
    }
    
    private void setOutBean(String outId, List<BaseBean> bList, List<OutBean> list, HttpServletRequest request)
    throws MYException
    {
    	OutBean bean = outDAO.find(outId);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	User user = (User) request.getSession().getAttribute("user");
    	
    	String description = request.getParameter("description");
    	
    	// 生成销售单,然后保存
        OutBean newOut = new OutBean();

        newOut.setOutTime(TimeTools.now_short());
        newOut.setType(OutConstant.OUT_TYPE_OUTBILL);
        newOut.setOutType(OutConstant.OUTTYPE_OUT_COMMON);
        newOut.setRefOutFullId(outId);
        newOut.setDutyId(bean.getDutyId());
        newOut.setMtype(bean.getMtype());
        newOut.setDescription("领样转销售,领样单据（批量）:" + outId + "," + description);
        newOut.setDepartment(bean.getDepartment());
        newOut.setLocation(bean.getLocation());
        newOut.setLocationId(bean.getLocationId());
        newOut.setDepotpartId(bean.getDepotpartId());
        newOut.setStafferId(bean.getStafferId());
        newOut.setStafferName(bean.getStafferName());
        
        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW)
        {
        	newOut.setCustomerId(bean.getCustomerId());
        	newOut.setCustomerName(bean.getCustomerName());
        	newOut.setConnector(bean.getConnector());
        }

        // 商务
        User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
        
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            throw new MYException("登陆异常,请重新登陆");
        }
        
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
        {
        	newOut.setOperator(g_srcUser.getStafferId());
        	newOut.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	newOut.setOperator(user.getStafferId());
        	newOut.setOperatorName(user.getStafferName());
        }
        // 商务 - end
        
        newOut.setBaseList(bList);
        
        list.add(newOut);
    }
    
    /**
     * 查询要参加的排序的销售(销售出库 - 中信订单发货优先级)
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward statsRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	User user  = Helper.getUser(request); 
    		
    	CommonTools.saveParamers(request);
    	
    	String customerId = request.getParameter("customerId");
    	
    	String outTime = request.getParameter("outTime");
    	
    	String outTime1 = request.getParameter("outTime1");
    	
    	String batchId = request.getParameter("batchId");
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	// 假设条件均有值
    	if (!StringTools.isNullOrNone(customerId))
    		con.addCondition("OutBean.customerId", "=", customerId);
    	
    	con.addCondition("OutBean.outTime", ">=", outTime);
    	
    	con.addCondition("OutBean.outTime", "<=", outTime1);
    	
    	con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
    	//
    	con.addIntCondition("OutBean.outtype", "=", OutConstant.OUTTYPE_OUT_COMMON);

    	con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SUBMIT);
    	
    	con.addCondition("OutBean.flowId", "=", OutImportConstant.CITIC);
    	
    	List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);
    	
    	String newBatchId = "";
    	
    	if (ListTools.isEmptyOrNull(outList))
    	{
    		return mapping.findForward("statsRank");
    	}
    	
    	try{
    		
    		newBatchId = outManager.statsAndAddRank(user, outList, batchId);

    		request.setAttribute("newBatchId", newBatchId);
    		
    		request.setAttribute("newFlag", "0");
    		
    		return queryStatsRank(mapping, form, request, reponse);
    		
    	}catch(MYException e){
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询出错:" + e.getErrorContent());
    	}
    	
    	return mapping.findForward("statsRank");
    }
    
    /**
     * queryStatsRank
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryStatsRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	CommonTools.saveParamers(request);
    	
    	String batchId = RequestTools.getValueFromRequest(request, "newBatchId");
    	
    	if (StringTools.isNullOrNone(batchId))
    		batchId = request.getParameter("batchId");
    	
    	String flag = RequestTools.getValueFromRequest(request, "newFlag");
    	
    	if (StringTools.isNullOrNone(flag))
    		flag = request.getParameter("flag");
    	
    	List<StatsDeliveryRankBean> list = null;
    	
		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = new ConditionParse();
				
				condtion.addWhereStr();
				
				condtion.addCondition("StatsDeliveryRankBean.batchId", "=", batchId);
				
				condtion.addCondition(" order by StatsDeliveryRankBean.rank");
				
				int tatol = statsDeliveryRankDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSTATSRANK);

				list = statsDeliveryRankDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYSTATSRANK);

				list = statsDeliveryRankDAO.queryEntityVOsByCondition(OldPageSeparateTools
						.getCondition(request, QUERYSTATSRANK),
						OldPageSeparateTools.getPageSeparate(request,
								QUERYSTATSRANK));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("listOut", list);
		
		request.setAttribute("batchId", batchId);
		
		request.setAttribute("flag", flag);
		
		return mapping.findForward("queryStatsRank");
    }
    
    /**
     * updateStatsRank
     * ajax 动态更新
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateStatsRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	AjaxResult ajax = new AjaxResult();
    	
    	String id = request.getParameter("id");
    	
    	String type = request.getParameter("type");
    	
    	String value = request.getParameter("value");
    	
    	try{
    		outManager.updateStatsRank(id, type, value);
    		
    		ajax.setSuccess("更新成功");
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		ajax.setError("更新出错:" + e.getErrorContent());
    	}
    	
    	return JSONTools.writeResponse(reponse, ajax);
    }
    
    /**
     * sortsRank 排序
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward sortsRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	CommonTools.saveParamers(request);
    	
    	String batchId = request.getParameter("batchId");
    	
    	User user = Helper.getUser(request);
    	
    	try{
    		outManager.sortsRank(user, batchId);
    	}catch(MYException e)
    	{
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "排序处理失败." + e.getErrorContent());

			_logger.error(e, e);

			return mapping.findForward("error");
    	}
    	
		request.setAttribute("newBatchId", batchId);
		
		request.setAttribute("newFlag", "1");
		
    	return queryStatsRank(mapping, form, request, reponse);
    }
    
    /**
     * passSortRank
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward passSortRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String batchId = request.getParameter("batchId");
    	
    	User user = Helper.getUser(request);
    	
    	String result = "";
    	
    	try{
    		
    		result = outManager.passSortRank(user, batchId);
    		
    		if (StringTools.isNullOrNone(result))
    			result = "成功处理";
    		
    		request.setAttribute(KeyConstant.MESSAGE, "处理结果:" + result);
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "出错:" + e.getErrorContent());
    		
    		request.setAttribute("newBatchId", batchId);
    		
    		request.setAttribute("newFlag", "1");
    		
        	return queryStatsRank(mapping, form, request, reponse);
    	}
    	
    	return mapping.findForward("statsRank");
    }
    
    /**
     * rejectSortRank
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward undoSortRank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String batchId = request.getParameter("batchId");
    	
    	try{
    		
    		outManager.undoSortRank(batchId);
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    	}
    	
    	return mapping.findForward("statsRank");
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryDistributionByOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String outId = request.getParameter("outId");
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	List<DistributionBean> list = distributionDAO.queryEntityBeansByFK(outId);
    	
    	ajax.setSuccess(list);
    	
    	return JSONTools.writeResponse(reponse, ajax);
    }
    
    /**
     * querForConfirmRetIns
     * 查询需要确认发票的退货单 - 不分页
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryForConfirmRetIns(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String invoiceId = request.getParameter("invoiceId");
    	
    	String invoiceName = request.getParameter("invoiceName");
    	
    	String providerId = request.getParameter("providerId");
    	
    	String providerName = request.getParameter("providerName");
    	
    	double total = MathTools.parseDouble(request.getParameter("total"));
    	
    	User user = Helper.getUser(request);
    	
    	CommonTools.saveParamers(request);
    	
    	ConditionParse condtion1 = new ConditionParse();

    	condtion1.addWhereStr();
		
		//condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());
		
    	condtion1.addCondition("OutBean.invoiceId", "=", invoiceId);
    	
    	condtion1.addCondition("OutBean.customerId", "=", providerId);

    	condtion1.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
    	condtion1.addIntCondition("OutBean.outtype", "=", OutConstant.OUTTYPE_IN_OUTBACK);
    	condtion1.addCondition(" and OutBean.status in (3,4)");
    	condtion1.addIntCondition("OutBean.hasConfirm", "=", 0);
    	
    	getQueryConfirmInsCondition1(condtion1,request,user);
    	
    	ConditionParse condtion = getQueryConfirmInsCondition2(request,
				user);
    	
    	List<ConfirmInsWrap> wrapList = outDAO.queryCanConfirmIn(condtion1);
    	
    	wrapList.addAll(outBalanceDAO.queryCanConfirmBalance(condtion));
    	
    	request.setAttribute("wrapList", wrapList);
    	
    	request.setAttribute("invoiceId", invoiceId);
    	request.setAttribute("total", total);
    	request.setAttribute("invoiceName", invoiceName);
    	
    	request.setAttribute("providerId", providerId);
    	request.setAttribute("providerName", providerName);
    	
    	request.setAttribute("type", "0");
    	
    	return mapping.findForward("refConfirmInvoice");
    }
    
    /**
     * queryForConfirmOutIns
     * 采购入库 - 分页
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryForConfirmOutIns(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String invoiceId = request.getParameter("invoiceId");
    	
    	String invoiceName = request.getParameter("invoiceName");
    	
    	String providerId = request.getParameter("providerId");
    	
    	String providerName = request.getParameter("providerName");
    	
    	double total = MathTools.parseDouble(request.getParameter("total"));
    	
    	User user = Helper.getUser(request);
    	
    	List<OutVO> list = null;

		CommonTools.saveParamers(request);
		
		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = new ConditionParse();

				condtion.addWhereStr();
				
				//condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());
				
				condtion.addCondition("OutBean.invoiceId", "=", invoiceId);

				condtion.addCondition("OutBean.customerId", "=", providerId);
				
				condtion.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
				condtion.addIntCondition("OutBean.outtype", "=", OutConstant.OUTTYPE_IN_COMMON);
				condtion.addCondition(" and OutBean.status in (3,4)");
				condtion.addIntCondition("OutBean.hasConfirm", "=", 0);
				
				getQueryConfirmInsCondition1(condtion,request, user);

				int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSELFCONFIRMOUT);

				list = outDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request,
						QUERYSELFCONFIRMOUT);

				list = outDAO.queryEntityVOsByCondition(
						OldPageSeparateTools.getCondition(request,
								QUERYSELFCONFIRMOUT), OldPageSeparateTools
								.getPageSeparate(request, QUERYSELFCONFIRMOUT));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		for (OutVO each : list)
		{
			each.setMayConfirmMoney(each.getTotal() - each.getHasConfirmInsMoney());
		}
		
    	request.setAttribute("wrapList", list);
    	
    	request.setAttribute("invoiceId", invoiceId);
    	request.setAttribute("total", total);
    	request.setAttribute("invoiceName", invoiceName);
    	
    	request.setAttribute("providerId", providerId);
    	request.setAttribute("providerName", providerName);
    	
    	request.setAttribute("type", "1");
    	
    	return mapping.findForward("refConfirmInvoice");
    }
    
    private void getQueryConfirmInsCondition1(ConditionParse condtion, HttpServletRequest request, User user)
	{
		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", ">=",
					TimeTools.now_short(-30));

			request.setAttribute("outTime", TimeTools.now_short(-30));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", "<=",
					TimeTools.now_short(1));

			request.setAttribute("outTime1", TimeTools.now_short(1));
		}

		String outId = request.getParameter("outId");

		if (!StringTools.isNullOrNone(outId))
		{
			condtion.addCondition("OutBean.fullId", "like", outId);
		}

		condtion.addCondition("order by OutBean.outTime desc");

		return;
	}
    
    private ConditionParse getQueryConfirmInsCondition2(HttpServletRequest request, User user)
	{
		ConditionParse condtion = new ConditionParse();

		//condtion.addWhereStr();

		//condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());
		
		String invoiceId = request.getParameter("invoiceId");
		
		String providerId = request.getParameter("providerId");
		
		condtion.addCondition("OutBean.invoiceId", "=", invoiceId);
		
		condtion.addCondition("OutBean.customerId", "=", providerId);
		
		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBalanceBean.logTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("OutBalanceBean.logTime", ">=",
					TimeTools.now_short(-30));

			request.setAttribute("outTime", TimeTools.now_short(-30));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBalanceBean.logTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("OutBalanceBean.logTime", "<=",
					TimeTools.now_short(1));

			request.setAttribute("outTime1", TimeTools.now_short(1));
		}

		String outId = request.getParameter("outId");

		if (!StringTools.isNullOrNone(outId))
		{
			condtion.addCondition("OutBean.fullId", "like", outId);
		}

		condtion.addCondition("order by OutBalanceBean.logTime desc");

		return condtion;
	}
    
    /**
     * queryPurchaseBack
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPurchaseBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) 
    throws ServletException
    {
    	String customerId = request.getParameter("customerId");
    	
    	String customerName = request.getParameter("customerName");
    	
    	String total = request.getParameter("total");
    	
    	User user = Helper.getUser(request);
    	
    	List<OutVO> list = null;

		CommonTools.saveParamers(request);
		
		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = new ConditionParse();

				condtion.addWhereStr();
				
				condtion.addCondition("OutBean.customerId", "=", customerId);

				condtion.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
				condtion.addIntCondition("OutBean.outtype", "=", OutConstant.OUTTYPE_IN_STOCK);
				condtion.addCondition(" and OutBean.status in (3,4)");
				condtion.addIntCondition("OutBean.hadpay", "=", 0);
				
				getQueryConfirmInsCondition1(condtion,request, user);
				
				int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSELFCONFIRMOUT);

				list = outDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request,
						QUERYSELFCONFIRMOUT);

				list = outDAO.queryEntityVOsByCondition(
						OldPageSeparateTools.getCondition(request,
								QUERYSELFCONFIRMOUT), OldPageSeparateTools
								.getPageSeparate(request, QUERYSELFCONFIRMOUT));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

    	request.setAttribute("itemList", list);
    	
    	request.setAttribute("customerId", customerId);
    	request.setAttribute("customerName", customerName);
    	request.setAttribute("total", total);
    	
    	return mapping.findForward("refPurchaseBack");
    }
    
    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the customerMainDAO
     */
    public CustomerMainDAO getCustomerMainDAO()
    {
        return customerMainDAO;
    }

    /**
     * @param customerMainDAO
     *            the customerMainDAO to set
     */
    public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
    {
        this.customerMainDAO = customerMainDAO;
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
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the consignDAO
     */
    public ConsignDAO getConsignDAO()
    {
        return consignDAO;
    }

    /**
     * @param consignDAO
     *            the consignDAO to set
     */
    public void setConsignDAO(ConsignDAO consignDAO)
    {
        this.consignDAO = consignDAO;
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

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
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
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
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
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
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
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
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

    /**
     * @return the fatalNotify
     */
    public FatalNotify getFatalNotify()
    {
        return fatalNotify;
    }

    /**
     * @param fatalNotify
     *            the fatalNotify to set
     */
    public void setFatalNotify(FatalNotify fatalNotify)
    {
        this.fatalNotify = fatalNotify;
    }

    /**
     * @return the authManager
     */
    public AuthManager getAuthManager()
    {
        return authManager;
    }

    /**
     * @param authManager
     *            the authManager to set
     */
    public void setAuthManager(AuthManager authManager)
    {
        this.authManager = authManager;
    }

    /**
     * @return the baseBalanceDAO
     */
    public BaseBalanceDAO getBaseBalanceDAO()
    {
        return baseBalanceDAO;
    }

    /**
     * @param baseBalanceDAO
     *            the baseBalanceDAO to set
     */
    public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
    {
        this.baseBalanceDAO = baseBalanceDAO;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * @return the outQueryDAO
     */
    public OutQueryDAO getOutQueryDAO()
    {
        return outQueryDAO;
    }

    /**
     * @param outQueryDAO
     *            the outQueryDAO to set
     */
    public void setOutQueryDAO(OutQueryDAO outQueryDAO)
    {
        this.outQueryDAO = outQueryDAO;
    }

    /**
     * @return the stafferManager
     */
    public StafferManager getStafferManager()
    {
        return stafferManager;
    }

    /**
     * @param stafferManager
     *            the stafferManager to set
     */
    public void setStafferManager(StafferManager stafferManager)
    {
        this.stafferManager = stafferManager;
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
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the dutyVSInvoiceDAO
     */
    public DutyVSInvoiceDAO getDutyVSInvoiceDAO()
    {
        return dutyVSInvoiceDAO;
    }

    /**
     * @param dutyVSInvoiceDAO
     *            the dutyVSInvoiceDAO to set
     */
    public void setDutyVSInvoiceDAO(DutyVSInvoiceDAO dutyVSInvoiceDAO)
    {
        this.dutyVSInvoiceDAO = dutyVSInvoiceDAO;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the invoiceCreditDAO
     */
    public InvoiceCreditDAO getInvoiceCreditDAO()
    {
        return invoiceCreditDAO;
    }

    /**
     * @param invoiceCreditDAO
     *            the invoiceCreditDAO to set
     */
    public void setInvoiceCreditDAO(InvoiceCreditDAO invoiceCreditDAO)
    {
        this.invoiceCreditDAO = invoiceCreditDAO;
    }

    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO()
    {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO
     *            the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
    {
        this.principalshipDAO = principalshipDAO;
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

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the insVSOutDAO
     */
    public InsVSOutDAO getInsVSOutDAO()
    {
        return insVSOutDAO;
    }

    /**
     * @param insVSOutDAO
     *            the insVSOutDAO to set
     */
    public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
    {
        this.insVSOutDAO = insVSOutDAO;
    }

    /**
     * @return the sailConfigDAO
     */
    public SailConfigDAO getSailConfigDAO()
    {
        return sailConfigDAO;
    }

    /**
     * @param sailConfigDAO
     *            the sailConfigDAO to set
     */
    public void setSailConfigDAO(SailConfigDAO sailConfigDAO)
    {
        this.sailConfigDAO = sailConfigDAO;
    }

    /**
     * @return the sailManager
     */
    public SailManager getSailManager()
    {
        return sailManager;
    }

    /**
     * @param sailManager
     *            the sailManager to set
     */
    public void setSailManager(SailManager sailManager)
    {
        this.sailManager = sailManager;
    }

    public ProvinceDAO getProvinceDAO() {
        return provinceDAO;
    }

    public void setProvinceDAO(ProvinceDAO provinceDAO) {
        this.provinceDAO = provinceDAO;
    }

    public CityDAO getCityDAO() {
        return cityDAO;
    }

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public AreaDAO getAreaDAO() {
        return areaDAO;
    }

    public void setAreaDAO(AreaDAO areaDAO) {
        this.areaDAO = areaDAO;
    }

    public StafferVSCustomerDAO getStafferVSCustomerDAO() {
        return stafferVSCustomerDAO;
    }

    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO) {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
    }

    public PromotionDAO getPromotionDAO() {
        return promotionDAO;
    }

    public void setPromotionDAO(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    public PromotionItemDAO getPromotionItemDAO() {
        return promotionItemDAO;
    }

    public void setPromotionItemDAO(PromotionItemDAO promotionItemDAO) {
        this.promotionItemDAO = promotionItemDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public PaymentApplyDAO getPaymentApplyDAO() {
        return paymentApplyDAO;
    }

    public void setPaymentApplyDAO(PaymentApplyDAO paymentApplyDAO) {
        this.paymentApplyDAO = paymentApplyDAO;
    }

    public CommonMailManager getCommonMailManager() {
        return commonMailManager;
    }

    public void setCommonMailManager(CommonMailManager commonMailManager) {
        this.commonMailManager = commonMailManager;
    }
    
	public InvoiceinsItemDAO getInvoiceinsItemDAO()
	{
		return invoiceinsItemDAO;
	}

	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
	{
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}

	public OutRepaireDAO getOutRepaireDAO()
	{
		return outRepaireDAO;
	}

	public void setOutRepaireDAO(OutRepaireDAO outRepaireDAO)
	{
		this.outRepaireDAO = outRepaireDAO;
	}

	public BaseRepaireDAO getBaseRepaireDAO()
	{
		return baseRepaireDAO;
	}

	public void setBaseRepaireDAO(BaseRepaireDAO baseRepaireDAO)
	{
		this.baseRepaireDAO = baseRepaireDAO;
	}
	
	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public ExpressDAO getExpressDAO()
	{
		return expressDAO;
	}

	public void setExpressDAO(ExpressDAO expressDAO)
	{
		this.expressDAO = expressDAO;
	}

	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	/**
	 * @return the appOutDAO
	 */
	public AppOutDAO getAppOutDAO()
	{
		return appOutDAO;
	}

	/**
	 * @param appOutDAO the appOutDAO to set
	 */
	public void setAppOutDAO(AppOutDAO appOutDAO)
	{
		this.appOutDAO = appOutDAO;
	}

	/**
	 * @return the appOutVSOutDAO
	 */
	public AppOutVSOutDAO getAppOutVSOutDAO()
	{
		return appOutVSOutDAO;
	}

	/**
	 * @param appOutVSOutDAO the appOutVSOutDAO to set
	 */
	public void setAppOutVSOutDAO(AppOutVSOutDAO appOutVSOutDAO)
	{
		this.appOutVSOutDAO = appOutVSOutDAO;
	}

	/**
	 * @return the statsDeliveryRankDAO
	 */
	public StatsDeliveryRankDAO getStatsDeliveryRankDAO()
	{
		return statsDeliveryRankDAO;
	}

	/**
	 * @param statsDeliveryRankDAO the statsDeliveryRankDAO to set
	 */
	public void setStatsDeliveryRankDAO(StatsDeliveryRankDAO statsDeliveryRankDAO)
	{
		this.statsDeliveryRankDAO = statsDeliveryRankDAO;
	}

	public PreInvoiceVSOutDAO getPreInvoiceVSOutDAO()
	{
		return preInvoiceVSOutDAO;
	}

	public void setPreInvoiceVSOutDAO(PreInvoiceVSOutDAO preInvoiceVSOutDAO)
	{
		this.preInvoiceVSOutDAO = preInvoiceVSOutDAO;
	}
}
