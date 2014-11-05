/**
 * File Name: DutyListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.listener.DutyListener;
import com.china.center.oa.tax.dao.FinanceDAO;


/**
 * DutyListenerTaxImpl
 * 
 * @author ZHUZHU
 * @version 2012-1-3
 * @see DutyListenerTaxImpl
 * @since 1.0
 */
public class DutyListenerTaxImpl implements DutyListener
{
    private FinanceDAO financeDAO = null;

    /**
     * 删除纳税实体需要校验凭证
     */
    public void onDeleteDuty(User user, DutyBean bean)
        throws MYException
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addIntCondition("dutyId", "=", bean.getId());

        int count = financeDAO.countByCondition(con.toString());

        if (count > 0)
        {
            throw new MYException("纳税实体被使用,不能删除");
        }
    }

    public String getListenerType()
    {
        return "DutyListener.TaxImpl";
    }

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

}
