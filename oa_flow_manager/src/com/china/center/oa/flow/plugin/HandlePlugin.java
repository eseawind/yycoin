/**
 * File Name: HandlerPlugin.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.plugin;


import java.util.List;

import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * HandlerPlugin
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see HandlePlugin
 * @since 1.0
 */
public interface HandlePlugin
{
    /**
     * hasAuth
     * 
     * @param user
     * @param token
     * @param instanceId
     * @param locationId
     * @param processers
     * @return
     */
    boolean hasAuth(String instanceId, List<String> processers);

    /**
     * listNextHandler
     * 
     * @param nextTokenId
     * @return
     */
    List<StafferBean> listNextHandler(String instanceId, String nextTokenId);

    /**
     * list流程的查阅者
     * 
     * @param instanceId
     * @param viewer
     * @throws MYException
     */
    List<StafferBean> listInstanceViewer(String instanceId);

    String getHandleName(String handleId);

    String getTypeName();

    /**
     * getType
     * 
     * @return
     */
    int getType();
}
