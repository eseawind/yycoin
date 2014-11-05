/**
 * File Name: OutQueryDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customervssail.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.JdbcOperation;
import com.china.center.oa.client.wrap.NotPayWrap;
import com.china.center.oa.customervssail.dao.OutQueryDAO;
import com.china.center.oa.publics.helper.OATools;


/**
 * OutQueryDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-14
 * @see OutQueryDAOImpl
 * @since 3.0
 */
public class OutQueryDAOImpl implements OutQueryDAO
{
    private JdbcOperation jdbcOperation = null;

    private IbatisDaoSupport ibatisDaoSupport = null;

    /**
     * default constructor
     */
    public OutQueryDAOImpl()
    {
    }

    /**
     * 鏌ヨ褰撳墠鎵�湁鐨勫瓨鍦ㄥ簲鏀剁殑瀹㈡埛
     * 
     * @return
     */
    public List<NotPayWrap> listNotPayWrap()
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("outTime", OATools.getFinanceBeginDate());

        List<NotPayWrap> result = ibatisDaoSupport.queryForList("OutQueryDAO.listNotPayWrap",
            paramterMap);

        return result;
    }

    /**
     * @return the jdbcOperation
     */
    public JdbcOperation getJdbcOperation()
    {
        return jdbcOperation;
    }

    /**
     * @param jdbcOperation
     *            the jdbcOperation to set
     */
    public void setJdbcOperation(JdbcOperation jdbcOperation)
    {
        this.jdbcOperation = jdbcOperation;
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport()
    {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }

}
