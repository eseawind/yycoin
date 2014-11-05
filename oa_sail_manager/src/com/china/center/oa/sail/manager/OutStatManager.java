/**
 * File Name: OutStatManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager;


import com.china.center.common.MYException;


/**
 * OutStatManager
 * 
 * @author ZHUZHU
 * @version 2012-5-8
 * @see OutStatManager
 * @since 3.0
 */
public interface OutStatManager
{
    /**
     * 统计付款状态是否完全正确
     * 
     * @throws MYException
     */
    void statOutPay()
        throws MYException;;
        
    void statsPersonalSwatch();
}
