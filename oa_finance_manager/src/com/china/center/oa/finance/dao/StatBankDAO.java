/**
 * File Name: StatBankDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.StatBankBean;
import com.china.center.oa.finance.vo.StatBankVO;


/**
 * StatBankDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankDAO
 * @since 3.0
 */
public interface StatBankDAO extends DAO<StatBankBean, StatBankVO>
{
    StatBankBean findByBankIdAndTimeKey(String bankId, String timeKey);
}
