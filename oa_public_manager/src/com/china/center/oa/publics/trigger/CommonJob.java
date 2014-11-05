/**
 * File Name: CommonJob.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger;

/**
 * CommonJob
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see CommonJob
 * @since 1.0
 */
public interface CommonJob
{
    /**
     * 执行任务
     */
    void excuteJob();

    /**
     * job的标识
     * 
     * @return
     */
    String getJobName();
}
