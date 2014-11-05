/**
 * File Name: TemplateFileManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.flow.bean.TemplateFileBean;


/**
 * TemplateFileManager
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TemplateFileManager
 * @since 1.0
 */
public interface TemplateFileManager
{
    boolean addBean(User user, TemplateFileBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;

    boolean updateBean(User user, TemplateFileBean bean)
        throws MYException;
}
