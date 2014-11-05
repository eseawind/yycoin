/**
 * File Name: CustomerListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.tax.dao.FinanceItemDAO;


/**
 * 客户删除的凭证监听
 * 
 * @author ZHUZHU
 * @version 2011-8-21
 * @see CustomerListenerTaxImpl
 * @since 3.0
 */
public class CustomerListenerTaxImpl implements ClientListener
{
    private FinanceItemDAO financeItemDAO = null;

    /**
     * default constructor
     */
    public CustomerListenerTaxImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerListener#onDelete(com.china.center.oa.customer.bean.CustomerBean)
     */
    public void onDelete(CustomerBean bean)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("unitId", "=", bean.getId());

        int countByCondition = financeItemDAO.countByCondition(condtion.toString());

        if (countByCondition > 0)
        {
            throw new MYException("客户[%s]已经生成[%d]条凭证数据,不能删除", bean.getName(), countByCondition);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerListener#onNoPayBusiness(com.china.center.oa.customer.bean.CustomerBean)
     */
    public double onNoPayBusiness(CustomerBean bean)
    {
        return 0;
    }

    /**
     * 通过其他实现
     */
    public void onChangeCustomerRelation(User user, AssignApplyBean apply, CustomerBean cus)
        throws MYException
    {
        // NA
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "CustomerListener.TaxImpl";
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
