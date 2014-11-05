/**
 * File Name: LogoutAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * LogoutAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see LogoutAction
 * @since 1.0
 */
public class LogoutAction extends Action
{
    private static Log logger = LogFactory.getLog(LogoutAction.class);

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                 HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);

        if (session == null)
        {
            logger.error("LogoutAction.execute. getSession is null");
            return actionMapping.findForward("success");
        }

        session.invalidate();

        return actionMapping.findForward("success");
    }
}
