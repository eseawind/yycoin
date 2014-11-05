/**
 * File Name: PopQueryAction.java<br>
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.HandleQueryCondition;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.vo.CityVO;
import com.china.center.oa.publics.vo.LogVO;
import com.china.center.oa.publics.vo.RoleVO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.vs.StafferVSPriBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;


/**
 * PopQueryAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PopQueryAction
 * @since 1.0
 */
public class PopQueryAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
    private StafferDAO stafferDAO = null;

    private LocationDAO locationDAO = null;
    
    private DutyDAO dutyDAO = null;

    private LogDAO logDAO = null;

    private UserDAO userDAO = null;

    private OrgManager orgManager = null;

    private CityDAO cityDAO = null;

    private RoleManager roleManager = null;

    private StafferVSPriDAO stafferVSPriDAO = null;
    
    private PrincipalshipDAO principalshipDAO = null;

    private static String RPTQUERYSTAFFER = "rptQueryStaffer";

    private static String RPTQUERYORG = "rptQueryOrg";

    private static String RPTQUERYCITY = "rptQueryCity";

    private static String RPTQUERYUSER = "rptQueryUser";
    
    private static String RPTQUERYPRODUCT = "rptQueryProduct";

    /**
     * 职员的查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryStaffer(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String status = request.getParameter("status");
        
        //不含离职的
        String excludeQuit = request.getParameter("excludeQuit");
        
        if (StringTools.isNullOrNone(excludeQuit))
        	excludeQuit = "";

        List<StafferVO> list = null;

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            if ( !"99".equals(status))
            {
                // 过滤废弃的
                condtion.addIntCondition("StafferBean.status", "=", StafferConstant.STATUS_COMMON);
            }
            else
            {
                condtion.addIntCondition("StafferBean.status", "=", StafferConstant.STATUS_DROP);
            }

            if (excludeQuit.equals("1"))
            {
            	condtion.addCondition("StafferBean.birthday", "=", "");
            }
            
            setStafferInnerCondition(request, condtion);

            int total = stafferDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSTAFFER);

            list = stafferDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSTAFFER);

            list = stafferDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYSTAFFER), PageSeparateTools.getPageSeparate(request, RPTQUERYSTAFFER));
        }

        for (StafferVO vo : list)
        {
            PrincipalshipBean pri = orgManager.findPrincipalshipById(vo.getPrincipalshipId());

            if (pri != null)
            {
                vo.setDepartmentFullName(pri.getFullName());
            }
        }

        request.setAttribute("beanList", list);

        request.setAttribute("locationList", locationList);

        return mapping.findForward("rptQueryStaffer");
    }

    /**
     * 组织列表查询
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
        CommonTools.saveParamers(request);

        String cacheKey = RPTQUERYORG;

        List<PrincipalshipBean> list = ActionTools.commonQueryInPageSeparate(cacheKey, request,
            principalshipDAO, new HandleQueryCondition()
            {
                public void setQueryCondition(HttpServletRequest request, ConditionParse condtion)
                {
                    String name = request.getParameter("name");
                    String level = request.getParameter("level");

                    if ( !StringTools.isNullOrNone(name))
                    {
                        condtion.addCondition("PrincipalshipBean.name", "like", name);
                    }

                    if ( !StringTools.isNullOrNone(level))
                    {
                        condtion.addIntCondition("PrincipalshipBean.level", "=", level);
                    }
                }
            });

        if ( !ListTools.isEmptyOrNull(list))
        {
            for (PrincipalshipBean principalshipBean : list)
            {
                PrincipalshipBean fullBean = orgManager.findPrincipalshipById(principalshipBean
                    .getId());

                BeanUtil.copyProperties(principalshipBean, fullBean);
            }
        }

        return mapping.findForward(cacheKey);
    }

    /**
     * 用户的查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryUser(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<UserVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setUserInnerCondition(request, condtion);

            int total = userDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYUSER);

            list = userDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSTAFFER);

            list = userDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYUSER), PageSeparateTools.getPageSeparate(request, RPTQUERYUSER));
        }

        for (UserVO userVO : list)
        {
            RoleVO bean = null;

            try
            {
                bean = roleManager.findVO(userVO.getRoleId());

                if (bean == null)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, userVO.getName() + "的角色不存在");

                    return mapping.findForward("error");
                }

                JSONArray jarr = new JSONArray(bean.getAuth(), true);

                // 借用
                userVO.setRoleName(jarr.toString());
            }
            catch (MYException e)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, userVO.getName() + "的角色不存在");

                return mapping.findForward("error");
            }
        }

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryUser");
    }

    /**
     * rptQueryCity
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryCity(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<CityVO> list = null;

        String cacheKey = RPTQUERYCITY;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setCityInnerCondition(request, condtion);

            int total = cityDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, cacheKey);

            list = cityDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, cacheKey);

            list = cityDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                cacheKey), PageSeparateTools.getPageSeparate(request, cacheKey));
        }

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryCity");
    }

    /**
     * rptQuerySuperiorStaffer(查询上级)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQuerySuperiorStaffer(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String stafferId = request.getParameter("stafferId");

        Set<StafferVO> set = new HashSet<StafferVO>();

        // 获得人员的组织结构
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(stafferId);

        // 循环获得所有可以操作的人员
        for (StafferVSPriBean stafferVSPriBean : vsList)
        {
            PrincipalshipBean pri = principalshipDAO.find(stafferVSPriBean.getPrincipalshipId());

            if (pri == null || StringTools.isNullOrNone(pri.getParentId()))
            {
                continue;
            }

            String parentId = pri.getParentId();

            List<StafferVSPriBean> svsp = stafferVSPriDAO.queryEntityBeansByFK(parentId,
                AnoConstant.FK_FIRST);

            for (StafferVSPriBean each : svsp)
            {
                set.add(stafferDAO.findVO(each.getStafferId()));
            }
        }

        request.setAttribute("beanList", set);

        return mapping.findForward("rptQuerySuperiorStaffer");
    }

    /**
     * query log
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryLog(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String fk = request.getParameter("fk");

        List<LogVO> list = logDAO.queryEntityVOsByFK(fk);

        Collections.sort(list, new Comparator<LogVO>()
        {

            public int compare(LogVO o1, LogVO o2)
            {
                return o1.getLogTime().compareTo(o2.getLogTime());
            }
        });

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryLog");
    }

    public ActionForward rptQueryPrincipalship(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        CommonTools.saveParamers(request);

        List<PrincipalshipBean> allIndustry = orgManager.listAllIndustry();

        for(Iterator<PrincipalshipBean> iterator = allIndustry.iterator();iterator.hasNext();)
        {
            PrincipalshipBean bean = iterator.next();
            // 去掉停用的
            if (bean.getStatus()==1)
            {
                iterator.remove();
            }
        }
        
        request.setAttribute("allIndustry", allIndustry);
//        for (PrincipalshipBean principalshipBean : allIndustry) {
//            principalshipBean.setName(principalshipBean.getParentName() + "-->"
//                    + principalshipBean.getName());
//
//            principalshipBean.setFullName(principalshipBean.getName());
//        }

        return mapping.findForward("rptQueryPrincipalship");
    }
    
    public ActionForward selectDeparts(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        CommonTools.saveParamers(request);

        List<PrincipalshipBean> allIndustry;
        
        List<PrincipalshipBean> returnList = new ArrayList<PrincipalshipBean>();
        String flag = request.getParameter("flag");
        if(null != flag && flag.equals("0"))
        {
        	request.setAttribute("allIndustry", returnList);

            return mapping.findForward("rptQueryDeparts");
        }
        
        String name = request.getParameter("name");

        String code = request.getParameter("code");
        
		try {
			allIndustry = orgManager.querySubPrincipalship("3");//只显示营销管理中心
		} catch (MYException e) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("error");
		}

		int k = 0;
        for(Iterator<PrincipalshipBean> iterator = allIndustry.iterator();iterator.hasNext();)
        {
        	k++;
            PrincipalshipBean bean = iterator.next();
         // 去掉停用
            if (bean.getStatus()==1 )
            {
                continue;
            }
            if(null != code && code.length() > 0)
            {
            	if(bean.getId().trim().equals(code.trim()))
            	{
            		returnList.add(bean);
            		break;
            	}
            	else
            	{
            		continue;
            	}
            }
            
            if(null != name && name.length() > 0)
            {
            	if(bean.getName().contains(name.trim()))
            	{
            		returnList.add(bean);
            		continue;
            	}
            }
            
//            if(k==20)
//            {
//            	break;
//            }
//            else if(name == null || name.length() < 1)
//            {
            	returnList.add(bean);//只显示20条记录
//            }
        }
        request.setAttribute("allIndustry", returnList);

        return mapping.findForward("rptQueryDeparts");
    }
    
   
    
    
    public ActionForward selectNaShuiShiTi(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        CommonTools.saveParamers(request);

        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        request.setAttribute("dutyList", dutyList);

        return mapping.findForward("queryNaShuiShiTi");
    }
    
    public ActionForward selectIndustryId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        CommonTools.saveParamers(request);

        List<PrincipalshipBean> industryList = orgManager.listAllIndustry();

        request.setAttribute("industryList", industryList);

        return mapping.findForward("queryIndustry");
    }
    
    /**
     * @param request
     * @param condtion
     */
    private void setStafferInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String locationId = request.getParameter("locationId");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("StafferBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("StafferBean.code", "like", code);
        }

        if ( !StringTools.isNullOrNone(locationId))
        {
            condtion.addCondition("StafferBean.locationId", "=", locationId);
        }
    }

    /**
     * @param request
     * @param condtion
     */
    private void setUserInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String sname = request.getParameter("sname");
        
        String otype = request.getParameter("otype");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("UserBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(sname))
        {
            condtion.addCondition("StafferBean.name", "like", sname);
        }

        // 只显示正常的用户
        condtion.addIntCondition("StafferBean.status", "=", StafferConstant.STATUS_COMMON);
        
        // 只显示销售系
        if (!StringTools.isNullOrNone(otype)&&otype.equals("0"))
        {
        	String stafferId = Helper.getStaffer(request).getId();
        	
        	condtion.addIntCondition("StafferBean.otype", "=", StafferConstant.OTYPE_SAIL);	
        	
        	condtion.addCondition("StafferBean.id", "<>", stafferId);
        }
    }

    /**
     * setCityInnerCondition
     * 
     * @param request
     * @param condtion
     */
    private void setCityInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String sname = request.getParameter("sname");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("CityBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(sname))
        {
            condtion.addCondition("ProvinceBean.name", "like", sname);
        }
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
     * @return the logDAO
     */
    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    /**
     * @param logDAO
     *            the logDAO to set
     */
    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
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
     * @return the cityDAO
     */
    public CityDAO getCityDAO()
    {
        return cityDAO;
    }

    /**
     * @param cityDAO
     *            the cityDAO to set
     */
    public void setCityDAO(CityDAO cityDAO)
    {
        this.cityDAO = cityDAO;
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

	public DutyDAO getDutyDAO() {
		return dutyDAO;
	}

	public void setDutyDAO(DutyDAO dutyDAO) {
		this.dutyDAO = dutyDAO;
	}

    
}
