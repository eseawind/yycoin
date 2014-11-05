/**
 * File Name: HandleMessage.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-8-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager;


import com.china.center.oa.note.bean.ShortMessageTaskBean;


/**
 * HandleMessage
 * 
 * @author ZHUZHU
 * @version 2009-8-8
 * @see HandleMessage
 * @since 1.0
 */
public interface HandleMessage
{
    /**
     * flow
     */
    int TYPE_FLOW = 101;

    /**
     * out in YY
     */
    int TYPE_OUT = 102;

    /**
     * ask
     */
    int TYPE_ASK = 103;

    /**
     * stock
     */
    int TYPE_STOCK = 104;

    /**
     * 产品通知
     */
    int TYPE_PRODUCT = 105;

    /**
     * getHandleType
     * 
     * @return handleType
     */
    int getHandleType();

    /**
     * handleMessage
     * 
     * @param task
     *            SMS task
     */
    void handleMessage(ShortMessageTaskBean task);

    void cancelMessage(ShortMessageTaskBean task);
}
