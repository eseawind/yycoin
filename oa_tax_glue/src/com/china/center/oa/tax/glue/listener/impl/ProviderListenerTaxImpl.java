/**
 * File Name: ProviderListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.listener.ProviderListener;
import com.china.center.oa.tax.dao.FinanceItemDAO;


/**
 * ProviderListenerTaxImpl
 * 
 * @author ZHUZHU
 * @version 2011-8-21
 * @see ProviderListenerTaxImpl
 * @since 3.0
 */
public class ProviderListenerTaxImpl implements ProviderListener
{
    private FinanceItemDAO financeItemDAO = null;

    /**
     * default constructor
     */
    public ProviderListenerTaxImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.ProviderListener#onDelete(com.china.center.oa.product.bean.ProviderBean)
     */
    public void onDelete(ProviderBean bean)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("unitId", "=", bean.getId());

        int countByCondition = financeItemDAO.countByCondition(condtion.toString());

        if (countByCondition > 0)
        {
            throw new MYException("供应商[%s]已经生成[%d]条凭证数据,不能删除", bean.getName(), countByCondition);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "ProviderListener.TaxImpl";
    }

    /**
     * @return the financeItemDAO
     */
    public FinanceItemDAO getFinanceItemDAO()
    {
        return financeItemDAO;
    }

    /**
     * @param financeItemDAO
     *            the financeItemDAO to set
     */
    public void setFinanceItemDAO(FinanceItemDAO financeItemDAO)
    {
        this.financeItemDAO = financeItemDAO;
    }

}
