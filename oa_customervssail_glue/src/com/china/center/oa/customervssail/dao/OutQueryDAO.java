/**
 * File Name: OutQueryDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customervssail.dao;


import java.util.List;

import com.china.center.oa.client.wrap.NotPayWrap;


/**
 * OutQueryDAO
 * 
 * @author ZHUZHU
 * @version 2010-11-14
 * @see OutQueryDAO
 * @since 3.0
 */
public interface OutQueryDAO
{
    List<NotPayWrap> listNotPayWrap();
}
