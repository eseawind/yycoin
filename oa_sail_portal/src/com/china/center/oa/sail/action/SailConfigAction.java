/**
 * File Name: SailConfigAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.action;


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
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.dao.SailConfDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.SailConfVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * SailConfigAction
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigAction
 * @since 3.0
 */
public class SailConfigAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private SailConfigDAO sailConfigDAO = null;

    private SailConfigManager sailConfigManager = null;

    private ShowDAO showDAO = null;

    private SailConfDAO sailConfDAO = null;

    private OrgManager orgManager = null;

    private static final String QUERYSAILCONFIG = "querySailConfig";

    /**
     * default constructor
     */
    public SailConfigAction()
    {
    }

    /**
     * querySailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySailConfig(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSAILCONFIG, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSAILCONFIG, request, condtion,
            this.sailConfDAO, new HandleResult<SailConfVO>()
            {
                public void handle(SailConfVO obj)
                {

                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddSailConfig(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        List<PrincipalshipBean> industryList = orgManager.listAllIndustry();

        for (PrincipalshipBean principalshipBean : industryList)
        {
            principalshipBean.setName(principalshipBean.getParentName() + "-->"
                                      + principalshipBean.getName());
        }

        request.setAttribute("industryList", industryList);

        return mapping.findForward("addSailConfig");
    }

    /**
     * addSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addSailConfig(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        SailConfBean bean = new SailConfBean();

        String sailType = request.getParameter("sailType");
        String productType = request.getParameter("productType");

        try
        {
            BeanUtil.getBean(bean, request);

            if (StringTools.isNullOrNone(sailType))
            {
                bean.setSailType( -1);
            }

            if (StringTools.isNullOrNone(productType))
            {
                bean.setProductType( -1);
            }

            User user = Helper.getUser(request);

            sailConfigManager.addBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySailConfig");
    }

    /**
     * addSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateSailConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        SailConfBean bean = new SailConfBean();

        String sailType = request.getParameter("sailType");
        String productType = request.getParameter("productType");

        try
        {
            BeanUtil.getBean(bean, request);

            if (StringTools.isNullOrNone(sailType))
            {
                bean.setSailType( -1);
            }

            if (StringTools.isNullOrNone(productType))
            {
                bean.setProductType( -1);
            }

            User user = Helper.getUser(request);

            sailConfigManager.updateBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySailConfig");
    }

    /**
     * findSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findSailConfig(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        SailConfVO vo = sailConfDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("querySailConfig");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            return mapping.findForward("updateSailConfig");
        }

        return mapping.findForward("detailSailConfig");
    }

    /**
     * deleteSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteSailConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            sailConfigManager.deleteConf(user, id);

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
     * @return the sailConfigDAO
     */
    public SailConfigDAO getSailConfigDAO()
    {
        return sailConfigDAO;
    }

    /**
     * @param sailConfigDAO
     *            the sailConfigDAO to set
     */
    public void setSailConfigDAO(SailConfigDAO sailConfigDAO)
    {
        this.sailConfigDAO = sailConfigDAO;
    }

    /**
     * @return the sailConfigManager
     */
    public SailConfigManager getSailConfigManager()
    {
        return sailConfigManager;
    }

    /**
     * @param sailConfigManager
     *            the sailConfigManager to set
     */
    public void setSailConfigManager(SailConfigManager sailConfigManager)
    {
        this.sailConfigManager = sailConfigManager;
    }

    /**
     * @return the showDAO
     */
    public ShowDAO getShowDAO()
    {
        return showDAO;
    }

    /**
     * @param showDAO
     *            the showDAO to set
     */
    public void setShowDAO(ShowDAO showDAO)
    {
        this.showDAO = showDAO;
    }

    /**
     * @return the sailConfDAO
     */
    public SailConfDAO getSailConfDAO()
    {
        return sailConfDAO;
    }

    /**
     * @param sailConfDAO
     *            the sailConfDAO to set
     */
    public void setSailConfDAO(SailConfDAO sailConfDAO)
    {
        this.sailConfDAO = sailConfDAO;
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
}
