/**
 * File Name: OutBillDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.vo.OutBillVO;


/**
 * OutBillDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see OutBillDAO
 * @since 3.0
 */
public interface OutBillDAO extends DAO<OutBillBean, OutBillVO>
{
    double sumByCondition(ConditionParse condition);

    int lockByCondition(ConditionParse condition);

    double sumByRefId(String refId);

    int countUnitInBill(String id);

    boolean updateSrcMoneys(String id, double srcMoneys);
}
