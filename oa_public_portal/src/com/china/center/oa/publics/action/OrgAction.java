/**
 * File Name: OrgAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.util.ArrayList;
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

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.jsonimpl.JSONObject;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.OrgDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.OrgVO;
import com.china.center.oa.publics.vo.StafferVSPriVO;
import com.china.center.oa.publics.vs.OrgBean;
import com.china.center.oa.publics.wrap.StafferOrgWrap;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * OrgAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see OrgAction
 * @since 1.0
 */
public class OrgAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private LocationDAO locationDAO = null;

    private OrgDAO orgDAO = null;

    private PrincipalshipDAO principalshipDAO = null;

    private PublicFacade publicFacade = null;

    private StafferDAO stafferDAO = null;

    private OrgManager orgManager = null;

    private StafferVSPriDAO stafferVSPriDAO = null;

    /**
     * default constructor
     */
    public OrgAction()
    {
    }

    /**
     * queryOrg
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryOrg(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        preForListAllOrgTree(request);

        return mapping.findForward("queryOrg");
    }

    /**
     * 查询人员树
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStafferOrg(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        try
        {
            preForListAllStafferOrgTree(request, "0");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward("error");
        }

        return mapping.findForward("queryStafferOrg");
    }

    /**
     * 弹出org
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward popOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        preForListAllOrgTree(request);

        return mapping.findForward("popOrg");
    }

    /**
     * rptQueryOrg
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryOrg(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        return popOrg(mapping, form, request, response);
    }

    /**
     * 为准备显示整个组织树准备
     * 
     * @param request
     */
    private void preForListAllOrgTree(HttpServletRequest request)
    {
        
        List<PrincipalshipBean> plist = principalshipDAO.listEntityBeansByOrder("order by level");

        JSONArray jarr = new JSONArray(plist, true);

        request.setAttribute("shipList", jarr.toString());

        Map<String, List<OrgVO>> map = new HashMap<String, List<OrgVO>>();

        for (PrincipalshipBean principalshipBean : plist)
        {
            List<OrgVO> vos = orgDAO.queryEntityVOsByFK(principalshipBean.getId());
            
            for(OrgVO orgVO : vos){
                
                if (orgVO.getStatus()== PublicConstant.ORG_STATUS_STOP){
                    
                    orgVO.setSubName(orgVO.getSubName()+"(已停用)");
                }
            }
            
            map.put(principalshipBean.getId(), vos);
        }

        JSONObject object = new JSONObject();

        object.createMapList(map, true);

        request.setAttribute("mapJSON", object.toString());
    }

    /**
     * 准备人员树
     * 
     * @param request
     * @throws MYException
     */
    private void preForListAllStafferOrgTree(HttpServletRequest request, String prinRootId)
        throws MYException
    {
        List<PrincipalshipBean> plist = orgManager.querySubPrincipalship(prinRootId);

        Map<String, List<StafferOrgWrap>> map = new HashMap<String, List<StafferOrgWrap>>();

        // 循环组织
        for (PrincipalshipBean principalshipBean : plist)
        {
            // 查询下一级岗位
            List<PrincipalshipBean> vos = principalshipDAO.querySubPrincipalship(principalshipBean
                .getId());

            List<StafferOrgWrap> wraps = new ArrayList<StafferOrgWrap>();

            for (PrincipalshipBean orgBean : vos)
            {
                createWrap(wraps, orgBean);
            }

            map.put(principalshipBean.getId(), wraps);
        }

        PrincipalshipBean root = principalshipDAO.find(prinRootId);

        JSONObject object = new JSONObject();

        object.createMapList(map, true);

        request.setAttribute("mapJSON", object.toString());

        JSONObject rootObj = new JSONObject(root);

        List<StafferOrgWrap> rootWraps = new ArrayList<StafferOrgWrap>();

        createWrap(rootWraps, root);

        request.setAttribute("rootStaffer", rootWraps.get(0).getStafferName());

        request.setAttribute("root", rootObj.toString());
    }

    private void createWrap(List<StafferOrgWrap> wraps, PrincipalshipBean orgBean)
    {
        // 子组织下的人员
        List<StafferVSPriVO> pplist = stafferVSPriDAO.queryEntityVOsByFK(orgBean.getId(),
            AnoConstant.FK_FIRST);

        if (pplist.isEmpty())
        {
            StafferOrgWrap wrap = new StafferOrgWrap();

            wrap.setStafferId(orgBean.getId());

            wrap.setStafferName("缺 额");

            wrap.setPrincipalshipId(orgBean.getId());

            wrap.setPrincipalshipName(orgBean.getName());

            wrap.setPersonal(1);

            wraps.add(wrap);
        }
        else
        {
            StafferOrgWrap wrap = new StafferOrgWrap();

            wrap.setStafferId(orgBean.getId());

            StringBuilder buffer = new StringBuilder();

            for (StafferVSPriVO stafferBean2 : pplist)
            {
                buffer.append(stafferBean2.getStafferName()).append("/");
            }

            buffer.deleteCharAt(buffer.length() - 1);

            wrap.setStafferName(buffer.toString());

            wrap.setPrincipalshipId(orgBean.getId());

            wrap.setPrincipalshipName(orgBean.getName());

            wraps.add(wrap);
        }
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
    public ActionForward preForAddOrg(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        preForListAllOrgTree(request);

        return mapping.findForward("addOrg");
    }

    /**
     * addOrg
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException
    {
        PrincipalshipBean bean = new PrincipalshipBean();

        try
        {
            BeanUtil.getBean(bean, request);

            createOrgList(request, bean);

            User user = Helper.getUser(request);

            publicFacade.addOrgBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加组织:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加组织失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return queryOrg(mapping, form, request, response);
    }

    /**
     * createOrgList
     * 
     * @param request
     * @param bean
     */
    private void createOrgList(HttpServletRequest request, PrincipalshipBean bean)
    {
        String[] auths = request.getParameterValues("tree_checkbox");

        List<OrgBean> orgList = new ArrayList<OrgBean>();

        if (auths != null && auths.length > 0)
        {
            for (String item : auths)
            {
                OrgBean rab = new OrgBean();

                rab.setParentId(item);

                rab.setSubId(bean.getId());

                orgList.add(rab);
            }
        }

        bean.setParentOrgList(orgList);

        bean.setParentId(orgList.get(0).getParentId());

        PrincipalshipBean parent = principalshipDAO.find(bean.getParentId());

        bean.setLevel(parent.getLevel() + 1);
    }

    /**
     * updateOrg
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateOrg(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        PrincipalshipBean bean = new PrincipalshipBean();

        // String modifyParent = request.getParameter("modifyParent");
        try
        {
            BeanUtil.getBean(bean, request);

            // createOrgList(request, bean);

            LocationBean oldLocation = locationDAO.find(bean.getId());

            if (oldLocation != null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此组织只能在分公司管理里面操作");

                return queryOrg(mapping, form, request, response);
            }

            User user = Helper.getUser(request);

            publicFacade.updateOrgBean(user.getId(), bean, false);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改组织:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改组织失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return queryOrg(mapping, form, request, response);
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
    public ActionForward delOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
        throws ServletException
    {
        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            LocationBean oldLocation = locationDAO.find(id);

            if (oldLocation != null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此组织只能在分公司管理里面操作");

                return queryOrg(mapping, form, request, response);
            }

            publicFacade.delOrgBean(user.getId(), id);

            request.setAttribute(KeyConstant.MESSAGE, "成功删除组织");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "删除组织失败:" + e.getMessage());
        }

        return queryOrg(mapping, form, request, response);
    }

    /**
     * findOrg
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findOrg(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String update = request.getParameter("update");

        String id = request.getParameter("id");
        try
        {

            PrincipalshipBean bean = principalshipDAO.find(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "组织不存在");

                return queryOrg(mapping, form, request, response);
            }

            request.setAttribute("bean", bean);

            List<OrgVO> vos = orgDAO.queryEntityVOsByFK(id, AnoConstant.FK_FIRST);            

            String parentName = "";

            for (OrgVO orgVO : vos)
            {
                parentName += orgVO.getParentName() + " ";
            }

            request.setAttribute("parentName", parentName);

        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "删除组织失败:" + e.getMessage());
        }

        if ("1".equals(update))
        {
            LocationBean oldLocation = locationDAO.find(id);

            if (oldLocation != null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此组织只能在分公司管理里面操作");

                return mapping.findForward("error");
            }

            preForListAllOrgTree(request);

            return mapping.findForward("updateOrg");
        }
        else
        {
            return mapping.findForward("findOrg");
        }
    }

    /**
     * @return the orgDAO
     */
    public OrgDAO getOrgDAO()
    {
        return orgDAO;
    }

    /**
     * @param orgDAO
     *            the orgDAO to set
     */
    public void setOrgDAO(OrgDAO orgDAO)
    {
        this.orgDAO = orgDAO;
    }

    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO()
    {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO
     *            the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
    {
        this.principalshipDAO = principalshipDAO;
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
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
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
     * @return the stafferVSPriDAO
     */
    public StafferVSPriDAO getStafferVSPriDAO()
    {
        return stafferVSPriDAO;
    }

    /**
     * @param stafferVSPriDAO
     *            the stafferVSPriDAO to set
     */
    public void setStafferVSPriDAO(StafferVSPriDAO stafferVSPriDAO)
    {
        this.stafferVSPriDAO = stafferVSPriDAO;
    }

}
