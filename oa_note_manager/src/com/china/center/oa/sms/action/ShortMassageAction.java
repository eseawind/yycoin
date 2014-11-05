/**
 * File Name: WorkLogAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sms.action;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.common.MYException;
import com.china.center.oa.note.bean.ReceiveTaskBean;
import com.china.center.oa.note.manager.NoteManager;


/**
 * WorkLogAction
 * 
 * @author ZHUZHU
 * @version 2009-2-16
 * @see ShortMassageAction
 * @since 1.0
 */
public class ShortMassageAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private NoteManager noteManager = null;

    /**
     * default constructor
     */
    public ShortMassageAction()
    {}

    /**
     * query your mail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward receiveSMS(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String phone = request.getParameter("MobilePhones");

        String content = request.getParameter("Content");

        String exNumber = request.getParameter("ExNumber");

        ReceiveTaskBean task = new ReceiveTaskBean();

        task.setExNumber(exNumber);

        task.setMessage(content);

        task.setSender(phone);

        try
        {
            noteManager.addReceiveTask(task);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
        }

        response.setStatus(200);

        return null;
    }

    /**
     * @return the noteManager
     */
    public NoteManager getNoteManager()
    {
        return noteManager;
    }

    /**
     * @param noteManager
     *            the noteManager to set
     */
    public void setNoteManager(NoteManager noteManager)
    {
        this.noteManager = noteManager;
    }

}
