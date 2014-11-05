/**
 * File Name: StafferListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import com.center.china.osgi.publics.ParentListener;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * StafferListener
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see StafferListener
 * @since 1.0
 */
public interface StafferListener extends ParentListener
{
    /**
     * onAdd
     * 
     * @param bean
     */
    void onAdd(StafferBean bean)
        throws MYException;

    /**
     * onUpdate
     * 
     * @param bean
     */
    void onUpdate(StafferBean bean)
        throws MYException;

    /**
     * onDelete
     * 
     * @param stafferId
     */
    void onDelete(String stafferId)
        throws MYException;

    void onDrop(StafferBean bean)
        throws MYException;
}
