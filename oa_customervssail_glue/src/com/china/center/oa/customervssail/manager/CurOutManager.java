/**
 * File Name: CurOutManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customervssail.manager;

/**
 * CurOutManager
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CurOutManager
 * @since 1.0
 */
public interface CurOutManager
{
    void statOut();

    /**
     * 清理三个月前的日志信息
     */
    void deleteHis();
}
