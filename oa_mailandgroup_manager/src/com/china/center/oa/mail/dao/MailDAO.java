/**
 * File Name: MailDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.mail.bean.MailBean;


/**
 * MailDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailDAO
 * @since 1.0
 */
public interface MailDAO extends DAO<MailBean, MailBean>
{
    boolean updateStatus(String id, int status);

    boolean updateFeeback(String id, int feeback);

    String findNextId(String currentId, String stafferId);

    String findPreviewId(String currentId, String stafferId);
}
