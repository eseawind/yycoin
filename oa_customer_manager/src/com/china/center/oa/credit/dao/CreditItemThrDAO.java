/**
 * File Name: CreditItemThrDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.vo.CreditItemThrVO;


/**
 * CreditItemThrDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditItemThrDAO
 * @since 1.0
 */
public interface CreditItemThrDAO extends DAO<CreditItemThrBean, CreditItemThrVO>
{
    int countByPidAndIndexPos(String pid, int indexPos);

    CreditItemThrBean findMaxDelayItem();

    CreditItemThrBean findDelayItemByDays(int days);

    CreditItemThrBean findMaxBusiness();

    CreditItemThrBean findSingleMaxBusinessByValue(double sigleMax);

    CreditItemThrBean findTotalBusiness();

    CreditItemThrBean findTotalBusinessByValue(double sumBusiness);
}
