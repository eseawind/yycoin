/**
 * File Name: PublicQueryAction.java<br>
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.jsonimpl.JSONObject;
import com.china.center.actionhelper.query.QueryConditionBean;
import com.china.center.actionhelper.query.QueryConfigResource;
import com.china.center.actionhelper.query.QueryItemBean;
import com.china.center.common.MYException;
import com.china.center.common.taglib.MapBean;
import com.china.center.common.taglib.PageSelectOption;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.EnumDefineBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.EnumDefineDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.PostDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.RoleDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.PublicQueryManager;
import com.china.center.oa.publics.manager.QueryManager;
import com.china.center.oa.publics.wrap.StafferOrgWrap;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.StringTools;


/**
 * PublicQueryAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicQueryAction
 * @since 1.0
 */
public class PublicQueryAction extends DispatchAction
{
    private StafferDAO stafferDAO = null;

    private OrgManager orgManager = null;

    private LocationManager locationManager = null;

    private LocationDAO locationDAO = null;

    private PrincipalshipDAO principalshipDAO = null;

    private DepartmentDAO departmentDAO = null;

    private PublicQueryManager publicQueryManager = null;

    private QueryManager queryManager = null;

    private PostDAO postDAO = null;

    private DutyDAO dutyDAO = null;

    private InvoiceDAO invoiceDAO = null;

    private RoleDAO roleDAO = null;

    private ProvinceDAO provinceDAO = null;

    private EnumDefineDAO enumDefineDAO = null;

    /**
     * default constructor
     */
    public PublicQueryAction()
    {
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
    public ActionForward popCommonQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        // 在xml里面定义的
        String key = request.getParameter("key");

        // name是用于如何定义查询是使用同一个，但是在内存里面保存的查询条件不一样
        String name = request.getParameter("name");

        QueryItemBean query = QueryConfigResource.getConfigMap().get(key);

        if (query == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有配置查询,请核实");

            return mapping.findForward("error");
        }

        if (StringTools.isNullOrNone(name))
        {
            name = query.getName();
        }

        request.setAttribute("queryName", name);

        Map<String, List> selectMap = new HashMap<String, List>();
        request.setAttribute("selectMap", selectMap);
        request.setAttribute("query", query);

        User user = Helper.getUser(request);

        List<QueryConditionBean> condition = query.getConditions();

        for (QueryConditionBean queryConditionBean : condition)
        {
            if ("select".equals(queryConditionBean.getType()))
            {
                setSelectOption(user, queryConditionBean.getOption(), selectMap);
            }
        }

        return mapping.findForward("commonQuery");
    }

    /**
     * popCommonQuery2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward popCommonQuery2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        // 在XML里面定义的
        String key = request.getParameter("key");

        // name是用于如何定义查询是使用同一个，但是在内存里面保存的查询条件不一样
        String name = request.getParameter("name");

        QueryItemBean query = QueryConfigResource.getConfigMap().get(key);

        if (query == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有配置查询,请核实");

            return mapping.findForward("error");
        }

        if (StringTools.isNullOrNone(name))
        {
            name = query.getName();
        }

        request.setAttribute("queryName", name);

        Map<String, List> selectMap = new HashMap<String, List>();
        request.setAttribute("selectMap", selectMap);
        request.setAttribute("query", query);

        User user = Helper.getUser(request);

        List<QueryConditionBean> condition = query.getConditions();

        for (QueryConditionBean queryConditionBean : condition)
        {
            if ("select".equals(queryConditionBean.getType()))
            {
                setSelectOption(user, queryConditionBean.getOption(), selectMap);
            }
        }

        StringBuilder sb = new StringBuilder();

        buildQueryHtml(request, name, query, selectMap, sb);

        Map<String, String> sbMap = new HashMap();

        sbMap.put("key", sb.toString());

        JSONObject object = new JSONObject(sbMap);

        return JSONTools.writeResponse(response, object.toString());
    }

    /**
     * buildQueryHtml
     * 
     * @param request
     * @param name
     * @param query
     * @param selectMap
     * @param sb
     */
    private void buildQueryHtml(HttpServletRequest request, String name, QueryItemBean query,
                                Map<String, List> selectMap, StringBuilder sb)
    {
        sb.append("<input type=hidden name=load value=1>");

        // write JAVA object to string
        List<QueryConditionBean> hidenConditions = query.getConditions();

        for (QueryConditionBean eachItem : hidenConditions)
        {
            sb.append(StringTools.format("<input type=hidden name='hidden_query_%s' value='%s'/>", eachItem.getName(),
                eachItem.getAssistant()));
        }

        String pkey = name + "_pmap";

        Map ppmap = (Map)request.getSession().getAttribute(pkey);

        if (ppmap == null)
        {
            ppmap = new HashMap();
        }

        sb.append("<table align='center' width='98%' cellpadding='0' id='default_table' cellspacing='1' class='table0'>");

        for (int i = 0; i < hidenConditions.size(); i++ )
        {
            QueryConditionBean eachItem = hidenConditions.get(i);

            String value = StringTools.print((String)ppmap.get(eachItem.getName()));

            sb.append("<tr class='content1'>");

            sb.append(StringTools.format("<td width='20%%' align='left'>%s：</td>", eachItem.getCaption()));
            sb.append("<td width=80% >");

            if ("text".equals(eachItem.getType()))
            {
                sb.append(StringTools.format(
                    "<input type=text name='%s' id='%s' size=35 onkeypress='enterKeyPress(querySure)' frister=%s %s value='%s'/>",
                    eachItem.getName(), eachItem.getName(), (i == 0 ? "1" : "0"), eachItem.getInner(), value));
            }
            else if ("select".equals(eachItem.getType()))
            {
                sb.append(StringTools.format(
                    "<select name='%s' id='%s' quick=true %s class='select_class' values='%s' style='width:250px'>",
                    eachItem.getName(), eachItem.getName(), eachItem.getInner(),
                    StringTools.print((String)ppmap.get(eachItem.getName()))));

                sb.append("<option value=''>--</option>");

                List optionList = selectMap.get(eachItem.getOption());

                for (Object optionItem : optionList)
                {
                    sb.append(StringTools.format("<option value='%s'>%s</option>", BeanUtil.getProperty(optionItem,
                        "id"), BeanUtil.getProperty(optionItem, "name")));
                }
                sb.append("</select>");
            }
            else if ("date".equals(eachItem.getType()))
            {
                sb.append(StringTools.format(
                    "<input type=text name = '%s' id='%s'  value = '%s' %s readonly=readonly >"
                        + "<img src='%s/images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDate(\"%s\");' height='20px' width='20px'/>",
                    eachItem.getName(), eachItem.getName(), value, eachItem.getInner(), request.getContextPath(),
                    eachItem.getName()));
            }
            else if ("datetime".equals(eachItem.getType()))
            {
                sb.append(StringTools.format(
                    "<input type=text name = '%s' id='%s'  value = '%s' %s readonly=readonly >"
                        + "<img src='%s/images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateTime(\"%s\");' height='20px' width='20px'/>",
                    eachItem.getName(), eachItem.getName(), value, eachItem.getInner(), request.getContextPath(),
                    eachItem.getName()));
            }

            sb.append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");
    }

    /**
     * 设置set
     * 
     * @param user
     * @param key
     * @param selectMap
     */
    private void setSelectOption(User user, String key, Map<String, List> selectMap)
    {
        if (StringTools.isNullOrNone(key))
        {
            return;
        }

        // $就是全局的
        if (key.startsWith("$"))
        {
            List list = PageSelectOption.optionMap.get(key.substring(1));

            if (list != null)
            {
                selectMap.put(key, list);

                return;
            }
        }

        // $locationList
        if ("$locationlist".equalsIgnoreCase(key))
        {
            List<LocationBean> locationList = locationDAO.listEntityBeans();

            selectMap.put(key, locationList);

            return;
        }

        // $postList
        if ("$postList".equalsIgnoreCase(key))
        {
            List<PostBean> postList = postDAO.listEntityBeans();

            selectMap.put(key, postList);

            return;
        }

        // $examType
        if ("$examType".equalsIgnoreCase(key))
        {
            List<MapBean> el = PageSelectOption.optionMap.get("examType");

            selectMap.put(key, el);

            return;
        }

        // $departmentList
        if ("$departmentList".equalsIgnoreCase(key))
        {
            List<DepartmentBean> departmentList = departmentDAO.listEntityBeans();

            selectMap.put(key, departmentList);

            return;
        }

        // 列举全部的省
        if ("$provinceList".equals(key))
        {
            List<ProvinceBean> list = provinceDAO.listEntityBeans();

            selectMap.put(key, list);

            return;
        }

        // $staffer_location 区域下的职员
        if ("$staffer_location".equals(key))
        {
            List<StafferBean> list = null;
            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                list = stafferDAO.listEntityBeans();
            }
            else
            {
                list = stafferDAO.queryStafferByLocationId(user.getLocationId());
            }

            selectMap.put(key, list);

            return;
        }

        if ("$staffer".equals(key))
        {
            List<StafferBean> list = stafferDAO.listEntityBeans();

            selectMap.put(key, list);

            return;
        }

        // 自己和下属
        if ("$staffer_belong".equals(key))
        {
            StafferBean sb = stafferDAO.find(user.getStafferId());

            if (sb == null)
            {
                return;
            }

            List<StafferOrgWrap> list;
            try
            {
                list = orgManager.queryAllSubStaffer(sb.getPrincipalshipId());
                selectMap.put(key, list);
            }
            catch (MYException e)
            {
            }

            return;
        }

        // $role_location 区域下的角色
        if ("$role_location".equals(key))
        {
            List<RoleBean> list = null;

            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                list = roleDAO.listEntityBeans();
            }
            else
            {
                list = roleDAO.queryRoleByLocationId(user.getLocationId());
            }

            selectMap.put(key, list);

            return;
        }

        if ("$enumDefine".equals(key))
        {
            // MapBean 获得所有的enum定义
            List<EnumDefineBean> enumDefineList = enumDefineDAO.listEntityBeans();

            List<MapBean> mapList = new ArrayList();

            for (EnumDefineBean enumDefineBean : enumDefineList)
            {
                MapBean mbean = new MapBean();

                mbean.setKey(enumDefineBean.getName());

                mbean.setValue(enumDefineBean.getCnname());

                mapList.add(mbean);
            }

            selectMap.put(key, mapList);

            return;
        }

        // $dutyList 纳税实体管理
        if ("$dutyList".equals(key))
        {
            List<DutyBean> dutyList = dutyDAO.listEntityBeans();

            selectMap.put(key, dutyList);

            return;
        }

        // $invoiceList
        if ("$invoiceList".equals(key))
        {
            List<InvoiceBean> invoiceList = invoiceDAO.listEntityBeans();

            for (InvoiceBean invoiceBean : invoiceList)
            {
                invoiceBean.setName(invoiceBean.getFullName());
            }

            selectMap.put(key, invoiceList);

            return;
        }

        // 从注入的publicQueryManager里面获取
        List<?> dataList = publicQueryManager.getSelectList(key);

        if (dataList != null)
        {
            selectMap.put(key, dataList);

            return;
        }

        // 动态获取数据库里面的值
        QueryListener queryListener = queryManager.getListenerMap().get(key);

        if (queryListener != null)
        {
            selectMap.put(key, queryListener.getBeanList());

            return;
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
     * @return the provinceDAO
     */
    public ProvinceDAO getProvinceDAO()
    {
        return provinceDAO;
    }

    /**
     * @param provinceDAO
     *            the provinceDAO to set
     */
    public void setProvinceDAO(ProvinceDAO provinceDAO)
    {
        this.provinceDAO = provinceDAO;
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
     * @return the publicQueryManager
     */
    public PublicQueryManager getPublicQueryManager()
    {
        return publicQueryManager;
    }

    /**
     * @param publicQueryManager
     *            the publicQueryManager to set
     */
    public void setPublicQueryManager(PublicQueryManager publicQueryManager)
    {
        this.publicQueryManager = publicQueryManager;
    }

    /**
     * @return the enumDefineDAO
     */
    public EnumDefineDAO getEnumDefineDAO()
    {
        return enumDefineDAO;
    }

    /**
     * @param enumDefineDAO
     *            the enumDefineDAO to set
     */
    public void setEnumDefineDAO(EnumDefineDAO enumDefineDAO)
    {
        this.enumDefineDAO = enumDefineDAO;
    }

    /**
     * @return the queryManager
     */
    public QueryManager getQueryManager()
    {
        return queryManager;
    }

    /**
     * @param queryManager
     *            the queryManager to set
     */
    public void setQueryManager(QueryManager queryManager)
    {
        this.queryManager = queryManager;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO()
    {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO
     *            the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO)
    {
        this.invoiceDAO = invoiceDAO;
    }
}
