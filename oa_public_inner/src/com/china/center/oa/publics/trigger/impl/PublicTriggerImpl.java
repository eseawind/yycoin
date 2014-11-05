/**
 * File Name: PublicTriggerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger.impl;


import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.publics.statics.PublicStatic;
import com.china.center.oa.publics.trigger.CommonJob;
import com.china.center.oa.publics.trigger.PublicTrigger;


/**
 * PublicTriggerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicTriggerImpl
 * @since 1.0
 */
public class PublicTriggerImpl implements PublicTrigger
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    /**
     * 每天凌晨1点执行<br>
     * 事务由目的对象保证
     */
    public void everyDayCarryWithOutTransactional()
    {
        Collection<CommonJob> values = PublicStatic.getDayJobMap().values();

        for (CommonJob commonJob : values)
        {
            triggerLog.info("begin excute " + commonJob.getJobName());

            try
            {
                commonJob.excuteJob();
            }
            catch (Throwable e)
            {
                triggerLog.error(e, e);
            }

            triggerLog.info("end excute " + commonJob.getJobName());
        }
    }

    /**
     * 每小时执行一次 <br>
     * 事务由目的对象保证
     */
    public void everyHourCarryWithOutTransactional()
    {
        Collection<CommonJob> values = PublicStatic.getHourJobMap().values();

        for (CommonJob commonJob : values)
        {
            triggerLog.info("begin excute " + commonJob.getJobName());

            try
            {
                commonJob.excuteJob();
            }
            catch (Throwable e)
            {
                triggerLog.error(e, e);
            }

            triggerLog.info("end excute " + commonJob.getJobName());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.PublicTrigger#putDayCommonJob(com.china.center.oa.publics.trigger.CommonJob)
     */
    public void putDayCommonJob(CommonJob job)
    {
        PublicStatic.getDayJobMap().remove(job.getJobName());

        PublicStatic.getDayJobMap().put(job.getJobName(), job);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.PublicTrigger#putHourCommonJob(com.china.center.oa.publics.trigger.CommonJob)
     */
    public void putHourCommonJob(CommonJob job)
    {
        PublicStatic.getHourJobMap().remove(job.getJobName());

        PublicStatic.getHourJobMap().put(job.getJobName(), job);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.PublicTrigger#removeDayCommonJob(java.lang.String)
     */
    public void removeDayCommonJob(String name)
    {
        PublicStatic.getDayJobMap().remove(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.PublicTrigger#removeHourCommonJob(java.lang.String)
     */
    public void removeHourCommonJob(String name)
    {
        PublicStatic.getHourJobMap().remove(name);
    }
}
