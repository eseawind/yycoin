/**
 * File Name: QueryListenerDutyImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener.impl;


import java.util.Iterator;
import java.util.List;

import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.constant.DutyConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.listener.QueryListener;


/**
 * 可见的duty
 * 
 * @author ZHUZHU
 * @version 2011-12-25
 * @see QueryListenerDutyImpl2
 * @since 3.0
 */
public class QueryListenerDutyImpl2 implements QueryListener
{
    private DutyDAO dutyDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        List<DutyBean> listEntityBeans = dutyDAO.listEntityBeans();

        for (Iterator iterator = listEntityBeans.iterator(); iterator.hasNext();)
        {
            DutyBean dutyBean = (DutyBean)iterator.next();

            if (dutyBean.getShowType() == DutyConstant.SHOWTYPE_NO)
            {
                iterator.remove();
            }

        }

        return listEntityBeans;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$dutyList2";
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
