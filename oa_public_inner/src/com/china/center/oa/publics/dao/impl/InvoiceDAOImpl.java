/**
 * File Name: InvoiceDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.dao.InvoiceDAO;


/**
 * InvoiceDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-19
 * @see InvoiceDAOImpl
 * @since 1.0
 */
public class InvoiceDAOImpl extends BaseDAO<InvoiceBean, InvoiceBean> implements InvoiceDAO
{
    public List<InvoiceBean> listForwardIn()
    {
        ConditionParse condition = new ConditionParse();

        condition.addIntCondition("forward", "=", InvoiceConstant.INVOICE_FORWARD_IN);

        return this.queryEntityBeansByCondition(condition);
    }

    public List<InvoiceBean> listForwardOut()
    {
        ConditionParse condition = new ConditionParse();

        condition.addIntCondition("forward", "=", InvoiceConstant.INVOICE_FORWARD_OUT);

        return this.queryEntityBeansByCondition(condition);
    }

    public List<InvoiceBean> queryForwardInByDutyId(String dutyId)
    {
        String sql = "select t1.* from T_CENTER_INVOICE t1, T_CENTER_DUTYENTITY t2 , T_CENTER_VS_DUTYINV t3 "
                     + "where t1.forward = ? and t1.id = t3.invoiceId and t3.dutyType = t2.type and t2.id = ?";

        return this.jdbcOperation
            .queryObjectsBySql(sql, InvoiceConstant.INVOICE_FORWARD_IN, dutyId)
            .list(this.claz);
    }

    public List<InvoiceBean> queryForwardInByType(int type)
    {
        String sql = "select t1.* from T_CENTER_INVOICE t1, T_CENTER_VS_DUTYINV t2 "
                     + "where t1.forward = ? and t1.id = t2.invoiceId and t2.dutyType = ?";

        return this.jdbcOperation
            .queryObjectsBySql(sql, InvoiceConstant.INVOICE_FORWARD_IN, type)
            .list(this.claz);
    }

    public List<InvoiceBean> queryForwardOutByDutyId(String dutyId)
    {
        String sql = "select t1.* from T_CENTER_INVOICE t1, T_CENTER_DUTYENTITY t2 , T_CENTER_VS_DUTYINV t3 "
                     + "where t1.forward = ? and t1.id = t3.invoiceId and t3.dutyType = t2.type and t2.id = ?";

        return this.jdbcOperation.queryObjectsBySql(sql, InvoiceConstant.INVOICE_FORWARD_OUT,
            dutyId).list(this.claz);
    }

    public List<InvoiceBean> queryForwardOutByType(int type)
    {
        String sql = "select t1.* from T_CENTER_INVOICE t1, T_CENTER_VS_DUTYINV t2 "
                     + "where t1.forward = ? and t1.id = t2.invoiceId and t2.dutyType = ?";

        return this.jdbcOperation
            .queryObjectsBySql(sql, InvoiceConstant.INVOICE_FORWARD_OUT, type)
            .list(this.claz);
    }
}
