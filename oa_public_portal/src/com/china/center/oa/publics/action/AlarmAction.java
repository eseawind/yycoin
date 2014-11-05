/**
 * File Name: SailTranApplyAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


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
import com.china.center.oa.publics.bean.AlarmBean;
import com.china.center.oa.publics.constant.AlarmConstant;
import com.china.center.oa.publics.dao.AlarmDAO;
import com.china.center.oa.publics.manager.CommonManager;
import com.china.center.tools.CommonTools;


/**
 * SailTranApplyAction
 * 
 * @author ZHUZHU
 * @version 2012-5-6
 * @see AlarmAction
 * @since 3.0
 */
public class AlarmAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private AlarmDAO alarmDAO = null;

    private CommonManager commonManager = null;

    private static final String QUERYALARM = "queryAlarm";

    /**
     * default constructor
     */
    public AlarmAction()
    {
    }

    /**
     * querySailTranApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAlarm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALARM, request, condtion);

        condtion.addCondition("order by AlarmBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALARM, request, condtion,
            this.alarmDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * findSailTranApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findAlarm(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        AlarmBean bean = alarmDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }

        request.setAttribute("bean", bean);

        return mapping.findForward("detailAlarm");
    }

    /**
     * updateAlarm
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateAlarm(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            String reason = request.getParameter("reason");

            User user = Helper.getUser(request);

            AlarmBean bean = alarmDAO.findVO(id);

            bean.setStatus(AlarmConstant.ALARMBEAN_END);

            bean.setDescription(reason);

            commonManager.updateAlarm(user, bean);

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
     * @return the alarmDAO
     */
    public AlarmDAO getAlarmDAO()
    {
        return alarmDAO;
    }

    /**
     * @param alarmDAO
     *            the alarmDAO to set
     */
    public void setAlarmDAO(AlarmDAO alarmDAO)
    {
        this.alarmDAO = alarmDAO;
    }

    /**
     * @return the commonManager
     */
    public CommonManager getCommonManager()
    {
        return commonManager;
    }

    /**
     * @param commonManager
     *            the commonManager to set
     */
    public void setCommonManager(CommonManager commonManager)
    {
        this.commonManager = commonManager;
    }

}
