/**
 * File Name: QueryListenerDutyImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener.impl;


import java.util.List;

import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * QueryListenerDutyImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-25
 * @see QueryListenerDutyImpl
 * @since 3.0
 */
public class QueryListenerDutyImpl implements QueryListener
{
    private DutyDAO dutyDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        return dutyDAO.listEntityBeans();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$dutyList";
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }
}
