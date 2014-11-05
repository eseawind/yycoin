/**
 * File Name: DesktopStatic.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.statics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.publics.manager.CarryPlan;
import com.china.center.oa.publics.message.RegisterCommon;
import com.china.center.oa.publics.message.RegisterP2P;
import com.china.center.oa.publics.trigger.CommonJob;


/**
 * 由于OSGI在r的时候static是重新加载的,但是定义在manage里面依赖都会重新加载.但是inner如果r操作不会重新加载此处
 * 
 * @author ZHUZHU
 * @version 2012-1-14
 * @see PublicStatic
 * @since 3.0
 */
public abstract class PublicStatic
{
    /**
     * 桌面map
     */
    private static Map<String, DesktopListener> deskListenerMap = new HashMap();

    /**
     * 查询监听实现
     */
    private static Map<String, QueryListener> queryListenerMap = new HashMap();

    /**
     * 下拉列表的map
     */
    private static Map<String, List> selectMap = new HashMap();

    /**
     * 全局注入的执行计划
     */
    private static List<CarryPlan> carryPlanList = new ArrayList<CarryPlan>();

    /**
     * 全局锁
     */
    private static Object LOCK = new Object();

    /**
     * p2p消息map
     */
    private static Map<String, RegisterP2P> p2pMap = new HashMap<String, RegisterP2P>();

    /**
     * 普通的消息通知map
     */
    private static Map<String, RegisterCommon> commonMap = new HashMap<String, RegisterCommon>();

    /**
     * 每日执行的任务
     */
    private static Map<String, CommonJob> dayJobMap = new HashMap();

    /**
     * 每小时执行的任务
     */
    private static Map<String, CommonJob> hourJobMap = new HashMap();

    /**
     * @return the p2pMap
     */
    public static Map<String, RegisterP2P> getP2pMap()
    {
        return p2pMap;
    }

    /**
     * @return the commonMap
     */
    public static Map<String, RegisterCommon> getCommonMap()
    {
        return commonMap;
    }

    /**
     * @return the listenerMap
     */
    public static Map<String, DesktopListener> getDeskListenerMap()
    {
        return deskListenerMap;
    }

    /**
     * @return the listenerMap
     */
    public static Map<String, QueryListener> getQueryListenerMap()
    {
        return queryListenerMap;
    }

    /**
     * @return the lOCK
     */
    public static Object getLOCK()
    {
        return LOCK;
    }

    /**
     * @return the carryPlanList
     */
    public static List<CarryPlan> getCarryPlanList()
    {
        return carryPlanList;
    }

    /**
     * @return the selectMap
     */
    public static Map<String, List> getSelectMap()
    {
        return selectMap;
    }

    /**
     * @return the dayJobMap
     */
    public static Map<String, CommonJob> getDayJobMap()
    {
        return dayJobMap;
    }

    /**
     * @return the hourJobMap
     */
    public static Map<String, CommonJob> getHourJobMap()
    {
        return hourJobMap;
    }
}
