/**
 * File Name: CustomerCreditListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.sail.manager.OutManager;


/**
 * CustomerCreditListenerSailImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-20
 * @see CustomerListenerSailImpl
 * @since 3.0
 */
public class CustomerListenerSailImpl implements ClientListener
{
    private OutDAO outDAO = null;

    private StafferDAO stafferDAO = null;

    private OutManager outManager = null;
    
    /**
     * default constructor
     */
    public CustomerListenerSailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerCreditListener#onNoPayBusiness(com.china.center.oa.customer.bean.CustomerBean)
     */
    public double onNoPayBusiness(CustomerBean bean)
    {
        return outDAO.sumNoPayBusiness(bean.getId(), YYTools.getFinanceBeginDate(), YYTools
            .getFinanceEndDate());
    }

    public void onDelete(CustomerBean bean)
        throws MYException
    {
        if (outDAO.countCustomerInOut(bean.getId()) > 0)
        {
            throw new MYException("客户[%s]名下存在销售单,不能删除", bean.getName());
        }
    }

    /**
     * 未完全付款的销售单移交(只有销售出库的单据,其他的不能移交)
     */
    public void onChangeCustomerRelation(User user, AssignApplyBean apply, CustomerBean cus)
        throws MYException
    {
    	StafferBean staffer = stafferDAO.find(apply.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 查询此客户下所有的未付款的销售单(2011-04-01之后) -modify 2012.7.2
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("customerId", "=", cus.getId());
        con.addIntCondition("pay", "=", OutConstant.PAY_NOT);
        con.addIntCondition("outType", "=", OutConstant.OUTTYPE_OUT_COMMON);
        con.addIntCondition("type", "=", OutConstant.OUT_TYPE_OUTBILL);

        // 四月数据
        con.addCondition("outTime", ">=", "2011-04-01");
        
        // 处理回款或者结束  -- modify 所有 
        //con.addCondition("and status in (3, 4)");

        List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);

        if (outList.size() > 0)
        {
            outManager.tranCompleteOutListNT(user, outList, staffer);
        }
        
        //处理委托代销未全部结算的单子
        con.clear();
        
        con.addWhereStr();
        
        con.addCondition("customerId", "=", cus.getId());
        con.addIntCondition("pay", "=", OutConstant.PAY_NOT);
        con.addIntCondition("outType", "=", OutConstant.OUTTYPE_OUT_CONSIGN);
        con.addIntCondition("type", "=", OutConstant.OUT_TYPE_OUTBILL);

        // 四月数据
        con.addCondition("outTime", ">=", "2011-04-01");

        // 处理回款或者结束
        con.addCondition("and status in (3, 4)");
        
        List<OutBean> outList1 = outDAO.queryEntityBeansByCondition(con);

        if (outList1.size() > 0)
        {
            outManager.tranCompleteConsignOutListNT(user, outList1, staffer);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "CustomerListener.SailImpl";
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
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
