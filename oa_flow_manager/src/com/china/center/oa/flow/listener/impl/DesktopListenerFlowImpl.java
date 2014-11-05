/**
 * File Name: DeskListenerMailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.flow.dao.FlowBelongDAO;
import com.china.center.oa.flow.vo.FlowBelongVO;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.wrap.DesktopItemWrap;
import com.china.center.oa.publics.wrap.DesktopWrap;
import com.china.center.tools.StringTools;


/**
 * DeskListenerMailImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopListenerFlowImpl
 * @since 3.0
 */
public class DesktopListenerFlowImpl implements DesktopListener
{
    private FlowBelongDAO flowBelongDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.DeskListener#getDeskWrap()
     */
    public DesktopWrap getDeskWrap(User user)
    {
        DesktopWrap wrap = new DesktopWrap();

        wrap.setName("待我审批(流程)");

        final ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        condtition.addCondition("FlowBelongBean.stafferId", "=", user.getStafferId());

        condtition.addCondition("order by FlowBelongBean.logTime asc");

        List<FlowBelongVO> flowList = flowBelongDAO.queryEntityVOsByLimit(condtition, 5);

        List<DesktopItemWrap> itemList = new ArrayList();

        for (FlowBelongVO vo : flowList)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            item.setHref("../flow/instance.do?method=findFlowInstance&id=" + vo.getInstanceId());
            // [${item.logTime}] ${my:truncateString(item.title, 0, 45)}
            item.setTitle("[" + vo.getLogTime() + "] "
                          + StringTools.truncateString(vo.getTitle(), 0, 45));

            itemList.add(item);
        }

        wrap.setItemList(itemList);

        return wrap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.DeskListener#getKey()
     */
    public String getKey()
    {
        return "flow";
    }

    /**
     * @return the flowBelongDAO
     */
    public FlowBelongDAO getFlowBelongDAO()
    {
        return flowBelongDAO;
    }

    /**
     * @param flowBelongDAO
     *            the flowBelongDAO to set
     */
    public void setFlowBelongDAO(FlowBelongDAO flowBelongDAO)
    {
        this.flowBelongDAO = flowBelongDAO;
    }

}
