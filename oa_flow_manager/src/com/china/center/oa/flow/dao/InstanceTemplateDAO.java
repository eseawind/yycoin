/**
 * File Name: InstanceTemplateDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.InstanceTemplateBean;


/**
 * InstanceTemplateDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see InstanceTemplateDAO
 * @since 1.0
 */
public interface InstanceTemplateDAO extends DAO<InstanceTemplateBean, InstanceTemplateBean>
{
    List<InstanceTemplateBean> queryByDuration(String beginTime, String endTime);
}
