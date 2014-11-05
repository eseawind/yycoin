/**
 * File Name: CustomerListenerFinanceImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;


/**
 * CustomerListenerFinanceImpl
 * 
 * @author ZHUZHU
 * @version 2011-3-9
 * @see CustomerListenerFinanceImpl
 * @since 3.0
 */
public class CustomerListenerFinanceImpl implements ClientListener
{
    private InBillDAO inBillDAO = null;

    private BillManager billManager = null;

    private OutBillDAO outBillDAO = null;

    private StafferDAO stafferDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerListener#onDelete(com.china.center.oa.customer.bean.CustomerBean)
     */
    public void onDelete(CustomerBean bean)
        throws MYException
    {
        if (inBillDAO.countUnitInBill(bean.getId()) > 0)
        {
            throw new MYException("客户[%s]已经被收款单使用,不能删除", bean.getName());
        }

        if (outBillDAO.countUnitInBill(bean.getId()) > 0)
        {
            throw new MYException("客户[%s]已经被付款单使用,不能删除", bean.getName());
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
     * 客户预收全部移交
     */
    public void onChangeCustomerRelation(User user, AssignApplyBean apply, CustomerBean cus)
        throws MYException
    {
        StafferBean destStaffer = stafferDAO.find(apply.getStafferId());

        if (destStaffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("customerId", "=", cus.getId());

        con.addIntCondition("status", "=", FinanceConstant.INBILL_STATUS_NOREF);

        // 客户下所有的预收
        List<InBillBean> inBillList = inBillDAO.queryEntityBeansByCondition(con);

        if (inBillList.size() > 0)
        {
            billManager.changeBillListToTranWithoutTransactional(user, inBillList, destStaffer);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "CustomerListener.FinanceImpl";
    }

    /**
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the billManager
     */
    public BillManager getBillManager()
    {
        return billManager;
    }

    /**
     * @param billManager
     *            the billManager to set
     */
    public void setBillManager(BillManager billManager)
    {
        this.billManager = billManager;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

}
