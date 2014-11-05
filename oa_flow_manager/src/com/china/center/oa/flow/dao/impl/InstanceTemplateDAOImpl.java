/**
 * File Name: InstanceTemplateDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.InstanceTemplateBean;
import com.china.center.oa.flow.dao.InstanceTemplateDAO;


/**
 * InstanceTemplateDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see InstanceTemplateDAOImpl
 * @since 1.0
 */
public class InstanceTemplateDAOImpl extends BaseDAO<InstanceTemplateBean, InstanceTemplateBean> implements InstanceTemplateDAO
{
    /**
     * queryByDuration
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<InstanceTemplateBean> queryByDuration(String beginTime, String endTime)
    {
        return this.queryEntityBeansByCondition("where logTime >= ? and logTime <= ?", beginTime, endTime);
    }
}
