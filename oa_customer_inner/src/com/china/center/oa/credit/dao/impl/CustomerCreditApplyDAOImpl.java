/**
 * File Name: CustomerCreditApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import java.io.Serializable;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.dao.CustomerCreditApplyDAO;
import com.china.center.oa.credit.vo.CustomerCreditApplyVO;
import com.china.center.oa.credit.vs.CustomerCreditApplyBean;


/**
 * CustomerCreditApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditApplyDAOImpl
 * @since 1.0
 */
public class CustomerCreditApplyDAOImpl extends BaseDAO<CustomerCreditApplyBean, CustomerCreditApplyVO> implements CustomerCreditApplyDAO
{
    public boolean hasUpdate(Serializable cid)
    {
        return super.countByFK(cid) > 0;
    }
}
