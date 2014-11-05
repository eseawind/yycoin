/**
 * File Name: CustomerCheckAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.action;


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
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CiticBranchBean;
import com.china.center.oa.client.bean.CiticVSStafferBean;
import com.china.center.oa.client.dao.CiticBranchDAO;
import com.china.center.oa.client.dao.CiticVSStafferDAO;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.client.vo.CiticBranchVO;
import com.china.center.oa.client.vo.CiticVSStafferVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * CustomerAddressAction
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCiticGroupAction
 * @since 1.0
 */
public class CustomerCiticGroupAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ClientManager clientManager = null;

    private CiticBranchDAO citicBranchDAO = null;
    
    private CiticVSStafferDAO citicVSStafferDAO = null;
    
    private UserManager userManager = null;

    private StafferDAO stafferDAO = null;

    private static String QUERYCITICGROUP = "QUERYCITICGROUP";

    /**
     * default constructor
     */
    public CustomerCiticGroupAction()
    {
    }

    /**
     * queryCiticGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCiticGroup(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // filter system group
        // condtion.addCondition("GroupBean.type", "<>", GroupConstant.GROUP_TYPE_SYSTEM);

        ActionTools.processJSONQueryCondition(QUERYCITICGROUP, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCITICGROUP, request, condtion,
            this.citicBranchDAO, new HandleResult<CiticBranchVO>()
            {
                public void handle(CiticBranchVO vo)
                {
                    wrapGroup(vo);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    private void wrapGroup(CiticBranchVO vo)
    {
        List<CiticVSStafferVO> voList = citicVSStafferDAO.queryEntityVOsByFK(vo.getId());

        for (CiticVSStafferVO groupVSStafferVO : voList)
        {
            vo.setCustomerNames(groupVSStafferVO.getCustomerName() + ',' + vo.getCustomerNames());
        }
    }
    
    /**
     * addOrUpdateGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateCiticGroup(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
    	CiticBranchBean bean = new CiticBranchBean();

        String update = request.getParameter("update");

        try
        {
        	BeanUtil.getBean(bean, request);
        	
            User user = Helper.getUser(request);

            createItems(request, bean);

            if ("1".equals(update))
            {
                clientManager.updateCiticBean(user, bean);
            }
            else
            {
            	clientManager.addCiticBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存客户分组信息");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存客户分组失败:" + e.getMessage());
        }

        return mapping.findForward("queryCiticGroup");
    }    
    
    /**
     * 构建Visit
     * 
     * @param request
     * @param bean
     * @throws MYException
     */
    private void createItems(HttpServletRequest request, CiticBranchBean bean)
        throws MYException
    {
    	List<CiticVSStafferBean> vsList = new ArrayList<CiticVSStafferBean>();
    	
    	bean.setVsList(vsList);
    	
        String[] vsCustomerIds = request.getParameterValues("vsCustomerIds");

        if (vsCustomerIds == null)
        {
            return;
        }

        for (int i = 0; i < vsCustomerIds.length; i++ )
        {
            if (StringTools.isNullOrNone(vsCustomerIds[i]))
            {
                continue;
            }

            CiticVSStafferBean vs = new CiticVSStafferBean();

            vs.setCustomerId(vsCustomerIds[i]);

            vsList.add(vs);
        }
    }    
    
    /**
     * delGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delCiticGroup(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            clientManager.delCiticBean(user, id);

            ajax.setSuccess("成功删除群组");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除群组失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    
    /**
     * find群组
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCiticGroup(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        CiticBranchVO bean = citicBranchDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("客户分组不存在", mapping, request);
        }

        List<CiticVSStafferVO> items = citicVSStafferDAO.queryEntityVOsByFK(id);

        request.setAttribute("bean", bean);

        request.setAttribute("items", items);

        if ("1".equals(update))
        {
            return mapping.findForward("updateCiticGroup");
        }

        return mapping.findForward("detailCiticGroup");
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

	public CiticBranchDAO getCiticBranchDAO()
	{
		return citicBranchDAO;
	}

	public void setCiticBranchDAO(CiticBranchDAO citicBranchDAO)
	{
		this.citicBranchDAO = citicBranchDAO;
	}

	public CiticVSStafferDAO getCiticVSStafferDAO()
	{
		return citicVSStafferDAO;
	}

	public void setCiticVSStafferDAO(CiticVSStafferDAO citicVSStafferDAO)
	{
		this.citicVSStafferDAO = citicVSStafferDAO;
	}
}
