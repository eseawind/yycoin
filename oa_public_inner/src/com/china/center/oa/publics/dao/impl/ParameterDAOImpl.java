/**
 * File Name: ParameterDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.JdbcOperation;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.tools.MathTools;


/**
 * ParameterDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see ParameterDAOImpl
 * @since 1.0
 */
public class ParameterDAOImpl implements ParameterDAO
{
    private JdbcOperation jdbcOperation = null;

    private Map<String, String> parMap = new HashMap<String, String>();

    public void init()
    {
        List<Map> list = jdbcOperation.queryForList("select * from t_center_sysconfig");

        for (Map map : list)
        {
            parMap.put(map.get("CONFIG").toString(), map.get("VALUE").toString());
        }
    }

    public boolean getBoolean(String key)
    {
        String value = parMap.get(key);

        if ("true".equalsIgnoreCase(value))
        {
            return true;
        }

        return false;
    }

    public String getString(String key)
    {
        String value = parMap.get(key);

        if (value == null)
        {
            return "";
        }

        return parMap.get(key);
    }

    public int getInt(String key)
    {
        String value = parMap.get(key);

        return MathTools.parseInt(value);
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
}
