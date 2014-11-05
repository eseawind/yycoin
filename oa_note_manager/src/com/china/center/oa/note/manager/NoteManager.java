/**
 * File Name: NoteManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager;


import com.china.center.common.MYException;
import com.china.center.oa.note.bean.ReceiveTaskBean;


/**
 * NoteManager
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see NoteManager
 * @since 1.0
 */
public interface NoteManager
{
    boolean addReceiveTask(ReceiveTaskBean task)
        throws MYException;

    void synReceiveToSend();

    void changeInitToWaitSend();

    void moveTimeoutData();
}
