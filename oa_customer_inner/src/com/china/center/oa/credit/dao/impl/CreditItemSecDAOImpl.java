/**
 * File Name: CreditItemSecDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.vo.CreditItemSecVO;


/**
 * CreditItemSecDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditItemSecDAOImpl
 * @since 1.0
 */
public class CreditItemSecDAOImpl extends BaseDAO<CreditItemSecBean, CreditItemSecVO> implements CreditItemSecDAO
{
    /**
     * sumPerByPid
     * 
     * @param pid
     * @return
     */
    public double sumPerByPid(String pid)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "per") + "where pid = ?", pid);
    }
}
