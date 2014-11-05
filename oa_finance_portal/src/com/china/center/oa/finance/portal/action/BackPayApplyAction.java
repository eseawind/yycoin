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
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.dao.BackPayApplyDAO;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.vo.BackPayApplyVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;


/**
 * BackPayApplyAction
 * 
 * @author ZHUZHU
 * @version 2011-2-18
 * @see BackPayApplyAction
 * @since 3.0
 */
public class BackPayApplyAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private FlowLogDAO flowLogDAO = null;

    private BankDAO bankDAO = null;

    private OutDAO outDAO = null;

    private OutManager outManager = null;

    private UserManager userManager = null;

    private BackPayApplyDAO backPayApplyDAO = null;

    private FinanceFacade financeFacade = null;

    private static final String QUERYBACKPAYAPPLY = "queryBackPayApply";

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
    public ActionForward queryBackPayApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYBACKPAYAPPLY, request, condtion);

        String mode = RequestTools.getValueFromRequest(request, "mode");

        User user = Helper.getUser(request);

        ActionForward checkAuth = checkAuth(mapping, request);

        if (checkAuth != null)
        {
            return checkAuth;
        }

        // 自己的
        if ("0".equals(mode))
        {
            condtion.addCondition("BackPayApplyBean.stafferId", "=", user.getStafferId());
        }
        // 结算中心
        else if ("1".equals(mode))
        {
            condtion.addIntCondition("BackPayApplyBean.status", "=",
                BackPayApplyConstant.STATUS_SUBMIT);
        }
        // 出纳
        else if ("2".equals(mode))
        {
            if ( !userManager.containAuth(user.getId(), AuthConstant.BILL_QUERY_ALL))
            {
                condtion.addCondition("BackPayApplyBean.locationId", "=", user.getLocationId());
            }

            condtion.addIntCondition("BackPayApplyBean.status", "=",
                BackPayApplyConstant.STATUS_SEC);
        }
        else
        {
            condtion.addFlaseCondition();
        }

        condtion.addCondition("order by BackPayApplyBean.id desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBACKPAYAPPLY, request, condtion,
            this.backPayApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * checkAuth
     * 
     * @param mapping
     * @param request
     * @return
     */
    private ActionForward checkAuth(ActionMapping mapping, HttpServletRequest request)
    {
        User user = Helper.getUser(request);

        String mode = request.getParameter("mode");

        // 自己的
        if ("0".equals(mode))
        {
            if ( !userManager.containAuth(user, AuthConstant.SAIL_SUBMIT))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                return mapping.findForward("queryBackPayApply");
            }
        }
        // 结算中心
        else if ("1".equals(mode))
        {
            if ( !userManager.containAuth(user, AuthConstant.SAIL_BACKPAY_CENTER))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                return mapping.findForward("queryBackPayApply");

            }
        }
        // 出纳
        else if ("2".equals(mode))
        {
            if ( !userManager.containAuth(user, AuthConstant.SAIL_BACKPAY_SEC))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                return mapping.findForward("queryBackPayApply");
            }
        }
        else
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

            return mapping.findForward("queryBackPayApply");
        }

        return null;
    }

    /**
     * findBackPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBackPayApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        BackPayApplyVO bean = backPayApplyDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);

        List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

        request.setAttribute("loglist", loglist);

        if (bean.getType() == BackPayApplyConstant.TYPE_OUT)
        {
            OutBean out = outDAO.find(bean.getOutId());

            request.setAttribute("out", out);

            double backTotal = outDAO.sumOutBackValue(bean.getOutId());

            request.setAttribute("backTotal", backTotal);
        }

        List<BankBean> bankList = bankDAO.listEntityBeans();

        request.setAttribute("bankList", bankList);

        return mapping.findForward("handleBackPayApply");
    }

    /**
     * addBackPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBackPayApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        BackPayApplyBean bean = new BackPayApplyBean();

        User user = Helper.getUser(request);
        
        BeanUtil.getBean(bean, request);

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
        	bean.setOperator(g_srcUser.getStafferId());
        	bean.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	bean.setOperator(user.getStafferId());
        	bean.setOperatorName(user.getStafferName());
        }
        // 商务 - end
        
        try
        {
            bean.setStafferId(user.getStafferId());

            bean.setType(BackPayApplyConstant.TYPE_OUT);

            financeFacade.addBackPayApplyBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("queryType", "8");

        RequestTools.menuInitQuery(request);

        return mapping.findForward("queryOut");
    }

    /**
     * passBackPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passBackPayApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String mode = request.getParameter("mode");

        try
        {
            User user = Helper.getUser(request);

            financeFacade.passBackPayApplyBean(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", mode);

        return mapping.findForward("queryBackPayApply");
    }

    /**
     * deleteBackPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteBackPayApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            financeFacade.deleteBackPayApplyBean(user.getId(), id);

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
     * endBackPayApply(结束)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward endBackPayApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        BackPayApplyBean back = backPayApplyDAO.find(id);

        try
        {
            User user = Helper.getUser(request);

            List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

            if (back.getBackPay() > 0)
            {
                String[] bankIds = request.getParameterValues("bankId");
                String[] payTypes = request.getParameterValues("payType");
                String[] moneys = request.getParameterValues("money");

                double pageTotal = 0.0d;

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

                    pageTotal += outBill.getMoneys();
                }

                if ( !MathTools.equal(back.getBackPay(), pageTotal))
                {
                    return ActionTools.toError("付款金额不等于申请付款金额", mapping, request);
                }
            }

            // 会计结束退款申请
            financeFacade.endBackPayApplyBean(user.getId(), id, reason, outBillList);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", "2");

        return mapping.findForward("queryBackPayApply");
    }

    /**
     * rejectBackPayApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectBackPayApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String mode = request.getParameter("mode");

        try
        {
            User user = Helper.getUser(request);

            financeFacade.rejectBackPayApplyBean(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        request.setAttribute("mode", mode);

        return mapping.findForward("queryBackPayApply");
    }

    private ActionForward checkAuthForEcommerce(HttpServletRequest request, User user, ActionMapping mapping)
    {
        // 针对非商务模式下，业务员开单要有权限限制
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        // elogin 为空，则表示非商务模式, elogin 1 表示是商务切换登陆
        if (StringTools.isNullOrNone(elogin) || (elogin.equals("1") && g_loginType.equals("2")))
        {
        	// 检查是否有权限
        	if (!containAuth(user, AuthConstant.DIRECT_SALE))
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "非商务模式下,没有权限操作");

                return mapping.findForward("error");
        	}
        }
		return null;
    }
    
    private boolean containAuth(User user, String authId)
    {
        if (StringTools.isNullOrNone(authId))
        {
            return true;
        }

        if (authId.equals(AuthConstant.PUNLIC_AUTH))
        {
            return true;
        }

        List<RoleAuthBean> authList = user.getAuth();

        for (RoleAuthBean roleAuthBean : authList)
        {
            if (roleAuthBean.getAuthId().equals(authId))
            {
                return true;
            }
        }

        return false;
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
     * @return the backPayApplyDAO
     */
    public BackPayApplyDAO getBackPayApplyDAO()
    {
        return backPayApplyDAO;
    }

    /**
     * @param backPayApplyDAO
     *            the backPayApplyDAO to set
     */
    public void setBackPayApplyDAO(BackPayApplyDAO backPayApplyDAO)
    {
        this.backPayApplyDAO = backPayApplyDAO;
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
}
