/**
 * File Name: ShortMessageTaskDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.note.bean.ShortMessageTaskBean;


/**
 * ShortMessageTaskDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see ShortMessageTaskDAO
 * @since 1.0
 */
public interface ShortMessageTaskDAO extends DAO<ShortMessageTaskBean, ShortMessageTaskBean>
{
    ShortMessageTaskBean findByReceiverAndHandId(String receiver, String handId);

    boolean updateInitToWaitSend();
}
