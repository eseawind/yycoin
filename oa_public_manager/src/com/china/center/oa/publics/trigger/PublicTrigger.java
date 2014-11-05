/**
 * File Name: PublicTrigger.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger;

/**
 * PublicTrigger
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicTrigger
 * @since 1.0
 */
public interface PublicTrigger
{
    void putDayCommonJob(CommonJob job);

    void putHourCommonJob(CommonJob job);

    void removeDayCommonJob(String name);

    void removeHourCommonJob(String name);
}
