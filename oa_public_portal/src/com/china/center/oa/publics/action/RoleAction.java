/**
 * File Name: RoleAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.QueryConfig;
import com.china.center.actionhelper.query.QueryItemBean;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.dao.AuthDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.RoleDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.manager.AuthManager;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.vo.RoleVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * RoleAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see RoleAction
 * @since 1.0
 */
public class RoleAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private RoleManager roleManager = null;

    private RoleDAO roleDAO = null;

    private AuthDAO authDAO = null;

    private AuthManager authManager = null;

    private QueryConfig queryConfig = null;

    private LocationDAO locationDAO = null;

    private PublicFacade publicFacade = null;

    private static String QUERYROLE = "queryRole";

    /**
     * default constructor
     */
    public RoleAction()
    {
    }

    /**
     * queryStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryRole(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 只能看到本部的角色
        if ( !LocationHelper.isVirtualLocation(user.getLocationId()))
        {
            condtion.addCondition("RoleBean.locationId", "=", user.getLocationId());
        }

        ActionTools.processJSONQueryCondition(QUERYROLE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYROLE, request, condtion,
            this.roleDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddRole
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddRole(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        List<AuthBean> authtList = authManager.listAllConfigAuth();

        request.setAttribute("authtList", authtList);

        User user = Helper.getUser(request);

        if (LocationHelper.isVirtualLocation(user.getLocationId()))
        {
            List<LocationBean> locationList = locationDAO.listEntityBeans();

            request.setAttribute("locationList", locationList);
        }
        else
        {
            List<LocationBean> locationList = new ArrayList<LocationBean>();

            locationList.add(locationDAO.find(user.getLocationId()));

            request.setAttribute("locationList", locationList);
        }

        JSONArray jarr = new JSONArray(authtList, true);

        request.setAttribute("authListJSON", jarr.toString());

        return mapping.findForward("addRole");
    }

    /**
     * popStafferQuery
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward popStafferCommonQuery(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        QueryItemBean query = queryConfig.findQueryCondition(QUERYROLE);

        if (query == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有配置查询,请核实");

            return mapping.findForward("queryStaffer");
        }

        return mapping.findForward("commonQuery");
    }

    /**
     * addRole
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateRole(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        RoleBean bean = new RoleBean();

        try
        {
            BeanUtil.getBean(bean, request);

            createRoleBean(request, bean);

            User user = Helper.getUser(request);

            if (StringTools.isNullOrNone(bean.getId()))
            {
                publicFacade.addRoleBean(user.getId(), bean);
            }
            else
            {
                publicFacade.updateRoleBean(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功操作权限:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作权限失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryUser");
    }

    /**
     * 构建角色
     * 
     * @param request
     * @param bean
     */
    private void createRoleBean(HttpServletRequest request, RoleBean bean)
    {
        String[] auths = request.getParameterValues("tree_checkbox");

        List<RoleAuthBean> auList = new ArrayList<RoleAuthBean>();

        if (auths != null && auths.length > 0)
        {
            for (String item : auths)
            {
                RoleAuthBean rab = new RoleAuthBean();

                rab.setAuthId(item);

                auList.add(rab);
            }
        }

        bean.setAuth(auList);
    }

    /**
     * delStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delRole(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.delRoleBean(user.getId(), id);

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
     * updateStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findRole(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        try
        {
            List<AuthBean> authtList = authManager.listAllConfigAuth();

            request.setAttribute("authtList", authtList);

            User user = Helper.getUser(request);

            // 超级管理员
            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                List<LocationBean> locationList = locationDAO.listEntityBeans();

                request.setAttribute("locationList", locationList);
            }
            else
            {
                List<LocationBean> locationList = new ArrayList<LocationBean>();

                locationList.add(locationDAO.find(user.getLocationId()));

                request.setAttribute("locationList", locationList);
            }

            JSONArray jarr = new JSONArray(authtList, true);

            request.setAttribute("authListJSON", jarr.toString());

            RoleVO bean = roleManager.findVO(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "角色不存在");

                return mapping.findForward("queryRole");
            }

            jarr = new JSONArray(bean.getAuth(), true);

            request.setAttribute("myAuth", jarr.toString());

            request.setAttribute("bean", bean);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward("queryRole");
        }

        return mapping.findForward("updateRole");
    }

    /**
     * @return the roleManager
     */
    public RoleManager getRoleManager()
    {
        return roleManager;
    }

    /**
     * @param roleManager
     *            the roleManager to set
     */
    public void setRoleManager(RoleManager roleManager)
    {
        this.roleManager = roleManager;
    }

    /**
     * @return the roleDAO
     */
    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }

    /**
     * @param roleDAO
     *            the roleDAO to set
     */
    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }

    /**
     * @return the queryConfig
     */
    public QueryConfig getQueryConfig()
    {
        return queryConfig;
    }

    /**
     * @param queryConfig
     *            the queryConfig to set
     */
    public void setQueryConfig(QueryConfig queryConfig)
    {
        this.queryConfig = queryConfig;
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
     * @return the authDAO
     */
    public AuthDAO getAuthDAO()
    {
        return authDAO;
    }

    /**
     * @param authDAO
     *            the authDAO to set
     */
    public void setAuthDAO(AuthDAO authDAO)
    {
        this.authDAO = authDAO;
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
}
