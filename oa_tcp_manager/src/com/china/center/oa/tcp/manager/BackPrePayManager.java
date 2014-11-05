package com.china.center.oa.tcp.manager;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.listener.BackPayApplyListener;
import com.china.center.oa.finance.vo.BackPrePayApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;

public interface BackPrePayManager extends ListenerManager<BackPayApplyListener>
{
	boolean addBackPrePayBean(User user, BackPrePayApplyBean bean) throws MYException;
	
	boolean updateBackPrePayBean(User user, BackPrePayApplyBean bean) throws MYException;
	
	boolean submitBackPrePayBean(User user, String id, String processId) throws MYException;
	
	boolean passBackPrePayBean(User user, TcpParamWrap param) throws MYException;
	
	boolean rejectBackPrePayBean(User user, TcpParamWrap param) throws MYException;
	
	BackPrePayApplyVO findVO(String id);
	
    /**
     * 删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteBackPrePayBean(User user, String id)
        throws MYException;
}
