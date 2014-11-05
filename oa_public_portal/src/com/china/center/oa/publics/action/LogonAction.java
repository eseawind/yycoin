/**
 * File Name: LogoutAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.jsonimpl.JSONObject;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.MenuItemDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.manager.MenuManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.ListTools;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * 
 * 请输入功能描述
 *
 * @author fangliwen 2013-4-11
 */
public class LogonAction extends DispatchAction
{
	private final Log _accessLog = LogFactory.getLog("access");
	
    private UserDAO userDAO = null;
    
    private StafferDAO stafferDAO = null;

    private UserManager userManager = null;
    
    private MenuItemDAO menuItemDAO = null;

    private RoleAuthDAO roleAuthDAO = null;

    private MenuManager menuManager = null;
    
    private ProvinceDAO provinceDAO = null;

    private CityDAO cityDAO = null;
    
    private AreaDAO areaDAO = null;
    
    /**
     * simple login
     * 
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param response
     * @return
     */
    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse reponse)
	{
    	boolean passwordEquals = false;
    	
    	UserVO user = null;
    	
    	StafferVO stafferBean = null;
    	
    	String longinName = request.getParameter("userName");

        String password = request.getParameter("password");

        String rand = request.getParameter("rand");
        
        ActionForward checkCommonResult = checkCommon(mapping, request, rand, true);

        if (checkCommonResult != null)
        {
            return checkCommonResult;
        }
        
        user = userDAO.findUserByName(longinName);

        if (user == null)
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误");
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
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误!");

            return mapping.findForward("error");
        }

        // 验证密码
        passwordEquals = user.getPassword().equals(Security.getSecurity(password));
        
        if (!passwordEquals)
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误!");

            return mapping.findForward("error");
        }
        
        handleLoginSucess(request, "", user);
        
        processCity(user, request);
        
        JSONArray auths = new JSONArray(user.getAuth(), true);

        request.getSession().setAttribute("authJSON", auths.toString());
        
        request.getSession().setAttribute("user", user);
        
        ActionForward forward = mapping.findForward("index");

        String path = forward.getPath();

        return new ActionForward(path, true);
	}
    
    /**
     * @param request
     */
    public void processCity(User user, HttpServletRequest request)
    {
        List<ProvinceBean> plist = provinceDAO.listEntityBeans();

        Map<String, List<CityBean>> map = new HashMap<String, List<CityBean>>();

        for (ProvinceBean provinceBean : plist)
        {
            List<CityBean> clist = cityDAO.queryEntityBeansByFK(provinceBean.getId());

            map.put(provinceBean.getId(), clist);
        }
        
        List<CityBean> clist = cityDAO.listEntityBeans();
        
        Map<String, List<AreaBean>> areaMap = new HashMap<String, List<AreaBean>>();

        for (CityBean cityBean : clist)
        {
            List<AreaBean> alist = areaDAO.queryEntityBeansByFK(cityBean.getId());

            areaMap.put(cityBean.getId(), alist);
        }

        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();

        JSONArray jarr = new JSONArray(plist, true);

        object.createMapList(map, false);
        object1.createMapList(areaMap, false);

        request.getSession().setAttribute("areaStrJSON", object1.toString());
        
        request.getSession().setAttribute("jsStrJSON", object.toString());

        request.getSession().setAttribute("pStrJSON", jarr.toString());
    }
    
    private ActionForward checkCommon(ActionMapping mapping, HttpServletRequest request,
            String rand, boolean real)
	{
    	if (real && rand == null)
    	{
    	request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "验证码错误");
    	return mapping.findForward("error");
    	}
    	
    	Object oo = request.getSession().getAttribute("rand");
    	
    	if (real && oo == null)
    	{
    	request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "验证码错误");
    	return mapping.findForward("error");
    	}
    	
    	if (real && !rand.equalsIgnoreCase(oo.toString()))
    	{
    	request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "验证码错误");
    	return mapping.findForward("error");
    	}
    	
    	return null;
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
    
    private String logLogin(HttpServletRequest request, User user, boolean success)
    {
    	User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
    	
    	String srcUser = "";
    	
    	if (null != g_srcUser)
    		srcUser = "/原用户:"+g_srcUser.getStafferName();
    	
        return request.getRemoteAddr() + ',' + user.getName() + ',' + user.getStafferId() + srcUser + ','
               + success;
    }
    
	public UserDAO getUserDAO()
	{
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public UserManager getUserManager()
	{
		return userManager;
	}

	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}

	public MenuItemDAO getMenuItemDAO()
	{
		return menuItemDAO;
	}

	public void setMenuItemDAO(MenuItemDAO menuItemDAO)
	{
		this.menuItemDAO = menuItemDAO;
	}

	public RoleAuthDAO getRoleAuthDAO()
	{
		return roleAuthDAO;
	}

	public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO)
	{
		this.roleAuthDAO = roleAuthDAO;
	}

	public MenuManager getMenuManager()
	{
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager)
	{
		this.menuManager = menuManager;
	}

	/**
	 * @return the provinceDAO
	 */
	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	/**
	 * @param provinceDAO the provinceDAO to set
	 */
	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	/**
	 * @return the cityDAO
	 */
	public CityDAO getCityDAO() {
		return cityDAO;
	}

	/**
	 * @param cityDAO the cityDAO to set
	 */
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * @return the areaDAO
	 */
	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	/**
	 * @param areaDAO the areaDAO to set
	 */
	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}
}
