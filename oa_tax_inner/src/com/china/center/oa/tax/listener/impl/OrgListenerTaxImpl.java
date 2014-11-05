/**
 * File Name: OrgListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-4-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.OrgListener;
import com.china.center.oa.tax.dao.FinanceItemDAO;


/**
 * OrgListenerTaxImpl
 * 
 * @author ZHUZHU
 * @version 2012-4-18
 * @see OrgListenerTaxImpl
 * @since 3.0
 */
public class OrgListenerTaxImpl implements OrgListener
{
    private FinanceItemDAO financeItemDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.OrgListener#onDeleteOrg(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.PrincipalshipBean)
     */
    public void onDeleteOrg(User user, PrincipalshipBean org)
        throws MYException
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("departmentId", "=", org.getId());

        int count = financeItemDAO.countByCondition(con.toString());

        if (count > 0)
        {
            throw new MYException("组织[%s]被凭证使用,不能删除", org.getName());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "OrgListener.TaxImpl";
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
