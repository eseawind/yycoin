/**
 * File Name: CreditLevelDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.credit.vo.CreditLevelVO;


/**
 * CreditLevelDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditLevelDAOImpl
 * @since 1.0
 */
public class CreditLevelDAOImpl extends BaseDAO<CreditLevelBean, CreditLevelVO> implements CreditLevelDAO
{
    public CreditLevelBean findByVal(int val)
    {
        return this.findUnique("where min <= ? and max >= ?", (double)val, (double)val);
    }
}
