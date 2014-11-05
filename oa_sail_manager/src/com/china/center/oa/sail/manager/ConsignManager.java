/**
 * File Name: ConsignManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-29<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager;


import java.util.List;

import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.TransportBean;


/**
 * ConsignManager
 * 
 * @author ZHUZHU
 * @version 2010-11-29
 * @see ConsignManager
 * @since 3.0
 */
public interface ConsignManager
{
    boolean addTransport(TransportBean bean)
        throws MYException;

    boolean updateTransport(TransportBean bean)
        throws MYException;

    boolean addConsign(ConsignBean bean)
        throws MYException;

    boolean updateConsign(ConsignBean bean)
        throws MYException;
    
    boolean updateAllConsign(List<ConsignBean> list)
    throws MYException;

    boolean delConsign(String id)
        throws MYException;

    boolean delTransport(String id)
        throws MYException;
    
    boolean handleAllConsign(List<ConsignBean> list)
    	throws MYException;
}
