/**
 * File Name: CustomerCreditManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.manager;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.credit.vs.CustomerCreditApplyBean;
import com.china.center.oa.credit.vs.CustomerCreditBean;


/**
 * CustomerCreditManager
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditManager
 * @since 1.0
 */
public interface CustomerCreditManager
{
    boolean configSpecialCredit(User user, String ccode, List<CustomerCreditBean> creditList)
        throws MYException;

    boolean configCustomerCredit(User user, String cid, List<CustomerCreditBean> creditList)
        throws MYException;

    void updateCustomerCredit(String cid)
        throws MYException;

    boolean interposeCredit(User user, String cid, double newCreditVal)
        throws MYException;

    boolean interposeCredit(User user, String cid, CustomerCreditBean customerCreditBean)
        throws MYException;

    boolean interposeCreditInner(User user, String cid, CustomerCreditBean customerCreditBean)
        throws MYException;

    boolean applyConfigStaticCustomerCredit(User user, String cid, List<CustomerCreditApplyBean> creditList)
        throws MYException;

    boolean doPassApplyConfigStaticCustomerCredit(User user, String cid)
        throws MYException;

    boolean doRejectApplyConfigStaticCustomerCredit(User user, String cid)
        throws MYException;
    
    boolean interposeCreditInnerWithoutUpdateLevel(User user, String cid, CustomerCreditBean customerCreditBean)
    throws MYException;
    
    void updateCustomerCredit2(String cid)
    throws MYException;
}
