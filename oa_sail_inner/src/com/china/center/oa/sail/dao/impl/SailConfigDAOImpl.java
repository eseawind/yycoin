/**
 * File Name: SailConfigDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.vo.SailConfigVO;


/**
 * SailConfigDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigDAOImpl
 * @since 3.0
 */
public class SailConfigDAOImpl extends BaseDAO<SailConfigBean, SailConfigVO> implements SailConfigDAO
{
    public int countSailConfigByConstion(ConditionParse condition)
    {
        return jdbcOperation.queryObjectsBySql(getLastQuerySql(condition)).getCount();
    }

    public List<SailConfigVO> querySailConfigByConstion(ConditionParse condition, PageSeparate page)
    {
        return jdbcOperation.queryObjectsBySql(getLastQuerySql(condition)).list(SailConfigVO.class);
    }

    private String getQuerySelfSql()
    {
        return "select SailConfigBean.* , ShowBean.name as showName "
               + "from T_CENTER_SAILCONFIG SailConfigBean, T_CENTER_SHOW ShowBean where SailConfigBean.showId = ShowBean.id";
    }

    private String getLastQuerySql(ConditionParse condition)
    {
        ConditionParse copy = new ConditionParse();

        copy.setCondition(condition.toString());

        copy.removeWhereStr();

        return getQuerySelfSql() + ' ' + copy.toString();
    }
}
