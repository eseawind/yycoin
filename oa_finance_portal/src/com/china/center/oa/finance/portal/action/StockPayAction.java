/**
 * File Name: StockPayAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.portal.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InvoiceStorageDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.vo.StockPayApplyVO;
import com.china.center.oa.finance.vo.StockPrePayApplyVO;
import com.china.center.oa.finance.vs.StockPayVSPreBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StockPayAction
 * 
 * @author ZHUZHU
 * @version 2011-2-18
 * @see StockPayAction
 * @since 3.0
 */
public class StockPayAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    protected CommonMailManager commonMailManager = null;
    
    private StafferDAO stafferDAO = null;

    private StockPayApplyDAO stockPayApplyDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BankDAO bankDAO = null;

    private OutBillDAO outBillDAO = null;

    private UserManager userManager = null;

    private FinanceFacade financeFacade = null;
    
    private RoleAuthDAO   roleAuthDAO   = null;
    
    private StockPrePayApplyDAO stockPrePayApplyDAO = null;
    
    private InvoiceDAO invoiceDAO = null;
    
    private InvoiceStorageDAO invoiceStorageDAO = null;

    private static final String QUERYSTOCKPAYAPPLY = "queryStockPayApply";

    private static final String QUERYCEOSTOCKPAYAPPLY = "queryCEOStockPayApply";
    
    private static final String QUERYSTOCKPREPAYAPPLY = "queryStockPrePayApply";

    private static final String QUERYCEOSTOCKPREPAYAPPLY = "queryCEOStockPrePayApply";    

    /**
     * queryStockPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStockPayApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
    	User user = Helper.getUser(request);
    	
    	String roleid = user.getRoleId();

    	List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("131801"))
        		{
        			flag = flag + 131801;
        			
        		}
        		if(rab.getAuthId().trim().equals("131802"))
        		{
        			flag = flag + 131802;
        		}
        		
        	}
        }
        
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        if(flag == 131801)//只展示终端事业部
    	{

           condtion.addCondition("StafferBean.industryId", "=", "5111658");
    	}
    	if(flag == 131802)//只展示非终端事业部
    	{
            condtion.addCondition("StafferBean.industryId", "<>", "5111658");
    	}

        Map<String, String> initMap = initLogTime(request, condtion);

        ActionTools.processJSONDataQueryCondition(QUERYSTOCKPAYAPPLY, request, condtion, initMap);

        condtion.addCondition("order by StockPayApplyBean.payDate desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTOCKPAYAPPLY, request,
            condtion, this.stockPayApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    private Map<String, String> initLogTime(HttpServletRequest request, ConditionParse condtion)
    {
        Map<String, String> changeMap = new HashMap<String, String>();

        String alogTime = request.getParameter("alogTime");

        String blogTime = request.getParameter("blogTime");

        if (StringTools.isNullOrNone(alogTime) && StringTools.isNullOrNone(blogTime))
        {
            changeMap.put("blogTime", TimeTools.now_short());

            condtion.addCondition("StockPayApplyBean.payDate", "<=", TimeTools.now_short());
            
            condtion.addCondition("StockPayApplyBean.stafferId", "=", Helper.getUser(request).getStafferId());
        }

        return changeMap;
    }
    
    private Map<String, String> initLogTime2(HttpServletRequest request, ConditionParse condtion)
    {
        Map<String, String> changeMap = new HashMap<String, String>();

        String stafferName = request.getParameter("stafferName");

        if (StringTools.isNullOrNone(stafferName))
        {
            //condtion.addCondition("StockPrePayApplyBean.payDate", "<=", TimeTools.now_short());
            
            condtion.addCondition("StockPrePayApplyBean.stafferId", "=", Helper.getUser(request).getStafferId());
        }

        return changeMap;
    }

    /**
     * queryCEOStockPayApply(过程中审批通过的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCEOStockPayApply(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCEOSTOCKPAYAPPLY, request, condtion);

        String mode = RequestTools.getValueFromRequest(request, "mode");

        if ("1".equals(mode))
        {
            condtion.addIntCondition("StockPayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CEO);
        }
        else if ("2".equals(mode))
        {
            condtion.addIntCondition("StockPayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_SAIL);
        }
        else if ("3".equals(mode))
        {
            condtion.addIntCondition("StockPayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CHECK);
        }
        else if ("4".equals(mode))
        {
            condtion.addIntCondition("StockPayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CFO);
        }
        else if ("5".equals(mode))
        {
            User user = Helper.getUser(request);

            if ( !userManager.containAuth(user.getId(), AuthConstant.BILL_QUERY_ALL))
            {
                condtion.addCondition("StockPayApplyBean.locationId", "=", user.getLocationId());
            }

            condtion.addIntCondition("StockPayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_SEC);
        }
        else
        {
            condtion.addFlaseCondition();
        }

        condtion.addCondition("order by StockPayApplyBean.payDate");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCEOSTOCKPAYAPPLY, request,
            condtion, this.stockPayApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 告警列表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryWarnStockPayApply(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("and StockPayApplyBean.status in (0, 1) ");

        condtion.addCondition("StockPayApplyBean.payDate", "=", TimeTools.now_short());

        condtion.addCondition("order by StockPayApplyBean.payDate desc");

        List<StockPayApplyVO> warnList = stockPayApplyDAO.queryEntityVOsByCondition(condtion);

        request.setAttribute("warnList", warnList);

        return mapping.findForward("queryWarnStockPayApply");
    }
    
    /**
     * 首页 总裁审批 列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryWarnCEOStockPayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();
		
		condtion.addCondition("and StockPayApplyBean.status = 2 ");
		
		condtion.addCondition("order by StockPayApplyBean.payDate desc");
		
		List<StockPayApplyVO> warnCEOList = stockPayApplyDAO.queryEntityVOsByCondition(condtion);
		
		request.setAttribute("warnCEOList", warnCEOList);
		
		condtion.clear();
		
		condtion.addCondition("and StockPrePayApplyBean.status = 2 ");
		
		condtion.addCondition("order by StockPrePayApplyBean.payDate desc");
		
		List<StockPrePayApplyVO> warnCEOList2 = stockPrePayApplyDAO.queryEntityVOsByCondition(condtion);
		
		request.setAttribute("warnCEOList2", warnCEOList2);
		
		return mapping.findForward("queryWarnCEOStockPayApply");
	}

    /**
     * findStockPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findStockPayApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        //String id = request.getParameter("id");
        String id = RequestTools.getValueFromRequest(request, "id");

        //String update = request.getParameter("update");
        String update = RequestTools.getValueFromRequest(request, "update");

        String mode = request.getParameter("mode");
        
        String ifRef = request.getParameter("ifRef");
        
        StockPayApplyVO bean = stockPayApplyDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);
        
        List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

        request.setAttribute("loglist", loglist);

        if ("1".equals(update) && bean.getStatus() != StockPayApplyConstant.APPLY_STATUS_END)
        {
            if (TimeTools.now_short().compareTo(bean.getPayDate()) >= 0)
            {
                if ("2".equals(mode))
                {
                    List<BankBean> bankList = bankDAO.queryEntityBeansByFK(bean.getDutyId());

                    request.setAttribute("bankList", bankList);
                }
                
                if (bean.getStatus() == StockPayApplyConstant.APPLY_STATUS_SEC && StringTools.isNullOrNone(ifRef))
                {
                	// 检查是否有预付款(供应商\发票类型\结束状态)
                	List<StockPrePayApplyVO> preList = stockPrePayApplyDAO.queryVOsByProviderAndInvoiceId(bean.getProvideId(), bean.getInvoiceId());
                	
                	if (!ListTools.isEmptyOrNull(preList))
                	{
                		request.setAttribute("stockPreList", preList);
                		
                		return mapping.findForward("refStockPrePay");
                	}
                }

                return mapping.findForward("handleStockPayApply");
            }
            else
            {
                return ActionTools.toError("付款的最早时间还没有到", "queryStockPayApply", mapping, request);
            }
        }

        String inBillId = bean.getInBillId();

        if ( !StringTools.isNullOrNone(inBillId))
        {
            List<OutBillBean> outList = new ArrayList<OutBillBean>();

            String[] split = inBillId.split(";");

            for (String each : split)
            {
                if ( !StringTools.isNullOrNone(each))
                {
                    OutBillBean outBill = outBillDAO.findVO(each);

                    if (outBill != null)
                    {
                        outList.add(outBill);
                    }
                }
            }

            request.setAttribute("outList", outList);
        }

        return mapping.findForward("detailStockPayApply");
    }

    /**
     * submitStockPay
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward submitStockPay(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String payMoney = request.getParameter("payMoney");

        StockPayApplyBean bean = new StockPayApplyBean();
        
        BeanUtil.getBean(bean, request);
        
        bean.setMoneys(MathTools.parseDouble(payMoney));
        
        try
        {
            User user = Helper.getUser(request);

            financeFacade.submitStockPayApply(user.getId(), id, reason, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "0");

        return mapping.findForward("queryStockPayApply");
    }

    /**
     * composeStockPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward composeStockPayApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("ids");

        try
        {
            User user = Helper.getUser(request);

            String[] splits = id.split(";");

            List<String> idList = new ArrayList<String>();

            for (String each : splits)
            {
                if ( !StringTools.isNullOrNone(each))
                {
                    idList.add(each.trim());
                }
            }

            financeFacade.composeStockPayApply(user.getId(), idList);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "0");

        return mapping.findForward("queryStockPayApply");
    }

    /**
     * closeStockPay
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward closeStockPay(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            financeFacade.closeStockPayApply(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "0");

        return mapping.findForward("queryStockPayApply");
    }

    /**
     * passStockPayByCEO
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passStockPayByCEO(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            financeFacade.passStockPayByCEO(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "1");

        return mapping.findForward("queryCEOStockPayApply");
    }

    /**
     * endStockPayBySEC(结束)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward endStockPayBySEC(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String[] bankIds = request.getParameterValues("bankId");
        String[] payTypes = request.getParameterValues("payType");
        String[] moneys = request.getParameterValues("money");

        List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

        for (int i = 0; i < bankIds.length; i++ )
        {
            if (StringTools.isNullOrNone(bankIds[i]))
            {
                continue;
            }

            OutBillBean outBill = new OutBillBean();

            outBill.setBankId(bankIds[i]);

            outBill.setPayType(MathTools.parseInt(payTypes[i]));

            outBill.setMoneys(MathTools.parseDouble(moneys[i]));

            outBillList.add(outBill);
        }

        try
        {
            User user = Helper.getUser(request);

            // 结束采购付款
            financeFacade.endStockPayBySEC(user.getId(), id, reason, outBillList);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "2");

        return mapping.findForward("queryCEOStockPayApply");
    }

    /**
     * rejectStockPay
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectStockPayApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");
        
        StockPayApplyVO stockPayApplyVO = stockPayApplyDAO.findVO(id);

        try
        {
            User user = Helper.getUser(request);

            financeFacade.rejectStockPayApply(user.getId(), id, reason);
            
            sendOutRejectMail(id, user, reason, stockPayApplyVO,"采购付款申请驳回");
            
            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.saveParamers(request);

        return mapping.findForward("queryCEOStockPayApply");
    }
    
    /**
     * queryStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        Map<String, String> initMap = initLogTime2(request, condtion);

        ActionTools.processJSONDataQueryCondition(QUERYSTOCKPREPAYAPPLY, request, condtion, initMap);

        condtion.addCondition("order by StockPrePayApplyBean.payDate desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTOCKPREPAYAPPLY, request,
            condtion, this.stockPrePayApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * queryCEOStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCEOStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCEOSTOCKPREPAYAPPLY, request, condtion);

        String mode = RequestTools.getValueFromRequest(request, "mode");

        if ("1".equals(mode))
        {
            condtion.addIntCondition("StockPrePayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CEO);
        }
        else if ("2".equals(mode))
        {
            condtion.addIntCondition("StockPrePayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_SAIL);
        }
        else if ("3".equals(mode))
        {
            condtion.addIntCondition("StockPrePayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CHECK);
        }
        else if ("4".equals(mode))
        {
            condtion.addIntCondition("StockPrePayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_CFO);
        }
        else if ("5".equals(mode))
        {
            condtion.addIntCondition("StockPrePayApplyBean.status", "=",
                StockPayApplyConstant.APPLY_STATUS_SEC);
        }
        else
        {
            condtion.addFlaseCondition();
        }

        condtion.addCondition("order by StockPrePayApplyBean.payDate");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCEOSTOCKPREPAYAPPLY, request,
            condtion, this.stockPrePayApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * findStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        //String mode = request.getParameter("mode");

        StockPrePayApplyVO bean = stockPrePayApplyDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);
        
        List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

        request.setAttribute("loglist", loglist);
        
        prepare(request);

        if ("1".equals(update) && bean.getStatus() != StockPayApplyConstant.APPLY_STATUS_END)
        {
//            if (TimeTools.now_short().compareTo(bean.getPayDate()) >= 0)
//            {
                return mapping.findForward("handleStockPrePayApply");
//            }
//            else
//            {
//                return ActionTools.toError("付款的最早时间还没有到", "queryStockPrePayApply", mapping, request);
//            }
        }
        
        if ("2".equals(update))
        {
        	return mapping.findForward("updateStockPrePayApply");
        }

        String inBillId = bean.getInBillId();

        if ( !StringTools.isNullOrNone(inBillId))
        {
            List<OutBillBean> outList = new ArrayList<OutBillBean>();

            String[] split = inBillId.split(";");

            for (String each : split)
            {
                if ( !StringTools.isNullOrNone(each))
                {
                    OutBillBean outBill = outBillDAO.findVO(each);

                    if (outBill != null)
                    {
                        outList.add(outBill);
                    }
                }
            }

            request.setAttribute("outList", outList);
        }

        return mapping.findForward("detailStockPrePayApply");
    }
    
    /**
     * passStockPrePayByCEO
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passStockPrePayByCEO(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            financeFacade.passStockPrePayByCEO(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "1");

        return mapping.findForward("queryCEOStockPrePayApply");
    }
    
    /**
     * endStockPrePayBySEC
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward endStockPrePayBySEC(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String[] bankIds = request.getParameterValues("bankId");
        String[] payTypes = request.getParameterValues("payType");
        String[] moneys = request.getParameterValues("money");

        List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

        for (int i = 0; i < bankIds.length; i++ )
        {
            if (StringTools.isNullOrNone(bankIds[i]))
            {
                continue;
            }

            OutBillBean outBill = new OutBillBean();

            outBill.setBankId(bankIds[i]);

            outBill.setPayType(MathTools.parseInt(payTypes[i]));

            outBill.setMoneys(MathTools.parseDouble(moneys[i]));

            outBillList.add(outBill);
        }

        try
        {
            User user = Helper.getUser(request);

            // 结束采购付款
            financeFacade.endStockPrePayBySEC(user.getId(), id, reason, outBillList);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "2");

        return mapping.findForward("queryCEOStockPrePayApply");
    }
    
    /**
     * rejectStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");
        
        try
        {
            User user = Helper.getUser(request);

            financeFacade.rejectStockPrePayApply(user.getId(), id, reason);
            
            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.saveParamers(request);

        return mapping.findForward("queryCEOStockPrePayApply");
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	prepare(request);
        
        return mapping.findForward("addStockPrePayApply");
	}

	private void prepare(HttpServletRequest request)
	{
		// 这里的发票应该是进项的
        List<InvoiceBean> invoiceList = invoiceDAO.listForwardIn();

        request.setAttribute("invoiceList", invoiceList);
	}
    
 
    /**
     * addStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	StockPrePayApplyBean bean = new StockPrePayApplyBean();
    	
    	BeanUtil.getBean(bean, request);
    	
    	try{
    		User user = Helper.getUser(request);
    		
    		bean.setStafferId(user.getStafferId());
    		
    		bean.setLogTime(TimeTools.now());
    		
    		financeFacade.addStockPrePayApply(user.getId(), bean);
    		
    		request.setAttribute(KeyConstant.MESSAGE, "成功操作");
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败." + e.getErrorContent());
    	}
    	
    	request.setAttribute("mode", "0");
    	
    	return mapping.findForward("queryStockPrePayApply");
	}
    
    /**
     * updateStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	StockPrePayApplyBean bean = new StockPrePayApplyBean();
    	
    	BeanUtil.getBean(bean, request);
    	
    	try{
    		User user = Helper.getUser(request);
    		
    		bean.setStafferId(user.getStafferId());
    		
    		bean.setLogTime(TimeTools.now());
    		
    		financeFacade.updateStockPrePayApply(user.getId(), bean);
    		
    		request.setAttribute(KeyConstant.MESSAGE, "成功操作");
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败." + e.getErrorContent());
    	}
    	
    	request.setAttribute("mode", "0");
    	
    	return mapping.findForward("queryStockPrePayApply");
	}
    
    /**
     * deleteStockPrePayApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteStockPrePayApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	AjaxResult ajax = new AjaxResult();
    	
    	String id = request.getParameter("id");
    	
    	try{
    		User user = Helper.getUser(request);
    		
    		financeFacade.deleteStockPrePayApply(user.getId(), id);
    		
    		ajax.setSuccess("操作成功");
    		
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		ajax.setError("操作失败");
    	}
    	
    	return JSONTools.writeResponse(response, ajax);
	}

    /**
     * refStockPrePay
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward refStockPrePay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	double total = 0.0d;

    	String applyId = request.getParameter("applyId");
    	
		String[] preApplyIds = request.getParameterValues("preApplyId");

		double outTotal = MathTools.parseDouble(request.getParameter("total"));
		
		List<StockPayVSPreBean> vsList = new ArrayList<StockPayVSPreBean>();
		
		for (String preId : preApplyIds) {
			
			StockPrePayApplyBean pre = stockPrePayApplyDAO.find(preId);
			
			StockPayVSPreBean vs = new StockPayVSPreBean();

			vs.setPayApplyId(applyId);
			
			vs.setPrePayId(preId);
			
			vs.setMoneys(pre.getMoneys() - pre.getRealMoneys());
			
			vs.setLogTime(TimeTools.now());

			vsList.add(vs);

			total += vs.getMoneys();

			if (total >= outTotal) {
				break;
			}
		}
		
		String newApplyId = "";
		
		try{
			newApplyId = financeFacade.refStockPrePay(user.getId(), vsList);
			
			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

//		StockPayApplyBean apply = stockPayApplyDAO.find(newApplyId);
//		
//		if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_SEC)
//		{
//			request.setAttribute("id", apply.getId());
//			
//			request.setAttribute("update", "1");
//			
//			request.setAttribute("ifRef", "");
//
//			return findStockPayApply(mapping, form, request, response);
//		}
		
        request.setAttribute("mode", "2");

        return mapping.findForward("queryCEOStockPayApply");
	}
    
    /**
     * queryForConfirmStockPay
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    @Deprecated
    public ActionForward queryForConfirmStockPay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	String invoiceId = request.getParameter("invoiceId");
    	
    	String invoiceName = request.getParameter("invoiceName");
    	
    	double canUseMoney = MathTools.parseDouble(request.getParameter("canUseMoney"));
    	
    	User user = Helper.getUser(request);
    	
    	List<ConfirmInsWrap> wrapList = stockPayApplyDAO.queryCanConfirmApply(user.getStafferId(), invoiceId);
    	
    	request.setAttribute("wrapList", wrapList);
    	
    	request.setAttribute("invoiceId", invoiceId);
    	request.setAttribute("canUseMoney", canUseMoney);
    	request.setAttribute("invoiceName", invoiceName);
    	
    	return mapping.findForward("refConfirmInvoice");
    }
    
    /**
     * refInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward refInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	double total = 0.0d;

    	// checkBox selected = true
		String[] ids = request.getParameterValues("id");
		
		double canUseMoney = MathTools.parseDouble(request.getParameter("total"));
		
		String stockPayApplyId = request.getParameter("stockPayApplyId");
		
		List<InvoiceBindOutBean> vsList = new ArrayList<InvoiceBindOutBean>();
		
		for (String id : ids) {
			
			InvoiceStorageBean isbean = invoiceStorageDAO.find(id);
			
			InvoiceBindOutBean vs = new InvoiceBindOutBean();

			vs.setInvoiceStorageId(id);
			
			vs.setFullId(stockPayApplyId);
			
			vs.setOuttype(999);
			
			vs.setConfirmMoney(isbean.getMoneys() - isbean.getHasConfirmMoneys());
			
			vsList.add(vs);

			total += vs.getConfirmMoney();

			if (total >= canUseMoney) {
				break;
			}
		}
		
		try{
			financeFacade.refInvoice(user.getId(), vsList);
			
			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "0");

        return mapping.findForward("queryStockPayApply");
	}
    
    /**
     * @return the stockPayApplyDAO
     */
    public StockPayApplyDAO getStockPayApplyDAO()
    {
        return stockPayApplyDAO;
    }

    /**
     * @param stockPayApplyDAO
     *            the stockPayApplyDAO to set
     */
    public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
    {
        this.stockPayApplyDAO = stockPayApplyDAO;
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
     * @return the financeFacade
     */
    public FinanceFacade getFinanceFacade()
    {
        return financeFacade;
    }

    /**
     * @param financeFacade
     *            the financeFacade to set
     */
    public void setFinanceFacade(FinanceFacade financeFacade)
    {
        this.financeFacade = financeFacade;
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
     * 邮件驳回发送
     * @param fullId
     * @param user
     * @param reason
     * @param out
     * @param subject
     */
    private void sendOutRejectMail(String fullId, User user, String reason, StockPayApplyVO stockPayApplyVO,String subject) 
    {
        StafferBean rejectorBean = stafferDAO.find(user.getStafferId());        
        
        StafferBean approverBean = stafferDAO.find(stockPayApplyVO.getStafferId());
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("\r\n").append("单号:"+ fullId).append(",")
            .append("\r\n").append("供应商:"+ stockPayApplyVO.getProvideName()).append(",")
            .append("\r\n").append("总金额:"+ stockPayApplyVO.getMoneys()).append(",")
            .append("\r\n").append("审批人:"+ rejectorBean.getName()).append(",")
            .append("\r\n").append("审批结果:驳回").append(",")
            .append("\r\n").append("审批意见:" + reason).append(",")
            .append("\r\n").append("审批人电话:"+ rejectorBean.getHandphone());
            
            String message = sb.toString();
            
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }

	public CommonMailManager getCommonMailManager() {
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager) {
		this.commonMailManager = commonMailManager;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	public RoleAuthDAO getRoleAuthDAO() {
		return roleAuthDAO;
	}

	public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO) {
		this.roleAuthDAO = roleAuthDAO;
	}

	/**
	 * @return the stockPrePayApplyDAO
	 */
	public StockPrePayApplyDAO getStockPrePayApplyDAO()
	{
		return stockPrePayApplyDAO;
	}

	/**
	 * @param stockPrePayApplyDAO the stockPrePayApplyDAO to set
	 */
	public void setStockPrePayApplyDAO(StockPrePayApplyDAO stockPrePayApplyDAO)
	{
		this.stockPrePayApplyDAO = stockPrePayApplyDAO;
	}

	/**
	 * @return the invoiceDAO
	 */
	public InvoiceDAO getInvoiceDAO()
	{
		return invoiceDAO;
	}

	/**
	 * @param invoiceDAO the invoiceDAO to set
	 */
	public void setInvoiceDAO(InvoiceDAO invoiceDAO)
	{
		this.invoiceDAO = invoiceDAO;
	}

	/**
	 * @return the invoiceStorageDAO
	 */
	public InvoiceStorageDAO getInvoiceStorageDAO()
	{
		return invoiceStorageDAO;
	}

	/**
	 * @param invoiceStorageDAO the invoiceStorageDAO to set
	 */
	public void setInvoiceStorageDAO(InvoiceStorageDAO invoiceStorageDAO)
	{
		this.invoiceStorageDAO = invoiceStorageDAO;
	}
}
