/**
 * File Name: ShortMessageConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * ShortMessageConstant
 * 
 * @author ZHUZHU
 * @version 2009-7-28
 * @see ShortMessageConstant
 * @since 1.0
 */
public interface ShortMessageConstant
{
    /**
     * only send
     */
    int MTYPE_ONLY_SEND = 0;

    /**
     * send and receive
     */
    int MTYPE_ONLY_SEND_RECEIVE = 1;

    /**
     * INIT
     */
    @Defined(key = "smsStatus", value = "初始")
    int STATUS_INIT = 0;

    /**
     * STATUS_WAIT_SEND
     */
    @Defined(key = "smsStatus", value = "等待发送")
    int STATUS_WAIT_SEND = 11;

    /**
     * send success
     */
    @Defined(key = "smsStatus", value = "发送成功")
    int STATUS_SEND_SUCCESS = 1;

    /**
     * failure
     */
    @Defined(key = "smsStatus", value = "发送失败")
    int STATUS_SEND_FAILURE = 2;

    /**
     * receive success
     */
    @Defined(key = "smsStatus", value = "接收回复")
    int STATUS_RECEIVE_SUCCESS = 3;

    /**
     * timeout
     */
    @Defined(key = "smsStatus", value = "超时结束")
    int STATUS_OVER_TIME = 99;

    /**
     * end
     */
    @Defined(key = "smsStatus", value = "正常结束")
    int STATUS_END_COMMON = 100;
}
