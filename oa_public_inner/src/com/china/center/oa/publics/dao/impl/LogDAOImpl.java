/**
 * File Name: LogDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.vo.LogVO;


/**
 * LogDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see LogDAOImpl
 * @since 1.0
 */
public class LogDAOImpl extends BaseDAO<LogBean, LogVO> implements LogDAO
{
    /**
     * findLogBeanByFKAndPosid
     * 
     * @param fk
     * @param posid
     * @return
     */
    public LogBean findLogBeanByFKAndPosid(String fk, String posid)
    {
        List<LogBean> list = this.queryEntityBeansByCondition(
            "where fkId = ? and posid = ? order by logTime desc", fk, posid);

        if (list.isEmpty())
        {
            return null;
        }

        return list.get(0);
    }
}
