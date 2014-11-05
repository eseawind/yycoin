/**
 * File Name: DeskListenerMailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.listener.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.User;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.wrap.DesktopItemWrap;
import com.china.center.oa.publics.wrap.DesktopWrap;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.tools.StringTools;


/**
 * DeskListenerMailImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopListenerTCPImpl
 * @since 3.0
 */
public class DesktopListenerTCPImpl implements DesktopListener
{
    private TcpApproveDAO tcpApproveDAO = null;
    
    private TravelApplyDAO travelApplyDAO = null;
    
    private final Log _logger = LogFactory.getLog(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.DeskListener#getDeskWrap()
     */
    public DesktopWrap getDeskWrap(User user)
    {
        DesktopWrap wrap = new DesktopWrap();

        wrap.setName("报销审批");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_COMMON);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        List<TcpApproveVO> flowList = tcpApproveDAO.queryEntityVOsByLimit(condtion, 5);

        List<DesktopItemWrap> itemList = new ArrayList();

        for (TcpApproveVO vo : flowList)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            TCPHelper.getTcpApproveVO(vo);
            TravelApplyBean bean = travelApplyDAO.find(vo.getApplyId());
            if(bean != null)
            {
	            if(vo.getApplyId().startsWith("TC"))
	            {
	        		vo.setUrl(TcpConstanst.TCP_COMMIS_PROCESS_URL + vo.getApplyId());
	            }
	        	else if(bean.getType() == TcpConstanst.VOCATION_WORK)
	        	{
	            vo.setUrl(TcpConstanst.TCP_COMMIS_PROCESS_URL + vo.getApplyId());
	        	}
            }
            item.setHref(vo.getUrl());

            item.setTitle("[" + vo.getLogTime() + "] "
                          + StringTools.truncateString(vo.getName(), 0, 45));

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
        return "tcp";
    }

    /**
     * @return the tcpApproveDAO
     */
    public TcpApproveDAO getTcpApproveDAO()
    {
        return tcpApproveDAO;
    }

    /**
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
    }

	public TravelApplyDAO getTravelApplyDAO() {
		return travelApplyDAO;
	}

	public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO) {
		this.travelApplyDAO = travelApplyDAO;
	}

}
