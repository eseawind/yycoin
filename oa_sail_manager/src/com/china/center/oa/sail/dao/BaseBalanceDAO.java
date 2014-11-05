/**
 * File Name: BaseBalanceDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.vo.BaseBalanceVO;


/**
 * BaseBalanceDAO
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see BaseBalanceDAO
 * @since 3.0
 */
public interface BaseBalanceDAO extends DAO<BaseBalanceBean, BaseBalanceVO>
{
    /**
     * 查询已经通过的结算清单
     * 
     * @param baseId
     * @return
     */
    List<BaseBalanceVO> queryPassBaseBalance(String baseId);

    /**
     * 查询已经通过的结算清单
     * 
     * @param baseId
     * @return
     */
    int sumPassBaseBalance(String baseId);

    boolean updateInvoice(String id, double invoiceMoney);
}
