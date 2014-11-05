/**
 * File Name: MailDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.dao.impl;


import java.util.List;
import java.util.Map;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.tools.ListTools;


/**
 * MailDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailDAOImpl
 * @since 1.0
 */
public class MailDAOImpl extends BaseDAO<MailBean, MailBean> implements MailDAO
{
    /**
     * updateStatus
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }

    /**
     * updateFeeback
     * 
     * @param id
     * @param feeback
     * @return
     */
    public boolean updateFeeback(String id, int feeback)
    {
        this.jdbcOperation.updateField("feeback", feeback, id, claz);

        return true;
    }

    /**
     * find next mail id
     * 
     * @param currentId
     * @return
     */
    public String findNextId(String currentId, String stafferId)
    {
        List list = this.jdbcOperation.queryForList("select max(id) as maxId from" + BeanTools.getTableName2(claz)
                                                    + "where reveiveId = ? and id < ?", stafferId, currentId);

        return returnId(list);
    }

    /**
     * find preview mail id
     * 
     * @param currentId
     * @return
     */
    public String findPreviewId(String currentId, String stafferId)
    {
        List list = this.jdbcOperation.queryForList("select min(id) as maxId from" + BeanTools.getTableName2(claz)
                                                    + "where reveiveId = ? and id > ?", stafferId, currentId);

        return returnId(list);
    }

    /**
     * @param list
     * @return
     */
    private String returnId(List list)
    {
        if (ListTools.isEmptyOrNull(list))
        {
            return "";
        }

        Map map = (Map)list.get(0);

        Object oo = map.get("maxId");

        if (oo == null)
        {
            return "";
        }

        return oo.toString();
    }
}
