/**
 * File Name: NetLoginAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.action;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.dao.ProductTypeVSCustomerDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.ProviderUserDAO;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;
import com.china.center.oa.publics.bean.MenuItemBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.MenuItemDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.tools.ListTools;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * NetLoginAction
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see NetLoginAction
 * @since 1.0
 */
public class NetLoginAction extends DispatchAction
{
    private ProviderUserDAO providerUserDAO = null;

    private ParameterDAO parameterDAO = null;

    private ProviderDAO providerDAO = null;

    private ProductTypeVSCustomerDAO productTypeVSCustomerDAO = null;

    private MenuItemDAO menuItemDAO = null;

    /**
     * loginAsk
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse reponse)
        throws ServletException
    {
        String longinName = request.getParameter("userName");

        String password = request.getParameter("password");

        String rand = request.getParameter("rand");

        String key = request.getParameter("key");

        boolean real = getContorl();

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

        ProviderUserBean puser = providerUserDAO.findByUnique(longinName);

        if (puser == null)
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误");

            return mapping.findForward("error");
        }

        User user = getUser(puser);

        ProviderBean provider = providerDAO.find(puser.getProvideId());

        if (provider == null)
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "供应商不存在");

            return mapping.findForward("error");
        }

        // 验证密码
        if ( !real || puser.getPassword().equals(Security.getSecurity(password)))
        {
            request.getSession().setAttribute("user", user);

            request.getSession().setAttribute("GLocationName", "系统");

            request.getSession().setAttribute("GProvider", provider);

            request.getSession().setAttribute("g_stafferBean", user);

            request.getSession().setAttribute("SNFLAG", "ASK");

            request.getSession().setAttribute("SN", "询价系统");

            request.getSession().setAttribute("GTime", TimeTools.now("yyyy-MM-dd"));

            List<ProductTypeVSCustomer> list = productTypeVSCustomerDAO.queryEntityBeansByFK(user.getId(), 1);

            if (ListTools.isEmptyOrNull(list))
            {
                request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "供应商没有绑定产品分类,无法登陆");

                return mapping.findForward("error");
            }

            Map<String, List<MenuItemBean>> menuItemMap = new HashMap<String, List<MenuItemBean>>();

            List<MenuItemBean> result = filterMenuItem(user, menuItemMap);

            request.getSession().setAttribute("menuRootList", result);

            request.getSession().setAttribute("typeList", list);

            request.getSession().setAttribute("menuItemMap", menuItemMap);
        }
        else
        {
            request.getSession().setAttribute(KeyConstant.ERROR_MESSAGE, "用户名或密码错误");

            return mapping.findForward("error");
        }

        request.getSession().setAttribute("gkey", key);

        JSONArray auths = new JSONArray(user.getAuth(), true);

        request.getSession().setAttribute("authJSON", auths.toString());

        request.getSession().setAttribute("g_logout", "../netask/checkuser.do?method=logout");

        request.getSession().setAttribute("g_modifyPassword", "../netask/modifyPassword.jsp");

        ActionForward forward = mapping.findForward("success");

        String path = forward.getPath();

        return new ActionForward(path, true);
    }

    /**
     * logout
     * 
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param response
     * @return
     */
    public ActionForward logout(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);

        if (session == null)
        {
            return actionMapping.findForward("index");
        }

        session.invalidate();

        return actionMapping.findForward("index");
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

            if (item.getBottomFlag() != PublicConstant.BOTTOMFLAG_YES)
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

            menuItemMap.get(menuItemBean.getParentId()).add(menuItemBean);

            Collections.sort(menuItemMap.get(menuItemBean.getParentId()), new Comparator<MenuItemBean>()
            {
                public int compare(MenuItemBean o1, MenuItemBean o2)
                {
                    return o1.getIndexPos() - o2.getIndexPos();
                }
            });
        }

        Collections.sort(rootMenus, new Comparator<MenuItemBean>()
        {
            public int compare(MenuItemBean o1, MenuItemBean o2)
            {
                return o1.getIndexPos() - o2.getIndexPos();
            }

        });

        return rootMenus;
    }

    /**
     * modifyPassword
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward modifyPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse reponse)
        throws ServletException
    {
        String oldPassword = request.getParameter("oldPassword");

        String newPassword = request.getParameter("newPassword");

        User user = (User)request.getSession().getAttribute("user");

        ProviderUserBean puser = providerUserDAO.find(user.getStafferId());

        if (puser == null)
        {
            request.setAttribute(KeyConstant.MESSAGE, "用户不存在");

            return mapping.findForward("password");
        }

        if ( !puser.getPassword().equals(Security.getSecurity(oldPassword)))
        {
            request.setAttribute(KeyConstant.MESSAGE, "原密码错误");

            return mapping.findForward("password");
        }

        // 修改外网询价用户的密码
        String md5 = Security.getSecurity(newPassword);

        providerUserDAO.updatePassword(user.getStafferId(), md5);

        request.setAttribute(KeyConstant.MESSAGE, "密码修改成功");

        return mapping.findForward("password");

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

    /**
     * getContorl
     * 
     * @return
     */
    private boolean getContorl()
    {
        return parameterDAO.getBoolean("REAL_LOGIN");
    }

    /**
     * getUser
     * 
     * @param puser
     * @return
     */
    private User getUser(ProviderUserBean puser)
    {
        UserVO user = new UserVO();

        user.setName(puser.getName());

        user.setStafferId(puser.getId());

        // 供应商的ID
        user.setId(puser.getProvideId());

        user.setStafferName(puser.getName());

        // 这里构建权限
        RoleAuthBean auth = new RoleAuthBean();

        auth.setAuthId(AuthConstant.PRICE_ASK_NET_PROCESS);

        List<RoleAuthBean> authList = new ArrayList<RoleAuthBean>();

        authList.add(auth);

        user.setAuth(authList);

        return user;
    }

    /**
     * @return the providerUserDAO
     */
    public ProviderUserDAO getProviderUserDAO()
    {
        return providerUserDAO;
    }

    /**
     * @param providerUserDAO
     *            the providerUserDAO to set
     */
    public void setProviderUserDAO(ProviderUserDAO providerUserDAO)
    {
        this.providerUserDAO = providerUserDAO;
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
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
    }

    /**
     * @return the productTypeVSCustomerDAO
     */
    public ProductTypeVSCustomerDAO getProductTypeVSCustomerDAO()
    {
        return productTypeVSCustomerDAO;
    }

    /**
     * @param productTypeVSCustomerDAO
     *            the productTypeVSCustomerDAO to set
     */
    public void setProductTypeVSCustomerDAO(ProductTypeVSCustomerDAO productTypeVSCustomerDAO)
    {
        this.productTypeVSCustomerDAO = productTypeVSCustomerDAO;
    }

    /**
     * @return the menuItemDAO
     */
    public MenuItemDAO getMenuItemDAO()
    {
        return menuItemDAO;
    }

    /**
     * @param menuItemDAO
     *            the menuItemDAO to set
     */
    public void setMenuItemDAO(MenuItemDAO menuItemDAO)
    {
        this.menuItemDAO = menuItemDAO;
    }

}
