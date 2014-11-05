/**
 * File Name: ShowListenerFinanceImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.publics.listener.ShowListener;


/**
 * ShowListenerFinanceImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-4
 * @see ShowListenerFinanceImpl
 * @since 3.0
 */
public class ShowListenerFinanceImpl implements ShowListener
{
    private InvoiceinsItemDAO invoiceinsItemDAO = null;

    /**
     * default constructor
     */
    public ShowListenerFinanceImpl()
    {
    }

    public void onDeleteShow(User user, String id)
        throws MYException
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("showId", "=", id);

        int count = invoiceinsItemDAO.countByCondition(condition.toString());

        if (count > 0)
        {
            throw new MYException("已经被发票项引用");
        }
    }

    public String getListenerType()
    {
        return "ShowListener.FinanceImpl";
    }

    /**
     * @return the invoiceinsItemDAO
     */
    public InvoiceinsItemDAO getInvoiceinsItemDAO()
    {
        return invoiceinsItemDAO;
    }

    /**
     * @param invoiceinsItemDAO
     *            the invoiceinsItemDAO to set
     */
    public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
    {
        this.invoiceinsItemDAO = invoiceinsItemDAO;
    }

}
