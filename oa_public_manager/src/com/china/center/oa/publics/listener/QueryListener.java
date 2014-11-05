/**
 * File Name: QueryListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import java.util.List;


/**
 * QueryListener
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see QueryListener
 * @since 1.0
 */
public interface QueryListener
{
    String getKey();

    List<?> getBeanList();
}
