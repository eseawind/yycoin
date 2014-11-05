/**
 * File Name: StatBankManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;

/**
 * StatBankManager
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankManager
 * @since 3.0
 */
public interface StatBankManager
{
    /**
     * 每月1号统计银行的结余
     */
    void statBank();

    /**
     * 查询帐号的余额
     * 
     * @param bankId
     * @return
     */
    double findTotalByBankId(String bankId);
}
