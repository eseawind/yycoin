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
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.dao.AddressDAO;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.client.vo.AddressVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerAddressAction
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerAddressAction
 * @since 1.0
 */
public class CustomerAddressAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ClientManager clientManager = null;

    private AddressDAO addressDAO = null;
    
    private UserManager userManager = null;

    private StafferDAO stafferDAO = null;

    private static String QUERYADDRESS = "queryAddress";

    private static String RPTQUERYADDRESS = "rptQueryAddress";

    /**
     * default constructor
     */
    public CustomerAddressAction()
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
    public ActionForward queryAddress(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);
        
        condtion.addCondition("AddressBean.stafferId", "=", user.getStafferId());
        
        ActionTools.processJSONQueryCondition(QUERYADDRESS, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYADDRESS, request,
            condtion, this.addressDAO, new HandleResult<AddressVO>()
            {
				public void handle(AddressVO vo)
				{
					vo.setFullAddress(vo.getProvinceName() + " " + vo.getCityName() + " " + vo.getAddress() );
				}
        		
            });

        return JSONTools.writeResponse(response, jsonstr);
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
    public ActionForward addAddress(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AddressBean bean = new AddressBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);
            
            bean.setStafferId(user.getStafferId());
            
            bean.setLogTime(TimeTools.now());

            clientManager.addAddressBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryAddress");
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
    public ActionForward updateAddress(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
		throws ServletException
	{
        AddressBean bean = new AddressBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());
            
            bean.setLogTime(TimeTools.now());
            
            clientManager.updateAddressBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryAddress");
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
    public ActionForward findAddress(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        AddressVO bean = addressDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

            return mapping.findForward("queryAddress");
        }

        request.setAttribute("bean", bean);

        if ("1".equals(update))
        {
            return mapping.findForward("updateAddress");
        }

        return mapping.findForward("detailAddress");
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
    public ActionForward delAddress(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientManager.delAddressBean(user, id);

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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryAddress(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        String customerId = request.getParameter("customerId");
        
        List<AddressVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();
            
            condtion.addCondition("AddressBean.CUSTOMERID", "=", customerId);

            int total = addressDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYADDRESS);

            list = addressDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYADDRESS);

            list = addressDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYADDRESS), PageSeparateTools.getPageSeparate(request,
                RPTQUERYADDRESS));
        }

        request.setAttribute("beanList", list);
        
        request.setAttribute("customerId", customerId);
        
        return mapping.findForward("rptQueryAddress");
    }
    
	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager()
	{
		return clientManager;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager)
	{
		this.clientManager = clientManager;
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

	public AddressDAO getAddressDAO()
	{
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO)
	{
		this.addressDAO = addressDAO;
	}
}
