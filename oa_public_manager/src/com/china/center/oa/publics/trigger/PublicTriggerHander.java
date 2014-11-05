/**
 * File Name: PublicTriggerHander.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger;


import java.util.List;


/**
 * PublicTriggerHander
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicTriggerHander
 * @since 1.0
 */
public class PublicTriggerHander
{
    private List<CommonJob> dayJobList = null;

    private List<CommonJob> hourJobList = null;

    private PublicTrigger publicTrigger = null;

    /**
     * default constructor
     */
    public PublicTriggerHander()
    {
    }

    public void init()
    {
        if (dayJobList != null)
        {
            for (CommonJob each : dayJobList)
            {
                publicTrigger.putDayCommonJob(each);
            }
        }

        if (hourJobList != null)
        {
            for (CommonJob each : hourJobList)
            {
                publicTrigger.putHourCommonJob(each);
            }
        }
    }

    public void destory()
    {
        if (dayJobList != null)
        {
            for (CommonJob each : dayJobList)
            {
                publicTrigger.removeDayCommonJob(each.getJobName());
            }
        }

        if (hourJobList != null)
        {
            for (CommonJob each : hourJobList)
            {
                publicTrigger.removeHourCommonJob(each.getJobName());
            }
        }
    }

    /**
     * @return the dayJobList
     */
    public List<CommonJob> getDayJobList()
    {
        return dayJobList;
    }

    /**
     * @param dayJobList
     *            the dayJobList to set
     */
    public void setDayJobList(List<CommonJob> dayJobList)
    {
        this.dayJobList = dayJobList;
    }

    /**
     * @return the hourJobList
     */
    public List<CommonJob> getHourJobList()
    {
        return hourJobList;
    }

    /**
     * @param hourJobList
     *            the hourJobList to set
     */
    public void setHourJobList(List<CommonJob> hourJobList)
    {
        this.hourJobList = hourJobList;
    }

    /**
     * @return the publicTrigger
     */
    public PublicTrigger getPublicTrigger()
    {
        return publicTrigger;
    }

    /**
     * @param publicTrigger
     *            the publicTrigger to set
     */
    public void setPublicTrigger(PublicTrigger publicTrigger)
    {
        this.publicTrigger = publicTrigger;
    }

}
