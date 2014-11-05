/**
 * File Name: PersionalAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.util.ArrayList;
import java.util.Collection;
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
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.manager.DesktopManager;
import com.china.center.oa.publics.wrap.DesktopWrap;


/**
 * PersionalAction
 * 
 * @author ZHUZHU
 * @version 2009-6-10
 * @see DesktopAction
 * @since 1.0
 */
public class DesktopAction extends DispatchAction
{
    private DesktopManager desktopManager = null;

    private StafferDAO stafferDAO = null;
    
    /**
     * default constructor
     */
    public DesktopAction()
    {
    }

    /**
     * queryPersionalDeskTop
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPersionalDeskTop(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        Map<String, DesktopListener> listenerMap = desktopManager.getListenerMap();

        Collection<DesktopListener> values = listenerMap.values();

        List<DesktopWrap> wrapList = new ArrayList();

        for (DesktopListener deskListener : values)
        {
            DesktopWrap deskWrap = deskListener.getDeskWrap(user);

            wrapList.add(deskWrap);
        }

        request.setAttribute("wrapList", wrapList);

        // 增加是否为黑名单的提示
        String stafferId = user.getStafferId();
        
        StafferBean stafferBean = stafferDAO.find(stafferId);
        
        if (stafferBean.getBlack() != StafferConstant.BLACK_NO)
        {
            String blackName = DefinedCommon.getValue("stafferBlack", stafferBean.getBlack());
            
            String msg = "您现在已进入黑名单，类型为:【"+blackName+"】，请尽快确认回款";
            
            request.setAttribute("black", msg);
        }
        else
        {
            request.setAttribute("black", "");
        }
        
        return mapping.findForward("desktop");
    }

    /**
     * @return the desktopManager
     */
    public DesktopManager getDesktopManager()
    {
        return desktopManager;
    }

    /**
     * @param desktopManager
     *            the desktopManager to set
     */
    public void setDesktopManager(DesktopManager desktopManager)
    {
        this.desktopManager = desktopManager;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }
    
}
