/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.vo.DutyVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * DutyAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DutyAction
 * @since 1.0
 */
public class DutyAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PublicFacade publicFacade = null;

    private DutyDAO dutyDAO = null;
    
    private StafferManager stafferManager = null;

    private static final String QUERYDUTY = "queryDuty";

    /**
     * default constructor
     */
    public DutyAction()
    {
    }

    public ActionForward queryDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYDUTY, request, condtion);

        String jsonstr = ActionTools.queryBeanByJSONAndToString(QUERYDUTY, request, condtion, this.dutyDAO);

        return JSONTools.writeResponse(response, jsonstr);
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
    public ActionForward preForAddDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	prepare(request);
        
        return mapping.findForward("addDuty");
	}

	private void prepare(HttpServletRequest request)
	{
		// 获得财务审批的权限人(1604)
        List<StafferBean> stafferList = stafferManager
            .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

        request.setAttribute("stafferList", stafferList);
	}
    
    /**
     * addDuty
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
        throws ServletException
    {
        DutyBean bean = new DutyBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.addDutyBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加税务实体:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDuty");
    }

    /**
     * updateDuty
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        DutyBean bean = new DutyBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.updateDutyBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作税务实体:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryDuty");
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
    public ActionForward deleteDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.deleteDutyBean(user, id);

            ajax.setSuccess("成功删除税务实体");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findDuty
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        DutyVO bean = dutyDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryDuty");
        }

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        prepare(request);
        
        if ("1".equals(update))
        {
            return mapping.findForward("updateDuty");
        }

        return mapping.findForward("detailDuty");
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

	public StafferManager getStafferManager()
	{
		return stafferManager;
	}

	public void setStafferManager(StafferManager stafferManager)
	{
		this.stafferManager = stafferManager;
	}
}
