/**
 * File Name: StafferListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotBean;


/**
 * DutyListener
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see DepotListener
 * @since 1.0
 */
public interface DepotListener extends ParentListener
{
    /**
     * 删除仓库
     * 
     * @param stafferId
     */
    void onDeleteDepot(User user, DepotBean bean)
        throws MYException;
}
