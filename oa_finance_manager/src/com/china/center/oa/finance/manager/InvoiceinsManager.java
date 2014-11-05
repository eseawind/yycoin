/**
 * File Name: InvoiceinsManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsImportBean;
import com.china.center.oa.finance.listener.InvoiceinsListener;
import com.china.center.oa.finance.vo.InvoiceinsVO;


/**
 * InvoiceinsManager
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsManager
 * @since 3.0
 */
public interface InvoiceinsManager extends ListenerManager<InvoiceinsListener>
{
    boolean addInvoiceinsBean(User user, InvoiceinsBean bean)
        throws MYException;

    boolean passInvoiceinsBean(User user, InvoiceinsBean bean, String reason)
        throws MYException;

    boolean checkInvoiceinsBean(User user, String id, String reason)
        throws MYException;

    boolean rejectInvoiceinsBean(User user, String id, String reason)
        throws MYException;

    /**
     * 总部核对
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean checkInvoiceinsBean2(User user, String id, String checks, String refId)
        throws MYException;

    boolean deleteInvoiceinsBean(User user, String id)
        throws MYException;

    InvoiceinsVO findVO(String id);

    void clearRejectInvoiceinsBean()
        throws MYException;
    
    boolean confirmInvoice(User user, String id)
    throws MYException;
    
    boolean confirmPay(User user, String id)
    throws MYException;
    
    boolean backInvoiceins(User user, String id)
    throws MYException;
    
    boolean importInvoice(User user, List<InvoiceStorageBean> list)
    throws MYException;
    
    boolean refConfirmInvoice(User user, List<InvoiceBindOutBean> vsList)
	throws MYException;
    
    boolean refInvoice(User user, List<InvoiceBindOutBean> vsList)
	throws MYException;
    
    String importInvoiceins(User user, List<InvoiceinsImportBean> list)
    throws MYException;
    
    boolean batchUpdateInsNum(User user, List<InvoiceinsImportBean> list)
    throws MYException;
    
    boolean process(List<InvoiceinsImportBean> list)
	throws MYException;

    boolean processAsyn(List<InvoiceinsImportBean> list);
    
    void checkImportIns(List<InvoiceinsImportBean> list, StringBuilder sb);
}
