/**
 * File Name: CustomerCreditApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import java.io.Serializable;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.vo.CustomerCreditApplyVO;
import com.china.center.oa.credit.vs.CustomerCreditApplyBean;


/**
 * CustomerCreditApplyDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditApplyDAO
 * @since 1.0
 */
public interface CustomerCreditApplyDAO extends DAO<CustomerCreditApplyBean, CustomerCreditApplyVO>
{
    boolean hasUpdate(Serializable cid);
}
