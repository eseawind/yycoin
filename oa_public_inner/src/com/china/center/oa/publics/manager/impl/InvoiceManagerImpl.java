/**
 * File Name: InvoiceManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.manager.InvoiceManager;
import com.china.center.tools.JudgeTools;


/**
 * InvoiceManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-19
 * @see InvoiceManagerImpl
 * @since 1.0
 */
@Exceptional
public class InvoiceManagerImpl implements InvoiceManager
{
    private InvoiceDAO invoiceDAO = null;

    /**
     * default constructor
     */
    public InvoiceManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.InvoiceManager#updateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.InvoiceBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, InvoiceBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        InvoiceBean old = invoiceDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 只修改税点和描述
        old.setVal(bean.getVal());

        old.setDescription(bean.getDescription());

        invoiceDAO.updateEntityBean(old);

        return true;
    }

    /**
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO()
    {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO
     *            the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO)
    {
        this.invoiceDAO = invoiceDAO;
    }

}
