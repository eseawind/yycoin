/**
 * File Name: CurOutDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.bean.CurOutBean;


/**
 * CurOutDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CurOutDAO
 * @since 1.0
 */
public interface CurOutDAO extends DAO<CurOutBean, CurOutBean>
{
    CurOutBean findNearestByCid(String cid);
}
