package com.china.center.oa.tcp.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;

public interface PreInvoiceManager
{
	boolean addPreInvoiceBean(User user, PreInvoiceApplyBean bean) throws MYException;
	
	boolean updatePreInvoiceBean(User user, PreInvoiceApplyBean bean) throws MYException;
	
	boolean submitPreInvoiceBean(User user, String id, String processId) throws MYException;
	
	boolean passPreInvoiceBean(User user, TcpParamWrap param) throws MYException;
	
	boolean rejectPreInvoiceBean(User user, TcpParamWrap param) throws MYException;
	
	PreInvoiceApplyVO findVO(String id);
	
    /**
     * 删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deletePreInvoiceBean(User user, String id)
        throws MYException;
}
