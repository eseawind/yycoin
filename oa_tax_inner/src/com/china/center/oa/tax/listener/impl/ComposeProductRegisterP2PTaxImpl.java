/**
 * File Name: ComposeProductListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.listener.impl;


import java.util.HashMap;
import java.util.Map;

import com.china.center.common.MYException;
import com.china.center.oa.publics.message.MessageConstant;
import com.china.center.oa.publics.message.RegisterP2P;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.tools.StringTools;


/**
 * 产品合成显示科目信息
 * 
 * @author ZHUZHU
 * @version 2011-5-8
 * @see ComposeProductRegisterP2PTaxImpl
 * @since 3.0
 */
public class ComposeProductRegisterP2PTaxImpl implements RegisterP2P
{
    private TaxDAO taxDAO = null;

    /**
     * default constructor
     */
    public ComposeProductRegisterP2PTaxImpl()
    {
    }

    public Map<String, String> publicP2PMessage(String msgId, String conent)
        throws MYException
    {
        Map<String, String> result = new HashMap<String, String>();

        if (StringTools.isNullOrNone(conent))
        {
            return result;
        }

        TaxBean tax = taxDAO.find(conent);

        if (tax != null)
        {
            result.put(MessageConstant.RESULT, tax.getCode() + tax.getName());
        }

        return result;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
    }

    public String getKey()
    {
        return "ComposeProductRegisterP2PTaxImpl.RegisterP2P";
    }

    public String getMessageType()
    {
        return MessageConstant.FINDCOMPOSEFEEDEFINEDVO;
    }
}
