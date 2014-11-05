/**
 * File Name: AuthDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.AuthBean;


/**
 * AuthDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see AuthDAO
 * @since 1.0
 */
public interface AuthDAO extends DAO<AuthBean, AuthBean>
{
    /**
     * 可配置权限
     * 
     * @return
     */
    List<AuthBean> listLocationAuth();

    /**
     * querySubAuthByParendId(仅仅查询下一级的权限,不递归)
     * 
     * @param parendId
     * @return
     */
    List<AuthBean> querySubAuthByParendId(String parendId);
}
