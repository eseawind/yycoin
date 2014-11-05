/**
 * File Name: MonitorImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.osgi.monitor.MonitorTools;
import com.china.center.osgi.monitor.bean.MemeryMonitorBean;
import com.china.center.osgi.monitor.bean.ThreadPoolMonitorBean;
import com.china.center.webportal.listener.MySessionListener;


/**
 * MonitorImpl
 * 
 * @author ZHUZHU
 * @version 2012-1-14
 * @see MonitorImpl
 * @since 3.0
 */
public class MonitorImpl
{
    private final Log monitorLog = LogFactory.getLog("monitor");

    public void monitor()
    {
        synchronized (MonitorImpl.class)
        {
            MemeryMonitorBean monitorMemery = MonitorTools.monitorMemery();

            monitorLog.info(monitorMemery);

            ThreadPoolMonitorBean monitorThreadPool = MonitorTools.monitorThreadPool();

            monitorLog.info(monitorThreadPool);

            monitorLog.info("Session count:" + MySessionListener.getSessionSets().size());
        }
    }
}
