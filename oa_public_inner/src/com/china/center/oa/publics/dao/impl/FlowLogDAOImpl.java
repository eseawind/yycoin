/**
 * File Name: FlowLogDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.FlowLogDAO;


/**
 * FlowLogDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see FlowLogDAOImpl
 * @since 1.0
 */
public class FlowLogDAOImpl extends BaseDAO<FlowLogBean, FlowLogBean> implements FlowLogDAO
{
    public FlowLogBean findLastLog(String refId)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("fullId", "=", refId);

        con.addCondition("order by id desc");

        List<FlowLogBean> list = this.queryEntityBeansByCondition(con);

        if (list.size() > 0)
        {
            return list.get(0);
        }

        return null;
    }
}
