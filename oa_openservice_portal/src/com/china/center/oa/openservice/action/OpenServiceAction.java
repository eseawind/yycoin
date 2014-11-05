package com.china.center.oa.openservice.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.MenuItemDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.LoginHelper;
import com.china.center.oa.publics.manager.MenuManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.ListTools;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.webplugin.inter.WebPathListener;
import com.china.center.webportal.listener.MySessionListener;

/**
 * 开放服务 OpenServiceAction
 * 
 * @author fangliwen 2012-10-26
 */
public class OpenServiceAction extends DispatchAction 
{
    
    private final Log           _accessLog    = LogFactory.getLog("access");

    private final Log           _logger       = LogFactory.getLog(getClass());

    private UserDAO             userDAO       = null;

    private UserManager         userManager   = null;

    private LocationDAO         locationDAO   = null;

    private MenuItemDAO         menuItemDAO   = null;

    private RoleAuthDAO         roleAuthDAO   = null;

    private MenuManager         menuManager   = null;

    private ParameterDAO        parameterDAO  = null;

    private StafferDAO          stafferDAO    = null;

    private static final String LOGIN_URLPATH = "/admin/main.jsp";

    
    @SuppressWarnings("unchecked")
    public ActionForward loginService(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {

        boolean hasEncLock = true;

        UserVO user = null;

        String spassword = "9999";

        StafferVO stafferBean = null;

        // 外部登陆必须 密码+加密狗登录 - 用户名要重编码, 密码要加密
        String longinName = request.getParameter("userName");

        String password = request.getParameter("password");

        String superRand = request.getParameter("superRand");

        String encSuperRand = request.getParameter("encSuperRand");

        String key = request.getParameter("key");

        user = userDAO.findUserByName(longinName);

        if (user == null) 
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误");
            
            return mapping.findForward("error");
        }

        // 锁定处理
        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK) {
            _accessLog.info(logLogin(request, user, false) + ',' + spassword);

            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户被锁定,请联系管理员解锁!");
            
            return mapping.findForward("error");
        }

        stafferBean = stafferDAO.findVO(user.getStafferId());

        if (stafferBean == null) 
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误!");
            
            return mapping.findForward("error");

        }

        if (stafferBean.getStatus() == StafferConstant.STATUS_DROP) 
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "该用户已废弃!");
            
            return mapping.findForward("error");

        }

        // 验证密码与UKEY
        if (user.getPassword().equals(Security.getSecurity(password))
                && handleEncLock(key, encSuperRand, superRand, true, stafferBean.getPwkey())) 
        {
            String locationId = user.getLocationId();

            if (!PublicConstant.VIRTUAL_LOCATION.equals(locationId))
            {
                LocationBean location = locationDAO.find(locationId);

                if (location == null)
                {
                    request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "登录的区域不存在");
                    
                    return mapping.findForward("error");
                }

                request.getSession().setAttribute("GLocationName", location.getName());
            }
            else
            {
                request.getSession().setAttribute("GLocationName", "系统");
            }

            StafferBean sb = stafferDAO.find(user.getStafferId());

            if (sb == null)
            {
                request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "登录的职员不存在");
                
                return mapping.findForward("error");
            }

            request.getSession().setAttribute("g_staffer", sb);
            
            handleLoginSucess(request, spassword, user);
        }
        else 
        {
            if ((user.getFail() + 1) >= PublicConstant.LOGIN_FAIL_MAX) {
                userDAO.updateStatus(user.getName(), PublicConstant.LOGIN_STATUS_LOCK);

                userDAO.updateFail(user.getName(), 0);
            } else {
                userDAO.updateFail(user.getName(), user.getFail() + 1);
            }
            
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误或UKEY无效");
            
            return mapping.findForward("error");
        }

        request.getSession().setAttribute("g_loginType", "0");

        // processCity(user, request);

        JSONArray auths = new JSONArray(user.getAuth(), true);

        request.getSession().setAttribute("authJSON", auths.toString());

        request.getSession().setAttribute("gkey", key);

        // 默认是20
        request.getSession().setAttribute("g_page",
                parameterDAO.getInt(SysConfigConstant.GLOBAL_PAGE_SIZE));

        request.getSession().setAttribute("hasEncLock", hasEncLock);

        request.getSession().setAttribute("g_stafferBean", stafferBean);

        request.getSession().setAttribute("g_logout", "../admin/logout.do");

        request.getSession().setAttribute("g_modifyPassword", "../admin/modifyPassword.jsp");

        // OA系统/SKY软件【V2.14.20100509】
        request.getSession().setAttribute("SN",
                "永银ERP系统【" + ConfigLoader.getProperty("version") + "】");

        String path = LOGIN_URLPATH;

        MySessionListener.getSessionSets().add(request.getSession().getId());

        WebPathListener.getGMap().put("SA", MySessionListener.getSessionSets().size());
        
        _logger.info("外部登陆成功!");
        
        return new ActionForward(path, true);

    }

    private String logLogin(HttpServletRequest request, User user, boolean success) {
        return request.getRemoteAddr() + ',' + user.getName() + ',' + user.getStafferId() + ','
                + success;
    }

    private boolean handleEncLock(String key, String randKey, String randVal, boolean hasEncLock,
            String pwkey) 
    {       
        return LoginHelper.encRadomStr(pwkey, key, randVal).equals(randKey)
                || (LoginHelper.encRadomStr(pwkey, key, randVal) + "00").equals(randKey);
           
        
    }

    private void handleLoginSucess(HttpServletRequest request, String spassword, UserVO user)
    {
        user.setPassword("");

        // 记录访问日志
        _accessLog.info(logLogin(request, user, true) + ',' + spassword);

        userManager.updateFail(user.getId(), 0);

        userManager.updateLogTime(user.getId(), TimeTools.now());

        request.getSession().setAttribute(PublicConstant.CURRENTLOCATIONID, user.getLocationId());

        request.getSession().setAttribute("user", user);

        request.getSession().setAttribute("GTime", TimeTools.now("yyyy-MM-dd"));

        Map<String, List<MenuItemBean>> menuItemMap = new HashMap<String, List<MenuItemBean>>();

        // get auth by role
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(user.getRoleId());

        RoleAuthBean publicAuth = new RoleAuthBean();

        publicAuth.setId(AuthConstant.PUNLIC_AUTH);

        publicAuth.setAuthId(AuthConstant.PUNLIC_AUTH);

        authList.add(publicAuth);

        user.setAuth(authList);

        List<MenuItemBean> result = filterMenuItem(user, menuItemMap);

        request.getSession().setAttribute("menuRootList", result);

        request.getSession().setAttribute("menuItemMap", menuItemMap);
    }
    
    /**
     * 过滤菜单
     * 
     * @param user
     * @return 跟节点的菜单
     */
    @SuppressWarnings("unchecked")
    private List<MenuItemBean> filterMenuItem(User user, Map<String, List<MenuItemBean>> menuItemMap)
    {
        List<MenuItemBean> list = menuItemDAO.listEntityBeans();

        List<MenuItemBean> result = ListTools.newList(MenuItemBean.class);

        List<MenuItemBean> rootMenus = new ArrayList<MenuItemBean>();

        Map<String, MenuItemBean> menuRootMap = new HashMap<String, MenuItemBean>();

        for (int i = 0; i < list.size(); i++ )
        {
            MenuItemBean item = list.get(i);

            // 根节点
            if (item.getBottomFlag() == PublicConstant.BOTTOMFLAG_NO)
            {
                menuRootMap.put(item.getId(), item);

                continue;
            }

            // 这里支持1101,1102
            String auth = item.getAuth();

            if (StringTools.isNullOrNone(auth))
            {
                continue;
            }

            if (containAuth(user, auth))
            {
                result.add(item);

                continue;
            }
        }

        for (int i = 0; i < result.size(); i++ )
        {
            MenuItemBean menuItemBean = result.get(i);

            if ( !menuItemMap.containsKey(menuItemBean.getParentId()))
            {
                rootMenus.add(menuRootMap.get(menuItemBean.getParentId()));

                menuItemMap.put(menuItemBean.getParentId(), new ArrayList<MenuItemBean>());
            }

            // 把二级子菜单放入list
            menuItemMap.get(menuItemBean.getParentId()).add(menuItemBean);
        }

        // 二级子菜单排序
        for (Map.Entry<String, List<MenuItemBean>> each : menuItemMap.entrySet())
        {
            // 放入扩展菜单
            List<MenuItemBean> expandMenuList = menuManager.onFindExpandMenu(user, each.getKey());

            if ( !ListTools.isEmptyOrNull(expandMenuList))
            {
                each.getValue().addAll(expandMenuList);
            }

            Collections.sort(each.getValue(), new Comparator<MenuItemBean>()
            {
                public int compare(MenuItemBean o1, MenuItemBean o2)
                {
                    return o1.getIndexPos() - o2.getIndexPos();
                }
            });

            // 统一加menu
            List<MenuItemBean> twoList = each.getValue();

            for (MenuItemBean menuItemBean : twoList)
            {
                if (StringTools.isNullOrNone(menuItemBean.getUrl()))
                {
                    continue;
                }

                if (menuItemBean.getUrl().indexOf("menu=1") != -1)
                {
                    continue;
                }

                if (menuItemBean.getUrl().indexOf("?") != -1)
                {
                    menuItemBean.setUrl(menuItemBean.getUrl() + "&menu=1");
                }
                else
                {
                    menuItemBean.setUrl(menuItemBean.getUrl() + "?menu=1");
                }
            }
        }

        // 一级菜单排序
        Collections.sort(rootMenus, new Comparator<MenuItemBean>()
        {
            public int compare(MenuItemBean o1, MenuItemBean o2)
            {
                return o1.getIndexPos() - o2.getIndexPos();
            }

        });

        return rootMenus;
    }
    
    @SuppressWarnings("unchecked")
    private boolean containAuth(User user, String auth)
    {
        String[] authArr = auth.split(",");

        List<RoleAuthBean> auths = user.getAuth();

        for (RoleAuthBean roleAuthBean : auths)
        {
            for (String eachAuth : authArr)
            {
                if (StringTools.isNullOrNone(eachAuth))
                {
                    continue;
                }

                if (roleAuthBean.getAuthId().equals(eachAuth.trim()))
                {
                    return true;
                }
            }
        }

        return false;
    }    
    
    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public MenuItemDAO getMenuItemDAO() {
        return menuItemDAO;
    }

    public void setMenuItemDAO(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }

    public RoleAuthDAO getRoleAuthDAO() {
        return roleAuthDAO;
    }

    public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO) {
        this.roleAuthDAO = roleAuthDAO;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public ParameterDAO getParameterDAO() {
        return parameterDAO;
    }

    public void setParameterDAO(ParameterDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }
    
    
}
