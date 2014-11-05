/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.EnumDefineBean;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.dao.EnumDefineDAO;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * EnumAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see EnumAction
 * @since 1.0
 */
public class EnumAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PublicFacade publicFacade = null;

    private EnumDAO enumDAO = null;

    private EnumDefineDAO enumDefineDAO = null;

    private static final String QUERYENUM = "queryEnum";

    /**
     * default constructor
     */
    public EnumAction()
    {
    }

    public ActionForward queryEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYENUM, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYENUM, request, condtion, this.enumDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addEnum
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
        throws ServletException
    {
        EnumBean bean = new EnumBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.addEnumBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加配置");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加配置失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryEnum");
    }

    /**
     * updateDuty
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            EnumBean old = enumDAO.find(id);

            String value = request.getParameter("value").trim();

            old.setValue(value);

            User user = Helper.getUser(request);

            publicFacade.updateEnumBean(user.getId(), old);

            ajax.setSuccess("成功操作配置");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作配置失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);

    }

    /**
     * deleteEnum
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            publicFacade.deleteEnumBean(user.getId(), id);

            ajax.setSuccess("成功操作配置");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作配置失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findEnumDefine
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddEnum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException
    {
        List<EnumDefineBean> enumDefineList = enumDefineDAO.listEntityBeans();

        request.setAttribute("enumDefineList", enumDefineList);

        return mapping.findForward("addEnum");
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
     * @return the enumDAO
     */
    public EnumDAO getEnumDAO()
    {
        return enumDAO;
    }

    /**
     * @param enumDAO
     *            the enumDAO to set
     */
    public void setEnumDAO(EnumDAO enumDAO)
    {
        this.enumDAO = enumDAO;
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

}
