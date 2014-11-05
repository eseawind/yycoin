/**
 * File Name: EnumDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.vo.EnumVO;


/**
 * EnumDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see EnumDAO
 * @since 1.0
 */
public interface EnumDAO extends DAO<EnumBean, EnumVO>
{
    EnumBean findByTypeAndEnumIndex(int type, String key);
}
