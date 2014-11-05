package com.china.center.oa.project.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;

public interface InvoiceLineProjectManager 
{

	 /**
     * 增加开票行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addInvoiceLineProject(User user, List<String> TinvoiceType,List<String> TinvoiceMoney,List<String> TinvoiceTime,
    		List<String> TfinishiDays1,List<String> Ts_invoiceCurTask,List<String> Ts_invoiceBeforeids,List<String> Ts_invoiceAfterids,String refid)
        throws MYException;

    
    /**
     * 修改开票行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateInvoiceLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid)
        throws MYException;
    
    public boolean submitInvoiceLineProject(User user, String id, String processId)
    throws MYException;
}
