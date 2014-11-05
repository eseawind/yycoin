/**
 * File Name: CustomerCreditDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import java.io.Serializable;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.vo.CustomerCreditVO;
import com.china.center.oa.credit.vs.CustomerCreditBean;
import com.china.center.oa.customer.constant.CreditConstant;


/**
 * CustomerCreditDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditDAOImpl
 * @since 1.0
 */
public class CustomerCreditDAOImpl extends BaseDAO<CustomerCreditBean, CustomerCreditVO> implements CustomerCreditDAO
{
    public double sumValByFK(Serializable cid)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "val") + "where cid = ?", cid);
    }

    /**
     * 剔除
     * 
     * @param cid
     * @param exceptionId
     * @return
     */
    public double sumValExceptionByFK(Serializable cid, String exceptionId)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "val") + "where cid = ? and itemId <> ?",
            cid, exceptionId);
    }

    public double sumValExceptPersonByFK(Serializable cid)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "val") + "where cid = ? and itemId <> ?",
            cid, CreditConstant.SET_DRECT);
    }

    /**
     * countByValueId
     * 
     * @param valueId
     * @return
     */
    public int countByValueId(Serializable valueId)
    {
        return this.jdbcOperation.queryForInt(BeanTools.getCountHead(claz) + "where valueid = ?", valueId);
    }
    
    public double sumValByFK(Serializable cid, int type)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "val") + "where cid = ? and ptype = ?", cid, type);
    }
}
