/**
 * File Name: CreditItemAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.action;


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
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditItemDAO;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.credit.manager.CreditItemManager;
import com.china.center.oa.credit.vo.CreditItemSecVO;
import com.china.center.oa.credit.vo.CreditItemThrVO;
import com.china.center.oa.credit.vo.CreditItemVO;
import com.china.center.oa.credit.vo.CreditLevelVO;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;


/**
 * CreditItemAction
 * 
 * @author ZHUZHU
 * @version 2009-10-28
 * @see CreditItemAction
 * @since 1.0
 */
public class CreditItemAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private CreditItemDAO creditItemDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private CreditItemSecDAO creditItemSecDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private CreditItemManager creditItemManager = null;

    private ClientFacade clientFacade = null;

    private CreditLevelDAO creditLevelDAO = null;

    private ParameterDAO parameterDAO = null;

    private static String QUERYCREDITITEM = "queryCreditItem";

    private static String QUERYCREDITITEMSEC = "queryCreditItemSec";

    private static String QUERYCREDITITEMTHR = "queryCreditItemThr";

    private static String QUERYCREDITLEVEL = "queryCreditLevel";

    /**
     * default constructor
     */
    public CreditItemAction()
    {
    }

    /**
     * queryCreditItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCreditItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCREDITITEM, request, condtion);

        final int staticAmount = parameterDAO.getInt(SysConfigConstant.CREDIT_STATIC);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCREDITITEM, request, condtion, this.creditItemDAO,
            new HandleResult<CreditItemVO>()
            {
                public void handle(CreditItemVO obj)
                {
                    double sum = creditItemSecDAO.sumPerByPid(obj.getId());

                    obj.setPerAmount(MathTools.formatNum(sum));

                    obj.setPoint(MathTools.formatNum( (staticAmount * obj.getPer()) / 100.0d));

                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCreditItemSec
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCreditItemSec(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCREDITITEMSEC, request, condtion);

        final int staticAmount = parameterDAO.getInt(SysConfigConstant.CREDIT_STATIC);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCREDITITEMSEC, request, condtion,
            this.creditItemSecDAO, new HandleResult<CreditItemSecVO>()
            {
                public void handle(CreditItemSecVO obj)
                {
                    // only percent item need to display point
                    if (obj.getType() == CreditConstant.CREDIT_ITEM_TYPE_PERCENT)
                    {
                        obj.setPoint(MathTools.formatNum( (staticAmount * obj.getPer() * obj.getParentPer()) / 10000.0d));
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCreditItemSec
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCREDITITEMTHR, request, condtion);

        condtion.addCondition("order by CreditItemThrBean.pid");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCREDITITEMTHR, request, condtion,
            this.creditItemThrDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCreditLevel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCreditLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCREDITLEVEL, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCREDITLEVEL, request, condtion, this.creditLevelDAO,
            new HandleResult<CreditLevelVO>()
            {
                public void handle(CreditLevelVO obj)
                {
                    int count = customerMainDAO.countByCreditLevelId(obj.getId());

                    obj.setCustomerAmount(String.valueOf(count));
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddCreditItemThr
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        CreditItemSecBean parent = creditItemSecDAO.find(pid);

        if (parent == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

            return queryCreditItemSec(mapping, form, request, response);
        }

        List<CreditItemThrVO> thrList = creditItemThrDAO.queryEntityVOsByFK(pid);

        request.setAttribute("thrList", thrList);
        request.setAttribute("parent", parent);

        return mapping.findForward("addCreditItemThr");
    }

    /**
     * addCreditItemThr
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        CreditItemThrBean bean = new CreditItemThrBean();

        String goon = request.getParameter("goon");

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            clientFacade.addCreditItemThr(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加评价级别:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加评价级别失败:" + e.getMessage());
        }

        if ("1".equals(goon))
        {
            return preForAddCreditItemThr(mapping, form, request, response);
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryCreditItemThr");
    }

    /**
     * findCreditItemThr
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        CreditItemThrBean bean = creditItemThrDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不存在");

            return mapping.findForward("queryCreditItemThr");
        }

        CreditItemSecBean parent = creditItemSecDAO.find(bean.getPid());

        if (parent == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不存在");

            return mapping.findForward("queryCreditItemThr");
        }

        List<CreditItemThrVO> thrList = creditItemThrDAO.queryEntityVOsByFK(bean.getPid());

        request.setAttribute("thrList", thrList);

        request.setAttribute("parent", parent);

        request.setAttribute("bean", bean);

        return mapping.findForward("updateCreditItemThr");
    }

    /**
     * updateCreditItemThr
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        CreditItemThrBean bean = new CreditItemThrBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            clientFacade.updateCreditItemThr(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改评价级别:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改评价级别失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryCreditItemThr");
    }

    /**
     * updateCreditLevel
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCreditLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newMoney = request.getParameter("newMoney");

        AjaxResult ajax = new AjaxResult();

        try
        {
            CreditLevelBean bean = creditLevelDAO.find(id);

            bean.setMoney(CommonTools.parseFloat(newMoney));

            User user = Helper.getUser(request);

            clientFacade.updateCreditLevel(user.getId(), bean);

            ajax.setSuccess("成功修改额度:" + bean.getName());

        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("修改额度失败:" + e.getMessage());

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改额度失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * deleteCreditItemThr
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteCreditItemThr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientFacade.deleteCreditItemThr(user.getId(), id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除申请失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * updateCreditItemPer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCreditItemPer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newPer = request.getParameter("newPer");

        AjaxResult ajax = new AjaxResult();

        try
        {
            CreditItemBean creditItemBean = creditItemDAO.find(id);

            if (creditItemBean == null)
            {
                ajax.setError("数据错误");

                return JSONTools.writeResponse(response, ajax);
            }

            creditItemBean.setPer(CommonTools.parseFloat(newPer));

            User user = Helper.getUser(request);

            clientFacade.updateCreditItem(user.getId(), creditItemBean);

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
     * updateCreditItemSecPer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCreditItemSecPer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newPer = request.getParameter("newPer");

        AjaxResult ajax = new AjaxResult();

        try
        {
            CreditItemSecBean creditItemBean = creditItemSecDAO.find(id);

            if (creditItemBean == null)
            {
                ajax.setError("数据错误");

                return JSONTools.writeResponse(response, ajax);
            }

            creditItemBean.setPer(CommonTools.parseFloat(newPer));

            User user = Helper.getUser(request);

            clientFacade.updateCreditItemSec(user.getId(), creditItemBean);

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
     * @return the creditItemDAO
     */
    public CreditItemDAO getCreditItemDAO()
    {
        return creditItemDAO;
    }

    /**
     * @param creditItemDAO
     *            the creditItemDAO to set
     */
    public void setCreditItemDAO(CreditItemDAO creditItemDAO)
    {
        this.creditItemDAO = creditItemDAO;
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

    /**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
    }

    /**
     * @return the creditItemManager
     */
    public CreditItemManager getCreditItemManager()
    {
        return creditItemManager;
    }

    /**
     * @param creditItemManager
     *            the creditItemManager to set
     */
    public void setCreditItemManager(CreditItemManager creditItemManager)
    {
        this.creditItemManager = creditItemManager;
    }

    /**
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
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
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

	/**
	 * @return the clientFacade
	 */
	public ClientFacade getClientFacade()
	{
		return clientFacade;
	}

	/**
	 * @param clientFacade the clientFacade to set
	 */
	public void setClientFacade(ClientFacade clientFacade)
	{
		this.clientFacade = clientFacade;
	}
}
