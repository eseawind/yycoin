/**
 * File Name: EnumDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.vo.EnumVO;


/**
 * EnumDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see EnumDAOImpl
 * @since 1.0
 */
public class EnumDAOImpl extends BaseDAO<EnumBean, EnumVO> implements EnumDAO
{
    /**
     * findByTypeAndEnumIndex
     * 
     * @param type
     * @param key
     * @return
     */
    public EnumBean findByTypeAndEnumIndex(int type, String key)
    {
        return this.findUnique("where type = ? and keyss = ?", type, key);
    }
}
