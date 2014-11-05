/**
 * File Name: StatBankDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.StatBankBean;
import com.china.center.oa.finance.dao.StatBankDAO;
import com.china.center.oa.finance.vo.StatBankVO;


/**
 * StatBankDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankDAOImpl
 * @since 3.0
 */
public class StatBankDAOImpl extends BaseDAO<StatBankBean, StatBankVO> implements StatBankDAO
{
    public StatBankBean findByBankIdAndTimeKey(String bankId, String timeKey)
    {
        return this.findByUnique(bankId, timeKey);
    }
}
