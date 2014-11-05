/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;


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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.actionhelper.query.QueryConfig;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.common.taglib.PageSelectOption;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.bean.ProviderHisBean;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.constant.ProviderConstant;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductTypeVSCustomerDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.ProviderHisDAO;
import com.china.center.oa.product.dao.ProviderUserDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.product.vo.ProductTypeVSCustomerVO;
import com.china.center.oa.product.vo.ProviderVO;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;
import com.china.center.oa.publics.Helper;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;


/**
 * ProviderAction
 * 
 * @author ZHUZHU
 * @version 2010-1-5
 * @see ProviderAction
 * @since 1.0
 */
public class ProviderAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private QueryConfig queryConfig = null;

    private ProviderDAO providerDAO = null;

    private ProductFacade productFacade = null;

    private ProviderHisDAO providerHisDAO = null;

    private ProductDAO productDAO = null;

    private ProviderUserDAO providerUserDAO = null;

    private ProductTypeVSCustomerDAO productTypeVSCustomerDAO = null;

    private static String QUERYPROVIDER = "queryProvider";

    private static String QUERYCHECKHISPROVIDER = "queryCheckHisProvider";

    /**
     * default constructor
     */
    public ProviderAction()
    {
    }

    /**
     * 查询供应商
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProvider(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPROVIDER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROVIDER, request, condtion,
            this.providerDAO, new HandleResult()
            {
                public void handle(Object obj)
                {
                    ProviderVO vo = (ProviderVO)obj;

                    List<ProviderUserBean> list = providerUserDAO.queryEntityVOsByFK(vo.getId());

                    if (list.size() > 0)
                    {
                        vo.setLoginName(list.get(0).getName());
                    }

                    // 获取分类
                    List<ProductTypeVSCustomerVO> typeList = productTypeVSCustomerDAO
                        .queryEntityVOsByFK(vo.getId(), AnoConstant.FK_FIRST);

                    StringBuilder sb = new StringBuilder();

                    for (ProductTypeVSCustomerVO pvo : typeList)
                    {
                        sb.append(DefinedCommon.getValue("productType", pvo.getProductTypeId())
                                  + "/");
                    }

                    vo.setTypeName(sb.toString());
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 客户绑定产品类型(前置)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForBing(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String pid = request.getParameter("id");

        request.setAttribute("list", PageSelectOption.optionMap.get("productType"));

        // 供应商
        ProviderBean bean = providerDAO.find(pid);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "供应商不存在");

            return mapping.findForward("queryProvider");
        }

        request.setAttribute("bean", bean);

        List<ProductTypeVSCustomer> list = productTypeVSCustomerDAO.queryVSByCustomerId(pid);

        Map<String, String> ps = new HashMap<String, String>();

        for (ProductTypeVSCustomer productTypeVSCustomer : list)
        {
            ps.put(productTypeVSCustomer.getProductTypeId(), productTypeVSCustomer.getCustomerId());
        }

        request.setAttribute("mapVS", ps);

        return mapping.findForward("bingProductType");
    }

    /**
     * bingProductTypeToProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward bingProductTypeToProvider(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse reponse)
        throws ServletException
    {
        String pid = request.getParameter("pid");

        String[] productTypeIds = request.getParameterValues("productTypeId");

        if (productTypeIds == null)
        {
            productTypeIds = new String[0];
        }

        try
        {
            User user = Helper.getUser(request);

            productFacade.bingProductTypeToCustmer(user.getId(), pid, productTypeIds);

            request.setAttribute(KeyConstant.MESSAGE, "绑定类型成功");
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "绑定类型失败:" + e.getMessage());
        }

        return mapping.findForward("queryProvider");
    }

    /**
     * 增加供应商
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addProvider(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProviderBean bean = new ProviderBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.addProvider(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加供应商:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加供应商失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * addOrUpdateUserBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateUserBean(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        ProviderUserBean bean = new ProviderUserBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            bean.setLocationId(user.getLocationId());

            bean.setRoleId("14");

            boolean isAdd = StringTools.isNullOrNone(bean.getId());

            String password = RandomTools.getRandomString(10);

            if (isAdd)
            {
                bean.setPassword(password);
            }

            productFacade.addOrUpdateUserBean(user.getId(), bean);

            if (isAdd)
            {
                request.setAttribute(KeyConstant.MESSAGE, "成功增加供应商登录用户,密码:" + password);
            }
            else
            {
                request.setAttribute(KeyConstant.MESSAGE, "成功修改供应商登录用户");
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理供应商登录用户失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * 修改供应商
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateProvider(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProviderBean bean = new ProviderBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.updateProvider(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "修改供应商成功:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改供应商失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryProvider");
    }

    /**
     * 删除供应商
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delProvider(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.delProvider(user.getId(), id);

            ajax.setSuccess("成功删除供应商");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除供应商失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * updateUserPassword
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateUserPassword(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            List<ProviderUserBean> list = providerUserDAO.queryEntityBeansByFK(id);

            if (list.size() == 0)
            {
                throw new MYException("没有供应商用户");
            }

            String password = RandomTools.getRandomString(10);

            productFacade.updateUserPassword(user.getId(), list.get(0).getId(), password);

            ajax.setSuccess("重置用户密码成功:" + password);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("重置用户密码失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 查看供应商详细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProvider(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        // User user = Helper.getUser(request);

        ProviderBean vo = providerDAO.find(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "供应商不存在");

            return mapping.findForward("querySelfCustomer");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            return mapping.findForward("updateProvider");
        }

        // detailProvider
        return mapping.findForward("detailProvider");
    }

    /**
     * findProviderUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProviderUser(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        List<ProviderUserBean> list = providerUserDAO.queryEntityVOsByFK(id);

        if (list.size() > 1)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

            return mapping.findForward("querySelfCustomer");
        }

        ProviderUserBean bean = null;

        if (list.size() == 1)
        {
            bean = list.get(0);
        }

        request.setAttribute("bean", bean);
        request.setAttribute("provideId", id);

        // detailProvider
        return mapping.findForward("addOrUpdateProviderUser");
    }

    /**
     * findHisProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findHisProvider(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        ProviderHisBean vo = providerHisDAO.find(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "供应商不存在");

            return mapping.findForward("querySelfCustomer");
        }

        request.setAttribute("bean", vo);

        // detailProvider
        return mapping.findForward("detailProvider");
    }

    /**
     * queryCheckHisCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckHisProvider(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("ProviderHisBean.checkStatus", "=", ProviderConstant.HIS_CHECK_NO);

        ActionTools.processJSONQueryCondition(QUERYCHECKHISPROVIDER, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKHISPROVIDER, request,
            condtion, this.providerHisDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * checkHisProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward checkHisProvider(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");

            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    productFacade.checkHisProvider(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("成功核对供应商");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("核对失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * rptProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProvider(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        List<ProviderBean> list = null;

        ConditionParse condition = new ConditionParse();

        String productType = request.getParameter("productType");

        String name = request.getParameter("name");

        String productId = request.getParameter("productId");

        String areaId = request.getParameter("areaId");

        if ( !StringTools.isNullOrNone(productId))
        {
            request.setAttribute("productId", productId);
        }

        if ( !StringTools.isNullOrNone(name))
        {
            condition.addCondition("t1.name", "like", name);
            request.setAttribute("name", name);
        }

        String code = request.getParameter("code");

        if ( !StringTools.isNullOrNone(code))
        {
            condition.addCondition("t1.code", "like", code);

            request.setAttribute("code", code);
        }

        if ( !StringTools.isNullOrNone(productType))
        {
            condition.addCondition("t2.productTypeId", "=", productType);

            request.setAttribute("productType", productType);
        }

        if ( !StringTools.isNullOrNone(areaId))
        {
            condition.addCondition("t1.location", "=", areaId);

            request.setAttribute("areaId", areaId);
        }

        list = providerDAO.queryByLimit(condition, 100);

        setDefined(list, productId, areaId);

        request.setAttribute("providerList", list);

        return mapping.findForward("rptQueryProvider");
    }

    /**
     * 设置产品定义的供应商
     * 
     * @param list
     * @param productId
     */
    private void setDefined(List<ProviderBean> list, String productId, String areaId)
    {
        // 增加产品自身的几个供应商
        if ( !StringTools.isNullOrNone(productId))
        {
            int index = 0;
            ProductBean product = productDAO.find(productId);

            if (product != null)
            {
                if ( !StringTools.isNullOrNone(product.getMainProvider()))
                {
                    // 获取供应商
                    ProviderBean main = providerDAO.find(product.getMainProvider());

                    if (main != null && fiter(main, areaId))
                    {
                        list.add(index++ , main);
                    }
                }

                if ( !StringTools.isNullOrNone(product.getAssistantProvider1()))
                {
                    // 获取供应商
                    ProviderBean assistant1 = providerDAO.find(product.getAssistantProvider1());

                    if (assistant1 != null && fiter(assistant1, areaId))
                    {
                        list.add(index++ , assistant1);
                    }
                }

                if ( !StringTools.isNullOrNone(product.getAssistantProvider2()))
                {
                    // 获取供应商
                    ProviderBean assistant2 = providerDAO.find(product.getAssistantProvider2());

                    if (assistant2 != null && fiter(assistant2, areaId))
                    {
                        list.add(index++ , assistant2);
                    }
                }

                if ( !StringTools.isNullOrNone(product.getAssistantProvider3()))
                {
                    // 获取供应商
                    ProviderBean assistant3 = providerDAO.find(product.getAssistantProvider3());

                    if (assistant3 != null && fiter(assistant3, areaId))
                    {
                        list.add(index++ , assistant3);
                    }
                }

                if ( !StringTools.isNullOrNone(product.getAssistantProvider4()))
                {
                    // 获取供应商
                    ProviderBean assistant4 = providerDAO.find(product.getAssistantProvider4());

                    if (assistant4 != null && fiter(assistant4, areaId))
                    {
                        list.add(index++ , assistant4);
                    }
                }
            }
        }
    }

    private boolean fiter(ProviderBean bean, String areaId)
    {
        if (StringTools.isNullOrNone(areaId))
        {
            return true;
        }

        return areaId.equals(bean.getLocation());
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
     * @return the productFacade
     */
    public ProductFacade getProductFacade()
    {
        return productFacade;
    }

    /**
     * @param productFacade
     *            the productFacade to set
     */
    public void setProductFacade(ProductFacade productFacade)
    {
        this.productFacade = productFacade;
    }

    /**
     * @return the providerHisDAO
     */
    public ProviderHisDAO getProviderHisDAO()
    {
        return providerHisDAO;
    }

    /**
     * @param providerHisDAO
     *            the providerHisDAO to set
     */
    public void setProviderHisDAO(ProviderHisDAO providerHisDAO)
    {
        this.providerHisDAO = providerHisDAO;
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
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }
}
