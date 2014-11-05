/**
 * File Name: ProcessShortMessage.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager;


import com.china.center.oa.note.bean.ShortMessageTaskBean;


/**
 * ProcessShortMessage
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see ProcessShortMessage
 * @since 1.0
 */
public interface ProcessShortMessage
{
    void handleMessage();

    void moveDataToHis(ShortMessageTaskBean shortMessageTaskBean);

    void putHandleMessage(HandleMessage handle);

    void removeHandleMessage(Integer type);
}
