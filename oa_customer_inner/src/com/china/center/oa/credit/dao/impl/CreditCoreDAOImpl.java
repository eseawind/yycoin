/**
 * File Name: CreditCoreDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.bean.CreditCoreBean;
import com.china.center.oa.credit.dao.CreditCoreDAO;


/**
 * CreditCoreDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditCoreDAOImpl
 * @since 1.0
 */
public class CreditCoreDAOImpl extends BaseDAO<CreditCoreBean, CreditCoreBean> implements CreditCoreDAO
{
    /**
     * 把数据付给历史字段
     * 
     * @param oldYear
     * @return
     */
    public int synMaxBusinessToOld(int oldYear)
    {
        return this.jdbcOperation.update("set oldMaxBusiness = maxBusiness, oldSumTotal = sumTotal where 1 = 1", claz);
    }

    public boolean updateCurCreToInit(String pid, String cid)
    {
        jdbcOperation.update("update T_CENTER_VS_CURCRE set val = 0.0 where pitemId = ? and cid = ?", pid, cid);

        // 需要重新计算
        double newCre = Math.ceil(jdbcOperation.queryForDouble("select sum(val) from T_CENTER_VS_CURCRE where cid = ?",
            cid));

        final List<String> list = new ArrayList();

        jdbcOperation.query("select * from t_center_credit_level where min <= ? and max >= ?", new Object[] {newCre,
            newCre}, new RowCallbackHandler()
        {
            public void processRow(ResultSet rst)
                throws SQLException
            {
                list.add(rst.getString("id"));
            }
        });

        if (list.size() > 0)
        {
            jdbcOperation.update("update t_center_customer_main set creditLevelId = ?, creditVal = ? where id = ?", list
                .get(0), newCre, cid);
        }

        return true;
    }
}
