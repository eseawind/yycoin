/**
 * File Name: CreditLevelDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.vo.CreditLevelVO;


/**
 * CreditLevelDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditLevelDAO
 * @since 1.0
 */
public interface CreditLevelDAO extends DAO<CreditLevelBean, CreditLevelVO>
{
    CreditLevelBean findByVal(int val);
}
