/**
 * File Name: ExpenseApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;


/**
 * ExpenseApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-9-22
 * @see ExpenseApplyDAO
 * @since 3.0
 */
public interface ExpenseApplyDAO extends DAO<ExpenseApplyBean, ExpenseApplyVO>
{
    int updateStatus(String id, int status);

    /**
     * updateBorrowTotal
     * 
     * @param id
     * @param borrowTotal
     * @return
     */
    int updateBorrowTotal(String id, long borrowTotal);

    /**
     * updateTotal
     * 
     * @param id
     * @param total
     * @return
     */
    int updateTotal(String id, long total);

    /**
     * updateBorrowStafferId
     * 
     * @param id
     * @param borrowStafferId
     * @return
     */
    int updateBorrowStafferId(String id, String borrowStafferId);
    
    /**
     * updateCompliance
     * @param id
     * @param compliance
     * @return
     */
    int updateCompliance(String id, String compliance);

}
