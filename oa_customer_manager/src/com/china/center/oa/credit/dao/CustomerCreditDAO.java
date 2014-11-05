/**
 * File Name: CustomerCreditDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import java.io.Serializable;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.vo.CustomerCreditVO;
import com.china.center.oa.credit.vs.CustomerCreditBean;


/**
 * CustomerCreditDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditDAO
 * @since 1.0
 */
public interface CustomerCreditDAO extends DAO<CustomerCreditBean, CustomerCreditVO>
{
    double sumValByFK(Serializable cid);

    double sumValExceptionByFK(Serializable cid, String exceptionId);

    double sumValExceptPersonByFK(Serializable cid);

    int countByValueId(Serializable valueId);
    
    double sumValByFK(Serializable cid, int type);
}
