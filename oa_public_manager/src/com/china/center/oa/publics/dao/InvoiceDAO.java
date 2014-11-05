/**
 * File Name: InvoiceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.InvoiceBean;


/**
 * InvoiceBean
 * 
 * @author ZHUZHU
 * @version 2010-9-19
 * @see InvoiceDAO
 * @since 1.0
 */
public interface InvoiceDAO extends DAO<InvoiceBean, InvoiceBean>
{
    List<InvoiceBean> listForwardIn();

    List<InvoiceBean> listForwardOut();

    List<InvoiceBean> queryForwardInByDutyId(String dutyId);

    List<InvoiceBean> queryForwardInByType(int type);

    List<InvoiceBean> queryForwardOutByDutyId(String dutyId);

    List<InvoiceBean> queryForwardOutByType(int type);
}
