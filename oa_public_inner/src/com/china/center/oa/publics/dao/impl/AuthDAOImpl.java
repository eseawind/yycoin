/**
 * File Name: AuthDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AuthDAO;


/**
 * AuthDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see AuthDAOImpl
 * @since 1.0
 */
public class AuthDAOImpl extends BaseDAO<AuthBean, AuthBean> implements AuthDAO
{
    /**
     * 查询区域权限
     * 
     * @return
     */
    public List<AuthBean> listLocationAuth()
    {
        return this.jdbcOperation.queryForList("where type = ? order by LEVEL, id", claz,
            PublicConstant.AUTH_TYPE_LOCATION);
    }

    public List<AuthBean> querySubAuthByParendId(String parendId)
    {
        return this.jdbcOperation.queryForList("where parentId = ? order by LEVEL, id", claz,
            parendId);
    }
}
