/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.jsonimpl.JSONObject;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.actionhelper.query.QueryConfig;
import com.china.center.actionhelper.query.QueryItemBean;
import com.china.center.common.MYException;
import com.china.center.common.taglib.MapBean;
import com.china.center.common.taglib.PageSelectOption;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.InvoiceCreditDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.OrgDAO;
import com.china.center.oa.publics.dao.PostDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferTransferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.helper.StafferHelper;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.vo.InvoiceCreditVO;
import com.china.center.oa.publics.vo.OrgVO;
import com.china.center.oa.publics.vo.StafferTransferVO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.vs.StafferTransferBean;
import com.china.center.oa.publics.vs.StafferVSPriBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.DecSecurity;
import com.china.center.tools.MathTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StafferAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see StafferAction
 * @since 1.0
 */
public class StafferAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private StafferDAO stafferDAO = null;

    private LocationManager locationManager = null;

    private LocationDAO locationDAO = null;

    private PrincipalshipDAO principalshipDAO = null;
    
    private OrgManager orgManager = null;

    private DepartmentDAO departmentDAO = null;

    private PostDAO postDAO = null;

    private InvoiceCreditDAO invoiceCreditDAO = null;

    private OrgDAO orgDAO = null;

    private StafferVSPriDAO stafferVSPriDAO = null;

    private StafferManager stafferManager = null;

    private StafferTransferDAO stafferTransferDAO = null;
    
    private UserDAO userDAO = null;

    private QueryConfig queryConfig = null;

    private PublicFacade publicFacade = null;

    private static String QUERYSTAFFER = "queryStaffer";

    private static String QUERYINVOICECREDIT = "queryInvoiceCredit";

    /**
     * default constructor
     */
    public StafferAction()
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
    public ActionForward queryStaffer(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        if ( !LocationHelper.isVirtualLocation(user.getLocationId()))
        {
            // condtion.addCondition("StafferBean.locationId", "=", user.getLocationId());
        }

        // condtion.addIntCondition("StafferBean.status", "=", StafferConstant.STATUS_COMMON);

        ActionTools.processJSONQueryCondition(QUERYSTAFFER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTAFFER, request, condtion,
            this.stafferDAO, new HandleResult<StafferVO>()
            {
                public void handle(StafferVO vo)
                {
                    if (StafferHelper.hasEnc(vo))
                    {
                        vo.setEnc("设置");
                    }
                    else
                    {
                        vo.setEnc("未设置");
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryInvoiceCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryInvoiceCredit(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYINVOICECREDIT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYINVOICECREDIT, request,
            condtion, this.invoiceCreditDAO, new HandleResult<InvoiceCreditVO>()
            {
                public void handle(InvoiceCreditVO obj)
                {
                    PrincipalshipBean pb = principalshipDAO.find(obj.getInvoiceId());

                    PrincipalshipBean parent = principalshipDAO.find(pb.getParentId());

                    obj.setInvoiceName(parent.getName() + "-->" + obj.getInvoiceName());
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddStaffer(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
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

        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
        condition.addIntCondition("status", "=", PublicConstant.ORG_STATUS_USED);
        
        //List<PrincipalshipBean> priList = principalshipDAO.listEntityBeans();
        
        List<PrincipalshipBean> priList = principalshipDAO.queryEntityBeansByCondition(condition);
        request.setAttribute("priList", priList);

        List<DepartmentBean> depList = departmentDAO.listEntityBeans();
        request.setAttribute("depList", depList);

        List<PostBean> postList = postDAO.listEntityBeans();
        request.setAttribute("postList", postList);

        preForListAllOrgTree(request);

        return mapping.findForward("addStaffer");
    }

    /**
     * preForTransferWork
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForTransferWork(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String srcId = request.getParameter("srcId");

        StafferBean src = stafferDAO.find(srcId);

        if (src == null)
        {
            return ActionTools.toError("移交的职员不存在", mapping, request);
        }

        StafferTransferVO srcTransfer = stafferTransferDAO.findVOByUnique(srcId);

        request.setAttribute("src", src);

        request.setAttribute("srcTransfer", srcTransfer);

        return mapping.findForward("transferWork");
    }

    /**
     * transferWork
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward transferWork(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StafferTransferBean bean = new StafferTransferBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            stafferManager.configStafferTransfer(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功移交工作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "移交工作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStaffer");
    }

    /**
     * preForFindStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForFindStaffer(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        request.setAttribute("sid", Helper.getStaffer(request).getId());

        return findStaffer(mapping, form, request, response);
    }

    /**
     * preForSetpwkey
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForSetpwkey(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        StafferVO bean = stafferDAO.findVO(id);

        request.setAttribute("bean", bean);

        if (StafferHelper.hasEnc(bean))
        {
            request.setAttribute("hasSet", true);
        }
        else
        {
            request.setAttribute("hasSet", false);
        }

        return mapping.findForward("updatePwkey");
    }

    /**
     * updatePwkey
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updatePwkey(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String key = request.getParameter("key").toUpperCase();

        String md5key = Security.getSecurity(key);

        String deskeygen = md5key.substring(0, 4)
                           + md5key.substring(md5key.length() - 4, md5key.length());

        StafferBean bean = stafferDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("职员不存在", "queryStaffer", mapping, request);
        }

        String enKey = RandomTools.getRandomMumber(32);

        bean.setPwkey(DecSecurity.encrypt(enKey, deskeygen));

        try
        {
            User user = Helper.getUser(request);

            publicFacade.updateStafferPwkey(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功设置加密锁:" + enKey + ".请拷贝32位锁至KeyTool进行设置");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改加密锁失败:" + e.getMessage());
        }

        return preForSetpwkey(mapping, form, request, response);
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
        QueryItemBean query = queryConfig.findQueryCondition(QUERYSTAFFER);

        if (query == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有配置查询,请核实");

            return mapping.findForward("queryStaffer");
        }

        // postList
        List<PostBean> postList = postDAO.listEntityBeans();

        List<DepartmentBean> departmentList = departmentDAO.listEntityBeans();

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        Map<String, List> selectMap = new HashMap<String, List>();

        selectMap.put("postList", postList);
        selectMap.put("departmentList", departmentList);
        selectMap.put("locationList", locationList);

        List<MapBean> el = PageSelectOption.optionMap.get("examType");

        selectMap.put("$examType", el);

        request.setAttribute("selectMap", selectMap);

        request.setAttribute("query", query);

        return mapping.findForward("commonQuery");
    }

    /**
     * addStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addStaffer(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StafferBean bean = new StafferBean();

        try
        {
            BeanUtil.getBean(bean, request);

            bean.setLogTime(TimeTools.now());

            String[] pris = request.getParameterValues("tree_checkbox");

            if (pris != null)
            {
                for (String each : pris)
                {
                    StafferVSPriBean vs = new StafferVSPriBean();

                    vs.setPrincipalshipId(each);

                    bean.getPriList().add(vs);
                }
            }

            User user = Helper.getUser(request);

            publicFacade.addStafferBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStaffer");
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
    public ActionForward updateStaffer(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StafferBean bean = new StafferBean();

        try
        {
            BeanUtil.getBean(bean, request);

            bean.setLogTime(TimeTools.now());

            User user = Helper.getUser(request);

            String[] pris = request.getParameterValues("tree_checkbox");
            
            int temp = 0;
            
            if (pris != null)
            {
                for (int i = 0;i < pris.length; i++ )
                {
                	if(!StafferHelper.isVirtualDepartment(pris[i]))
                	{
                		bean.setPrincipalshipId(pris[i]);
                		
                		temp = temp + 1;
                		
                		if(temp > 1)
                		{
                			throw new MYException("只能选择最末级部门,且只能选择一个，请重新修改",bean.getName(),null);
                		}
                	}
                	
                	List<PrincipalshipBean> prinList = orgManager.querySubPrincipalship(pris[i]);
                	
                	PrincipalshipBean prinBean = orgManager.findPrincipalshipById(pris[i]);
                    
                    for(Iterator<PrincipalshipBean> iterator = prinList.iterator(); iterator.hasNext();)
                    {
                        PrincipalshipBean pBean = iterator.next();
                        
                        if (pBean.getStatus() == 1)
                            iterator.remove();
                    }
                    
                	if (prinList.size() > 1)
                    {
                        throw new MYException("只能选择最末级部门,且只能选择一个，请重新修改",bean.getName(),prinBean.getName());
                    }
                	
                    StafferVSPriBean vs = new StafferVSPriBean();

                    vs.setPrincipalshipId(pris[i]);

                    bean.getPriList().add(vs);
                }
            }

            publicFacade.updateStafferBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStaffer");
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
    public ActionForward delStaffer(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        try
        {
            String id = request.getParameter("stafferId");

            User user = Helper.getUser(request);

            publicFacade.delStafferBean(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "删除失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStaffer");
    }

    /**
     * dropStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward dropStaffer(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        try
        {
            String id = request.getParameter("stafferId");

            User user = Helper.getUser(request);

            publicFacade.dropStafferBean(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功废弃");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "废弃失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStaffer");
    }

    /**
     * updateCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateCredit(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            String credit = request.getParameter("credit");

            User user = Helper.getUser(request);

            publicFacade.updateCredit(user.getId(), id, MathTools.parseDouble(credit));

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
     * updateStaffer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findStaffer(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        if (StringTools.isNullOrNone(id))
        {
            id = request.getAttribute("sid").toString();
        }

        String update = request.getParameter("update");

        StafferVO bean = stafferDAO.findVO(id);

        request.setAttribute("bean", bean);

        List<LocationBean> locationList = locationDAO.listEntityBeans();
        request.setAttribute("locationList", locationList);

        List<DepartmentBean> depList = departmentDAO.listEntityBeans();
        request.setAttribute("depList", depList);

        List<PostBean> postList = postDAO.listEntityBeans();
        request.setAttribute("postList", postList);

        preForListAllOrgTree(request);

        List<StafferVSPriBean> priList = stafferVSPriDAO.queryEntityBeansByFK(id);

        JSONArray jarr = new JSONArray(priList, true);

        request.setAttribute("myPris", jarr.toString());

        if ( !StringTools.isNullOrNone(update))
        {
            return mapping.findForward("updateStaffer");
        }

        return mapping.findForward("detailStaffer");
    }

    /**
     * 为准备显示整个组织树准备
     * 
     * @param request
     */
    private void preForListAllOrgTree(HttpServletRequest request)
    {
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
        condition.addIntCondition("PrincipalshipBean.status", "=", PublicConstant.ORG_STATUS_USED);
        
        condition.addCondition("order by PrincipalshipBean.level");
        
//        List<PrincipalshipBean> plist = principalshipDAO.listEntityBeansByOrder("order by level");
        
        List<PrincipalshipBean> plist = principalshipDAO.queryEntityBeansByCondition(condition);

        JSONArray jarr = new JSONArray(plist, true);

        request.setAttribute("shipList", jarr.toString());
        
        Map<String, List<OrgVO>> map = new HashMap<String, List<OrgVO>>();

        for (PrincipalshipBean principalshipBean : plist)
        {
            
            condition.clear();
            
            condition.addWhereStr();
            
            condition.addCondition("OrgBean.parentId", "=", principalshipBean.getId());
            
            condition.addIntCondition("OrgBean.status", "=", PublicConstant.ORG_STATUS_USED);
                        
//            List<OrgVO> vos = orgDAO.queryEntityVOsByFK(principalshipBean.getId());
            
            List<OrgVO> vos = orgDAO.queryEntityVOsByCondition(condition);

            map.put(principalshipBean.getId(), vos);
        }

        JSONObject object = new JSONObject();

        object.createMapList(map, true);

        request.setAttribute("mapJSON", object.toString());
                
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
     * @return the locationManager
     */
    public LocationManager getLocationManager()
    {
        return locationManager;
    }

    /**
     * @param locationManager
     *            the locationManager to set
     */
    public void setLocationManager(LocationManager locationManager)
    {
        this.locationManager = locationManager;
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
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
    }

    /**
     * @return the postDAO
     */
    public PostDAO getPostDAO()
    {
        return postDAO;
    }

    /**
     * @param postDAO
     *            the postDAO to set
     */
    public void setPostDAO(PostDAO postDAO)
    {
        this.postDAO = postDAO;
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

    /**
     * @return the invoiceCreditDAO
     */
    public InvoiceCreditDAO getInvoiceCreditDAO()
    {
        return invoiceCreditDAO;
    }

    /**
     * @param invoiceCreditDAO
     *            the invoiceCreditDAO to set
     */
    public void setInvoiceCreditDAO(InvoiceCreditDAO invoiceCreditDAO)
    {
        this.invoiceCreditDAO = invoiceCreditDAO;
    }

    /**
     * @return the stafferManager
     */
    public StafferManager getStafferManager()
    {
        return stafferManager;
    }

    /**
     * @param stafferManager
     *            the stafferManager to set
     */
    public void setStafferManager(StafferManager stafferManager)
    {
        this.stafferManager = stafferManager;
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

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public UserDAO getUserDAO()
	{
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}
}
