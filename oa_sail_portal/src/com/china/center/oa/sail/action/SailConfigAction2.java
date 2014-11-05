/**
 * File Name: SailConfigAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.action;


import java.util.ArrayList;
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
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.helper.SailConfigHelper;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.SailConfigVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;


/**
 * SailConfigAction(备份)
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigAction2
 * @since 3.0
 */
public class SailConfigAction2 extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private SailConfigDAO sailConfigDAO = null;

    private SailConfigManager sailConfigManager = null;

    private ShowDAO showDAO = null;

    private static final String QUERYSAILCONFIG = "querySailConfig";

    /**
     * default constructor
     */
    public SailConfigAction2()
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

        condtion.addCondition("group by SailConfigBean.pareId");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSAILCONFIG, request, condtion,
            this.sailConfigDAO, new HandleResult<SailConfigVO>()
            {
                public void handle(SailConfigVO obj)
                {
                    List<SailConfigVO> list = sailConfigDAO.queryEntityVOsByFK(obj.getPareId());

                    for (SailConfigVO sailConfigVO : list)
                    {
                        obj.setShowAllName(obj.getShowAllName() + '/' + sailConfigVO.getShowName());
                    }

                    obj.setShowAllName(obj.getShowAllName().substring(1));

                    SailConfigHelper.changeVO(obj);
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
        List<ShowBean> showList = showDAO.queryEntityBeansByFK("0");

        request.setAttribute("showList", showList);

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
        SailConfigBean bean = new SailConfigBean();

        try
        {
            BeanUtil.getBean(bean, request);

            String[] showIds = request.getParameterValues("showIds");

            List<SailConfigBean> list = new ArrayList();

            for (int i = 0; i < showIds.length; i++ )
            {
                SailConfigBean item = new SailConfigBean();

                BeanUtil.copyProperties(item, bean);

                item.setShowId(showIds[i]);

                list.add(item);
            }

            User user = Helper.getUser(request);

            sailConfigManager.addBean(user, list);

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
        SailConfigBean bean = new SailConfigBean();

        try
        {
            BeanUtil.getBean(bean, request);

            String[] showIds = request.getParameterValues("showIds");

            List<SailConfigBean> list = new ArrayList();

            for (int i = 0; i < showIds.length; i++ )
            {
                SailConfigBean item = new SailConfigBean();

                BeanUtil.copyProperties(item, bean);

                item.setShowId(showIds[i]);

                list.add(item);
            }

            User user = Helper.getUser(request);

            sailConfigManager.updateBean(user, bean.getPareId(), list);

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

        try
        {
            SailConfigVO vo = sailConfigManager.findVO(id);

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

                return mapping.findForward("querySailConfig");
            }

            request.setAttribute("bean", vo);

            List<ShowBean> showList = showDAO.queryEntityBeansByFK("0");

            List<SailConfigBean> list = sailConfigDAO.queryEntityBeansByFK(vo.getPareId());

            for (ShowBean showBean : showList)
            {
                for (SailConfigBean sailConfigBean : list)
                {
                    if (showBean.getId().equals(sailConfigBean.getShowId()))
                    {
                        showBean.setDutyId("1");

                        break;
                    }
                    else
                    {
                        showBean.setDutyId("0");
                    }
                }
            }

            request.setAttribute("showList", showList);

        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("querySailConfig");
        }

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
            String pareId = request.getParameter("id");

            User user = Helper.getUser(request);

            sailConfigManager.deleteBean(user, pareId);

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
}
