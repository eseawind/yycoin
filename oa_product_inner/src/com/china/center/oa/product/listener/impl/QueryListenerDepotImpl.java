/**
 * File Name: QueryListenerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener.impl;


import java.util.List;

import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryListenerDepotImpl
 * @since 1.0
 */
public class QueryListenerDepotImpl implements QueryListener
{
    private DepotDAO depotDAO = null;

    /**
     * default constructor
     */
    public QueryListenerDepotImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return depotDAO.queryCommonDepotBean();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$depotList";
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }

}
