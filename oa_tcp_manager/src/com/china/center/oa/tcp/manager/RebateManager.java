package com.china.center.oa.tcp.manager;

import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.vo.RebateApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;

public interface RebateManager extends ListenerManager<TcpPayListener>
{
	boolean addRebateBean(User user, RebateApplyBean bean) throws MYException;
	
	boolean updateRebateBean(User user, RebateApplyBean bean) throws MYException;
	
	boolean submitRebateBean(User user, String id, String processId) throws MYException;
	
	boolean passRebateBean(User user, TcpParamWrap param) throws MYException;
	
	boolean rejectRebateBean(User user, TcpParamWrap param) throws MYException;
	
	RebateApplyVO findVO(String id);
	
    /**
     * 删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteRebateBean(User user, String id)
        throws MYException;
    
    boolean importBatchPrice(User user, List<OutBatchPriceBean> bpList) throws MYException;
}
