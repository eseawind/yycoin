/*
 * File Name: LogBeanHelper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-5-18
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.helper;


import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.LogBean;
import com.china.center.oa.sail.bean.OutBean;


/**
 * <描述>
 * 
 * @author zhuzhu
 * @version 2007-5-18
 * @see
 * @since
 */
public class LogBeanHelper
{
    /**
     * 记录日志
     * 
     * @param outBean
     * @param baseBean
     * @param oprType
     * @param stafferName
     * @return
     */
    public static LogBean getLogBean(OutBean outBean, BaseBean baseBean, int oprType, String stafferName)
    {
        LogBean bean = new LogBean();

        bean.setOprType(oprType);

        bean.setOutId(outBean.getFullId());

        bean.setType(outBean.getType());

        bean.setOutType(outBean.getOutType());

        bean.setProductName(baseBean.getProductName());

        bean.setCurrent(baseBean.getAmount());

        bean.setValue(baseBean.getPrice());

        bean.setStaffer(stafferName);

        bean.setRefId(outBean.getRefOutFullId());

        return bean;
    }
}
