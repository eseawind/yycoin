/**
 * File Name: UserAction.java<br>
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
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AuthDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.RoleDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferTransferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.manager.AuthManager;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.publics.vs.StafferTransferBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.Security;
import com.china.center.tools.TimeTools;


/**
 * UserAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see UserAction
 * @since 1.0
 */
public class UserAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private RoleManager roleManager = null;

    private RoleDAO roleDAO = null;

    private StafferTransferDAO stafferTransferDAO = null;

    private AuthDAO authDAO = null;

    private AuthManager authManager = null;

    private StafferDAO stafferDAO = null;

    private UserManager userManager = null;

    private UserDAO userDAO = null;

    private QueryConfig queryConfig = null;

    private LocationDAO locationDAO = null;

    private PublicFacade publicFacade = null;

    private static String QUERYUSER = "queryUser";

    /**
     * default constructor
     */
    public UserAction()
    {
    }

    /**
     * queryUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryUser(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 只能看到本部的角色
        if ( !LocationHelper.isVirtualLocation(user.getLocationId()))
        {
            // condtion.addCondition("UserBean.locationId", "=", user.getLocationId());
        }

        // filter virtual location user
        condtion.addCondition("UserBean.locationId", "<>", PublicConstant.VIRTUAL_LOCATION);

        ActionTools.processJSONQueryCondition(QUERYUSER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYUSER, request, condtion,
            this.userDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * querySelfTransferUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfTransferUser(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        List<StafferTransferBean> vsList = stafferTransferDAO.queryEntityBeansByFK(user
            .getStafferId());

        List<UserVO> result = new ArrayList();

        for (StafferTransferBean each : vsList)
        {
            result.addAll(userDAO.queryEntityVOsByFK(each.getSrcId()));
        }

        PageSeparate page = new PageSeparate(result.size(), result.size());

        String jsonstr = JSONTools.getJSONString(result, page);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddUser(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        setArrForAddOrUpdate(request, user);

        List<AuthBean> authtList = authManager.listAllConfigAuth();

        request.setAttribute("authtList", authtList);

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

        return mapping.findForward("addUser");
    }

    /**
     * @param request
     * @param user
     */
    private void setArrForAddOrUpdate(HttpServletRequest request, User user)
    {
        List<StafferBean> stafferList = null;
        List<LocationBean> locationList = null;
        List<RoleBean> roleList = null;
        stafferList = stafferDAO.listEntityBeans();
        roleList = roleDAO.listEntityBeans();
        if (LocationHelper.isVirtualLocation(user.getLocationId()))
        {
            stafferList = stafferDAO.listEntityBeans();

            roleList = roleDAO.listEntityBeans();

            locationList = locationDAO.listEntityBeans();
        }
        else
        {
            stafferList = stafferDAO.queryStafferByLocationId(user.getLocationId());
            roleList = roleDAO.queryRoleByLocationId(user.getLocationId());

            locationList = new ArrayList<LocationBean>();

            locationList.add(locationDAO.find(user.getLocationId()));
        }

        request.setAttribute("stafferList", stafferList);
        request.setAttribute("roleList", roleList);
        request.setAttribute("locationList", locationList);
    }

    /**
     * addUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addUser(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        UserBean bean = new UserBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            getRoleAuth(request, bean);

            bean.setLoginTime(TimeTools.now());

            String password = RandomTools.getRandomString(PublicConstant.PASSWORD_MIN_LENGTH);

            bean.setPassword(Security.getSecurity(password));

            ActionForward ret = checkAddOrUpdate(mapping, form, request, response, bean);

            if (ret != null)
            {
                return ret;
            }

            publicFacade.addUserBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "增加用户[" + bean.getName()
                                                      + "]成功,密码：<input type=text value='"
                                                      + password + "' >" + ",请及时通知相应人员密码!");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加用户失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return this.preForAddUser(mapping, form, request, response);
    }

    /**
     * 构建角色
     * 
     * @param request
     * @param bean
     */
    private void getRoleAuth(HttpServletRequest request, UserBean bean)
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
     * updateUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateUser(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        UserBean bean = new UserBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            ActionForward ret = checkAddOrUpdate(mapping, form, request, response, bean);

            if (ret != null)
            {
                return ret;
            }

            publicFacade.updateUserBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改用户");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改用户失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryUser");
    }

    /**
     * 用户、职员、角色必须在同一个区域
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param bean
     * @throws ServletException
     */
    private ActionForward checkAddOrUpdate(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response, UserBean bean)
        throws ServletException
    {
        StafferBean sb = stafferDAO.find(bean.getStafferId());

        if (sb == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "职员不存在");

            return this.preForAddUser(mapping, form, request, response);
        }

//        if ( !bean.getLocationId().equals(sb.getLocationId()))
//        {
//            request.setAttribute(KeyConstant.ERROR_MESSAGE, "职员和用户不存在同一个区域");
//
//            return this.preForAddUser(mapping, form, request, response);
//        }

        return null;
    }

    /**
     * delUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delUser(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.delUserBean(user.getId(), id);

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
     * initPassword
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward initPassword(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            String password = Security.getSecurity("123456789q~");

            publicFacade.updateUserPassword(user.getId(), id, password);

            ajax.setSuccess("初始化密码成功,密码是<br>123456789q~");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("初始化密码失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * unlock(解锁)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            User oldBean = userDAO.findVO(id);

            if (oldBean == null)
            {
                ajax.setError("用户不存在");

                return JSONTools.writeResponse(response, ajax);
            }

            if (oldBean.getStatus() != PublicConstant.LOGIN_STATUS_LOCK)
            {
                ajax.setError("用户没有被锁定");

                return JSONTools.writeResponse(response, ajax);
            }

            publicFacade.updateUserStatus(user.getId(), id, PublicConstant.LOGIN_STATUS_COMMON);

            ajax.setSuccess("用户成功被解锁");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("解锁失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * updateUserLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateUserLocation(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newLocationId = request.getParameter("newLocationId");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            publicFacade.updateUserLocation(user.getId(), id, newLocationId);

            ajax.setSuccess("成功修改用户分公司属性");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("修改失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findUser(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        setArrForAddOrUpdate(request, user);

        try
        {
            User bean = userDAO.findVO(id);

            request.setAttribute("bean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward("queryUser");
        }

        return mapping.findForward("updateUser");
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
     * @return the stafferTransferDAO
     */
    public StafferTransferDAO getStafferTransferDAO()
    {
        return stafferTransferDAO;
    }

    /**
     * @param stafferTransferDAO
     *            the stafferTransferDAO to set
     */
    public void setStafferTransferDAO(StafferTransferDAO stafferTransferDAO)
    {
        this.stafferTransferDAO = stafferTransferDAO;
    }
}
